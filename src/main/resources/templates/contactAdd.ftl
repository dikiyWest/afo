<#import "parts/common.ftl" as c>
<@c.page>
    <#if check == "stay" || check == "checking">
<form action="/contact/contactAdd/checking" method="get">
    <div class="mb-3">
        <label class="form-label"> ИИН : </label>
        <input class="form-control" type="text" name="iin" value="<#if contact??>${contact.iin!""}</#if>">
    </div>
    <button class="btn btn-primary mt-3" type="submit">Проверить</button>
</form>
    <#else>
<form action="/contact" method="post">
    <div class="mb-3">
        <label class="form-label"> ИИН : </label>
        <input class="form-control" type="text" name="iin"
               value="<#if contact??>${contact.iin!""}<#else>${iin!""}</#if>">
    </div>
    <div class="mb-3">
        <label class="form-label"> ФИО : </label>
        <input class="form-control" type="text" name="fio" value="<#if contact??>${contact.fio!""}</#if>">
    </div>
    <div class="mb-3">
        <label class="form-label"> E-mail : </label>
        <input class="form-control" type="email" name="email" value="<#if contact??>${contact.email!""}</#if>">
    </div>
    <div class="mb-3">
        <label class="form-label"> Телефон мобильный : </label>
        <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon1">+7</span>
            <input class="form-control" aria-describedby="basic-addon1" type="text" name="phoneMobile"
                   value="<#if contact??>${contact.phoneMobile!""}</#if>">
        </div>
    </div>
    <div class="mb-3">
        <label class="form-label"> Телефон городской : </label>
        <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon1">+7</span>
            <input class="form-control" aria-describedby="basic-addon1" type="text" name="phoneCity"
                   value="<#if contact??>${contact.phoneCity!""}</#if>">
        </div>
    </div>
    <div class="mb-3">
        <label class="form-label"> Место работы : </label>
        <input class="form-control" type="text" name="placeOfWork"
               value="<#if contact??>${contact.placeOfWork!""}</#if>">
    </div>
    <div class="mb-3">
        <label class="form-label"> Должность : </label>
        <input class="form-control" type="text" name="position"
               value="<#if contact??>${contact.position!""}</#if>">
    </div>
    <label class="form-label"> Регион : </label>
    <select class="form-select" name="region" id="region">
    <#list regions as region>
        <option value="${region.id}" <#if contact??><#if contact.region?? && contact.region==region>selected</#if></#if>> ${region.nameRegion?if_exists}</option>
    </#list>
    </select>
    <div class="mb-3">
        <label class="form-label"> Примечание : </label>
        <textarea class="form-control" aria-label="Примечание"
                  name="note"><#if contact??>${contact.note!""}</#if></textarea>
    </div>
    <div class="form-check form-switch">
        <input role="switch" class="form-check-input" type="checkbox" name="isActve" id="flexSwitchUser" <#if user??>${user.isActve?string("checked","")}</#if>>
        <label class="form-check-label" for="flexSwitchUser">Активировать</label>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
<#if contact??><#if contact.id??> <input type="hidden" value="${contact.id}" name="contactId"></#if></#if>
    <button class="btn btn-primary mt-3" type="submit">Save</button>
</form>

    </#if>
</@c.page>