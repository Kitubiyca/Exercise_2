package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Класс для создания сущностей сообщений из БД.
 * */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message {

    int id;
    int chatId;
    int userId;
    String text;
    Timestamp dateTime;

}
