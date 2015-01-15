
<%@ page import="com.bayalpatra.hrm.Employee" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="layout" content="main_hrm" />
  <g:set var="entityName" value="${message(code: 'employee.label', default: 'Employee History')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>

  <g:javascript library="jquery" plugin="jquery"/>
  <jqui:resources/>
  <export:resource />

</head>
<body>

%{--<div class="nav">--}%
%{--<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>--}%
%{--<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>--}%
%{--</div>--}%

<div class="body">

  <h4><g:message code="default.list.label" args="[entityName]" /></h4>
  <g:render template='/employee/menu'></g:render>
  <div id="content-wrap">

    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div align="right" class="printthis">
      <export:formats formats="['excel']" params="['employeeIs':employeeInstance?.id,'exportFormat':'excel']" title="Export to Excel"/>

      <div class="print"><a href="#"title="print">
        <img src="${resource(dir: 'images', file: "print_icon.png")}" alt="Print Table" onclick="window.print()" >
      </a>
      </div>
    </div>

    <div class="personal_detail">

      <table  width="100%" cellspacing="0" cellpadding="0" border="0" class="gtable">
        <thead>
        <tr>
          <th>Field Type</th>
          <th>Old Value</th>
          <th>From Date</th>
          <th>To Date</th>
          <th>Updated By</th>
        </tr>
        </thead>
        <tbody id="empList">
        <g:each in="${empHistoryList}" status="i" var="employeeHistory">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

            <td width="15%">${fieldValue(bean: employeeHistory, field: "fieldType")}</td>

            <td width="55%">${fieldValue(bean: employeeHistory, field: "oldValue")}</td>

            <td width="15%"> <g:formatDate format="yyyy-MM-dd" date="${employeeHistory?.fromDate}" /></td>
            <td width="15%"> <g:formatDate format="yyyy-MM-dd" date="${employeeHistory?.toDate}" /></td>
            <td width="15%"> ${fieldValue(bean: employeeHistory, field: "user")}</td>




          </tr>
        </g:each>
        </tbody>
      </table>
      <div class="paginateButtons">
    <g:paginate total="${empHistoryCount}"/>
  </div>
    </div>
  </div>


  
</div>
</body>
</html>
