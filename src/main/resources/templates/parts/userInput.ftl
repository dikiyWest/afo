<#include "autorizationInput.ftl"/>


<div class="mb-3">
    <label class="form-label"> E-mail : </label>
    <input class="form-control" type="email" name="email" value="<#if user??>${user.email!""}</#if>">
</div>
<div class="mb-3">
    <label class="form-label"> ФИО : </label>
    <input class="form-control" type="text" name="fio" value="<#if user??>${user.fio!""}</#if>">
</div>
<div class="mb-3">
    <label class="form-label"> ИИН : </label>
    <input class="form-control" type="text" name="iin" value="<#if user??>${user.iin!""}</#if>">
</div>
<div class="mb-3">
    <label class="form-label"> Телефон : </label>
    <input class="form-control" type="text" name="phone" value="<#if user??>${user.phone!""}</#if>">
</div>
<div class="mb-3">
    <label class="form-label"> Место работы : </label>
    <input class="form-control" type="text" name="placeOfWork" value="<#if user??>${user.placeOfWork!""}</#if>">
</div>

 <label class="form-label"> Регион : </label>
        <select class="form-select" name="region" id="region">
    <#list regions as region>
        <option value="${region.id}" <#if user??><#if user.region?? && user.region==region>selected</#if></#if>> ${region.nameRegion?if_exists}</option>
    </#list>
        </select>

        <label class="form-label"> Роли : </label>
<#list roles as role>
            <div class="form-check form-switch">
                <input role="switch" class="form-check-input" type="checkbox" name="${role}" id="flexSwitchCheck${role}"
                <#if user??>${user.roles?seq_contains(role)?string("checked","")}</#if>>
                <label class="form-check-label" for="flexSwitchCheck${role}">${role}</label>
            </div>
</#list>
<label class="form-label"> Статус пользователя : </label>
<div class="form-check form-switch">
    <input role="switch" class="form-check-input" type="checkbox" name="active" id="flexSwitchUser" <#if user??>${user.active?string("checked","")}</#if>>
    <label class="form-check-label" for="flexSwitchUser">Активировать пользователя</label>
</div>
<#if user??><#if user.id??> <input type="hidden" value="${user.id}" name="userId"></#if></#if>
        <button class="btn btn-primary mt-3" type="submit">Save</button>

