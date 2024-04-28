package org.example.servlets;

import org.example.DAO.UserDAO;
import org.example.DTO.UserDTO.UserSignInDTO;
import org.example.entities.User;
import org.example.mappers.UserMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Сервлет для обработки запросов со страницы авторизации.
 * */

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {

    /**
     * Авторизует пользователя:
     *
     * name - имя пользователя,
     *
     * password - пароль.
     * */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            UserSignInDTO dto = new UserSignInDTO(req.getParameter("name"), req.getParameter("password"));
            User user = UserMapper.INSTANCE.toEntity(dto);
            int id = UserDAO.getId(user);
            if(id != 0){
                user.setId(id);
                req.getSession().setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/home");
            } else {
                resp.sendRedirect(req.getContextPath() + "/signin");
            }
        } catch (Exception e){
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/signin");
        }
    }

    /**
     * Возвращает пользователю страницу авторизации.
     * */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("user", null);
        req.getRequestDispatcher("/WEB-INF/views/sign_in.jsp").forward(req, resp);
    }

}
