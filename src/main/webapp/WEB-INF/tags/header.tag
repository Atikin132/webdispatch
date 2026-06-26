<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<header class="header">
    <div class="logo"></div>
    <div class="greeting-logout">
        <p class="user-greeting">Hello, ${sessionScope.user.login}!</p>
        <t:change-lang-btn/>
        <form method="post" action="${pageContext.request.contextPath}/logout.jhtml">
            <button class="button blue">Logout</button>
        </form>
    </div>
</header>