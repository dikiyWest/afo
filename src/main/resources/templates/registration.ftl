<#import "parts/common.ftl" as c>
<#import "parts/userInput.ftl" as l>

<@c.page>
add new user
${message!""}

<form action="/registration" method="post">
<#include "parts/userInput.ftl" />
    </form>
</@c.page>
