package org.example.mappers;

import org.example.DTO.ChatDTO.ChatDTO;
import org.example.entities.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper для чатов.
 * */

@Mapper
public interface ChatMapper {

    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    ChatDTO toDTO(Chat entity);

}
