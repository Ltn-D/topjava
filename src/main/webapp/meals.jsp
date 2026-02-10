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
        <th colspan="2">Operation</th>
    </tr>
<%--    <jsp:useBean id="allMeals" scope="request" type="java.util.List"/>--%>
    <c:forEach var="mealTo" items="${allMeals}">
    <tr class="${mealTo.excess?'excessTrue':'excessFalse'}">

            <td>${(mealTo.dateTime).format(FORMATTER)}</td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td><a href="${pageContext.request.contextPath}/meals?action=edit&id=${mealTo.id}">Update</a></td>
            <td><a href="${pageContext.request.contextPath}/meals?action=delete&id=${mealTo.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
