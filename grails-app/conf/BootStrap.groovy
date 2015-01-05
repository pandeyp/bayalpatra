import com.bayalpatra.hrm.Department
import com.bayalpatra.hrm.LeaveType
import com.bayalpatra.hrm.Requestmap
import com.bayalpatra.hrm.Role
import com.bayalpatra.hrm.User
import com.bayalpatra.hrm.UserRole
import commons.BayalpatraConstants

class BootStrap {

    def init = { servletContext ->
        def adminRole=Role.findByAuthority(BayalpatraConstants.ROLE_ADMIN)?:new Role(authority: BayalpatraConstants.ROLE_ADMIN).save(flush: true)
        def departmentHeadRole = Role.findByAuthority(BayalpatraConstants.ROLE_DEPARTMENT_HEAD) ?: new Role(authority: BayalpatraConstants.ROLE_DEPARTMENT_HEAD).save(failOnError: true)
        def supervisorRole = Role.findByAuthority(BayalpatraConstants.ROLE_SUPERVISOR) ?: new Role(authority: BayalpatraConstants.ROLE_SUPERVISOR).save(failOnError: true)
        def employeeRole = Role.findByAuthority(BayalpatraConstants.ROLE_EMPLOYEE) ?: new Role(authority: BayalpatraConstants.ROLE_EMPLOYEE).save(failOnError: true)

//       def taxSetting = TaxSetting.findAll()?:new TaxSetting(singleMinLimit: 160000,singleMedLimit: 260000,marriedMinLimit: 200000,marriedMedLimit: 300000,taxAmountLimitForMedium: 100000,minTaxPercentage: 1,midTaxPercentage: 15,maxTaxPercentage: 25,dashainBonusCutOff: 30000,femaleRebatePercentage:10).save(failOnError: true)

        def department = Department.findByName('BAYALPATRA')?:new Department(name: 'BAYALPATRA',parentId: 0,rootId:1,idNumber:"01").save(failOnError: true)
        def dayOffLeave = LeaveType.findByLeaveType(BayalpatraConstants.DAY_OFF_LEAVE)?:new LeaveType(leaveType: BayalpatraConstants.DAY_OFF_LEAVE,paidUnpaid: BayalpatraConstants.PAID_LEAVE,status: BayalpatraConstants.STATUS_ACTIVE,days: 0).save(failOnError: true)
        def nightOffLeave = LeaveType.findByLeaveType(BayalpatraConstants.NIGHT_OFF_LEAVE)?:new LeaveType(leaveType: BayalpatraConstants.NIGHT_OFF_LEAVE,paidUnpaid: BayalpatraConstants.PAID_LEAVE,status: BayalpatraConstants.STATUS_ACTIVE,days: 0).save(failOnError: true)
        def substituteLeave = LeaveType.findByLeaveType(BayalpatraConstants.SUBSTITUTE_LEAVE)?:new LeaveType(leaveType: BayalpatraConstants.SUBSTITUTE_LEAVE,paidUnpaid: BayalpatraConstants.PAID_LEAVE,status: BayalpatraConstants.STATUS_ACTIVE,days: 0).save(failOnError: true)
        def festivalOffLeave = LeaveType.findByLeaveType(BayalpatraConstants.FESTIVAL_OFF_LEAVE)?:new LeaveType(leaveType: BayalpatraConstants.FESTIVAL_OFF_LEAVE,paidUnpaid: BayalpatraConstants.PAID_LEAVE,status: BayalpatraConstants.STATUS_ACTIVE,days: 0).save(failOnError: true)

        def testAdmin = User.findByUsername('admin')?:new User(id: '1', username: 'admin', enabled: true, password: 'admin').save(flush: true)

        if (!testAdmin.authorities.contains(adminRole)) {
            UserRole.create testAdmin,adminRole
        }

        for (String url in [
                /*'/', '/index', '/index.gsp',*/ '/favicon.ico',
                '/assets/**', '/js/**', '/css/**', '/images/**',
                '/login', '/login.*', '/login/*',
                '/logout', '/logout.*', '/logout/*',
                /*'/rssUser','/rssUser*//*','/RSS_Image*//**',*/'/register/**','/image/**'
        ]) {
            Requestmap.findByUrl(url)?"":new Requestmap(url: url, configAttribute: 'permitAll').save()
        }

            Requestmap.findByUrl('/**')?"":new Requestmap(url: '/**', configAttribute: 'IS_AUTHENTICATED_FULLY').save()


/*        new Requestmap(url: '/profile*//**',    configAttribute: 'ROLE_USER').save()
        new Requestmap(url: '/admin*//**',      configAttribute: 'ROLE_ADMIN').save()
        new Requestmap(url: '/admin/role*//**', configAttribute: 'ROLE_SUPERVISOR').save()
        new Requestmap(url: '/admin/user*//**', configAttribute: 'ROLE_ADMIN,ROLE_SUPERVISOR').save()
        new Requestmap(url: '/j_spring_security_switch_user',configAttribute: 'ROLE_SWITCH_USER,isFullyAuthenticated()').save()*/


        Requestmap.findByUrl('/user/**')?"":new Requestmap(url: '/user/**', configAttribute: 'ROLE_ADMIN').save()
        Requestmap.findByUrl('/role/**')?"":new Requestmap(url: '/role/**', configAttribute: 'ROLE_ADMIN').save()
        Requestmap.findByUrl('/plugins*//**')?"":new Requestmap(url: '/plugins*//**', configAttribute: 'permitAll').save()

        Requestmap.findByUrl('/department*//**')?"":new Requestmap(url: '/department*//**', configAttribute: 'ROLE_ADMIN').save()
        Requestmap.findByUrl('/dashboard*//**')?"":new Requestmap(url: '/dashboard*//**', configAttribute: 'ROLE_ADMIN,ROLE_DEPARTMENTHEAD,ROLE_EMPLOYEE,ROLE_SUPERVISOR').save()
/*
        RequestMap.findByUrl("/hrm/attendance/**")?:new RequestMap(url: "/hrm/attendance/**",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/blockAllowance/**")?:new RequestMap(url: "/hrm/blockAllowance/**",configAttribute: "ROLE_HR_Admin,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/company/**")?:new RequestMap(url: "/hrm/company/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/hrmDashboard/**")?:new RequestMap(url: "/hrm/hrmDashboard/**",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_Employee,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/dateSetting/**")?:new RequestMap(url: "/hrm/dateSetting/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/departments/**")?:new RequestMap(url: "/hrm/departments/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/designation/**")?:new RequestMap(url: "/hrm/designation/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/dutyRoster/**")?:new RequestMap(url: "/hrm/dutyRoster/**",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/dutyRosterReport/**")?:new RequestMap(url: "/hrm/dutyRosterReport/**",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employeeAdvance/**")?:new RequestMap(url: "/hrm/employeeAdvance/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employee/create")?:new RequestMap(url: "/hrm/employee/create",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employee/edit/**")?:new RequestMap(url: "/hrm/employee/edit/**",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_Employee,ROLE_SubStoreInv,ROLE_UnitIncharge,ROLE_MainStoreInv,ROLE_ProcurementInv,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employee/employeeReport")?:new RequestMap(url: "/hrm/employee/employeeReport",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_UnitIncharge,ROLE_Employee,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employee/list")?:new RequestMap(url: "/hrm/employee/list",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employee/probationList")?:new RequestMap(url: "/hrm/employee/probationList",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employee/termedEmployeeList")?:new RequestMap(url: "/hrm/employee/termedEmployeeList",configAttribute: "ROLE_HR_Admin,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employeeDependents/**")?:new RequestMap(url: "/hrm/employeeDependents/**",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_Employee,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employeeEducation/**")?:new RequestMap(url: "/hrm/employeeEducation/**",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_Employee,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employeeLeaveDetail/create")?:new RequestMap(url: "/hrm/employeeLeaveDetail/create",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_Employee,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employeeLeaveDetail/list")?:new RequestMap(url: "/hrm/employeeLeaveDetail/list",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_Employee,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employeeLeaveDetail/approvalList")?:new RequestMap(url: "/hrm/employeeLeaveDetail/approvalList",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employeeLeaveDetail/edit/**")?:new RequestMap(url: "/hrm/employeeLeaveDetail/edit/**",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employeeLeaveDetail/updateStatus")?:new RequestMap(url: "/hrm/employeeLeaveDetail/updateStatus",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employeeReview/**")?:new RequestMap(url: "/hrm/employeeReview/**",configAttribute: "ROLE_HR_Admin,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employeeSalary/**")?:new RequestMap(url: "/hrm/employeeSalary/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/employeeTraining/**")?:new RequestMap(url: "/hrm/employeeTraining/**",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_Employee,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/extraAllowance/**")?:new RequestMap(url: "/hrm/extraAllowance/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/hrmEmployeeAccount/**")?:new RequestMap(url: "/hrm/hrmEmployeeAccount/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/hrmLeaveBalanceReport/list")?:new RequestMap(url: "/hrm/hrmLeaveBalanceReport/list",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_Employee,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/hrmLeaveBalanceReport/edit/**")?:new RequestMap(url: "/hrm/hrmLeaveBalanceReport/edit/**",configAttribute: "ROLE_HR_Admin,ROLE_HR_Secondary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/leaveReport/**")?:new RequestMap(url: "/hrm/leaveReport/**",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_Employee,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/leaveType/**")?:new RequestMap(url: "/hrm/leaveType/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/otherAllowance/**")?:new RequestMap(url: "/hrm/otherAllowance/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/role/**")?:new RequestMap(url: "/hrm/role/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/salaryClass/**")?:new RequestMap(url: "/hrm/salaryClass/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/salary/**")?:new RequestMap(url: "/hrm/salary/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/salaryReport/list")?:new RequestMap(url: "/hrm/salaryReport/list",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_Employee,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/salaryReport/showSalaryDetails/**")?:new RequestMap(url: "/hrm/salaryReport/showSalaryDetails/**",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_Employee,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/seniorityAllowance/**")?:new RequestMap(url: "/hrm/seniorityAllowance/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/shiftSetting/**")?:new RequestMap(url: "/hrm/shiftSetting/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/supervisor/**")?:new RequestMap(url: "/hrm/supervisor/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/timecard/list")?:new RequestMap(url: "/hrm/timecard/list",configAttribute: "ROLE_HR_Admin,ROLE_DepartmentHead,ROLE_Employee,ROLE_UnitIncharge,ROLE_Supervisor,ROLE_HR_Secondary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/timecard/create")?:new RequestMap(url: "/hrm/timecard/create",configAttribute: "ROLE_HR_Admin,ROLE_HR_Secondary").save(failOnError: true)
        RequestMap.findByUrl("/hrm/unit/**")?:new RequestMap(url: "/hrm/unit/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/user/userList")?:new RequestMap(url: "/hrm/user/userList",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/user/searchUser")?:new RequestMap(url: "/hrm/user/searchUser",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/user/create")?:new RequestMap(url: "/hrm/user/create",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
        RequestMap.findByUrl("/hrm/user/edit/**")?:new RequestMap(url: "/hrm/user/edit/**",configAttribute: "ROLE_HR_Admin,ROLE_Employee,ROLE_DepartmentHead,ROLE_UnitIncharge,ROLE_MainStoreInv,ROLE_SubStoreInv,ROLE_ProcurementInv,ROLE_Supervisor,ROLE_HR_Secondary,ROLE_HR_Primary").save(failOnError: true)
        RequestMap.findByUrl("/securityInfo/**")?:new RequestMap(url: "/securityInfo/**",configAttribute: "ROLE_HR_Admin").save(failOnError: true)
*/
/*         SpringSecurityUtils.clientRegisterFilter('authenticationProcessingFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 10)

         println(" .------..------..------.\n" +
         "|R.--. ||S.--. ||S.--. |\n" +
         "| :(): || :/\\: || :/\\: |\n" +
         "| ()() || :\\/: || :\\/: |\n" +
         "| '--'R|| '--'S|| '--'S|\n" +
         "`------'`------'`------' ")
         println "!!! Build Success !!!"
         */

        //////////////////////////////////////////////////////////////////sdfsdfsf
//        def ghatasthapana = DateSetting.findByDateType(AnnapurnaConstants.GHATASTHAPANA)?:new DateSetting(dateType: AnnapurnaConstants.GHATASTHAPANA,date: new Date(),year: new Date(),description: 'Default Ghatasthapana').save(failOnError: true)
//        def fiscalYear = DateSetting.findByDateType(AnnapurnaConstants.FISCAL_YEAR)?:new DateSetting(dateType: AnnapurnaConstants.FISCAL_YEAR,date: new Date(),year: new Date(),description: 'Default Fiscal Year').save(failOnError: true)


    }

    def destroy = {
    }
}
