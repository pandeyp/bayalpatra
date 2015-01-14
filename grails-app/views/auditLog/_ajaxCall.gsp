<%@ page contentType="text/html;charset=UTF-8" %>
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
    <g:paginate action="list" total="${auditLogEventInstanceTotal}" params="[updatedBy:updatedBy,user:user,startDate:startDate,endDate:endDate,className:className,eventName:eventName,module:module]" />
</div>
