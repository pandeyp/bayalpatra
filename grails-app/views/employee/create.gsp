

<%@ page import="commons.BayalpatraConstants com.bayalpatra.hrm.Employee" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <g:set var="entityName" value="${message(code: 'employee.label', default: 'Employee')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>

    <g:javascript library="jquery" plugin="jquery"/>
    <jqui:resources/>
    <jsTree:resources />

    <script type="text/javascript">

        $(document).ready(function(){

            var txtBox ='<td id = "volunteerDays" class="days">Days&nbsp;:&nbsp;<input type ="text" value ="" name ="volunteerDays" class="days_no" maxlength="4" id ="box_for_days"></td>';
            $(txtBox).hide();


            $("#username").val("")
            $("#password").val("")

            var temp =  $("#service").val()
            var vol_days =  $("#v_days").val()
            if(temp=='Volunteer'){
                $("#x").append(txtBox);
                $("#box_for_days").val(vol_days);
            }
            if(temp=='Probation'){
                $("#x").append(txtBox);
                $("#box_for_days").val(vol_days);
            }

            $("#gradeReward").attr("disabled", "disabled")

            if($("#isDocChecked").val()=='true'){
                $("#isDoc").attr('checked','checked');
            }

        });

        $(function () {

            $("#popup").jstree()

            $("#effectiveDate").datepicker({
                dateFormat: 'yy-mm-dd',
                changeMonth: true,
                changeYear: true,
                yearRange: "-10:+10",
                maxDate: '0'
            });


        });

        function selected(){
            $("#popup").show();
        }

        function showValueTextbox(val,name)
        {
            //alert(val)
            var parameter = "val="+val;

            $("#depart_name").val(name);
            $("#department").val(val);

        }

        function checkUserName(){

            var username = $("#username").val()
            var password = $("#password").val()
            /*var password2 = $("#password2").val()*/
            if(username && password){
                return true
            }else{
                alert("User Name or Password required")
                return false
            }
        }

        function addTextBox(val){
            var txtBox ='<td id = "volunteerDays" class="days">Days&nbsp;:&nbsp;<input type ="text" value ="" name ="volunteerDays" class="days_no" maxlength="4" id ="box_for_days"></td>';
            if(val=='Volunteer' || val=='Probation' ){
                $("#effectiveDate1").remove();
                $("#x").append(txtBox);
            }
            else{
                $("#volunteerDays").remove();

            }
        }

        function selectDate(){
            $("#expirydate").datepicker({
                dateFormat: 'yy-mm-dd',
                changeMonth:true
            });
        }

        function restrictSelfSupervision(){
            var emp=$('#firstName').val()+'  '+$('#lastName').val()+'-'+$('#employeeId').val()
            var sup=$('#supervisor').find('option:selected').text();
            if(emp == sup){
                alert('Employee and Supervisor must be different');
                return false;
            }
            if((($('#status').val())=='Volunteer') && ($('#box_for_days').val())=='') {
                alert ( "Please enter days for Volunteer 'Service Type'." );
                return false;
            }
        }

        function enableGradeTextbox(){
//            document.getElementById("gradeReward").disabled=false;
            $("#gradeReward").removeAttr("disabled");
        }


    </script>
</head>
<body>

<div class="body">
<h4><g:message code="default.create.label" args="[entityName]" /></h4>
<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>
<g:hasErrors bean="${employeeInstance}">
    <div class="errors">
        <g:renderErrors bean="${employeeInstance}" as="list" />
    </div>
</g:hasErrors>
<g:hasErrors bean="${userInstance}">
    <div class="errors">
        <g:renderErrors bean="${userInstance}" as="list" />
    </div>
</g:hasErrors>
<g:hiddenField id = "service" name="service"  value ="${employeeInstance.status}" />
<g:hiddenField id = "v_days" name="service"  value ="${employeeInstance.volunteerDays}" />
<g:hiddenField id = "isDocChecked" name="isDocChecked"  value ="${handleIsDocChecked}" />
<g:form id="form1" action="save" enctype="multipart/form-data" onSubmit="return restrictSelfSupervision();">
<div class="dialog">
<table>
<tbody>

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
        <g:select name="supervisor.id" id="supervisor" from="${supervisorList}"  optionKey="id" value="${employeeInstance?.supervisor?.id}" noSelection="['':'-Choose One-']" />
    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="dateOfBirth"><g:message code="employee.dateOfBirth.label" default="Date Of Birth *" /></label>
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

        <g:datePicker name="joinDate" precision="day" value="${employeeInstance?.joinDate}" years="${startYr..endYr}" />
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="status"><g:message code="employee.status.label" default="Service Type *" /></label>
    </td>

    <td id = "x" valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'status', 'errors')}">
        <g:select id ="status" name="status" from="${employeeInstance.constraints.status.inList}" value="${employeeInstance?.status}" valueMessagePrefix="employee.status" noSelection="['':'-Choose One-']"  OnChange ="addTextBox(this.value);"/>
    </td>
   </tr>


<tr class="prop">
    <td valign="top" class="name">
        <label for="maritalStatus"><g:message code="employee.maritalStatus.label" default="Marital Status *" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'maritalStatus', 'errors')}">
        <g:select name="maritalStatus" from="['Single','Married','Divorced']" noSelection="['':'--Choose One--']" value="${employeeInstance?.maritalStatus}"/>
    </td>
    <td valign="top" class="name">
        <label for="gender"><g:message code="employee.gender.label" default="Gender *" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'gender', 'errors')}">
        <g:radioGroup name="gender" labels="['Male','Female']" values="['Male','Female']" value="${employeeInstance?.gender}" >
            <g:message code="${it.label}" />: ${it.radio}
        </g:radioGroup>
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">Username *</td>
    <td class="value ${hasErrors(bean: userInstance, field: 'username', 'errors')}">
        <g:textField name="username" value="${userInstance?.username}" />
    </td>
    <td valign="top" class="name">Password *</td>
    <td class="value ${hasErrors(bean: userInstance, field: 'password', 'errors')}">
        <g:passwordField name="password" value="${userInstance?.password}"/>
    </td>


</tr>



<tr class="prop">
    <td valign="top" class="name">
        <label for="country"><g:message code="employee.country.label" default="Country *" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'country', 'errors')}">
        <g:countrySelect name="country"  noSelection="['':'--Select Your Country --']" value="${employeeInstance?.country}" default="npl"/>
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
        <label for="homePhone"><g:message code="employee.homePhone.label" default="Home Phone " /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'homePhone', 'errors')}">
        <g:textField name="homePhone" value="${employeeInstance?.homePhone}" />
    </td>
    <td valign="top" class="name">
        <label for="workPhone"><g:message code="employee.workPhone.label" default="Work Phone " /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'workPhone', 'errors')}">
        <g:textField name="workPhone" value="${employeeInstance?.workPhone}" />
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="mobile"><g:message code="employee.mobile.label" default="Mobile " /></label>
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
        <g:textField name="email" value="${employeeInstance?.email}" />
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
        <label for="departments.id"><g:message code="employee.departments.label" default="Department *" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'departments', 'errors')}">
        <g:textField id = "depart_name" name="depart_name" readonly = "true" value="${employeeInstance.departments}"/>
        <g:hiddenField id="department" name="departments.id" value="${employeeInstance.departments?.id}"/>
        %{--<g:hiddenField id = "service" name="service"  value ="${fieldValue(bean: employeeInstance, field: 'status')}" />--}%


    </td>


    <td valign="top" class="name">
        <label for="unit.id"><g:message code="employee.designation.label" default="Unit" /></label>
    </td>
    <td id="td_unit" valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'unit', 'errors')}">

        <g:select name="unit.id" from="${hrm.Unit.list()}" optionKey="id" value="${employeeInstance?.unit?.id}" noSelection="${['null':'--Choose Unit--']}"  />
        %{--<g:select name="unit.id" from="${unitList}" optionKey="id" value="${employeeInstance?.unit?.id}" noSelection="${['null':'--Choose Unit--']}"  />--}%
    </td>


</tr>



<tr>
    <td></td>
    <td><div id="popup">${deptTree}</div></td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="designation.id"><g:message code="employee.designation.label" default="Designation *" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'designation', 'errors')}">
        <g:select name="designation.id" from="${hrm.Designation.list()}" optionKey="id" value="${employeeInstance?.designation?.id}" noSelection="['':'--Choose Designation--']" onchange="enableGradeTextbox();"  />
    </td>
    <td valign="top" class="name">
        <label for="promotionDate"><g:message code="employee.promotionDate.label" default="Promotion Date" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'promotionDate', 'errors')}">
        <g:set var="startYr" value="${new GregorianCalendar().get(Calendar.YEAR)-29}"/>
        <g:set var="endYr" value="${new GregorianCalendar().get(Calendar.YEAR)}"/>

        <g:datePicker name="promotionDate" precision="day" value="${employeeInstance?.promotionDate}" years="${startYr..endYr}" />
    </td>

</tr>

<g:if test="${AnnapurnaConstants.CLIENT_NAME==AnnapurnaConstants.CLIENT_PHECT}">
    <td valign="top" class="value">
        <label for="status"><g:message code="employee.isDoc.label" default="Is Doctor" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'isDoc', 'errors')}">
        <g:checkBox id="isDoc" name="isDoc" value="${employeeInstance?.isDoc}" checked=""/>&nbsp;&nbsp;&nbsp;Yes (✔)
    </td>
    <td valign="top" class="name">
        <label for="councilNumber"><g:message code="employee.councilNumber.label" default="NMC/NNC #" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'councilNumber', 'errors')}">
        <g:textField name="councilNumber" value="${employeeInstance?.councilNumber}"/>
    </td>
</g:if>

<tr class="prop">
    <td valign="top" class="name">
        <label for="gradeReward.id"><g:message code="employee.gradeReward.label" default="Reward Grade" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'rewardGrade', 'errors')}">
        <g:textField id="gradeReward" name="gradeReward" value="${employeeInstance?.gradeReward}"/>
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        <label for="salaryclass.id"><g:message code="employee.salaryclass.label" default="Pay Date Class *" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'salaryclass', 'errors')}">
        <g:select name="salaryclass.id" from="${hrm.SalaryClass.list()}" optionKey="id" value="${employeeInstance?.salaryclass?.id}" noSelection="['':'--Choose Salary Group--']" />
    </td>
</tr>

<tr class="prop">
    <td valign="top" class="name">
        Upload Image:
        <br>
    <td valign="top" class="value">
        <input type="file" name="emp_image" />
    </td>
</tr>

</tbody>
</table>
</div>

<div class="formbuttons">
    <g:submitButton name="create"  class="savebutton" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
</div>
</g:form>
</div>
</body>
</html>
