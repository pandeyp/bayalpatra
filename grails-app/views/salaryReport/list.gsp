
<%@ page import="commons.BayalpatraConstants; com.bayalpatra.hrm.SalaryReport"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <g:set var="entityName"
            value="${message(code: 'salaryReport.label', default: 'Salary Report')}" />

    <title><g:message code="default.list.label" args="[entityName]" />
    </title>
    <g:javascript library="jquery" plugin="jquery"/>

    <jqui:resources />
    <export:resource/>


    <script type="text/javascript">

        $(document).ready(function() {
            setupGridAjax();

        });

        function setupGridAjax() {
            $("#grid").find(".pagination a, th.sortable a").live('click', function(event) {
                event.preventDefault();
                var url = $(this).attr('href');

                var grid = $(this).parents(".ajax");
                $(grid).html($("#spinner").html());

                $.ajax({
                    type: 'GET',
                    url: url,
                    success: function(data) {
                        $(grid).fadeOut('fast', function() {$(this).html(data).fadeIn('slow');});
                    }
                })
            });
        }

        function filterList(){
            var employee = $("#employee").val();
            var salMonth = $("#salaryMonth").val();
            var salaryClass = $("#salaryClass").val();
            var parameter = "emp="+employee+"&salaryMonth="+salMonth+"&salaryClass="+salaryClass;

            $.ajax( {
                url: "${createLink(controller: 'salaryReport', action:'ajaxCall')}",
                type : 'post',
                data : parameter,
                success : function( resp ) {
                    $('#div_1').html($('#inner_1' , resp).html());
                    $('#div_2').html($('#inner_2' , resp).html());
                },
                onComplete:setupGridAjax()

            });
        }

        function setAjax(){
            alert($("#ajx").val());

        }
    </script>
</head>
<body>


<div class="body salary_report_list" >

    <div id = "div_1" align="right" class="printthis">

        <g:if test="${isEmp==false}">
            <export:formats formats="['excel']" params="[salaryMonth:params.salaryMonth,emp:params.emp,salaryClass:params.salaryClass]" title="Export to Excel"/>

            <div class="print"><a title="print" href="#">
                <img onclick="window.print()" alt="Print Table" src="/annapurna/images/print_icon.png">
            </a>
            </div>

        </g:if>
        <g:else>
            <export:formats formats="['excel']" params="[employeeIs:employeeInstance?.id]" title="Export to Excel"/>
        </g:else>

    </div>


    <h4 style=" padding:7px;">
        <g:message code="default.list.label" args="[entityName]" />
    </h4>
<div style="clear:both;"></div>
    <g:if test="${flash.message}">
        <div class="message">
            ${flash.message}
        </div>
    </g:if>
    <g:if test="${isEmp==true}">
        <g:render template='/employee/menu'></g:render>
    </g:if>

    <div id="content-wrap" class="salary_report_list">

            <g:if test="${isEmp!=true}">

                <div class="filters">
                    <table width="100%" cellspacing="0" cellpadding="0" border="0">

                        <tr>
                            <td width="40%"><table width="100%" border="0">
                                <tr>

                                    <td>Select Employee :</td>
                                    <td><g:select id="employee" name="employee"
                                            from="${employeeList}" optionKey="id"
                                            value="${employee}" noSelection="['':'-Choose Employee-']"
                                            onChange="filterList()" />
                                    </td>

                                </tr>
                            </table></td>
%{--                            <td>Pay Group : &nbsp;&nbsp;&nbsp;
                            <g:select id="salaryClass" name="salaryClass"
                                    from="${hrm.SalaryClass.list()}" noSelection="['':'-Choose Group-']" optionKey="identifier"
                                    onChange="filterList()" value="${salaryClass}" />
                            </td>--}%
                            <td>Select Salary Month :&nbsp;&nbsp;&nbsp;
                            <g:select id="salaryMonth" name="salaryMonth"
                                    from="${salMonthList}" noSelection="['':'-Choose Month-']"
                                    onChange="filterList()" value="${salaryMonth}" />
                            </td>
                        </tr>
                    </table>
                </div>

            </g:if>

    <div id="div_2" class="list">

        <g:if test="${salaryTotal}">

        Total : <g:formatNumber number="${new BigDecimal (salaryTotal)}" format="###,##" />
        <br/>
        </g:if>

            <table>
            <thead>
                <tr>
                    <g:if test="${isEmp!=true}">
                        <g:sortableColumn property="employee"
                                title="${message(code: 'salaryReport.employee.label', default: 'Employee')}" />
                    </g:if>


                    <g:sortableColumn property="salaryMonth"
                            title="${message(code: 'salaryReport.salaryMonth.label', default: 'Salary Month')}"
                            id="doubleword" />

                    <g:sortableColumn property="startSalary"
                            title="${message(code: 'salaryReport.startSalary.label', default: 'Basic Salary')}"
                            id="doubleword" />


                    <g:if test="${isEmp!=true}">
                        <g:sortableColumn property="cit"
                                title="${message(code: 'salaryReport.cit.label', default: 'CIT')}" id="doubleword" />
                    </g:if>


                    <g:if test="${isEmp!=true}">
                        <g:sortableColumn property="pf"
                                title="${message(code: 'salaryReport.pf.label', default: 'Total PF')}"
                                id="doubleword" />
                    </g:if>

                    <g:sortableColumn property="tax"
                            title="${message(code: 'salaryReport.tax.label', default: 'Tax')}" id="doubleword" />

                    <g:sortableColumn property="total"
                            title="${message(code: 'salaryReport.total.label', default: 'Total')}"
                            id="doubleword" />

                    <g:if test="${isEmp==true}">
                        <th id="doubleword">Action</th>
                    </g:if>
                </tr>
                </thead>

                <tbody>

                <g:each in="${salaryReportInstanceList}" status="i" var="salaryReportInstance">

                    <g:if test="${isEmp==true}">

                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                            <g:if test="${isEmp!=true}">
                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "employee")}
                                </td>
                            </g:if>
                            <g:if test="${isEmp!=true}">
                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "salaryClass.identifier")}
                                </td>
                            </g:if>

                            <td><g:formatDate format="MMM-yy"
                                    date="${salaryReportInstance?.salaryMonth}" /></td>

                            <td>
                                ${fieldValue(bean: salaryReportInstance, field: "startSalary")}
                            </td>

                            <td>
                                ${fieldValue(bean: salaryReportInstance, field: "additionalSalary")}
                            </td>

                            <td>
                                ${fieldValue(bean: salaryReportInstance, field: "basicSalary")}
                            </td>

                            <td>
                                ${fieldValue(bean: salaryReportInstance, field: "grade")}
                            </td>
                            <g:if test="${isEmp!=true}">
                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "seniorityAllowance")}
                                </td>

                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "otherAllowance")}
                                </td>

                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "dearnessAllowance")}
                                </td>

                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "extraAllowance")}
                                </td>
                            </g:if>

                            <g:if test="${isEmp!=true}">
                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "cit")}
                                </td>
                            </g:if>

                            <g:if test="${isEmp!=true}">
                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "employeeAdvance")}
                                </td>
                            </g:if>

                            <g:if test="${isEmp!=true}">
                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "pf")}
                                </td>
                            </g:if>
                            <td>
                                ${fieldValue(bean: salaryReportInstance, field: "tax")}
                            </td>
                            <td>
                                ${fieldValue(bean: salaryReportInstance, field: "total")}
                            </td>

                            <g:if test="${isEmp==true}">

                                <td><g:link controller="salaryReport"
                                        action="showSalaryDetails"
                                        params="['salaryIs':salaryReportInstance?.id,'employeeIs': salaryReportInstance?.employee?.id]">View Details</g:link>
                                </td>


                            </g:if>


                        </tr>
                    </g:if>
                    <g:else>
                        <g:if test="${salaryReportInstance.employee.status == BayalpatraConstants.TERMINATED || salaryReportInstance.employee.status == BayalpatraConstants.CLEARED}">
                        </g:if>
                        <g:else>
                            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                                <g:if test="${isEmp!=true}">
                                    <td>
                                        ${fieldValue(bean: salaryReportInstance, field: "employee")}
                                    </td>
                                </g:if>
%{--                                <g:if test="${isEmp!=true}">
                                    <td>
                                        ${fieldValue(bean: salaryReportInstance, field: "salaryClass.identifier")}
                                    </td>
                                </g:if>--}%

                                <td><g:formatDate format="MMM-yy"
                                        date="${salaryReportInstance?.salaryMonth}" /></td>

                                <td>
                                    need val here!!
                                </td>

%{--                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "additionalSalary")}
                                </td>

                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "basicSalary")}
                                </td>

                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "grade")}
                                </td>--}%
                                <g:if test="${isEmp!=true}">
%{--                                    <td>
                                        ${fieldValue(bean: salaryReportInstance, field: "seniorityAllowance")}
                                    </td>

                                    <td>
                                        ${fieldValue(bean: salaryReportInstance, field: "otherAllowance")}
                                    </td>

                                    <td>
                                        ${fieldValue(bean: salaryReportInstance, field: "dearnessAllowance")}
                                    </td>

                                    <td>
                                        ${fieldValue(bean: salaryReportInstance, field: "extraAllowance")}
                                    </td>--}%
                                </g:if>

                                <g:if test="${isEmp!=true}">
                                    <td>
                                        ${fieldValue(bean: salaryReportInstance, field: "cit")}
                                    </td>
                                </g:if>

                                <g:if test="${isEmp!=true}">
%{--                                    <td>
                                        ${fieldValue(bean: salaryReportInstance, field: "employeeAdvance")}
                                    </td>--}%
                                </g:if>

                                <g:if test="${isEmp!=true}">
                                    <td>
                                        ${fieldValue(bean: salaryReportInstance, field: "pf")}
                                    </td>
                                </g:if>
                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "tax")}
                                </td>
                                <td>
                                    ${fieldValue(bean: salaryReportInstance, field: "total")}
                                </td>

                                <g:if test="${isEmp==true}">

                                    <td><g:link controller="salaryReport"
                                            action="showSalaryDetails"
                                            params="['id':salaryReportInstance?.id]">View Details</g:link>
                                    </td>

                                </g:if>

                            </tr>
                        </g:else>
                    </g:else>
                </g:each>
                </tbody>
            </table>
            <div class="paginateButtons">

                <g:paginate total="${salaryReportInstanceTotal}" action="list"
                        params="[salaryMonth:params.salaryMonth,emp:params.employee,salaryClass:params.salaryClass]" />

            </div>

        </div>

        <br>


    </div>

</div>

</body>
</html>
