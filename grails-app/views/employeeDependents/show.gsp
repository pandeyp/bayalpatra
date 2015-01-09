
<%@ page import="com.bayalpatra.hrm.EmployeeDependents" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'employeeDependents.label', default: 'EmployeeDependents')}" />
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
                            <td valign="top" class="name"><g:message code="employeeDependents.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.fatherName.label" default="Father Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "fatherName")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.fatherDob.label" default="Father's Year of Birth" /></td>

                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "fatherDob")}</td>

                        </tr>

                        %{--<tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.fatherAge.label" default="Father Age" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "fatherAge")}</td>
                            
                        </tr>--}%
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.fatherAddress.label" default="Father Address" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "fatherAddress")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.motherName.label" default="Mother Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "motherName")}</td>
                            
                        </tr>
                    
                       <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.motherDob.label" default="Mother's Year of Birth" /></td>

                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "motherDob")}</td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.spouseName.label" default="Spouse Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "spouseName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.spouseDob.label" default="Spouse's Year of Birth" /></td>

                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "spouseDob")}</td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.spouseAddress.label" default="Spouse Address" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "spouseAddress")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.childName1.label" default="Child Name1" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "childName1")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.child1Dob.label" default="Child 1: Year of Birth" /></td>

                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "child1Dob")}</td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.childName2.label" default="Child Name2" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "childName2")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.child2Dob.label" default="Child 2: Year of Birth" /></td>

                            <td valign="top" class="value">${fieldValue(bean: employeeDependentsInstance, field: "child2Dob")}</td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeeDependents.employee.label" default="Employee" /></td>
                            
                            <td valign="top" class="value"><g:link controller="employee" action="show" id="${employeeDependentsInstance?.employee?.id}">${employeeDependentsInstance?.employee?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="formbuttons" align="left" >
                <g:form>
                    <g:hiddenField name="id" value="${employeeDependentsInstance?.id}" />
                    <g:actionSubmit class="editbutton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" />
                    <g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </g:form>
            </div>
        </div>
    </body>
</html>
