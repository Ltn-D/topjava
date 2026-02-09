<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h3>Please enter info for Meal:</h3>
<br>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form action="" method="post">
    DateTime : <input type="datetime-local" name="dateTime" value="${meal.dateTime}">
    <br><br>
    Description : <input type="text" name="description" value="${meal.description}">
    <br><br>
    Calories : <input type="text" name="calories" value="${meal.calories}" >
    <input type="hidden" name="id" value="${meal.id}">
    <br><br>
    <input type="submit" value="Save">
</form>
<hr>
<button onclick="window.history.back()" type="button">Cancel</button>
</body>
</html>
