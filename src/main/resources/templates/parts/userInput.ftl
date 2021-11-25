
    <#include "autorizationInput.ftl"/>

           <#list roles as role>
        <div>
            <label><input type="checkbox" name="${role}" <#if user??>${user.roles?seq_contains(role)?string("checked","")}</#if>>${role}</label>
        </div>
           </#list>
        <input type="email" name="email" value="<#if user??>${user.email!""}</#if>">
        <input type="text" name="fio" value="<#if user??>${user.fio!""}</#if>">
        <input type="text" name="iin" value="<#if user??>${user.iin!""}</#if>">
        <input type="text" name="phone" value="<#if user??>${user.phone!""}</#if>">
        <input type="text" name="placeOfWork" value="<#if user??>${user.placeOfWork!""}</#if>">


        <select name="region" id="region">
    <#list regions as region>
        <option value="${region.id}" <#if user??><#if user.region?? && user.region==region>selected</#if></#if>> ${region.nameRegion?if_exists}</option>
    </#list>
        </select>
        <label><input type="checkbox" name="active" <#if user??>${user.active?string("checked","")}</#if>>Активировать пользователя</label>
<#if user??><#if user.id??> <input type="hidden" value="${user.id}" name="userId"></#if></#if>
        <button type="submit">Save</button>

