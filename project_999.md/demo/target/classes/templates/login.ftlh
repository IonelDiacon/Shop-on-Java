<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>

        <h1>BUYSELL | <a href="/login">Войти</a></h1><hr>


    <h4>Авторизация</h4>

    <form action="/login" method="post">
        Эл. почта: <input type="email" name="username"><br><br>
        Пароль: <input type="password" name="password"><br><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="go in">
    </form>

    <a href="/registration">register</a>
</body>
</html>
