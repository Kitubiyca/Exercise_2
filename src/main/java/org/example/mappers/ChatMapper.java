package org.example.mappers;

import org.example.DTO.ChatDTO.ChatDTO;
import org.example.entities.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.SQLException;

/**
 * Mapper для чатов.
 * */

@Mapper
public abstract class ChatMapper {

    public static ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    public ChatDTO toDTO(Chat entity) throws RuntimeException {
        try{
            ChatDTO dto = new ChatDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setOwner(entity.getOwner().getId());
            return dto;
        } catch (SQLException e){
            throw new RuntimeException("SQL exception: " + e.getMessage());
        }

    }

}
