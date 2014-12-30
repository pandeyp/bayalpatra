<%@page defaultCodec="none" %>
<%@ page import="com.bayalpatra.hrm.Department" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <g:set var="entityName" value="${message(code: 'departments.label', default: 'Departments')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
    <g:javascript library="jquery" plugin="jquery"/>

    <link rel="stylesheet" href="${resource(dir: '/dist/themes/default',file: 'style.min.css')}"/>
    <script type="text/javascript" src="${resource(dir:'/dist/',file:'jstree.js')}"></script>


    <script type="text/javascript" class="source below">
        $(document).ready(function(){
            $('#popup').hide();
        });

        $(function () {
            $('#popup').jstree();
        });

        function selected(){
            $('#popup').show();
        }

        function showValueTextbox(val,name,pid)
        {
            if(pid!=0){
                $("#depart_name").val(name);
                $("#parentId").val(val);
            }
        }
    </script>
</head>
<body>
<div class="body">
    <h4><g:message code="default.create.label" args="[entityName]" /></h4>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${departmentsInstance}">
        <div class="errors">
            <g:renderErrors bean="${departmentsInstance}" as="list" />
        </div>
    </g:hasErrors>
    <g:form action="save">
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td class="name">
                        <label for="name"><g:message code="departments.name.label" default="Name *" /></label>
                    </td>
                    <td class="value ${hasErrors(bean: departmentsInstance, field: 'name', 'errors')}">
                        <g:textField name="name" value="${departmentsInstance?.name}" />
                    </td>
                </tr>

                <tr class="prop">
                    <td class="name">
                        <label for="parentId"><g:message code="departments.parentId.label" default="Parent Department" /></label>
                    </td>

                    <td class="value ${hasErrors(bean: departmentsInstance, field: 'name', 'errors')}">

                        <g:textField id = "depart_name" name="depart_name" value="${parentDept?.name}" onClick = "selected();" onblur= "if(this.value=='0'){document.getElementById('parentId').value='0';}" onfocus = "selected();" readonly = "true"/>
                        <g:hiddenField name="parentId" value="${fieldValue(bean: departmentsInstance, field: 'parentId')}" />
                    </td>
                </tr>

                </tbody>
            </table>
        </div>
        <div id="popup">
          ${deptTree}
        </div>

        <div class="createbuttons">
            <g:submitButton name="create"  class="savebutton" value="${message(code: 'default.button.create.label', default: 'Create')}" />
        </div>

    </g:form>
</div>
</body>
</html>