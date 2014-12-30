%{--<script type="text/javascript">--}%
    %{--$(function(){--}%
        %{--var newLink = '<a href="/annapurna/hrm/departments/list?format=excel&extension=xls&parentName="+$("#parentName")+"&dept" class="excel">EXCEL</a>';--}%
        %{--$("#exportToExcelLink").html();--}%
    %{--});--}%
%{--</script>--}%
<div class="list">
    <table>
        <thead>
        <tr>


            <g:sortableColumn property="name" title="${message(code: 'departments.name.label', default: 'Name')}" />
            <g:sortableColumn property="parentId" title="${message(code: 'departments.name.label', default: 'Parent Name')}" />
            <th><g:message code="departments.idNumber.label" default="Department Code" /></th>
            <th><g:message code="departments.action.label" default="Action"/></th>

        </tr>
        </thead>
        <tbody>
        <g:each in="${departmentsInstanceList}" status="i" var="departmentsInstance">
            <g:hiddenField name="parentName" id="parentName" value="${parentName}"></g:hiddenField>
            <g:hiddenField name="deptName" id="deptName" value="${deptName}"></g:hiddenField>
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                <td>${fieldValue(bean: departmentsInstance, field: "name")}</td>

                <td>${pName[i]}</td>

                <td>${fieldValue(bean:departmentsInstance,field:"idNumber")}</td>

%{--                <td>
                    <g:if test="${departmentsInstance.isMainStore}">
                        Yes
                    </g:if>
                    <g:else>
                        No
                    </g:else>
                </td>

                <td>
                    <g:if test="${departmentsInstance.isSubStore}">
                        Yes
                    </g:if>
                    <g:else>
                        No
                    </g:else>
                </td>--}%

            <td id="linked">
                <g:if test="${departmentsInstance?.name == 'BAYALPATRA'}">
                </g:if>
                <g:else>
                    <g:link controller="departments" action="edit" id="${departmentsInstance?.id}" params="[offset:params.offset]"><em>Edit</em></g:link></td>
                </g:else>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
<div class="paginateButtons">
    <g:paginate total="${departmentsInstanceTotal}" action="list" params="[parentName:parentName,deptName:deptName,paginate:true]" />
</div>