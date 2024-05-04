package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.DAO.ChatDAO;
import org.example.DAO.MessageDAO;
import org.example.DAO.UserDAO;

import java.sql.SQLException;
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
    Chat chat;
    User user;
    String text;
    Timestamp dateTime;

    public Chat getChat() throws SQLException {
        if(chat == null){
            chat = ChatDAO.getMessageChat(this);
        }
        return chat;
    }

    public User getUser() throws SQLException {
        if(user == null){
            user = UserDAO.getMessageUser(this);
        }
        return user;
    }

    public String getText() throws SQLException {
        if(text == null){
            text = MessageDAO.getText(this);
        }
        return text;
    }

    public Timestamp getDateTime() throws SQLException {
        if(dateTime == null){
            dateTime = MessageDAO.getDateTime(this);
        }
        return dateTime;
    }

}
