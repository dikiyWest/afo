<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<div>

    <@l.logout/>
    <span><a href="/user">User</a></span>
    <form method="post" enctype="multipart/form-data">
        <input type="text" name="text" placeholder="message"/>
        <input type="text" name="tag" placeholder="tag"/>
        <input type="file" name="file">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Add</button>
    </form>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter?ifExists}">
        <button type="submit">Search</button>
    </form>
</div>
<div>Список сообщений</div>
<#list messages as message>
    <div>
    <b>${message.id}</b>
        <span>${message.text}</span>
        <i>${message.tag}</i>
        <strong>${message.authorName}</strong>
        <div>
            <#if message.filename??>
                <img src="/img/${message.filename}">
            </#if>
        </div>
    </div>
<#else>
No massage
</#list>
</@c.page>