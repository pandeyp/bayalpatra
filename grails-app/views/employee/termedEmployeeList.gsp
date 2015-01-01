<%@ page import="com.bayalpatra.hrm.Employee" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <export:resource />
    <g:set var="entityName" value="${message(code: 'employee.label', default: 'Terminated Employee')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <g:javascript library="jquery" plugin="jquery" />
    <jqui:resources />
    <script type="text/javascript">

        $(document).ready(function(){
            $('#msg').hide();
        });

        jQuery(function() {
            jQuery('#datepick1').datepicker({
                dateFormat: 'yy-mm-dd',
                changeMonth: true,
                changeYear: true,
                yearRange: "-20:+10",
                maxDate: '0'
            });
            jQuery('#datepick2').datepicker({
                dateFormat: 'yy-mm-dd',
                changeMonth: true,
                changeYear: true,
                yearRange: "-20:+10",
                maxDate: '0'
            });

        });
        function filterList(){
            $("#msg").hide();
            var employee = $("#employee").val();
            var parameter = "emp="+employee;

            $.ajax( {
                url: "${createLink(controller: 'employee', action:'filterTerminatedList')}",
                type : 'post',
                data : parameter,
                success : function( resp ) {
                    $('#div_1').html($('#inner_1' , resp).html());
                    $('#div_2').html($('#inner_2' , resp).html());
                    $('#datepick1').val(null);
                    $('#datepick2').val(null);
                    $('#totalCount').val($("#hiddenfield").val());
//                    $(".excel").attr("href","/annapurna/hrm/employee/exportToExcel?format=excel&extension=xls&offset=0&"+$("#searchParams").val());
                }
            });

        }


        function search(){
            $("#msg").hide();
            var startDate = $("#datepick1").val()
            var endDate = $("#datepick2").val()
            var type=$("#type").val()
            var parameter = "startDate="+startDate+"&endDate="+endDate+"&type="+type;

            $.ajax( {
                url: "${createLink(controller: 'employee', action:'terminatedEmployeeList')}",
                type : 'post',
                data : parameter,
                success : function( resp ) {
                    $('#div_1').html($('#inner_1' , resp).html());
                    $('#div_2').html($('#inner_2' , resp).html());
                    $('#employee').val(null);
                    $('#totalCount').val($("#hiddenfield").val());
//                    $(".excel").attr("href","/annapurna/hrm/employee/exportToExcel?format=excel&extension=xls&offset=0&"+$("#searchParams").val());
                }
            });
        }


    </script>
</head>
<body>
<div class="body">
    <div align="right" class="printthis">
        %{--<g:if test="${filter}">
        <export:formats formats="['excel']" action="exportToExcel" params="[type: type,startDate: startDate,endDate: endDate]" title="Export to Excel"/>
        </g:if>--}%

            <export:formats formats="['excel']" action="exportToExcel"  params="" title="Export to Excel"/>

        <div class="print"><a href="#"title="print">
            <img src="${resource(dir: 'images', file: "print_icon.png")}" alt="Print Table" onclick="window.print()" >
        </a>
        </div>
    </div>
    <h4><g:message code="default.list.label" args="[entityName]" /></h4>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <br>
    <div id="content-wrap">
        <div id="grid">
            %{--   <div id="totalTag">
                Total Employees&nbsp;:  ${employeeInstanceTotal}
            </div>--}%
            <br/>

            <table width="100%" cellspacing="0" cellpadding="0" border="0"
                   class="employee_table">
                <thead>
                <tr>
                    <th class="thead1" colspan="1">Total Employee :</th>
                    <th colspan="30" class="thead1"><g:textField
                            name="itemHead" readonly="" value="${employeeInstanceTotal}"
                            disabled="" size="5px" id="totalCount" />
                    </th>
                </tr>
                <tr>

                    <th class="thead2">Employee :</th>
                    <th class="thead2"><g:textField name="employee"
                                                    id="employee" value="" onKeyUp="filterList()" /></th>
                    <th class="thead2">
                        Type:
                    </th>
                    <th class="thead2">
                        <g:select id ="type" name="type"  value="${type}" from="${['Appointment','Terminated']}" />
                    </th>
                    <th class="thead2">From Date:</th>
                    <th colspan="12" class="thead2">
                        <input id="datepick1" type="text" name="startDate" class="employee_txt" value="${formatDate(format: 'yyyy-MM-dd',date: sDate)}"/>&nbsp;
                    <th class="thead2">To Date:</th>
                    <th colspan="12" class="thead2">
                        <input id="datepick2" type="text" name="endDate" class="employee_txt" value="${formatDate(format: 'yyyy-MM-dd',date: eDate)}" />

                        <input type="button" onclick="search()" value="Search" class="employee_btn">
                    </th>
                </tr>

                </thead>
            </table>

            <div id="msg">
                <p><i>No employees found</i></p>
            </div>

            <div id="div_2">
                <table  width="100%" cellspacing="0" cellpadding="0" border="0" class="gtable">
                    <thead>
                    <tr>

                        <g:sortableColumn property="id" title="${message(code: 'employee.id.label', default: 'Employee Id')}" />

                        <g:sortableColumn property="firstName" title="${message(code: 'employee.firstName.label', default: 'Name')}" />

                        <g:sortableColumn property="designation" title="${message(code:'employee.designation.label', default:'Designation')}" />

                        <th><g:message code="employee.departments.label" default="Department / Unit" /></th>

                        <g:sortableColumn property="joinDate" title="${message(code: 'employee.joinDate.label', default: 'Join Date')}" />

                        <g:sortableColumn property="terminatedDate" title="${message(code: 'employee.terminatedDate.label', default: 'Terminated Date')}" />

                        <g:sortableColumn property="status" title="${message(code: 'employee.supervisor.label', default: 'Status')}" />

                        <g:sortableColumn property="homePhone" title="${message(code: 'employee.homePhone.label', default: 'Contact Number')}" />

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

                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </br>
                <div class="paginateButtons">
                    <g:paginate  total="${employeeInstanceTotal}" params="[type: type,startDate: startDate,endDate: endDate]" />
                </div>
            </div>

        </div>
    </div>

</div>
</body>
</html>