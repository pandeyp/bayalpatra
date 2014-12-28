
<%@ page import="com.bayalpatra.hrm.LeaveType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'leaveType.label', default: 'LeaveType')}" />
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
                            <td valign="top" class="name"><g:message code="leaveType.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: leaveTypeInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="leaveType.leaveType.label" default="Leave Type" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: leaveTypeInstance, field: "leaveType")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="leaveType.status.label" default="Status" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: leaveTypeInstance, field: "status")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="leaveType.paidUnpaid.label" default="Paid Unpaid" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: leaveTypeInstance, field: "paidUnpaid")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="leaveType.days.label" default="Days" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: leaveTypeInstance, field: "days")}</td>
                            
                        </tr>
                    
                       
                    </tbody>
                </table>
            </div>
            <div class="formbuttons">
                <g:form>
                    <g:hiddenField name="id" value="${leaveTypeInstance?.id}" />
                    <span class="button"><g:actionSubmit class="editbutton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
