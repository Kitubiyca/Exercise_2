package org.example.servlets;

import org.example.DAO.ChatDAO;
import org.example.DAO.UserChatDAO;
import org.example.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

class HomeServletTest {

    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;
    private static RequestDispatcher dispatcher;
    private static HomeServlet servlet;

    @BeforeEach
    void beforeEach() {
        servlet = new HomeServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/views/home_signed.jsp")).thenReturn(dispatcher);
        when(request.getRequestDispatcher("/WEB-INF/views/home_unsigned.jsp")).thenReturn(dispatcher);
    }

    @Test()
    void doPostQuit() throws Exception {
        User user = new User(1, "oldUser", "0000");
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("method")).thenReturn("quit");
        when(request.getParameter("id")).thenReturn("2");
        servlet.doPost(request, response);
        Assertions.assertFalse(UserChatDAO.isUserJoined(user, 2));
    }

    @Test
    void doPostDelete() throws Exception {
        User user = new User(1, "oldUser", "0000");
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("method")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("1");
        servlet.doPost(request, response);
        Assertions.assertNull(ChatDAO.getChat(1));
    }

    @Test
    void doPostCreate() throws Exception {
        User user = new User(1, "oldUser", "0000");
        when(session.getAttribute("user")).thenReturn(new User(1, "oldUser", "0000"));
        when(request.getParameter("method")).thenReturn("create");
        when(request.getParameter("name")).thenReturn("veryNewChat");
        servlet.doPost(request, response);
        Assertions.assertTrue(UserChatDAO.isUserJoined(user, 4));
    }

    @Test
    void doGetAuthorized() throws Exception{
        when(request.getSession().getAttribute("user")).thenReturn(new User(1, "oldUser", "0000"));
        servlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/home_signed.jsp");
    }

    @Test
    void doGetUnauthorized() throws Exception{
        servlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/home_unsigned.jsp");
    }

}