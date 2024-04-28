package org.example.DTO.MessageDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * DTO общего назначения для Message.
 *
 * @see org.example.entities.Message
 * */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageDTO {

    int id;
    int userId;
    String text;
    Timestamp dateTime;

}
