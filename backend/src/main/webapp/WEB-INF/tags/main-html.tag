<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <c:if test="${not empty pageName}">
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/resources/css/${pageName}.css">
    </c:if>
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