package com.bayalpatra.hrm

import com.bayalpatra.commons.BayalpatraEmail
import commons.BayalpatraConstants
import commons.DateUtils
import grails.plugin.springsecurity.authentication.dao.NullSaltSource
import grails.util.Holders
import org.apache.log4j.Logger

class EmployeeController extends grails.plugin.springsecurity.ui.UserController{

//get the logger instance
    private static final Logger LOGGER = Logger.getLogger(EmployeeController)
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


    DepartmentService departmentService
    RoleService roleService
    EmployeeService employeeService
    SupervisorService supervisorService

/*    SalaryService salaryService
    EmployeeService employeeService
    EmployeeHistoryService employeeHistoryService

    DutyRosterReportService abc
    def leaveService*/

    def springSecurityService
    def exportService
    def mailService
   // def grailsApplication

    def dataSource

    def index = {
        redirect(action: "list", params: params)
    }


    def  list = {

        def user = User.findById(springSecurityService.principal.id)
        def role = roleService.getRole()
        def startDate
        def endDate
        def finalDept
        def employeeInstanceList
        departmentService.departmentTree = ""
        def deptTree = departmentService.generateNavigation(0)
        def count
        def max = 30
        def offset
        def employeeList

        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        if(params.offset){
            offset=params.offset
        }else{
            offset=0
        }

        def sortingParam = request.getParameter("sort") ?: 'firstName';
        def sortingOrder = request.getParameter("order") ?: 'asc';
        //Note for developer: params argument passed for the function call is only for offset.
        // In future, use request.getParameter to capture offset so that the function will only take 7 arguments as compared to eight now.

        if(role[0].toString()==BayalpatraConstants.ROLE_ADMIN){
            println 'inside where params'+params
            if (params.emp){
                def ar=ajxReq()
                employeeInstanceList=ar.employeeInstanceList
                count = ar.employeeInstanceTotal
            }else {
                if(params.startDate){
                    startDate = DateUtils.stringToDate(params.startDate)
                }
                if(params.endDate){
                    endDate = DateUtils.stringToDate(params.endDate)
                }

                if(params.department){
                    finalDept = departmentService.getDepartmentList(Long.parseLong(params.department))
                }

                employeeInstanceList = employeeService.getEmpForFilter(finalDept,startDate,endDate,params,max,offset,sortingParam,sortingOrder)
                count = employeeService.getEmpCountForFilter(finalDept,startDate,endDate)
            }



            if(params?.exportFormat && params.exportFormat != "html"){
                employeeList=employeeService.getEmpForFilter(finalDept,startDate,endDate,params,0,0,'firstName','asc')

                //validation. if(!params
//                response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                response.contentType = Holders.getConfig().config.grails.mime.types[params.exportFormat]
                response.setHeader("Content-disposition", "attachment; filename=Employee_List1.${params.extension}")

                List fields = [
                        "employeeId",
                        "salutation",
                        "name",
                        "designation",
                        "departments",
                        "unit",
                        "joinDate",
                        "supervisor",
                        "status",
                        "permanentAddress",
                        "email",
                        "dateOfBirth",
                        "maritalStatus",
                        "gender",
                        "mobile"
                ]
                Map labels = ["employeeId":"Employee Id","salutation":"Mr./Ms./Dr.","name":"Name","designation":"Designation", "departments":"Department","unit":"Unit" ,"joinDate":"Join Date", "supervisor": "Supervisor","status":"Status","permanentAddress":"Permanent Address","email":"E-mail","dateOfBirth":"Date Of Birth","maritalStatus":"Marital Status","gender":"Gender", "mobile" :"Cell#"]
                //Formatter Clouser
                def fDate = {domain, value ->
                    return value.format("yyyy-MM-dd")
                }
                Map formatter = [dateOfBirth:fDate,joinDate:fDate]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15

                ]]

                exportService.export(params.exportFormat, response.outputStream,employeeService.getValuesToExport(employeeList), fields, labels,formatter,parameters)
            }

        }

        else if(role[0].toString()==BayalpatraConstants.ROLE_DEPARTMENT_HEAD){

            finalDept = departmentService.getDepartmentList(session["department"])
            employeeInstanceList = employeeService.getEmpForFilter(finalDept,startDate,endDate,params,max,offset,sortingParam,sortingOrder)
            count = employeeService.getEmpCountForFilter(finalDept,startDate,endDate)

            if(params?.exportFormat && params.exportFormat != "html"){
                employeeList=employeeService.getEmpForFilter(finalDept,startDate,endDate,params,0,0,'firstName','asc')
                //validation. if(!params
               // response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                response.contentType = Holders.getConfig().config.grails.mime.types[params.exportFormat]
                response.setHeader("Content-disposition", "attachment; filename=Department_Head_List.${params.extension}")

                List fields = [
                        "employeeId",
                        "salutation",
                        "name",
                        "designation",
                        "departments",
                        "unit",
                        "joinDate",
                        "supervisor",
                        "status",
                        "permanentAddress",
                        "email",
                        "dateOfBirth",
                        "maritalStatus",
                        "gender",
                        "mobile"
                ]
                Map labels = ["employeeId":"Employee Id","salutation":"Mr./Ms./Dr.","name":"Name","designation":"Designation", "departments":"Department","unit":"Unit" ,"joinDate":"Join Date", "supervisor": "Supervisor","status":"Status","permanentAddress":"Permanent Address","email":"E-mail","dateOfBirth":"Date Of Birth","maritalStatus":"Marital Status","gender":"Gender", "mobile" :"Cell#"]
                //Formatter Clouser
                def fDate = {domain, value ->
                    return value.format("yyyy-MM-dd")
                }
                Map formatter = [dateOfBirth:fDate,joinDate:fDate]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15
                ]]


                exportService.export(params.exportFormat, response.outputStream,employeeService.getValuesToExport(employeeList), fields, labels,formatter,parameters)

            }



        }
        else if(role[0].toString()==BayalpatraConstants.ROLE_SUPERVISOR){

            def supervisor = Supervisor.findByEmployee(user?.employee)
            if(supervisor){
                employeeInstanceList = employeeService.getEmployeeBySupervisor(supervisor,user,params)
                count = employeeService.getCountBySupervisor(supervisor,user)
            }

            if(params?.exportFormat && params.exportFormat != "html"){
                employeeInstanceList = employeeService.getEmployeeBySupervisor(supervisor,user,null)
//        employeeInstanceList = Employee.findAllBySupervisorOrId(supervisor,user?.employee?.id)
                //validation. if(!params
             //   response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                response.contentType = Holders.getConfig().config.grails.mime.types[params.exportFormat]
                response.setHeader("Content-disposition", "attachment; filename=Supervisor_List.${params.extension}")

                List fields = [
                        "employeeId",
                        "salutation",
                        "name",
                        "designation",
                        "departments",
                        "unit",
                        "joinDate",
                        "supervisor",
                        "status",
                        "permanentAddress",
                        "email",
                        "dateOfBirth",
                        "maritalStatus",
                        "gender",
                        "mobile"
                ]
                Map labels = ["employeeId":"Employee Id","salutation":"Mr./Ms./Dr.","name":"Name","designation":"Designation", "departments":"Department","unit":"Unit" ,"joinDate":"Join Date", "supervisor": "Supervisor","status":"Status","permanentAddress":"Permanent Address","email":"E-mail","dateOfBirth":"Date Of Birth","maritalStatus":"Marital Status","gender":"Gender", "mobile" :"Cell#"]
                //Formatter Clouser
                def fDate = {domain, value ->
                    return value.format("yyyy-MM-dd")
                }
                Map formatter = [dateOfBirth:fDate,joinDate:fDate]

                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15
                ]]

                exportService.export(params.exportFormat, response.outputStream,employeeService.getValuesToExport(employeeInstanceList), fields, labels,formatter,parameters)

            }
        }

        [employeeInstanceList: employeeInstanceList, employeeInstanceTotal:count,deptTree: deptTree,department:params.department, startDate:params.startDate, endDate:params.endDate,offset:params.offset,employee: params.emp]
    }

    def ajxReq={
        def employeeInstanceList
        def max = 30
        def count
        def offset
        def user = User.findById(springSecurityService.principal.id)
        def role = roleService.getRole()
        // params.each {println(it)}
        // println(params.employee.toString())

        params.max = max//Math.min(params.max ? params.int('max') : 20, 100)
        if(params.offset){
            offset=params.offset
        }else{
            offset=0
        }

        if (role[0]==BayalpatraConstants.ROLE_ADMIN){
            if(params.emp){
                count = employeeService.getEmpListCount(params.emp.toString())
                if (params.format=='excel'){
                    max = count
                }
                employeeInstanceList = employeeService.getEmpList(params.emp.toString(),offset,max)

            }else{
                count = employeeService.getEmpCountForFilter(null,null,null)

                employeeInstanceList = employeeService.getEmpForFilter(null,null,null,params,max,offset,'firstName','asc')

            }
        }else if (role[0]==BayalpatraConstants.ROLE_DEPARTMENT_HEAD){
            def finalDept = departmentService.getDepartmentList(session["department"])
            if(params.emp){
                employeeInstanceList = employeeService.getEmpForSelective(params.emp.toString(),finalDept,null,null,null)
                count = employeeInstanceList.size()
            }else{
                employeeInstanceList = employeeService.getEmpForFilter(finalDept,null,null,params,max,offset,'firstName','asc')
                count = employeeService.getEmpCountForFilter(finalDept,null,null)
            }
        }else if (role[0]==BayalpatraConstants.ROLE_SUPERVISOR){
            def supervisor = Supervisor.findByEmployee(user?.employee)
            if(params.emp){
                employeeInstanceList = employeeService.getEmpForSelective(params.emp.toString(),null,supervisor,user,null)
                count = employeeInstanceList.size()
            }else{
                employeeInstanceList = employeeService.getEmployeeBySupervisor(supervisor,user,params)
                count = employeeService.getCountBySupervisor(supervisor,user)
            }
        }

        [employeeInstanceList: employeeInstanceList,employeeInstanceTotal:count,employee:params.emp,offset:params.offset]

    }


    def create = {
        def supervisorList
        def sortingParam = request.getParameter("sort") ?: 'employee.firstName';
        def sortingOrder = request.getParameter("order") ?: 'asc';

        def employeeInstance = new Employee()
        def userInstance = new User()
        employeeInstance.properties = params
        departmentService.departmentTree = ""
        def deptTree =  departmentService.generateNavigation(0)
        def employeeList = employeeService.getEmpByStatus()
        if(employeeList){
            supervisorList = supervisorService.getSupervisorList(employeeList,params,0,0,sortingParam,sortingOrder)
        }

        return [employeeInstance: employeeInstance,userInstance: userInstance,deptTree: deptTree,supervisorList:supervisorList]

    }

/*
    def ajaxEmployeeList = {

        def max = 30
        //    params.max = Math.min(params.max ? params.int('max') : 30, 100)
        params.sort = params.sort?:'firstName'
        params.order = params.order?:'asc'
        def employeeInstanceList
        def startDate
        def endDate
        def count
        def finalDept
        def offset

        if(params.offset){
            offset=params.offset
        }else{
            offset=0
        }

        def sortingParam = request.getParameter("sort") ?: 'firstName';
        def sortingOrder = request.getParameter("order") ?: 'asc';

        if(params.startDate){
            startDate = DateUtils.stringToDate(params.startDate)
        }

        if(params.endDate){
            endDate = DateUtils.stringToDate(params.endDate)
        }

        if(params.department){
            finalDept = departmentService.getDepartmentList(Long.parseLong(params.department))
        }
        count = employeeService.getEmpCountForFilter(finalDept,startDate,endDate)

        if (params.format=='excel'){
            max=count
        }

        employeeInstanceList = employeeService.getEmpForFilter(finalDept,startDate,endDate,params,max,offset,sortingParam,sortingOrder)

        if(params?.format && params.format != "html"){
            excelImport(employeeInstanceList)
        }
        */
/*if(params?.format && params.format != "html"){

            response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=Employee_List1.${params.extension}")

            List fields = [
                    "employeeId",
                    "firstName",
                    "lastName",
                    "middleName",
                    "designation",
                    "departments",
                    "nationality",
                    "dateOfBirth",
                    "maritalStatus",
                    "gender",
                    "country",
                    "permanentAddress",
                    "temporaryAddress",
                    "email",
                    "joinDate",
                    "status",
                    "volunteerDays",
                    "salaryclass",
                    "unit",
                    "supervisor",
                    "homePhone"
            ]
            Map labels = ["employeeId":"Employee Id", "firstName":"First Name", "lastName":"Last Name", "middleName":"Middle Name", "designation":"Designation", "departments":"Department","nationality":"Nationality","dateOfBirth":"Date Of Birth","maritalStatus":"Marital Status","gender":"Gender","country":"Country","permanentAddress":"Permanent Address","temporaryAddress":"Temporary Address","email":"E-mail","joinDate":"Join Date","status":"Status","volunteerDays":"Volunteer Days","salaryclass":"Salary Class","unit":"Unit" , "supervisor": "Supervisor", "homePhone" :"Home Phone"]
            //Formatter Closure
            def fDate = {domain, value ->
                return value.format("yyyy-MM-dd")
            }
            Map formatter = [dateOfBirth:fDate,joinDate:fDate]
            Map parameters =["column.widths": [
                    0.15,
                    0.15,
                    0.15 ,
                    0.15 ,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15
            ]]

            exportService.export(params.format, response.outputStream,employeeService.getEmpForFilter(finalDept,startDate,endDate,params,0,0,'firstName','asc'), fields, labels,formatter,parameters)
        }*//*

        if (employeeInstanceList)
        {
            render(template:'ajaxEmployeeList', model:[employeeInstanceList: employeeInstanceList,employeeInstanceTotal:count,department:params.department, startDate:params.startDate, endDate:params.endDate])
        }
        else    {
            render "noValue"
        }
    }


    def employeeReport = {

        def employee = Employee.findById(params.employeeIs);
        def empHistory = EmployeeHistory.findAllByEmployee(employee)

        if(params?.format && params.format != "html"){

            response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=Employee_History_list.${params.extension}")
            List fields = [
                    "fieldType",
                    "oldValue",
                    "fromDate",
                    "toDate"
            ]
            Map labels = ["fieldType":"Field Type","oldValue":"Old Value","fromDate":"From Date","toDate":"To Date" ]

            def fDate = {domain, value ->
                return value.format("yyyy-MM-dd")
            }
            Map formatter = [fromDate:fDate,toDate:fDate]
            Map parameters =["column.widths": [0.15, 0.15, 0.15, 0.15]]
            exportService.export(params.format, response.outputStream,empHistory, fields, labels,formatter,parameters)

        }

        render(view:"employeeReport",model:[empHistoryList:empHistory,employeeInstance: employee,empHistoryCount:empHistory.count()])
    }




    def save = {

        Boolean handleIsDocChecked
        def deptId

        departmentService.departmentTree = ""
        def deptTree =  departmentService.generateNavigation(0)
        def unitList
        def supervisorList
        def user = new User(params)

        def empId = employeeService.getEmployeeId()

        def imageFile = request.getFile('emp_image')

        def employeeInstance = new Employee(params)
        def sortingParam = request.getParameter("sort") ?: 'employee.firstName';
        def sortingOrder = request.getParameter("order") ?: 'asc';

        def department = Departments.findById(params.departments.id)
        def employeeList = employeeService.getEmpByStatus()
        def execDirectorEmail = new ArrayList<String>()


        if(employeeList){
            supervisorList = supervisorService.getSupervisorList(employeeList,params,0,0,sortingParam,sortingOrder)
        }

        if(department){
            deptId = department.idNumber
            unitList = departmentService.getUnitList(department)
        }

        if(!imageFile.empty) {
            employeeInstance.filename =  employeeService.getFileName(params.firstName, params.lastName, imageFile)
        }

        employeeInstance.employeeId = employeeService.createEmployeeId(deptId, empId)
        employeeInstance.updatedJoinDate = employeeInstance.joinDate

        if(params.password){
            String salt = saltSource instanceof NullSaltSource ? null : params.userName
            def password = springSecurityService.encodePassword(params.password, salt)

            user.password=password
        }
        user.enabled=true
        user.employee= employeeInstance

        if (params.isDoc == 'on'){
            handleIsDocChecked = true
        }

        def empSaved = employeeInstance.save(flush: true)

        if(empSaved){
            if(!user.save()){
                def oldEmployeeInstance = employeeInstance
                employeeInstance.delete()
                render(view: "create", model: [employeeInstance: oldEmployeeInstance,userInstance: user,deptTree : deptTree,supervisorList:supervisorList])
            }else{
                roleService.insertRole(user.id,Role.findByAuthority(BayalpatraConstants.ROLE_EMPLOYEE).id,'HR')
                employeeInstance.employeeId = employeeService.createEmployeeId(deptId, employeeInstance.id)
                flash.message="New employee has been created successfully."

                //get required to_addresses for concerned department HOD, concerned Unit_Incharge,admin department HOD, account department HOD and
                // executive director.

                def toAddressArray = new ArrayList<String>();

                toAddressArray = employeeService.getToAddresses(employeeInstance);           //gets all the associated department heads

                if (employeeInstance.unit){
                    def unitIncharge = employeeService.getUnitInchargeEmail(employeeInstance.unit)      //gets all the associated unit incharges

                    unitIncharge.eachWithIndex { val, i ->
                        toAddressArray.add(val)
                    }

                }

                if (Designation.findByJobTitleName(BayalpatraConstants.DESIGNATION_EXECUTIVE_DIRECTOR)){
                    execDirectorEmail = Employee.findAllByDesignation(Designation.findByJobTitleName(BayalpatraConstants.DESIGNATION_EXECUTIVE_DIRECTOR))

                    if (execDirectorEmail){
                        execDirectorEmail.eachWithIndex { vox, idx ->
                            toAddressArray.add(vox.email)
                        }
                    }
                }



                //create a email object and save to send the email notification to the Employee
                def deptHeads = employeeService.getToAddresses(employeeInstance);
                def deptHeadEmails
                def ccAdd=BayalpatraConstants.ADMIN_EMAIL+", "+BayalpatraConstants.ACCOUNT_EMAIL

                for (def i = 0; i < deptHeads.size(); i++){
                    deptHeadEmails = ", " + deptHeads[i].toString()

                }
                if(deptHeadEmails){
                    ccAdd=ccAdd + deptHeadEmails

                }
                if(empSaved.supervisor){
                    ccAdd=ccAdd +", "+ empSaved.supervisor.employee.email

                }
                BayalpatraEmail bayalpatraEmail = new BayalpatraEmail()
                def volunteerDays=empSaved.volunteerDays
                if(empSaved.volunteerDays==null || empSaved.volunteerDays==""){
                    volunteerDays=""
                }
                bayalpatraEmail.toAddress = empSaved.email
                bayalpatraEmail.ccAddress = ccAdd
                bayalpatraEmail.subject = "Notification of New Staff Appointment"
                bayalpatraEmail.messageBody = "Congratulations being appointed as "+empSaved.designation+" with effect from "+empSaved?.joinDate +"<br><br> Welcome to "+ employeeService.getCompanyName()+"/"+empSaved.departments +""" as one of the important member of this institution.<br>
                                                    <br>Username: """+params.username+"<br>Password: "+params.password+"""<br>
                                                    <br><b>Appointment Details:</b><br>
                                                    Name: """+employeeInstance?.firstName+" "+employeeInstance?.middleName+" "+employeeInstance?.lastName+"""<br>
                                                    Id  : """+empSaved.employeeId+"""<br>
                                                    Designation: """+empSaved.designation+" <br>Department: "+empSaved.departments
                if(empSaved.unit){bayalpatraEmail.messageBody+="<br>Unit: "+empSaved.unit}
                bayalpatraEmail.messageBody+="<br>Appointment Date: "+empSaved.joinDate+"<br>Volunteer Period: "+volunteerDays+"""<br> Salary: <br>Dependants:<br>


                                                    <br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL"""


                bayalpatraEmail.save(flush:true, failOnError: true)

                //create a leave balance for this user by user's join date
                if(BayalpatraConstants.CLIENT_NAME.equals("DW")){
                    leaveService.updateLeaveBalanceReportOfEachEmployeeAfterRegistration(empSaved,BayalpatraConstants.CREATE,0)
                }
                if(employeeInstance.status==BayalpatraConstants.SUSPENDED){
     */
/*               def suspendedDetail = new SuspendedEmployeeDetails()
                    suspendedDetail.employee=employeeInstance
                    suspendedDetail.startDate=new Date()
                    suspendedDetail.startDate.clearTime()
                    suspendedDetail.save(failOnError:true)*//*

                }
                redirect(action: "list", id: employeeInstance.id)
            }
        }else{

            render(view: "create", model: [employeeInstance: employeeInstance,userInstance: user,deptTree : deptTree,unitList:unitList,handleIsDocChecked:handleIsDocChecked,supervisorList:supervisorList])
        }

    }

    def show = {
        def employeeInstance = Employee.get(params.id)
        if (!employeeInstance) {
            redirect(action: "list")
        }
        else {
            [employeeInstance: employeeInstance]
        }
    }

    def edit = {
        def parentName
        boolean statusFlag

        departmentService.departmentTree = ""
        def deptTree =  departmentService.generateNavigation(0)
        def sortingParam = request.getParameter("sort") ?: 'employee.firstName';
        def sortingOrder = request.getParameter("order") ?: 'asc';

        def employeeInstance = Employee.get(params.employeeIs)
        def employeeList = employeeService.getEmpByStatus()
        def supervisorList = supervisorService.getSupervisorList(employeeList,params,0,0,sortingParam,sortingOrder)
        def unitList = departmentService.getUnitList(employeeInstance.departments)

//        def userName=User.findById(springSecurityService.getPrincipal()?.id)?.employee?.id

        if(params.statusFlag) {
            statusFlag = params.statusFlag
*/
/*      if(employeeInstance.getStatus()==BayalpatraConstants.TERMINATED ||employeeInstance.getStatus()==BayalpatraConstants.CLEARED ){
        statusFlag=false
      }*//*

        }

        if (!employeeInstance) {
            redirect(action: "list")
        }
        else {
            return [employeeInstance: employeeInstance,designationEdited:false,unitEdited:false,statusEdited:false,departmentEdited:false,deptTree : deptTree,offset:params.offset, statusFlag:statusFlag,supervisorList:supervisorList,unitList:unitList]
        }
    }

    def update = {

        def parentName
        def oldEmployeeInstance=Employee.get(params.id)
        def oldDepartment=oldEmployeeInstance?.departments
        def oldSupervisor=oldEmployeeInstance?.supervisor
        def role = roleService.getModuleWiseRole(User.findById(springSecurityService.principal.id),session['module'])
        departmentService.departmentTree = ""
        def deptTree =  departmentService.generateNavigation(0)
        def  joinDateUpdated= true
        def probationUpdated = false   // boolean set to check whether status changed from probation to permanent.If yes then call function to update leave balance
        def sortingParam = request.getParameter("sort") ?: 'employee.firstName';
        def sortingOrder = request.getParameter("order") ?: 'asc';

        def employeeList = employeeService.getEmpByStatus()
        def supervisorList = supervisorService.getSupervisorList(employeeList,params,0,0,sortingParam,sortingOrder)


        def imageFile = request.getFile('emp_image')
        def String fileName = '';
        if(!imageFile.empty) {
            fileName =  employeeService.getFileName(params.firstName, params.lastName, imageFile)
        }

        def employeeInstance = Employee.get(params.id)
        def deptHeads = employeeService.getToAddresses(employeeInstance);
        def deptHeadEmails
        def ccAdd=BayalpatraConstants.ADMIN_EMAIL+", "+BayalpatraConstants.ACCOUNT_EMAIL


        for (def i = 0; i < deptHeads.size(); i++){
            deptHeadEmails = ", " + deptHeads[i].toString()

        }
        if(deptHeadEmails){
            ccAdd=ccAdd + deptHeadEmails

        }
        if(employeeInstance.supervisor){
            ccAdd=ccAdd +", "+ employeeInstance.supervisor.employee.email

        }




        def employeeId = employeeInstance.employeeId
        def empId = employeeInstance.employeeId.substring(2,7)
        def depId = employeeInstance.employeeId.substring(0,2)
        def noOfProbationDays = 0
        def execDirectorEmail = new ArrayList<String>()
        def toAddressArray = new ArrayList<String>();

        Integer gradeInDb = employeeInstance.gradeReward.toInteger()
        Boolean gradeEdited = false;

        //checks if grade rewarded.
        if (params.gradeReward){
            if (Integer.parseInt(params.gradeReward) != gradeInDb){
                gradeEdited = true
            }
        }

        if(employeeInstance.departments.parentId==1){
            parentName = Department.findById(employeeInstance.department.id)
        }
        else if(employeeInstance.departments.parentId!=1){
            parentName = Department.findById(employeeInstance.department.parentId)
        }
        if (employeeInstance) {

            def updatedJoinDate = employeeInstance.updatedJoinDate
            Employee employee = Employee.get(params.id)
            if(role!=BayalpatraConstants.ROLE_EMPLOYEE){
                if(params.joinDate){
                    if(employee.joinDate.compareTo(params.joinDate)!=0){
                        if(employee.joinDate.compareTo(employee.updatedJoinDate)==0 && employee.joinDate.compareTo(employee.promotionDate)==0){
                            updatedJoinDate = params.joinDate
                        }else if(employee.joinDate.compareTo(employee.updatedJoinDate)==0 && employee.joinDate.compareTo(employee.promotionDate)!=0){
                            updatedJoinDate = params.joinDate
                        }else if(employee.joinDate.compareTo(employee.updatedJoinDate)!=0){


                            joinDateUpdated = false
                        }
                    }
                }
            }
            def fromDate = employeeInstance.joinDate
            if(params.designationEdited=='true' || params.unitEdited=='true' || params.statusEdited=='true' || params.departmentEdited=='true'){
                def toDate = DateUtils.getCurrentDate()
                if(params.designationEdited=='true'){
                    EmployeeHistory employeeHistoryList = EmployeeHistory.findByFieldTypeAndEmployee(BayalpatraConstants.FIELD_DESIGNATION,employee)
                    if(employeeHistoryList){
                        fromDate = employeeHistoryService.getFromDate(BayalpatraConstants.FIELD_DESIGNATION,employee)
                    }
                    toDate = params.promotionDate
                    employeeHistoryService.createEmployeeHistory(employee,employee.designation,BayalpatraConstants.FIELD_DESIGNATION,fromDate,toDate)
                }
                if(params.unitEdited=='true'){
                    EmployeeHistory employeeHistoryList = EmployeeHistory.findByFieldTypeAndEmployee(BayalpatraConstants.FIELD_UNIT,employee)
                    if(employeeHistoryList){
                        fromDate = employeeHistoryService.getFromDate(BayalpatraConstants.FIELD_UNIT,employee)
                    }
                    employeeHistoryService.createEmployeeHistory(employee,employee.unit,BayalpatraConstants.FIELD_UNIT,fromDate,toDate)
                }
                if(params.statusEdited=='true'){

                }

                if(params.departmentEdited=='true'){

                }
            }

            if (params.version) {
                def version = params.version.toLong()
                if (employeeInstance.version > version) {

                    employeeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
                            message(code: 'employee.label', default: 'Employee')]
                            as Object[], "Another user has updated this Employee while you were editing")
                    render(view: "edit", model: [employeeInstance: employeeInstance,statusFlag:true,supervisorList:supervisorList])
                    return
                }
            }

            if (fileName!='') employeeInstance.filename = fileName;

            if(!joinDateUpdated){
                flash.message="Cannot update Join Date."
                render(view: "edit", model: [employeeInstance: employeeInstance,parentName: parentName,designationEdited:false,unitEdited:false,statusEdited:false,departmentEdited:false,deptTree: deptTree,statusFlag:true,supervisorList:supervisorList])
            }else{
                employeeInstance.properties = params
                employeeInstance.updatedJoinDate = updatedJoinDate
                employeeInstance.employeeId = employeeId
                */
/*  def unitList = departmentService.getUnitList(employeeInstance.departments)*//*


                if(probationUpdated){
                    noOfProbationDays = employeeInstance.volunteerDays
                    leaveService.updateLeaveBalanceReportOfEachEmployeeAfterRegistration(employeeInstance,BayalpatraConstants.EDIT,noOfProbationDays)
                }

                if(params.volunteerDays){
                    if(employeeInstance.status == BayalpatraConstants.VOLUNTEER || BayalpatraConstants.PROBATION ){
                        employeeInstance.volunteerDays=Integer.valueOf(params.volunteerDays);
                    }
                }

                employeeInstance.effectiveDate=DateUtils.stringToDate(params.dateEffective)
                def suspensionDetail
                def suspendedDetail


                if (employeeInstance.statusChangedTo){
                    employeeInstance.updatedBy=User.findById(springSecurityService.getPrincipal().id).employee
                    if (employeeInstance.statusChangedTo!='Permanent' && employeeInstance.statusChangedTo!='Suspended' && employeeInstance.statusChangedTo!='Terminated'){
                        //history of service type
                        def toDate = DateUtils.getCurrentDate()
                        if(employee.status==BayalpatraConstants.SUSPENDED){
                            def noOfDaysSuspended = DateUtils.getDaysFromTwoDates(fromDate,toDate)
                            updatedJoinDate = employee.updatedJoinDate.plus(noOfDaysSuspended)
                            employeeInstance.updatedJoinDate=updatedJoinDate
                            def unClosedSuspension = SuspendedEmployeeDetails.findByEmployee(employeeInstance)
                            if(unClosedSuspension){
                                suspensionDetail = unClosedSuspension
                                suspensionDetail.endDate=new Date()
                                suspensionDetail.endDate.clearTime()
                                */
/* suspensionDetail.save(failOnError: true)*//*

                            }
                        }
                        EmployeeHistory employeeHistoryList = EmployeeHistory.findByFieldTypeAndEmployee(BayalpatraConstants.FIELD_SERVICE_TYPE,employee)
                        if(employeeHistoryList){
                            fromDate = employeeHistoryService.getFromDate(BayalpatraConstants.FIELD_SERVICE_TYPE,employee)
                        }
                        employeeHistoryService.createEmployeeHistory(employee,employee.status,BayalpatraConstants.FIELD_SERVICE_TYPE,fromDate,toDate)

                        employeeInstance.status=params.statusChangedTo
                        employeeInstance.statusChangedTo=""
                        employeeInstance.effectiveDate=null

                    }
                    else{
                        def toDate=DateUtils.getCurrentDate()
                        def todayDate=DateUtils.formatDateToYYYYMMDD(toDate)
                        def effectiveDate=DateUtils.formatDateToYYYYMMDD(employeeInstance.effectiveDate)

                        if (todayDate==effectiveDate){
                            if (employee.statusChangedTo==BayalpatraConstants.SUSPENDED)
                            {
*/
/*                                suspendedDetail = new SuspendedEmployeeDetails()
                                suspendedDetail.employee=employeeInstance
                                suspendedDetail.startDate=new Date()
                                suspendedDetail.startDate.clearTime()*//*


                                */
/*suspendedDetail.save(failOnError:true)*//*

                            }
                            else{
                                if (employee.statusChangedTo==BayalpatraConstants.TERMINATED){
                                    employee.terminatedDate=employee.effectiveDate
                                }
                                employeeInstance.effectiveDate=null
                            }
                            if(employee.status==BayalpatraConstants.SUSPENDED){
                                def noOfDaysSuspended = DateUtils.getDaysFromTwoDates(fromDate,toDate)
                                updatedJoinDate = employee.updatedJoinDate.plus(noOfDaysSuspended)
                                employeeInstance.updatedJoinDate=updatedJoinDate
                                def unClosedSuspension = SuspendedEmployeeDetails.findByEmployee(employeeInstance)
                                if(unClosedSuspension){
                                    suspensionDetail = unClosedSuspension
                                    suspensionDetail.endDate=new Date()
                                    suspensionDetail.endDate.clearTime()
                                    */
/* suspensionDetail.save(failOnError: true)*//*

                                }
                            }

                            //maintain history
                            EmployeeHistory employeeHistoryList = EmployeeHistory.findByFieldTypeAndEmployee(BayalpatraConstants.FIELD_SERVICE_TYPE,employee)
                            if(employeeHistoryList){
                                fromDate = employeeHistoryService.getFromDate(BayalpatraConstants.FIELD_SERVICE_TYPE,employee)
                            }
                            employeeHistoryService.createEmployeeHistory(employee,employee.status,BayalpatraConstants.FIELD_SERVICE_TYPE,fromDate,toDate)

                            employeeInstance.status=params.statusChangedTo
                            employeeInstance.statusChangedTo=""
                        }
                    }

                }

                if(params.change_department){
                    employeeInstance.updatedDepartmentBy=User.findById(springSecurityService.getPrincipal().id).employee
                    def todayDate=DateUtils.formatDateToYYYYMMDD(DateUtils.getCurrentDate())
                    def department = Departments.findByName(params.change_department)


                    def effectiveDateDept=DateUtils.stringToDate(params.dateForDepartment)

                    employeeInstance.effectiveDateForDepartment=DateUtils.stringToDate(params.dateForDepartment)
                    employeeInstance.changeDepartment=department

                    if (todayDate==effectiveDateDept){
                        depId = department.idNumber
                        employeeId = employeeService.updateEmployeeId(depId,empId)
                        employeeInstance.employeeId=employeeId

                        EmployeeHistory employeeHistoryList = EmployeeHistory.findByFieldTypeAndEmployee(BayalpatraConstants.FIELD_DEPARTMENT,employee)
                        if(employeeHistoryList){
                            fromDate = employeeHistoryService.getFromDate(BayalpatraConstants.FIELD_DEPARTMENT,employee)
                        }
                        employeeHistoryService.createEmployeeHistory(employee,employee.departments.name,BayalpatraConstants.FIELD_DEPARTMENT,fromDate,todayDate)

                        employeeInstance.departments=department
                        employeeInstance.changeDepartment=null
                        employeeInstance.effectiveDateForDepartment=null
                    }
                    else{
                        employeeInstance.effectiveDateForDepartment=DateUtils.stringToDate(params.dateForDepartment)
                        employeeInstance.changeDepartment=department
                    }
                }
                if (!employeeInstance.hasErrors() && employeeInstance.save(flush: true)) {
                    if (suspensionDetail)
                    {
                        suspensionDetail.save(failOnError: true)
                    }
                    else if (suspendedDetail)
                    {
                        suspendedDetail.save(failOnError: true)
                    }
                    def status = employeeInstance.status
                    // calculates salary for terminated and disables user priviledge for both terminated and cleared employees
                    if(status.equals(BayalpatraConstants.TERMINATED) || status.equals(BayalpatraConstants.CLEARED)){
                        if(status.equals(BayalpatraConstants.TERMINATED)){
                            def salClass = employeeInstance.salaryclass
                            salaryService.generateSalaryForTermedEmployees(employeeInstance,salClass)
                            employeeService.updateSupervisor(employeeInstance)
                        }
                        User.executeUpdate("UPDATE User u SET u.enabled=0 where u.employee="+employeeInstance.id);
                    }

                    //send email based on several scenarios

                    //     <<-------------------------------------Send email when promotion of employee------------------------------->>

                    if(BayalpatraConstants.CLIENT_NAME.equals("KMH")){

                        if (params.designationEdited=='true'){

                            //get required to_addresses for concerned department HOD, concerned Unit_Incharge,admin department HOD, account department HOD and
                            // executive director.

                            toAddressArray = employeeService.getToAddresses(employeeInstance);           //gets all the associated department heads

                            if (employeeInstance.unit){
                                def unitIncharge = employeeService.getUnitInchargeEmail(employeeInstance.unit)      //gets all the associated unit incharges

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

                            //create a email object and save to send the email notification to the Employee on promotion
                            BayalpatraEmail bayalpatraEmail = new BayalpatraEmail()
                            bayalpatraEmail.toAddress = employeeInstance.email

                            def newDeptHeads = employeeService.getToAddresses(employeeInstance);
                            def newDeptHeadEmails

                            for (def i = 0; i < newDeptHeads.size(); i++){
                                newDeptHeadEmails = ", " + newDeptHeads[i].toString()


                            }
                            if(newDeptHeadEmails && newDeptHeadEmails!=deptHeadEmails){
                                ccAdd=ccAdd + newDeptHeadEmails


                            }
                            if(employeeInstance?.supervisor && employeeInstance?.supervisor!=oldSupervisor){
                                ccAdd=ccAdd +", "+ employeeInstance.supervisor.employee.email

                            }
                            bayalpatraEmail.ccAddress=ccAdd

                            bayalpatraEmail.subject = "Notification of Promotion of " +employeeInstance?.firstName+" "+employeeInstance?.middleName+" "+employeeInstance?.lastName
                            bayalpatraEmail.messageBody = "It is our pleasure to inform you that you have been promoted to the post of "+Designation.findById(Long.parseLong(params.designation.id)).jobTitleName +" effective from<br>"+employeeInstance.promotionDate+".<br><br><b>Promotion Details:</b><br>Name: "+employeeInstance?.firstName+" "+employeeInstance?.middleName+" "+employeeInstance?.lastName+"<br>ID: "+employeeInstance?.employeeId+"<br>Designation: "+employeeInstance?.designation+"<br>Department: "+employeeInstance?.departments+"<br>Promoted to: "+Designation.findById(Long.parseLong(params.designation.id)).jobTitleName

                            if (employeeInstance?.supervisor!=oldSupervisor){
                                bayalpatraEmail.messageBody+="<br>New Supervisor: "+employeeInstance?.supervisor
                            }

                            bayalpatraEmail.messageBody+="<br>Staff Status: "+employeeInstance?.status+"<br>Allowance: <br><br>Your efforts in the previous department have always been commendable and we look forward seeing more<br>excellent performance from you in new place. Good luck and hope  you will do much better.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL"

                            bayalpatraEmail.save(flush:true, failOnError: true)
                        }

                        //     <<-------------------------------------Send email when grade is rewarded------------------------------->>

                        if (gradeEdited){
                            //get required to_addresses for concerned department HOD, concerned Unit_Incharge,admin department HOD, account department HOD and
                            // executive director.

                            toAddressArray = employeeService.getToAddresses(employeeInstance);           //gets all the associated department heads

                            if (employeeInstance.unit){
                                def unitIncharge = employeeService.getUnitInchargeEmail(employeeInstance.unit)      //gets all the associated unit incharges

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
                            BayalpatraEmail bayalpatraEmail = new BayalpatraEmail()
                            bayalpatraEmail.toAddress = employeeInstance.email
                            bayalpatraEmail.ccAddress=ccAdd
                            bayalpatraEmail.subject = "Grade Reward for " + employeeInstance.fullName

                            bayalpatraEmail.messageBody = "Dear "+ employeeInstance.fullName +",<br><br> A Grade of "+ Integer.parseInt(params.gradeReward) +" has been awarded to you.<br> Congratulations!<br><br>Please check your profile.<br><br> Thank You!<br>Annapurna Support."


                            bayalpatraEmail.save(flush:true, failOnError: true)
                        }

                        //     <<---------------------Send email when department is shifted/transfer from program or branch-------------------->>

                        if (params.departmentEdited == 'true'){

                            //get required to_addresses for concerned department HOD, concerned Unit_Incharge,admin department HOD, account department HOD and
                            // executive director.

                            toAddressArray = employeeService.getToAddresses(employeeInstance);           //gets all the associated department heads

                            if (employeeInstance.unit){
                                def unitIncharge = employeeService.getUnitInchargeEmail(employeeInstance.unit)      //gets all the associated unit incharges

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

//                            toAddressArray.add('a@b.com')
                            BayalpatraEmail bayalpatraEmail = new BayalpatraEmail()
                            bayalpatraEmail.toAddress = employeeInstance.email
                            def newDeptHeads = employeeService.getToAddresses(employeeInstance);
                            def newDeptHeadEmails


                            for (def i = 0; i < newDeptHeads.size(); i++){
                                newDeptHeadEmails = ", " + newDeptHeads[i].toString()

                            }
                            if(newDeptHeadEmails && newDeptHeadEmails!=deptHeadEmails){
                                ccAdd=ccAdd + newDeptHeadEmails

                            }
                            if(employeeInstance?.supervisor && employeeInstance?.supervisor!=oldSupervisor){
                                ccAdd=ccAdd +", "+ employeeInstance.supervisor.employee.email

                            }
                            bayalpatraEmail.ccAddress=ccAdd
                            bayalpatraEmail.subject = "Notification of Transfer of " +employeeInstance?.firstName+" "+employeeInstance?.middleName+" "+employeeInstance?.lastName
                            bayalpatraEmail.messageBody =" We would like to inform you that you have been transferred from "+oldDepartment+" Department to "+employeeInstance.departments+" effective from "+new Date()+"<br><br><b>Transfer Details:</b><br>Name: "+employeeInstance?.firstName+" "+employeeInstance?.middleName+" "+employeeInstance?.lastName+"<br>ID: "+employeeInstance?.employeeId+"<br>Designation: "+employeeInstance?.designation+"<br>Department: "+oldDepartment+"<br>Transferred to: "+employeeInstance?.departments
                            if (employeeInstance?.supervisor!=oldSupervisor){
                                bayalpatraEmail.messageBody+="<br>New Supervisor: "+employeeInstance?.supervisor
                            }
                            bayalpatraEmail.messageBody+="<br>Staff Status: "+employeeInstance?.status+"<br>Allowance: <br><br> Your efforts in the previous department have always been commendable and we look forward seeing more excellent performance from you in new place. Good luck and hope  you will do much better.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL"
                            bayalpatraEmail.save(flush:true, failOnError: true)
                        }

                        //     <<---------------------Send email when employee is terminated-------------------->>
                        if (params.status==BayalpatraConstants.TERMINATED){
                            BayalpatraEmail bayalpatraEmail = new BayalpatraEmail()
                            bayalpatraEmail.toAddress = employeeInstance.email
                            bayalpatraEmail.ccAddress=ccAdd
                            bayalpatraEmail.subject = "Termination of " + employeeInstance?.firstName+" "+employeeInstance?.middleName+" "+employeeInstance?.lastName

                            bayalpatraEmail.messageBody = "Dear "+ employeeInstance?.firstName+" "+employeeInstance?.middleName+" "+employeeInstance?.lastName +",<br><br> You have been terminated today.<br>For further information, please visit Annapurna.<br> Thank You!<br> Annapurna Support."

                            bayalpatraEmail.save(flush:true, failOnError: true)
                        }

                        //     <<---------------------Send email when employee is suspended (disciplinary action)-------------------->>

                        if (params.status==BayalpatraConstants.SUSPENDED){

                            toAddressArray = employeeService.getToAddresses(employeeInstance);           //gets all the associated department heads

                            if (employeeInstance.unit){
                                def unitIncharge = employeeService.getUnitInchargeEmail(employeeInstance.unit)      //gets all the associated unit incharges

                                unitIncharge.eachWithIndex { val, i ->
                                    toAddressArray.add(val)
                                }
                            }

                            if (Designation.findByJobTitleName(BayalpatraConstants.DESIGNATION_EXECUTIVE_DIRECTOR)){
                                execDirectorEmail = Employee.findAllByDesignation(Designation.findByJobTitleName(BayalpatraConstants.DESIGNATION_EXECUTIVE_DIRECTOR))

                                if (execDirectorEmail){
                                    execDirectorEmail.eachWithIndex { vox, idx ->
                                        toAddressArray.add(vox.email)
                                    }
                                }
                            }



                            //send email to concerned person on his/her suspension.

                            //create a email object and save to send the email notification to the Employee on suspension
                            BayalpatraEmail bayalpatraEmail = new BayalpatraEmail()
                            bayalpatraEmail.toAddress = employeeInstance.email
                            bayalpatraEmail.ccAddress=ccAdd
                            bayalpatraEmail.subject = "Notification of Suspension of " + employeeInstance?.firstName+" "+employeeInstance?.middleName+" "+employeeInstance?.lastName
                            Date toDate = new Date().plus(employeeInstance?.suspensionDays)

                            bayalpatraEmail.messageBody ="As a result of your misconduct and breach of by law dated "+new Date()+" you are suspended from your<br>position in the department/unit for "+employeeInstance?.suspensionDays+" days effective from "+new Date()+" to "+ toDate +".<br><br><b>Suspension Details:</b><br>Name: "+employeeInstance?.firstName+" "+employeeInstance?.middleName+" "+employeeInstance?.lastName+"<br>ID: "+employeeInstance?.employeeId+"<br>Designation: "+employeeInstance?.designation+"<br>Department: "+employeeInstance?.departments
                            if(employeeInstance.unit){annapurnaEmail.messageBody+="<br>Unit: "+employeeInstance.unit}

                            bayalpatraEmail.messageBody+="<br>Suspend Period: "+employeeInstance?.suspensionDays+" days<br><br>You are hereby warned to refrain form such activities again in future. Otherwise, serious action shall be taken<br>against you as per regulation of the institution.<br><br><b>Note:</b> Submit all your belongings related to this institution to admin for the suspended period.<br><br>Thanking you,<br>The Manager<br>Human Resources<br>phect-NEPAL"
//                                "Dear "+ employeeInstance.fullName +",<br><br> You have been suspended today.<br>For further information, please visit Annapurna.<br> Thank You!<br> Annapurna Support."
//                        }


                            bayalpatraEmail.save(flush:true, failOnError: true)
                        }

                    }

                    //     <<-------------------------------------Send email EOF ------------------------------->>

                    */
/* def unClosedSuspension = SuspendedEmployeeDetails.findAllWhere(endDate: null,employee: employeeInstance)
                                        if(unClosedSuspension){
                                            def suspensionDetail = unClosedSuspension[0]
                                            suspensionDetail.endDate=new Date()
                                            suspensionDetail.save(failOnError: true)
                                        }else{
                                            def suspensionDetail = new SuspendedEmployeeDetails()
                                            suspensionDetail.startDate=new Date()
                                            suspensionDetail.employee=employeeInstance
                                            suspensionDetail.save(failOnError: true)
                                        }
                    *//*


                    if(role==BayalpatraConstants.ROLE_EMPLOYEE){
                        render(view: "edit", model: [employeeInstance: employeeInstance,parentName: parentName,designationEdited:false,unitEdited:false,statusEdited:false,departmentEdited:false,deptTree: deptTree,statusFlag:true,supervisorList:supervisorList])
                    }
                    else{
                        redirect(action: "list",params:[offset:params.offset])
                    }
                }
                else {
                    render(view: "edit", model: [employeeInstance: employeeInstance,parentName: parentName,designationEdited:false,unitEdited:false,statusEdited:false,departmentEdited:false,deptTree: deptTree,statusFlag:true,supervisorList:supervisorList])
                }
            }

        }
        else {
            redirect(action: "list")
        }
    }

    def delete = {
        def employeeInstance = Employee.get(params.id)
        if (employeeInstance) {
            try {
                employeeInstance.delete(flush: true)
                //flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                //flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
            redirect(action: "list")
        }
    }

    */
/* *
    * sends Employee object as params to checkBoundPeiod() method in EmployeeService,
    * checks, whether there is bound period or not in EmployeeService,
    * returns true if yes, false if no back to this method
    * finally this method sends text string to the calling js function in edit page
    *//*

    def checkBoundPeriod = {
        Employee employeeInstance = Employee.get(params.employee)
        def checkBoundPeriod = employeeService.checkBoundPeriod(employeeInstance)
        if(checkBoundPeriod == true){
            response.outputStream << "This user has bound period.".bytes
        }
        else{
        }
    }


    */
/**
     * Disables user who are terminated or cleared
     * @params employee
     *//*

    def calculateSalaryForTermed = {
        def emp=Employee.get(params.employee)
        def salClass=SalaryClass.get(params.salaryClass)
        salaryService.generateSalaryForTermedEmployees(emp,salClass)
    }


    def disableTermedAndClearedEmployee = {
        def emp = Employee.get(params.employee)
        User.executeUpdate("UPDATE User u SET u.enabled=0 where u.employee="+emp.id);
    }

    */
/**
     * Generates employeeList based on entered firstName in the search field of list.
     * @return employeeList
     *//*


    def ajxReq={
        def employeeInstanceList
        def max = 30
        def count
        def offset
        def user = User.findById(springSecurityService.principal.id)
        def role = roleService.getRole()
        // params.each {println(it)}
        // println(params.employee.toString())

        params.max = max//Math.min(params.max ? params.int('max') : 20, 100)
        if(params.offset){
            offset=params.offset
        }else{
            offset=0
        }

        if (role==BayalpatraConstants.ROLE_ADMIN){
            if(params.emp){
                count = employeeService.getEmpListCount(params.emp.toString())
                if (params.format=='excel'){
                    max = count
                }
                employeeInstanceList = employeeService.getEmpList(params.emp.toString(),offset,max)

            }else{
                count = employeeService.getEmpCountForFilter(null,null,null)

                employeeInstanceList = employeeService.getEmpForFilter(null,null,null,params,max,offset,'firstName','asc')

            }
        }else if (role==BayalpatraConstants.ROLE_DEPARTMENT_HEAD){
            def finalDept = departmentService.getDepartmentList(session["department"])
            if(params.emp){
                employeeInstanceList = employeeService.getEmpForSelective(params.emp.toString(),finalDept,null,null,null)
                count = employeeInstanceList.size()
            }else{
                employeeInstanceList = employeeService.getEmpForFilter(finalDept,null,null,params,max,offset,'firstName','asc')
                count = employeeService.getEmpCountForFilter(finalDept,null,null)
            }
        }else if (role==BayalpatraConstants.ROLE_SUPERVISOR){
            def supervisor = Supervisor.findByEmployee(user?.employee)
            if(params.emp){
                employeeInstanceList = employeeService.getEmpForSelective(params.emp.toString(),null,supervisor,user,null)
                count = employeeInstanceList.size()
            }else{
                employeeInstanceList = employeeService.getEmployeeBySupervisor(supervisor,user,params)
                count = employeeService.getCountBySupervisor(supervisor,user)
            }
        }*/
/*else if (role==BayalpatraConstants.ROLE_UNIT_INCHARGE){
            def unit = Unit.find(session["unit"])
            if(params.emp){
                employeeInstanceList = employeeService.getEmpForSelective(params.emp.toString(),null,null,null,unit)
                count = employeeInstanceList.size()
            }else{
                employeeInstanceList = employeeService.getEmpByUnit(unit,params,max,offset,'firstName','asc')
                count = employeeService.getCountByUnit(unit)
            }
        }*//*

        [employeeInstanceList: employeeInstanceList,employeeInstanceTotal:count,employee:params.emp,offset:params.offset]

    }

    def ajaxCall={
        def aR= ajxReq()
        if(params?.format && params.format != "html"){
            excelImport(aR.employeeInstanceList)
        }
        //params.frompagination?:params.emp

        if (aR.employeeInstanceList){

            render(template: "ajaxEmployeeList", model:[employeeInstanceList:aR. employeeInstanceList,employeeInstanceTotal:aR.employeeInstanceTotal,employee:params.emp,offset:aR.offset])
        }
        else{
            render "noValue"

        }


    }

//to import excel correctly
    def excelImport={ employeeList->



        //validation. if(!params
        response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
        response.setHeader("Content-disposition", "attachment; filename=Employee_List1.${params.extension}")

        List fields = [
                "employeeId",
                "salutation",
                "name",
                "designation",
                "departments",
                "unit",
                "joinDate",
                "supervisor",
                "status",
                "permanentAddress",
                "email",
                "dateOfBirth",
                "maritalStatus",
                "gender",
                "mobile"
        ]
        Map labels = ["employeeId":"Employee Id","salutation":"Mr./Ms./Dr.","name":"Name","designation":"Designation", "departments":"Department","unit":"Unit" ,"joinDate":"Join Date", "supervisor": "Supervisor","status":"Status","permanentAddress":"Permanent Address","email":"E-mail","dateOfBirth":"Date Of Birth","maritalStatus":"Marital Status","gender":"Gender", "mobile" :"Cell#"]
        //Formatter Clouser
        def fDate = {domain, value ->
            return value.format("yyyy-MM-dd")
        }
        Map formatter = [dateOfBirth:fDate,joinDate:fDate]
        Map parameters =["column.widths": [
                0.15,
                0.15,
                0.15 ,
                0.15 ,
                0.15,
                0.15,
                0.15,
                0.15,
                0.15,
                0.15,
                0.15,
                0.15,
                0.15,
                0.15,
                0.15

        ]]

        exportService.export(params.format, response.outputStream,employeeService.getValuesToExport(employeeList), fields, labels,formatter,parameters)


    }
    def termedEmployeeList = {

        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        params.sort = params.sort?:'firstName'
        params.order = params.order?:'asc'
        def termedEmpList
        def count
        def startDate
        def endDate
        def type=params.type
        def filter=false
        if(params.startDate){
            startDate = DateUtils.stringToDate(params.startDate)
        }
        if(params.endDate){
            endDate = DateUtils.stringToDate(params.endDate)
        }
        if (params.startDate||params.endDate||params.type){
            filter = true
        }
        if (!params.offset){
            session.startDate=""
            session.endDate=""
            session.type=""
            session.emp=""
        }

        termedEmpList = employeeService.getTerminatedEmpForFilter(type,startDate,endDate,params,params.max,params.offset,params.sort,params.order)
        count = employeeService.getTerminatedEmpCountForFilter(type,startDate,endDate)


        return [employeeInstanceList:termedEmpList,employeeInstanceTotal:count,startDate: params.startDate,endDate: params.endDate,type: type,filter:filter,sDate:startDate,eDate:endDate]
    }

    def exportToExcel={
        def startDate
        def endDate
        def type=session.type
        if(session.startDate){
            startDate = DateUtils.stringToDate(session.startDate)
        }
        if(session.endDate){
            endDate = DateUtils.stringToDate(session.endDate)
        }

        if(params?.format && params.format != "html"){

            response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=Termed_Employee_List.${params.extension}")
            List fields = [
                    "employeeId",
                    "fullName",
                    "designation",
                    "departments",
                    "joinDate",
                    "terminatedDate",
                    "supervisor",
                    "status",
                    "mobile"
            ]
            Map labels = ["employeeId":"Employee Id", "fullName":"Full Name", "designation":"Designation", "departments":"Departments","joinDate":"Join Date","terminatedDate":"Terminated Date","supervisor":"Supervisor","status":"Status","mobile":"Contact Number"]
            // Formatter Closure
            def fDate = {  domain , value ->
                return value.format("yyyy-MM-dd")

            }
            Map formatter = [joinDate:fDate]
            Map parameters =["column.widths": [
                    0.15,
                    0.15,
                    0.15 ,
                    0.15 ,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
            ]]
            def exportList = []
            if(session.emp){
                exportList = employeeService.getTerminatedEmp(session.emp.toString(),params)
            }else if (session.startDate || session.endDate){
                if(session.startDate){
                    startDate = DateUtils.stringToDate(session.startDate)
                }
                if(session.endDate){
                    endDate = DateUtils.stringToDate(session.endDate)
                }
                exportList = employeeService.getAllFilterList(type,startDate,endDate)

            }else{
                exportList = employeeService.getAllFilterList(type,null,null)
            }
            exportService.export(params.format, response.outputStream, exportList, fields, labels,formatter,parameters)
        }
    }

    def filterTerminatedList={
        def employeeInstanceList
        def count
        def max = 30
        def searchParams = ""
        if(params.emp) searchParams = "emp="+params.emp
        def offset
        session.emp=params.emp
        session.startDate=""
        session.endDate=""
        session.type=""


        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        if(params.offset){
            offset=params.offset
        }else{
            offset=0
        }
        if(params.emp){
            employeeInstanceList = employeeService.getTerminatedEmp(params.emp.toString(),params)
            count = employeeInstanceList.size()
        }
        else{

            employeeInstanceList = employeeService.getTerminatedEmpForFilter(null,null,null,params,params.max,params.offset,'firstName','asc')
            count = employeeService.getTerminatedEmpCountForFilter(null,null,null)
        }

        render(template: "terminatedEmployeeList", model:[employeeInstanceList: employeeInstanceList,employeeInstanceTotal:count,emp:params.emp,searchParams:searchParams])

    }

    def terminatedEmployeeList={
        def max = 30
        params.sort = params.sort?:'firstName'
        params.order = params.order?:'asc'
        def employeeInstanceList
        def startDate
        def endDate
        def searchParams = ""
        if(params.startDate) searchParams = "startDate="+params.startDate
        if(params.endDate) searchParams = searchParams + "&endDate="+params.endDate
        if(params.type) searchParams = searchParams + "&type="+params.type

        session.startDate=params.startDate
        session.endDate=params.endDate
        session.type=params.type
        session.emp=""

        def count
        def offset

        if(params.offset){
            offset=params.offset
        }else{
            offset=0
        }

        def type=params.type

        def sortingParam = request.getParameter("sort") ?: 'firstName';
        def sortingOrder = request.getParameter("order") ?: 'asc';

        if(params.startDate){
            startDate = DateUtils.stringToDate(params.startDate)
        }
        if(params.endDate){
            endDate = DateUtils.stringToDate(params.endDate)
        }



        employeeInstanceList = employeeService.getTerminatedEmpForFilter(type,startDate,endDate,params,max,offset,sortingParam,sortingOrder)
        count = employeeService.getTerminatedEmpCountForFilter(type,startDate,endDate)




        render(template:'terminatedEmployeeList', model:[employeeInstanceList: employeeInstanceList,employeeInstanceTotal:count,startDate:params.startDate,
                                                         endDate:params.endDate,type:type,offset: offset,searchParams:searchParams])
    }


    def probationList={
        if(params?.format && params.format != "html"){
            params.max = 0

            response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=Pobation_Employee_List.${params.extension}")
            List fields = [
                    "employeeId",
                    "firstName",
                    "middleName",
                    "lastName",
                    "departments",
                    "probationDays"
            ]
            Map labels = ["employeeId":"Employee Id", "firstName":"First Name", "middleName":"Middle Name", "lastName":"Last Name", "departments":"Departments","probationDays":"Days of Probation"]
            Map parameters =["column.widths": [
                    0.15,
                    0.15,
                    0.15 ,
                    0.15 ,
                    0.15,
                    0.15
            ]]
            exportService.export(params.format, response.outputStream,employeeService.getProbationCompletedEmployees(), fields, labels,[:],parameters)
        }

        params.max = Math.min(params.max ? params.int('max') : 30, 100)

        def employeeList = employeeService.getProbationCompletedEmployees()
        def count = employeeService.getCountOfProbationalEmployees()
        render(view:'probationList',model:[employeeInstanceList:employeeList,employeeInstanceTotal:count])
    }


*/


}
