package com.bayalpatra.hrm

import com.bayalpatra.commons.BayalpatraEmail
import commons.BayalpatraConstants
import commons.DateUtils

class EmployeeLeaveDetailController {
    def exportService
    def springSecurityService
    def dataSource
    def leaveService

    EmployeeLeaveService employeeLeaveService      ///comment for recommit!
    DepartmentService departmentService
    EmployeeService employeeService
    RoleService roleService
    LeaveTypeService leaveTypeService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        def employeeListByStatus = employeeService.getEmpByStatus()

        def employeeLeaveDetailInstanceList
        def employeeList
        def user = User.findById(springSecurityService.principal.id)
        def role = roleService.getRole()
        def monthList
        def adminContactEmail = BayalpatraConstants.ADMIN_CONTACT_PERSON
        def curUserEmail = user?.employee?.email
        def isAdminContact = false
        if (adminContactEmail==curUserEmail && role==BayalpatraConstants.ROLE_ADMIN) isAdminContact=true

        boolean isEmp=false
        boolean isAdmin=false
        def count = 0

        def max = 30
        def sortingParam = request.getParameter("sort") ?: 'employee.firstName';
        def sortingOrder = request.getParameter("order") ?: 'asc';
        def offset = request.getParameter("offset") ?:'0';

        departmentService.departmentTree = ""
        def deptTree =  departmentService.generateNavigation(0)
        def yearList = employeeLeaveService.getLeaveYearList()

        def month
        def year

        if (params.month){
            if (params.month == "Jan"){
                month = 1
            }else if (params.month == "Feb"){
                month = 2
            } else if (params.month == "Mar"){
                month = 3
            }else if (params.month == "Apr"){
                month = 4
            }else if (params.month == "May"){
                month = 5
            }else if (params.month == "Jun"){
                month = 6
            }else if (params.month == "Jul"){
                month = 7
            }else if (params.month == "Aug"){
                month = 8
            }else if (params.month == "Sep"){
                month = 9
            } else if (params.month == "Oct"){
                month = 10
            }else if (params.month == "Nov"){
                month = 11
            }else if (params.month == "Dec"){
                month = 12
            }

        }

        if(params.employeeIs){  // params.employeeIs refers to id of employee
            params.max=max
            params.offset=offset
            employeeLeaveDetailInstanceList = EmployeeLeaveDetail.findAllByEmployee(Employee.findById(params.employeeIs),params)
            count = EmployeeLeaveDetail.findAllByEmployee(Employee.findById(params.employeeIs)).size()
            isEmp = true
            Employee emp = Employee.findById(params.employeeIs)
            if(params?.exportFormat && params.exportFormat != "html"){

                response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                response.setHeader("Content-disposition", "attachment; filename= Employee_Leave_Detail.${params.extension}")

                List fields = [
                        "employee",
                        "leaveType",
                        "fromDate",
                        "toDate",
                        "leaveDifference",
                        "approvedBy",
                        "status"
                ]
                Map labels = ["employee":"Employee", "leaveType":"Leave type", "fromDate":"From Date", "toDate":"To Date", "leaveDifference":"Leave Days", "approvedBy":"Approved By", "status": "Status"]
                //Date Closure
                def fDate = { domain, value ->
                    return value.format("yyyy-MM-dd")
                }
                Map formatter = [fromDate: fDate,toDate:fDate]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15
                ]]

                exportService.export(params.exportFormat, response.outputStream,employeeLeaveDetailInstanceList,fields, labels,formatter,parameters)
            }


            return [employeeLeaveDetailInstanceList: employeeLeaveDetailInstanceList, employeeLeaveDetailInstanceTotal: count,isEmp:isEmp,employeeInstance:emp]

        }
        else{

            def status = [BayalpatraConstants.LEAVE_APPROVED,BayalpatraConstants.LEAVE_DENIED]


            if(role[0].toString()==BayalpatraConstants.ROLE_ADMIN){
               // unitList = Unit.findAll()
                isAdmin=true

                if (params.employeeId && params.month && params.year){
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(Employee.findAllById(Integer.parseInt(params.employeeId)),month,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(Employee.findAllById(Integer.parseInt(params.employeeId)),month,Integer.parseInt(params.year))
                }/* else if(params.unit && params.month && params.year) {
                    def unit = Unit.findById(Integer.parseInt(params.unit))
                    employeeList = employeeService.getEmpByStatusAndUnit(unit)
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(employeeList,month,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(employeeList,month,Integer.parseInt(params.year))
                }*/else if(params.department && params.month && params.year) {
                    def department = departmentService.getDepartmentList(Long.parseLong(params.department))
                    employeeList = employeeService.getEmpByStatusAndDepartment(department)
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(employeeList,month,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(employeeList,month,Integer.parseInt(params.year))
                }    else if(params.employeeId && params.year) {
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(Employee.findAllById(Integer.parseInt(params.employeeId)),null,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(Employee.findAllById(Integer.parseInt(params.employeeId)),null,Integer.parseInt(params.year))
                }/*   else if(params.unit && params.year) {
                    def unit = Unit.findById(Integer.parseInt(params.unit))
                    employeeList = employeeService.getEmpByStatusAndUnit(unit)
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(employeeList,null,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(employeeList,null,Integer.parseInt(params.year))
                }*/else if(params.department && params.year) {
                    def department = departmentService.getDepartmentList(Long.parseLong(params.department))
                    employeeList = employeeService.getEmpByStatusAndDepartment(department)
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(employeeList,null,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(employeeList,null,Integer.parseInt(params.year))
                }   else if(params.employeeId && params.month) {
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(Employee.findAllById(Integer.parseInt(params.employeeId)),month,null,params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(Employee.findAllById(Integer.parseInt(params.employeeId)),month,null)
                }/*   else if(params.unit && params.month) {
                    def unit = Unit.findById(Integer.parseInt(params.unit))
                    employeeList = employeeService.getEmpByStatusAndUnit(unit)
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(employeeList,month,null,params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(employeeList,month,null)
                }*/else if(params.department && params.month) {
                    def department = departmentService.getDepartmentList(Long.parseLong(params.department))
                    employeeList = employeeService.getEmpByStatusAndDepartment(department)
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(employeeList,month,null,params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(employeeList,month,null)
                } else if(params.month && params.year) {
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(null,month,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(null,month,Integer.parseInt(params.year))
                } else if(params.employeeId){
                    employeeList = Employee.get(Integer.parseInt(params.employeeId))
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(employeeList,null,null,params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(employeeList,null,null)

                }else if (params.department){
                    def department = departmentService.getDepartmentList(Long.parseLong(params.department))
                    employeeList = employeeService.getEmpByStatusAndDepartment(department)
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(employeeList,null,null,params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(employeeList,null,null)

                }/* else if (params.unit){
                    def unit = Unit.findById(Integer.parseInt(params.unit))
                    employeeList = employeeService.getEmpByStatusAndUnit(unit)
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(employeeList,null,null,params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(employeeList,null,null)
                }*/else if (params.month){
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(null,month,null,params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(null,month,null)

                } else if (params.year){
                    year  = Integer.parseInt(params.year)
                    employeeLeaveDetailInstanceList = employeeLeaveService.getLeaveListForFilter(null,null,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
                    count =  employeeLeaveService.getCountForFilter(null,null,Integer.parseInt(params.year))

                }else{

                    employeeList = employeeService.getEmpByStatus()
                    employeeLeaveDetailInstanceList = employeeLeaveService.getEmployeeLeaveDetail(status,employeeList,isAdmin,params,max,offset,sortingParam,sortingOrder)
                    count = employeeLeaveService.getCountLeaveDetailRoleDeptHead(status,employeeList)
                }


//                if(employeeList){
//                    employeeLeaveDetailInstanceList = employeeLeaveService.getEmployeeLeaveDetail(BayalpatraConstants.LEAVE_APPROVED,employeeList,isAdmin,params,max,offset,sortingParam,sortingOrder)
//                    count = employeeLeaveService.getCountLeaveDetailRoleDeptHead(BayalpatraConstants.LEAVE_APPROVED,employeeList)

                if(params?.exportFormat && params.exportFormat != "html"){

                    response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                    response.setHeader("Content-disposition", "attachment; filename= Employee_Leave_Detail.${params.extension}")

                    List fields = [
                            "employee",
                            "leaveType",
                            "fromDate",
                            "toDate",
                            "leaveDifference",
                            "approvedBy",
                            "status"
                    ]
                    Map labels = ["employee":"Employee", "leaveType":"Leave type", "fromDate":"From Date", "toDate":"To Date", "leaveDifference":"Leave Days", "approvedBy":"Approved By", "status": "Status"]
                    //Date Clouser
                    def fDate = { domain, value ->
                        return value.format("yyyy-MM-dd")
                    }
                    Map formatter = [fromDate: fDate,toDate:fDate]
                    Map parameters =["column.widths": [
                            0.15,
                            0.15,
                            0.15 ,
                            0.15 ,
                            0.15,
                            0.15,
                            0.15
                    ]]

                    exportService.export(params.exportFormat, response.outputStream,employeeLeaveService.getEmployeeLeaveDetail(BayalpatraConstants.LEAVE_APPROVED,employeeList,isAdmin,params,0,0,'employee.firstName','asc'),fields, labels,formatter,parameters)
                }
//                }

            }

            else if(role[0].toString()==BayalpatraConstants.ROLE_DEPARTMENT_HEAD){

                def finalDept = departmentService.getDepartmentList(session["department"])
                employeeList = employeeService.getEmpByStatusAndDepartment(finalDept)
                if(employeeList){
                    employeeLeaveDetailInstanceList = employeeLeaveService.getEmployeeLeaveDetail(status,employeeList,isAdmin,params,max,offset,sortingParam,sortingOrder)

                    count = employeeLeaveService.getCountLeaveDetailRoleDeptHead(status,employeeList)

                    if(params?.exportFormat && params.exportFormat != "html"){

                        response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                        response.setHeader("Content-disposition", "attachment; filename= Employee_Leave_Detail.${params.extension}")

                        List fields = [
                                "employee",
                                "leaveType",
                                "fromDate",
                                "toDate",
                                "leaveDifference",
                                "approvedBy",
                                "status"
                        ]
                        Map labels = ["employee":"Employee", "leaveType":"Leave type", "fromDate":"From Date", "toDate":"To Date", "leaveDifference":"Leave Days", "approvedBy":"Approved By", "status": "Status"]
                        def fDate = { domain, value ->
                            return value.format("yyyy-MM-dd")
                        }
                        Map formatter = [fromDate: fDate,toDate:fDate]
                        Map parameters =["column.widths": [
                                0.15,
                                0.15,
                                0.15 ,
                                0.15 ,
                                0.15,
                                0.15,
                                0.15
                        ]]


                        exportService.export(params.exportFormat, response.outputStream, employeeLeaveService.getEmployeeLeaveDetail(BayalpatraConstants.LEAVE_APPROVED,employeeList,isAdmin,params,0,0,'employee.firstName','asc'), fields, labels,formatter,parameters)

                    }
                }
            }

            else if(role[0].toString()==BayalpatraConstants.ROLE_SUPERVISOR){

                def supervisor = Supervisor.findByEmployee(user?.employee)
                if(supervisor){
                    employeeList = employeeService.getEmployeeBySupervisor(supervisor,user,null)
                    if(employeeList){
                        employeeLeaveDetailInstanceList = employeeLeaveService.getEmployeeLeaveDetail(status,employeeList,isAdmin,params,max,offset,sortingParam,sortingOrder)
                        count = employeeLeaveService.getCountLeaveDetailRoleDeptHead(status,employeeList)
                    }

                    if(params?.exportFormat && params.exportFormat != "html"){

                        response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                        response.setHeader("Content-disposition", "attachment; filename= Employee_Leave_Detail.${params.extension}")

                        List fields = [
                                "employee",
                                "leaveType",
                                "fromDate",
                                "toDate",
                                "leaveDifference",
                                "approvedBy",
                                "status"
                        ]
                        Map labels = ["employee":"Employee", "leaveType":"Leave type", "fromDate":"From Date", "toDate":"To Date", "leaveDifference":"Leave Days", "approvedBy":"Approved By", "status": "Status"]
                        //Date Clouser
                        def fDate = { domain, value ->
                            return value.format("yyyy-MM-dd")
                        }
                        Map formatter = [fromDate: fDate,toDate:fDate]
                        Map parameters =["column.widths": [
                                0.15,
                                0.15,
                                0.15 ,
                                0.15 ,
                                0.15,
                                0.15,
                                0.15
                        ]]

                        exportService.export(params.exportFormat, response.outputStream,employeeLeaveService.getEmployeeLeaveDetail(BayalpatraConstants.LEAVE_APPROVED,employeeList,isAdmin,params,0,0,'employee.firstName','asc'),fields, labels,formatter,parameters)
                    }

                }
            }
        }
        return [employeeLeaveDetailInstanceList: employeeLeaveDetailInstanceList, employeeLeaveDetailInstanceTotal:count,isEmp:isEmp,isAdmin:isAdmin,employeeList:employeeListByStatus,deptTree:deptTree,yearList:yearList,unitName: params.unit,monthName:params.month,yearName:params.year,departmentName:params.department,employeeId:params.employeeId]
    }

    def approvalList = {
        def user = User.findById(springSecurityService.principal.id)
        def role = roleService.getRole()
        boolean isEmp=false
        boolean isAdmin = false
        def employeeLeaveDetailInstanceList
        def employeeList
        def count = 0

        def max = 30
        def sortingParam = request.getParameter("sort") ?: 'employee.firstName';
        def sortingOrder = request.getParameter("order") ?: 'asc';
        def offset = request.getParameter("offset") ?:'0';

        if(role[0].toString()==BayalpatraConstants.ROLE_EMPLOYEE){
            isEmp = true
        }

        if(role[0].toString()==BayalpatraConstants.ROLE_ADMIN){

            isAdmin = true
            employeeList = employeeService.getEmpByStatus()
            if(employeeList){
                employeeLeaveDetailInstanceList = employeeLeaveService.getEmployeeLeaveDetail(BayalpatraConstants.LEAVE_UNAPPROVED,employeeList,isAdmin,params,max,offset,sortingParam,sortingOrder)
                count = employeeLeaveService.getCountLeaveDetailRoleDeptHead(BayalpatraConstants.LEAVE_UNAPPROVED,employeeList)

                if(params?.exportFormat && params.exportFormat != "html"){

                    response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                    response.setHeader("Content-disposition", "attachment; filename= Employee_Leave_Detail.${params.extension}")

                    List fields = [
                            "employee",
                            "leaveType",
                            "fromDate",
                            "toDate",
                            "leaveDifference",
                            "approvedBy",
                            "status"
                    ]
                    Map labels = ["employee":"Employee", "leaveType":"Leave type", "fromDate":"From Date", "toDate":"To Date", "leaveDifference":"Leave Days", "approvedBy":"Approved By", "status": "Status"]
                    def fDate = { domain, value ->
                        return value.format("yyyy-MM-dd")
                    }
                    Map formatter = [fromDate: fDate,toDate:fDate]
                    Map parameters =["column.widths": [
                            0.15,
                            0.15,
                            0.15 ,
                            0.15 ,
                            0.15,
                            0.15,
                            0.15
                    ]]

                    exportService.export(params.exportFormat, response.outputStream,employeeLeaveService.filterUnapprovedLeave(params,employeeLeaveDetailInstanceList),fields, labels,formatter,parameters)
                }
            }
        }

        else if(role[0].toString()==BayalpatraConstants.ROLE_DEPARTMENT_HEAD){

            def finalDept = departmentService.getDepartmentList(session["department"])
            employeeList = employeeService.getEmpByStatusAndDepartment(finalDept)
            if(employeeList){
                employeeLeaveDetailInstanceList = employeeLeaveService.getEmployeeLeaveDetail(BayalpatraConstants.LEAVE_UNAPPROVED,employeeList,isAdmin,params,max,offset,sortingParam,sortingOrder)
                count = employeeLeaveService.getCountLeaveDetailRoleDeptHead(BayalpatraConstants.LEAVE_UNAPPROVED,employeeList)

                if(params?.exportFormat && params.exportFormat != "html"){

                    response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                    response.setHeader("Content-disposition", "attachment; filename= Employee_Leave_Detail.${params.extension}")

                    List fields = [
                            "employee",
                            "leaveType",
                            "fromDate",
                            "toDate",
                            "leaveDifference",
                            "approvedBy",
                            "status"
                    ]
                    Map labels = ["employee":"Employee", "leaveType":"Leave type", "fromDate":"From Date", "toDate":"To Date", "leaveDifference":"Leave Days", "approvedBy":"Approved By", "status": "Status"]
                    def fDate = { domain, value ->
                        return value.format("yyyy-MM-dd")
                    }
                    Map formatter = [fromDate: fDate,toDate:fDate]
                    Map parameters =["column.widths": [
                            0.15,
                            0.15,
                            0.15 ,
                            0.15 ,
                            0.15,
                            0.15,
                            0.15
                    ]]

                    exportService.export(params.exportFormat, response.outputStream,employeeLeaveService.filterUnapprovedLeave(params,employeeLeaveDetailInstanceList),fields, labels,formatter,parameters)
                }
            }
        }
        else if(role[0].toString()==BayalpatraConstants.ROLE_SUPERVISOR){

            def supervisor = Supervisor.findByEmployee(user?.employee)
            //def supervisorId=Supervisor.findById(user?.id)
            if(supervisor){
                employeeList = employeeService.getEmployeeBySupervisor(supervisor,user,null)
                if(employeeList){

                    if(params?.exportFormat && params.exportFormat != "html"){
                        response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                        response.setHeader("Content-disposition", "attachment; filename= Employee_Leave_Detail.${params.extension}")

                        List fields = [
                                "employee",
                                "leaveType",
                                "fromDate",
                                "toDate",
                                "leaveDifference",
                                "approvedBy",
                                "status"
                        ]
                        Map labels = ["employee":"Employee", "leaveType":"Leave type", "fromDate":"From Date", "toDate":"To Date", "leaveDifference":"Leave Days", "approvedBy":"Approved By", "status": "Status"]
                        def fDate = { domain, value ->
                            return value.format("yyyy-MM-dd")
                        }
                        Map formatter = [fromDate: fDate,toDate:fDate]
                        Map parameters =["column.widths": [
                                0.15,
                                0.15,
                                0.15 ,
                                0.15 ,
                                0.15,
                                0.15,
                                0.15
                        ]]

                        exportService.export(params.exportFormat, response.outputStream,employeeLeaveService.filterUnapprovedLeave(params,employeeLeaveDetailInstanceList),fields, labels,formatter,parameters)
                    }

                    employeeLeaveDetailInstanceList = employeeLeaveService.getEmployeeLeaveDetail(BayalpatraConstants.LEAVE_UNAPPROVED,employeeList,isAdmin,params,max,offset,sortingParam,sortingOrder)
                    count = employeeLeaveService.getCountLeaveDetailRoleDeptHead(BayalpatraConstants.LEAVE_UNAPPROVED,employeeList)
                }

            }
        }
        render(view:'leaveApprovalList',model:[employeeLeaveDetailInstanceList :employeeLeaveDetailInstanceList,employeeLeaveDetailInstanceTotal:count,isEmp:isEmp,employeeList:employeeList])
    }

    def filterUnapprovedLeave = {
        def user = User.findById(springSecurityService.principal.id)
        def role = roleService.getRole()
        def sortingParam = request.getParameter("sort") ?: 'employee.firstName';
        def sortingOrder = request.getParameter("order") ?: 'asc';
        def employeeList,employeeLeaveDetailInstanceList
        def count
        if(role[0].toString()==BayalpatraConstants.ROLE_ADMIN){
            employeeList = employeeService.getEmpByStatus()
            if(employeeList){
                count = employeeLeaveService.getCountLeaveDetailRoleDeptHead(BayalpatraConstants.LEAVE_UNAPPROVED,employeeList)
                employeeLeaveDetailInstanceList = employeeLeaveService.getEmployeeLeaveDetail(BayalpatraConstants.LEAVE_UNAPPROVED,employeeList,true,params,count,0,sortingParam,sortingOrder)
            }
        }else if(role[0].toString()==BayalpatraConstants.ROLE_DEPARTMENT_HEAD){
            def finalDept = departmentService.getDepartmentList(session["department"])
            employeeList = employeeService.getEmpByStatusAndDepartment(finalDept)
            if(employeeList){
                count = employeeLeaveService.getCountLeaveDetailRoleDeptHead(BayalpatraConstants.LEAVE_UNAPPROVED,employeeList)
                employeeLeaveDetailInstanceList = employeeLeaveService.getEmployeeLeaveDetail(BayalpatraConstants.LEAVE_UNAPPROVED,employeeList,false,params,count,0,sortingParam,sortingOrder)
            }
        }else if(role[0].toString()==BayalpatraConstants.ROLE_SUPERVISOR){
            def supervisor = Supervisor.findByEmployee(user?.employee)
            if(supervisor){
                employeeList = employeeService.getEmployeeBySupervisor(supervisor,user,null)
                if(employeeList){
                    count = employeeLeaveService.getCountLeaveDetailRoleDeptHead(BayalpatraConstants.LEAVE_UNAPPROVED,employeeList)
                    employeeLeaveDetailInstanceList = employeeLeaveService.getEmployeeLeaveDetail(BayalpatraConstants.LEAVE_UNAPPROVED,employeeList,false,params,count,0,sortingParam,sortingOrder)
                }
            }
        }
        render(template: 'unapprovedLeaveList',model: [employeeLeaveDetailInstanceList:employeeLeaveService.filterUnapprovedLeave(params,employeeLeaveDetailInstanceList),isEmp: params.isEmp])
    }


    def employeeLeaveDetailFilter = {
        def leaveList
        int count=0
        def employee
        def month
        def year
        boolean isAdmin=false
        def max = 30
        def sortingParam = request.getParameter("sort") ?: 'employee.firstName';
        def sortingOrder = request.getParameter("order") ?: 'asc';
        def offset = request.getParameter("offset") ?:'0';

        def user = User.findById(springSecurityService.principal.id)
        def role = roleService.getRole()
        if (role==BayalpatraConstants.ROLE_ADMIN)  {
            isAdmin = true
        }

        session.leaveFilterMonth = params.month
        session.leaveFilterYear = params.year
        session.leaveFilterUnit = params.unit
        session.leaveFilterDepartment = params.department
        session.leaveFilterEmployee = params.employeeId


        if (params.month){
            if (params.month == "Jan"){
                month = 1
            }else if (params.month == "Feb"){
                month = 2
            } else if (params.month == "Mar"){
                month = 3
            }else if (params.month == "Apr"){
                month = 4
            }else if (params.month == "May"){
                month = 5
            }else if (params.month == "Jun"){
                month = 6
            }else if (params.month == "Jul"){
                month = 7
            }else if (params.month == "Aug"){
                month = 8
            }else if (params.month == "Sep"){
                month = 9
            } else if (params.month == "Oct"){
                month = 10
            }else if (params.month == "Nov"){
                month = 11
            }else if (params.month == "Dec"){
                month = 12
            }

        }

        if (params.employeeId && params.month && params.year){
            leaveList = employeeLeaveService.getLeaveListForFilter(Employee.findAllById(Integer.parseInt(params.employeeId)),month,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(Employee.findAllById(Integer.parseInt(params.employeeId)),month,Integer.parseInt(params.year))
        } else if(params.unit && params.month && params.year) {
            def unit = Unit.findById(Integer.parseInt(params.unit))
            employee = employeeService.getEmpByStatusAndUnit(unit)
            leaveList = employeeLeaveService.getLeaveListForFilter(employee,month,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(employee,month,Integer.parseInt(params.year))
        }else if(params.department && params.month && params.year) {
            def department = departmentService.getDepartmentList(Long.parseLong(params.department))
            employee = employeeService.getEmpByStatusAndDepartment(department)
            leaveList = employeeLeaveService.getLeaveListForFilter(employee,month,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(employee,month,Integer.parseInt(params.year))
        }    else if(params.employeeId && params.year) {
            leaveList = employeeLeaveService.getLeaveListForFilter(Employee.findAllById(Integer.parseInt(params.employeeId)),null,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(Employee.findAllById(Integer.parseInt(params.employeeId)),null,Integer.parseInt(params.year))
        }   else if(params.unit && params.year) {
            def unit = Unit.findById(Integer.parseInt(params.unit))
            employee = employeeService.getEmpByStatusAndUnit(unit)
            leaveList = employeeLeaveService.getLeaveListForFilter(employee,null,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(employee,null,Integer.parseInt(params.year))
        }else if(params.department && params.year) {
            def department = departmentService.getDepartmentList(Long.parseLong(params.department))
            employee = employeeService.getEmpByStatusAndDepartment(department)
            leaveList = employeeLeaveService.getLeaveListForFilter(employee,null,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(employee,null,Integer.parseInt(params.year))
        }   else if(params.employeeId && params.month) {
            leaveList = employeeLeaveService.getLeaveListForFilter(Employee.findAllById(Integer.parseInt(params.employeeId)),month,null,params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(Employee.findAllById(Integer.parseInt(params.employeeId)),month,null)
        }   else if(params.unit && params.month) {
            def unit = Unit.findById(Integer.parseInt(params.unit))
            employee = employeeService.getEmpByStatusAndUnit(unit)
            leaveList = employeeLeaveService.getLeaveListForFilter(employee,month,null,params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(employee,month,null)
        }else if(params.department && params.month) {
            def department = departmentService.getDepartmentList(Long.parseLong(params.department))
            employee = employeeService.getEmpByStatusAndDepartment(department)
            leaveList = employeeLeaveService.getLeaveListForFilter(employee,month,null,params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(employee,month,null)
        } else if(params.month && params.year) {
            leaveList = employeeLeaveService.getLeaveListForFilter(null,month,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(null,month,Integer.parseInt(params.year))
        } else if(params.employeeId){
            employee = Employee.get(Integer.parseInt(params.employeeId))
            leaveList = employeeLeaveService.getLeaveListForFilter(employee,null,null,params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(employee,null,null)

        }else if (params.department){
            def department = departmentService.getDepartmentList(Long.parseLong(params.department))
            employee = employeeService.getEmpByStatusAndDepartment(department)
            leaveList = employeeLeaveService.getLeaveListForFilter(employee,null,null,params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(employee,null,null)

        } else if (params.unit){
            def unit = Unit.findById(Integer.parseInt(params.unit))
            employee = employeeService.getEmpByStatusAndUnit(unit)
            leaveList = employeeLeaveService.getLeaveListForFilter(employee,null,null,params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(employee,null,null)
        }else if (params.month){
            leaveList = employeeLeaveService.getLeaveListForFilter(null,month,null,params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(null,month,null)

        } else if (params.year){
            year  = Integer.parseInt(params.year)
            leaveList = employeeLeaveService.getLeaveListForFilter(null,null,Integer.parseInt(params.year),params,max,offset,sortingParam,sortingOrder)
            count =  employeeLeaveService.getCountForFilter(null,null,Integer.parseInt(params.year))

        }else{
            def status = [BayalpatraConstants.LEAVE_APPROVED,BayalpatraConstants.LEAVE_DENIED]
            def employeeList = employeeService.getEmpByStatus()
            leaveList = employeeLeaveService.getEmployeeLeaveDetail(status,employeeList,isAdmin,params,max,offset,sortingParam,sortingOrder)
            count = employeeLeaveService.getCountLeaveDetailRoleDeptHead(status,employeeList)
        }
        render(template: "leaveFilterByCriteria", model:[leaveList:leaveList,leaveCount:count,unitName: params.unit,employeeId:params.employeeId,monthName:params.month,yearName:params.year,departmentName:params.department,unitName:params.unit])

    }

    def leaveDetailExport = {

        def month =    session.leaveFilterMonth
        def year =    session.leaveFilterYear
        def unit =    session.leaveFilterUnit
        def department =    session.leaveFilterDepartment
        def employeeId =    session.leaveFilterEmployee
        def max = 1000
        def offset = request.getParameter("offset") ?:'0';

        def sortingParam ='employee.firstName';
        def sortingOrder = 'asc';

        def employeeList

        if (month){
            if (month == "Jan"){
                month = 1
            }else if (month == "Feb"){
                month = 2
            } else if (month == "Mar"){
                month = 3
            }else if (month == "Apr"){
                month = 4
            }else if (month == "May"){
                month = 5
            }else if (month == "Jun"){
                month = 6
            }else if (month == "Jul"){
                month = 7
            }else if (month == "Aug"){
                month = 8
            }else if (month == "Sep"){
                month = 9
            } else if (month == "Oct"){
                month = 10
            }else if (month == "Nov"){
                month = 11
            }else if (month == "Dec"){
                month = 12
            }

        }

        if (year){
            year = Integer.parseInt(year)
        }

        if (unit) {
            unit = Unit.findById(Integer.parseInt(unit))
            employeeList = employeeService.getEmpByStatusAndUnit(unit)
        }

        if (department){
            department = departmentService.getDepartmentList(Long.parseLong(department))
            employeeList = employeeService.getEmpByStatusAndDepartment(department)
        }
        if (employeeId){
            employeeList = Employee.get(Integer.parseInt(employeeId))
        }

        if(params?.exportFormat && params.exportFormat != "html"){

            response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
            response.setHeader("Content-disposition", "attachment; filename= Employee_Leave_Detail.${params.extension}")

            List fields = [
                    "employee",
                    "leaveType",
                    "fromDate",
                    "toDate",
                    "leaveDifference",
                    "approvedBy",
                    "status"
            ]
            Map labels = ["employee":"Employee", "leaveType":"Leave type", "fromDate":"From Date", "toDate":"To Date", "leaveDifference":"Leave Days", "approvedBy":"Approved By", "status": "Status"]
            //Date Clouser
            def fDate = { domain, value ->
                return value.format("yyyy-MM-dd")
            }
            Map formatter = [fromDate: fDate,toDate:fDate]
            Map parameters =["column.widths": [
                    0.15,
                    0.15,
                    0.15 ,
                    0.15 ,
                    0.15,
                    0.15,
                    0.15
            ]]

            exportService.export(params.exportFormat, response.outputStream, employeeLeaveService.getLeaveListForFilter(employeeList,month,year,params,max,offset,sortingParam,sortingOrder),fields, labels,formatter,parameters)
        }


    }

    def create = {
        def user = User.findById(springSecurityService.principal.id)
        def role = roleService.getRole()
        def employeeList
        def leaveType = leaveTypeService.getLeaveType()
        boolean isEmp=false
        Employee emp
        if(params.employeeIs){
            isEmp = true
            emp = Employee.findById(params.employeeIs)
        }

        if(role[0].toString()==BayalpatraConstants.ROLE_ADMIN){
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

        def employeeLeaveDetailInstance = new EmployeeLeaveDetail()
        //        employeeLeaveDetailInstance.properties = params
        return [employeeLeaveDetailInstance: employeeLeaveDetailInstance, employeeList: employeeList,leaveType: leaveType,isEmp:isEmp,employeeInstance:emp,leaveDay:0]
    }

    def save = {


        def emp
        def chkLeave
        def employeeList
        boolean isEmp=false
        def leaveType = leaveTypeService.getLeaveType()
        def employeeLeaveDetailInstance = new EmployeeLeaveDetail(params)
        def user = User.findById(springSecurityService.principal.id)
        def role = roleService.getRole()
        def daysDifference = DateUtils.getDaysFromTwoDates(employeeLeaveDetailInstance.getFromDate(),employeeLeaveDetailInstance.getToDate())

        daysDifference = daysDifference - employeeLeaveDetailInstance.getLeaveDays()
        employeeLeaveDetailInstance.setLeaveDifference(daysDifference)


        def execDirectorEmail
        def adminEmail = new ArrayList<String>()
        BayalpatraEmail annapEmail = new BayalpatraEmail()

        if(params.employee && !params.checkForMenu){
            isEmp = true
            emp = Employee.findById(params.employee?.id)
        }

        if(role[0].toString()==BayalpatraConstants.ROLE_ADMIN){
            employeeList = employeeService.getEmpByStatus()
        }else if(role[0].toString()==BayalpatraConstants.ROLE_DEPARTMENT_HEAD){
            def finalDept = departmentService.getDepartmentList(session["department"])
            employeeList = employeeService.getEmpByStatusAndDepartment(finalDept)
        }else if(role[0].toString()==BayalpatraConstants.ROLE_SUPERVISOR){

            def supervisor = Supervisor.findByEmployee(user?.employee)
            if(supervisor){
                employeeList = employeeService.getEmployeeBySupervisor(supervisor,user,null)
            }
        }

        if(employeeLeaveDetailInstance.employee){
            chkLeave=employeeLeaveService.validateLeaveRange(employeeLeaveDetailInstance.fromDate,employeeLeaveDetailInstance.toDate,employeeLeaveDetailInstance.employee)
        }
        if(chkLeave==0){
            flash.message="Attempted to add invalid leave. Specified leave date seems to be the date before the join date of Employee."
            render(view: "create", model: [employeeLeaveDetailInstance: employeeLeaveDetailInstance,employeeList: employeeList,leaveType: leaveType,isEmp:isEmp,employeeInstance:emp,leaveDay:employeeLeaveDetailInstance?.leaveDays] )
        }else if(chkLeave==1){
            flash.message="Attempted to add invalid leave. Specified leave date overlaps the existing leave date of the Employee."
            render(view: "create", model: [employeeLeaveDetailInstance: employeeLeaveDetailInstance,employeeList: employeeList,leaveType: leaveType,isEmp:isEmp,employeeInstance:emp,leaveDay:employeeLeaveDetailInstance?.leaveDays] )
        }else{
            if (employeeLeaveDetailInstance.save(flush: true)) {
                String empLeaveType=employeeLeaveDetailInstance.leaveType
                if (!empLeaveType.contains('Leave'))
                    empLeaveType+=" Leave"

                if (BayalpatraConstants.CLIENT_NAME.equals(BayalpatraConstants.CLIENT_BAYALPATRA)){

                    if (employeeLeaveDetailInstance.leaveType.paidUnpaid  == BayalpatraConstants.UNPAID_LEAVE){

                        //get required to_addresses for concerned department HOD, concerned Unit_Incharge,admin department HOD, account department HOD and
                        // executive director.

                        def toAddressArray = new ArrayList<String>();

                        toAddressArray = employeeService.getToAddresses(employeeLeaveDetailInstance.employee);           //gets all the associated department heads

                        if (employeeLeaveDetailInstance.employee.unit){
                            def unitIncharge = employeeService.getUnitInchargeEmail(employeeLeaveDetailInstance.employee.unit)      //gets all the associated unit incharges

                            unitIncharge.eachWithIndex { val, i ->
                                toAddressArray.add(val)
                            }
                        }

                        execDirectorEmail = Employee.findAllByDesignation(Designation.findByJobTitleName(BayalpatraConstants.DESIGNATION_EXECUTIVE_DIRECTOR))

                        if (execDirectorEmail){
                            execDirectorEmail.eachWithIndex { vox, idx ->
                                toAddressArray.add(vox.email)
                            }
                        }

                        //statically add group email to toAddressArray for account and admin dept head.

                        toAddressArray.add(BayalpatraConstants.ADMIN_EMAIL)
                        toAddressArray.add(BayalpatraConstants.ACCOUNT_EMAIL)
                        //send email of appointment to concerned HOD, Unit_Incharge, Executive Director and group email (admin and account head)

                        toAddressArray.each{ val->
                            BayalpatraEmail BayalpatraEmail = new BayalpatraEmail()
                            BayalpatraEmail.toAddress = val
                            BayalpatraEmail.subject = "Leave Application for " + employeeLeaveDetailInstance.employee.fullName
                            BayalpatraEmail.messageBody = "Dear All,<br><br>" +  employeeLeaveDetailInstance.employee.fullName + " has requested for "+empLeaveType+""".<br/><br/> Please log into Bayalpatra to take any action.<br/><br/>
                            Thank You.<br>
                            Bayalpatra Support."""
                            BayalpatraEmail.save(flush:true, failOnError: true)
                        }

                        //create a email object and save to send the email notification to the Employee
                        BayalpatraEmail BayalpatraEmail = new BayalpatraEmail()
                        BayalpatraEmail.toAddress = employeeLeaveDetailInstance.employee.email
                        BayalpatraEmail.subject = "Leave Application for " + employeeLeaveDetailInstance.employee.fullName
                        BayalpatraEmail.messageBody = "Dear "+ employeeLeaveDetailInstance.employee.fullName + ",<br><br> You have requested for "+empLeaveType+""".<br/><br/> Please log into Bayalpatra to see the status of leave.<br/><br/>
                            Thank You.<br>
                            Bayalpatra Support."""

                        BayalpatraEmail.save(flush:true, failOnError: true)
                    }
                    else if(employeeLeaveDetailInstance.leaveType.paidUnpaid == BayalpatraConstants.PAID_LEAVE){

                        //if leavedays is less than 7 send email to concerned person and supervisor, if greater or equal to 7 then send email to
                        // concerned person, supervisor and admin department head
                        if (daysDifference<7){

                            emp = Employee.findById(employeeLeaveDetailInstance.employee.id)
                            def supervisorAvailable = emp.getSupervisor()
                            BayalpatraEmail BayalpatraEmail = new BayalpatraEmail()
                            if(supervisorAvailable){
                                Employee supervisor = Employee.find(emp.supervisor.employee)
                                BayalpatraEmail.toAddress = supervisor.email
                                BayalpatraEmail.ccAddress = BayalpatraConstants.ADMIN_CONTACT_PERSON
                                BayalpatraEmail.subject = "Leave Application " + emp.fullName
                                BayalpatraEmail.messageBody = "Dear " + employeeService.getSalutation(supervisor.gender) + ",<br/><br/>" + emp.fullName + " has requested for "+empLeaveType+""".<br/><br/> Please take action by logging into Bayalpatra.<br/><br/>
                            Thank You.<br>
                            Bayalpatra Support."""
                            }
                            else{
                                BayalpatraEmail.toAddress = BayalpatraConstants.ADMIN_CONTACT_PERSON
                                BayalpatraEmail.subject = "Leave Application " + emp.fullName
                                BayalpatraEmail.messageBody = "Dear All,<br><br>" + emp.fullName + " has requested for "+empLeaveType+""".<br/><br/> Please take action by logging into Bayalpatra.<br/><br/>
                            Thank You.<br>
                            Bayalpatra Support."""
                            }

                            BayalpatraEmail.save(flush:true, failOnError: true)

                            //create a email object and save to send the email notification to the Employee
                            BayalpatraEmail aEmail = new BayalpatraEmail()
                            aEmail.toAddress = employeeLeaveDetailInstance.employee.email
                            aEmail.subject = "Leave Application for " + employeeLeaveDetailInstance.employee.fullName
                            aEmail.messageBody = "Dear "+ employeeLeaveDetailInstance.employee.fullName + ",<br><br> You have requested for "+empLeaveType+""".<br/><br/> Please log into Bayalpatra to see the status of leave.<br/><br/>
                            Thank You.<br>
                            Bayalpatra Support."""

                            aEmail.save(flush:true, failOnError: true)
                        }else if (daysDifference>=7){
                            emp = Employee.findById(employeeLeaveDetailInstance.employee.id)
                            def supervisorAvailable = emp.getSupervisor()
                            BayalpatraEmail BayalpatraEmail = new BayalpatraEmail()
                            if(supervisorAvailable){
                                Employee supervisor = Employee.find(emp.supervisor.employee)
                                BayalpatraEmail.toAddress = supervisor.email
                                BayalpatraEmail.ccAddress = BayalpatraConstants.ADMIN_CONTACT_PERSON
                                BayalpatraEmail.subject = "Leave Application " + emp.fullName
                                BayalpatraEmail.messageBody = "Dear " + employeeService.getSalutation(supervisor.gender) + ",<br/><br/>" + emp.fullName + " has requested for "+empLeaveType+" for "+daysDifference+""" days.<br/><br/> Please take action by logging into Bayalpatra.<br/><br/>
                            Thank You.<br>
                            Bayalpatra Support."""
                            }else{
                                BayalpatraEmail.toAddress = BayalpatraConstants.ADMIN_CONTACT_PERSON
                                BayalpatraEmail.subject = "Leave Application " + emp.fullName
                                BayalpatraEmail.messageBody = "Dear All,<br><br>" + emp.fullName + " has requested for "+empLeaveType+" for "+daysDifference+""" days.<br/><br/> Please take action by logging into Bayalpatra.<br/><br/>
                            Thank You.<br>
                            Bayalpatra Support."""
                            }

                            BayalpatraEmail.save(flush:true, failOnError: true)

                            //create a email object and save to send the email notification to the Employee
                            BayalpatraEmail aEmail = new BayalpatraEmail()
                            aEmail.toAddress = employeeLeaveDetailInstance.employee.email
                            aEmail.subject = "Leave Application for " + employeeLeaveDetailInstance.employee.fullName
                            aEmail.messageBody = "Dear "+ employeeLeaveDetailInstance.employee.fullName + ",<br><br> You have requested for "+empLeaveType+""".<br/><br/> Please log into Bayalpatra to see the status of leave.<br/><br/>
                            Thank You.<br>
                            Bayalpatra Support."""

                            aEmail.save(flush:true, failOnError: true)

                            //send notification to admin department head

                            //adminEmail = employeeLeaveService.getAdminDeptHeadEmail();



                            BayalpatraEmail annEmail = new BayalpatraEmail()
                            annEmail.toAddress = BayalpatraConstants.ADMIN_EMAIL
                            annEmail.subject = "Leave Application " + emp.fullName
                            annEmail.messageBody = "Dear All,<br><br>" + emp.fullName + " has requested for "+empLeaveType+" for "+daysDifference+""" days.<br/><br/> Please take action by logging into Bayalpatra.<br/><br/>
                            Thank You.<br>
                            Bayalpatra Support."""

                            annEmail.save(flush:true, failOnError: true)
                        }
                    }
                }



                if(role[0].toString()==BayalpatraConstants.ROLE_EMPLOYEE){
                    redirect(action: "list",params:[employeeIs:params.employee.id])
                }
                else{
                    redirect(action: "approvalList")
                }
            }
            else {
                render(view: "create", model: [employeeLeaveDetailInstance: employeeLeaveDetailInstance,employeeList: employeeList,leaveType: leaveType,isEmp:isEmp,employeeInstance:emp,leaveDay:employeeLeaveDetailInstance?.leaveDays] )
            }
        }
    }

    def show = {
        def employeeLeaveDetailInstance = EmployeeLeaveDetail.get(params.id)
        if (!employeeLeaveDetailInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeeLeaveDetail.label', default: 'EmployeeLeaveDetail'), params.id])}"
            redirect(action: "list")
        }
        else {
            [employeeLeaveDetailInstance: employeeLeaveDetailInstance]
        }



    }

    def edit = {
        def employeeLeaveDetailInstance = EmployeeLeaveDetail.get(params.id)
        def leaveType = leaveTypeService.getLeaveType()
        if (!employeeLeaveDetailInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeeLeaveDetail.label', default: 'EmployeeLeaveDetail'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [employeeLeaveDetailInstance: employeeLeaveDetailInstance,offset:params.offset,leaveType: leaveType,leaveDay:employeeLeaveDetailInstance?.leaveDays]
        }
    }


    def updateStatus = {


        def employeeLeaveDetailInstance = EmployeeLeaveDetail.get(params.id)
        def leaveName=LeaveType.findById(employeeLeaveDetailInstance.leaveType.id)
        def leaveFor=leaveName.leaveType
        def deputation=leaveFor.substring(0,4)

        if(params.approve){
            employeeLeaveDetailInstance.status=BayalpatraConstants.LEAVE_APPROVED
            employeeLeaveDetailInstance.approvedBy = session["employeeName"]
            /*if(BayalpatraConstants.CLIENT_NAME.equals(BayalpatraConstants.CLIENT_DEERWALK)){
                leaveService.updateLeaveBalanceReportOfEachEmployeeAfterLeaveApproved(employeeLeaveDetailInstance)
            }
            def sunday = dutyRosterService.getDateSunday()
            def currentSunday = sunday.minus(7)
            def dutyRoster = dutyRosterService.getDutyByEmployee(employeeLeaveDetailInstance.employee)

            if(dutyRoster){
                dutyRoster.each {
                    if(it.date>= employeeLeaveDetailInstance.fromDate && it.date<= employeeLeaveDetailInstance.toDate){
                        it.morning = false
                        it.day = false
                        it.evening = false
                        it.night = false
                        it.leaveType = employeeLeaveDetailInstance.leaveType
                    }
                }
            }*/
        }
        if(params.deny){
            employeeLeaveDetailInstance.status=BayalpatraConstants.LEAVE_DENIED
            employeeLeaveDetailInstance.approvedBy = session["employeeName"]
            EmployeeHistory empHistory = new EmployeeHistory(employee:employeeLeaveDetailInstance.employee,fieldType:'Leave',fromDate:employeeLeaveDetailInstance.fromDate,toDate:employeeLeaveDetailInstance.toDate,oldValue:BayalpatraConstants.LEAVE_DENIED)
            empHistory.save()
        }
        //create a email object and save to send the email notification
        def employeeId = employeeLeaveDetailInstance.employee.id
        Employee emp = Employee.findById(employeeId)
        def deptHeads = employeeService.getToAddresses(emp);
        def deptHeadEmails
        def ccAdd=BayalpatraConstants.ADMIN_EMAIL+", "+BayalpatraConstants.ACCOUNT_EMAIL

        for (def i = 0; i < deptHeads.size(); i++){
            deptHeadEmails = ", " + deptHeads[i].toString()

        }
        if(deptHeadEmails){
            ccAdd=ccAdd + deptHeadEmails

        }
        if(emp.supervisor){
            ccAdd=ccAdd +", "+ emp.supervisor.employee.email

        }
        BayalpatraEmail BayalpatraEmail = new BayalpatraEmail()
        BayalpatraEmail.toAddress = emp.email
        BayalpatraEmail.ccAddress = ccAdd
        def leaveReason = '<li>Leave Reason : '
        if (BayalpatraConstants.CLIENT_NAME==BayalpatraConstants.CLIENT_DEERWALK && employeeLeaveDetailInstance.leaveReason){
            leaveReason=leaveReason+employeeLeaveDetailInstance.leaveReason
        }
        leaveReason=leaveReason+'</li>'
        if (leaveFor=="Maternity Leave"){
            if (params.approve){
                BayalpatraEmail.subject = " Approval of Maternity Leave of "+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName
                BayalpatraEmail.messageBody = "The maternity leave you have applied from "+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+" has been accepted by the institution. You will be given 45 days as paid maternity leave. If you are unable to attend your duty after the maternity leave, you will have to take approval for further leave.<br><br><b>Maternity leave Details:</b><br>Name: "+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName+"<br>ID: "+employeeLeaveDetailInstance?.employee?.employeeId+"<br>Designation: "+employeeLeaveDetailInstance?.employee?.designation+"<br>Department: "+employeeLeaveDetailInstance?.employee?.departments
                if(emp.unit){BayalpatraEmail.messageBody+="<br>Unit: "+emp.unit}
                BayalpatraEmail.messageBody+="<br>Leave From :"+employeeLeaveDetailInstance?.fromDate+" to "+employeeLeaveDetailInstance?.toDate+"<br><br>Wish you a very good health.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL"

                BayalpatraEmail.save(flush:true, failOnError: true)
            }else{
                BayalpatraEmail.subject = " Denial of Maternity Leave of "+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName
                BayalpatraEmail.messageBody = "The maternity leave you have applied from "+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+" has been denied by the institution.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL\""

                BayalpatraEmail.save(flush:true, failOnError: true)

            }
        }else if(leaveFor=="Bereavement"){
            if (params.approve){
                BayalpatraEmail.subject = " Approval of Bereavement Leave of "+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName
                BayalpatraEmail.messageBody = "This institution is deeply grieved by the untimely demise of your dear one. We would like to extend deepest<br>sympathies to you and your family.<br><br><b>Bereavement leave detail:</b><br>Please be notified that you will be provided 13 days bereavement leave as paid leave from "+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+"<br>Details are as follows:<br>Name: "+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName+"<br>ID: "+employeeLeaveDetailInstance?.employee?.employeeId+"<br>Designation: "+employeeLeaveDetailInstance?.employee?.designation+"<br>Department: "+employeeLeaveDetailInstance?.employee?.departments
                if(emp.unit){BayalpatraEmail.messageBody+="<br>Unit: "+emp.unit}
                BayalpatraEmail.messageBody+="<br>Leave from: "+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+" to "+DateUtils.dateToString(employeeLeaveDetailInstance.toDate)+"<br><br>We send you our prayers in this time of grief and we wish the departed soul rest in peace.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL"
                BayalpatraEmail.save(flush:true, failOnError: true)
            }else{
                BayalpatraEmail.subject = " Denial of Bereavement Leave of "+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName
                BayalpatraEmail.messageBody = "The bereavement leave you applied from "+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+" has been denied by the institution.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL"

                BayalpatraEmail.save(flush:true, failOnError: true)

            }
        }else if (leaveFor=="Unpaid Leave"){
            if (params.approve){
                BayalpatraEmail.subject = " Approval of Unpaid Leave of "+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName
                BayalpatraEmail.messageBody = "This is to notify you that the leave you have applied has been approved as unpaid leave as per regulation of<br>the institution with effect from "+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+" to "+DateUtils.dateToString(employeeLeaveDetailInstance.toDate)+" for "+DateUtils.getDaysFromTwoDates(employeeLeaveDetailInstance.getFromDate(),employeeLeaveDetailInstance.getToDate())+""" days.
                                                <br><br>Please contact the unit incharge/HOD and HR as soon as your unpaid leave will be finished to start duty.<br><br><b>Unpaid Leave Details:</b><br>Name: """+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName+"<br>ID: "+emp?.employeeId+"<br>Designation: "+emp?.designation+"<br>Department: "+emp?.departments
                if(emp.unit){BayalpatraEmail.messageBody+="<br>Unit: "+emp.unit}
                BayalpatraEmail.messageBody+="<br>Unpaid Leave from: "+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+" to "+DateUtils.dateToString(employeeLeaveDetailInstance.toDate)+"<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL"
                BayalpatraEmail.save(flush:true, failOnError: true)
            }else{
                BayalpatraEmail.subject = " Denial of Unpaid Leave of"+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName
                BayalpatraEmail.messageBody = "The unpaid leave you applied from "+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+" has been denied by the institution.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL"

                BayalpatraEmail.save(flush:true, failOnError: true)

            }

        }
        else if(deputation=="Depu"){
            if (params.approve){
                BayalpatraEmail.subject = " Notification of Deputation of "+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName
                BayalpatraEmail.messageBody = "We would like to inform you that you have been deputed effective from "+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+" to "+DateUtils.dateToString(employeeLeaveDetailInstance.toDate)+" for "+DateUtils.getDaysFromTwoDates(employeeLeaveDetailInstance.getFromDate(),employeeLeaveDetailInstance.getToDate())+""" days.
                                               <br><br><b>Deputation Details:</b><br>Name: """+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName+"<br>ID: "+emp?.employeeId+"<br>Designation: "+emp?.designation+"<br>Department: "+emp?.departments
                if(emp.unit){BayalpatraEmail.messageBody+="<br>Unit: "+emp.unit}
                BayalpatraEmail.messageBody+="<br>New Supervisor: "+emp?.supervisor+"<br>Deputation Period: "+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+" to "+DateUtils.dateToString(employeeLeaveDetailInstance.toDate)+"<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL"
                BayalpatraEmail.save(flush:true, failOnError: true)
            }else{
                BayalpatraEmail.subject = " Denial of Deputation of"+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName
                BayalpatraEmail.messageBody = "The deputation of "+emp?.fullName+" has been denied by the institution.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL"

                BayalpatraEmail.save(flush:true, failOnError: true)

            }
        }
        else{
            BayalpatraEmail.subject = " Action on Leave Application " + emp.fullName

            BayalpatraEmail.messageBody = "Dear " + emp.firstName + ",<br><br> Your leave application has been <b>"+ employeeLeaveDetailInstance.status+ """</b>&nbsp;<br/>
            <b>Leave Details</b>
            <br/><ul><li>From : """+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+"""</li>
                    <li>To : """+DateUtils.dateToString(employeeLeaveDetailInstance.toDate)+"""</li>

                    <li>Leave Type : """+employeeLeaveDetailInstance.leaveType+"""</li>"""+
                    leaveReason+"""</ul>
            <br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL"""
            BayalpatraEmail.save(flush:true, failOnError: true)
        }

//        if(emp.supervisor){
//            BayalpatraEmail supEmail = new BayalpatraEmail()
//            supEmail.toAddress = emp.supervisor?.employee?.email
//            supEmail.subject = " Action on Leave Application " + emp.fullName
//            supEmail.messageBody = "Dear " + employeeService.getSalutation(emp.supervisor?.employee?.gender) + """,
//                <br><br> Leave application for """+emp+""" has been <b>"""+ employeeLeaveDetailInstance.status+ """</b>&nbsp;<br/>
//                Leave Details
//                <br/><ul><li>From : """+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+"""</li>
//                        <li>To : """+DateUtils.dateToString(employeeLeaveDetailInstance.toDate)+"""</li>
//
//                        <li>Leave Type : """+employeeLeaveDetailInstance.leaveType+"""</li>"""+
//                    leaveReason+"""</ul>
//                For further information, please visit.<br/><br/>
//                http://annapurna.deerwalk.com/login/auth<br><br>
//                Thank You.<br>
//                Annapurna Support."""
//            supEmail.save(flush:true, failOnError: true)
//        }
        println BayalpatraEmail.errors
        if(BayalpatraConstants.CLIENT_NAME.equals(BayalpatraConstants.CLIENT_DEERWALK)){
            redirect(action:'approvalList')
        }else{
            redirect(action:'list')
        }

    }


    def cancelLeave = {
        def employeeLeaveDetailInstance = EmployeeLeaveDetail.get(params.id)
        employeeLeaveDetailInstance.status = BayalpatraConstants.LEAVE_CANCELLED
        leaveService.updateLeaveBalanceReportOfEachEmployeeAfterLeaveCancelled(employeeLeaveDetailInstance)

        //create a email object and save to send the email notification
        def employeeId = employeeLeaveDetailInstance.employee.id
        Employee emp = Employee.findById(employeeId)
        BayalpatraEmail BayalpatraEmail = new BayalpatraEmail()
        BayalpatraEmail.toAddress = emp.email
        BayalpatraEmail.ccAddress = BayalpatraConstants.ADMIN_CONTACT_PERSON
        BayalpatraEmail.subject = " Action on Leave Application " + emp.fullName
        def leaveReason=''
        leaveReason="""<li>Leave Reason : """
        if(employeeLeaveDetailInstance.leaveReason) leaveReason=leaveReason+employeeLeaveDetailInstance.leaveReason
        leaveReason=leaveReason+"""</li>"""
        BayalpatraEmail.messageBody = "Dear " + emp.firstName + ",<br><br> Your leave application has been <b>"+ employeeLeaveDetailInstance.status+ """</b>&nbsp;
            Leave Details<br/><ul><li>From : """+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+"""</li>
            <li>To : """+DateUtils.dateToString(employeeLeaveDetailInstance.toDate)+"""</li>
            <li>Leave Type : """+employeeLeaveDetailInstance.leaveType+"""</li>
            """+leaveReason+"""</ul><br/><br/>
            Please check your profile in Bayalpatra.<br/><br/>
            Thank You.<br>
            Bayalpatra Support."""
        BayalpatraEmail.save(flush:true, failOnError: true)


        //email for depthead
        BayalpatraEmail aEmail = new BayalpatraEmail()
        aEmail.toAddress = emp.supervisor.employee.email
        aEmail.subject = " Action on Leave Application " + emp.fullName
        aEmail.messageBody = "Dear " + employeeService.getSalutation(emp.supervisor.employee.gender) + """,<br><br>
                Leave application for """+emp+""" has been <b>"""+ employeeLeaveDetailInstance.status+ """</b>&nbsp;
                Leave Details<br/><ul><li>From : """+DateUtils.dateToString(employeeLeaveDetailInstance.fromDate)+"""</li>
                <li>To : """+DateUtils.dateToString(employeeLeaveDetailInstance.toDate)+"""</li>
                <li>Leave Type : """+employeeLeaveDetailInstance.leaveType+"""</li>
                """+leaveReason+"""</ul><br/><br/>

                Thank You.<br>
                Bayalpatra Support."""
        aEmail.save(flush:true, failOnError: true)


        println BayalpatraEmail.errors
        redirect(action:'list')
    }



    def update = {

        def employeeLeaveDetailInstance = EmployeeLeaveDetail.get(params.id)
        def leaveType = leaveTypeService.getLeaveType()


        def daysDifference = DateUtils.getDaysFromTwoDates(params.fromDate,params.toDate)

        daysDifference = daysDifference - Double.valueOf(params.leaveDays)

        employeeLeaveDetailInstance.properties = params

        employeeLeaveDetailInstance.setLeaveDifference(daysDifference)

        def chkLeave

        if(employeeLeaveDetailInstance.employee){
            chkLeave=employeeLeaveService.validateLeaveRangeEdit(employeeLeaveDetailInstance.id,employeeLeaveDetailInstance.fromDate,employeeLeaveDetailInstance.toDate,employeeLeaveDetailInstance.employee)
        }


        if(chkLeave==0){
            flash.message="Attempted to add invalid leave. Specified leave date seems to be the date before the join date of Employee."
            render(view: "edit", model: [employeeLeaveDetailInstance: employeeLeaveDetailInstance,leaveType: leaveType,leaveDay:employeeLeaveDetailInstance?.leaveDays] )
        }else if(chkLeave==1){
            flash.message="Attempted to add invalid leave. Specified leave date overlaps the existing leave date of the Employee."
            render(view: "edit", model: [employeeLeaveDetailInstance: employeeLeaveDetailInstance,leaveType: leaveType,leaveDay:employeeLeaveDetailInstance?.leaveDays] )
        }else{
            if (!employeeLeaveDetailInstance.hasErrors() && employeeLeaveDetailInstance.save(flush: true)) {

//        if(BayalpatraConstants.CLIENT_NAME.equals(BayalpatraConstants.CLIENT_DEERWALK)){
//          leaveService.updateLeaveBalanceReportOfEachEmployeeAfterLeaveApproved(employeeLeaveDetailInstance)
//        }

                redirect(action: "list",params:[offset:params.offset])
            }
            else {
                render(view: "edit", model: [employeeLeaveDetailInstance: employeeLeaveDetailInstance,leaveType: leaveType,leaveDay:employeeLeaveDetailInstance?.leaveDays] )
            }
        }

    }

    def delete = {
        def employeeLeaveDetailInstance = EmployeeLeaveDetail.get(params.id)
        if (employeeLeaveDetailInstance) {
            try {
                employeeLeaveDetailInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'employeeLeaveDetail.label', default: 'EmployeeLeaveDetail'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'employeeLeaveDetail.label', default: 'EmployeeLeaveDetail'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeeLeaveDetail.label', default: 'EmployeeLeaveDetail'), params.id])}"
            redirect(action: "list")
        }
    }

    def dailyLeaveDetail = {

        params.max = Math.min(params.max ? params.int('max') :30, 100)
        def offset = request.getParameter("offset") ?:'0';
        params.offset = offset
        def date = Date.parse("yyyy-MM-dd",DateUtils.dateToString(new Date()))
        def leaveDetail = employeeLeaveService.getDailyLeaveDetail(date)
        def count = employeeLeaveService.getCountDailyLeaveDetail(date)
        String defaultDate = commons.DateUtils.getCurrentDate().format("yyyy-MM-dd")
        render(view: "leaveDetail", model: [employeeLeaveDetailInstance:leaveDetail,defaultDate:defaultDate,employeeLeaveDetailInstanceTotal:count,offset:params.offset] )

    }
    def filterLeaveList = {
        def date
        if(params.date){
            date = DateUtils.stringToDate(params.date)
        }  else {

            def currentDate =Date.parse("yyyy-MM-dd",DateUtils.dateToString(new Date()))
            date = currentDate

        }
        def leaveDetail = employeeLeaveService.getDailyLeaveDetail(date)
        render(template:"ajavLeaveDetailList",model: [employeeLeaveDetailInstance:leaveDetail] )
    }


}
