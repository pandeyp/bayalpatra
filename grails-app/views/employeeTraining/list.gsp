
<%@ page import="com.bayalpatra.hrm.EmployeeTraining" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'employeeTraining.label', default: 'Employee Training')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h4><g:message code="default.list.label" args="[entityName]" /></h4>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'employeeTraining.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="title" title="${message(code: 'employeeTraining.title.label', default: 'Title')}" />
                        
                            <g:sortableColumn property="startDate" title="${message(code: 'employeeTraining.startDate.label', default: 'Start Date')}" />
                        
                            <g:sortableColumn property="endDate" title="${message(code: 'employeeTraining.endDate.label', default: 'End Date')}" />
                        
                            <g:sortableColumn property="boundPeriodFrom" title="${message(code: 'employeeTraining.boundPeriodFrom.label', default: 'Bound Period From')}" />
                        
                            <g:sortableColumn property="boundPeriodTo" title="${message(code: 'employeeTraining.boundPeriodTo.label', default: 'Bound Period To')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${employeeTrainingInstanceList}" status="i" var="employeeTrainingInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td id="linked"><g:link action="show" id="${employeeTrainingInstance.id}">${fieldValue(bean: employeeTrainingInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: employeeTrainingInstance, field: "title")}</td>
                        
                            <td><g:formatDate date="${employeeTrainingInstance.startDate}" /></td>
                        
                            <td><g:formatDate date="${employeeTrainingInstance.endDate}" /></td>
                        
                            <td><g:formatDate date="${employeeTrainingInstance.boundPeriodFrom}" /></td>
                        
                            <td><g:formatDate date="${employeeTrainingInstance.boundPeriodTo}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${employeeTrainingInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
