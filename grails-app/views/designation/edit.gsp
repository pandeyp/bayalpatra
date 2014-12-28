

<%@ page import="hrm.Designation" %>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <meta name="layout" content="main_hrm" />
      <g:set var="entityName" value="${message(code: 'designation.label', default: 'Designation')}" />
      <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
%{--<div class="nav">
      <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
      <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
      <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
</div>--}%
<div class="body">
      <h4><g:message code="default.edit.label" args="[entityName]" /></h4>
      <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${designationInstance}">
            <div class="errors">
                  <g:renderErrors bean="${designationInstance}" as="list" />
            </div>
      </g:hasErrors>
      <g:form method="post" >
            <g:hiddenField name="id" value="${designationInstance?.id}" />
            <g:hiddenField name="version" value="${designationInstance?.version}" />
            <g:hiddenField name="offset" value="${offset}"/>
        <div class="dialog">
                  <table>
                        <tbody>

                        <tr class="prop">
                              <td valign="top" class="name">
                                    <label for="jobTitleName"><g:message code="designation.jobTitleName.label" default="Job Title Name *" /></label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean: designationInstance, field: 'jobTitleName', 'errors')}">
                                    <g:textField name="jobTitleName" value="${designationInstance?.jobTitleName}" />
                              </td>
                        </tr>

                        <tr class="prop">
                              <td valign="top" class="name">
                                    <label for="jobDescription"><g:message code="designation.jobDescription.label" default="Job Description" /></label>
                              </td>
                              <td valign="top" class="value ${hasErrors(bean: designationInstance, field: 'jobDescription', 'errors')}">
                                    <g:textField name="jobDescription" value="${designationInstance?.jobDescription}" />
                              </td>
                        </tr>

                        </tbody>
                  </table>
            </div>
            <div class="createbuttons">
                  <g:actionSubmit class="savebutton" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                  %{--<g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
            </div>
      </g:form>
</div>
</body>
</html>
