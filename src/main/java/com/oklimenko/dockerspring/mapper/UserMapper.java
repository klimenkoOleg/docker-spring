package com.oklimenko.dockerspring.mapper;

import com.oklimenko.dockerspring.dto.CreateUserRequest;
import com.oklimenko.dockerspring.dto.GetUserResponse;
import com.oklimenko.dockerspring.dto.UpdateUserRequest;
import com.oklimenko.dockerspring.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity map(CreateUserRequest request);
    void map(@MappingTarget UserEntity userEntity, UpdateUserRequest request);
    UserEntity map(UpdateUserRequest request);
    GetUserResponse map(UserEntity request);
}
