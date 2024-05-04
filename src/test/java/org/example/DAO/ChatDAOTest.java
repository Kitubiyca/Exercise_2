package org.example.DAO;

import org.example.entities.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class ChatDAOTest {

    @Test
    void getMessageChat() throws SQLException {
        Message message = new Message(1, null, null, null, null);
        Assertions.assertEquals(3, message.getChat().getId());
    }

}