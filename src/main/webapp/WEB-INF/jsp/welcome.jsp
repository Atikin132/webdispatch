<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/welcome.css">
</head>
<body>

<div class="welcome-page">
    <h2>Welcome, ${sessionScope.user.login}!</h2>
    <p>
        Role:
        ${sessionScope.user.role}
    </p>
    <a href="${pageContext.request.contextPath}/loginedit.jhtml">
        Change password
    </a>
    <form method="post" action="${pageContext.request.contextPath}/logout.jhtml">
        <button>Logout</button>
    </form>
</div>

</body>
</html>