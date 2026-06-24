<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:main-html title="${requestScope.mode == 'add' ? 'Add User' : 'Edit User'}" pageName="user-form">
    <div class="user-form-container">
        <h2>${requestScope.mode == 'add' ? 'Add User' : 'Edit User'}</h2>
        <form:form modelAttribute="user" class="user-form" method="post"
                   action="${pageContext.request.contextPath}/${requestScope.mode == 'add' ?
        'useradd.jhtml' : 'useredit.jhtml'}">
            <div class="inputs-container">
                <form:hidden path="id"/>
                <div class="form-group">
                    <label class="form-label" for="login">Login:</label>
                    <form:input path="login" id="login" class="form-input" required="true"
                                autocomplete="off"/>
                </div>
                <div class="form-group">
                    <label class="form-label" for="password">Password:</label>
                    <form:input path="password" type="password" id="password" class="form-input"
                                required="true"
                                autocomplete="off"/>
                </div>
                <div class="form-group">
                    <label class="form-label" for="name">Name:</label>
                    <form:input path="name" id="name" class="form-input" required="true"
                                autocomplete="off"/>
                </div>
                <div class="form-group">
                    <label class="form-label" for="birthDate">Birth Date:</label>
                    <form:input path="birthDate" id="birthDate" class="form-input" type="date"
                                max="${requestScope.maxDate}" autocomplete="off"/>
                </div>
                <div class="form-group">
                    <label class="form-label" for="age">Age:</label>
                    <form:input path="age" id="age" class="form-input" type="number"
                                required="true" min="19" autocomplete="off"/>
                </div>
                <div class="form-group">
                    <label class="form-label" for="salary">Salary:</label>
                    <form:input path="salary" id="salary" class="form-input" required="true"
                                autocomplete="off"/>
                </div>
                <div class="form-group">
                    <label class="form-label">Roles:</label>
                    <div class="roles-checkboxes form-input">
                        <form:checkboxes
                                path="roles"
                                items="${requestScope.roles}"
                                itemValue="id"
                                itemLabel="name"/>
                    </div>
                </div>
            </div>
            <c:if test="${not empty requestScope.errorMessage}">
                <div style="color:red">
                        ${requestScope.errorMessage}
                </div>
            </c:if>
            <button class="button green" type="submit">Save</button>
        </form:form>
        <a class="button blue" href="${pageContext.request.contextPath}/users.jhtml">
            Back
        </a>
    </div>
</t:main-html>