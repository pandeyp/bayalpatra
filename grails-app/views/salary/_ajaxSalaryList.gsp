<div class="list">
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="level"
                              title="${message(code: 'salary.level.label', default: 'Level')}" />

            <g:sortableColumn property="designation.jobTitleName"
                              title="${message(code: 'designation', default: 'Designation')}" />
        </th>

            <g:sortableColumn property="startSalary"
                              title="${message(code: 'startSalary', default: 'Start Salary')}" />

            <th>Action</th>

        </tr>
        </thead>
        <tbody>
        <g:each in="${salaryInstanceList}" status="i" var="salaryInstance">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                %{--
                <td id="linked"><g:link action="show"
                        id="${salaryInstance.id}">
                        ${fieldValue(bean: salaryInstance, field: "id")}
                    </g:link>
                </td>--}%

                <td>
                    ${fieldValue(bean: salaryInstance, field: "level")}
                </td>

                <td>
                    ${fieldValue(bean: salaryInstance, field: "designation")}
                </td>

                <td>
                    ${fieldValue(bean: salaryInstance, field: "basicSalary")}
                </td>

                <td id="linked"><g:link controller="salary" action="edit"
                                        id="${salaryInstance?.id}" params="[offset:params.offset]">
                    <em>Edit</em>
                </g:link>
                </td>

            </tr>
        </g:each>
        </tbody>
    </table>
</div>
<div class="paginateButtons">
    <g:paginate total="${salaryInstanceTotal}" action="list" params="[designation:designation,fromSalary:fromSalary,toSalary:toSalary]" />
</div>