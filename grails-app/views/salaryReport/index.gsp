
<%@ page import="com.bayalpatra.hrm.SalaryReport" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'salaryReport.label', default: 'SalaryReport')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-salaryReport" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-salaryReport" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="salaryReport.employee.label" default="Employee" /></th>
					
						<g:sortableColumn property="tax" title="${message(code: 'salaryReport.tax.label', default: 'Tax')}" />
					
						<g:sortableColumn property="salaryDate" title="${message(code: 'salaryReport.salaryDate.label', default: 'Salary Date')}" />
					
						<g:sortableColumn property="total" title="${message(code: 'salaryReport.total.label', default: 'Total')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${salaryReportInstanceList}" status="i" var="salaryReportInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${salaryReportInstance.id}">${fieldValue(bean: salaryReportInstance, field: "employee")}</g:link></td>
					
						<td>${fieldValue(bean: salaryReportInstance, field: "tax")}</td>
					
						<td><g:formatDate date="${salaryReportInstance.salaryDate}" /></td>
					
						<td>${fieldValue(bean: salaryReportInstance, field: "total")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${salaryReportInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
