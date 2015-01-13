
<%@ page import="com.bayalpatra.hrm.Salary" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'salary.label', default: 'Salary')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        %{--<div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>--}%
        <div class="body">
            <h4><g:message code="default.show.label" args="[entityName]" /></h4>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="salary.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: salaryInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="salary.level.label" default="Level" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: salaryInstance, field: "level")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="salary.designation.label" default="Designation" /></td>
                            
                            <td valign="top" class="value"><g:link controller="designation" action="show" id="${salaryInstance?.designation?.id}">${salaryInstance?.designation?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="salary.startSalary.label" default="Start Salary" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: salaryInstance, field: "startSalary")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="formbuttons">
                <g:form>
                    <g:hiddenField name="id" value="${salaryInstance?.id}" />
                    <span class="button"><g:actionSubmit class="editbutton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    %{--<span class="button"><g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>--}%
                </g:form>
            </div>
        </div>
    </body>
</html>
