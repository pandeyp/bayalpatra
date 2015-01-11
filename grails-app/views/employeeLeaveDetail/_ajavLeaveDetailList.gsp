<%@ page import="commons.BayalpatraConstants" %>

<script type="text/javascript">
    $(function(){
        $(".seeMore").each(function(){
            $(this).seeMore();
        });
    })

</script>
<div>
<table>
    <thead>
    <tr>
        <th width="15%"><g:message code="employeeLeaveDetail.employee.label"
                       default="Employee" /></th>

        <th width="15%"><g:message code="employeeLeaveDetail.leaveType.label"
                       default="Leave Type" /></th>


        <th width="10%">From Date</th>
        <th width="10%">To Date</th>
        <th width="10%">Leave Days</th>
        <th width="10%">Half</th>
        <g:if test="${commons.AnnapurnaConstants.CLIENT_NAME==AnnapurnaConstants.CLIENT_DEERWALK}">
            <th width="30%">Leave Reason</th>
        </g:if>
    </tr>
    </thead>
    <tbody>
    <g:each in="${employeeLeaveDetailInstance}" status="i"
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
            <td>${employeeLeaveDetailInstance?.whichHalf}</td>
            <g:if test="${commons.AnnapurnaConstants.CLIENT_NAME==AnnapurnaConstants.CLIENT_DEERWALK}">
                <td>
                    <span class="seeMore" id="seeMore${i}">${fieldValue(bean: employeeLeaveDetailInstance, field: "leaveReason")}</span>
                </td>
            </g:if>
        </tr>

    </g:each>
    </tbody>
</table>
        <div class="paginateButtons">
            <g:paginate total="${totalCount?:0}" action="list" params="[pagination:true]"  />
        </div>
</div>