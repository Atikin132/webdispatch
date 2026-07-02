<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="welcome" var="welcome"/>
<t:main-html title="${welcome}" pageName="welcome">
    <div class="welcome-page">
        <h2><spring:message code="welcome"/>, ${pageContext.request.userPrincipal.name}!</h2>
    </div>
</t:main-html>