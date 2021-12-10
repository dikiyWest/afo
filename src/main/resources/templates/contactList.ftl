<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>
<@c.page>

<a class="btn btn-primary mb-3" href="/contact/contactAdd/stay">Добавить</a>
<div class="container">
    <div class="row mt-2">
        <div class="col-10">
            <form method="get" action="/contact" class="row row-cols-lg-auto g-3 align-items-center">
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
            <form method="get" action="/contact" class="row row-cols-lg-auto g-3 align-items-center">
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
            <th>ФИО</th>
            <th>ИИН</th>
            <th>Место работы</th>
            <th>Телефон городской</th>
            <th>Телефон мобильный</th>
            <th>Email</th>
            <th>Дата создания</th>
            <th>Действие</th>
        </tr>
        </thead>
        <tbody>
    <#list page.content as contact>
    <tr>
        <td>${contact.fio?ifExists}</td>
        <td>${contact.iin?ifExists}</td>
        <td>${contact.placeOfWork?ifExists}</td>
        <td>${contact.phoneMobile?ifExists}</td>
        <td>${contact.phoneCity?ifExists}</td>
        <td>${contact.email?ifExists}</td>
        <td>${contact.getFormatCreatedAt()?ifExists}</td>
        <td><a class="btn btn-outline-info" href="/contact/${contact.id}">Редактировать</a></td>
    </tr>
    </#list>
        </tbody>

    </table>

    <@p.pager url page/>
</@c.page>