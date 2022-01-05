<#macro login path>
<form action="${path}" method="post">
    <#include "autorizationInput.ftl"/>
    <button class="btn btn-primary" type="submit" value="Sign In"> Войти</button>
</form>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <button class="btn btn-primary" type="submit" value="Sign Out"> Выйти</button>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>
</#macro>