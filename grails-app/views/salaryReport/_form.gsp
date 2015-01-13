<%@ page import="com.bayalpatra.hrm.SalaryReport" %>



<div class="fieldcontain ${hasErrors(bean: salaryReportInstance, field: 'employee', 'error')} required">
	<label for="employee">
		<g:message code="salaryReport.employee.label" default="Employee" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="employee" name="employee.id" from="${com.bayalpatra.hrm.Employee.list()}" optionKey="id" required="" value="${salaryReportInstance?.employee?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: salaryReportInstance, field: 'tax', 'error')} required">
	<label for="tax">
		<g:message code="salaryReport.tax.label" default="Tax" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="tax" value="${fieldValue(bean: salaryReportInstance, field: 'tax')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: salaryReportInstance, field: 'salaryDate', 'error')} required">
	<label for="salaryDate">
		<g:message code="salaryReport.salaryDate.label" default="Salary Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="salaryDate" precision="day"  value="${salaryReportInstance?.salaryDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: salaryReportInstance, field: 'total', 'error')} required">
	<label for="total">
		<g:message code="salaryReport.total.label" default="Total" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="total" value="${fieldValue(bean: salaryReportInstance, field: 'total')}" required=""/>

</div>

