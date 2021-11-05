<#import "parts/common.ftl" as c>
<@c.page>
 <div class="form-row">
     <form method="get" action="/main" class="row row-cols-lg-auto g-3 align-items-center">
         <div class="form-group col-md-7">
             <div class="input-group">
                 <input class="form-control" type="text" name="filter" value="${filter?ifExists}"
                        placeholder="Search by tag">
             </div>
         </div>
         <div class="col-12">
             <button class="btn btn-primary" type="submit">Search</button>
         </div>
     </form>
 </div>

    <#include "parts/messageEdit.ftl"/>
    <#include "parts/messageList.ftl"/>

</@c.page>