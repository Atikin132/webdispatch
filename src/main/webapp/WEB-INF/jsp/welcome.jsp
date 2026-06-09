<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:main-html title="Welcome" pageName="welcome">
    <div class="welcome-page">
        <h2>Welcome, ${sessionScope.user.login}!</h2>
    </div>
</t:main-html>