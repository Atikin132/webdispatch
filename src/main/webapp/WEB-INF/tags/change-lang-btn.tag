<%@ tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="enUrl" value="">
    <c:param name="lang" value="en"/>
    <c:if test="${not empty param.id}">
        <c:param name="id" value="${param.id}"/>
    </c:if>
</c:url>

<c:url var="ruUrl" value="">
    <c:param name="lang" value="ru"/>
    <c:if test="${not empty param.id}">
        <c:param name="id" value="${param.id}"/>
    </c:if>
</c:url>

<div class="lang-switch">
    <a class="en-lang-btn" href="${enUrl}"></a>
    <a class="ru-lang-btn" href="${ruUrl}"></a>
</div>