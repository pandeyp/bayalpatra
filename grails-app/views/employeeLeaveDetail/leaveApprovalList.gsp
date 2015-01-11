<%@ page import="commons.BayalpatraConstants; com.bayalpatra.hrm.EmployeeLeaveDetail" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <export:resource/>
    <g:set var="entityName" value="${message(code: 'employeeLeaveDetail.label', default: 'Employee Leave Detail')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <g:javascript library="jquery" plugin="jquery" />
    <script language="javascript" type="text/javascript" src="${resource(dir:'js',file:'SeeMore.js')}"></script>
    <jqui:resources />
    <script type="text/javascript">
        $(function(){
            $(".seeMore").each(function(){
                 $(this).seeMore();
            });

            $(".datepick").datepicker({
                dateFormat: 'yy-mm-dd',
                changeMonth: true,
                changeYear: true,
                yearRange: "-20:+10"
            });
        });

        function searchLeave(){
            var parameter = "selectedEmp="+$("#selectedEmp").val()+"&fromDate="+$("#datepick1").val()+"&toDate="+$("#datepick2").val()+"&isEmp="+$("#isEmp").val();
            ${remoteFunction(controller: 'employeeLeaveDetail',action:'filterUnapprovedLeave',params:'parameter',update:'filteredLeaves',onComplete:'changeExportParams(parameter)')}
        }

        function changeExportParams(parameter){
            $(".excel").attr("href","/com/bayalpatra/hrm/employeeLeaveDetail/approvalList?rxportFormat=excel&extension=xls&"+parameter);
        }

    </script>
</head>
<body>
<div class="body">

    <div align="right" class="printthis">
        <export:formats formats="['excel']" params="['exportFormat':'excel']" title="Export to Excel"/>

        <div class="print"><a href="#"title="print">
            <img src="${resource(dir: 'images', file: "print_icon.png")}" alt="Print Table" onclick="window.print()" >
        </a>
        </div>
    </div>

    <h4><g:message code="default.list.label" args="[entityName]" /></h4>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
</br>
    <table width="100%" cellspacing="0" cellpadding="0" border="0"
           class="employee_table">
        <thead>
        <tr>
            <th class="thead2">Employee :</th>
            <th class="thead2"><g:select name="selectedEmp" from="${employeeList}" optionKey="id" noSelection="['':'-- Select One --']" onchange="searchLeave()" /></th>
            <th class="thead2">From Date:</th>
            <th colspan="12" class="thead2">
                <input id="datepick1" type="text" name="startDate" class="employee_txt datepick" value="${formatDate(format: 'yyyy-MM-dd',date: startDate)}" onchange="searchLeave()"/>&nbsp;
            <th class="thead2">To Date:</th>
            <th colspan="12" class="thead2">
                <input id="datepick2" type="text" name="endDate" class="employee_txt datepick" value="${formatDate(format: 'yyyy-MM-dd',date: endDate)}" onchange="searchLeave()"/>
            </th>
        </tr>

        </thead>
    </table>
    <div class="list" id="filteredLeaves">
        <table>
            <thead>
            <tr>

                <th width="20%"><g:message code="employeeLeaveDetail.employee.label" default="Employee" /></th>

                <th width="10%"><g:message code="employeeLeaveDetail.leaveType.label" default="Leave Type" /></th>

                <g:sortableColumn property="fromDate" title="${message(code: 'employeeLeaveDetail.fromDate.label', default: 'From Date')}" width="10%" />

                <g:sortableColumn property="toDate" title="${message(code: 'employeeLeaveDetail.toDate.label', default: 'To Date')}" width="10%" />

                <g:sortableColumn property="leaveDays" title="${message(code: 'employeeLeaveDetail.leaveDays.label', default: 'Leave Days')}" width="10%" />

                <g:sortableColumn property="status" title="${message(code: 'employeeLeaveDetail.toDate.label', default: 'Status')}" width="10%" />

                <g:if test="${commons.BayalpatraConstants.CLIENT_NAME==BayalpatraConstants.CLIENT_DEERWALK}">
                <th>Leave Reason</th>
                </g:if>

                <g:if test="${isEmp==false}">
                    <th  width="10%" colspan ="2">Action</th>
                %{--<th></th>--}%
                </g:if>

            </tr>
            </thead>
            <tbody>
            <g:each in="${employeeLeaveDetailInstanceList}" status="i" var="employeeLeaveDetailInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td>${fieldValue(bean: employeeLeaveDetailInstance, field: "employee")}</td>


                    <td>${fieldValue(bean: employeeLeaveDetailInstance, field: "leaveType")}</td>

                    <td><g:formatDate format="yyyy-MM-dd" date="${employeeLeaveDetailInstance.fromDate}" /></td>

                    <td><g:formatDate format="yyyy-MM-dd" date="${employeeLeaveDetailInstance.toDate}" /></td>

                    <td>${fieldValue(bean: employeeLeaveDetailInstance, field: "leaveDifference")}</td>

                    <td>${fieldValue(bean: employeeLeaveDetailInstance, field: "status")}</td>

                    <g:if test="${commons.BayalpatraConstants.CLIENT_NAME==BayalpatraConstants.CLIENT_DEERWALK}">
                        <td>
                            <span class="seeMore" id="seeMore${i}">${fieldValue(bean: employeeLeaveDetailInstance, field: "leaveReason")}</span>
                        </td>
                    </g:if>

                    <g:if test="${isEmp==false}">

                        <td id="linked"><g:link controller="employeeLeaveDetail" action="updateStatus"  id="${employeeLeaveDetailInstance.id}" params="[approve:'approve']" onclick="return confirm('Are you sure want to approve?');" >Approve</g:link></td>

                        <td id="linked"><g:link controller="employeeLeaveDetail" action="updateStatus"  id="${employeeLeaveDetailInstance.id}" params="[deny:'deny']" onclick="return confirm('Are you sure want to Deny?');" >Deny</g:link></td>

                    </g:if>

                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
<g:hiddenField name="isEmp" value="${isEmp}" />
   %{-- <div class="paginateButtons">
        <g:paginate total="${employeeLeaveDetailInstanceTotal}" />
    </div>--}%
</div>
</body>
</html>
