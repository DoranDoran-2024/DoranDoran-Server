package com.sash.dorandoran.user.implement;

import com.sash.dorandoran.chat.dao.ChatRoomRepository;
import com.sash.dorandoran.common.exception.GeneralException;
import com.sash.dorandoran.common.response.status.ErrorStatus;
import com.sash.dorandoran.user.business.DiaryMapper;
import com.sash.dorandoran.user.dao.AttendanceRepository;
import com.sash.dorandoran.user.dao.UserRepository;
import com.sash.dorandoran.user.domain.Attendance;
import com.sash.dorandoran.user.domain.User;
import com.sash.dorandoran.user.presentation.dto.DiarySummaryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.IntStream;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void checkAttendance(User user) {
        LocalDate today = LocalDate.now();
        if (attendanceRepository.findByUserAndDateBetween(user, today, today).isEmpty()) {
            Attendance attendance = Attendance.builder()
                    .user(user)
                    .date(today)
                    .build();
            attendanceRepository.save(attendance);
        }
    }

    public List<Boolean> getAttendanceStatus(User user) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));

        List<LocalDate> attendedDates = attendanceRepository.findByUserAndDateBetween(user, startOfWeek, endOfWeek)
                .stream()
                .map(Attendance::getDate)
                .toList();

        return IntStream.range(0, 7)
                .mapToObj(i -> attendedDates.contains(startOfWeek.plusDays(i)))
                .toList();
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }

    public DiarySummaryListResponse getDiaries(User user) {
        return DiaryMapper.toDiarySummaryListResponse(chatRoomRepository.findByUserOrderByCreatedAtDesc(user));
    }

}
