<#import "parts/common.ftl" as c>
<@c.page>

<form action="/report/${type}/export" method="get">
    <div class="mb-3">
        <label class="form-label"> Дата от : </label>
        <div class="input-group mb-3">
            <input class="form-control" type="date" name="dateMin">
        </div>
    </div>
    <div class="mb-3">
        <label class="form-label"> Дата до : </label>
        <div class="input-group mb-3">
            <input class="form-control" type="date" name="dateMax">
        </div>
    </div>
    <#if type != "user">
    <select class="form-select" name="userId" id="userId">
        <option value="" >Все пользователи</option>
    <#list users as user>
        <option value="${user.id}" > ${user.fio?if_exists}</option>
    </#list>
    </select>
    </#if>
    <button class="btn btn-primary mt-3" type="submit">Сформировать</button>
</form>
</@c.page>