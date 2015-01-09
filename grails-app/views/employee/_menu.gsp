<div class="detail">
  <div id="photodiv">

  <p>
      <p><img src="${resource(dir: 'images/employee', file: 'defaultImage.jpg')}" alt="" width="150" height="140"><br>
      <p><a title="Click to edit photo" onclick="showPhotoHandler()" href="#"> <span id="empname2">${employeeInstance?.firstName} ${employeeInstance?.lastName}</span> </a></p>
  </div>

  <div class="employee_detail_menu">
    <ul>
      <li><g:link controller="employee" action="edit" params="['employeeIs': employeeInstance?.id,offset:params.offset,statusFlag:true]" >Personal Details</g:link></li>
      <li>
        <g:link controller="employeeDependents" action="create" params="['employeeIs': employeeInstance?.id]">Dependents</g:link>

      </li>

      <li><g:link controller="employeeEducation" action="create" params="['employeeIs': employeeInstance?.id]">Education</g:link></li>

      <li><g:link controller="employeeTraining" action="create" params="['employeeIs': employeeInstance?.id]">Training</g:link></li>

      <li>
        <g:link controller="employeeLeaveDetail" action="list" params="['employeeIs': employeeInstance?.id]">Leave</g:link>
      </li>

      <li><g:link controller="salaryReport" action="list" params="['employeeIs': employeeInstance?.id]">Salary</g:link></li>

      <li><g:link controller="employee" action="employeeReport" params="['employeeIs': employeeInstance?.id]">Employee History</g:link></li>



    </ul>
  </div>
</div>