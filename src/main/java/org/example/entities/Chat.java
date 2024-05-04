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
 * Класс для создания сущностей чатов из БД.
 * */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Chat {

    private int id;
    private String name;
    private User owner;
    ArrayList<User> users;
    ArrayList<Message> messages;

    public String getName() throws SQLException {
        if (name == null) {
            name = ChatDAO.getChat(id).getName();
        }
        return name;
    }

    public User getOwner() throws SQLException {
        if (owner == null) {
            owner = UserDAO.getOwner(this);
        }
        return owner;
    }

    public ArrayList<User> getUsers() throws SQLException {
        if (users == null) {
            users = UserDAO.getUsersInChat(this);
        }
        return users;
    }

    public ArrayList<Message> getMessages() throws SQLException {
        if (messages == null) {
            messages = MessageDAO.getChatMessages(id);
        }
        return messages;
    }

}
