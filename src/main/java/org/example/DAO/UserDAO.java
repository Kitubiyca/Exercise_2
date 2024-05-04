package org.example.DAO;

import org.example.DBConfig;
import org.example.entities.Chat;
import org.example.entities.Message;
import org.example.entities.User;

import java.sql.*;
import java.util.ArrayList;


/**
 * Класс для взаимодействия с пользователями в БД.
 * */
public class UserDAO {

    /**
     * Добавляет нового пользователя в БД.
     *
     * @param user пользователь.
     * */
    public static void insert(User user) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement("INSERT INTO public.user(name, password) VALUES(?, ?)")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
        }
    }

    /**
     * Возвращает id пользователя по его имени и паролю.
     *
     * @param user пользователь.
     *
     * @return id пользователя.
     * */
    public static int getId(User user) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT id FROM public.user WHERE name=? AND password=?")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            } else {
                throw new SQLException("User not found");
            }
        }
    }

    /**
     * Возвращает пользователя по его имени.
     *
     * @param name имя пользователя.
     *
     * @return id пользователя.
     * */
    public static int getUserIdByName(String name) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT id FROM public.user WHERE name=?")) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            } else {
                throw new SQLException("User not found");
            }
        }
    }

    /**
     * Возвращает имя пользователя.
     *
     * @param user пользователь.
     *
     * @return имя пользователя.
     * */
    public static String getUserName(User user) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT name FROM public.user WHERE id=?")) {
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            } else {
                throw new SQLException("User not found");
            }
        }
    }

    /**
     * Возвращает владельца чата.
     *
     * @param chat чат.
     *
     * @return пользователь.
     * */
    public static User getOwner(Chat chat) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT public.user.id, public.user.name FROM public.user " +
                "INNER JOIN public.chat ON owner=public.user.id " +
                "WHERE public.chat.id=?")) {
            ps.setInt(1, chat.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new User(
                        rs.getInt(1),
                        rs.getString(2),
                        null,
                        null,
                        null
                );
            } else {
                throw new SQLException("User not found");
            }
        }
    }

    /**
     * Возвращает всех пользователей состоящих в чате.
     *
     * @param chat чат.
     *
     * @return список пользователей.
     * */
    public static ArrayList<User> getUsersInChat(Chat chat) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT id, name FROM public.user " +
                "LEFT JOIN public.user_chat on id=user_id " +
                "WHERE chat_id=?")) {
            ps.setInt(1, chat.getId());
            ResultSet rs = ps.executeQuery();
            ArrayList<User> users = new ArrayList<>();
            while(rs.next()){
                users.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        null,
                        null,
                        null
                ));
            }
            return users;
        }
    }

    /**
     * Возвращает данные о пользователе отправившем сообщение.
     *
     * @param message сообщение.
     *
     * @return сущность пользователя.
     * */
    public static User getMessageUser(Message message) throws SQLException {
        try (Connection c = DBConfig.getConnection(); PreparedStatement ps = c.prepareStatement(
                "SELECT public.user.id, name FROM public.user INNER JOIN public.message on public.user.id=user_id" +
                        " WHERE public.message.id=?")) {
            ps.setInt(1, message.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new User(
                        rs.getInt(1),
                        rs.getString(2),
                        null,
                        null,
                        null
                );
            }
            throw new SQLException("user not found");
        }
    }

}