<%@ page import="com.bayalpatra.hrm.Department" %>



<div class="fieldcontain ${hasErrors(bean: departmentInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="department.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="description" required="" value="${departmentInstance?.description}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: departmentInstance, field: 'level', 'error')} required">
	<label for="level">
		<g:message code="department.level.label" default="Level" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="level" type="number" value="${departmentInstance.level}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: departmentInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="department.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${departmentInstance?.name}"/>

</div>

