
<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>
<@c.page>
<div class="dropdown mb-3">
    <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButtonTask" data-bs-toggle="dropdown" aria-expanded="false">
        Добавить
    </button>
    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButtonTask">
        <li><a class="dropdown-item" href="/task/activity/taskAdd">Добавить по мероприятию</a></li>
        <li><a class="dropdown-item" href="/task/contact/taskAdd">Добавить по контакту</a></li>
        <li><a class="dropdown-item" href="/task/enrollee/taskAdd">Добавить по поступающему</a></li>
    </ul>
</div>

<div class="container">
    <div class="row mt-2">
        <div class="col-10">
            <form method="get" action="/task" class="row row-cols-lg-auto g-3 align-items-center">
                <div class="form-group col-md-7">
                    <div class="input-group">
                        <input class="form-control" type="text" name="filter" value="${filter?ifExists}"
                               placeholder="Поиск по задаче">
                    </div>
                </div>
                <div>
                    <button class="btn btn-primary" type="submit">Поиск</button>
                </div>
        </div>
        </form>
        <div class="col">
            <form method="get" action="/task" class="row row-cols-lg-auto g-3 align-items-center">
                <select class="form-select bg-size" name="sort" id="sort" onchange="this.form.submit()">
                    <option value="createdAt,DESC" <#if pageSort == "createdAt,DESC">selected</#if>>Дата создания по убыванию</option>
                    <option value="createdAt,ASC" <#if pageSort=="createdAt,ASC">selected</#if>>Дата создания по возрастанию</option>
                    <option value="dateTask,DESC" <#if pageSort=="datetask,DESC">selected</#if>>Дата задачи по убыванию</option>
                    <option value="dateTask,ASC" <#if pageSort=="datetask,ASC">selected</#if>>Дата задачи по возрастанию</option>
                    <option value="updatedAt,DESC" <#if pageSort=="updatedAt,DESC">selected</#if>>Дата изменения по убыванию</option>
                    <option value="updatedAt,ASC" <#if pageSort=="updatedAt,ASC">selected</#if>>Дата изменения по возрастанию</option>
                    <option value="nameTask,DESC" <#if pageSort=="nametask,DESC">selected</#if>>Наименование по убыванию</option>
                    <option value="nameTask,ASC" <#if pageSort=="nametask,ASC">selected</#if>>Наименование по возрастанию</option>
                </select>
            </form>
        </div>
    </div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Дата и время задачи</th>
            <th>Наименование задачи</th>
            <th>Описание</th>
            <th>Назначение задачи</th>
            <th>Действие</th>
        </tr>
        </thead>
        <tbody>
    <#list page.content as task>

    <tr <#if task.dateTask.isBefore(nowLocalDateTime)> class="table-danger" </#if>>
        <td>${task.dateTask?ifExists}</td>
        <td>${task.nameTask?ifExists}</td>
        <td>${task.description?ifExists}</td>
        <td> <#if task??>
            <#if task.activity??>
            <a class="btn btn-outline-secondary" href="/task/activity/${task.id}">Задача по мероприятию/Просмотреть</a>
        <#elseIf task.contact??>
         <a class="btn btn-outline-secondary" href="/task/contact/${task.id}">Задача по контакту/Просмотреть</a>
        <#elseIf task.enrollee??>
        <a class="btn btn-outline-secondary" href="/task/enrollee/${task.id}">Задача по поступающему/Просмотреть</a>
        </#if>
        </#if>
        </td>
        <td><a class="btn btn-outline-info" href="/task/${task.id}">Редактировать</a></td>
    </tr>
    </#list>
        </tbody>

    </table>

    <@p.pager url page/>
</@c.page>