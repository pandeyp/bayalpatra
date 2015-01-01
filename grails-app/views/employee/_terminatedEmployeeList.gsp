<%@ page contentType="text/html;charset=UTF-8" %>
<div>
    <div id="inner_2">
        <g:hiddenField name="searchParams" value="${searchParams}" />
        <g:if test="${employeeInstanceList}">
            <table  width="100%" cellspacing="0" cellpadding="0" border="0" class="gtable">
                <thead>
                <tr>
                    <th><g:message code="employee.id.label" default="Employee Id" /></th>
                    <th><g:message code="employee.firstName.label" default="Name" /></th>
                    <th><g:message code="employee.designation.label" default="Designation" /></th>
                    <th><g:message code="employee.departments.label" default="Department / Unit" /></th>
                    <th><g:message code="employee.joinDate.label" default="Join Date" /></th>
                    <th><g:message code="employee.terminatedDate.label" default="Terminated Date" /></th>
                    <th><g:message code="employee.status.label" default="Status" /></th>
                    <th><g:message code="employee.mobile.label" default="Contact Number" /></th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${employeeInstanceList}" status="i" var="employeeInstance">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                        <td>${fieldValue(bean: employeeInstance, field: "employeeId")}</td>

                        <td id="linked"><g:link action="edit" params="[employeeIs:employeeInstance.id,offset:params.offset,statusFlag:true]">${fieldValue(bean: employeeInstance, field: "firstName")} ${fieldValue(bean: employeeInstance, field: "middleName")} ${fieldValue(bean: employeeInstance, field: "lastName")}</g:link></td>
                        %{--id="${employeeInstance.id}"--}%
                        <td>${fieldValue(bean: employeeInstance, field: "designation")}</td>

                        <g:if test="${employeeInstance?.unit?.id}">
                            <td>${fieldValue(bean: employeeInstance, field: "unit")}</td>

                        </g:if>

                        <g:else>
                            <td>${fieldValue(bean: employeeInstance, field: "departments")}</td>
                        </g:else>

                    %{--<td>${fieldValue(bean: employeeInstance, field: "joinDate")}</td>--}%
                        <td> <g:formatDate format="yyyy-MM-dd" date="${employeeInstance?.joinDate}" /></td>

                        <td> <g:formatDate format="yyyy-MM-dd" date="${employeeInstance?.terminatedDate}" /></td>
                        <td>${fieldValue(bean: employeeInstance, field: "status")}</td>
                        <td>${fieldValue(bean: employeeInstance, field: "mobile")}</td>
                        <g:hiddenField name="myField" value="${employeeInstanceTotal}" id="hiddenfield"/>
                    </tr>
                </g:each>
                </tbody>
            </table>
            </br>

            <div id="pagination" class="paginateButtons">
                <g:paginate total="${employeeInstanceTotal}" action="termedEmployeeList" params="[type: type,startDate: startDate,endDate: endDate]" />
            </div>
        </g:if>
        <g:else>
            <i>No employees found</i>
        </g:else>
    </div>
</div>
