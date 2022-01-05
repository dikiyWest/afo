<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>
<@c.page>

<form action="/task" method="post">
    <div class="mb-3">
        <label class="form-label"> Дата мероприятия : </label>
        <div class="input-group mb-3">
            <input class="form-control" type="date" name="dateValueTask" min="${minDate}"
                   value="${minDate}">
            <span class="input-group-text">:</span>
            <input class="form-control" type="time" name="timeValueTask" step="1800" min="00:00"
                   value="00:00">
        </div>
    </div>
    <div class="mb-3">
        <label class="form-label"> Наименование задачи : </label>
        <input class="form-control" type="text" name="nameTask"
               value="<#if task??>${task.nameTask!""}</#if>">
    </div>
    <div class="mb-3">
        <label class="form-label"> Примечание : </label>
        <textarea class="form-control" aria-label="Примечание"
                  name="description"><#if task??>${task.description!""}</#if></textarea>
    </div>

    <label class="form-label"> Назначение задачи  : </label>
    <select class="form-select mb-3" name="${type}" id="${type}">
    <#list typeList as object>
        <option value="${object.getIdFromHelper()}" <#if task??><#if task.getType("${type}")?? && task.getType("${type}")==object>selected</#if></#if>> ${object.getNameFromHelper()?if_exists}</option>
    </#list>
    </select>
    <div class="mb-3">
    <#if type??>
    <#if type =="activity">
    <a class="btn btn-primary mb-3" href="/activity/activityAdd">Добавить новое мероприятие</a>
    <#elseif type == "contact">
    <a class="btn btn-primary mb-3" href="/contact/contactAdd/stay">Добавить новый контакт</a>
    <#elseif type=="enrollee">
    <a class="btn btn-primary mb-3" href="/enrollee/enrolleeAdd/stay">Добавить нового поступающего</a>
    <#else>
    </#if>
    </#if>
    </div>
    <#include "parts/userSelect.ftl">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
<#if task??><#if task.id??> <input type="hidden" value="${task.id}" name="taskId"></#if></#if>
    <button class="btn btn-primary mt-3" type="submit">Сохранить</button>
</form>
</@c.page>