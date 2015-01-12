<%@ page import="commons.BayalpatraConstants"
        contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main_hrm" />
    <title>HRM Dashboard</title>
</head>


<body>

<div class="body_part">
<div class="body_content">

    <div class="left_part">
        <div class="dashbord_box">
            <h2>Employee</h2>

            <div class="dashbord_box_content">
                <div class="timesheet">
                    <ul>
                        <g:link controller="employee" action='list'> %{--id="${employeeInstance?.id}"--}%
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'employee-icon.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Employee Detail</p></li>
                        </g:link>

                        <sec:ifAllGranted roles="ROLE_ADMIN">

                            <g:link controller="employee" action='create'>
                                <li><img
                                        src="${createLinkTo(dir:'images', file:'add-employee.jpg')}"
                                        alt="" /><br />
                                    <p class="dsbord_con_txt">Add Employee</p> %{--<sec:loggedInUserInfo
												field='id' />&nbsp;&nbsp;&nbsp;<sec:username />--}%</li>
                            </g:link>

                        </sec:ifAllGranted>
                    %{--<sec:ifAnyGranted roles="ROLE_DepartmentHead,ROLE_UnitIncharge,ROLE_Supervisor">--}%
                        <g:link controller="employeeDependents" action='dependentsList'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'dependents.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Dependents Report</p></li>
                        </g:link>
                    %{--</sec:ifAnyGranted>--}%
                        <sec:ifAnyGranted roles="ROLE_ADMIN">
                            <g:link controller="employeeReview" action='list'>
                                <li><img
                                        src="${createLinkTo(dir:'images', file:'employee-review.png')}"
                                        alt="" /><br />
                                    <p class="dsbord_con_txt">Employee Review</p></li>
                            </g:link>
                            <g:link controller="employee" action='termedEmployeeList'>
                                <li><img
                                        src="${createLinkTo(dir:'images', file:'terminatedemployee.jpg')}"
                                        alt="" /><br />
                                    <p class="dsbord_con_txt">Terminated Employee Detail</p></li>
                            </g:link>
                        </sec:ifAnyGranted>
                    </ul>
                </div>
            </div>
        </div>
    </div>


    <sec:ifAnyGranted
            roles="ROLE_ADMIN,ROLE_DEPARTMENTHEAD,ROLE_SUPERVISOR">

        <div class="mid_part">
            <div class="dashbord_box">
                <h2>Attendance</h2>

                <div class="dashbord_box_content">
                    <div class="timesheet">
                        <ul>
%{--
                            <g:link controller="dutyRoster" action='create'>
                                <li><img
                                        src="${createLinkTo(dir:'images', file:'duty-roster.jpg')}"
                                        alt="" /><br />
                                    <p class="dsbord_con_txt">Duty Roster</p></li>
                            </g:link>

                            <g:link controller="dutyRosterReport" action='report'>
                                <li><img
                                        src="${createLinkTo(dir:'images', file:'roster-report.png')}"
                                        alt="" /><br />
                                    <p class="dsbord_con_txt">Roster Report</p></li>
                            </g:link>
--}%
                            <g:link controller="timecard" action='index'>
                                <li><img
                                        src="${createLinkTo(dir:'images', file:'app-time.jpg')}"
                                        alt="" /><br />
                                    <p class="dsbord_con_txt">Time Card</p></li>
                            </g:link>




                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </sec:ifAnyGranted>
    <sec:ifAnyGranted
            roles="ROLE_ADMIN,ROLE_DEPARTMENTHEAD,ROLE_SUPERVISOR">

        <div class="leave">
            <div class="dashbord_box">
                <h2>Leave</h2>

                <div class="dashbord_box_content">
                    <div class="timesheet">
                        <ul>
                            <g:link controller="employeeLeaveDetail" action='list'>
                                <li><img
                                        src="${createLinkTo(dir:'images', file:'leave-icon-h.jpg')}"
                                        alt="" /><br />
                                    <p class="dsbord_con_txt">Leave Detail</p></li>
                            </g:link>


                            <g:link controller="employeeLeaveDetail" action='approvalList'>
                                <li><img
                                        src="${createLinkTo(dir:'images', file:'approve-leave.jpg')}"
                                        alt="" /><br />
                                    <p class="dsbord_con_txt">Approve Leave</p></li>
                            </g:link>


                            <sec:ifAnyGranted roles="ROLE_DEPARTMENTHEAD,ROLE_SUPERVISOR">
                                <g:link controller="employeeLeaveDetail" action='create'>
                                    <li><img
                                            src="${createLinkTo(dir:'images', file:'leave.jpg')}"
                                            alt="" /><br />
                                        <p class="dsbord_con_txt">Add Leave</p></li>
                                </g:link>
                            </sec:ifAnyGranted>

                        </ul>
                    </div>

                </div>

            </div>
        </div>
    </sec:ifAnyGranted>
</div>

<sec:ifAllGranted roles="ROLE_ADMIN">
<div class="clear"></div>
<div>
    <div class="setting">
        <div class="dashbord_box">
            <h2>Setting</h2>

            <div class="dashbord_box_content">
                <div class="timesheet">
                    <ul>
                        <g:link controller="company" action='edit' id="1">
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'company-profile.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Organizational Detail</p></li>
                        </g:link>
                        <g:link controller="user" action='userList'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'user.jpg')}" alt="" /><br />
                                <p class="dsbord_con_txt">User Administration</p></li>
                        </g:link>


                    %{--
                    <g:link controller="salary" action='list'>
                        <li><img
                            src="${createLinkTo(dir:'images', file:'salary-report.jpg')}"
                            alt="" /><br />
                            <p class="dsbord_con_txt">Salary</p></li>
                    </g:link>
                    --}%
                        <g:link controller="department" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'department-1.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Department</p></li>
                        </g:link>
                        <g:link controller="designation" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'designation.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Designation</p></li>
                        </g:link>
                        %{--<g:link controller="shiftSetting" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'shift-setting.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Shift Setting</p></li>
                        </g:link>
--}%
                        <g:link controller="department" action="showTree">
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'structure.jpg')}" alt="" /><br />
                                <p class="dsbord_con_txt">Structure</p></li>
                        </g:link>

 %{--                       <g:link controller="unit" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'unit.jpg')}" alt="" /><br />
                                <p class="dsbord_con_txt">Unit</p></li>
                        </g:link>
--}%
                        <g:link controller="dateSetting" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'date-setting.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Holiday Setting</p></li>
                        </g:link>

                        <g:link controller="supervisor" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'supervisor-setting.png')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Supervisor Setting</p></li>
                        </g:link>

%{--                        <g:if test="${AnnapurnaConstants.CLIENT_NAME==AnnapurnaConstants.CLIENT_DEERWALK}">
                            <g:link controller="employee" action='probationList'>
                                <li><img
                                        src="${createLinkTo(dir:'images', file:'probation.png')}"
                                        alt="" /><br />
                                    <p class="dsbord_con_txt">Probation Completed Employee
                                    Report</p></li>
                            </g:link>
                        </g:if>--}%

                        <g:link controller="auditLog" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'audit_trail1.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Audit Log</p></li>
                        </g:link>
    %{--                    <g:link controller="hrmSubStoreSetting" action='list'>
                            <li><img 	src="${createLinkTo(dir:'images', file:'manage-stores_sources.jpg')}"
                                <p class="dsbord_con_txt">Manage Stores Sources</p></li>
                        </g:link>
--}%
                        <g:link controller="earnedLeaveSetting" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'leave-icon-h.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Earned Leave Setting</p></li>
                        </g:link>
                        <g:link controller="leaveType" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'leave-icon.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Leave Type</p></li>
                        </g:link>
                        <g:link controller="report" action='list'>
                            <li><img src="${createLinkTo(dir:'images', file:'report-hrm-setting.png')}" alt=""/><br/>
                                <p class="dsbord_con_txt">Report</p>
                            </li>
                        </g:link>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="payroll">
        <div class="dashbord_box">
            <h2>Payroll</h2>

            <div class="dashbord_box_content">
                <div class="timesheet">
                    <ul>
                        <g:if test="${BayalpatraConstants.CLIENT_NAME==BayalpatraConstants.CLIENT_BAYALPATRA}">
                            <g:link controller="salary" action='list'>
                                <li><img
                                        src="${createLinkTo(dir:'images', file:'payroll-managment.jpg')}"
                                        alt="" /><br />
                                    <p class="dsbord_con_txt">Payroll Management</p></li>
                            </g:link>
                        </g:if>
%{--                        <g:if test="${AnnapurnaConstants.CLIENT_NAME==AnnapurnaConstants.CLIENT_DEERWALK}">
                            <g:link controller="hrmEmployeeSalary" action='list'>
                                <li><img
                                        src="${createLinkTo(dir:'images', file:'payroll-managment.jpg')}"
                                        alt="" /><br />
                                    <p class="dsbord_con_txt">Payroll Management</p></li>
                            </g:link>
                        </g:if>--}%

                        <g:link controller="salaryClass" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'salaryclass.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Pay Date</p></li>
                        </g:link>
                        <g:link controller="seniorityAllowance" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'seniority.png')}" alt="" /><br />
                                <p class="dsbord_con_txt">Seniority Allowance</p></li>
                        </g:link>
                        <g:link controller="dearnessAllowance" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'dearness-allowance.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Dearness Allowance</p></li>
                        </g:link>

                        <g:link controller="salaryReport" action='salaryReports'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'salary-report.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Salary Report</p></li>
                        </g:link>
                        <g:link controller="extraAllowance" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'extra-allowance.png')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Extra Allowance</p></li>
                        </g:link>
                        <g:link controller="otherAllowance" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'allowance.jpg')}" alt="" /><br />
                                <p class="dsbord_con_txt">Other Allowance</p></li>
                        </g:link>
                        <g:link controller="taxSetting" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'selective_salary.png')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Tax Setting</p></li>
                        </g:link>
                        <g:link controller="hrmEmployeeAccount" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'employee-account.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Employee Account</p></li>
                        </g:link>
                        <g:link controller="employeeAdvance" action='list'>
                            <li><img
                                    src="${createLinkTo(dir:'images', file:'employee-advance.jpg')}"
                                    alt="" /><br />
                                <p class="dsbord_con_txt">Employee Advance</p></li>
                        </g:link>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="clear"></div>
</div>

</sec:ifAllGranted>
</div>

</body>
</html>



