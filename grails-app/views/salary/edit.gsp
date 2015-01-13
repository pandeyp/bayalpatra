

<%@ page import="com.bayalpatra.hrm.Salary" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'salary.label', default: 'Salary')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        %{--<div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>--}%
        <div class="body">
            <h4><g:message code="default.edit.label" args="[entityName]" /></h4>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${salaryInstance}">
            <div class="errors">
                <g:renderErrors bean="${salaryInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${salaryInstance?.id}" />
                <g:hiddenField name="version" value="${salaryInstance?.version}" />
                <g:hiddenField name="offset" value="${offset}"/>
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="level"><g:message code="salary.level.label" default="Level *" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: salaryInstance, field: 'level', 'errors')}">
                                    <g:textField name="level" value="${salaryInstance?.level}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="designation.id"><g:message code="salary.designation.label" default="Designation *" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: salaryInstance, field: 'designation', 'errors')}">
                                    <g:select name="designation.id" from="${com.bayalpatra.hrm.Designation.list()}" optionKey="id" value="${salaryInstance?.designation?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="basicSalary"><g:message code="salary.startSalary.label" default="Basic Salary *" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: salaryInstance, field: 'startSalary', 'errors')}">
                                    <g:textField name="basicSalary" value="${fieldValue(bean: salaryInstance, field: 'basicSalary')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="createbuttons">
                    <g:actionSubmit class="savebutton" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                    %{--<g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
                </div>
            </g:form>
        </div>
    </body>
</html>
