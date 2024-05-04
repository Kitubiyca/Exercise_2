package org.example.servlets;

import org.example.DAO.ChatDAO;
import org.example.DAO.MessageDAO;
import org.example.DAO.UserChatDAO;
import org.example.DAO.UserDAO;
import org.example.DTO.ChatDTO.ChatDTO;
import org.example.DTO.MessageDTO.MessageDTO;
import org.example.DTO.UserDTO.UserInfoDTO;
import org.example.entities.Chat;
import org.example.entities.User;
import org.example.mappers.ChatMapper;
import org.example.mappers.MessageMapper;
import org.example.mappers.UserMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Сервлет для обработки запросов со страницы чата.
 * */

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    /**
     * Принимает method в одном из трёх значений (c - id чата):
     *
     * rename - переименовать чат (name - новое название чата),
     *
     * invite - пригласить пользователя (username - имя пользователя),
     *
     * send - отправить сообщение (message - текст сообщения).
     * */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            try {
                String method = req.getParameter("method");
                Chat chat = new Chat(Integer.parseInt(req.getParameter("c")), req.getParameter("name"), ChatDAO.getChat(Integer.parseInt(req.getParameter("c"))).getOwner(), null, null);
                if (method == null) return;
                switch (method) {
                    case "rename":
                        if (chat.getName() != null && chat.getOwner().getId() == user.getId() && chat.getName().length() <= 20) {
                            ChatDAO.update(chat);
                        }
                        break;
                    case "invite":
                        if (req.getParameter("username") != null && chat.getOwner().getId() == user.getId() && req.getParameter("username").length() <= 20) {
                            User addUser = new User(UserDAO.getUserIdByName(req.getParameter("username")), req.getParameter("username"), null, null, null);
                            UserChatDAO.joinChat(addUser, chat.getId());
                        }
                        break;
                    case "send":
                        if (req.getParameter("message") != null && UserChatDAO.isUserJoined(user, chat.getId()) && req.getParameter("message").length() <= 100) {
                            MessageDAO.send(chat, user, req.getParameter("message"));
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect(req.getContextPath() + "/chat?c=" + req.getParameter("c"));
    }

    /**
     * Возвращает пользователя страницу чата или владельца чата (c - id чата).
     * */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null && req.getParameter("c") != null) {
            try {
                int chatId = Integer.parseInt(req.getParameter("c"));
                if (UserChatDAO.isUserJoined(user, chatId)) {
                    Chat chat = ChatDAO.getChat(chatId);
                    ChatDTO chatDTO = ChatMapper.INSTANCE.toDTO(chat);
                    req.setAttribute("chat", chatDTO);
                    ArrayList<UserInfoDTO> users = chat.getUsers().stream().map(UserMapper.INSTANCE::toInfoDTO).collect(Collectors.toCollection(ArrayList::new));
                    req.setAttribute("users", users);
                    ArrayList<MessageDTO> messages = chat.getMessages().stream().map(MessageMapper.INSTANCE::toDTO).collect(Collectors.toCollection(ArrayList::new));
                    Collections.reverse(messages);
                    req.setAttribute("messages", messages);
                    if (ChatDAO.isUserOwner(user, chatId)) {
                        req.getRequestDispatcher("/WEB-INF/views/owner_chat.jsp").forward(req, resp);
                    } else {
                        req.getRequestDispatcher("/WEB-INF/views/chat.jsp").forward(req, resp);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
