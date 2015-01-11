

<%@ page import="commons.BayalpatraConstants; com.bayalpatra.hrm.EmployeeLeaveDetail" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="layout" content="main_hrm" />
  <g:set var="entityName" value="${message(code: 'employeeLeaveDetail.label', default: 'Employee Leave Detail')}" />
  <title><g:message code="default.edit.label" args="[entityName]" /></title>

  <g:javascript library="jquery" plugin="jquery"/>
  <jqui:resources/>

  <script type="text/javascript">

    $(document).ready(function(){
      if(${leaveDay==0}){
        $("#whichHalf").hide();
      }

    });

    function checkApproval(){

      if($('#approve').attr('checked') && $('#deny').attr('checked')){
        alert('')
        return false
      }

    }

    function getHalf(){
      var leaveDays = $("#leaveDays").val()
      if(leaveDays==0.5){
        $("#whichHalf").show()
      }else{
        $("#leave_first").attr("checked",false);
        $("#leave_second").attr("checked",false);
        $("#whichHalf").hide()
      }

    }

  </script>

</head>
<body>
<div class="body">
  <h4><g:message code="default.edit.label" args="[entityName]" /></h4>
  <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
  </g:if>
  <g:hasErrors bean="${employeeLeaveDetailInstance}">
    <div class="errors">
      <g:renderErrors bean="${employeeLeaveDetailInstance}" as="list" />
    </div>
  </g:hasErrors>
  <g:form method="post" onSubmit="return checkApproval();">
    <g:hiddenField name="id" value="${employeeLeaveDetailInstance?.id}" />
    <g:hiddenField name="version" value="${employeeLeaveDetailInstance?.version}" />
    <g:hiddenField name="offset" value="${offset}"/>
    <div class="dialog">
      <table>
        <tbody>

        <tr class="prop">
          <td valign="top" class="name">
            <label for="employee.id"><g:message code="employeeLeaveDetail.employee.label" default="Employee" /></label>
          </td>

          <td valign="top" class="value ${hasErrors(bean: employeeLeaveDetailInstance, field: 'employee', 'errors')}">
            ${employeeLeaveDetailInstance.employee.firstName} ${employeeLeaveDetailInstance.employee.lastName}
          </td>

        </tr>

        <tr class="prop"></tr>
        <tr class="prop"></tr>
        <tr class="prop"></tr>
        <tr class="prop">
          <td valign="top" class="name">
            <label for="leaveType.id"><g:message code="employeeLeaveDetail.leaveType.label" default="Leave Type" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: employeeLeaveDetailInstance, field: 'leaveType', 'errors')}">
            <g:select name="leaveType.id" from="${leaveType}" optionKey="id" value="${employeeLeaveDetailInstance?.leaveType?.id}"  />
          </td>
        </tr>

        <tr class="prop">
          <td valign="top" class="name">
            <label for="fromDate"><g:message code="employeeLeaveDetail.fromDate.label" default="From Date" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: employeeLeaveDetailInstance, field: 'fromDate', 'errors')}">
            <g:set var="startYr" value="${new GregorianCalendar().get(Calendar.YEAR)}"/>
            <g:set var="endYr" value="${new GregorianCalendar().get(Calendar.YEAR)+10}"/>

            <g:datePicker name="fromDate" precision="day" value="${employeeLeaveDetailInstance?.fromDate}" years="${startYr..endYr}"  />
          </td>
        </tr>



        <tr class="prop">
          <td valign="top" class="name">
            <label for="toDate"><g:message code="employeeLeaveDetail.toDate.label" default="To Date" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: employeeLeaveDetailInstance, field: 'toDate', 'errors')}">
            <g:set var="startYr" value="${new GregorianCalendar().get(Calendar.YEAR)}"/>
            <g:set var="endYr" value="${new GregorianCalendar().get(Calendar.YEAR)+10}"/>

            <g:datePicker name="toDate" precision="day" value="${employeeLeaveDetailInstance?.toDate}" years="${startYr..endYr}"  />
          </td>
        </tr>

        <tr class="prop">
          <td valign="top" class="name">
            <label for="leaveDays"><g:message code="employeeLeaveDetail.leaveDays.label" default="Leave Days" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: employeeLeaveDetailInstance, field: 'leaveDays', 'errors')}" style="width:70px; float:left;">
            <g:select name="leaveDays" from ="${['0.0','0.5']}" value="${employeeLeaveDetailInstance?.leaveDays}"  onchange="getHalf();"  />
          </td>
          <td id="whichHalf" style="width:150px; float:left;">
            <input id='leave_first' type="radio" name="whichHalf" value="First"<g:if test="${employeeLeaveDetailInstance?.whichHalf=='First'}">checked="" </g:if>/>First
            <input id='leave_second' type="radio" name="whichHalf" value="Second"<g:if test="${employeeLeaveDetailInstance?.whichHalf=='Second'}">checked="" </g:if>/>Second
          </td>
        </tr>

        <g:hiddenField name="employee.id" value="${employeeLeaveDetailInstance?.employee?.id}"></g:hiddenField>

        </tbody>
      </table>
    </div>
    <div class="createbuttons">
      <g:actionSubmit class="savebutton" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
      %{--<g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
    </div>
  </g:form>
</div>
</body>
</html>
