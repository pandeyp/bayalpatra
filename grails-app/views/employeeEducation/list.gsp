
<%@ page import="com.bayalpatra.hrm.EmployeeEducation" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'employeeEducation.label', default: 'Employee Education')}" />
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
                        
                            %{--<g:sortableColumn property="id" title="${message(code: 'employeeEducation.id.label', default: 'Id')}" />--}%
                        
                            <g:sortableColumn property="degree" title="${message(code: 'employeeEducation.degree.label', default: 'Degree')}" />
                        
                            <g:sortableColumn property="college" title="${message(code: 'employeeEducation.college.label', default: 'College')}" />
                        
                            <g:sortableColumn property="date" title="${message(code: 'employeeEducation.date.label', default: 'Date')}" />
                        
                            <th><g:message code="employeeEducation.employee.label" default="Employee" /></th>
                        
                            <g:sortableColumn property="remarks" title="${message(code: 'employeeEducation.remarks.label', default: 'Remarks')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${employeeEducationInstanceList}" status="i" var="employeeEducationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            %{--<td id="linked"><g:link action="show" id="${employeeEducationInstance.id}">${fieldValue(bean: employeeEducationInstance, field: "id")}</g:link></td>--}%
                        
                            <td>${fieldValue(bean: employeeEducationInstance, field: "degree")}</td>
                        
                            <td>${fieldValue(bean: employeeEducationInstance, field: "college")}</td>
                        
                            <td><g:formatDate format="yyyy-MM-dd" date="${employeeEducationInstance?.date}" /></td>
                        
                            <td>${fieldValue(bean: employeeEducationInstance, field: "employee")}</td>
                        
                            <td>${fieldValue(bean: employeeEducationInstance, field: "remarks")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${employeeEducationInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
