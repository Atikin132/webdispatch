<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="loginTitle"/></title>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/normalize.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/login.css">

    <meta charset="UTF-8">
</head>
<body>
<section class="login-section">
    <form:form class="login-form" method="post"
               action="${pageContext.request.contextPath}/login.jhtml"
               modelAttribute="loginFormDTO">
        <div class="auth-field">
            <label for="login"><spring:message code="login"/></label>
            <form:input path="login" id="login" cssClass="auth-input"
                        cssErrorClass="auth-input error-input"
                        autocomplete="off"/>
            <form:errors path="login" cssClass="error-message"/>
        </div>
        <div class="auth-field">
            <label for="password"><spring:message code="password"/></label>
            <form:password path="password" id="password" cssClass="auth-input"
                           cssErrorClass="auth-input error-input"
                           autocomplete="off"/>
            <form:errors path="password" cssClass="error-message"/>
        </div>
        <c:if test="${not empty errorMessage}">
            <span class="error-message">${errorMessage}</span>
        </c:if>
        <button class="button blue" type="submit"><spring:message code="loginBtn"/></button>
    </form:form>

    <t:change-lang-btn/>
</section>
</body>
</html>