<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:main-html title="${requestScope.mode == 'add' ? 'Add User' : 'Edit User'}" pageName="user-form">
    <div class="user-form-container">
        <h2>${requestScope.mode == 'add' ? 'Add User' : 'Edit User'}</h2>
        <form class="user-form" method="post"
              action="${pageContext.request.contextPath}/${requestScope.mode == 'add' ?
        'useradd.jhtml' : 'useredit.jhtml?id='.concat(requestScope.user.id)}">
            <div class="inputs-container">
                <input type="hidden" id="id" name="id" value="${requestScope.user.id}"/>
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
                    <label class="form-label" for="name">Name:</label>
                    <input id="name" class="form-input" type="text" name="name" required
                           autocomplete="off" value="${requestScope.user.name}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="birthday">Birthday:</label>
                    <input id="birthday" class="form-input" max="${requestScope.maxDate}"
                           type="date"
                           name="birthday"
                           autocomplete="off" value="${requestScope.user.birthday}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="age">Age:</label>
                    <input id="age" class="form-input" type="number" name="age" required min="19"
                           autocomplete="off" value="${requestScope.user.age}">
                </div>
                <div class="form-group">
                    <label class="form-label" for="salary">Salary:</label>
                    <input id="salary" class="form-input" type="text" name="salary" required
                           autocomplete="off" value="${requestScope.user.salary}">
                </div>


                <div class="form-group">
                    <label class="form-label">Roles:</label>
                    <div class="roles-checkboxes form-input">
                        <c:forEach var="role" items="${requestScope.roles}">
                            <label>
                                <input type="checkbox"
                                       name="roles"
                                       value="${role.id}"
                                    ${requestScope.user.hasRole(role.id) ? 'checked' : ''}>
                                    ${role.name}
                            </label>
                        </c:forEach>
                    </div>
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
</t:main-html>