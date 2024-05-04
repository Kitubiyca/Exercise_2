package org.example.DAO;

import org.example.entities.Message;
import org.example.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class MessageDAOTest {

    @Test
    void getUserMessages() throws SQLException {
        User user = new User(1, null, null, null, null);
        Assertions.assertEquals("hi", user.getMessages().get(0).getText());
    }

    @Test
    void getText() throws SQLException {
        Message message = new Message(3, null, null, null, null);
        Assertions.assertEquals("cool", MessageDAO.getText(message));
    }

}