<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="menu">
    <ul class="menu-list">
        <li class="${requestScope.currentPage == 'welcome' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/welcome.jhtml">Home</a>
        </li>
        <c:if test="${sessionScope.user.hasRole('Administrator')}">
            <li class="${requestScope.currentPage == 'users' ? 'active' : ''}">
                <a href="${pageContext.request.contextPath}/users.jhtml">Users</a>
            </li>
        </c:if>
        <li class="${requestScope.currentPage == 'loginedit' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/loginedit.jhtml">Change password</a>
        </li>
    </ul>
</nav>