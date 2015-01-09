
<%@ page import="com.bayalpatra.hrm.EmployeeTraining" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'employeeTraining.label', default: 'EmployeeTraining')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h4><g:message code="default.show.label" args="[entityName]" /></h4>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeTraining.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeTrainingInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeTraining.title.label" default="Title" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeTrainingInstance, field: "title")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeTraining.startDate.label" default="Start Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${employeeTrainingInstance?.startDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeTraining.endDate.label" default="End Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${employeeTrainingInstance?.endDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeTraining.boundPeriodFrom.label" default="Bound Period From" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${employeeTrainingInstance?.boundPeriodFrom}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeTraining.boundPeriodTo.label" default="Bound Period To" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${employeeTrainingInstance?.boundPeriodTo}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeTraining.employee.label" default="Employee" /></td>
                            
                            <td valign="top" class="value"><g:link controller="employee" action="show" id="${employeeTrainingInstance?.employee?.id}">${employeeTrainingInstance?.employee?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="formbuttons">
                <g:form>
                    <g:hiddenField name="id" value="${employeeTrainingInstance?.id}" />
                    <span class="button"><g:actionSubmit class="editbutton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    %{--<span class="button"><g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>--}%
                </g:form>
            </div>
        </div>
    </body>
</html>
