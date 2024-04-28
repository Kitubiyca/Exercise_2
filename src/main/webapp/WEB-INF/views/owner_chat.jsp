<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <%@ page import="org.example.DTO.UserDTO.UserInfoDTO, org.example.DTO.ChatDTO.ChatDTO, org.example.DTO.MessageDTO.MessageDTO, java.util.ArrayList" %>
    <title><%= ((ChatDTO) request.getAttribute("chat")).getName() %></title>
  </head>
  <body>
    <b>Изменить название чата</b>
    <form method="post">
        <input type="hidden" name="method" value="rename"/>
        <input type="text" name="name" id="name" required maxlength=20 placeholder="Название"/><br><br>
        <input type="submit" value="Изменить"/>
    </form><br><br>
    <b>Пригласить пользователя</b>
    <form method="post">
        <input type="hidden" name="method" value="invite"/>
        <input type="text" name="username" id="username" required maxlength=20 placeholder="Пользователь"/><br><br>
        <input type="submit" value="Пригласить"/>
    </form><br><br>
    <b>Отправить сообщение</b>
    <form method="post">
        <input type="hidden" name="method" value="send"/>
        <input type="textarea" name="message" id="message" required maxlength=100 placeholder="Сообщение"/><br><br>
        <input type="submit" value="Отправить"/>
    </form><br><br>
    <%
            if (request.getAttribute("users") != null && request.getAttribute("messages") != null){
                ArrayList<UserInfoDTO> users = ((ArrayList<UserInfoDTO>) request.getAttribute("users"));
                ArrayList<MessageDTO> messages = ((ArrayList<MessageDTO>) request.getAttribute("messages"));
                for (MessageDTO msg : messages){
                    String name = users.stream().filter(user -> user.getId() == msg.getUserId()).findFirst().get().getName();
                    out.println("<p><b>" + name + "</b></p>");
                    out.println("<p>" + msg.getText() + "</p>");
                    out.println("<br><br>");
                }
            }
        %>
  </body>
</html>