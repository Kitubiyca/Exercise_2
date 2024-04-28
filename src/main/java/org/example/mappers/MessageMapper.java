package org.example.mappers;

import org.example.DTO.MessageDTO.MessageDTO;
import org.example.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper для сообщений.
 * */

@Mapper
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageDTO toDTO(Message entity);

}
