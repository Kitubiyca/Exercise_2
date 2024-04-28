package org.example.servlets;

import org.example.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

class SignInServletTest {

    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;
    private static RequestDispatcher dispatcher;
    private static SignInServlet servlet;

    @BeforeEach
    void beforeEach() {
        servlet = new SignInServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/WEB-INF/views/sign_in.jsp")).thenReturn(dispatcher);
    }

    @Test
    void doPostValid() throws Exception {
        when(request.getParameter("name")).thenReturn("oldUser");
        when(request.getParameter("password")).thenReturn("0000");
        servlet.doPost(request, response);
        verify(session, times(1)).setAttribute(eq("user"), Mockito.any(User.class));
    }

    @Test()
    void doPostInvalid() throws Exception {
        when(request.getParameter("name")).thenReturn("oldUser");
        when(request.getParameter("password")).thenReturn("4444");
        servlet.doPost(request, response);
        verify(session, times(0)).setAttribute(eq("user"), Mockito.any(User.class));
    }

    @Test
    void doGet() throws Exception{
        servlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/sign_in.jsp");
    }

}