
<%@ page import="com.bayalpatra.hrm.EmployeeDependents" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'employeeDependents.label', default: 'Employee Dependents')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>

        <div class="body">
            <h4><g:message code="default.list.label" args="[entityName]" /></h4>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                              %{--   <g:sortableColumn property="designation.jobTitleName" title="${message(code: 'employee.designation.label', default: 'Designation')}" />--}%
                            <g:sortableColumn property="fatherName" title="${message(code: 'employeeDependents.fatherName.label', default: 'Father Name')}" />
                        
                            <g:sortableColumn property="fatherDob" title="${message(code: 'employeeDependents.fatherDob.label', default: 'Year of Birth')}" />
                        
                            <g:sortableColumn property="fatherAddress" title="${message(code: 'employeeDependents.fatherAddress.label', default: 'Father Address')}" />
                        
                            <g:sortableColumn property="motherName" title="${message(code: 'employeeDependents.motherName.label', default: 'Mother Name')}" />
                        
                            <g:sortableColumn property="motherAge" title="${message(code: 'employeeDependents.motherAge.label', default: 'Mother Age')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${employeeDependentsInstanceList}" status="i" var="employeeDependentsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            %{--<td id="linked" ><g:link action="show" id="${employeeDependentsInstance.id}">${fieldValue(bean: employeeDependentsInstance, field: "id")}</g:link></td>--}%
                        
                            <td>${fieldValue(bean: employeeDependentsInstance, field: "fatherName")}</td>
                        
                            <td>${fieldValue(bean: employeeDependentsInstance, field: "fatherDob")}</td>
                        
                            <td>${fieldValue(bean: employeeDependentsInstance, field: "fatherAddress")}</td>
                        
                            <td>${fieldValue(bean: employeeDependentsInstance, field: "motherName")}</td>
                        
                            <td>${fieldValue(bean: employeeDependentsInstance, field: "motherAge")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${employeeDependentsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
