<div class="list" id="userGrid">
    <table>
        <thead>
        <tr>

            <th><g:message code="hrmEmployeeAccount.employee.label" default="Employee" /></th>

            <th>${message(code: 'hrmEmployeeAccount.accountNumber.label', default: 'Account #')}</th>

            <th>${message(code: 'hrmEmployeeAccount.cit.label', default: 'Pan #')}</th>

            <th>${message(code: 'hrmEmployeeAccount.cit.label', default: 'PF #')}</th>

            <th>${message(code: 'hrmEmployeeAccount.citNumber.label', default: 'CIT #')}</th>

            <th>Action</th>

        </tr>
        </thead>
        <tbody>
        <g:each in="${hrmEmployeeAccountInstanceList}" status="i" var="hrmEmployeeAccountInstance">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                <td>${fieldValue(bean: hrmEmployeeAccountInstance, field: "employee")}</td>

                <td>${fieldValue(bean: hrmEmployeeAccountInstance, field: "accountNumber")}</td>

                <td>${fieldValue(bean: hrmEmployeeAccountInstance, field: "panNumber")}</td>

                <td>${fieldValue(bean: hrmEmployeeAccountInstance, field: "pfNumber")}</td>

                <td>${fieldValue(bean: hrmEmployeeAccountInstance, field: "citNumber")}</td>


                <td id="linked"><g:link controller="hrmEmployeeAccount" action="edit" id="${hrmEmployeeAccountInstance.id}" params="[offset:params.offset]">Edit </g:link></td>


            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="paginateButtons">
        <g:paginate total="${hrmEmployeeAccountInstanceTotal}" action="list" params="[emp:emp]" />
    </div>
</div>