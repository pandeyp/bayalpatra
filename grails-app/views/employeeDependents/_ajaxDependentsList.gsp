
<%@ page contentType="text/html;charset=UTF-8" %>
<table  width="100%" cellspacing="0" cellpadding="0" border="0" class="gtable">
    <thead>
    <tr>

        %{--<g:sortableColumn property="id" title="${message(code: 'employee.id.label', default: 'Employee Id')}" />--}%

        <g:sortableColumn property="employee.firstName" title="${message(code: 'employee.firstName.label', default: 'Employee Name')}" />
        <g:sortableColumn property="fatherName" title="${message(code: 'employee.firstName.label', default: 'Fathers Name')}" />
        <g:sortableColumn property="motherName" title="${message(code: 'employee.firstName.label', default: 'Mothers Name')}" />
        <g:sortableColumn property="spouseName" title="${message(code: 'employee.firstName.label', default: 'Spouse Name')}" />
        <g:sortableColumn property="childName1" title="${message(code: 'employee.firstName.label', default: 'First Child Name')}" />
        <g:sortableColumn property="childName2" title="${message(code: 'employee.firstName.label', default: 'Second Child Name')}" />
        <th>Details</th>


    </tr>
    </thead>
    <tbody>

    <g:each in="${employeeDependentsList}" status="i" var="dependentsInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td>${fieldValue(bean: dependentsInstance, field: "employee")} </td>
            <td>${fieldValue(bean: dependentsInstance, field: "fatherName")} </td>
            <td>${fieldValue(bean: dependentsInstance, field: "motherName")} </td>
            <td>${fieldValue(bean: dependentsInstance, field: "spouseName")}</td>
            <td>${fieldValue(bean: dependentsInstance, field: "childName1")} </td>
            <td>${fieldValue(bean: dependentsInstance, field: "childName2")}</td>
            <td>
                <g:link controller="employeeDependents" action="create" params="['employee.id':dependentsInstance.employee.id]" >View</g:link>
            </td>
        </tr>
    </g:each>
    </tbody>
</table>
<g:hiddenField name="exportParams" value="${exportParams}" />
<br />
<div class="paginateButtons">
    <g:paginate action="dependentsList" params="[emp:exportParams]" total="${employeeInstanceTotal}" />
</div>





