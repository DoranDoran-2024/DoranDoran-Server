package com.sash.dorandoran.user.implement;

import com.sash.dorandoran.feign.client.NicknameClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.sash.dorandoran.common.DoranDoranConstant.*;

@Component
@RequiredArgsConstructor
public class NicknameGenerator {

    private final NicknameClient nicknameClient;

    public String getOneRandomNickName() {
        return getOneRandomNickNameWithDetails(NICKNAME_MAX_LENGTH);
    }

    private String getOneRandomNickNameWithDetails(int maxLength) {
        List<String> nickNameWords = nicknameClient.getNickName(NICKNAME_FORMAT, NICKNAME_COUNT, maxLength).getWords();
        if (!nickNameWords.isEmpty()) return nickNameWords.get(0);
        return DEFAULT_NICKNAME;
    }
}
