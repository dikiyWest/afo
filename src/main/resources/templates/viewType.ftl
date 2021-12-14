<#import "parts/common.ftl" as c>
<@c.page>


    <div class="mb-3">
        <label class="form-label"> Дата мероприятия : </label>
        <div class="input-group mb-3">
            <input class="form-control" type="datetime-local" name="dateValueTask"
                   value="${task.dateTask}" disabled>
        </div>
    </div>
    <div class="mb-3">
        <label class="form-label"> Наименование задачи : </label>
        <input class="form-control" type="text" name="nameTask"
               value="<#if task??>${task.nameTask!""}</#if>" disabled>
    </div>
    <div class="mb-3">
        <label class="form-label"> Примечание : </label>
        <textarea class="form-control" aria-label="Примечание"
                  name="description" disabled><#if task??>${task.description!""}</#if></textarea>
    </div>

    <#if task??>
        <#if task.activity??>
                <div class="mb-3">
                    <label class="form-label"> Наименование мероприятия : </label>
                    <input class="form-control" type="text" name="nameActivity"
                           value="${task.activity.nameActivity!""}" disabled>
                </div>
          <div class="mb-3">
              <label class="form-label"> Место проведения : </label>
              <input class="form-control" type="text" name="placeActivity"
                     value="${task.activity.placeActivity!""}" disabled>
          </div>
        <#elseIf task.contact??>
           <div class="mb-3">
               <label class="form-label"> ФИО : </label>
               <input class="form-control" type="text" name="fio" value="${task.contact.fio!""}" disabled>
           </div>
          <div class="mb-3">
              <label class="form-label"> Место работы : </label>
              <input class="form-control" type="text" name="placeOfWork"
                     value="${task.contact.placeOfWork!""}" disabled>
          </div>
           <div class="mb-3">
               <label class="form-label"> Телефон мобильный : </label>
               <div class="input-group mb-3">
                   <span class="input-group-text" id="basic-addon1">+7</span>
                   <input class="form-control" aria-describedby="basic-addon1" type="text" name="phoneMobile"
                          value="${task.contact.phoneMobile!""}" disabled>
               </div>
           </div>
        <#elseIf task.enrollee??>
        <div class="mb-3">
            <label class="form-label"> ФИО : </label>
            <input class="form-control" type="text" name="fio" value="${task.enrollee.fio!""}" disabled>
        </div>
         <div class="mb-3">
             <label class="form-label"> Учебное заведение : </label>
             <input class="form-control" type="text" name="university"
                    value="${task.enrollee.university!""}" disabled>
         </div>
        </#if>
    </#if>


</@c.page>