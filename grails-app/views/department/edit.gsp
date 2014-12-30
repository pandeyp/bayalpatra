<%@page defaultCodec="none" %>
<%@ page import="com.bayalpatra.hrm.Department" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <g:set var="entityName" value="${message(code: 'departments.label', default: 'Department')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
    <g:javascript library="jquery" plugin="jquery"/>

    <link rel="stylesheet" href="${resource(dir: '/dist/themes/default',file: 'style.min.css')}"/>
    <script type="text/javascript" src="${resource(dir:'/dist/',file:'jstree.js')}"></script>

    <script type="text/javascript" class="source below">

        $(document).ready(function(){
            $("#popup").hide();
        });


        $(function () {

            $("#popup").jstree()

        });
        function selected(){

            $("#popup").show();
        }
        function showValueTextbox(val,name)
        {
            $("#depart_name").val(name);
            $("#parentId").val(val);
        }

/*        function Checked(isThisMainStore,isMainStore) {
            var correct = true;
            var mainStoreStatus = isThisMainStore;
            if($("#isSubStore").attr("checked") && $("#isMainStore").attr("checked")){
                correct = false;
                alert("Same department can\'t be main store and sub store at the same time. ")
            }

            else if(mainStoreStatus.toString() == "true"){
                correct = true;
            }
            else{
                if(isMainStore && $("#isMainStore").attr("checked")){
                    correct = false ;
                    alert("Main store already exists. ");
                }
            }

            return correct
        }*/
    </script>
</head>
<body>

<div class="body">
    <h4><g:message code="default.edit.label" args="[entityName]" /></h4>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${departmentsInstance}">
        <div class="errors">
            <g:renderErrors bean="${departmentsInstance}" as="list" />
        </div>
    </g:hasErrors>
    <g:form method="post">
        <g:hiddenField name="id" value="${departmentsInstance?.id}" />
        <g:hiddenField name="version" value="${departmentsInstance?.version}" />
        <g:hiddenField name="offset" value="${offset}" />
        <g:hiddenField name="oldDeptCode" value="${departmentsInstance?.rootId}"/>
        <div class="dialog">

            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="departments.name.label" default="Name *" /></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: departmentsInstance, field: 'name', 'errors')}">
                        <g:textField name="name" value="${departmentsInstance?.name}" />
                    </td>
                </tr>

%{--                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="isSubStore"><g:message code="departments.isSubStore.label" default="Is SubStore" /></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: departmentsInstance, field: 'isSubStore', 'errors')}">
                        <g:checkBox name="isSubStore" value="${departmentsInstance?.isSubStore}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="isMainStore"><g:message code="departments.isMainStore.label" default="Is MainStore" /></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: departmentsInstance, field: 'isMainStore', 'errors')}">
                        <g:checkBox name="isMainStore" value="${departmentsInstance?.isMainStore}"/>
                    </td>
                </tr>--}%


                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="parentId"><g:message code="departments.parentId.label" default="Parent Name" /></label>
                    </td>

                    <td valign="top" class="value ${hasErrors(bean: departmentsInstance, field: 'name', 'errors')}">

                        <g:textField id = "depart_name" name="depart_name" value="${pName}" onClick = "selected();" onblur= "if(this.value=='0'){document.getElementById('parentId').value='0';}" onfocus = "selected()" readonly = "true"/>
                        <g:hiddenField name="parentId" value="${pName?.id}" />
                    </td>



                </tr>

                </tbody>
            </table>
        </div>
        <div id="popup">${deptTree}</div>
        <div class="createbuttons">
            <g:actionSubmit class="savebutton" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}"/>

        </div>
    </g:form>
</div>
</body>
</html>