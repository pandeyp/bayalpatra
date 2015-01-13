
<%@ page import="com.bayalpatra.hrm.Salary"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <export:resource/>
    <g:set var="entityName"
            value="${message(code: 'salary.label', default: 'Salary')}" />
    <title><g:message code="default.list.label" args="[entityName]" />
    </title>
    <g:javascript library="jquery" plugin="jquery"></g:javascript>
    <script type="text/javascript">
    function validateNumeric(sender){
        var min=-1;
        var content = document.getElementById(sender).value;
        if(isNaN(content)){
            document.getElementById(sender).value='';
            $(".salary").html('0.00');
            return false;
        }else{
            if(content<=min){
                document.getElementById(sender).value='';
                $(".salary").html('0.00');
                return false;
            }
        }
        calculateChange();
    }

    function filterList(){
        var designation=$("#designation").val();
        var fromSalary=$("#fromSalary").val();
        var toSalary=$("#toSalary").val();

        var parameter="designation="+designation+"&fromSalary="+fromSalary+"&toSalary="+toSalary;
        ${remoteFunction(controller: "salary",action: "ajaxCallForSalary",update: "list",params:"parameter")}

    }

    </script>
</head>
<body>
%{--
    <div class="nav">
        <span class="menuButton"><a class="home"
            href="${createLink(uri: '/')}"><g:message
                    code="default.home.label" />
        </a>
        </span> <span class="menuButton"><g:link class="create"
                action="create">
                <g:message code="default.new.label" args="[entityName]" />
            </g:link>
        </span>
    </div>
    --}%
<div class="body">

    <div align="right" class="printthis">
        <export:formats formats="['excel']" action="exportToExcel" params="['exportFormat':'excel']" title="Export to Excel" />

        <div class="print">
            <a href="#" title="print"> <img
                    src="${resource(dir: 'images', file: "print_icon.png")}"
                    alt="Print Table" onclick="window.print()"> </a>
        </div>
    </div>

    <h4>
        <g:message code="default.list.label" args="[entityName]" />
    </h4>
    <g:if test="${flash.message}">
        <div class="message">
            ${flash.message}
        </div>
    </g:if>

    <div class="adduser_pending">
        <g:link controller="salary" action="create">
            <img src="${resource(dir: 'images', file: "add.jpg")}"> Add</g:link>
    </div>
    <div class="pending-review">

        <g:link controller="salary" action="getSalaryNotSetList">
            <img src="${resource(dir: 'images', file: "report.png")}" align="left">Designation (Unassigned Salary)</g:link>

    %{--<g:link controller="salary" action="salaryNotSetList">--}%
    %{--<img src="${resource(dir: 'images', file: "pending-review.jpg")}">Designation (Unassigned Salary)</g:link>--}%
    </div>


    <div class="clear"></div>

    <br>
    <div id="filters">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">

            <tr>
                <th class="thead2" width="8%">Designation</th>
                <th class="thead2" width="15%"><input type="text" name="designation" id="designation" onkeyup="filterList()" value="${designation}" > </th>
                <th class="thead2" width="8%">Start Salary</th>
                <th class="thead2" width="8%"><input type="text" name="fromSalary" id="fromSalary" class="salary" onkeyup="validateNumeric(this.id)" placeholder="FromSalary" value="${fromSalary}"></th>
                <th class="thead2" width="8%"><input type="text" name="toSalary" id="toSalary" class="salary" onkeyup="validateNumeric(this.id)"  placeholder="ToSalary" value="${toSalary}"></th>
            <th class="thead2"><input type="button" class="employee_btn" onclick="filterList()" value="Search"></th>
            </tr>
        </table>
    </div>
<div id="list">
    <div class="list">
        <table>
            <thead>
            <tr>

                <g:sortableColumn property="level"
                        title="${message(code: 'salary.level.label', default: 'Level')}" />

                <g:sortableColumn property="designation.jobTitleName"
                        title="${message(code: 'designation', default: 'Designation')}" />
            </th>

                <g:sortableColumn property="basicSalary"
                        title="${message(code: 'basicSalary', default: 'Start Salary')}" />

                <th>Action</th>

            </tr>
            </thead>
            <tbody>
            <g:each in="${salaryInstanceList}" status="i" var="salaryInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    %{--
                                 <td id="linked"><g:link action="show"
                                         id="${salaryInstance.id}">
                                         ${fieldValue(bean: salaryInstance, field: "id")}
                                     </g:link>
                                 </td>--}%

                    <td>
                        ${fieldValue(bean: salaryInstance, field: "level")}
                    </td>

                    <td>
                        ${fieldValue(bean: salaryInstance, field: "designation")}
                    </td>

                    <td>
                        ${fieldValue(bean: salaryInstance, field: "basicSalary")}
                    </td>

                    <td id="linked"><g:link controller="salary" action="edit"
                            id="${salaryInstance?.id}" params="[offset:params.offset]">
                        <em>Edit</em>
                    </g:link>
                    </td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="paginateButtons">
        <g:paginate total="${salaryInstanceTotal}" params="[designation:designation,fromSalary:fromSalary,toSalary:toSalary]" />
    </div>


</div>
</div>
</body>
</html>
