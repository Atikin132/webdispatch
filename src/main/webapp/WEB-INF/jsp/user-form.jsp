<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:main-html title="${mode == 'add' ? 'Add User' : 'Edit User'}" pageName="user-form">
    <div class="user-form-container">
        <h2>${mode == 'add' ? 'Add User' : 'Edit User'}</h2>
        <form:form modelAttribute="userFormDTO" class="user-form" method="post"
                   action="${pageContext.request.contextPath}/${mode == 'add' ?
        'useradd.jhtml' : 'useredit.jhtml'}">
            <div class="inputs-container">
                <form:hidden path="id"/>
                <div class="form-group">
                    <label class="form-label" for="login">Login:</label>
                    <div class="input-container">
                        <form:input path="login" id="login" cssClass="form-input"
                                    cssErrorClass="form-input error-input"
                                    autocomplete="off"/>
                        <form:errors path="login" cssClass="error-message"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label" for="password">Password:</label>
                    <div class="input-container">
                        <form:input path="password" id="password" type="password"
                                    cssClass="form-input"
                                    cssErrorClass="form-input error-input" autocomplete="off"/>
                        <form:errors path="password" cssClass="error-message"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label" for="name">Name:</label>
                    <div class="input-container">
                        <form:input path="name" id="name" cssClass="form-input"
                                    cssErrorClass="form-input error-input" autocomplete="off"/>
                        <form:errors path="name" cssClass="error-message"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label" for="birthDate">Birth Date:</label>
                    <div class="input-container">
                        <form:input path="birthDate" id="birthDate" class="form-input" type="date"
                                    max="${maxDate}" autocomplete="off"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label" for="age">Age:</label>
                    <div class="input-container">
                        <form:input path="age" id="age" cssClass="form-input" type="number"
                                    cssErrorClass="form-input error-input" autocomplete="off"/>
                        <form:errors path="age" cssClass="error-message"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label" for="salary">Salary:</label>
                    <div class="input-container">
                        <form:input path="salary" id="salary" cssClass="form-input" type="number"
                                    cssErrorClass="form-input error-input" autocomplete="off"/>
                        <form:errors path="salary" cssClass="error-message"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label">Roles:</label>
                    <div class="input-container">
                        <div class="roles-checkboxes form-input">
                            <form:checkboxes
                                    path="roles"
                                    items="${roles}"
                                    itemValue="id"
                                    itemLabel="name"/>
                        </div>
                        <form:errors path="roles" cssClass="error-message"/>
                    </div>
                </div>
            </div>
            <c:if test="${not empty errorMessage}">
                <div class="error-message">${errorMessage}</div>
            </c:if>
            <button class="button green" type="submit">Save</button>
        </form:form>
        <a class="button blue" href="${pageContext.request.contextPath}/users.jhtml">
            Back
        </a>
    </div>
</t:main-html>