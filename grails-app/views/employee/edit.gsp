<%@page defaultCodec="none" %>
<%@ page import="com.bayalpatra.hrm.SalaryClass; com.bayalpatra.hrm.Designation; commons.BayalpatraConstants; com.bayalpatra.hrm.Employee" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <g:set var="entityName" value="${message(code: 'employee.label', default: 'Employee')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>

    <g:javascript library="jquery" plugin="jquery"/>
    <jqui:resources/>
    <link rel="stylesheet" href="${resource(dir: '/dist/themes/default',file: 'style.min.css')}"/>
    <script type="text/javascript" src="${resource(dir:'/dist/',file:'jstree.js')}"></script>

    <script type="text/javascript">



        $(document).ready(function(){
            $("#gradeReward").attr("disabled", "disabled");
        });

        $(function () {

            $("#popup").jstree()

        });


        function showValueTextbox(val,name)
        {
            var parameter = "val="+val;
            var empId = ${employeeInstance.id}
                    checkEdited('department', empId)
            $("#change_department").val(name);
            $("#department").val(val);
           /* alert($("#change_department").val())
            alert( $("#department").val())
            alert(val)*/

        }

        function validateBound(empId){
            var status0=$("#status").val();
            if(status0 == 'Terminated'){
                var parameter = "employee="+empId;
                var url="${createLinkTo(dir: '/')}employee/checkBoundPeriod"
                var handler  = function callbackHandler(warningMessage){
                    alert(warningMessage);
                };

                $.post(url,{employee: empId},handler,"html");
            }
        }


        function checkEdited(id,empId){
            var serviceType = $("#"+id).val();

            var designationEdited = ${designationEdited}
            var unitEdited = ${unitEdited}
            var statusEdited = ${statusEdited}
            var departmentEdited = ${departmentEdited}
                    addTextBox(serviceType);
            if(id=='designation.id'){
                designationEdited=true
                $("#designationEdited").val(designationEdited)
            }else if(id=='unit.id'){
                unitEdited=true
                $("#unitEdited").val(unitEdited)
            }else if(id=='statusChangedTo'){
                statusEdited=true
                $("#statusEdited").val(statusEdited)
                validateBound(empId)
            }else if(id=='department'){
                departmentEdited=true
                $("#departmentEdited").val(departmentEdited)
            }

            $("#gradeReward").removeAttr("disabled");

        }

        function validate(){
            if($("#statusChangedTo").val()=="Volunteer"){
                if($("#noDays").val()==""){
                    alert ( "Please enter days for 'Changed To' volunteer" );
                    return false;
                }
            }
            if($("#statusChangedTo").val()=="Suspended"){
                if($("#suspensionDays").val()==""){
                    alert ( "Please enter days for 'Changed To' suspend" );
                    return false;
                }
            }

        }
        function restrictSelfSupervision(){

            var content = $("#noDays").val();

            var emp=$('#firstName').val()+'  '+$('#lastName').val()+'-'+$('#employeeId').val()
            var sup=$('#supervisor').find('option:selected').text();
            if(emp == sup){
                alert('Employee and Supervisor must be different')
                return false;
            }else if($("#serviceType").val()=="Volunteer"||$("#serviceType").val()=="Suspended"){
                if(content==""){
                    alert('Please enter days in Service Type')
                    return false;
                }
                if(isNaN(content)){
                    alert('Please enter numeric values for days in Service Type')
                    return false;
                }
            }else if($("#change_department").val()){
                console.log('dept being changed');
                if(($("#change_department").val() != $("#depart_name").val()) && (!$("#effectiveDateForDepartment").val())){
                    alert('Please enter effective date of department change');
                    return false;
                }
            }
            else{
                return true;
            }


        }




        function addTextBox(val){
            var txtBox
            var date
            if(val=='Volunteer'){
                //  $(".remVol").remove();
                $("#addTextBox").remove();
                $("#remDays").remove();
                $(".remDate").remove();
                $("#dateBox").remove();
                $("#dateBox2").remove();

                txtBox ='<td id ="addTextBox"> <div class="days-edit-empl">Days:&nbsp;<input id="noDays" type ="text" value ="" name ="volunteerDays" class="days_no" maxlength="4"></div></td>';
                $("#x").append(txtBox);
            }else if(val=='Suspended'){
                $(".remDate").remove();
                $("#addTextBox").remove();
                $("#dateBox").remove();
                $("#dateBox2").remove();

                txtBox ='<td id ="addTextBox"> <div class="days-edit-empl">Days:&nbsp;<input id="suspensionDays" type ="text" value ="" name ="suspensionDays" class="days_no" maxlength="4"></div></td>';
                date='<td id="dateBox"> Effective Date:</td><td id="dateBox2"><input name="dateEffective" id="effectiveDate" onmouseover=\"javascript:selectDate();\" type="text" value="${formatDate(format: 'yyyy-MM-dd',date: employeeInstance?.effectiveDate)}"></td>'

                $("#x").append(txtBox);
                $("#changeStatus").append(date);


            }
            else if(val=='Permanent' || val=='Terminated'){
                //  $(".remVol").remove();
                $("#addTextBox").remove();
                $("#remDays").remove();
                $(".remDate").remove();
                $("#dateBox").remove();
                $("#dateBox2").remove();

                date='<td id="dateBox"> Effective Date:</td><td id="dateBox2"><input name="dateEffective" id="effectiveDate" onmouseover=\"javascript:selectDate();\" type="text" value="${formatDate(format: 'yyyy-MM-dd',date: employeeInstance?.effectiveDate)}"></td>'
                $("#changeStatus").append(date);

            }
            else{
                $("#remDays").remove();
                $(".remDate").remove();
                //   $(".remVol").remove();
                $("#addTextBox").remove();
                $("#dateBox").remove();
                $("#dateBox2").remove();


            }

        }
        function selectDate(){
            $("#effectiveDate").datepicker({
                dateFormat: 'yy-mm-dd',
                changeMonth:true,
                changeYear: true,
                yearRange: "-0:+5",
                minDate: "0"
            });
            $("#effectiveDateForDepartment").datepicker({
                dateFormat: 'yy-mm-dd',
                changeMonth:true,
                changeYear: true,
                yearRange: "-0:+5",
                minDate: "0"
            })
        }

    </script>

</head>
<body>

<div class="body">
<h4><g:message code="default.edit.label" args="[entityName]" /></h4>

<g:render template="menu"/>


<g:if test="${flash.message}">
    <div class="messageForMenuBar">${flash.message}</div>
</g:if>
<div class="personal_detail">
<g:hasErrors bean="${employeeInstance}">
    <div class="errors">
        <g:renderErrors bean="${employeeInstance}" as="list" />
    </div>
</g:hasErrors>

<g:form method="post" enctype="multipart/form-data" onSubmit="return restrictSelfSupervision()" >
<g:hiddenField name="id" value="${employeeInstance?.id}" />
<g:hiddenField name="designationEdited" value=""/>
<g:hiddenField name="unitEdited" value=""/>
<g:hiddenField name="statusEdited" value=""/>
<g:hiddenField name="departmentEdited" value=""/>
<g:hiddenField name="offset" value="${offset}"/>
<g:hiddenField name="version" value="${employeeInstance?.version}" />


<div class="dialog">
<table>
<tbody>

<tr class="prop">
    <td valign="top" class="name">
        <label for="employeeId"><g:message code="employee.employeeId.label" default="Employee ID*" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'employeeId', 'errors')}">
        <g:textField name="employeeId" value="${employeeInstance?.employeeId}" readonly="true" disabled="true"/>
    </td>
</tr>



<tr class="prop">

    <td valign="top" class="name">
        <label for="firstName"><g:message code="employee.firstName.label" default="First Name *" /></label>

    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'firstName', 'errors')}">
        <g:textField name="firstName" value="${employeeInstance?.firstName}" />
    </td>
    <td valign="top" class="name">
        <label for="lastName"><g:message code="employee.lastName.label" default="Last Name *" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'lastName', 'errors')}">
        <g:textField name="lastName" value="${employeeInstance?.lastName}" />
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="middleName"><g:message code="employee.middleName.label" default="Middle Name" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'middleName', 'errors')}">
        <g:textField name="middleName" value="${employeeInstance?.middleName}" />
    </td>
    <td valign="top" class="name">
        <label for="supervisor.id"><g:message code="employee.supervisor.label" default="Supervisor" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'supervisor', 'errors')}">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:select name="supervisor.id" id="supervisor" from="${supervisorList}" optionKey="id" value="${employeeInstance?.supervisor?.id}" noSelection="['null':'-Choose One-']"  />
        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_DEPARTMENTHEAD,ROLE_EMPLOYEE,ROLE_SUPERVISOR">
            <g:select name="supervisor.id" id="supervisor" from="${supervisorList}" optionKey="id" value="${employeeInstance?.supervisor?.id}" noSelection="['null':'-Choose One-']"  disabled="true"/>
        </sec:ifAnyGranted>
    </td>
</tr>


<tr class="prop">
    <td valign="top" class="name">
        <label for="dateOfBirth"><g:message code="employee.dateOfBirth.label" default="Date Of Birth" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'dateOfBirth', 'errors')}">
        <g:set var="startYr" value="${new GregorianCalendar().get(Calendar.YEAR)-75}"/>
        <g:set var="endYr" value="${new GregorianCalendar().get(Calendar.YEAR)}"/>

        <g:datePicker name="dateOfBirth" precision="day" value="${employeeInstance?.dateOfBirth}" years="${startYr..endYr}" />
    </td>
    <td valign="top" class="name">
        <label for="joinDate"><g:message code="employee.joinDate.label" default="Join Date" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'joinDate', 'errors')}">
        <g:set var="startYr" value="${new GregorianCalendar().get(Calendar.YEAR)-50}"/>
        <g:set var="endYr" value="${new GregorianCalendar().get(Calendar.YEAR)}"/>
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:datePicker name="joinDate" precision="day" value="${employeeInstance?.joinDate}" years="${startYr..endYr}" />
        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_DEPARTMENTHEAD,ROLE_EMPLOYEE,ROLE_SUPERVISOR">
            <label><g:formatDate format="yyyy-MM-dd" date="${employeeInstance?.joinDate}" /></label>
        </sec:ifAnyGranted>
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="status"><g:message code="employee.status.label" default="Service Type *" /></label>
    </td>
    <td class="value ${hasErrors(bean: employeeInstance, field: 'status', 'errors')}">
        ${employeeInstance.status}
    </td>


%{--    <g:if test="${employeeInstance?.status == 'Volunteer' ||  employeeInstance?.status == 'Probation'}" >
        <td class ="remVol"> Days</td>
        <td class ="remVol"><g:textField name="volunteerDays" value="${employeeInstance?.volunteerDays}" class="days_no" /></td>
    </g:if>
    <g:elseif test="${employeeInstance?.status == 'Suspended'}">
        <td class ="remVol"> Days</td>
        <td class ="remVol"><g:textField  name="suspensionDays" value="${employeeInstance?.suspensionDays}" class="days_no" /></td>
    </g:elseif>--}%


</tr>
<tr id="changeStatus" class="prop">
    <td valign="top" class="name">
        <label for="statusChangedTo"><g:message code="employee.statusChangedTo.label" default="Changed To" /></label>
    </td>
    <td id = "x" valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'status', 'errors')}">

        <sec:ifAnyGranted roles="ROLE_ADMIN">

            <g:select id="statusChangedTo" name="statusChangedTo" from="${employeeInstance.constraints.status.inList}" value="${employeeInstance?.statusChangedTo}" valueMessagePrefix="employee.statusChangedTo" noSelection="['':'Choose One']" onchange="checkEdited(this.id,${employeeInstance?.id});" />
        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_DEPARTMENTHEAD,ROLE_EMPLOYEE,ROLE_SUPERVISOR">
            <g:select id="statusChangedTo" name="statusChangedTo" from="${employeeInstance.constraints.status.inList}" value="${employeeInstance?.statusChangedTo}" valueMessagePrefix="employee.statusChangedTo" noSelection="['':'Choose One']" onchange="checkEdited(this.id,${employeeInstance?.id});" disabled="true"/>
        </sec:ifAnyGranted>
        <g:if test="${employeeInstance?.statusChangedTo == 'Suspended'}">
            <p id="remDays">&nbsp;Days:&nbsp;<g:textField  id="suspensionDays" name="suspensionDays" value="${employeeInstance?.suspensionDays}" class="days_no" /></p>
        </g:if>
    </td>


    <g:if test="${employeeInstance?.statusChangedTo == 'Permanent' ||  employeeInstance?.statusChangedTo == 'Terminated'}" >

        <td class="remDate"> Effective Date:</td>
        <td class="remDate"><input name="dateEffective" id="effectiveDate" onmouseover="javascript:selectDate();" type="text" value="${formatDate(format: 'yyyy-MM-dd',date: employeeInstance?.effectiveDate)}">
        </td>
    </g:if>
    <g:elseif test="${employeeInstance?.statusChangedTo == 'Suspended'}">
        <td class="remDate"> Effective Date:</td>
        <td class="remDate"><input name="dateEffective" id="effectiveDate" onmouseover="javascript:selectDate();" type="text" value="${formatDate(format: 'yyyy-MM-dd',date: employeeInstance?.effectiveDate)}">
        </td>
    </g:elseif>

</tr>


<tr class="prop">
    <td valign="top" class="name">
        <label for="maritalStatus"><g:message code="employee.maritalStatus.label" default="Marital Status *" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'maritalStatus', 'errors')}">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:select name="maritalStatus" from="['Single','Married','Divorced']" noSelection="['':'--Choose One--']" value="${employeeInstance?.maritalStatus}"/>
        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_DEPARTMENTHEAD,ROLE_EMPLOYEE,ROLE_SUPERVISOR">
            <g:select name="maritalStatus" from="['Single','Married','Divorced']" noSelection="['':'--Choose One--']" value="${employeeInstance?.maritalStatus}" disabled="true"/>
        </sec:ifAnyGranted>
        <%--<g:textField name="maritalStatus" value="${employeeInstance?.maritalStatus}" /> --%>
    </td>

    <td valign="top" class="name">
        <label for="gender"><g:message code="employee.gender.label" default="Gender *" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'gender', 'errors')}">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:radioGroup name="gender" labels="['Male','Female']" values="['Male','Female']" value="${employeeInstance?.gender}" >
                <g:message code="${it.label}" />: ${it.radio}
            </g:radioGroup>
        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_DEPARTMENTHEAD,ROLE_EMPLOYEE,ROLE_SUPERVISOR">
            <g:textField name="gender" value="${employeeInstance?.gender}" readonly="true" disabled="true"/>
        </sec:ifAnyGranted>
    </td>

</tr>



<tr class="prop">
    <td valign="top" class="name">
        <label for="country"><g:message code="employee.country.label" default="Country *" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'country', 'errors')}">
        <g:countrySelect name="country"  noSelection="['':'--Select Your Country --']" value="${employeeInstance?.country}"/>
        <%--<g:textField name="country" value="${employeeInstance?.country}" /> --%>
    </td>
    <td valign="top" class="name">
        <label for="nationality"><g:message code="employee.nationality.label" default="Nationality" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'nationality', 'errors')}">
        <g:textField name="nationality" value="${employeeInstance?.nationality}" />
    </td>
</tr>


<tr class="prop">
    <td valign="top" class="name">
        <label for="permanentAddress"><g:message code="employee.permanentAddress.label" default="Permanent Address *" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'permanentAddress', 'errors')}">
        <g:textArea name="permanentAddress" value="${employeeInstance?.permanentAddress}" />
    </td>

    <td valign="top" class="name">
        <label for="temporaryAddress"><g:message code="employee.temporaryAddress.label" default="Temporary Address" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'temporaryAddress', 'errors')}">
        <g:textArea name="temporaryAddress" value="${employeeInstance?.temporaryAddress}" />
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="homePhone"><g:message code="employee.homePhone.label" default="Home Phone" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'homePhone', 'errors')}">
        <g:textField name="homePhone" value="${employeeInstance?.homePhone}" />
    </td>
%{--    <td valign="top" class="name">
        <label for="workPhone"><g:message code="employee.workPhone.label" default="Work Phone" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'homePhone', 'errors')}">
        <g:textField name="workPhone" value="${employeeInstance?.workPhone}" />
    </td>--}%
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="mobile"><g:message code="employee.mobile.label" default="Mobile" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'mobile', 'errors')}">
        <g:textField name="mobile" value="${employeeInstance?.mobile}" />
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="email"><g:message code="employee.email.label" default="Email *" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'email', 'errors')}">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:textField name="email" value="${employeeInstance?.email}" />
        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_DEPARTMENTHEAD,ROLE_EMPLOYEE,ROLE_SUPERVISOR">
            <g:textField name="email" value="${employeeInstance?.email}" readonly="true" disabled="true"/>
        </sec:ifAnyGranted>
    </td>
    <td valign="top" class="name">
        <label for="alterEmail"><g:message code="employee.alterEmail.label" default="Alternative Email" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'alterEmail', 'errors')}">
        <g:textField name="alterEmail" value="${employeeInstance?.alterEmail}" />
    </td>
</tr>

<tr class="prop">

    <td valign="top" class="name">
        <label for="departments.id"><g:message code="employee.departments.label" default="Department" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'departments', 'errors')}">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:textField id = "depart_name" name="depart_name" readonly = "true" value="${employeeInstance.department}"/>
            %{--<g:hiddenField id="department" name="departments.id" value="${employeeInstance.departments.id}"/>--}%
        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_DEPARTMENTHEAD,ROLE_EMPLOYEE,ROLE_SUPERVISOR">
            <g:textField id = "depart_name" name="depart_name" readonly = "true" value="${employeeInstance.department}" disabled="true"/>
        </sec:ifAnyGranted>
    </td>


</tr>
<tr class="prop">

    <td valign="top" class="name">
        <label for="changeDepartments.id"><g:message code="employee.changeDepartments.label" default="Department Change" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'changeDepartment', 'errors')}">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:textField id = "change_department" name="change_department" readonly = "true" value="${employeeInstance.changeDepartment}"/>

           <g:hiddenField id="department" name="changeDepartment" value="${employeeInstance.changeDepartment}" />

        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_DEPARTMENTHEAD,ROLE_EMPLOYEE,ROLE_SUPERVISOR">
            <g:textField id = "change_department" name="change_department" readonly = "true" value="${employeeInstance.changeDepartment}" disabled="true"/>
        </sec:ifAnyGranted>
    </td>
    <td>
        Effective Date:
    </td>
    <td>
    <input name="dateForDepartment" id="effectiveDateForDepartment" onmouseover="javascript:selectDate();" type="text" value="${formatDate(format: 'yyyy-MM-dd',date: employeeInstance?.effectiveDateForDepartment)}">
    </td>


</tr>
<tr>
    <td></td>
    <td>
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <div id="popup">${deptTree}</div>
        </sec:ifAnyGranted>
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="designation.id"><g:message code="employee.designation.label" default="Designation" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'designation', 'errors')}">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:select name="designation.id" from="${com.bayalpatra.hrm.Designation.list()}" optionKey="id" value="${employeeInstance?.designation?.id}" onchange="checkEdited(this.id,${employeeInstance?.id});"  />
        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_DEPARTMENTHEAD,ROLE_EMPLOYEE,ROLE_SUPERVISOR">
            <g:select name="designation.id" from="${Designation.list()}" optionKey="id" value="${employeeInstance?.designation?.id}" onchange="checkEdited(this.id,${employeeInstance?.id});" disabled="true"/>
        </sec:ifAnyGranted>
    </td>

    <sec:ifAnyGranted roles="ROLE_ADMIN">
        <td valign="top" class="name">
            <label for="promotionDate"><g:message code="employee.promotionDate.label" default="Promotion Date" /></label>
        </td>
        <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'promotionDate', 'errors')}">
            <g:set var="startYr" value="${new GregorianCalendar().get(Calendar.YEAR)-50}"/>
            <g:set var="endYr" value="${new GregorianCalendar().get(Calendar.YEAR)}"/>
            <g:datePicker name="promotionDate" precision="day" value="${employeeInstance?.promotionDate}" years="${startYr..endYr}" />
        </td>
    </sec:ifAnyGranted>
</tr>

<g:if test="${BayalpatraConstants.CLIENT_NAME==BayalpatraConstants.CLIENT_BAYALPATRA}">
    <td valign="top" class="value">
        <label for="status"><g:message code="employee.isDoc.label" default="Is Doctor" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'isDoc', 'errors')}">
        <g:checkBox id="isDoc" name="isDoc" value="${employeeInstance?.isDoc}"/>&nbsp;&nbsp;&nbsp;Yes (✔)
    </td>

</g:if>
%{--




<tr class="prop">
    <td valign="top" class="name">
        <label for="salaryclass.id"><g:message code="employee.salaryclass.label" default="Pay Date Class *" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'salaryclass', 'errors')}">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:select name="salaryclass.id" from="${SalaryClass.list()}" optionKey="id" value="${employeeInstance?.salaryclass?.id}"  />
        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_DEPARTMENTHEAD,ROLE_EMPLOYEE,ROLE_SUPERVISOR">
            <g:select name="salaryclass.id" from="${com.bayalpatra.hrm.SalaryClass.list()}" optionKey="id" value="${employeeInstance?.salaryclass?.id}"  disabled="true"/>
        </sec:ifAnyGranted>
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        Upload Image:
        <br>
    </td>
    <td valign="top" class="value">
        <input type="file" name="emp_image" />
    </td>
</tr>
--}%

</tbody>
</table>
</div>


<%--<sec:ifAllGranted roles="ROLE_HR_Admin">
--%><g:if test="${statusFlag}">
    <div class="employee_edit">
        <g:actionSubmit class="savebutton" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" onclick="return validate();" />
    </div>
</g:if>
<%--</sec:ifAllGranted>

--%></g:form>
</div>
</div>
</body>
</html>
