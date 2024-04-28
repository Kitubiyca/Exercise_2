<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Вход</title>
  </head>
  <body>
    <div>
        <form method="post">
            <label for="name">Введите имя: </label>
            <input type="text" name="name" id="name" required maxlength=20/><br><br>

            <label for="password">Введите пароль: </label>
            <input type="password" name="password" id="password" required maxlength=20/><br><br>

            <input type="submit" value="Войти"/>
        </form>
    </div>
  </body>
</html>