

<%@ page import="com.bayalpatra.hrm.EmployeeTraining" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="layout" content="main_hrm" />
  <g:set var="entityName" value="${message(code: 'employeeTraining.label', default: 'Employee Training')}" />
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
    <g:hasErrors bean="${employeeTrainingInstance}">
      <div class="errors">
        <g:renderErrors bean="${employeeTrainingInstance}" as="list" />
      </div>
    </g:hasErrors>

    <g:form action="save" >
      <div class="dialog">
        <table>
          <tbody>
          <tr class="prop">
            <td valign="top" class="name">
              <label for="title"><g:message code="employeeTraining.title.label" default="Title *" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: employeeTrainingInstance, field: 'title', 'errors')}">
              <g:textField name="title" value="${employeeTrainingInstance?.title}"  />
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="startDate"><g:message code="employeeTraining.startDate.label" default="Start Date *" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: employeeTrainingInstance, field: 'startDate', 'errors')}">
              <g:set var="startYr" value="${new GregorianCalendar().get(Calendar.YEAR)-50}"/>
              <g:set var="endYr" value="${new GregorianCalendar().get(Calendar.YEAR) + 50}"/>
              <g:datePicker id = "startDate" name="startDate" precision="day" value="${employeeTrainingInstance?.startDate}" years="${startYr..endYr}" />
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="endDate"><g:message code="employeeTraining.endDate.label" default="End Date *" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: employeeTrainingInstance, field: 'endDate', 'errors')}">
              <g:set var="startYr" value="${new GregorianCalendar().get(Calendar.YEAR)-50}"/>
              <g:set var="endYr" value="${new GregorianCalendar().get(Calendar.YEAR) + 50}"/>
              <g:datePicker id = "endDate" name="endDate" precision="day" value="${employeeTrainingInstance?.endDate}" years="${startYr..endYr}" />
            </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="boundPeriodFrom"><g:message code="employeeTraining.boundPeriodFrom.label" default="Bound Period From" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: employeeTrainingInstance, field: 'boundPeriodFrom', 'errors')}">
              %{--<g:set var="nowYr" value="${new Date()}"/>--}%
              <g:set var="startYr" value="${new GregorianCalendar().get(Calendar.YEAR)-60}"/>
              <g:set var="endYr" value="${new GregorianCalendar().get(Calendar.YEAR) + 40}"/>
              <g:datePicker name="boundPeriodFrom" precision="day" value="${employeeTrainingInstance?.boundPeriodFrom}" years="${startYr..endYr}" />

              %{--<g:datePicker id = "boundPeriodFrom" name="boundPeriodFrom" precision="day" value="${employeeTrainingInstance?.boundPeriodFrom}" years="${2011..1976}" />--}%
            </td>
          </tr>





          <tr class="prop">
            <td valign="top" class="name">
              <label for="boundPeriodTo"><g:message code="employeeTraining.boundPeriodTo.label" default="Bound Period To" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: employeeTrainingInstance, field: 'boundPeriodTo', 'errors')}">
              <g:set var="startYr" value="${new GregorianCalendar().get(Calendar.YEAR)-60}"/>
              <g:set var="endYr" value="${new GregorianCalendar().get(Calendar.YEAR) + 40}"/>

              <g:datePicker name="boundPeriodTo" precision="day" value="${employeeTrainingInstance?.boundPeriodTo}" years="${startYr..endYr}" />
            </td>
          </tr>
           %{--  <tr class="prop">
              <td valign="top" class="name">
                  <label for="employee"><g:message code="employeeTraining.employee.label" default="Employee" /></label>
              </td>
              <td valign="top" class="value ${hasErrors(bean: employeeTrainingInstance, field: 'employee', 'errors')}">
                  <g:select name="employee.id" from="${hrm.Employee.list()}" optionKey="id" value="${employeeTrainingInstance?.employee?.id}"  />
              </td>
          </tr>--}%

          %{--<g:hiddenField name="employee" value="${employeeTrainingInstance?.employee?.id}"></g:hiddenField>--}%

          <g:hiddenField name="employee.id" value="${employeeInstance.id}"/>

          </tbody>
        </table>
          <div class="employee_education">
            <g:submitButton name="create" class="savebutton" value="${message(code: 'default.button.create.label', default: 'Create')}" />
          </div>
      </div>

      <g:if test="${empTrainingList}">

        <p align="right" class="printthis">
          <a href="#">
            <img src="${resource(dir: 'images', file: "print_icon.png")}" alt="Print Table" onclick="window.print()" >
          </a>
        </p>


        <h3><g:message code="default.list.label" args="[entityName]" /></h3>
        <br>
        <div class="list" id="empTrainingList" >
          <table>

            <thead>
            <tr>
              <th>Training</th>
              <th>Start Date</th>
              <th>End Date</th>
              <th>Bound Period From</th>
              <th>Bound Period To</th>
              <th><em>Action</em></th>
              %{--<th>Compulsary Days</th>--}%
            </tr>
            </thead>

            <tbody>

            <g:each in="${empTrainingList}" status="i" var="employeeTraining">
              <tr>
                <td>${employeeTraining?.title}</td>
                <td><g:formatDate format="yyyy-MM-dd" date="${employeeTraining?.startDate}" /></td>
                <td><g:formatDate format="yyyy-MM-dd" date="${employeeTraining?.endDate}" /></td>
                <td><g:formatDate format="yyyy-MM-dd" date="${employeeTraining?.boundPeriodFrom}" /></td>
                <td><g:formatDate format="yyyy-MM-dd" date="${employeeTraining?.boundPeriodTo}" /></td>
                %{--<td>${fieldValue(bean: employeeTraining, field: "compulsaryDays")}</td>--}%
                <td id="linked" >
                  <g:link controller="employeeTraining" action="edit" id="${employeeTraining?.id}"><em>Edit</em></g:link>
                %{--<g:link controller="employeeTraining" action="delete" id="${employeeTraining?.id}"><em>Delete</em></g:link>--}%


                </td>

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



  