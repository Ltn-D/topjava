<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<nav class="navbar navbar-dark bg-dark py-0">
    <div class="container">
        <a href="meals" class="navbar-brand"><img src="resources/images/icon-meal.png"> <spring:message code="app.title"/></a>
        <sec:authorize access="isAuthenticated()">
            <form:form class="form-inline my-2" action="logout" method="post">
                <sec:authorize access="hasRole('ADMIN')">
                    <a class="btn btn-info mr-1" href="users"><spring:message code="user.title"/></a>
                </sec:authorize>
                <a class="btn btn-info mr-1" href="profile">${userTo.name} <spring:message code="app.profile"/></a>
                <button class="btn btn-primary my-1" type="submit">
                    <span class="fa fa-sign-out"></span>
                </button>
            </form:form>
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
            <form:form class="form-inline my-2" id="login_form" action="spring_security_check" method="post">
                <input class="form-control mr-1" type="text" placeholder="Email" name="username">
                <input class="form-control mr-1" type="password" placeholder="Password" name="password">
                <button class="btn btn-success" type="submit">
                    <span class="fa fa-sign-in"></span>
                </button>
            </form:form>
        </sec:authorize>

        <div class="dropdown ml-2">
            <button class="btn btn-secondary dropdown-toggle" type="button" id="languageDropdown" data-toggle="dropdown">
                ${pageContext.response.locale.language}
            </button>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="languageDropdown">
                <spring:url value="${requestScope['javax.servlet.forward.request_uri']}" var="ruUrl" context="/">
                    <spring:param name="lang" value="ru"/>
                </spring:url>
                <a class="dropdown-item ${pageContext.response.locale.language == 'ru' ? 'active' : ''}"
                   href="${ruUrl}">Русский</a>

                <spring:url value="${requestScope['javax.servlet.forward.request_uri']}" var="enUrl" context="/">
                    <spring:param name="lang" value="en"/>
                </spring:url>
                <a class="dropdown-item ${pageContext.response.locale.language == 'en' ? 'active' : ''}"
                   href="${enUrl}">English</a>
            </div>
        </div>
    </div>
</nav>
