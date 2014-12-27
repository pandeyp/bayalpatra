<%@ page import="com.bayalpatra.hrm.UserRole" contentType="text/html;charset=UTF-8" %>
<html>

<head> <meta name="layout" content="main_hrm" />
    <g:javascript library="jquery" plugin="jquery"/>

</head>

<body>
<div class="body">

    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>

    <g:form action="update" name='userEditForm' class="button-style">
        <g:hiddenField name="offset" value="${offset}"/>
        <sec:ifAnyGranted roles="ROLE_ADMIN">

        </sec:ifAnyGranted>
        <div class="my_profile">
            <table class="userAdmin">
                <tbody>
                <g:hiddenField name="user" value="${userRole.user.id}"/>
                <tr>
                    <sec:ifAnyGranted roles="ROLE_ADMIN">
                        <td valign="middle">
                            <s2ui:textFieldRow  name='username' labelCode='user.username.label' bean="${user}"
                                                labelCodeDefault='Username' value="${userRole.user.username}"/>
                        </td>
                    </sec:ifAnyGranted>
                    <sec:ifNotGranted roles = "ROLE_ADMIN">
                        <td valign="middle">
                            <s2ui:textFieldRow  name='username' labelCode='user.username.label' bean="${user}"
                                                labelCodeDefault='Username' value="${userRole.user.username}" disabled="true"/>
                        </td>
                    </sec:ifNotGranted>
                    <td>
                        <s2ui:passwordFieldRow name='password' labelCode='user.password.label' bean="${user}"
                                               labelCodeDefault='Password' value="${userRole.user.password}"/>
                    </td>
                </tr>

                <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <tr>
                        <td>
                            <s2ui:checkboxRow name='enabled' labelCode='user.enabled.label' bean="${user}"
                                              labelCodeDefault='Enabled' value="${userRole.user.enabled}"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            %{--<s2ui:checkboxRow name='isSuperAdmin' labelCode='Is Super Admin' labelCodeDefault='Is Super Admin' bean="${user}" value=""/>--}%
                        </td>
                    </tr>

%{--
                    <tr>
                        <td>Module</td>
                        <td><g:if test="${userRole.role?.authority=='ROLE_Visiting_Doctor'}">
                            <g:select name="mod" from="${userRole.constraints.module.inList}" value="${userRole?.module}" noSelection="['':'-Choose One-']" disabled="true" />
                            <input type="hidden" name="module" value="${userRole?.module}" />
                        </g:if>
                        <g:else>
                            <g:select name="module" from="${userRole.constraints.module.inList}" value="${userRole?.module}" noSelection="['':'-Choose One-']"
                                      onchange="${remoteFunction(controller:'user',action:'moduleWiseRole',params:'\'module=\'+this.value+\'&username='+userRole.user.username+'\'',update:'roleList')}" />

                        </g:else>
                        </td>

                    </tr>
--}%
                    <tr>
                        <td>Select Role</td>
                        <td id ="roleList">
                            %{--<g:render template="moduleWiseRole" ></g:render>--}%

                            <p class="select_role">Select Role:</p>
                        %{-- if new role is being created , radio button isn't checked ; else checked--}%
                            <g:if test="${newModule == true}">
                                <g:each in="${authList}" var="auth" >
                                    <div class="administrator_part">
                                        <input type="radio" name="authority" value="${auth.id}"/>
                                        ${auth.authority}
                                    </div>
                                </g:each>
                            </g:if>
                            <g:else>
                                <g:each in="${authList}" var="auth" >
                                    <div class="administrator_part">
                                        <input type="radio" name="authority" value="${auth.id}"
                                               <g:if test="${auth.authority==userRole.role.authority}">checked</g:if> />
                                        ${auth.authority}
                                    </div>
                                </g:each>
                            </g:else>
                            <br/>

                        </td>

                    </tr>
                </sec:ifAnyGranted>

                <tr>
                    <td></td>
                    <td> <input type="submit" value="Update"  class="savebutton"></td>
                </tr>

                </tbody>
            </table>

        </div>

    </g:form>
</div>


<script>
    $(document).ready(function() {
        $('#username').focus();
        <s2ui:initCheckboxes/>
    });
</script>
</body>
</html>