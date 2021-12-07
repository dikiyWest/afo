<#import "parts/common.ftl" as c>
<@c.page>

<form action="/activity" method="post">

    <div class="mb-3">
        <label class="form-label"> Дата мероприятия : </label>
        <div class="input-group mb-3">
            <input class="form-control" type="date" name="dateActivity" min="${minDate}"
                   value="<#if activity??>${activity.dateActivity!""}<#else>${dateActivity!""}</#if>">
            <span class="input-group-text">:</span>
            <input class="form-control" type="time" name="dateActivity" step="1800" min="00:00"
                   value="<#if activity??>${activity.dateActivity!""}<#else>${dateActivity!""}</#if>">
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
        <option value="иное">иное</option>
    </select>
    <div class="mb-3">
        <label class="form-label"> Телефон : </label>
        <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon1">+7</span>
            <input class="form-control" aria-describedby="basic-addon1" type="text" name="placeActivity"
                   value="<#if activity??>${activity.placeActivity!""}</#if>">
        </div>
    </div>
    <label class="form-label"> Регион : </label>
    <select class="form-select" name="region" id="region">
    <#list regions as region>
        <option value="${region.id}" <#if activity??><#if activity.region?? && activity.region==region>selected</#if></#if>> ${region.nameRegion?if_exists}</option>
    </#list>
    </select>


    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
<#if activity??><#if activity.id??> <input type="hidden" value="${activity.id}" name="activityId"></#if></#if>
    <button class="btn btn-primary mt-3" type="submit">Save</button>
</form>
</@c.page>