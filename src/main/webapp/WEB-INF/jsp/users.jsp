<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:main-html title="Users" pageName="users">
    <div class="users-list">
        <h2 class="page-title">Users list</h2>
        <div class="table-container">
            <table class="users-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Login</th>
                    <th>Name</th>
                    <th>Birthday</th>
                    <th>Age</th>
                    <th>Salary</th>
                    <th>Roles</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="user" items="${requestScope.users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.login}</td>
                        <td>${user.name}</td>
                        <td>${user.birthday}</td>
                        <td>${user.age}</td>
                        <td>${user.salary}</td>
                        <td><c:forEach var="role" items="${user.roles}">
                            <p>${role.toString()}</p>
                        </c:forEach></td>
                        <td>
                            <div class="btn-container">
                                <a class="edit-btn"
                                   href="useredit.jhtml?login=${user.login}"></a>
                            </div>
                        </td>
                        <td>
                            <c:if test="${user.login ne sessionScope.user.login}">
                                <form class="btn-container" method="post"
                                      action="${pageContext.request.contextPath}/userdelete.jhtml">
                                    <input type="hidden" name="login" value="${user.login}">
                                    <button class="delete-btn" type="submit"></button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <a class="button green" href="${pageContext.request.contextPath}/useradd.jhtml">Add user</a>
    </div>
</t:main-html>