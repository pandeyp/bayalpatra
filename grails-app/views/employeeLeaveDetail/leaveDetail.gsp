
<%@ page import="commons.BayalpatraConstants; com.bayalpatra.hrm.EmployeeLeaveDetail"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <g:set var="entityName"
           value="${message(code: 'employeeLeaveDetail.label', default: 'Employee Leave Detail')}" />
    <title><g:message code="default.list.label" args="[entityName]" />
    </title>
    <g:javascript library="jquery" plugin="jquery"/>
    <jqui:resources/>
    <script language="javascript" type="text/javascript" src="${resource(dir:'js',file:'SeeMore.js')}"></script>
    <script type="text/javascript">

        $(function(){
            $(".seeMore").each(function(){
                $(this).seeMore();
            });
        })

        function getDate(){
            var date = $("#datepick").val()  ;
            var parameter = "date="+date;
        ${remoteFunction(controller:'employeeLeaveDetail',action:'filterLeaveList',update:'grid', params: "parameter" )}
        }

        jQuery(function() {
            jQuery('#datepick').datepicker({dateFormat: 'yy-mm-dd'});

        });
    </script>
</head>
<body>

<div class="body">


    <div align="right" class="printthis">


        <export:formats formats="['excel']" params=""
                        title="Export to Excel" />

        <div class="print">
            %{-- <a href="#" title="print"> <img
         src="${resource(dir: 'images', file: "print_icon.png")}"
         alt="Print Table" onclick="window.print()"> </a>--}%
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

    <br />
    <div class="list">

        <tr class="prop">

            <td   valign="top" class="name">
                <label for="date"><g:message code="employeeLeaveDetail.fromDate.label" default="Date :" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: employeeLeaveDetailInstance, field: 'date', 'errors')}"> &nbsp;&nbsp;

                <input id="datepick"  value="${defaultDate}" type="text" name="date" class="employee_txt" >&nbsp;
                <input type="button"   onclick="getDate()" value="Search" class="employee_btn">

            </td>
        </tr>


        <br/>
        <br/>

        <div id="grid">
            <table>
                <thead>
                <tr>
                    <th><g:message code="employeeLeaveDetail.employee.label"
                                   default="Employee" /></th>

                    <th><g:message code="employeeLeaveDetail.leaveType.label"
                                   default="Leave Type" /></th>


                    <th>From Date</th>
                    <th>To Date</th>
                    <th>Leave Days</th>
                    <th>Half</th>
                    <g:if test="${commons.AnnapurnaConstants.CLIENT_NAME==AnnapurnaConstants.CLIENT_DEERWALK}">
                        <th>Leave Reason</th>
                    </g:if>
                </tr>
                </thead>
                <tbody>

                <g:each in="${employeeLeaveDetailInstance}" status="i"
                        var="employeeLeaveDetailInstance">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>
                            ${fieldValue(bean: employeeLeaveDetailInstance, field: "employee")}
                        </td>


                        <td>
                            ${fieldValue(bean: employeeLeaveDetailInstance, field: "leaveType")}
                        </td>

                        <td><g:formatDate format="yyyy-MM-dd"
                                          date="${employeeLeaveDetailInstance.fromDate}" /></td>

                        <td><g:formatDate format="yyyy-MM-dd"
                                          date="${employeeLeaveDetailInstance.toDate}" /></td>

                        <td>
                            ${fieldValue(bean: employeeLeaveDetailInstance, field: "leaveDifference")}
                        </td>
                        <td>${employeeLeaveDetailInstance?.whichHalf}</td>
                        <g:if test="${commons.AnnapurnaConstants.CLIENT_NAME==AnnapurnaConstants.CLIENT_DEERWALK}">
                            <td>
                                <span class="seeMore" id="seeMore${i}">${fieldValue(bean: employeeLeaveDetailInstance, field: "leaveReason")}</span>
                            </td>
                        </g:if>
                    </tr>

                </g:each>
                </tbody>
            </table>
            <div class="paginateButtons">
                <g:paginate total="${employeeLeaveDetailInstanceTotal} " action="dailyLeaveDetail"/>
            </div>
        </div>


    </div>
</div>




</body>
</html>
