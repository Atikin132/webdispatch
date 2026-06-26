<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header class="header">
    <div class="logo"></div>
    <div class="greeting-logout">
        <p class="user-greeting">
            <spring:message code="headerUserGreeting"/>,
            ${pageContext.request.userPrincipal.name}!
        </p>
        <t:change-lang-btn/>
        <form method="post" action="${pageContext.request.contextPath}/logout.jhtml">
            <sec:csrfInput/>
            <button class="button blue"><spring:message code="headerLogoutBtn"/></button>
        </form>
    </div>
</header>