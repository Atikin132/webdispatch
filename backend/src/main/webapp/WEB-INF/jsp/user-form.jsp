<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="addUserTitle" var="addUserTitle"/>
<spring:message code="editUserTitle" var="editUserTitle"/>

<t:main-html title="${mode == 'add' ? addUserTitle : editUserTitle}" pageName="user-form">
    <div class="user-form-container">
        <h2>${mode == 'add' ? addUserTitle : editUserTitle}</h2>
        <form:form modelAttribute="userFormDTO" class="user-form" method="post"
                   action="${pageContext.request.contextPath}/${mode == 'add' ?
        'useradd.jhtml' : 'useredit.jhtml'}">
            <div class="inputs-container">
                <form:hidden path="id"/>
                <div class="form-group">
                    <label class="form-label" for="login">
                        <spring:message code="userFormLogin"/>
                    </label>
                    <div class="input-container">
                        <form:input path="login" id="login" cssClass="form-input"
                                    cssErrorClass="form-input error-input"
                                    autocomplete="off"/>
                        <form:errors path="login" cssClass="error-message"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label" for="password">
                        <spring:message code="userFormPassword"/>
                    </label>
                    <div class="input-container">
                        <form:input path="password" id="password" type="password"
                                    cssClass="form-input"
                                    cssErrorClass="form-input error-input" autocomplete="off"/>
                        <form:errors path="password" cssClass="error-message"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label" for="name">
                        <spring:message code="userFormName"/>
                    </label>
                    <div class="input-container">
                        <form:input path="name" id="name" cssClass="form-input"
                                    cssErrorClass="form-input error-input" autocomplete="off"/>
                        <form:errors path="name" cssClass="error-message"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label" for="birthDate">
                        <spring:message code="userFormBirthDate"/>
                    </label>
                    <div class="input-container">
                        <form:input path="birthDate" id="birthDate" class="form-input" type="date"
                                    max="${maxDate}" autocomplete="off"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label" for="age">
                        <spring:message code="userFormAge"/>
                    </label>
                    <div class="input-container">
                        <form:input path="age" id="age" cssClass="form-input" type="number"
                                    cssErrorClass="form-input error-input" autocomplete="off"/>
                        <form:errors path="age" cssClass="error-message"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label" for="salary">
                        <spring:message code="userFormSalary"/>
                    </label>
                    <div class="input-container">
                        <form:input path="salary" id="salary" cssClass="form-input" type="number"
                                    cssErrorClass="form-input error-input" autocomplete="off"/>
                        <form:errors path="salary" cssClass="error-message"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="form-label">
                        <spring:message code="userFormRoles"/>
                    </label>
                    <div class="input-container">
                        <div class="roles-checkboxes form-input">
                            <form:checkboxes
                                    path="roles"
                                    items="${roles}"
                                    itemValue="id"
                                    itemLabel="displayName"/>
                        </div>
                        <form:errors path="roles" cssClass="error-message"/>
                    </div>
                </div>
            </div>
            <c:if test="${not empty errorMessage}">
                <div class="error-message">${errorMessage}</div>
            </c:if>
            <button class="button green" type="submit">
                <spring:message code="userFormSaveBtn"/>
            </button>
        </form:form>
        <a class="button blue" href="${pageContext.request.contextPath}/users.jhtml">
            <spring:message code="userFormBackBtn"/>
        </a>
    </div>
</t:main-html>