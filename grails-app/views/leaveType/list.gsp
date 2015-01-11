
<%@ page import="commons.BayalpatraConstants; com.bayalpatra.hrm.LeaveType" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <export:resource/>
    <g:set var="entityName" value="${message(code: 'leaveType.label', default: 'Leave Type')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
%{--<div class="nav">--}%
%{--<span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>--}%
%{--<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>--}%
%{--</div>--}%
<div class="body">

    <div align="right" class="printthis">
        <export:formats formats="['excel']" params="['exportFormat':'excel']" title="Export to Excel"/>

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
        <g:link controller="leaveType" action="create">
            <img src="${resource(dir: 'images', file: "add.jpg")}"> Add</g:link>
    </div>

</br>

    <div class="list">
        <table>
            <thead>
            <tr>

                %{--<g:sortableColumn property="id" title="${message(code: 'leaveType.id.label', default: 'Id')}" />--}%

                <g:sortableColumn property="leaveType" title="${message(code: 'leaveType.leaveType.label', default: 'Leave Type')}" />

                <g:sortableColumn property="status" title="${message(code: 'leaveType.status.label', default: 'Status')}" />

                <g:sortableColumn property="paidUnpaid" title="${message(code: 'leaveType.paidUnpaid.label', default: 'Paid Unpaid')}" />

                <g:sortableColumn property="days" title="${message(code: 'leaveType.days.label', default: 'Days')}" />

                <th>Action</th>

            </tr>
            </thead>
            <tbody>
            <g:each in="${leaveTypeInstanceList}" status="i" var="leaveTypeInstance">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    %{--<td><g:link action="show" id="${leaveTypeInstance.id}">${fieldValue(bean: leaveTypeInstance, field: "id")}</g:link></td>--}%

                    <td>${fieldValue(bean: leaveTypeInstance, field: "leaveType")}</td>

                    <td>${fieldValue(bean: leaveTypeInstance, field: "status")}</td>

                    <td>${fieldValue(bean: leaveTypeInstance, field: "paidUnpaid")}</td>

                    <td>${fieldValue(bean: leaveTypeInstance, field: "days")}</td>

                    <g:if test="${leaveTypeInstance.leaveType == BayalpatraConstants.DAY_OFF_LEAVE || leaveTypeInstance.leaveType == BayalpatraConstants.NIGHT_OFF_LEAVE || leaveTypeInstance.leaveType == BayalpatraConstants.FESTIVAL_OFF_LEAVE || leaveTypeInstance.leaveType == BayalpatraConstants.SUBSTITUTE_LEAVE}">
                    <td></td>
                    </g:if>
                    <g:else>
                        <td id="linked"><g:link controller="leaveType" action="edit" id="${leaveTypeInstance.id}" params="[offset:params.offset]">Edit </g:link></td>
                    </g:else>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <div class="paginateButtons">
        <g:paginate total="${leaveTypeInstanceTotal}" />
    </div>
</div>
</body>
</html>
