
<%@ page import="com.bayalpatra.hrm.Designation" %>
<html>
<head>


      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8; no-cache"/>
      <meta name="layout" content="main_hrm" />

      <g:set var="entityName" value="${message(code: 'designation.label', default: 'Designation')}" />
      %{--<link rel="stylesheet" href="print.css" type="text/css" media="print" />--}%
      <title><g:message code="default.list.label" args="[entityName]" /></title>
    <g:javascript library="jquery" plugin="jquery" />
    <jqui:resources />
    <export:resource />
    <script type="text/javascript">

//    $(function () {
//        $('#chkSelectAll').click(function(){
//            if($(this).is(':checked')){
//                $('table tbody input:checkbox').attr('checked',true);
//            }
//            else{
//                $('table tbody input:checkbox').attr('checked',false)
//            }
//        });
//    });
        function filterList(){
            var jobs=$("#jobs").val();

            var parameter="jobs="+jobs
        ${remoteFunction(controller:'designation',action:'ajaxCallForDesignation',update:'list',params: "parameter")}
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
            <g:link controller="designation" action="create">
                  <img src="${resource(dir: 'images', file: "add.jpg")}"> Add</g:link>
      </div>
    <br>
    <div id="filters">
        <table width="100%" cellspacing="0" cellpadding="0" border="0">
            <tr><th class="thead2" width="10%">Job Title Name:</th>
                <th class="thead2" align="left"><input type="text" id="jobs" name="jobs" onkeyup="filterList()" value="${jobs}" ></th>
            </tr>
        </table>
    </div>
    <br>
    %{--<input type="checkbox" id="chkSelectAll">Select All<input type="submit" id="submit" value="Delete">--}%
<div id="list">
      <div class="list">
            <table>
                  <thead>
                  <tr>

                        %{--<g:sortableColumn property="id" title="${message(code: 'designation.id.label', default: 'Id')}" />--}%
                      %{--<th>Select</th>--}%
                        <g:sortableColumn property="jobTitleName" title="${message(code: 'designation.jobTitleName.label', default: 'Job Title Name')}" />
                        <g:sortableColumn property="jobDescription" title="${message(code: 'designation.jobDescription.label', default: 'Job Description')}" />
                        <th>Action</th>
                      %{--<th>Delete</th>--}%

                  </tr>
                  </thead>
                  <tbody>
                  <g:each in="${designationInstanceList}" status="i" var="designationInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                              %{--<td>${fieldValue(bean: designationInstance, field: "id")}</td>  --}%%{--<g:link action="show" id="${designationInstance.id}">   </g:link>--}%
                              %{--<td><input type="checkbox"> </td>--}%
                              <td>${fieldValue(bean: designationInstance, field: "jobTitleName")}</td>
                              <td>${fieldValue(bean: designationInstance, field: "jobDescription")}</td>
                              <td id="linked"><g:link controller="designation" action="edit" id="${designationInstance.id}" params="[offset:params.offset]">Edit </g:link></td>
                             %{--<td id="linked"><g:link controller="designation" action="edit" id="${designationInstance.id}" params="[offset:params.offset]">Delete </g:link></td>--}%
                        </tr>
                  </g:each>
                  </tbody>
            </table>
      </div>
      <div class="paginateButtons">
            <g:paginate total="${designationInstanceTotal}" params="[jobs:jobs]" />
      </div>
</div>
</div>
</body>
</html>
