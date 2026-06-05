<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/normalize.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/header.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/footer.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/useradd.css">
</head>
<body>
<jsp:include page="layouts/header.jsp"/>

<main>
    <div class="add-user-container">
        <h2>Add User</h2>
        <form class="add-user-form" method="post"
              action="${pageContext.request.contextPath}/useradd.jhtml">
            <div class="inputs-container">
                <div class="form-group">
                    <label class="form-label" for="login">Login:</label>
                    <input id="login" class="form-input" type="text" name="login" required
                           autocomplete="off" value="${requestScope.login}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="password">Password:</label>
                    <input id="password" class="form-input" type="password" name="password" required
                           autocomplete="off" value="${requestScope.password}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="email">Email:</label>
                    <input id="email" class="form-input" type="email" name="email" required
                           autocomplete="off" value="${requestScope.email}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="surname">Surname:</label>
                    <input id="surname" class="form-input" type="text" name="surname" required
                           autocomplete="off" value="${requestScope.surname}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="name">Name:</label>
                    <input id="name" class="form-input" type="text" name="name" required
                           autocomplete="off" value="${requestScope.name}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="patronymic">Patronymic:</label>
                    <input id="patronymic" class="form-input" type="text" name="patronymic" required
                           autocomplete="off" value="${requestScope.patronymic}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="birthday">Birthday:</label>
                    <input id="birthday" class="form-input" type="date" name="birthday" required
                           autocomplete="off" value="${requestScope.birthday}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="role">Role:</label>
                    <select id="role" class="form-input" name="role">
                        <option value="USER">USER</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                </div>
            </div>
            <c:if test="${not empty requestScope.errorMessage}">
                <div style="color:red">
                        ${requestScope.errorMessage}
                </div>
            </c:if>
            <button class="button green" type="submit">
                Save
            </button>
        </form>
        <a class="button blue" href="${pageContext.request.contextPath}/users.jhtml">
            Back
        </a>
    </div>

</main>
<%@ include file="layouts/footer.jsp" %>

</body>
</html>