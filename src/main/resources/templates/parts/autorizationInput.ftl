<input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div class="mb-3">
        <label class="form-label"> Логин : </label>
        <input class="form-control ${(usernameError??)?string('is-invalid','')}" <#if user??><#if user.username??>value="${user.username}"</#if></#if> type="text" name="username"/>
    <#if usernameError??>
            <div class="invalid-feedback">
                ${usernameError}
            </div>
        </#if>
    </div>
    <div class="mb-3">
        <label class="form-label"> Пароль: </label>
        <input class="form-control ${(passwordError??)?string('is-invalid','')}" type="password" name="password"/>
        <#if passwordError??>
            <div class="invalid-feedback">
                ${passwordError}
            </div>
        </#if>
    </div>