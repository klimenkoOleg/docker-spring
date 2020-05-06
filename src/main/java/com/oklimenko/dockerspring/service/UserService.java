package com.oklimenko.dockerspring.service;

import com.oklimenko.dockerspring.dto.CreateUserRequest;
import com.oklimenko.dockerspring.dto.CreateUserResponse;
import com.oklimenko.dockerspring.dto.GetUserResponse;
import com.oklimenko.dockerspring.dto.UpdateUserRequest;
import com.oklimenko.dockerspring.entity.UserEntity;
import com.oklimenko.dockerspring.mapper.UserMapper;
import com.oklimenko.dockerspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public CreateUserResponse createUser(CreateUserRequest request) {
        UserEntity userEntity = mapper.map(request);
        UserEntity savedUser = userRepository.save(userEntity);
        return new CreateUserResponse(savedUser.getId());
    }

    public GetUserResponse getUser(Long userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        UserEntity userEntity = userEntityOptional.orElseThrow(() -> {
            log.error("User not found by id={}", userId);
            return new RuntimeException("User not found");
        });
        return mapper.map(userEntity);
    }

    public void deleteUser(Long userId) {
        //TODO: make idempotent
        userRepository.deleteById(userId);
    }

    public void updateUser(Long userId, UpdateUserRequest request) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        UserEntity userEntity = userEntityOptional.orElseThrow( () -> {
            log.error("User not found by id={}", userId);
            return new RuntimeException("User not found");
        });
        mapper.map(userEntity, request);
        userRepository.save(userEntity);
    }

    public List<GetUserResponse> getAll() {
        return mapper.map(userRepository.findAll());
    }
}
