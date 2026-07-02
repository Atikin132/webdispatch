<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<spring:message code="loginEdit" var="loginEditTitle"/>
<t:main-html title="${loginEditTitle}" pageName="loginedit">
    <div class="change-password-form-container">
        <h2><spring:message code="loginEdit"/></h2>
        <form:form class="change-password-form" method="post"
                   action="${pageContext.request.contextPath}/loginedit.jhtml"
                   modelAttribute="passwordChangeFormDTO">
            <div class="password-field">
                <label for="oldPassword"><spring:message code="oldPassword"/></label>
                <form:password path="oldPassword" id="oldPassword" cssClass="password-input"
                               cssErrorClass="password-input error-input"
                               autocomplete="off"/>
                <form:errors path="oldPassword" cssClass="error-message"/>
            </div>
            <div class="password-field">
                <label for="newPassword"><spring:message code="newPassword"/></label>
                <form:password path="newPassword" id="newPassword" cssClass="password-input"
                               cssErrorClass="password-input error-input"
                               autocomplete="off"/>
                <form:errors path="newPassword" cssClass="error-message"/>
            </div>
            <c:if test="${not empty errorMessage}">
                <span class="error-message">${errorMessage}</span>
            </c:if>
            <c:if test="${not empty successMessage}">
                <span class="success-message">${successMessage}</span>
            </c:if>
            <button class="change-password-btn button green" type="submit">
                <spring:message code="changePasswordBtn"/>
            </button>
        </form:form>
    </div>
</t:main-html>