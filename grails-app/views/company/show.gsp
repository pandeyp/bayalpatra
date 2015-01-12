
<%@ page import="com.bayalpatra.hrm.Company" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main_hrm" />
        <g:set var="entityName" value="${message(code: 'company.label', default: 'Company')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        %{--<div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>--}%
        <div class="body">
            <h4><g:message code="default.show.label" args="[entityName]" /></h4>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="company.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="company.name.label" default="Company Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "name")}</td>
                            
                        </tr>

                        %{--<tr class="prop">
                            <td valign="top" class="name"><g:message code="company.numEmployee.label" default="Number of Employees" /></td>

                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "numEmployee")}</td>

                        </tr>--}%


                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="company.taxId.label" default="Tax Id" /></td>

                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "taxId")}</td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="company.phone.label" default="Phone" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "phone")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="company.fax.label" default="Fax" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "fax")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="company.country.label" default="Country" /></td>

                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "country")}</td>

                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="company.address1.label" default="Address1" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "address1")}</td>
                            
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="company.address2.label" default="Address2" /></td>

                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "address2")}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="company.district.label" default="District" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "district")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="company.zone.label" default="Zone" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "zone")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="company.city.label" default="City" /></td>

                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "city")}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="company.comment.label" default="Comment" /></td>

                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "comment")}</td>

                        </tr>
                    
                        %{--<tr class="prop">
                            <td valign="top" class="name"><g:message code="company.logo.label" default="Logo" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: companyInstance, field: "logo")}</td>
                            
                        </tr>--}%
                    
                    </tbody>
                </table>
            </div>
            <div class="formbuttons" align="left" >
                <g:form>
                    <g:hiddenField name="id" value="${companyInstance?.id}" />
                    <g:actionSubmit class="editbutton" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" />
                    %{--<g:actionSubmit class="deletebutton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
                </g:form>
            </div>
        </div>
    </body>
</html>
