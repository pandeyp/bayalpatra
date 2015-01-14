<%@ page import="commons.BayalpatraConstants; com.bayalpatra.hrm.HrmEmployeeAccount" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="Create Employee Account Detail" />
        <title>${entityName}</title>
    </head>
    <body>
        
        <div class="body">
            <h4>${entityName}</h4>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${hrmEmployeeAccountInstance}">
            <div class="errors">
                <g:renderErrors bean="${hrmEmployeeAccountInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name" width="14%">
                                    <label for="employee"><g:message code="hrmEmployeeAccount.employee.label" default="Employee" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: hrmEmployeeAccountInstance, field: 'employee', 'errors')}">
                                    <g:select name="employee.id" from="${com.bayalpatra.hrm.Employee.findAllByStatusNotEqual(BayalpatraConstants.CLEARED)}" optionKey="id" value="${hrmEmployeeAccountInstance?.employee?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="accountNumber"><g:message code="hrmEmployeeAccount.accountNumber.label" default="Account #" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: hrmEmployeeAccountInstance, field: 'accountNumber', 'errors')}">
                                    <g:textField name="accountNumber" value="${hrmEmployeeAccountInstance?.accountNumber}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="citNumber"><g:message code="hrmEmployeeAccount.citNumber.label" default="CIT #" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: hrmEmployeeAccountInstance, field: 'citNumber', 'errors')}">
                                    <g:textField name="citNumber" value="${hrmEmployeeAccountInstance?.citNumber}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="panNumber"><g:message code="hrmEmployeeAccount.panNumber.label" default="Pan #" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: hrmEmployeeAccountInstance, field: 'panNumber', 'errors')}">
                                    <g:textField name="panNumber" value="${hrmEmployeeAccountInstance?.panNumber}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="pfNumber"><g:message code="hrmEmployeeAccount.pfNumber.label" default="Pf #" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: hrmEmployeeAccountInstance, field: 'pfNumber', 'errors')}">
                                    <g:textField name="pfNumber" value="${hrmEmployeeAccountInstance?.pfNumber}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="createbuttons" style="margin:0 0 0 343px">
                    <g:submitButton name="create"  class="savebutton" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </div>
            </g:form>
        </div>
    </body>
</html>
