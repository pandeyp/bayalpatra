<%@page defaultCodec="none" %>
<%@ page import="com.bayalpatra.hrm.Employee"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <g:set var="entityName"
           value="${message(code: 'employee.label', default: 'Employee')}" />
    <title><g:message code="default.list.label" args="[entityName]" />
    </title>

    <g:javascript library="jquery" plugin="jquery" />
    <jqui:resources />
    <link rel="stylesheet" href="${resource(dir: '/dist/themes/default',file: 'style.min.css')}"/>
    <script type="text/javascript" src="${resource(dir:'/dist/',file:'jstree.js')}"></script>
    <export:resource />


    <script type="text/javascript">
        $(document).ready(function() {
            $('#msg').hide();

            var department=$("#departmentSel").val()
            var startDate=$("#datepick1").val()
            var endDate=$("#datepick2").val()
            var employee=$("#employee").val()
            var parameter="department="+department+"&startDate="+startDate+"&endDate="+endDate

            if(department||startDate||endDate){
                $(".excel").attr("href","/annapurna/hrm/employee/ajaxEmployeeList?format=excel&extension=xls&"+parameter);
            }else if(employee){
                $(".excel").attr("href","/annapurna/hrm/employee/ajaxCall?format=excel&extension=xls&emp="+employee);

            }
        }   );

        function getDepartment(val){

            var startDate = $("#datepick1").val()
            var endDate = $("#datepick2").val()
            var parameter = "startDate="+startDate+"&endDate="+endDate+"&department="+val;

            $("#employee").val(null);

            $.ajax( {
                url: "${createLink(controller: 'employee', action:'ajaxEmployeeList')}",
                type : 'post',
                data : parameter,
                    async:false,
                success : function( resp ) {
                    $('#div_1').html($('#inner_1' , resp).html());
                    $('#div_2').html($('#inner_2' , resp).html());
                }
            });

        }

        function getDate(){
            $("#msg").hide();
            $("#employee").val(null);
            var startDate = $("#datepick1").val()
            var endDate = $("#datepick2").val()
            var department = $("#department").val()
            var parameter = "startDate="+startDate+"&endDate="+endDate+"&department="+department;

            $.ajax( {
                url: "${createLink(controller: 'employee', action:'ajaxEmployeeList')}",
                type : 'post',
                data : parameter,
                success : function( resp ) {
                    if(resp=="noValue"){
                        $("#msg").show();
                    }
                    $('#div_1').html($('#inner_1' , resp).html());
                    $('#div_2').html($('#inner_2' , resp).html());
                    $('#employee').val();
                    $('#totalCount').val($("#hiddenfield").val());
                }
            });

        }

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

        $(function () {
            $("#popup").jstree()



        });

        function showValueTextbox(val,name){


            $("#msg").hide();
            $("#employee").val(null);
            $("#department").val(val);
            var startDate = $("#datepick1").val();
            var endDate = $("#datepick2").val();
            var department = val;
            var parameter = "startDate="+startDate+"&endDate="+endDate+"&department="+department;

            $.ajax( {
                url: "${createLink(controller: 'employee', action:'ajaxEmployeeList')}",
                type : 'post',
                data : parameter,
                success : function( resp ) {
                    if(resp=="noValue"){
                        $("#msg").show();
                    }
                    $('#div_1').html($('#inner_1' , resp).html());
                    $('#div_2').html($('#inner_2' , resp).html());
                    $('#totalCount').val($("#hiddenfield").val());

                }
            });

        }

        function filterList(){
            $("#msg").hide();
            var employee = $("#employee").val();
            $('#datepick1').val(null);
            $('#datepick2').val(null);
            $("#department").val(null);

            var parameter = "emp="+employee+"&offset=0&max=20";
            $.ajax( {
                url: "${createLink(controller: 'employee', action:'ajaxCall')}",
                type : 'post',
                data : parameter,
                success : function( resp ) {
                    if(resp=="noValue"){
                        $('#msg').show();
                    }
                    $('#div_1').html($('#inner_1' , resp).html());
                    $('#div_2').html($('#inner_2' , resp).html());


                    $('#totalCount').val($("#hiddenfield").val());
                }
            });

        }

    </script>

</head>
<body>
<g:hiddenField name="department" value=""/>
<div class="body">

    <div  id = "div_1" align="right" class="printthis">
        %{--<export:formats action="ajaxCall" formats="['excel']" params="[emp:params.employee,department: params.department ,startDate:params.startDate,endDate:params.endDate]" title="Export to Excel" />--}%
            <export:formats formats="['excel']" params="" title="Export To Excel"/>
        <div class="print">
            <a href="#" title="print"> <img
                    src="${resource(dir: 'images', file: "print_icon.png")}"
                    alt="Print Table" onclick="window.print()"> </a>
        </div>
    </div>

    <h4>
        <g:message code="default.list.label" args="[entityName]" />
    </h4>
    <g:if test="${flash.message}">
        <div class="message">
            ${flash.message}
        </div>
    </g:if>
    <div class="adduser">
        <g:link controller="employee" action="create">
            <img src="${resource(dir: 'images', file: "add.jpg")}"> Add</g:link>

    </div>

    <br>
    <div>

        <div id="filters">
            <sec:ifAnyGranted roles="ROLE_ADMIN">
                <table width="100%" cellspacing="0" cellpadding="0" border="0">
                    <td width="40%">
                        <table width="100%" border="0">
                            <tr>
                                <g:hiddenField name="departmentSel" id="departmentSel" value="${department}"/>
                                <td width="8%" align="center">Department</td>
                                <td width="87%">
                                    <div id="popup">
                                        ${deptTree}
                                    </div></td>
                            </tr>
                        </table></td>
                </table></sec:ifAnyGranted>
            <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPERVISOR,ROLE_DEPARTMENTHEAD">
                <table width="100%" cellspacing="0" cellpadding="0" border="0"
                       class="employee_table">
                    <thead>
                    <sec:ifAnyGranted roles="ROLE_ADMIN">
                        <tr>
                            <th class="thead1" colspan="2">Total Employee :</th>
                            <th colspan="30" class="thead1"><g:textField
                                    name="itemHead" readonly="" value="${employeeInstanceTotal}"
                                    disabled="" size="5px" id="totalCount" />
                            </th>
                        </tr>
                    </sec:ifAnyGranted>

                    <tr>

                        <th class="thead2">Employee :</th>

                        <th class="thead2">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </th>


                        <th class="thead2"><g:textField name="employee"
                                                        id="employee" value="${employee}" onKeyUp="filterList()" /></th>
                        <sec:ifAnyGranted roles="ROLE_ADMIN">
                            <th class="thead2">Join Date :</th>
                            <th colspan="12" class="thead2">
                                <input id="datepick1" type="text" name="startDate" class="employee_txt"  value="${startDate}" />&nbsp;
                            <th class="thead2">To Date :</th>
                            <th colspan="12" class="thead2">
                                <input id="datepick2" type="text" name="endDate" class="employee_txt" value="${endDate}" />
                                <input type="button" onclick="getDate()" value="Search" class="employee_btn">
                            </th></sec:ifAnyGranted>
                    </tr>
                    </thead>
                </table>
            </sec:ifAnyGranted>

        </div>
        <br>
        <div id="msg">
            <p><i>No employees found</i></p>
        </div>
        <div id="div_2">



       <table width="100%" cellspacing="0" cellpadding="0" border="0"
                   class="gtable employee_table" id="tb1">
                <thead>

                <tr>

                    <g:sortableColumn property="id"
                                      title="${message(code: 'employee.id.label', default: 'Employee Id')}" />

                    <g:sortableColumn property="firstName"
                                      title="${message(code: 'employee.firstName.label', default: 'Name')}" />

                    <g:sortableColumn property="designation.jobTitleName"
                                      title="${message(code: 'employee.designation.label', default: 'Designation')}" />

                    <th><g:message code="employee.departments.label"
                                   default="Unit / Department" /></th>

                    <g:sortableColumn property="joinDate"
                                      title="${message(code: 'employee.joinDate.label', default: 'Join Date')}" />

                    <g:sortableColumn property="supervisor.employee.firstName"
                                      title="${message(code: 'employee.supervisor.label', default: 'Supervisor')}" />
                    <g:sortableColumn property="status"
                                      title="${message(code: 'employee.supervisor.label', default: 'Status')}" />
                    <g:sortableColumn property="mobile"
                                      title="${message(code: 'employee.mobile.label', default: 'Contact Number')}" />


                </tr>
                </thead>
                <tbody>

                <g:each in="${employeeInstanceList}" status="i"
                        var="employeeInstance">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                        <td>
                            ${fieldValue(bean: employeeInstance, field: "employeeId")}
                        </td> %{--
								<td id="linked"><g:link action="edit"
										id="${employeeInstance.id}" params="[offset:params.offset]">
										${employeeInstance.fullName}
									</g:link></td>--}%


                        <td id="linked"><g:link action="edit"
                                                params="[employeeIs:employeeInstance.id,offset:params.offset,statusFlag:true]">
                            ${fieldValue(bean: employeeInstance, field: "firstName")}
                            ${fieldValue(bean: employeeInstance, field: "middleName")}
                            ${fieldValue(bean: employeeInstance, field: "lastName")}
                        </g:link></td> %{--id="${employeeInstance.id}"--}%
                        <td>
                            ${fieldValue(bean: employeeInstance, field: "designation")}
                        </td>

                            <td>
                                ${fieldValue(bean: employeeInstance, field: "department")}
                            </td>

                        <td><g:formatDate format="yyyy-MM-dd"
                                          date="${employeeInstance?.joinDate}" /></td>

                        <td>
                            ${fieldValue(bean: employeeInstance, field: "supervisor")}
                        </td>
                        <td>
                            ${fieldValue(bean: employeeInstance, field: "status")}
                        </td>
                        <td>
                            ${fieldValue(bean: employeeInstance, field: "mobile")}
                        </td>

                    </tr>
                </g:each>
                </tbody>
            </table>

        </br>
            <div class="paginateButtons">
                <g:paginate total="${employeeInstanceTotal}" action="list"
                            params="[department:department,startDate:startDate,endDate:endDate,offset:offset,employee:employee]" />
            </div>
        </div>
    </div>

</div>
</body>
</html>
