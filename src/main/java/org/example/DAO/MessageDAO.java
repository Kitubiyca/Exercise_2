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
                        new Chat(rs.getInt(2), null, null, null, null),
                        new User(rs.getInt(3), null, null, null, null),
                        rs.getString(4),
                        rs.getTimestamp(5)
                ));
            }
            return messages;
        }
    }

    /**
     * Получает все сообщения пользователя.
     *
     * @param user пользователь.
     *
     * @return список сообщений.
     * */
    public static ArrayList<Message> getUserMessages(User user) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM public.message WHERE user_id=?")) {
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            ArrayList<Message> messages = new ArrayList<>();
            while(rs.next()){
                messages.add(new Message(
                        rs.getInt(1),
                        new Chat(rs.getInt(2), null, null, null, null),
                        new User(rs.getInt(3), null, null, null, null),
                        rs.getString(4),
                        rs.getTimestamp(5)
                ));
            }
            return messages;
        }
    }

    /**
     * Возвращает текст сообщения.
     *
     * @param message сообщение.
     *
     * @return текст сообщения.
     * */
    public static String getText(Message message) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "SELECT text_content FROM public.message WHERE id=?")) {
            ps.setInt(1, message.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
        }
        throw new SQLException("message not found");
    }

    /**
     * Возвращает дату сообщения.
     *
     * @param message сообщение.
     *
     * @return дата сообщения.
     * */
    public static Timestamp getDateTime(Message message) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "SELECT date_time FROM public.message WHERE id=?")) {
            ps.setInt(1, message.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getTimestamp(1);
            }
        }
        throw new SQLException("message not found");
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
