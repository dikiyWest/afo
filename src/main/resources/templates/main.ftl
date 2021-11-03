<#import "parts/common.ftl" as c>
<@c.page>
 <div class="form-row">
     <form method="get" action="/main" class="row row-cols-lg-auto g-3 align-items-center">
         <div class="form-group col-md-7">
             <div class="input-group">
                 <input class="form-control" type="text" name="filter" value="${filter?ifExists}"
                        placeholder="Search by tag">
             </div>
         </div>
         <div class="col-12">
             <button class="btn btn-primary" type="submit">Search</button>
         </div>
     </form>
 </div>

 <a class="btn btn-primary mt-3" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
    aria-controls="collapseExample">
     Add new message
 </a>

<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <form method="post" enctype="multipart/form-data">
        <div class="input-group mb-3 mt-3">
            <span class="input-group-text" id="inputGroup-sizing-default">Сообщение</span>
            <input aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default"
                   type="text" class="form-control ${(textError??)?string('is-invalid','')}"
                   value="<#if message??>${message.text}</#if>"
                   name="text" placeholder="message"/>
            <#if textError??>
            <div class="invalid-feedback">
                ${textError}
            </div>
            </#if>
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text" id="inputGroup-sizing-default">tag</span>
            <input class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default"
                   type="text" name="tag" placeholder="tag"/>
        </div>
        <div class="input-group mb-3">
            <label class="input-group-text" for="inputGroupFile01">Upload</label>
            <input type="file" name="file" class="form-control" id="inputGroupFile01">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary mb-3" type="submit">Add</button>
    </form>

</div>

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
                        <strong>${message.authorName}</strong>
                    </div>
                </div>
            </div>
        </div>

    <#else>
No massage
    </#list>
 </div>
</@c.page>