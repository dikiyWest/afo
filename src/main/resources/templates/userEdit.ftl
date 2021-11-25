<#import "parts/common.ftl" as c>
<#import "parts/userInput.ftl" as l>
<@c.page>
<form action="/user" method="post">
    <#include "parts/userInput.ftl" />
</form>
</@c.page>