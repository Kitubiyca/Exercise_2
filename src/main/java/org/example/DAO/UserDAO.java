package org.example.DAO;

import org.example.DBConfig;
import org.example.entities.User;

import java.sql.*;


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

}