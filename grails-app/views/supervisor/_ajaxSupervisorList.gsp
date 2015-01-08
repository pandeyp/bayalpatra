<div class="list">
    <table >
        <thead>
        <tr>

            %{--<g:sortableColumn property="id" title="${message(code: 'supervisor.id.label', default: 'Id')}" />--}%

            <th><g:message code="supervisor.employee.label" default="Supervisor" /></th>
            <th>Action</th>

        </tr>
        </thead>
        <tbody>
        <g:each in="${supervisorInstanceList}" status="i" var="supervisorInstance">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                %{--<td><g:link action="show" id="${supervisorInstance.id}">${fieldValue(bean: supervisorInstance, field: "id")}</g:link></td>--}%

                <td>${fieldValue(bean: supervisorInstance, field: "employee")}</td>
                <td id="linked"><g:link controller="supervisor" action="edit" id="${supervisorInstance.id}" params="[offset:params.offset]">Edit </g:link>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>