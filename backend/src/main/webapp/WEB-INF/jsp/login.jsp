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
    <form class="login-form" action="${pageContext.request.contextPath}/login.jhtml" method="post">
        <div class="auth-field">
            <label for="login"><spring:message code="login"/></label>
            <input type="text" name="login" id="login" class="auth-input" autocomplete="off" required/>
        </div>
        <div class="auth-field">
            <label for="password"><spring:message code="password"/></label>
            <input type="password" id="password" name="password" class="auth-input"
                   autocomplete="off" required/>
        </div>
        <c:if test="${not empty errorMessage}">
            <span class="error-message">${errorMessage}</span>
        </c:if>
        <button class="button blue" type="submit"><spring:message code="loginBtn"/></button>
    </form>

    <t:change-lang-btn/>
</section>
</body>
</html>