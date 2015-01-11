<table>
    <thead>
    <tr>

        <th width="20%"><g:message code="employeeLeaveDetail.employee.label" default="Employee" /></th>

        <th width="10%"><g:message code="employeeLeaveDetail.leaveType.label" default="Leave Type" /></th>

        <g:sortableColumn property="fromDate" title="${message(code: 'employeeLeaveDetail.fromDate.label', default: 'From Date')}" width="10%" />

        <g:sortableColumn property="toDate" title="${message(code: 'employeeLeaveDetail.toDate.label', default: 'To Date')}" width="10%" />

        <g:sortableColumn property="leaveDays" title="${message(code: 'employeeLeaveDetail.leaveDays.label', default: 'Leave Days')}" width="10%" />

        <g:sortableColumn property="status" title="${message(code: 'employeeLeaveDetail.toDate.label', default: 'Status')}" width="10%" />

        <g:if test="${isEmp=='false'}">
            <th  width="10%" colspan ="2">Action</th>
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

            <g:if test="${isEmp=='false'}">

                <td id="linked"><g:link controller="employeeLeaveDetail" action="updateStatus"  id="${employeeLeaveDetailInstance.id}" params="[approve:'approve']" onclick="return confirm('Are you sure want to approve?');" >Approve</g:link></td>

                <td id="linked"><g:link controller="employeeLeaveDetail" action="updateStatus"  id="${employeeLeaveDetailInstance.id}" params="[deny:'deny']" onclick="return confirm('Are you sure want to Deny?');" >Deny</g:link></td>

            </g:if>

        </tr>
    </g:each>
    </tbody>
</table>