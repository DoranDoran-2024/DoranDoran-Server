package com.sash.dorandoran.user.dao;

import com.sash.dorandoran.user.domain.Attendance;
import com.sash.dorandoran.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);

}
