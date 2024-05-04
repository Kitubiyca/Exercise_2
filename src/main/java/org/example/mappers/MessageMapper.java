package org.example.mappers;

import org.example.DTO.MessageDTO.MessageDTO;
import org.example.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.SQLException;

/**
 * Mapper для сообщений.
 * */

@Mapper
public abstract class MessageMapper {

    public static MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    public MessageDTO toDTO(Message entity) throws RuntimeException {
        try{
            MessageDTO dto = new MessageDTO();
            dto.setId(entity.getId());
            dto.setUserId(entity.getUser().getId());
            dto.setText(entity.getText());
            dto.setDateTime(entity.getDateTime());
            return dto;
        } catch (SQLException e){
            throw new RuntimeException("SQL exception: " + e.getMessage());
        }

    }

}
