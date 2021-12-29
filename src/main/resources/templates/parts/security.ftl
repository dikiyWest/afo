<#assign
know = Session.SPRING_SECURITY_CONTEXT??
>
<#if know>
    <#assign
    user= Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    isKurator = user.isKurator()
    currentUserId = user.getId()
    currentPageUrl = springMacroRequestContext.requestUri
    >
<#else>
<#assign
name="unknown"
isAdmin = false
currentUserId = -1
>
</#if>