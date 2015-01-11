<%@ page import="commons.BayalpatraConstants; com.bayalpatra.hrm.EmployeeLeaveDetail" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <g:set var="entityName" value="${message(code: 'employeeLeaveDetail.label', default: 'Employee Leave Detail')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>

    <g:javascript library="jquery" plugin="jquery"/>
    <jqui:resources/>

    <script type="text/javascript">

        $(document).ready(function(){
            if(${leaveDay==0}){
                $("#whichHalf").hide();
            }
        });

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
    <h4><g:message code="default.create.label" args="[entityName]" /></h4>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${employeeLeaveDetailInstance}">
        <div class="errors">
            <g:renderErrors bean="${employeeLeaveDetailInstance}" as="list" />
        </div>
    </g:hasErrors>
    <g:form action="save">
        <div class="dialog">
            <table>
                <tbody>

                <g:if test="${isEmp}">
                    <g:hiddenField name="employee.id" value="${employeeInstance?.id}" />
                </g:if>
                <g:else>
                    <g:hiddenField name="checkForMenu" value="${!isEmp}"/>
                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="employee.id"><g:message code="employeeLeaveDetail.employee.label" default="Employee *" /></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: employeeLeaveDetailInstance, field: 'employee', 'errors')}">
                            <g:select name="employee.id" from="${employeeList}" optionKey="id" value="${employeeLeaveDetailInstance?.employee?.id}" noSelection="${['':'--Choose Employee--']}" />
                        </td>
                    </tr>
                </g:else>
                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="fromDate"><g:message code="employeeLeaveDetail.fromDate.label" default="From Date *" /></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: employeeLeaveDetailInstance, field: 'fromDate', 'errors')}">
                        <g:set var="startYr" value="${new GregorianCalendar().get(Calendar.YEAR)}"/>
                        <g:set var="endYr" value="${new GregorianCalendar().get(Calendar.YEAR)+13}"/>

                        <g:datePicker name="fromDate" precision="day" value="${employeeLeaveDetailInstance?.fromDate}" years="${startYr..endYr}"  />
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="toDate"><g:message code="employeeLeaveDetail.toDate.label" default="To Date *" /></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: employeeLeaveDetailInstance, field: 'toDate', 'errors')}">
                        <g:set var="startYr" value="${new GregorianCalendar().get(Calendar.YEAR)}"/>
                        <g:set var="endYr" value="${new GregorianCalendar().get(Calendar.YEAR)+13}"/>

                        <g:datePicker name="toDate" precision="day" value="${employeeLeaveDetailInstance?.toDate}" years="${startYr..endYr}" />
                    </td>
                </tr>


                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="leaveType.id"><g:message code="employeeLeaveDetail.leaveType.label" default="Leave Type *" /></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: employeeLeaveDetailInstance, field: 'leaveType', 'errors')}">
                        <g:select name="leaveType.id" from="${leaveType}" optionKey="id" value="${employeeLeaveDetailInstance?.leaveType?.id}" noSelection="['': '--Select Leave Type--']"/>
                    </td>
                </tr>



                <tr>
                    <td valign="top" class="name">
                        <label for="leaveDays"><g:message code="employeeLeaveDetail.leaveDays.label" default="Leave Days" /></label>
                    </td>

                    <td valign="top" class="value ${hasErrors(bean: employeeLeaveDetailInstance, field: 'leaveDays', 'errors')}" style="width:70px; float:left;">
                        <g:select name="leaveDays" from ="${['0.0','0.5']}" value="${employeeLeaveDetailInstance?.leaveDays}" onchange="getHalf();"  />

                    </td>
                    <td id="whichHalf" style="width:150px; float:left;">

                        <input id='leave_first' type="radio" name="whichHalf" value="First"<g:if test="${employeeLeaveDetailInstance?.whichHalf=='First'}">checked="" </g:if>/>First
                        <input id='leave_second' type="radio" name="whichHalf" value="Second"<g:if test="${employeeLeaveDetailInstance?.whichHalf=='Second'}">checked="" </g:if>/>Second

                    </td>
                </tr>

                <g:if test="${commons.BayalpatraConstants.CLIENT_NAME==BayalpatraConstants.CLIENT_DEERWALK}">
                    <tr>
                        <td valign="top" class="name">
                            <label for="leaveDays"><g:message code="employeeLeaveDetail.leaveReason.label" default="Leave Reason" /></label><br/>
                            <i>(Max 300 characters)</i>
                        </td>

                        <td valign="top" class="value ${hasErrors(bean: employeeLeaveDetailInstance, field: 'leaveReason', 'errors')}" style="width:70px; float:left;">
                            <g:textArea name="leaveReason" rows="3" cols="7" value="${employeeLeaveDetailInstance?.leaveReason}"/>
                        </td>

                    </tr>
                </g:if>

                </tbody>
            </table>
        </div>
        <div class="formbuttons">
            <g:submitButton name="create"  class="savebutton" value="${message(code: 'default.button.create.label', default: 'Create')}"  />
        </div>
    </g:form>
</div>
</body>
</html>
