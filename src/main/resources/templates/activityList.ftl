<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>
<@c.page>

<a class="btn btn-primary mb-3" href="/activity/activityAdd">Добавить</a>
<div class="container">
    <div class="row mt-2">
        <div class="col-10">
            <form method="get" action="/activity" class="row row-cols-lg-auto g-3 align-items-center">
                <div class="form-group col-md-7">
                    <div class="input-group">
                        <input class="form-control" type="text" name="filter" value="${filter?ifExists}"
                               placeholder="Поиск по мероприятию">
                    </div>
                </div>
                <div>
                    <button class="btn btn-primary" type="submit">Поиск</button>
                </div>
        </div>
        </form>
        <div class="col">
            <form method="get" action="/enrollee" class="row row-cols-lg-auto g-3 align-items-center">
                <select class="form-select bg-size" name="sort" id="sort" onchange="this.form.submit()">
                    <option value="createdAt,DESC" <#if pageSort == "createdAt,DESC">selected</#if>>Дата создания по убыванию</option>
                    <option value="createdAt,ASC" <#if pageSort=="createdAt,ASC">selected</#if>>Дата создания по возрастанию</option>
                    <option value="fio,DESC" <#if pageSort=="fio,DESC">selected</#if>>ФИО по убыванию</option>
                    <option value="fio,ASC" <#if pageSort=="fio,ASC">selected</#if>>ФИО по возрастанию</option>
                    <option value="updatedAt,DESC" <#if pageSort=="updatedAt,DESC">selected</#if>>Дата изменения по убыванию</option>
                    <option value="updatedAt,ASC" <#if pageSort=="updatedAt,ASC">selected</#if>>Дата изменения по возрастанию</option>
                    <option value="iin,DESC" <#if pageSort=="iin,DESC">selected</#if>>ИИН по убыванию</option>
                    <option value="iin,ASC" <#if pageSort=="iin,ASC">selected</#if>>ИИН по возрастанию</option>
                </select>
            </form>
        </div>
    </div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Дата и время мероприятия</th>
            <th>Наименование мероприятия</th>
            <th>Формат</th>
            <th>Учебное заведение</th>
            <th>Регион</th>
            <th>Действие</th>
        </tr>
        </thead>
        <tbody>
    <#list page.content as activity>
    <tr>
        <td>${activity.dateActivity?ifExists}</td>
        <td>${activity.nameActivity?ifExists}</td>
        <td>${activity.formatActivity?ifExists}</td>
        <td>${activity.placeActivity?ifExists}</td>
        <td>${activity.region.nameRegion?ifExists}</td>
        <td><a class="btn btn-outline-info" href="/activity/${activity.id}">Редактировать</a></td>
    </tr>
    </#list>
        </tbody>

    </table>

    <@p.pager url page/>
</@c.page>