<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="pageName" required="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/normalize.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/styles.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/header.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/menu.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/footer.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/pages/${pageName}.css">
</head>

<body>

<t:header/>
<t:menu/>

<main>
    <jsp:doBody/>
</main>

<t:footer/>

</body>
</html>