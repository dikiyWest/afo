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
        <input type="hidden" name="id" value="<#if message??>${message.id}<#else > </#if>"/>
        <button class="btn btn-primary mb-3" type="submit">Save</button>
    </form>

</div>