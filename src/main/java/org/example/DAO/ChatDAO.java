package org.example.DAO;

import org.example.DBConfig;
import org.example.entities.Chat;
import org.example.entities.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Класс для взаимодействия с чатами в БД.
 * */
public class ChatDAO {

    /**
     * Находит базе данных запись о чате по id.
     *
     * @param chatId id чата.
     *
     * @return сущность чата или null.
     * */
    public static Chat getChat(int chatId) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM public.chat WHERE id=?")) {
            ps.setInt(1, chatId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Chat(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)
                );
            }
            return null;
        }
    }

    /**
     * Проверяет является ли пользовтель владельцем чата.
     *
     * @param user пользователь.
     *
     * @param chatId id чата.
     *
     * @return true - если да, иначе false.
     * */
    public static boolean isUserOwner(User user, int chatId) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM public.chat WHERE id=? AND owner=?")) {
            ps.setInt(1, chatId);
            ps.setInt(2, user.getId());
            return ps.executeQuery().next();
        }
    }

    /**
     * Возвращает список чатов, в которых присутствует пользователь.
     *
     * @param user пользователь.
     *
     * @return Список чатов.
     * */
    public static ArrayList<Chat> getUserChats(User user) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "SELECT public.chat.id, public.chat.name, owner FROM public.chat\n" +
                "\tINNER JOIN public.user_chat on id=chat_id\n" +
                "\tINNER JOIN public.user on user_id=public.user.id\n" +
                "\tWHERE public.user.id=?")) {
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            ArrayList<Chat> chats = new ArrayList<>();
            while(rs.next()){
                chats.add(new Chat(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)
                ));
            }
            return chats;
        }
    }

    /**
     * Удаляет чат по id.
     *
     * @param chatId id чата.
     * */
    public static void deleteById(int chatId) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "DELETE FROM public.chat WHERE id=?")) {
            ps.setInt(1, chatId);
            ps.executeUpdate();
        }
    }

    /**
     * Добавляет новый чат в БД.
     *
     * @param chat сущность чата.
     * */
    public static int create(Chat chat) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "INSERT INTO public.chat (name, owner) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, chat.getName());
            ps.setInt(2, chat.getOwner());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }
        }
        return 0;
    }

    /**
     * Обновляет информацию о чате по его id.
     *
     * @param chat сущность чата.
     * */
    public static void update(Chat chat) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "UPDATE public.chat SET name=?, owner=? WHERE id=?")) {
            ps.setString(1, chat.getName());
            ps.setInt(2, chat.getOwner());
            ps.setInt(3, chat.getId());
            ps.executeUpdate();
        }
    }

}
