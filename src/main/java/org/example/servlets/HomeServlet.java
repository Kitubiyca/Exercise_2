package org.example.servlets;

import org.example.DAO.ChatDAO;
import org.example.DAO.UserChatDAO;
import org.example.DTO.ChatDTO.ChatDTO;
import org.example.DTO.UserDTO.UserInfoDTO;
import org.example.entities.Chat;
import org.example.entities.User;
import org.example.mappers.ChatMapper;
import org.example.mappers.UserMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Сервлет для обработки запросов с домашней страницы.
 * */

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    /**
     * Принимает method в одном из трёх значений:
     *
     * quit - покинуть чат (id - id чата),
     *
     * delete - удалить чат (только для владельца) (id - id чата),
     *
     * create - создать чат (name - название чата).
     * */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            String method = req.getParameter("method");
            if (method == null) return;
            switch (method) {
                case "quit":
                    if (req.getParameter("id") != null) {
                        try {
                            UserChatDAO.leaveChat(user, Integer.parseInt(req.getParameter("id")));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "delete":
                    if (req.getParameter("id") != null) {
                        try {
                            ChatDAO.deleteById(Integer.parseInt(req.getParameter("id")));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "create":
                    if (req.getParameter("name") != null) {
                        try {
                            int chatId = ChatDAO.create(new Chat(0, req.getParameter("name"), user.getId()));
                            if(chatId != 0){
                                UserChatDAO.joinChat(user, chatId);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/home");
    }

    /**
     * Возвращает пользователю домашнюю страницу.
     * */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null){
            UserInfoDTO userInfoDTO = UserMapper.INSTANCE.toInfoDTO(user);
            req.setAttribute("user", userInfoDTO);
            try {
                ArrayList<ChatDTO> chats = ChatDAO.getUserChats(user).stream().map(ChatMapper.INSTANCE::toDTO).collect(Collectors.toCollection(ArrayList::new));
                req.setAttribute("chats", chats);
            } catch (SQLException e) {
                e.printStackTrace();
                req.setAttribute("chats", new ArrayList<ChatDTO>());
            }
            req.getRequestDispatcher("/WEB-INF/views/home_signed.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/home_unsigned.jsp").forward(req, resp);
        }
    }

}
