
<%@ page import="com.bayalpatra.hrm.EmployeeLeaveDetail" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'employeeLeaveDetail.label', default: 'EmployeeLeaveDetail')}" />
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
                            <td valign="top" class="name"><g:message code="employeeLeaveDetail.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeLeaveDetailInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeLeaveDetail.employee.label" default="Employee" /></td>
                            
                            <td valign="top" class="value"><g:link controller="employee" action="show" id="${employeeLeaveDetailInstance?.employee?.id}">${employeeLeaveDetailInstance?.employee?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeLeaveDetail.fromDate.label" default="From Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${employeeLeaveDetailInstance?.fromDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeLeaveDetail.leaveType.label" default="Leave Type" /></td>
                            
                            <td valign="top" class="value"><g:link controller="leaveType" action="show" id="${employeeLeaveDetailInstance?.leaveType?.id}">${employeeLeaveDetailInstance?.leaveType?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeLeaveDetail.toDate.label" default="To Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${employeeLeaveDetailInstance?.toDate}" /></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="formbuttons">
                <g:form>
                    <g:hiddenField name="id" value="${employeeLeaveDetailInstance?.id}" />
                    <span class="button"><g:actionSubmit class="editbutton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
