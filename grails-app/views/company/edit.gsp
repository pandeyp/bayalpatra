

<%@ page import="com.bayalpatra.hrm.Company" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'company.label', default: 'Organization')}" />
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
            <g:hasErrors bean="${companyInstance}">
            <div class="errors">
                <g:renderErrors bean="${companyInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${companyInstance?.id}" />
                <g:hiddenField name="version" value="${companyInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                            %{--<g:link controller="company" action='edit' id="${companyInstance?.id}">comp Instance</g:link>--}%
                            <tr class="prop">

          <td valign="top" class="name">
            <label for="name"><g:message code="company.name.label" default="Company Name*" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'name', 'errors')}">
            <g:textField name="name" value="${companyInstance?.name}" />
          </td>
          %{--<td valign="top" class="name">
            <label for="numEmployee"><g:message code="company.numEmployee.label" default="Number of Employee" /></label>
          </td>--}%
         %{-- <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'numEmployee', 'errors')}">
            <g:textField name="numEmployee" value="${companyInstance?.numEmployee}" />
          </td>--}%
        </tr>

        <tr class="prop">
          <td valign="top" class="name">
            <label for="taxId"><g:message code="company.taxId.label" default="Tax Id *" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'taxId', 'errors')}">
            <g:textField name="taxId" value="${companyInstance?.taxId}" />
          </td>
          %{--<td valign="top" class="name">
            <label for="naiCs"><g:message code="company.naiCs.label" default="NAICS" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'naiCs', 'errors')}">
            <g:textField name="naiCs" value="${companyInstance?.naiCs}" />
          </td>--}%

        </tr>

        <tr class="prop">
          <td valign="top" class="name">
            <label for="phone"><g:message code="company.phone.label" default="Phone" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'phone', 'errors')}">
            <g:textField name="phone" value="${companyInstance?.phone}" />
          </td>
          <td valign="top" class="name">
            <label for="fax"><g:message code="company.fax.label" default="Fax" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'fax', 'errors')}">
            <g:textField name="fax" value="${companyInstance?.fax}" />
          </td>
        </tr>

        <tr class="prop">
          <td valign="top" class="name">
            <label for="country"><g:message code="company.country.label" default="Country" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'country', 'errors')}">
            <g:countrySelect name="country"  noSelection="['':'--Select Your Country --']" value="${companyInstance?.country}"/>
            <%--<g:textField name="country" value="${employeeInstance?.country}" /> --%>
          </td>
        </tr>
        <tr class="prop">
          <td valign="top" class="name">
            <label for="address1"><g:message code="company.address1.label" default="Address1" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'address1', 'errors')}">
            <g:textField name="address1" value="${companyInstance?.address1}" />
          </td>
          <td valign="top" class="name">
            <label for="address2"><g:message code="company.address2.label" default="Address2" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'address2', 'errors')}">
            <g:textField name="address2" value="${companyInstance?.address2}" />
          </td>
        </tr>

        <tr class="prop">
          <td valign="top" class="name">
            <label for="district"><g:message code="company.district.label" default="District" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'district', 'errors')}">
            <g:textField name="district" value="${companyInstance?.district}" />
          </td>
           <td valign="top" class="name">
            <label for="zone"><g:message code="company.zone.label" default="Zone" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'zone', 'errors')}">
            <g:textField name="zone" value="${companyInstance?.zone}" />
          </td>
        </tr>

        <tr class="prop">
          <td valign="top" class="name">
            <label for="city"><g:message code="company.city.label" default="City" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'city', 'errors')}">
            <g:textField name="city" value="${companyInstance?.city}" />
          </td>
        </tr>
        <tr class="prop">
          <td valign="top" class="name">
            <label for="comment"><g:message code="company.comment.label" default="Comments" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'comment', 'errors')}">
            <g:textArea name="comment" value="${companyInstance?.comment}" />
          </td>
        </tr>

        %{--<tr class="prop">
          <td valign="top" class="name">
            <label for="logo"><g:message code="company.logo.label" default="Logo" /></label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: companyInstance, field: 'logo', 'errors')}">
            <g:textField name="logo" value="${companyInstance?.logo}" />
          </td>
        </tr>--}%
                        
                        </tbody>
                    </table>
                </div>
                <div class="formbuttons" align="left">
                    <g:actionSubmit class="savebutton" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}"/>
                    %{--<g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
                </div>
            </g:form>
        </div>
    </body>
</html>
