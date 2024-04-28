package org.example.mappers;

import org.example.DTO.UserDTO.UserInfoDTO;
import org.example.DTO.UserDTO.UserSignInDTO;
import org.example.DTO.UserDTO.UserSignUpDTO;
import org.example.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper для пользователей.
 * */

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserSignUpDTO dto);

    User toEntity(UserSignInDTO dto);

    UserInfoDTO toInfoDTO(User user);

}
