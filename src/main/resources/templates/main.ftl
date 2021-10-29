<#import "parts/common.ftl" as c>
<@c.page>
 <div class="form-row">
     <form method="get" action="/main" class="row row-cols-lg-auto g-3 align-items-center">
         <div class="form-group col-md-7">
             <div class="input-group">
                 <input class="form-control" type="text" name="filter" value="${filter?ifExists}" placeholder="Search by tag">
             </div>
         </div>
         <div class="col-12">
             <button class="btn btn-primary" type="submit">Search</button>
         </div>
     </form>
 </div>

<div>
    <form method="post" enctype="multipart/form-data">
        <input type="text" class="form-control ${(textError??)?string('is-invalid','')}"
               value=" <#if message??>${message.text}</#if>"
               name="text" placeholder="message"/>
        <#if message??>
        <div class="invalid-feedback">
            ${textError}
        </div>
        </#if>
        <input type="text" name="tag" placeholder="tag"/>
        <input type="file" name="file">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Add</button>
    </form>

</div>
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