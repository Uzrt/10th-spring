package com.springboot.mission.domain.user.service;

import com.springboot.mission.domain.user.code.MemberErrorCode;
import com.springboot.mission.domain.user.dto.UserRequestDTO;
import com.springboot.mission.domain.user.dto.UserResponseDTO;
import com.springboot.mission.domain.user.entity.User;
import com.springboot.mission.domain.user.exception.UserException;
import com.springboot.mission.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDTO.JoinResponse join(UserRequestDTO.JoinUser request) {

        // 1. 중복 가입 확인
        if (userRepository.existsByMail(request.mail())) {
            throw new UserException(MemberErrorCode.MEMBER_ALREADY_EXISTS);
        }

        // 2. DTO -> Entity (정적 팩토리 메서드 활용)
        User user = request.toEntity();

        // 3. 저장
        User savedUser = userRepository.save(user);

        // 4. Entity -> DTO 반환
        return UserResponseDTO.JoinResponse.from(savedUser);
    }
}
