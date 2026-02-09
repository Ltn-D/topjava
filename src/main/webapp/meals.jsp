<%--
  Created by IntelliJ IDEA.
  User: 111
  Date: 06.02.2026
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<style>
    .excessTrue {
        color: red;
    }
    .excessFalse {
        color: darkgreen;
    }
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<br>
<h3><a href="meals?action=create">Add Meal</a></h3>
<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Operation</th>
    </tr>
    <jsp:useBean id="allMeals" scope="request" type="java.util.List"/>
    <c:forEach var="mealTo" items="${allMeals}">
        <tr>
            <td><span class=${mealTo.excess?'excessTrue':'excessFalse'}>${mealTo.dateTime}</td>
            <td><span class=${mealTo.excess?'excessTrue':'excessFalse'}>${mealTo.description}</td>
            <td><span class=${mealTo.excess?'excessTrue':'excessFalse'}>${mealTo.calories}</td>
            <td><a href="${pageContext.request.contextPath}/meals?action=edit&mealId=${mealTo.id}">Update</a></td>
            <td><a href="${pageContext.request.contextPath}/meals?action=delete&mealId=${mealTo.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
