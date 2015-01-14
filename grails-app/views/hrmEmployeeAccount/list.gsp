

<%@ page import="com.bayalpatra.hrm.HrmEmployeeAccount" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <g:set var="entityName" value="Employee Account List" />
    <title>${entityName}</title>
    <export:resource/>
    <g:javascript library="jquery" plugin="jquery" />
    <script type="text/javascript">
    $(document).ready(function() {
        var empName=$("#empName").val()
        var parameter = "emp="+empName;
        if(empName){

            $(".excel").attr("href","/bayalpatra/hrmEmployeeAccount/list/?exportFormat=excel&extension=xls&"+parameter);
        }
    }   );

        function filterList(){
            var employee = $("#employee").val();
            var parameter = "emp="+employee;
             ${remoteFunction(controller:'hrmEmployeeAccount',action:'searchEmployee',update:'userGrid', params: "parameter",onComplete: "modifyExportParameter(parameter)")}
        }

    function modifyExportParameter(parameter){

        $(".excel").attr("href","/bayalpatra/hrmEmployeeAccount/list/?exportFormat=excel&extension=xls&"+parameter);
    }

//        $(function(){
//        var newLink = '<a href="/annapurna/hrm/departments/list?format=excel&extension=xls&parentName="+$("#parentName") class="excel">EXCEL</a>';
//        $("#exportToExcelLink").html();
//        });
    </script>

</head>
<body>


<div class="body">
    <h4>${entityName}
        <div align="right" class="printthis">
            <export:formats formats="['excel']" params="['exportFormat':'excel']" title="Export to Excel"/>

            <div class="print"><a href="#"title="print">
                <img src="${resource(dir: 'images', file: "print_icon.png")}" alt="Print Table" onclick="window.print()" >
            </a>
            </div>
        </div></h4>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="adduser">
        <g:link controller="hrmEmployeeAccount" action="create">
            <img src="${resource(dir: 'images', file: "add.jpg")}"> Add</g:link>
    </div>

    <br>
    <div>
        <table>
            <tr>
                <th class="thead2" width="10%">Employee :</th>
                <th class="thead2"><g:textField name="employee" id="employee"
                                                value="${emp}" onKeyUp="filterList()" />
                </th>
            </tr>
        </table>
    </div>

    <div class="list" id="userGrid">
        <table>
            <thead>
            <tr>

                <th><g:message code="hrmEmployeeAccount.employee.label" default="Employee" /></th>

                <g:sortableColumn property="accountNumber" title="${message(code: 'hrmEmployeeAccount.accountNumber.label', default: 'Account #')}" />

                <g:sortableColumn property="cit" title="${message(code: 'hrmEmployeeAccount.cit.label', default: 'Pan #')}" />

                <g:sortableColumn property="cit" title="${message(code: 'hrmEmployeeAccount.cit.label', default: 'PF #')}" />

                <g:sortableColumn property="citNumber" title="${message(code: 'hrmEmployeeAccount.citNumber.label', default: 'CIT #')}" />

                <th>Action</th>

            </tr>
            </thead>
            <tbody>
            <g:each in="${hrmEmployeeAccountInstanceList}" status="i" var="hrmEmployeeAccountInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <g:hiddenField name="empName" id="empName" value="${emp}"/>

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
            <g:paginate total="${hrmEmployeeAccountInstanceTotal}" params="[emp:emp]" />
        </div>
    </div>
</div>

</body>
</html>
