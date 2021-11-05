<#macro login path>
<form action="${path}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div class="mb-3">
        <label class="form-label"> User Name : </label>
        <input class="form-control ${(usernameError??)?string('is-invalid','')}" type="text" name="username"/>
        <#if usernameError??>
            <div class="invalid-feedback">
                ${usernameError}
            </div>
        </#if>
    </div>
    <div class="mb-3">
        <label class="form-label"> Password: </label>
        <input class="form-control ${(passwordError??)?string('is-invalid','')}" type="password" name="password"/>
        <#if passwordError??>
            <div class="invalid-feedback">
                ${passwordError}
            </div>
        </#if>

    </div>
    <button class="btn btn-primary" type="submit" value="Sign In"> Submit</button>

</form>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <button class="btn btn-primary" type="submit" value="Sign Out"> Sign Out</button>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>
</#macro>