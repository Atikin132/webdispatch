<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login Edit</title>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/normalize.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/menu.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/loginedit.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/header.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/footer.css">

</head>
<body>
<jsp:include page="layouts/header.jsp"/>
<jsp:include page="layouts/menu.jsp"/>
<main>
    <div class="change-password-form-container">
        <h2>Change Password</h2>
        <form class="change-password-form" method="post"
              action="${pageContext.request.contextPath}/loginedit.jhtml">
            <div class="password-field">
                <label for="oldPassword">
                    Old password
                </label>
                <input class="password-input" id="oldPassword" type="password" name="oldPassword" required
                       autocomplete="off">
            </div>
            <div class="password-field">
                <label for="newPassword">
                    New password
                </label>
                <input class="password-input" id="newPassword" type="password" name="newPassword"
                       required
                       autocomplete="off">
            </div>
            <c:if test="${not empty requestScope.errorMessage}">
                <span style="color:red">${requestScope.errorMessage}</span>
            </c:if>
            <c:if test="${not empty requestScope.successMessage}">
                <span style="color:green">${requestScope.successMessage}</span>
            </c:if>
            <button class="change-password-btn button green" type="submit">
                Change password
            </button>
        </form>
    </div>

</main>
<%@ include file="layouts/footer.jsp" %>

</body>
</html>