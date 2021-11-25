<#import "parts/common.ftl" as c>
<@c.page>

<a href="/registration">Add new user</a>

<table class="table table-striped">
    <thead>
    <tr>
        <th>Имя пользователя</th>
        <th>Роли</th>
        <th>Дата входа в систему</th>
        <th>Действие</th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
    <tr>
        <td>${user.username}</td>
        <td><#list user.roles as role>${role}<#sep>, </#list></td>
        <td>${user.updatedAt?ifExists}</td>
        <td><a class="btn btn-outline-info" href="/user/${user.id}">Редактировать/Просмотр</a></td>
    </tr>
    </#list>
    </tbody>
</table>
</@c.page>