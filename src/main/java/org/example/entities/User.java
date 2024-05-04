package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.DAO.ChatDAO;
import org.example.DAO.MessageDAO;
import org.example.DAO.UserDAO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс для создания сущностей пользователей из БД.
 * */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    int id;
    String name;
    String password;
    ArrayList<Chat> chats;
    ArrayList<Message> messages;

    public String getName() throws SQLException {
        if (name == null) {
            name = UserDAO.getUserName(this);
        }
        return name;
    }

    public ArrayList<Chat> getChats() throws SQLException {
        if (chats == null) {
            chats = ChatDAO.getUserChats(this);
        }
        return chats;
    }

    public ArrayList<Message> getMessages() throws SQLException {
        if (messages == null) {
            messages = MessageDAO.getUserMessages(this);
        }
        return messages;
    }

}
