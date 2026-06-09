<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>


    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/normalize.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/login.css">
</head>
<body>

<form class="login-form" method="post" action="${pageContext.request.contextPath}/login.jhtml">
    <div class="auth-field">
        <label  for="login">
            Login
        </label>
        <input class="auth-input" id="login" type="text" name="login" required autocomplete="off">
    </div>
    <div class="auth-field">
        <label for="password">
            Password
        </label>
        <input class="auth-input" id="password" type="password" name="password" required autocomplete="off">
    </div>
    <c:if test="${not empty requestScope.errorMessage}">
        <span style="color:red">
                ${requestScope.errorMessage}
        </span>
    </c:if>
    <button class="button blue" type="submit">Login</button>
</form>


</body>
</html>