<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <%@ page import="org.example.DTO.UserDTO.UserInfoDTO, org.example.DTO.ChatDTO.ChatDTO, java.util.ArrayList" %>
    <title>Главная</title>
  </head>
  <body>
    <p> Привет, <%= ((UserInfoDTO) request.getAttribute("user")).getName() %>! </p><br>
    <p><b>Создать новый чат:</b></p>
    <form method="post">
        <input type="hidden" name="method" value="create"/>
        <input type="text" name="name" id="name" required maxlength=20 placeholder="Название"/><br><br>
        <input type="submit" value="Создать"/>
    </form><br><br>
    <p><b>Список чатов:</b></p>
    <%
        if (request.getAttribute("chats") != null){
            ArrayList<ChatDTO> chats = ((ArrayList<ChatDTO>) request.getAttribute("chats"));
            for (ChatDTO dto : chats){
                out.println("<p><a href=\"/chat?c=" + dto.getId() + "\">" + dto.getName() + "</a></p>");
                if(((UserInfoDTO) request.getAttribute("user")).getId() != dto.getOwner()){
                    out.println("<form method=\"post\">" +
                                    "<input type=\"hidden\" name=\"method\" value=\"quit\" />" +
                                    "<input type=\"hidden\" name=\"id\" value=\"" + dto.getId() + "\" />" +
                                    "<input type=\"submit\" value=\"Покинуть\" />" +
                                    "</form><br>");
                } else {
                    out.println("<form method=\"post\">" +
                                    "<input type=\"hidden\" name=\"method\" value=\"delete\" />" +
                                    "<input type=\"hidden\" name=\"id\" value=\"" + dto.getId() + "\" />" +
                                    "<input type=\"submit\" value=\"Удалить\" />" +
                                    "</form><br>");
                }
            }
        }
    %>
  </body>
</html>