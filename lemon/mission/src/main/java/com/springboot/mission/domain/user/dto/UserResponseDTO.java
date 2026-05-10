package com.springboot.mission.domain.user.dto;

import com.springboot.mission.domain.user.entity.User;
import lombok.Builder;

public class UserResponseDTO {

    @Builder
    public record JoinResponse(
            String message,
            Long userId,      // 가입된 유저의 고유 ID
            String nickname   // 가입된 유저의 닉네임
    ){
        //Entity -> DTO 변환
        // 가입된 엔티티 정보를 바탕으로 응답 DTO를 생성합니다.
        public static JoinResponse from(User user) {
            return JoinResponse.builder()
                    .message(user.getNickname() + "님, 회원가입에 성공했습니다.")
                    .userId(user.getId())
                    .nickname(user.getNickname())
                    .build();
        }
    }
}
