package org.example.DAO;

import org.example.DBConfig;
import org.example.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс для взаимодействия со связью пользователей и чатов в БД.
 * */
public class UserChatDAO {

    /**
     * Удаляет пользователя из чата.
     *
     * @param user пользователь.
     *
     * @param chatId id чата.
     * */
    public static void leaveChat(User user, int chatId) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "DELETE FROM public.user_chat WHERE user_id=? AND chat_id=?")) {
            ps.setInt(1, user.getId());
            ps.setInt(2, chatId);
            ps.executeUpdate();
        }
    }

    /**
     * Добавляет пользователя в чат.
     *
     * @param user пользователь.
     *
     * @param chatId id чата.
     * */
    public static void joinChat(User user, int chatId) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "INSERT INTO public.user_chat (user_id, chat_id) VALUES (?, ?)")) {
            ps.setInt(1, user.getId());
            ps.setInt(2, chatId);
            ps.executeUpdate();
        }
    }

    /**
     * Проверяет, состоит ли пользователь в чате.
     *
     * @param user пользователь.
     *
     * @param chatId id чата.
     *
     * @return true, если да, иначе false.
     * */
    public static boolean isUserJoined(User user, int chatId) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM public.user_chat WHERE user_id=? AND chat_id=?")) {
            ps.setInt(1, user.getId());
            ps.setInt(2, chatId);
            return ps.executeQuery().next();
        }
    }

}
