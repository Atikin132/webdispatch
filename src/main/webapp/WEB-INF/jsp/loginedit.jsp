<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:main-html title="Change Password" pageName="loginedit">
    <div class="change-password-form-container">
        <h2>Change Password</h2>
        <form:form class="change-password-form" method="post"
                   action="${pageContext.request.contextPath}/loginedit.jhtml"
                   modelAttribute="passwordChangeFormDTO">
            <div class="password-field">
                <label for="oldPassword">
                    Old password
                </label>
                <form:password path="oldPassword" id="oldPassword" class="password-input"
                               autocomplete="off"/>
            </div>
            <div class="password-field">
                <label for="newPassword">
                    New password
                </label>
                <form:password path="newPassword" id="newPassword" class="password-input"
                               autocomplete="off"/>
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
        </form:form>
    </div>
</t:main-html>