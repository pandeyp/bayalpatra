<%@page defaultCodec="none" %>
<%@ page import="commons.BayalpatraConstants; com.bayalpatra.hrm.EmployeeLeaveDetail"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <export:resource />
    <g:set var="entityName"
           value="${message(code: 'employeeLeaveDetail.label', default: 'Employee Leave Detail')}" />
    <title><g:message code="default.list.label" args="[entityName]" />
    </title>
    <g:javascript library="jquery" plugin="jquery"/>
    <jqui:resources/>
    <link rel="stylesheet" href="${resource(dir: '/dist/themes/default',file: 'style.min.css')}"/>
    <script type="text/javascript" src="${resource(dir:'/dist/',file:'jstree.js')}"></script
    <export:resource />
    <script language="javascript" type="text/javascript" src="${resource(dir:'js',file:'SeeMore.js')}"></script>
    <script type="text/javascript">

        $(function () {
            $("#popup").jstree()

            $(".seeMore").each(function(){
                $(this).seeMore();
            });
        });

        function showValueTextbox(val,name){
            $('#deptSelected').val(val);
            var month =  $('#month').val();
            var year =  $('#year').val();
            var parameter =  "department="+val+"&month="+month  +"&year="+year;
        ${remoteFunction(controller:'employeeLeaveDetail',action:'employeeLeaveDetailFilter',update:'div_leave_list', params: "parameter" )}
        }

        function getFilteredList(){
            var employee =  $('#employee').val();
            var month =  $('#month').val();
            var year =  $('#year').val();
            var unit =     $('#unit').val();
            var dept = $('#deptSelected').val();
            var parameter =  "employeeId="+  employee  +"&month="+month  +"&year="+year  +"&unit="+unit  +"&department="+dept;
        ${remoteFunction(controller:'employeeLeaveDetail',action:'employeeLeaveDetailFilter',update:'div_leave_list', params: "parameter" )}

        }

    </script>



</head>
<body>

<div class="body">


<div align="right" class="printthis">

    <g:if test="${isEmp==false}">
        <export:formats formats="['excel']" params="['exportFormat':'excel']" action="leaveDetailExport" title="Export to Excel" />
    </g:if>
    <g:else>
        <export:formats formats="['excel']"
                        params="[employeeIs:employeeInstance.id,'exportFormat':'excel']" title="Export to Excel" />
    </g:else>


    <div class="print">
        <a href="#" title="print"> <img
                src="${resource(dir: 'images', file: "print_icon.png")}"
                alt="Print Table" onclick="window.print()"> </a>
    </div>
</div>

<h4>
    <g:message code="default.list.label" args="[entityName]" />
</h4>
<g:if test="${flash.message}">
    <div class="message">
        ${flash.message}
    </div>
</g:if>
<g:if test="${isEmp==true}">
    <g:render template='/employee/menu'></g:render>
</g:if>
<div class="adduser">
    <g:link controller="employeeLeaveDetail" action="create"
            params="['employeeIs': employeeInstance?.id]">
        <img src="${resource(dir: 'images', file: "add.jpg")}">Apply Leave</g:link>

%{--        <div class="leave_report">

            <g:link controller="hrmLeaveBalanceReport" action="list"
                    params="[emp:employeeInstance.id]">
                <img src="${resource(dir: 'images', file: "report.png")}"
                     align="left">Leave Balance Report</g:link>
        </div>--}%


</div>
<br>
<g:if test="${BayalpatraConstants.CLIENT_NAME==BayalpatraConstants.CLIENT_BAYALPATRA}">
    <sec:ifAnyGranted roles="ROLE_ADMIN">
        <g:if test="${isEmp==false}">
            <div>

                <table width="100%" cellspacing="0" cellpadding="0" border="0">

                    <tr>

                        <td>Employee :</td>
                        <td><g:select name="employee"
                                      from="${employeeList}" optionKey="id"
                                      value="${employeeId}" noSelection="['':'-Choose Employee-']"
                                      onChange="getFilteredList()" />
                        </td>
                        <td>Department</td>
                        <td>
                            <div id="popup" onclick="$('#unit').val(null)">${deptTree}</div>
                            <g:hiddenField name="deptSelected" value=""/>
                        </td>
%{--                        <td>
                            Unit
                        </td>
                        <td onclick="$('#deptSelected').val(null)">
                            <g:select id="unit"  name="unit" from="${unitList}" noSelection="['':'-Choose-']" optionKey="id" onchange="getFilteredList();" value="${unitName}"/>
                        </td>--}%
                        <td>
                            Month
                        </td>
                        <td>
                            <g:set var="months" value="${new java.text.DateFormatSymbols().shortMonths}"/>
                            <g:select name="month" from="${months as List}" value="${monthName}" noSelection="['':'-Choose-']" onchange="getFilteredList()"/>
                        </td>
                        <td>
                            Year
                        </td>
                        <td>
                            <g:select name="year" from="${yearList}" value="${yearName}" noSelection="['':'-Choose-']" onchange="getFilteredList()"/>
                        </td>



                    </tr>
                </table>
            </div>
        </g:if>
    </sec:ifAnyGranted>
</g:if>

<br />
<div id="div_leave_list" class="list">
    <g:if test="${isEmp==false}">
        <div class="personal_detail_eld1">
    </g:if>
    <g:else>
        <div class="personal_detail_eld">
    </g:else>

    <table>
        <thead>
        <tr>

            <g:if test="${isEmp==false}">
                <th width="10%"><g:message code="employeeLeaveDetail.employee.label"
                               default="Employee" /></th>
            </g:if>

            <th width="10%"><g:message code="employeeLeaveDetail.leaveType.label"
                           default="Leave Type" /></th>


            <th width="10%">From Date</th>
            <th width="10%">To Date</th>
            <th width="10%">Leave Days</th>
            <th width="10%">Leave Balance</th>
            <th width="10%">Status</th>
            <g:if test="${commons.BayalpatraConstants.CLIENT_NAME==BayalpatraConstants.CLIENT_DEERWALK}">
                <th width="20%">Leave Reason</th>
            </g:if>
            <th width="10%">Approved By</th>
            <g:if test="${isEmp==false}">
                <th width="10%"><g:message code="employeeLeaveDetail.action.label"
                               default="Action" /></th>

            </g:if>
            <g:if test="${BayalpatraConstants.CLIENT_NAME==BayalpatraConstants.CLIENT_DEERWALK}">
                <g:if test="${isAdmin==true}">
                    <th></th>
                </g:if>
            </g:if>


        </tr>
        </thead>
        <tbody>
        <g:each in="${employeeLeaveDetailInstanceList}" status="i"
                var="employeeLeaveDetailInstance">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">


                <g:if test="${isEmp==false}">
                    <td>
                        ${fieldValue(bean: employeeLeaveDetailInstance, field: "employee")}
                    </td>
                </g:if>


                <td>
                    ${fieldValue(bean: employeeLeaveDetailInstance, field: "leaveType")}
                </td>

                <td><g:formatDate format="yyyy-MM-dd"
                                  date="${employeeLeaveDetailInstance.fromDate}" /></td>

                <td><g:formatDate format="yyyy-MM-dd"
                                  date="${employeeLeaveDetailInstance.toDate}" /></td>

                <td>
                    ${fieldValue(bean: employeeLeaveDetailInstance, field: "leaveDifference")}
                </td>

                <td>
                    ${employeeLeaveDetailInstance.leaveType.days - employeeLeaveDetailInstance.leaveDifference}
                </td>

                <td>
                    ${fieldValue(bean: employeeLeaveDetailInstance, field: "status")}
                </td>

                <g:if test="${BayalpatraConstants.CLIENT_NAME==BayalpatraConstants.CLIENT_DEERWALK}">
                    <td>
                        <span class="seeMore" id="seeMore${i}">${fieldValue(bean: employeeLeaveDetailInstance, field: "leaveReason")}</span>
                    </td>
                </g:if>

                <td>
                    ${fieldValue(bean: employeeLeaveDetailInstance, field: "approvedBy")}
                </td>

                <g:if test="${isEmp==false}">
                    <td id="linked"><g:link controller="employeeLeaveDetail"
                                            action="edit" id="${employeeLeaveDetailInstance.id}"
                                            params="[offset:params.offset]">
                    <em>Edit</em>
                </g:link>
                </g:if>


                <g:if test="${BayalpatraConstants.CLIENT_NAME==BayalpatraConstants.CLIENT_DEERWALK}">
                    <g:if test="${isAdmin==true}">
                        <td id="linked"><g:link controller="employeeLeaveDetail"
                                                action="cancelLeave" id="${employeeLeaveDetailInstance.id}"
                                                params="[offset:params.offset]" onclick="return confirm('Are you sure want to cancel?');">
                            <em>Cancel</em>
                        </g:link></td>
                    </g:if>
                </g:if>



            </tr>
        </g:each>
        </tbody>
    </table>
    <g:if test="${isEmp==true}">
        <div class="paginateButtonsWithMenu">
            <g:paginate total="${employeeLeaveDetailInstanceTotal}"
                        params="['employee':employeeInstance?.id]" />
        </div>
    </g:if>
    <g:else>
        <div class="paginateButtons">
            <g:paginate total="${employeeLeaveDetailInstanceTotal}"
                        params="[employee:employeeInstance?.id,unit:unitName,department:departmentName,employeeId:employeeId,month:monthName,year:yearName]" />
        </div>
    </g:else>
</div>
</div>





</div>
</body>
</html>
