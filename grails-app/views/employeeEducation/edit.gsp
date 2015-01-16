

<%@ page import="com.bayalpatra.hrm.EmployeeEducation" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'employeeEducation.label', default: 'Employee Education')}" />
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
              <g:render template='/employee/menu'></g:render>
        <div id="content-wrap">
              <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
          <div class="personal_detail">
          <g:hasErrors bean="${employeeEducationInstance}">
            <div class="errors">
                <g:renderErrors bean="${employeeEducationInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="educationIs" value="${employeeEducationInstance?.id}" />
                <g:hiddenField name="version" value="${employeeEducationInstance?.version}" />
              <g:hiddenField name="employee.id" value="${employeeEducationInstance?.employee?.id}"></g:hiddenField>
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="degree"><g:message code="employeeEducation.degree.label" default="Degree *" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeEducationInstance, field: 'degree', 'errors')}">
                                    <g:textField name="degree" value="${employeeEducationInstance?.degree}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="college"><g:message code="employeeEducation.college.label" default="School/College *" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeEducationInstance, field: 'college', 'errors')}">
                                    <g:textField name="college" value="${employeeEducationInstance?.college}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="date"><g:message code="employeeEducation.date.label" default="Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeEducationInstance, field: 'date', 'errors')}">
                                  <g:set var="startYr" value="${new GregorianCalendar().get(Calendar.YEAR)-60}"/>
                                  <g:set var="endYr" value="${new GregorianCalendar().get(Calendar.YEAR)+5}"/>

                                  <g:datePicker name="date" precision="day" value="${employeeEducationInstance?.date}" years="${startYr..endYr}"  />
                                </td>
                            </tr>
                        
                            %{--<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="employee.id"><g:message code="employeeEducation.employee.label" default="Employee" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeEducationInstance, field: 'employee', 'errors')}">
                                    <g:select name="employee.id" from="${hrm.Employee.list()}" optionKey="id" value="${employeeEducationInstance?.employee?.id}"  />
                                </td>
                            </tr>--}%
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="remarks"><g:message code="employeeEducation.remarks.label" default="Remarks" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeEducationInstance, field: 'remarks', 'errors')}">
                                    <g:textField name="remarks" value="${employeeEducationInstance?.remarks}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="employee_education" align="left">
                    <g:actionSubmit class="savebutton" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}"/>
                    %{--<g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
                </div>
            </g:form>
                </div>
            </div>
        </div>
    </body>
</html>
