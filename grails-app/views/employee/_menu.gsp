
<div class="detail">
%{--  <div id="photodiv">

  <p>
    <g:if test="${employeeInstance?.filename}">
      <img src="${resource(dir: 'images/employee', file: employeeInstance?.filename)}" alt="Employee Image" width="150" height="140">
      <p><a title="Click to edit photo" onclick="showPhotoHandler()" href="#"> <span id="empname1">${employeeInstance?.firstName} ${employeeInstance?.lastName}</span> </a></p>
    </g:if>
    <g:else>
      <p><img src="${resource(dir: 'images/employee', file: 'defaultImage.jpg')}" alt="" width="150" height="140"><br>
      <p><a title="Click to edit photo" onclick="showPhotoHandler()" href="#"> <span id="empname2">${employeeInstance?.firstName} ${employeeInstance?.lastName}</span> </a></p>
    </g:else>

  </div>--}%

  <div class="employee_detail_menu">
    <ul>
      <li><g:link controller="employee" action="edit" params="['employeeIs': employeeInstance?.id,offset:params.offset,statusFlag:true]" >Personal Details</g:link></li>
      %{--  id="${employeeInstance?.id}"  <li><g:link controller="employee" action="edit" params="['employee.id': employeeInstance?.id]">Personal Details</g:link></li>--}%
      <li>
        <g:link controller="employeeDependents" action="create" params="['employeeIs': employeeInstance?.id]">Dependents</g:link>
        %{--<g:form controller="employeeDependents" action="create" method="post">--}%
          %{--<g:hiddenField name="employee.id" value="${employeeInstance?.id}"/>--}%
          %{--<g:submitButton name="Dependents" value="Dependents"/>--}%
        %{--</g:form>--}%
      </li>

      <li><g:link controller="employeeEducation" action="create" params="['employeeIs': employeeInstance?.id]">Education</g:link></li>

      %{--<g:form controller="employeeEducation" action="create" method="post">--}%
        %{--<g:hiddenField name="employee.id" value="${employeeInstance?.id}"/>--}%
        %{--<g:submitButton name="Education" value="Education"/>--}%
      %{--</g:form>--}%

      <li><g:link controller="employeeTraining" action="create" params="['employeeIs': employeeInstance?.id]">Training</g:link></li>

      %{--<g:form controller="employeeTraining" action="create" method="post">--}%
        %{--<g:hiddenField name="employee.id" value="${employeeInstance?.id}"/>--}%
        %{--<g:submitButton name="Training" value="Training"/>--}%
      %{--</g:form>--}%


      <li>
        <g:link controller="employeeLeaveDetail" action="list" params="['employeeIs': employeeInstance?.id]">Leave</g:link>

      </li>




      %{--<g:form controller="employeeLeaveDetail" action="list" method="post">--}%
        %{--<g:hiddenField name="employee" value="${employeeInstance?.id}"/>--}%
        %{--<g:submitButton name="Leave" value="Leave"/>--}%
      %{--</g:form>--}%

      <li><g:link controller="salaryReport" action="list" params="['employeeIs': employeeInstance?.id]">Salary</g:link></li>

      %{--<g:form controller="salaryReport" action="list" method="post">--}%
        %{--<g:hiddenField name="employee" value="${employeeInstance?.id}"/>--}%
        %{--<g:submitButton name="Salary" value="Salary"/>--}%
      %{--</g:form>--}%
		<sec:ifAnyGranted roles="ROLE_HR_Admin,ROLE_HR_Secondary,ROLE_HR_Primary">
      		<li><g:link controller="blockAllowance" action="create" params="['employeeIs': employeeInstance?.id]">Restrict Allowance</g:link></li>
		</sec:ifAnyGranted>

      %{--<g:form controller="blockAllowance" action="create" method="post">--}%
        %{--<g:hiddenField name="employee.id" value="${employeeInstance?.id}"/>--}%
        %{--<g:submitButton name="Restrict Allowance" value="Restrict Allowance"/>--}%
      %{--</g:form>--}%

      <li><g:link controller="employee" action="employeeReport" params="['employeeIs': employeeInstance?.id]">Employee History</g:link></li>

      %{--<g:form controller="employee" action="employeeReport" method="post">--}%
        %{--<g:hiddenField name="employee.id" value="${employeeInstance?.id}"/>--}%
        %{--<g:submitButton name="Employee History" value="Employee History"/>--}%
      %{--</g:form>--}%


    </ul>
  </div>
</div>