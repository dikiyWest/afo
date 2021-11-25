
    <#include "autorizationInput.ftl"/>

           <#list roles as role>
        <div>
            <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked","")}>${role}</label>
        </div>
           </#list>
        <input type="email" name="email" value="${user.email!""}">
        <input type="text" name="fio" value="${user.fio!""}">
        <input type="text" name="iin" value="${user.iin!""}">
        <input type="text" name="phone" value="${user.phone!""}">
        <input type="text" name="placeOfWork" value="${user.placeOfWork!""}">


        <select name="region" id="region">
    <#list regions as region>
        <option value="${region.id}" <#if user.region?? && user.region==region>selected</#if>> ${region.nameRegion?if_exists}</option>
    </#list>
        </select>
        <label><input type="checkbox" name="active" ${user.active?string("checked","")}>Активировать пользователя</label>
<#if user??><#if user.id??> <input type="hidden" value="${user.id}" name="userId"></#if></#if>
        <button type="submit">Save</button>

