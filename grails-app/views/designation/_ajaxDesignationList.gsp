<div class="list">
    <table>
        <thead>
        <tr>

            %{--<g:sortableColumn property="id" title="${message(code: 'designation.id.label', default: 'Id')}" />--}%
            %{--<th>Select</th>--}%
            <g:sortableColumn property="jobTitleName" title="${message(code: 'designation.jobTitleName.label', default: 'Job Title Name')}" />
            <g:sortableColumn property="jobDescription" title="${message(code: 'designation.jobDescription.label', default: 'Job Description')}" />
            <th>Action</th>
            %{--<th>Delete</th>--}%

        </tr>
        </thead>
        <tbody>
        <g:each in="${designationInstanceList}" status="i" var="designationInstance">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                %{--<td>${fieldValue(bean: designationInstance, field: "id")}</td>  --}%%{--<g:link action="show" id="${designationInstance.id}">   </g:link>--}%
                %{--<td><input type="checkbox"> </td>--}%
                <td>${fieldValue(bean: designationInstance, field: "jobTitleName")}</td>
                <td>${fieldValue(bean: designationInstance, field: "jobDescription")}</td>
                <td id="linked"><g:link controller="designation" action="edit" id="${designationInstance.id}" params="[offset:params.offset]">Edit </g:link></td>
                %{--<td id="linked"><g:link controller="designation" action="edit" id="${designationInstance.id}" params="[offset:params.offset]">Delete </g:link></td>--}%

            </tr>
        </g:each>
        </tbody>
    </table>
</div>
<div class="paginateButtons">
    <g:paginate total="${designationInstanceTotal}" action="list" params="[jobs:jobs]" />
</div>