<%@ page import="com.bayalpatra.hrm.Employee" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <export:resource />
    <g:set var="entityName" value="${message(code: 'employee.label', default: 'Probation Completed Employee')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="body">
    <div align="right" class="printthis">
        <export:formats formats="['excel']" params="" title="Export to Excel"/>
        <div class="print"><a href="#"title="print">
            <img src="${resource(dir: 'images', file: "print_icon.png")}" alt="Print Table" onclick="window.print()" >
        </a>
        </div>
    </div>
    <h4><g:message code="default.list.label" args="[entityName]" /></h4>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <br>
    <div id="content-wrap">
        <div id="grid">
            <table  width="100%" cellspacing="0" cellpadding="0" border="0" class="gtable">
                <thead>
                <tr>

                    <g:sortableColumn property="id" title="${message(code: 'employee.id.label', default: 'Employee Id')}" />

                    <g:sortableColumn property="firstName" title="${message(code: 'employee.firstName.label', default: 'Name')}" />

                    <th><g:message code="employee.departments.label" default="Department" /></th>

                    <g:sortableColumn property="volunteerDays" title="${message(code: 'employee.volunteerDays.label', default: 'Days of Probation')}" />



                </tr>
                </thead>
                <tbody>

                <g:each in="${employeeInstanceList}" status="i" var="employeeInstance">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                        <td>${fieldValue(bean: employeeInstance, field: "employeeId")}</td>

                        <td id="linked"><g:link action="edit" id="${employeeInstance.id}" params="[offset:params.offset,flag:true]">${fieldValue(bean: employeeInstance, field: "firstName")} ${fieldValue(bean: employeeInstance, field: "middleName")} ${fieldValue(bean: employeeInstance, field: "lastName")}</g:link></td>


                        <td>${fieldValue(bean: employeeInstance, field: "departments")}</td>


                    %{--<td>${fieldValue(bean: employeeInstance, field: "joinDate")}</td>--}%
                        <td>${fieldValue(bean: employeeInstance, field: "volunteerDays")}</td>


                    </tr>
                </g:each>
                </tbody>
            </table>

        </br>
           %{-- <div class="paginateButtons">
                <g:paginate  total="${employeeInstanceTotal}" action="termedEmployeeList" params="[offset:offset]" />
            </div>--}%
        </div>
    </div>

</div>
</body>
</html>