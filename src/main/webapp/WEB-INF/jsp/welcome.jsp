<h2>Welcome, ${sessionScope.user.login}!</h2>
<form method="post" action="${pageContext.request.contextPath}/logout.jhtml">
    <button>Logout</button>
</form>