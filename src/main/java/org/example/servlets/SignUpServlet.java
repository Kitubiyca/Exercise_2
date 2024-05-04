package org.example.servlets;

import org.example.DAO.UserDAO;
import org.example.DTO.UserDTO.UserSignUpDTO;
import org.example.entities.User;
import org.example.mappers.UserMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для обработки запросов со страницы регистрации.
 * */

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    /**
     * Авторизует пользователя:
     *
     * name - имя пользователя,
     *
     * password1 - пароль,
     *
     * password1 - повторение пароля.
     * */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            UserSignUpDTO dto = new UserSignUpDTO(req.getParameter("name"), req.getParameter("password1"), req.getParameter("password2"));
            User user = UserMapper.INSTANCE.toEntity(dto);
            UserDAO.insert(user);
            resp.sendRedirect(req.getContextPath() + "/signin");
        } catch (Exception e){
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/signup");
        }
    }

    /**
     * Возвращает пользователю страницу регистрации.
     * */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("user", null);
        req.getRequestDispatcher("/WEB-INF/views/sign_up.jsp").forward(req, resp);
    }

}
