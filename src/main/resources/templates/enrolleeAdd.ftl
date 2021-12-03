<#import "parts/common.ftl" as c>
<@c.page>

<form action="/enrollee" method="post">
    <div class="mb-3">
        <label class="form-label"> ФИО : </label>
        <input class="form-control" type="text" name="fio" value="<#if enrollee??>${enrollee.fio!""}</#if>">
    </div>
    <div class="mb-3">
        <label class="form-label"> ИИН : </label>
        <input class="form-control" type="text" name="iin" value="<#if enrollee??>${enrollee.iin!""}</#if>">
    </div>
    <div class="mb-3">
        <label class="form-label"> E-mail : </label>
        <input class="form-control" type="email" name="email" value="<#if enrollee??>${enrollee.email!""}</#if>">
    </div>
    <div class="mb-3">
        <label class="form-label"> Телефон : </label>
        <input class="form-control" type="text" name="phone" value="<#if enrollee??>${enrollee.phone!""}</#if>">
    </div>
    <div class="mb-3">
        <label class="form-label"> Место работы : </label>
        <input class="form-control" type="text" name="placeOfWork" value="<#if enrollee??>${enrollee.placeOfWork!""}</#if>">
    </div>

    <label class="form-label"> Регион : </label>
    <select class="form-select" name="region" id="region">
    <#list regions as region>
        <option value="${region.id}" <#if enrollee??><#if enrollee.region?? && enrollee.region==region>selected</#if></#if>> ${region.nameRegion?if_exists}</option>
    </#list>
    </select>

    <label class="form-label"> Роли : </label>
<#list roles as role>
            <div class="form-check form-switch">
                <input role="switch" class="form-check-input" type="checkbox" name="${role}" id="flexSwitchCheck${role}"
                <#if enrollee??>${enrollee.roles?seq_contains(role)?string("checked","")}</#if>>
                <label class="form-check-label" for="flexSwitchCheck${role}">${role}</label>
            </div>
</#list>
    <label class="form-label"> Статус пользователя : </label>
    <div class="form-check form-switch">
        <input role="switch" class="form-check-input" type="checkbox" name="active" id="flexSwitchenrollee" <#if enrollee??>${enrollee.active?string("checked","")}</#if>>
        <label class="form-check-label" for="flexSwitchenrollee">Активировать пользователя</label>
    </div>
<#if enrollee??><#if enrollee.id??> <input type="hidden" value="${enrollee.id}" name="enrolleeId"></#if></#if>
    <button class="btn btn-primary mt-3" type="submit">Save</button>
</form>
</@c.page>