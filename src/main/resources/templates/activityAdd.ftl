<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>
<@c.page>

<form action="/activity" method="post" enctype="multipart/form-data">
    <div class="mb-3">
        <label class="form-label"> Дата мероприятия : </label>
        <div class="input-group mb-3">
            <input class="form-control" type="date" name="dateValueActivity" min="${minDate}"
                   value="${minDate}">
            <span class="input-group-text">:</span>
            <input class="form-control" type="time" name="timeValueActivity" step="1800" min="00:00"
                   value="00:00">
        </div>
    </div>
    <div class="mb-3">
        <label class="form-label"> Наименование мероприятия : </label>
        <input class="form-control" type="text" name="nameActivity"
               value="<#if activity??>${activity.nameActivity!""}</#if>">
    </div>

    <label class="form-label"> Формат : </label>
    <select class="form-select" name="formatActivity" id="formatActivity">
        <option value="ZOOM">ZOOM</option>
        <option value="Личная презентация">Личная презентация</option>
        <option value="Иное">Иное</option>
    </select>
    <div class="mb-3">
        <label class="form-label">Количество участников: </label>
        <input class="form-control" type="number" name="countPeople"
               value="<#if activity??>${activity.countPeople!""}</#if>">
    </div>
    <div class="mb-3">
        <label class="form-label"> Место проведения : </label>
        <input class="form-control" type="text" name="placeActivity"
               value="<#if activity??>${activity.placeActivity!""}</#if>">
    </div>
    <div class="input-group mb-3">
        <label class="input-group-text" for="inputGroupFile01">Подтверждающий документ(Загружать в
            архиве(zip,rar))</label>
        <input type="file" name="file" class="form-control" id="inputGroupFile01">
    </div>

    <label class="form-label"> Контакт : </label>
    <select class="form-select mb-3" name="contact" id="contact">
        <option value="">Любой контакт</option>
    <#list contacts as contact>
        <option value="${contact.id}" <#if activity??><#if activity.contact?? && activity.contact==contact>selected</#if></#if>> ${contact.getFio()?if_exists}</option>
    </#list>
    </select>
    <a class="btn btn-primary mb-3" href="/contact/contactAdd/stay">Добавить новый контакт</a>

    <br>
    <label class="form-label"> Регион : </label>
    <select class="form-select" name="region" id="region">
    <#list regions as region>
        <option value="${region.id}" <#if activity??><#if activity.region?? && activity.region==region>selected</#if></#if>> ${region.nameRegion?if_exists}</option>
    </#list>
    </select>
    <#include "parts/userSelect.ftl">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
<#if activity??><#if activity.id??> <input type="hidden" value="${activity.id}" name="activityId"></#if></#if>
    <button class="btn btn-primary mt-3" type="submit">Сохранить</button>
</form>
</@c.page>