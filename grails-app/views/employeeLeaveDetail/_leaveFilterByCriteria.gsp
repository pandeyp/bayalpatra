<%@ page import="commons.BayalpatraConstants" %>
<div class="div_leave_list" class="list">

    <div class="personal_detail_eld1">
        <table>
            <thead>
            <tr>
                <th>
                    <g:message code="employeeLeaveDetail.employee.label"
                               default="Employee" /></th>

                <th><g:message code="employeeLeaveDetail.leaveType.label"
                               default="Leave Type" /></th>


                <th>From Date</th>
                <th>To Date</th>
                <th>Leave Days</th>
                <th>Status</th>
                <th>Approved By</th>
                <th><g:message code="employeeLeaveDetail.action.label"
                               default="Action" /></th>

            </tr>
            </thead>
            <tbody>
            <g:each in="${leaveList}" status="i"
                    var="employeeLeaveDetailInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td>
                        ${fieldValue(bean: employeeLeaveDetailInstance, field: "employee")}
                    </td>

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
                        ${fieldValue(bean: employeeLeaveDetailInstance, field: "status")}
                    </td>

                    <td>
                        ${fieldValue(bean: employeeLeaveDetailInstance, field: "approvedBy")}
                    </td>

                    <td id="linked"><g:link controller="employeeLeaveDetail"
                                            action="edit" id="${employeeLeaveDetailInstance.id}"
                                            params="[offset:params.offset]">
                        <em>Edit</em>
                    </g:link>

                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="paginateButtons">
        <g:paginate total="${leaveCount}" action="list" params="[unit:unitName,department:departmentName,employeeId:employeeId,month:monthName,year:yearName]" />
    </div>
</div>


