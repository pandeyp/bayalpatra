<%@ page import="com.bayalpatra.hrm.EmployeeDependents" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <g:set var="entityName" value="${message(code: 'employee.label', default: 'Dependents')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>

    <g:javascript library="jquery" plugin="jquery"/>
    <jqui:resources/>
    <export:resource />


    <script type="text/javascript">

        function filterList(){
            var employee = $("#employee").val();
            var parameter = "emp="+employee
        ${remoteFunction(controller:'employeeDependents',action:'ajaxCall',update:'grid', params: "parameter",onComplete:'changeExportParams()')}
        }

        function changeExportParams(){
            $(".excel").attr("href","/annapurna/hrm/employeeDependents/dependentsList?format=excel&extension=xls&"+"emp="+$("#exportParams").val());
        }

    </script>

</head>
<body>
<div class="body">

    <div align="right" class="printthis">
        <export:formats formats="['excel']" params="[emp:searchEmp]" title="Export to Excel"/>

        <div class="print"><a href="dependentsList.gsp#" title="print">
            <img src="${resource(dir: 'images', file: "print_icon.png")}" alt="Print Table" onclick="window.print(); return false;" >
        </a>
        </div>
    </div>
    <h4><g:message code="default.list.label" args="[entityName]" /></h4>

    <div>
        <div id="filters">
            <table  width="100%" cellspacing="0" cellpadding="0" border="0">

                <tr align="right">
                    <sec:ifAnyGranted roles="ROLE_HR_Admin,ROLE_HR_Secondary,ROLE_HR_Primary">
                        <td>Employee :</td>
                        <td><g:textField name="employee" id ="employee" value="${searchEmp}" onKeyUp="filterList()" /></td>
                    </sec:ifAnyGranted>

                </tr>
            </table>
        </div>
         <br />
        <div id="grid">
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

                <g:each in="${dependentsList}" status="i" var="dependentsInstance">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                        <td>${fieldValue(bean: dependentsInstance, field: "employee")} </td>
                        <td>${fieldValue(bean: dependentsInstance, field: "fatherName")} </td>
                        <td>${fieldValue(bean: dependentsInstance, field: "motherName")} </td>
                        <td>${fieldValue(bean: dependentsInstance, field: "spouseName")} </td>
                        <td>${fieldValue(bean: dependentsInstance, field: "childName1")} </td>
                        <td>${fieldValue(bean: dependentsInstance, field: "childName2")} </td>
                        <td>
                            <g:link controller="employeeDependents" action="create" params="['employeeIs':dependentsInstance.employee.id]" >View</g:link>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>

        <br />
            <div class="paginateButtons">
                <g:paginate total="${employeeDependentsInstanceTotal}" />
            </div>
        </div>
    </div>

</div>
</body>
</html>
