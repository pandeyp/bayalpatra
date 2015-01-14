
<%@ page import="com.bayalpatra.hrm.HrmEmployeeAccount" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'hrmEmployeeAccount.label', default: 'HrmEmployeeAccount')}" />
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
                            <td valign="top" class="name"><g:message code="hrmEmployeeAccount.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: hrmEmployeeAccountInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="hrmEmployeeAccount.employee.label" default="Employee" /></td>
                            
                            <td valign="top" class="value"><g:link controller="employee" action="show" id="${hrmEmployeeAccountInstance?.employee?.id}">${hrmEmployeeAccountInstance?.employee?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="hrmEmployeeAccount.accountNumber.label" default="Account Number" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: hrmEmployeeAccountInstance, field: "accountNumber")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="hrmEmployeeAccount.cit.label" default="Cit" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: hrmEmployeeAccountInstance, field: "cit")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="hrmEmployeeAccount.citNumber.label" default="Cit Number" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: hrmEmployeeAccountInstance, field: "citNumber")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="hrmEmployeeAccount.insurancePremiumAmount.label" default="Insurance Premium Amount" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: hrmEmployeeAccountInstance, field: "insurancePremiumAmount")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="hrmEmployeeAccount.panNumber.label" default="Pan Number" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: hrmEmployeeAccountInstance, field: "panNumber")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="hrmEmployeeAccount.pfNumber.label" default="Pf Number" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: hrmEmployeeAccountInstance, field: "pfNumber")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="formbuttons">
                <g:form>
                    <g:hiddenField name="id" value="${hrmEmployeeAccountInstance?.id}" />
                    <span class="button"><g:actionSubmit class="editbutton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
