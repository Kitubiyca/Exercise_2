<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Регистрация</title>
  </head>
  <body>
    <div>
        <form method="post">
            <label for="name">Введите имя: </label>
            <input type="text" name="name" id="name" required maxlength=20/><br><br>

            <label for="password1">Введите пароль: </label>
            <input type="password" name="password1" id="password1" required maxlength=20/><br><br>

            <label for="password2">Повторите пароль: </label>
            <input type="password" name="password2" id="password2" required maxlength=20/><br><br>

            <input type="submit" value="Зарегистрироваться"/>
        </form>
    </div>
  </body>
</html>