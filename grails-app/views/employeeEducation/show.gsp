
<%@ page import="com.bayalpatra.hrm.EmployeeEducation" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'employeeEducation.label', default: 'EmployeeEducation')}" />
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
                            <td valign="top" class="name"><g:message code="employeeEducation.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeEducationInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeEducation.degree.label" default="Degree" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeEducationInstance, field: "degree")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeEducation.college.label" default="College" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeEducationInstance, field: "college")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeEducation.date.label" default="Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd" date="${employeeEducationInstance?.date}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeEducation.employee.label" default="Employee" /></td>
                            
                            <td valign="top" class="value"><g:link controller="employee" action="show" id="${employeeEducationInstance?.employee?.id}">${employeeEducationInstance?.employee?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeEducation.remarks.label" default="Remarks" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeEducationInstance, field: "remarks")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="formbuttons" align="left" >
                <g:form>
                    <g:hiddenField name="id" value="${employeeEducationInstance?.id}" />
                    <g:actionSubmit class="editbutton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" />
                    %{--<g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
                </g:form>
            </div>
        </div>
    </body>
</html>
