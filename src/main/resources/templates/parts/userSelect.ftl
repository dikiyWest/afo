
<#if isAdmin || isKurator>
    <label class="form-label"> Назначить пользователю : </label>
    <select class="form-select" name="toUser" id="toUser">
        <option value=""> Выберите пользователя</option>
    <#list users as user>
        <option value="${user.id}"> ${user.getFio()?if_exists}</option>
    </#list>
    </select>
</#if>