

<%@ page import="com.bayalpatra.hrm.Supervisor" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'supervisor.label', default: 'Supervisor')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>

        <div class="body">
            <h4><g:message code="default.create.label" args="[entityName]" /></h4>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${supervisorInstance}">
            <div class="errors">
                <g:renderErrors bean="${supervisorInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="employee.id"><g:message code="supervisor.employee.label" default="Select Supervisor" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: supervisorInstance, field: 'employee', 'errors')}">
                                    <g:select name="employee.id" from="${employee}" optionKey="id" value="${supervisorInstance?.employee?.id}"  />
                                %{--optionValue="${{it.firstName +' ' + it.middleName+' '+ it.lastName+' - '+it.employeeId}}"--}%
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="createbuttons">
                    <g:submitButton name="create"  class="savebutton" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </div>
            </g:form>
        </div>
    </body>
</html>
