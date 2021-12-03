<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>
<@c.page>

<a class="btn btn-primary mb-3" href="/registration">Новый пользователь</a>
<div class="container">
    <div class="row mt-2">
        <div class="col-10">
            <form method="get" action="/user" class="row row-cols-lg-auto g-3 align-items-center">
                <div class="form-group col-md-7">
                    <div class="input-group">
                        <input class="form-control" type="text" name="filter" value="${filter?ifExists}"
                               placeholder="Поиск по ИИН/ФИО">
                    </div>
                </div>
                <div>
                    <button class="btn btn-primary" type="submit">Поиск</button>
                </div>
        </div>
        </form>
        <div class="col">
            <form method="get" action="/user" class="row row-cols-lg-auto g-3 align-items-center">
                <select class="form-select bg-size" name="sort" id="sort" onchange="this.form.submit()">
                    <option value="createdAt,DESC" <#if pageSort == "createdAt,DESC">selected</#if>>Дата создания по
                        убыванию
                    </option>
                    <option value="createdAt,ASC" <#if pageSort=="createdAt,ASC">selected</#if>>Дата создания по
                        возрастанию
                    </option>
                    <option value="username,DESC" <#if pageSort=="username,DESC">selected</#if>>Пользователь по
                        убыванию
                    </option>
                    <option value="username,ASC" <#if pageSort=="username,ASC">selected</#if>>Пользователь по
                        возрастанию
                    </option>
                    <option value="updatedAt,DESC" <#if pageSort=="updatedAt,DESC">selected</#if>>Дата входа по
                        убыванию
                    </option>
                    <option value="updatedAt,ASC" <#if pageSort=="updatedAt,ASC">selected</#if>>Дата входа по
                        возрастанию
                    </option>
                    <option value="iin,DESC" <#if pageSort=="iin,DESC">selected</#if>>ИИН по убыванию</option>
                    <option value="iin,ASC" <#if pageSort=="iin,ASC">selected</#if>>ИИН по возрастанию</option>
                </select>

            </form>
        </div>

    </div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ФИО</th>
            <th>ИИН</th>
            <th>Учебное заведение</th>
            <th>Образовательная программа</th>
            <th>Телефон</th>
            <th>Email</th>
            <th>Дата создания</th>
            <th>Действие</th>
        </tr>
        </thead>
        <tbody>
    <#list page.content as enrollee>
    <tr>
        <td>${enrollee.fio?ifExists}</td>
        <td>${enrollee.iin?ifExists}</td>
        <td>${user.university?ifExists}</td>
        <td>${user.educationProgramm.getNameEducation()?ifExists}</td>
        <td>${user.phone?ifExists}</td>
        <td>${user.email?ifExists}</td>
        <td>${user.phone?ifExists}</td>
        <td><a class="btn btn-outline-info" href="/user/${user.id}">Редактировать/Просмотр</a></td>
    </tr>
    </#list>
        </tbody>

    </table>

    <@p.pager url page/>
</@c.page>