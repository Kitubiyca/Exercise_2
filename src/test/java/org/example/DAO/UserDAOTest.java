package org.example.DAO;

import org.example.entities.Chat;
import org.example.entities.Message;
import org.example.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class UserDAOTest {

    @Test
    void getUserName() throws SQLException {
        User user = new User(1, null, null, null, null);
        Assertions.assertEquals("oldUser", user.getName());
    }

    @Test
    void getOwner() throws SQLException {
        Chat chat = new Chat(3, null, null, null, null);
        Assertions.assertEquals(1, chat.getOwner().getId());
    }

    @Test
    void getMessageUser() throws SQLException {
        Message message = new Message(2, null, null, null, null);
        Assertions.assertEquals(2, message.getUser().getId());
    }

}