<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="header">
    <div class="logo"></div>
    <div class="greeting-logout">
        <p class="user-greeting">Hello, ${sessionScope.user.login}!</p>
        <form method="post" action="${pageContext.request.contextPath}/logout.jhtml">
            <button class="button blue">Logout</button>
        </form>
    </div>
</header>