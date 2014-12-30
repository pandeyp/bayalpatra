<%@ page import="com.bayalpatra.hrm.Department" %>
HttpSession session = request.getSession(true)
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />

    <g:set var="entityName" value="${message(code: 'departments.label', default: 'Department')}" />
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
            var deptName=$("#deptName").val();
            var parentName=$("#parentName").val();

            var parameter="deptName="+deptName+"&parentName="+parentName;
        ${remoteFunction(controller:'department',action:'ajaxCallForDepartments',update:'list',params: "parameter")}
        }
    </script>
</head>
<body>
<div class="body">
    <div align="right" class="printthis">
        %{--<span id="exportToExcelLink">--}%
            <export:formats formats="['excel']" action="exportToExcel" params="['exportFormat':'excel']" title="Export to Excel"/>
        %{--</span>--}%
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
        <g:link controller="department" action="create">
            <img src="${resource(dir: 'images', file: "add.jpg")}"> Add</g:link>
    </div>
    <br>
    <div id="filters">
        <table width="100%" cellspacing="0" cellpadding="0" border="0">
            <tr><th class="thead2" style="width: 5%">Name:</th>
                <th class="thead2" style="width: 23%"><input type="text" id="deptName" name="deptName" onkeyup="filterList()" onclick="$('#parentName').val('')" value="${deptName}" ></th>
                <th class="thead2" style="width: 10%">Parent Name:</th>
                <th class="thead2" onclick="$('#deptName').val('')"><g:select id="parentName" name="parentName" from="${parentDepartment}" noSelection="['':'----Select Parent----']" onchange="filterList()" value="${parentName}" optionKey="id"  ></g:select></th>
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
                <g:sortableColumn property="name" title="${message(code: 'departments.name.label', default: 'Name')}" />
                <g:sortableColumn property="parentId" title="${message(code: 'departments.name.label', default: 'Parent Name')}" />
                <th><g:message code="departments.idNumber.label" default="Department Code" /></th>
                <th><g:message code="departments.action.label" default="Action"/></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${departmentsInstanceList}" status="i" var="departmentsInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    %{--<td><g:link action="show" id="${departmentsInstance.id}">${fieldValue(bean: departmentsInstance, field: "id")}</g:link></td>--}%

                    %{--<td><input type="checkbox"> </td>--}%

                    <td>${fieldValue(bean: departmentsInstance, field: "name")}</td>

                    <td>${pName[i]}</td>

                    <td>${fieldValue(bean:departmentsInstance,field:"idNumber")}</td>


                <td id="linked">
                    <g:if test="${departmentsInstance?.name == 'BAYALPATRA'}">
                    </g:if>
                    <g:else>
                        <g:link controller="department" action="edit" id="${departmentsInstance?.id}" params="[offset:params.offset]"><em>Edit</em></g:link>
                    </g:else></td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="paginateButtons">
        <g:paginate total="${departmentsInstanceTotal}" params="[parentName:parentName,deptName:deptName,paginate:true]" />
    </div>
    </div>
</div>
</body>
</html>
