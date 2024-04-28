package org.example.DTO.ChatDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO общего назначения для Chat.
 *
 * @see org.example.entities.Chat
 * */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatDTO {

    private int id;
    private String name;
    private int owner;

}
