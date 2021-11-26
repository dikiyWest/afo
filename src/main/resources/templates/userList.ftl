<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>
<@c.page>

<a href="/registration">Add new user</a>
   <form method="get" action="/user" class="row row-cols-lg-auto g-3 align-items-center">
       <div class="form-group col-md-7">
           <div class="input-group">
               <input class="form-control" type="text" name="filter" value="${filter?ifExists}"
                      placeholder="Поиск по ИИН">
           </div>
       </div>
       <div class="col-12">
           <button class="btn btn-primary" type="submit">Search</button>
       </div>
   </form>

<form method="get" action="/user" class="row row-cols-lg-auto g-3 align-items-center">
<select name="sort" id="sort" onchange="this.form.submit()">
    <option value="createdAt,DESC" <#if pageSort == "createdAt,DESC">selected</#if>>Дата создания по убыванию</option>
    <option value="createdAt,ASC" <#if pageSort=="createdAt,ASC">selected</#if>>Дата создания по возрастанию</option>
    <option value="username,DESC" <#if pageSort=="username,DESC">selected</#if>>Пользователь по убыванию</option>
    <option value="username,ASC" <#if pageSort=="username,ASC">selected</#if>>Пользователь по возрастанию</option>
    <option value="updatedAt,DESC" <#if pageSort=="updatedAt,DESC">selected</#if>>Дата входа по убыванию</option>
    <option value="updatedAt,ASC" <#if pageSort=="updatedAt,ASC">selected</#if>>Дата входа по возрастанию</option>
    <option value="iin,DESC" <#if pageSort=="iin,DESC">selected</#if>>ИИН по убыванию</option>
    <option value="iin,ASC" <#if pageSort=="iin,ASC">selected</#if>>ИИН по возрастанию</option>
</select>

</form>
<table class="table table-striped">
    <thead>
    <tr>
        <th>Имя пользователя</th>
        <th>Роли</th>
        <th>ФИО</th>
        <th>ИИН</th>
        <th>Дата входа в систему</th>
        <th>Действие</th>
    </tr>
    </thead>
    <tbody>
    <#list page.content as user>
    <tr>
        <td>${user.username}</td>
        <td><#list user.roles as role>${role}<#sep>, </#list></td>
        <td>${user.fio?ifExists}</td>
        <td>${user.iin?ifExists}</td>
        <td>${user.updatedAt?ifExists}</td>
        <td><a class="btn btn-outline-info" href="/user/${user.id}">Редактировать/Просмотр</a></td>
    </tr>
    </#list>
    </tbody>

</table>

    <@p.pager url page/>
</@c.page>