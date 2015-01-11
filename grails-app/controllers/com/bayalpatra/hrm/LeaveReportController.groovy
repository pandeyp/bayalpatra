package com.bayalpatra.hrm

import commons.BayalpatraConstants
import commons.DateUtils

class LeaveReportController {
    def exportService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def springSecurityService
    DepartmentService departmentService
    EmployeeService employeeService
    def leaveReportService
    RoleService roleService
    def dutyRosterReportService
    def leaveService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.sort = params.sort?:'employee.firstName'
        params.order = params.order?:'asc'
        def leaveReportInstanceList
        def count = 0
        def leaveReport
        def employeeList
        def leaveMonthList = leaveReportService.populateLeaveMonth()
        def sortedList
        departmentService.departmentTree = ""
        def user = User.findById(springSecurityService.principal.id)
        def role = roleService.getRole()

        def deptTree =  departmentService.generateNavigation(0)


        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        if(!params.offset){
            session.filterUnit=null
            session.filterBeginDate=null
            session.filterDepartment=null
            session.employee=null
        }
        if(role[0].toString()==BayalpatraConstants.ROLE_ADMIN){
            employeeList = employeeService.getEmpByStatus()

        }
        else if(role[0].toString()==BayalpatraConstants.ROLE_DEPARTMENT_HEAD){
            def finalDept = departmentService.getDepartmentList(session["department"])
            employeeList = employeeService.getEmpByStatusAndDepartment(finalDept)

        }else if(role[0].toString()==BayalpatraConstants.ROLE_SUPERVISOR){

            def supervisor = Supervisor.findByEmployee(user?.employee)
            if(supervisor)
                employeeList = employeeService.getEmployeeBySupervisor(supervisor,user,null)
        }else{
            if(params.emp && params.leaveMonth){
                def employee = Employee.findById(params.emp)
                leaveReportInstanceList = leaveReportService.getLeaveReportByEmpAndMonth(employee,params.leaveMonth,params)
                count = leaveReportService.getCountByEmpAndMonth(employee,params.leaveMonth)
            }else if (params.deptSelected && params.leaveMonth){
                def dept=departmentService.getDepartmentList(Long.parseLong(params.deptSelected))
                def departmentName=Department.createCriteria().list(){
                    'in'("id",dept)
                }
                leaveReportInstanceList=leaveReportService.getLeaveReportByDeptAndMonth(departmentName,params.leaveMonth,params)
                count = leaveReportService.getLeaveCountByDeptAndMonth(departmentName,params.leaveMonth)
            }
            else if(params.emp){
                def employee = Employee.findById(params.emp)
                leaveReportInstanceList = leaveReportService.getLeaveReportByEmp(employee,params)
                count = leaveReportService.getCountByEmp(employee)
            }
            else if(params.leaveMonth){
                leaveReportInstanceList = leaveReportService.getLeaveReportByMonth(employeeList,params.leaveMonth,params)
                count = leaveReportService.getCountByMonth(employeeList,params.leaveMonth)

            }else if(params.deptSelected){
                def dept=departmentService.getDepartmentList(Long.parseLong(params.deptSelected))
                def departmentNames=Department.createCriteria().list(){
                    'in'("id",dept)
                }
                leaveReportInstanceList = leaveReportService.getLeaveReportByDepartment(departmentNames,params)
                count = leaveReportService.getCountByDepartment(departmentNames)

            }else{
                if(role==BayalpatraConstants.ROLE_ADMIN){

                    leaveReportInstanceList = leaveReportService.getLeaveReport(employeeList,params)
                    count =  leaveReportService.getCountByRole(employeeList)
                }
                else if(role==BayalpatraConstants.ROLE_DEPARTMENT_HEAD){

                    leaveReportInstanceList = leaveReportService.getLeaveReport(employeeList,params)
                    count =  leaveReportService.getCountByRole(employeeList)
                }else if(role==BayalpatraConstants.ROLE_SUPERVISOR){

                    if(employeeList){

                        leaveReportInstanceList = leaveReportService.getLeaveReport(employeeList,params)
                        count =  leaveReportService.getCountByRole(employeeList)
                    }
                }

            }
            sortedList=leaveReportInstanceList?.sort {
                map1, map2 -> map1.employee.firstName <=> map2.employee.firstName ?: map1.leaveDate <=> map2.leaveDate
            }
            return [leaveReportInstanceList:sortedList,leaveReportInstanceTotal: count,leaveMonthList:leaveMonthList,deptTree:deptTree,employee:params.emp,
                    leaveMonth:params.leaveMonth,deptSelected:params.deptSelected,source: params.source,employeeList:employeeList]


        }

    }

    def exportToExcel={
        def user = User.findById(springSecurityService.principal.id)
        def role = roleService.getRole()
        def beginDate = session.filterBeginDate
        def filterDepartment = session.filterDepartment
        def filterEmp = session.employee
//        def employeeList = dutyRosterReportService.getEmployeeListAccordingToRole(role,unit,filterDepartment,user)
        if (filterEmp){
            employeeList = []
            employeeList<<Employee.findById(filterEmp)
        }
        def allReportList = leaveReportService.getReportByEmployeeAndReportMonthLeave(employeeList,beginDate,null)
        def differentDepartments=[]
        def blankLineMap
        if (params.source){
            blankLineMap=["employee":"", "leaveDate":"","extraTime":""]
        }else{
            blankLineMap=["employee":"", "leaveDate":"","openingBalance":"", "earnedLeave":"","totalLeaveBalance":"", "paidLeave":"", "unpaidLeave": "", "balanceDays" :""]
        }
      if(params?.exportFormat && params.exportFormat != "html"){
            def masterExportList=[]
            def finalReportMap=[:]
            params.max = 0

            response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
            response.setHeader("Content-disposition", "attachment; filename=Leave_Report.${params.extension}")

                if (filterDepartment||beginDate){
                    def deptName=""
                    def deptHeads=""
                    String inchargeName=""
                    if (filterDepartment){
                        def dept=Department.findById(filterDepartment)
                        deptHeads = employeeService.getDeptHead(dept)
                        deptName=dept?.name
                        inchargeName=deptHeads.toString().replaceAll("\\[","")
                        inchargeName=inchargeName.replaceAll("\\]","")
                    }
                    def dateRange=""
                    if (beginDate){
                        Date start =  new Date().parse("yyyy-MM",beginDate)
                        def startDate=start.toString().split(" ")
                        dateRange=startDate[1]+"-"+startDate[5]
                    }
                    if (!filterEmp){
                        if (params.source){
                            masterExportList.add(['employee':'Department:'+deptName,'extraTime':'Month: '+dateRange])
                        }else{
                            masterExportList.add(['employee':'Department:'+deptName,'earnedLeave':'Month: '+dateRange])
                        }
                        masterExportList.add('employee':'Incharge Name:'+inchargeName)
                    }

                }
                if (filterEmp){

                    masterExportList.add(['employee':'Designation:'+Employee.findById(filterEmp)?.designation])

                }
                if(params.source){
                    masterExportList.add(["employee":"Employee", "leaveDate":"Leave Month","extraTime":"Extra Time"])
                }else{
                    masterExportList.add(["employee":"Employee", "leaveDate":"Leave Month","openingBalance":"Opening Balance", "earnedLeave":"Earned leave","totalLeaveBalance":"Total Leave Balance",
                                          "paidLeave":"Paid Leave", "unpaidLeave": "Unpaid Leave", "balanceDays" :"Balance Days"])
                }
                allReportList.each{eachReport->
                    masterExportList.add(leaveReportService.createReportMapForLeave(eachReport))
                }
                masterExportList.add(blankLineMap)


            List fields = []
            Map labels = [:]
            Map parameters = [:]
            if(params.source){
                fields = [
                        "employee",
                        "leaveDate",
                        "extraTime"
                ]
                labels =["employee":"Extra Time Report", "leaveDate":"","extraTime":"Extra Time"]
                parameters =["column.widths": [
                        0.20,
                        0.10,
                        0.10,
                ]]
            }else{
                fields = [
                        "employee",
                        "leaveDate",
                        "openingBalance",
                        "earnedLeave",
                        "totalLeaveBalance",
                        "paidLeave",
                        "unpaidLeave",
                        "balanceDays"
                ]
                labels =["employee":"Leave Balance Report", "leaveDate":"","openingBalance":"", "earnedLeave":"","totalLeaveBalance":'',
                         "paidLeave":" ", "unpaidLeave": " ", "balanceDays" :" "]
                parameters =["column.widths": [
                        0.20,
                        0.10,
                        0.10,
                        0.10,
                        0.10,
                        0.10,
                        0.10,
                        0.10
                ]]
            }
            exportService.export(params.exportFormat, response.outputStream,masterExportList, fields, labels,[:],parameters)
        }
    }


    def ajaxCall={
        def leaveReportInstanceList
        def model
        def count
        session.filterUnit=params.unit
        session.filterBeginDate=params.leaveMonth
        session.filterDepartment=params.deptSelected
        session.employee=params.emp

        def differentDepartments = []
        def masterReportMap=[:]
        def paginationMap=[:]
        def masterReportCountMap=[:]
        def employeeList
        def user = User.findById(springSecurityService.principal.id)
        def role = roleService.getRole()
 //       def unitList=dutyRosterReportService.getUnitAccordingToRole(role,user)
        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        if(role[0].toString()==BayalpatraConstants.ROLE_ADMIN){
            employeeList = employeeService.getEmpByStatus()

        }
        else if(role[0].toString()==BayalpatraConstants.ROLE_DEPARTMENT_HEAD){
            def finalDept = departmentService.getDepartmentList(session["department"])
            employeeList = employeeService.getEmpByStatusAndDepartment(finalDept)

        }else if(role[0].toString()==BayalpatraConstants.ROLE_SUPERVISOR){

            def supervisor = Supervisor.findByEmployee(user?.employee)
            if(supervisor)
                employeeList = employeeService.getEmployeeBySupervisor(supervisor,user,null)
        }


        if(params.emp && params.leaveMonth){
            def employee = Employee.findById(params.emp)
            leaveReportInstanceList = leaveReportService.getLeaveReportByEmpAndMonth(employee,params.leaveMonth,params)
            count = leaveReportService.getCountByEmpAndMonth(employee,params.leaveMonth)
        }else if (params.deptSelected && params.leaveMonth){
            def dept=departmentService.getDepartmentList(Long.parseLong(params.deptSelected))
            def departmentName=Department.createCriteria().list(){
                'in'("id",dept)
            }
            leaveReportInstanceList=leaveReportService.getLeaveReportByDeptAndMonth(departmentName,params.leaveMonth,params)
            count = leaveReportService.getLeaveCountByDeptAndMonth(departmentName,params.leaveMonth)
        }

        else if(params.emp){
            def employee = Employee.findById(params.emp)
            leaveReportInstanceList = leaveReportService.getLeaveReportByEmp(employee,params)
            count = leaveReportService.getCountByEmp(employee)
        }
        else if(params.leaveMonth){
            leaveReportInstanceList = leaveReportService.getLeaveReportByMonth(employeeList,params.leaveMonth,params)
            count = leaveReportService.getCountByMonth(employeeList,params.leaveMonth)
        }else if(params.deptSelected){
            def dept=departmentService.getDepartmentList(Long.parseLong(params.deptSelected))
            def departmentNames=Department.createCriteria().list(){
                'in'("id",dept)
            }

            leaveReportInstanceList = leaveReportService.getLeaveReportByDepartment(departmentNames,params)
            count = leaveReportService.getCountByDepartment(departmentNames)

        }
        else {
            if(role==BayalpatraConstants.ROLE_ADMIN){

                leaveReportInstanceList = leaveReportService.getLeaveReport(employeeList,params)
                count =  leaveReportService.getCountByRole(employeeList)
            }
            else if(role==BayalpatraConstants.ROLE_DEPARTMENT_HEAD){

                leaveReportInstanceList = leaveReportService.getLeaveReport(employeeList,params)
                count =  leaveReportService.getCountByRole(employeeList)
            }else if(role==BayalpatraConstants.ROLE_SUPERVISOR){

                if(employeeList){

                    leaveReportInstanceList = leaveReportService.getLeaveReport(employeeList,params)
                    count =  leaveReportService.getCountByRole(employeeList)
                }



            }
        }
        render(template: "ajaxFilteredLeaveList", model:[leaveReportInstanceList: leaveReportInstanceList, leaveReportInstanceTotal: count, leaveMonth:params.leaveMonth,
                                                         employee:params.emp,deptSelected: params.deptSelected, source: params.source])
        return

    }

    def create = {
        def user = User.findById(springSecurityService.principal.id)
        def leaveReportInstance = new LeaveReport()
        def role = roleService.getRole()
        leaveReportInstance.properties = params
        def employeeList
        if(role[0].toString() ==BayalpatraConstants.ROLE_ADMIN){
            employeeList = employeeService.getEmpByStatus()
        }
        else if(role[0].toString()==BayalpatraConstants.ROLE_DEPARTMENT_HEAD){
            def finalDept = departmentService.getDepartmentList(session["department"])
            employeeList = employeeService.getEmpByStatusAndDepartment(finalDept)
        }else if(role[0].toString()==BayalpatraConstants.ROLE_SUPERVISOR){

            def supervisor = Supervisor.findByEmployee(user?.employee)
            if(supervisor){
                employeeList = employeeService.getEmployeeBySupervisor(supervisor,user,null)
            }
        }
        return [leaveReportInstance: leaveReportInstance,employeeList: employeeList]
    }

    def save = {
        def leaveReportInstance = new LeaveReport(params)
        leaveReportInstance.earnedLeave=0
        leaveReportInstance.extraDay=0
        leaveReportInstance.extraTime=0
        leaveReportInstance.paidLeave=0
        leaveReportInstance.unpaidLeave=0
        leaveReportInstance.annualLeaveDays=0
        leaveReportInstance.openingBalance=0
        leaveReportInstance.leaveDate = DateUtils.toMonthYearFormat(params.leaveDate)
        if (leaveReportInstance.save(flush: true)) {
            flash.message = "Opening balance create"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [leaveReportInstance: leaveReportInstance])
        }
    }

    //Generate Monthly Roster Report According to New Format  (Patch)

    def generateMonthlyRosterReportAll={
        for(year in 2011..2012){
            for(month in 0..11){
                Calendar ca = Calendar.getInstance()
                ca.set(year,month,1)
                dutyRosterReportService.generateMonthlyRosterReport(DateUtils.dateToString(ca.getTime()))
            }
        }
    }

    def extraTimeReport = {
        redirect(controller: 'leaveReport', action: 'list',params: [source:'extraTime'])
    }


    def runLeaveReport = {
        leaveService.calculateLeaveReport()
        render 'DONE !!'
    }


}
