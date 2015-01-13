
<%@ page import="com.bayalpatra.hrm.Salary" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="layout" content="main_hrm" />
  <g:set var="entityName" value="${message(code: 'salary.label', default: 'Designation (Unassigned Salary)')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
  <export:resource />
</head>
<body>
%{--<div class="nav">--}%
%{--<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>--}%
%{--<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>--}%
%{--</div>--}%
<div class="body">

  <div align="right" class="printthis">
    <export:formats formats="['excel']" params="" title="Export to Excel"/>

    <div class="print"><a href="#" title="print">
      <img src="${resource(dir: 'images', file: "print_icon.png")}" alt="Print Table" onclick="window.print()" >
    </a>
    </div>
  </div>
  <h4><g:message code="default.list.label" args="[entityName]" /></h4>
  <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
  </g:if>

<div class="list">
            <table>
                  <thead>
                  <tr>

                        %{--<g:sortableColumn property="id" title="${message(code: 'designation.id.label', default: 'Id')}" />--}%

                        <g:sortableColumn property="jobTitleName" title="${message(code: 'designation.jobTitleName.label', default: 'Job Title Name')}" />
                        <g:sortableColumn property="jobDescription" title="${message(code: 'designation.jobDescription.label', default: 'Job Description')}" />
                        <th>Action</th>

                  </tr>
                  </thead>
                  <tbody>
                  <g:each in="${designationInstanceList}" status="i" var="designationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                              %{--<td>${fieldValue(bean: designationInstance, field: "id")}</td>  --}%%{--<g:link action="show" id="${designationInstance.id}">   </g:link>--}%

                              <td>${fieldValue(bean: designationInstance, field: "jobTitleName")}</td>
                              <td>${fieldValue(bean: designationInstance, field: "jobDescription")}</td>
                              <td id="linked"><g:link controller="salary" action="create" params="[designation:designationInstance.id,offset:params.offset]">Add Start Salary</g:link></td>

                        </tr>
                  </g:each>
                  </tbody>
            </table>
      </div>
      <div class="paginateButtons">
            <g:paginate total="${designationInstanceTotal}" />
      </div>
</div>
</body>
</html>