

<%@ page import="com.bayalpatra.hrm.EmployeeEducation" %>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <meta name="layout" content="main_hrm" />
      <g:set var="entityName" value="${message(code: 'employeeEducation.label', default: 'Employee Education')}" />
      <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
%{--<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
</div>--}%
<div class="body">

      <h4 class="notforprint"><g:message code="default.create.label" args="[entityName]" /></h4>
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
            <g:form action="save" >
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

                              %{--<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="percentage"><g:message code="employeeEducation.percentage.label" default="Percentage" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeEducationInstance, field: 'percentage', 'errors')}">
                                  <g:textField name="percentage" value="${employeeEducationInstance?.percentage}" />
                                </td>
                              </tr>--}%

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

                              <tr class="prop">
                                    <td valign="top" class="name">
                                          <label for="remarks"><g:message code="employeeEducation.remarks.label" default="Remarks" /></label>
                                    </td>
                                    <td valign="top" class="value ${hasErrors(bean: employeeEducationInstance, field: 'remarks', 'errors')}">
                                          <g:textField name="remarks" value="${employeeEducationInstance?.remarks}" />
                                    </td>
                              </tr>


                              %{--<g:hiddenField name="employee.id" value="${employeeEducationInstance?.employee?.id}"></g:hiddenField>--}%

                              <g:hiddenField name="employee.id" value="${employeeInstance.id}" />

                              %{--<tr class="prop">--}%
                              %{--<td valign="top" class="name">--}%
                              %{--<label for="employee.id"><g:message code="employeeEducation.employee.label" default="Employee" /></label>--}%
                              %{--</td>--}%
                              %{--<td valign="top" class="value ${hasErrors(bean: employeeEducationInstance, field: 'employee', 'errors')}">--}%
                              %{--<g:select name="employee.id" from="${hrm.Employee.list()}" optionKey="id" value="${employeeEducationInstance?.employee?.id}"  />--}%
                              %{--</td>--}%
                              %{--</tr>--}%

                              </tbody>
                        </table>

                              <div class="employee_education" align="left" >
                                    <g:submitButton name="create"  class="savebutton" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                              </div>
                  </div>

                  <g:if test="${empEducationList}">

                        <p align="right" class="printthis">
                              <a href="#">   <img src="${resource(dir: 'images', file: "print_icon.png")}" alt="Print Table" onclick="window.print()" >
                              </a>
                        </p>



                        <h3><g:message code="default.list.label" args="[entityName]" /></h3>
                        <br>
                        <div class="list" id="empEducationList" >
                              <table>

                                    <thead>
                                    <tr>
                                          <th>Degree</th>
                                          <th>College</th>
                                          <th>Date</th>
                                          <th>Action</th>

                                          %{--<th>Date</th>--}%
                                    </tr>
                                    </thead>

                                    <tbody>

                                    <g:each in="${empEducationList}" status="i" var="employeeEducation">
                                          <tr>
                                                <td>${fieldValue(bean: employeeEducation, field: "degree")}</td>
                                                <td>${fieldValue(bean: employeeEducation, field: "college")}</td>
                                                <td><g:formatDate format="yyyy-MM-dd" date="${employeeEducation?.date}" /></td>
                                                %{--format="MMM d, ''yy"--}%
                                                %{--<td><g:formatDate format="yyyy-MM-dd" date="${fieldValue(bean: employeeEducationInstance, field: "date")}" /></td>--}%
                                                <td id="linked">
                                                      %{--<g:hiddenField name="employee.id" value="${employeeEducationInstance?.employee?.id}"></g:hiddenField>--}%
                                                      <g:link controller="employeeEducation" action="edit" params="['educationIs':employeeEducation?.id]"><em>Edit</em></g:link></td>
                                                        %{--id="${employeeEducation?.id}"--}%
                                          </tr>

                                    </g:each>

                                    </tbody>

                              </table>
                        </div>

                  </g:if>


            </g:form>
      </div>
      </div>
</div>
</body>
</html>
