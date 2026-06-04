<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<header class="header">
    <div class="logo">Logo</div>
    <div class="greeting-logout">
        <p class="user-greeting">Hello, ${sessionScope.user.login}!</p>
        <form method="post" action="${pageContext.request.contextPath}/logout.jhtml">
            <button>Logout</button>
        </form>
    </div>
</header>
