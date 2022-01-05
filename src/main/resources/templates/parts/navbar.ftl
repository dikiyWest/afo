<#include "security.ftl">
<#import "login.ftl" as l>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Atu front office</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto">
            <li class="nav-item">
                <a class="nav-link" aria-current="page" href="/">Home</a>
            </li>
            <!--    <li class="nav-item">
                    <a class="nav-link" href="/main">Messages</a>
                </li>
                <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/user-messages/${currentUserId}">My Messages</a>
                </li>
                </#if>-->
                <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link <#if currentPageUrl == '/user'>active</#if>" href="/user">Пользователи</a>
                </li>
                </#if>
                  <#if know>
                <li class="nav-item">
                    <a class="nav-link <#if currentPageUrl == '/enrollee'>active</#if>" href="/enrollee">Поступающие</a>
                </li>
                  </#if>
                  <#if know>
                <li class="nav-item">
                    <a class="nav-link <#if currentPageUrl == '/activity'>active</#if>" href="/activity">Мероприятия</a>
                </li>
                  </#if>
                   <#if know>
                <li class="nav-item">
                    <a class="nav-link <#if currentPageUrl == '/contact'>active</#if>" href="/contact">Контакты</a>
                </li>
                   </#if>
                   <#if know>
                <li class="nav-item">
                    <a class="nav-link <#if currentPageUrl == '/task'>active</#if>" href="/task">Задачи</a>
                </li>
                   </#if>

                <#if know>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        Отчеты
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="/report/user">Отчет по пользователю</a></li>
                        <li><a class="dropdown-item" href="/report/enrollee">Отчет по поступающему</a></li>
                        <li><a class="dropdown-item" href="/report/activity">Отчет по мероприятиям</a></li>
                        <li><a class="dropdown-item" href="/report/contact">Отчет по контактам</a></li>

                    </ul>
                </li>
                </#if>
            </ul>

            <#if know>
            <div class="me-md-3 navbar-text">${name}</div>
            <div class="navbar-text">
                <@l.logout/>
            </div>

            <#else>

            <a class="btn btn-primary ml-3" href="/main">Войти</a>

            </#if>

        </div>
    </div>
</nav>