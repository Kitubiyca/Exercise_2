package org.example.servlets;

import org.example.DAO.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class SignUpServletTest {

    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;
    private static RequestDispatcher dispatcher;
    private static SignUpServlet servlet;

    @BeforeEach
    void beforeEach() {
        servlet = new SignUpServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/views/sign_up.jsp")).thenReturn(dispatcher);
    }

    @Test
    void doPostValid() throws Exception {
        when(request.getParameter("name")).thenReturn("firstUser");
        when(request.getParameter("password1")).thenReturn("1234");
        when(request.getParameter("password2")).thenReturn("1234");
        servlet.doPost(request, response);
        Assertions.assertEquals(4, UserDAO.getUserIdByName("firstUser"));
    }

    @Test()
    void doPostInvalid() throws Exception {
        when(request.getParameter("name")).thenReturn("secondUser");
        when(request.getParameter("password1")).thenReturn("1234");
        when(request.getParameter("password2")).thenReturn("4321");
        servlet.doPost(request, response);
        SQLException exception = Assertions.assertThrows(SQLException.class, () -> UserDAO.getUserIdByName("secondUser"));
        Assertions.assertEquals("User not found", exception.getMessage());
    }

    @Test
    void doGet() throws Exception{
        servlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/sign_up.jsp");
    }

}