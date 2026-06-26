<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="menu">
    <ul class="menu-list">
        <li class="${requestScope.currentPage == 'welcome' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/welcome.jhtml">
                <spring:message code="menuHome"/>
            </a>
        </li>
        <sec:authorize access="hasRole('Administrator')">
            <li class="${requestScope.currentPage == 'users' ? 'active' : ''}">
                <a href="${pageContext.request.contextPath}/users.jhtml">
                    <spring:message code="menuUsers"/>
                </a>
            </li>
        </sec:authorize>
        <li class="${requestScope.currentPage == 'loginedit' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/loginedit.jhtml">
                <spring:message code="menuChangePassword"/>
            </a>
        </li>
    </ul>
</nav>