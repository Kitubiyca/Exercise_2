package org.example.mappers;

import org.example.DTO.UserDTO.UserInfoDTO;
import org.example.DTO.UserDTO.UserSignInDTO;
import org.example.DTO.UserDTO.UserSignUpDTO;
import org.example.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.SQLException;

/**
 * Mapper для пользователей.
 * */

@Mapper
public abstract class UserMapper {

    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract User toEntity(UserSignUpDTO dto);

    public abstract User toEntity(UserSignInDTO dto);

    public UserInfoDTO toInfoDTO(User user){
        try{
            UserInfoDTO dto = new UserInfoDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            return dto;
        } catch (SQLException e){
            throw new RuntimeException("SQL exception: " + e.getMessage());
        }

    }

}
