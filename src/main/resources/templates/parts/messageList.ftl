<#include "security.ftl">
<div class="row row-cols-1 row-cols-md-3 g-4 mt-3">
    <#list messages as message>
        <div class="col">
            <div class="card" style="width: 18rem;">
                <div>
            <#if message.filename??>
                <img class="card-img-top" src="/img/${message.filename}">
            </#if>
                </div>
                <div class="card-body">
                    <h5 class="card-title">${message.tag}</h5>
                    <p class="card-text">${message.text}</p>
                    <div class="card-footer">
                        <a href="/user-messages/${message.author.id}"> ${message.authorName}</a>
                        <#if message.author.id == currentUserId>
                        <a href="/user-messages/${message.author.id}?message=${message.id}">Edit</a>
                        </#if>
                    </div>
                </div>
            </div>
        </div>

    <#else>
No massage
    </#list>
</div>