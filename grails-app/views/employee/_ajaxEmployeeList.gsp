<%--
  Created by IntelliJ IDEA.
  User: prativa
  Date: 24 Aug, 2011
  Time: 5:11:45 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div>

    <div id ="inner_1">
        <g:if test="${endDate||startDate||department||employee}">
            <export:formats formats="['excel']" params="[department:params.department,startDate:params.startDate,endDate:params.endDate,emp:employee]" title="Export to Excel"/>

        </g:if><g:else>
            <export:formats action="list" formats="['excel']" params="" title="Export to Excel"/>

        </g:else>
             <div class="print">
            <a href="#" title="print"> <img
                    src="${resource(dir: 'images', file: "print_icon.png")}"
                    alt="Print Table" onclick="window.print()"> </a>
        </div>
    </div>

    <div id="inner_2">
        <table  width="100%" cellspacing="0" cellpadding="0" border="0" class="gtable">
            <thead>

            <tr>

                <th><g:message code="employee.id.label" default="Employee Id" /></th>

                <th><g:message code="employee.firstName.label" default="Name" /></th>

                <th><g:message code="employee.designation.label" default="Designation" /></th>

                <th><g:message code="employee.departments.label" default="Department / Unit" /></th>

                <th><g:message code="employee.joinDate.label" default="Join Date" /></th>

                <th><g:message code="employee.supervisor.label" default="Supervisor" /></th>

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

                    <td>${fieldValue(bean: employeeInstance, field: "supervisor")}</td>
                    <td>${fieldValue(bean: employeeInstance, field: "status")}</td>
                    <td>${fieldValue(bean: employeeInstance, field: "mobile")}</td>
                    <g:hiddenField name="myField" value="${employeeInstanceTotal}" id="hiddenfield"/>




                </tr>
            </g:each>
            </tbody>
        </table>

    </br>
        <div id="pagination" class="paginateButtons">
            <g:paginate total="${employeeInstanceTotal}"  action="list" params="[department:department,startDate:startDate,endDate:endDate,emp:employee,frompagin:2,offset:params.offset,max:params.max]" />
        </div>
    </div>


</div>
