<%@ page import="com.bayalpatra.hrm.Supervisor" %>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <meta name="layout" content="main_hrm" />
      <export:resource/>
      <g:set var="entityName" value="${message(code: 'supervisor.label', default: 'Supervisor')}" />
      <title><g:message code="default.list.label" args="[entityName]" /></title>
    <g:javascript plugin="jquery" library="jquery"/>
    <script type="text/javascript">

        function filterList(){
            var supervisor=$("#supervisor").val();

            var parameter="supervisor="+supervisor;
        ${remoteFunction(controller:'supervisor',action:'ajaxCallForSupervisor',update:'list',params: "parameter")}
        }
    </script>
</head>
<body>
%{--<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
</div>--}%
<div class="body">
      <div align="right" class="printthis">
    <export:formats formats="['excel']" action="exportToExcel" params="['exportFormat':'excel']" title="Export to Excel"/>
    
  <div class="print"><a href="#"title="print">
      <img src="${resource(dir: 'images', file: "print_icon.png")}" alt="Print Table" onclick="window.print()" >
    </a>
    </div>  
  </div>

      <h4><g:message code="default.list.label" args="[entityName]" /></h4>
      <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
      </g:if>
      <div class="adduser">
            <g:link controller="supervisor" action="create">
                  <img src="${resource(dir: 'images', file: "add.jpg")}"> Add</g:link>
      </div>
      <br>
    <div id="filters">
        <table width="100%" cellspacing="0" cellpadding="0" border="0">
            <tr><th class="thead2" style="width: 5%">Supervisor:</th>
                <th class="thead2" style="width: 23%"><input type="text" id="supervisor" name="supervisor" onkeyup="filterList()" value="" ></th>
               </tr>
        </table>
    </div>
    <br>
    <div  id="list">
      <div class="list">
            <table >
                  <thead>
                  <tr>

                        %{--<g:sortableColumn property="id" title="${message(code: 'supervisor.id.label', default: 'Id')}" />--}%

                        <th><g:message code="supervisor.employee.label" default="Supervisor" /></th>
                        <th>Action</th>

                  </tr>
                  </thead>
                  <tbody>
                  <g:each in="${supervisorInstanceList}" status="i" var="supervisorInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                              %{--<td><g:link action="show" id="${supervisorInstance.id}">${fieldValue(bean: supervisorInstance, field: "id")}</g:link></td>--}%

                              <td>${fieldValue(bean: supervisorInstance, field: "employee")}</td>
                             <td id="linked"><g:link controller="supervisor" action="edit" id="${supervisorInstance.id}" params="[offset:params.offset]">Edit </g:link>
                        </tr>
                  </g:each>
                  </tbody>
            </table>
      </div>
      <div class="paginateButtons">
            <g:paginate total="${supervisorInstanceTotal}" />
      </div>
    </div>
</div>
</body>
</html>
