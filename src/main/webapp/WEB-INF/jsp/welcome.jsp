<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/normalize.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/welcome.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/menu.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/header.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/footer.css">
</head>
<body>
<jsp:include page="layouts/header.jsp"/>
<jsp:include page="layouts/menu.jsp"/>
<main>
    <div class="welcome-page">
        <h2>Welcome, ${sessionScope.user.login}!</h2>
    </div>
</main>
<%@ include file="layouts/footer.jsp" %>

</body>
</html>