<#macro login path>
<form action="${path}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div class="mb-3">
        <label class="form-label"> User Name : </label>
        <input  class="form-control" type="text" name="username"/>
    </div>
    <div class="mb-3">
        <label class="form-label"> Password: </label>
        <input  class="form-control" type="password" name="password"/>

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