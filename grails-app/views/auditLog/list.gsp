<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <title>Audit Log Event List</title>
    <g:javascript library="jquery" plugin="jquery" />
    <export:resource/>
    <jqui:resources />
    <script type="text/javascript">



    function filterList(){
        var updatedBy=$("#user").val();
        var className=$("#className").val();
        var eventName=$("#eventName").val()
        var startDate=$("#datepick1").val();
        var endDate=$("#datepick2").val();
        if(startDate && !endDate){
            alert("Please specify To Date");
            return false
        }
        if(!startDate && endDate){
            alert("Please specify From Date");
            return false
        }

        var parameter="updatedBy="+updatedBy+"&className="+className+"&startDate="+startDate+"&endDate="+endDate+"&eventName="+eventName;
    ${remoteFunction(controller:'auditLog',action:'ajaxCall',update:'list',params: "parameter")}
    }


        jQuery(function() {
            jQuery('#datepick1').datepicker({
                dateFormat: 'yy-mm-dd',
                changeMonth: true,
                changeYear: true,
                yearRange: "-20:+10",
                maxDate: '0'
            });
            jQuery('#datepick2').datepicker({
                dateFormat: 'yy-mm-dd',
                changeMonth: true,
                changeYear: true,
                yearRange: "-20:+10",
                maxDate: '0'
            });
        });

    </script>
</head>
<body>
%{--
    <div class="nav">
        <span class="menuButton"><a class="home"
            href="${resource(dir:'')}">Home</a> </span>
    </div>
    --}%
<div class="body">
    <h4>Audit Log</h4>
    <g:if test="${flash.message}">
        <div class="message">
            ${flash.message}
        </div>

    </g:if>
    <div align="right" class="printthis">
        <export:formats formats="['excel']" action="exportToExcel" params="" title="Export to Excel"/>

    </div>
    <div id="filters">
        <table width="100%" cellspacing="0" cellpadding="0" border="0">
            <tr>
                <th class="thead2">Updated By:</th>
                <th class="thead2"><g:select name="user" from="${hrm.Employee.list()}" optionKey="id" noSelection="['':'-Choose Employee-']" onchange="filterList()" value="${updatedByVal}"></g:select></th>
                <th class="thead2">Class Name</th>
                <th class="thead2"><g:select name="className" from="${className}" noSelection="['':'-Choose Class-']" onchange="filterList()" value="${classNameVal}" /> </th>
                <th class="thead2">Event Name</th>
                <th class="thead2"><g:select name="eventName" from="${eventName}" noSelection="['':'-Choose Event-']" onchange="filterList()" value="${eventNameVal}" /> </th>
                <th class="thead2">Date</th>
                <th class="thead2"><input id="datepick1" type="text" name="startDate" class="employee_txt"  placeholder="FromDate" value="${startDateVal}" /></th>
                <th class="thead2"><input id="datepick2" type="text" name="endDate" class="employee_txt"  placeholder="ToDate" value="${endDateVal}" /></th>
                <th class="thead2"><input type="button" onclick="filterList()" value="Search" class="employee_btn"></th>
            </tr>
        </table>
    </div>
    <br>

    %{--<g:hiddenField name="module" value="${module}"/>--}%
    <div id="list">
    <div class="list">
        <table>
            <thead>
            <tr>


                <g:sortableColumn property="actor" title="Updated BY" />

                <g:sortableColumn property="className" title="Class Name" />
                <g:sortableColumn property="eventName" title="Event Name" />
                <g:sortableColumn property="instance" title="Instance" />

                <g:sortableColumn property="propertyName" title="Property Name" />

                <g:sortableColumn property="oldValue" title="Old Value" />
                <g:sortableColumn property="newValue" title="New Value" />

                <g:sortableColumn property="dateCreated" title="Date Created" />


            </tr>
            </thead>
            <tbody>
            <g:each in="${auditLogEventInstanceList}" status="i"
                    var="auditLogEventInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td>
                        ${employeeNames.getAt(auditLogEventInstance.id)}
                    </td>
                    <td>
                        ${fieldValue(bean:auditLogEventInstance, field:'className')}
                    </td>
                    <td>
                        ${fieldValue(bean:auditLogEventInstance, field:'eventName')}
                    </td>
                    <td>
                        ${fieldValue(bean:auditLogEventInstance, field:'instance')}
                    </td>
                    <td>
                        ${fieldValue(bean:auditLogEventInstance, field:'propertyName')}
                    </td>
                    <td><g:if
                            test="${auditLogEventInstance.propertyName == 'password'}">
                        ******
                    </g:if> <g:else>
                        ${fieldValue(bean:auditLogEventInstance, field:'oldValue')}
                    </g:else></td>
                    <td><g:if
                            test="${auditLogEventInstance.propertyName == 'password'}">
                        ******
                    </g:if> <g:else>
                        ${fieldValue(bean:auditLogEventInstance, field:'newValue')}
                    </g:else>
                    </td>
                    <td><g:formatDate format="yyyy-MM-dd hh:mm"
                                      date="${auditLogEventInstance.dateCreated}" />
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="paginateButtons">
        <g:paginate total="${auditLogEventInstanceTotal}" params="[updatedBy:updatedBy,user:updatedByVal,startDate:startDateVal,endDate:endDateVal,className:classNameVal,eventName:eventNameVal,module:module]" />
    </div>
</div>
</div>
</body>
</html>
