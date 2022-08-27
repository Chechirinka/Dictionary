<%--
  Created by IntelliJ IDEA.
  User: ichernyaeva
  Date: 06.07.2022
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns: lang="ru">

<head>
    <meta charset="UTF-8">
    <title>Start</title>
</head>

<body>

<p>Select languages:</p>

<form action="/menu-controller/action-menu" method="get">

    <div>
        <select name="dictionaryId">
            <option value="1">English</option>
            <option value="2">Digital</option>
        </select>
        <select name="action" hidden="hidden">
            <option value="menu">English</option>
        </select>
    </div>
    <br/>
    <br/>
    <br/>

    <div>
        <button name="" value="">Send</button>
    </div>

</form>
</body>
</html>