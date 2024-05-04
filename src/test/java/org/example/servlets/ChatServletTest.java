package org.example.servlets;

import org.example.DAO.ChatDAO;
import org.example.DAO.MessageDAO;
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
import static org.mockito.Mockito.times;

class ChatServletTest {

    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;
    private static RequestDispatcher dispatcher;
    private static ChatServlet servlet;

    @BeforeEach
    void beforeEach() {
        servlet = new ChatServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/views/chat.jsp")).thenReturn(dispatcher);
        when(request.getRequestDispatcher("/WEB-INF/views/owner_chat.jsp")).thenReturn(dispatcher);
    }

    @Test()
    void doPostRename() throws Exception {
        User user = new User(1, "oldUser", "0000", null, null);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("method")).thenReturn("rename");
        when(request.getParameter("c")).thenReturn("3");
        when(request.getParameter("name")).thenReturn("newNameForChat");
        servlet.doPost(request, response);
        Assertions.assertEquals("newNameForChat", ChatDAO.getChat(3).getName());
    }

    @Test
    void doPostInvite() throws Exception {
        User user = new User(1, "oldUser", "0000", null, null);
        User anotherUser = new User(3, "anotherUser", "2222", null, null);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("method")).thenReturn("invite");
        when(request.getParameter("c")).thenReturn("3");
        when(request.getParameter("username")).thenReturn("anotherUser");
        servlet.doPost(request, response);
        Assertions.assertTrue(UserChatDAO.isUserJoined(anotherUser, 3));
    }

    @Test
    void doPostSend() throws Exception {
        when(session.getAttribute("user")).thenReturn(new User(2, "veryOldUser", "1111", null, null));
        when(request.getParameter("method")).thenReturn("send");
        when(request.getParameter("c")).thenReturn("3");
        when(request.getParameter("message")).thenReturn("bye");
        servlet.doPost(request, response);
        Assertions.assertEquals(4, MessageDAO.getChatMessages(3).size());
    }

    @Test
    void doGetAuthorizedMember() throws Exception{
        when(request.getSession().getAttribute("user")).thenReturn(new User(2, "veryOldUser", "1111", null, null));
        when(request.getParameter("c")).thenReturn("3");
        servlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/chat.jsp");
    }

    @Test
    void doGetAuthorizedOwner() throws Exception{
        when(request.getSession().getAttribute("user")).thenReturn(new User(1, "oldUser", "0000", null, null));
        when(request.getParameter("c")).thenReturn("3");
        servlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/owner_chat.jsp");
    }

    @Test
    void doGetNonExistent() throws Exception{
        when(request.getSession().getAttribute("user")).thenReturn(new User(1, "oldUser", "0000", null, null));
        when(request.getParameter("c")).thenReturn("999");
        servlet.doGet(request, response);
        verify(request, times(0)).getRequestDispatcher("/WEB-INF/views/owner_chat.jsp");
        verify(request, times(0)).getRequestDispatcher("/WEB-INF/views/chat.jsp");
        verify(response, times(0)).sendRedirect(null + "/home");
    }

    @Test
    void doGetUnauthorized() throws Exception{
        servlet.doGet(request, response);
        verify(response, times(1)).sendRedirect(null + "/home");
    }

}