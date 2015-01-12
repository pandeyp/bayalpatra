
<%@ page import="com.bayalpatra.hrm.Company" %>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <meta name="layout" content="main_hrm" />
      <g:set var="entityName" value="${message(code: 'company.label', default: 'Organization')}" />
      <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
%{--<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
</div>--}%
<div class="body">
      <g:link>
      <p align="right" class="printthis">
            <img src="${resource(dir: 'images', file: "print_icon.png")}" alt="Print Table" onclick="window.print()" >
      </p>
      </g:link>      
      <h4><g:message code="default.list.label" args="[entityName]" /></h4>
      <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
      </g:if>
      <div class="list">
            <table>
                  <thead>
                  <tr>

                        %{--<g:sortableColumn property="id" title="${message(code: 'company.id.label', default: 'Id')}" />--}%

                        <g:sortableColumn property="name" title="${message(code: 'company.name.label', default: 'Name')}" />

                        <g:sortableColumn property="phone" title="${message(code: 'company.phone.label', default: 'Phone')}" />

                        <g:sortableColumn property="fax" title="${message(code: 'company.fax.label', default: 'Fax')}" />

                        <g:sortableColumn property="address1" title="${message(code: 'company.address1.label', default: 'Address1')}" />

                        <g:sortableColumn property="city" title="${message(code: 'company.city.label', default: 'City')}" />

                  </tr>
                  </thead>
                  <tbody>
                  <g:each in="${companyInstanceList}" status="i" var="companyInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                              %{--<td id="linked"><g:link action="show" id="${companyInstance.id}">${fieldValue(bean: companyInstance, field: "id")}</g:link></td>--}%

                              <td>${fieldValue(bean: companyInstance, field: "name")}</td>

                              <td>${fieldValue(bean: companyInstance, field: "phone")}</td>

                              <td>${fieldValue(bean: companyInstance, field: "fax")}</td>

                              <td>${fieldValue(bean: companyInstance, field: "address1")}</td>

                              <td>${fieldValue(bean: companyInstance, field: "city")}</td>

                        </tr>
                  </g:each>
                  </tbody>
            </table>
      </div>
      <div class="paginateButtons">
            <g:paginate total="${companyInstanceTotal}" />
      </div>
</div>
</body>
</html>
