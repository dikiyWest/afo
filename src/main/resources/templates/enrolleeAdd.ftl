<#import "parts/common.ftl" as c>
<@c.page>
    <#if check == "stay" || check == "checking">
<form action="/enrollee/enrolleeAdd/checking" method="get">
    <div class="mb-3">
        <label class="form-label"> ИИН : </label>
        <input class="form-control" type="text" name="iin" value="<#if enrollee??>${enrollee.iin!""}</#if>">
    </div>
    <button class="btn btn-primary mt-3" type="submit">Проверить</button>
</form>
    <#else>
<form action="/enrollee" method="post">
    <div class="mb-3">
        <label class="form-label"> ИИН : </label>
        <input class="form-control" type="text" name="iin"
               value="<#if enrollee??>${enrollee.iin!""}<#else>${iin!""}</#if>">
    </div>
    <div class="mb-3">
        <label class="form-label"> ФИО : </label>
        <input class="form-control" type="text" name="fio" value="<#if enrollee??>${enrollee.fio!""}</#if>">
    </div>
    <div class="mb-3">
        <label class="form-label"> E-mail : </label>
        <input class="form-control" type="email" name="email" value="<#if enrollee??>${enrollee.email!""}</#if>">
    </div>
    <div class="mb-3">
        <label class="form-label"> Телефон : </label>
        <div class="input-group mb-3">
        <span class="input-group-text" id="basic-addon1">+7</span>
        <input class="form-control" aria-describedby="basic-addon1" type="text" name="phone" value="<#if enrollee??>${enrollee.phone!""}</#if>">
        </div>
    </div>
    <div class="mb-3">
        <label class="form-label"> Учебное заведение : </label>
        <input class="form-control" type="text" name="university"
               value="<#if enrollee??>${enrollee.university!""}</#if>">
    </div>
    <label class="form-label"> Регион : </label>
    <select class="form-select" name="region" id="region">
    <#list regions as region>
        <option value="${region.id}" <#if enrollee??><#if enrollee.region?? && enrollee.region==region>selected</#if></#if>> ${region.nameRegion?if_exists}</option>
    </#list>
    </select>
    <label class="form-label"> Образовательная программа : </label>
    <select class="form-select" name="educationProgramm" id="educationProgramm">
    <#list educationProgramms as educationProgramm>
        <option value="${educationProgramm.id}" <#if enrollee??><#if enrollee.educationProgramm?? && enrollee.educationProgramm==educationProgramm>selected</#if></#if>> ${educationProgramm.nameEducation?if_exists}</option>
    </#list>
    </select>
    <div class="mb-3">
        <label class="form-label"> Примечание : </label>
        <textarea class="form-control" aria-label="Примечание" name="note"><#if enrollee??>${enrollee.note!""}</#if></textarea>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
<#if enrollee??><#if enrollee.id??> <input type="hidden" value="${enrollee.id}" name="enrolleeId"></#if></#if>
    <button class="btn btn-primary mt-3" type="submit">Save</button>
</form>

    </#if>
</@c.page>