<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="usersTitle" var="usersTitle"/>
<t:main-html title="${usersTitle}" pageName="users">
    <div class="users-list">
        <h2 class="page-title"><spring:message code="usersList"/></h2>
        <div class="table-container">
            <table class="users-table">
                <thead>
                <tr>
                    <th><spring:message code="usersListID"/></th>
                    <th><spring:message code="usersListLogin"/></th>
                    <th><spring:message code="usersListName"/></th>
                    <th><spring:message code="usersListBirthDate"/></th>
                    <th><spring:message code="usersListAge"/></th>
                    <th><spring:message code="usersListSalary"/></th>
                    <th><spring:message code="usersListRoles"/></th>
                    <th><spring:message code="usersListEdit"/></th>
                    <th><spring:message code="usersListDelete"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.login}</td>
                        <td>${user.name}</td>
                        <td>${user.birthDate}</td>
                        <td>${user.age}</td>
                        <td>${user.salary}</td>
                        <td><c:forEach var="role" items="${user.roles}">
                            <p>${role.toString()}</p>
                        </c:forEach></td>
                        <td>
                            <div class="btn-container">
                                <a class="edit-btn"
                                   href="useredit.jhtml?id=${user.id}"></a>
                            </div>
                        </td>
                        <td>
                            <c:if test="${user.login ne sessionScope.user.login}">
                                <form class="btn-container" method="post"
                                      action="${pageContext.request.contextPath}/userdelete.jhtml">
                                    <input type="hidden" name="id" value="${user.id}">
                                    <button class="delete-btn" type="submit"></button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <a class="button green" href="${pageContext.request.contextPath}/useradd.jhtml">
            <spring:message code="usersListAddBtn"/>
        </a>
    </div>
</t:main-html>