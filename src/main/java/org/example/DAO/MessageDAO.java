package org.example.DAO;

import org.example.DBConfig;
import org.example.entities.Chat;
import org.example.entities.Message;
import org.example.entities.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Класс для взаимодействия с сообщенияим в БД.
 * */
public class MessageDAO {

    /**
     * Получает все сообщения из чата.
     *
     * @param chatId id чата.
     *
     * @return список сообщений.
     * */
    public static ArrayList<Message> getChatMessages(int chatId) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM public.message WHERE chat_id=?")) {
            ps.setInt(1, chatId);
            ResultSet rs = ps.executeQuery();
            ArrayList<Message> messages = new ArrayList<>();
            while(rs.next()){
                messages.add(new Message(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getTimestamp(5)
                ));
            }
            return messages;
        }
    }

    /**
     * Отправляет новое сообщение в БД.
     *
     * @param chat чат.
     *
     * @param user автор сообщения.
     *
     * @param text текст сообщения.
     * */
    public static void send(Chat chat, User user, String text) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "INSERT INTO public.message (chat_id, user_id, text_content) VALUES (?, ?, ?)")) {
            ps.setInt(1, chat.getId());
            ps.setInt(2, user.getId());
            ps.setString(3, text);
            ps.executeUpdate();
        }
    }

}
