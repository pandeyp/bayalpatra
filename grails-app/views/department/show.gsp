
<%@ page import="com.bayalpatra.hrm.Department" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'departments.label', default: 'Departments')}" />
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

                        %{--<tr class="prop">
                            <td valign="top" class="name"><g:message code="departments.id.label" default="Id" /></td>

                            <td valign="top" class="value">${fieldValue(bean: departmentsInstance, field: "id")}</td>

                        </tr>--}%

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="departments.name.label" default="Name" /></td>

                            <td valign="top" class="value">${fieldValue(bean: departmentsInstance, field: "name")}</td>

                        </tr>
                    
                       %{-- <tr class="prop">
                            <td valign="top" class="name"><g:message code="departments.company.label" default="Company" /></td>

                            <td valign="top" class="value"><g:link controller="company" action="show" id="${departmentsInstance?.company?.id}">${departmentsInstance?.company?.encodeAsHTML()}</g:link></td>
                        </tr>
--}%
                     %{--   <tr class="prop">
                            <td valign="top" class="name"><g:message code="departments.employee.label" default="Employee" /></td>

                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${departmentsInstance.employee}" var="e">
                                    <li><g:link controller="employee" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>

                        </tr>--}%

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="departments.parentId.label" default="Parent Name" /></td>

                            <td valign="top" class="value">${pName}</td>

                        </tr>

                    </tbody>
                </table>
            </div>
            <div class="formbuttons" align="left" >
                <g:form>
                    <g:hiddenField name="id" value="${departmentsInstance?.id}" />
                    
                </g:form>
            </div>
        </div>
    </body>
</html>
