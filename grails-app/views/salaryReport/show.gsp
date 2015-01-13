
<%@ page import="com.bayalpatra.hrm.SalaryReport" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'salaryReport.label', default: 'SalaryReport')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-salaryReport" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-salaryReport" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list salaryReport">
			
				<g:if test="${salaryReportInstance?.employee}">
				<li class="fieldcontain">
					<span id="employee-label" class="property-label"><g:message code="salaryReport.employee.label" default="Employee" /></span>
					
						<span class="property-value" aria-labelledby="employee-label"><g:link controller="employee" action="show" id="${salaryReportInstance?.employee?.id}">${salaryReportInstance?.employee?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${salaryReportInstance?.tax}">
				<li class="fieldcontain">
					<span id="tax-label" class="property-label"><g:message code="salaryReport.tax.label" default="Tax" /></span>
					
						<span class="property-value" aria-labelledby="tax-label"><g:fieldValue bean="${salaryReportInstance}" field="tax"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${salaryReportInstance?.salaryDate}">
				<li class="fieldcontain">
					<span id="salaryDate-label" class="property-label"><g:message code="salaryReport.salaryDate.label" default="Salary Date" /></span>
					
						<span class="property-value" aria-labelledby="salaryDate-label"><g:formatDate date="${salaryReportInstance?.salaryDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${salaryReportInstance?.total}">
				<li class="fieldcontain">
					<span id="total-label" class="property-label"><g:message code="salaryReport.total.label" default="Total" /></span>
					
						<span class="property-value" aria-labelledby="total-label"><g:fieldValue bean="${salaryReportInstance}" field="total"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:salaryReportInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${salaryReportInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
