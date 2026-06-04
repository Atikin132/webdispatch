<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/normalize.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/header.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/footer.css">
</head>
<body>
<jsp:include page="layouts/header.jsp"/>

<main>
    <h2>Users Page</h2>
</main>
<%@ include file="layouts/footer.jsp" %>

</body>
</html>