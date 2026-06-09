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
          href="${pageContext.request.contextPath}/resources/css/menu.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/header.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/footer.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/user-form.css">
</head>
<body>
<jsp:include page="layouts/header.jsp"/>
<jsp:include page="layouts/menu.jsp"/>
<main>
    <div class="user-form-container">
        <h2>${requestScope.mode == 'add' ? 'Add User' : 'Edit User'}</h2>
        <form class="user-form" method="post"
              action="${pageContext.request.contextPath}/${requestScope.mode == 'add' ?
        'useradd.jhtml' : 'useredit.jhtml?oldLogin='.concat(requestScope.oldLogin)}">
            <div class="inputs-container">
                <div class="form-group">
                    <label class="form-label" for="login">Login:</label>
                    <input id="login" class="form-input" type="text" name="login" required
                           autocomplete="off" value="${requestScope.user.login}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="password">Password:</label>
                    <input id="password" class="form-input" type="password" name="password" required
                           autocomplete="off" value="${requestScope.user.password}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="email">Email:</label>
                    <input id="email" class="form-input" type="email" name="email" required
                           autocomplete="off" value="${requestScope.user.email}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="surname">Surname:</label>
                    <input id="surname" class="form-input" type="text" name="surname" required
                           autocomplete="off" value="${requestScope.user.surname}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="name">Name:</label>
                    <input id="name" class="form-input" type="text" name="name" required
                           autocomplete="off" value="${requestScope.user.name}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="patronymic">Patronymic:</label>
                    <input id="patronymic" class="form-input" type="text" name="patronymic" required
                           autocomplete="off" value="${requestScope.user.patronymic}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="birthday">Birthday:</label>
                    <input id="birthday" class="form-input" max="${requestScope.maxDate}"
                           type="date"
                           name="birthday" required
                           autocomplete="off" value="${requestScope.user.birthday}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="role">Role:</label>
                    <select id="role" class="form-input" name="role">
                        <option value="USER" ${requestScope.user.role == 'USER' ? 'selected'
                                : ''}>USER
                        </option>
                        <option value="ADMIN" ${requestScope.user.role == 'ADMIN' ? 'selected' : ''}>
                            ADMIN
                        </option>
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