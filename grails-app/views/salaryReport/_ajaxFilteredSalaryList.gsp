
<%@ page import="commons.BayalpatraConstants" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <export:resource />
</head>
<body>


<div>
<div id ="inner_1" align="right" class="printthis321" >

    <export:formats formats="['excel']" params="[salaryMonth:params.salaryMonth,emp:params.emp,'exportFormat':'excel']" title="Export to Excel"/>
    <div class="print">
        <a href="#" title="print"> <img
                src="${resource(dir: 'images', file: "print_icon.png")}"
                alt="Print Table" onclick="window.print()"> </a>
    </div>

    <br/>
</div>




<div id="inner_2">
    <g:if test="${salaryTotal}">
        <div class="row-total">
            Total : <g:formatNumber number="${new BigDecimal (salaryTotal)}" format="###,##" />
        </div>
    </g:if>

    <table width="100%" cellspacing="0" cellpadding="0" border="0" class="gtable">

        <thead>
        <tr>

            <g:if test="${isEmp!=true}">
                <g:sortableColumn property="employee" title="${message(code: 'salaryReport.employee.label', default: 'Employee')}" />
            </g:if>

            <g:sortableColumn property="salaryMonth" title="${message(code: 'salaryReport.salaryMonth.label', default: 'Salary Month')}" id="doubleword" />

            <g:sortableColumn property="startSalary" title="${message(code: 'startSalary', default: 'Basic Salary')}" id="doubleword" />



            <g:if test="${isEmp!=true}">
                <g:sortableColumn property="cit" title="${message(code: 'salaryReport.cit.label', default: 'CIT')}" />
            </g:if>

            <g:sortableColumn property="pf" title="${message(code: 'salaryReport.pf.label', default: 'Total PF')}" id="doubleword" />

            <g:if test="${isEmp!=true}">
                <g:sortableColumn property="tax" title="${message(code: 'salaryReport.tax.label', default: 'Tax')}" />
            </g:if>


            <g:sortableColumn property="total" title="${message(code: 'salaryReport.total.label', default: 'Total')}" id="doubleword" />
            <g:if test="${isEmp==true}">
                <th>Action</th>
            </g:if>
        </tr>
        </thead>

        <tbody>

        <g:each in="${salaryReportInstanceList}" status="i" var="salaryReportInstance">
            <g:if test="${salaryReportInstance.employee.status == BayalpatraConstants.TERMINATED || salaryReportInstance.employee.status == BayalpatraConstants.CLEARED }">
            </g:if>
            <g:else>
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <g:if test="${isEmp!=true}">
                        <td>${fieldValue(bean: salaryReportInstance, field: "employee")} </td>
                    </g:if>


                    <td> <g:formatDate format="MMM-yy" date="${salaryReportInstance?.salaryMonth}" /></td>


                    <td>${com.bayalpatra.hrm.Salary.findByDesignation(salaryReportInstance.employee.designation).basicSalary}</td>


                    <g:if test="${isEmp!=true}">
                        <td>${fieldValue(bean: salaryReportInstance, field: "cit")}</td>
                    </g:if>


                    <g:if test="${isEmp!=true}">
                        <td>${fieldValue(bean: salaryReportInstance, field: "pf")}</td>
                    </g:if>

                    <td>${fieldValue(bean: salaryReportInstance, field: "tax")}</td>


                    <td>${fieldValue(bean: salaryReportInstance, field: "total")}</td>

                    <g:if test="${isEmp==true}">
                        <td><g:link controller="salaryReport" action="showSalaryDetails" params="['id':salaryReportInstance?.id]">View Details</g:link>  </td>
                    </g:if>

                </tr>
            </g:else>
        </g:each>
        </tbody>
    </table>


    <div class="paginateButtons">
        <g:paginate total="${salaryReportInstanceTotal}" action="list" params="[salaryMonth:params.salaryMonth,emp:params.employee,offset:params.offset]"/>
    </div>
</div>
</div>
</body>
</html>






             



