
<%@ page import="com.bayalpatra.hrm.Employee" %>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <meta name="layout" content="main_hrm" />
      <g:set var="entityName" value="${message(code: 'employee.label', default: 'Employee')}" />
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
                        <td valign="top" class="name"><g:message code="employee.id.label" default="Id" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "id")}</td>

                  </tr>


                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.supervisor.label" default="Supervisor" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "supervisor")}</td>

                  </tr>
                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.lastName.label" default="Last Name" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "lastName")}</td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.firstName.label" default="First Name" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "firstName")}</td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.middleName.label" default="Middle Name" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "middleName")}</td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.nationality.label" default="Nationality" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "nationality")}</td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.dateOfBirth.label" default="Date Of Birth" /></td>
                        <td valign="top" class="value"><g:formatDate format="yyyy-MM-dd" date="${employeeInstance?.dateOfBirth}" /></td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.maritalStatus.label" default="Marital Status" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "maritalStatus")}</td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.gender.label" default="Gender" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "gender")}</td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.country.label" default="Country" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "country")}</td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.permanentAddress.label" default="Permanent Address" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "permanentAddress")}</td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.temporaryAddress.label" default="Temporary Address" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "temporaryAddress")}</td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.homePhone.label" default="Home Phone" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "homePhone")}</td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.workPhone.label" default="Work Phone" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "workPhone")}</td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.mobile.label" default="Mobile" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "mobile")}</td>

                  </tr>


                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.email.label" default="Email" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "email")}</td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.alterEmail.label" default="Alternate Email" /></td>

                        <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "alterEmail")}</td>

                  </tr>

                  %{--<tr class="prop">
                      <td valign="top" class="name"><g:message code="employee.joinDate.label" default="Join Date" /></td>

                      <td valign="top" class="value"><g:formatDate date="${employeeInstance?.joinDate}" format="yyyy-MM-dd"  /></td>

                  </tr>--}%



                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.departments.label" default="Departments" /></td>

                        <td valign="top" class="value"><g:link controller="departments" action="show" id="${employeeInstance?.departments?.id}">${employeeInstance?.departments?.encodeAsHTML()}</g:link></td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.designation.label" default="Designation" /></td>

                        <td valign="top" class="value"><g:link controller="designation" action="show" id="${employeeInstance?.designation?.id}">${employeeInstance?.designation?.encodeAsHTML()}</g:link></td>

                  </tr>

                  <tr class="prop">
                        <td valign="top" class="name"><g:message code="employee.salaryclass.label" default="salary Group" /></td>

                        <td valign="top" class="value"><g:link controller="salaryClass" action="show" id="${employeeInstance?.salaryclass?.id}">${employeeInstance?.salaryclass?.encodeAsHTML()}</g:link></td>

                  </tr>

                  %{--<tr class="prop">
                      <td valign="top" class="name"><g:message code="employee.empEducation.label" default="Emp Education" /></td>

                      <td valign="top" style="text-align: left;" class="value">
                          <ul>
                          <g:each in="${employeeInstance.empEducation}" var="e">
                              <li><g:link controller="empEducation" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
                          </g:each>
                          </ul>
                      </td>

                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name"><g:message code="employee.employeeContracts.label" default="Employee Contracts" /></td>

                      <td valign="top" style="text-align: left;" class="value">
                          <ul>
                          <g:each in="${employeeInstance.employeeContracts}" var="e">
                              <li><g:link controller="employeeContracts" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
                          </g:each>
                          </ul>
                      </td>

                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name"><g:message code="employee.employeeDependents.label" default="Employee Dependents" /></td>

                      <td valign="top" style="text-align: left;" class="value">
                          <ul>
                          <g:each in="${employeeInstance.employeeDependents}" var="e">
                              <li><g:link controller="employeeDependents" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
                          </g:each>
                          </ul>
                      </td>

                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name"><g:message code="employee.employeeLeave.label" default="Employee Leave" /></td>

                      <td valign="top" style="text-align: left;" class="value">
                          <ul>
                          <g:each in="${employeeInstance.employeeLeave}" var="e">
                              <li><g:link controller="employeeLeave" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
                          </g:each>
                          </ul>
                      </td>

                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name"><g:message code="employee.employeeSalary.label" default="Employee Salary" /></td>

                      <td valign="top" style="text-align: left;" class="value">
                          <ul>
                          <g:each in="${employeeInstance.employeeSalary}" var="e">
                              <li><g:link controller="employeeSalary" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
                          </g:each>
                          </ul>
                      </td>

                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name"><g:message code="employee.employeeSkills.label" default="Employee Skills" /></td>

                      <td valign="top" style="text-align: left;" class="value">
                          <ul>
                          <g:each in="${employeeInstance.employeeSkills}" var="e">
                              <li><g:link controller="employeeSkills" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
                          </g:each>
                          </ul>
                      </td>

                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name"><g:message code="employee.employeeTraining.label" default="Employee Training" /></td>

                      <td valign="top" style="text-align: left;" class="value">
                          <ul>
                          <g:each in="${employeeInstance.employeeTraining}" var="e">
                              <li><g:link controller="employeeTraining" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
                          </g:each>
                          </ul>
                      </td>

                  </tr>

                  <tr class="prop">
                      <td valign="top" class="name"><g:message code="employee.employeeWorkExperiece.label" default="Employee Work Experiece" /></td>

                      <td valign="top" style="text-align: left;" class="value">
                          <ul>
                          <g:each in="${employeeInstance.employeeWorkExperiece}" var="e">
                              <li><g:link controller="employeeWorkExperience" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
                          </g:each>
                          </ul>
                      </td>

                  </tr>--}%

                  </tbody>
            </table>
      </div>
      <div class="formbuttons">
            <g:form>
                  <g:hiddenField name="id" value="${employeeInstance?.id}" />
                  <span class="button"><g:actionSubmit class="editbutton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                  %{--<span class="button"><g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>--}%
            </g:form>
      </div>
</div>
</body>
</html>
