package com.bayalpatra.hrm

import com.bayalpatra.commons.BayalpatraEmail
import commons.BayalpatraConstants

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EmployeeDependentsController {
    def exportService
    def springSecurityService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    EmployeeDependentsService employeeDependentsService
    EmployeeService employeeService
    RoleService roleService
    DepartmentService departmentService


    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        [employeeDependentsInstanceList: EmployeeDependents.list(params), employeeDependentsInstanceTotal: EmployeeDependents.count()]
    }

    def create = {

        def employee = Employee.findById(params.employeeIs);

        def employeeDependents = EmployeeDependents.findByEmployee(employee)

        def employeeDependentsInstance = new EmployeeDependents()
        employeeDependentsInstance.properties = params

        if(employeeDependents){
            render(view: 'edit', model:[employeeDependentsInstance: employeeDependents, employeeInstance: employee])
        }else{
            render (view: 'create', model:[employeeDependentsInstance: employeeDependentsInstance, employeeInstance: employee])    //just EmployeeDependents returns error
        }

    }

    def save = {

        def employeeDependentsInstance = new EmployeeDependents(params)


        if (employeeDependentsInstance.save(flush: true)) {
            redirect(action: "edit", id: employeeDependentsInstance.id)
        }
        else {
            render(view: "create", model: [employeeDependentsInstance: employeeDependentsInstance, employeeInstance: employeeDependentsInstance.employee])
        }
    }

    def show = {
        def employeeDependentsInstance = EmployeeDependents.get(params.id)
        if (!employeeDependentsInstance) {
            redirect(action: "list")
        }
        else {
            [employeeDependentsInstance: employeeDependentsInstance]
        }
    }

    def edit = {

        def employeeDependentsInstance = EmployeeDependents.get(params.id)
        def employee= employeeDependentsInstance.employee

        if (!employeeDependentsInstance) {
            redirect(action: "list")
        }
        else {
            return [employeeDependentsInstance: employeeDependentsInstance,employeeInstance: employee]
        }
    }

    def update = {

        def employeeDependentsInstance = EmployeeDependents.get(params.id)

        def messageBody=""
        def updateMsg=false

        if(employeeDependentsInstance?.fatherName!=params.fatherName ){
            updateMsg=true
            messageBody+="Father Name<br>"
        }

        if(employeeDependentsInstance?.fatherDob!=params.fatherDob ){
            updateMsg=true
            messageBody+="Father Year Of Birth<br>"
        }

        if(employeeDependentsInstance?.fatherAddress!=params.fatherAddress ){
            updateMsg=true
            messageBody+="Father Address<br>"
        }

        if(employeeDependentsInstance?.motherName!=params.motherName ){
            updateMsg=true
            messageBody+="Mother Name<br>"
        }

        if(employeeDependentsInstance?.motherDob!=params.motherDob ){
            updateMsg=true
            messageBody+="Mother Year Of Birth<br>"
        }

        if(employeeDependentsInstance?.spouseName!=params.spouseName ){
            updateMsg=true
            messageBody+="Spouse Name<br>"
        }

        if(employeeDependentsInstance?.spouseDob!=params.spouseDob ){
            updateMsg=true
            messageBody+="Spouse Year of Birth<br>"
        }

        if(employeeDependentsInstance?.spouseAddress!=params.spouseAddress ){
            updateMsg=true
            messageBody+="Spouse Address<br>"
        }

        if(employeeDependentsInstance?.childName1!=params.childName1 ){
            updateMsg=true
            messageBody+="Child 1 Name<br>"
        }

        if(employeeDependentsInstance?.child1Dob!=params.child1Dob ){
            updateMsg=true
            messageBody+="Child 1 Year Of Birth<br>"
        }

        if(employeeDependentsInstance?.childName2!=params.childName2 ){
            updateMsg=true
            messageBody+="Child 2 Name<br>"
        }

        if(employeeDependentsInstance?.child2Dob!=params.child2Dob ){
            updateMsg=true
            messageBody+="Child 2 Year of Birth<br>"
        }

        if (employeeDependentsInstance) {

            employeeDependentsInstance.properties = params
            if (!employeeDependentsInstance.hasErrors() && employeeDependentsInstance.save(flush: true)) {

                if (updateMsg){
                    BayalpatraEmail bpEmail = new BayalpatraEmail();
                    bpEmail.toAddress=BayalpatraConstants.ADMIN_EMAIL
                    bpEmail.subject="Notification On Update Of Employee Dependents"
                    bpEmail.messageBody ="The Dependents for "+employeeDependentsInstance?.employee+" has been updated.<br><b>Updated Fields:</b><br>"+messageBody+"<br>Thank You,<br>Annapurna Support"
                    bpEmail.save(failOnError: true)

                }


                redirect(action: "edit", id: employeeDependentsInstance.id)
            }
            else {
                render(view: "edit", model: [employeeDependentsInstance: employeeDependentsInstance,employeeInstance: employeeDependentsInstance.employee])
            }
        }
        else {
            redirect(action: "list")
        }
    }

    def delete = {
        def employeeDependentsInstance = EmployeeDependents.get(params.id)
        if (employeeDependentsInstance) {
            try {
                employeeDependentsInstance.delete(flush: true)
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                redirect(action: "show", id: params.id)
            }
        }
        else {
            redirect(action: "list")
        }
    }

    def dependentsList = {
        def user = User.findById(springSecurityService.principal.id)
        def role = roleService.getRole()
        def max = 30
        def count
        def employeeDependentsList
//    params.max = Math.min(params.max ? params.int('max') : 30, 100)
        params.sort = params.sort?:'employee.firstName'
        params.order = params.order?:'asc'
        def employee
        //Check for role of user
        if(role[0].toString()==BayalpatraConstants.ROLE_ADMIN){
            //show dependents for all user
            employee = employeeService.getEmpByStatus()
        }
        else if(role[0].toString()==BayalpatraConstants.ROLE_DEPARTMENT_HEAD){
            //show dependents for user under his/her department
            def finalDept = departmentService.getDepartmentList(session["department"])
            employee = employeeService.getEmpByStatusAndDepartment(finalDept)
        }

        else if(role[0].toString()==BayalpatraConstants.ROLE_SUPERVISOR){
            //show dependents for user under his/her
            def supervisor = Supervisor.findByEmployee(user?.employee)
            if(supervisor){
                employee = employeeService.getEmployeeBySupervisor(supervisor,user,null)
            }
        }

        if(employee){
            employeeDependentsList = employeeDependentsService.getDependentList(employee,params,max)
            count = employeeDependentsService.getDependentListCount(employee)
        }
        else
        {
            count = 0
            employeeDependentsList=null
        }

        if (params.emp){
            employeeDependentsList = employeeDependentsService.getEmp(params.emp.toString(),params)
            count = employeeDependentsService.getEmpCount(params.emp.toString())
        }


        if(params?.exportFormat && params.exportFormat != "html"){
            response.contentType = grailsApplication.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=Dependents_List.${params.extension}")
            List fields = [
                    "employee",
                    "fatherName",
                    "motherName",
                    "spouseName",
                    "childName1",
                    "childName2"
            ]
            Map labels = ["employee":"Employee", "fatherName":"Father Name", "motherName":"Mother Name", "childName1":"First Child Name", "childName2":"Second Child Name"]
            Map parameters =["column.widths": [
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15,
                    0.15
            ]]

            def exportList = []
            if (params.emp){
                def allExportParamMap = [offset:0,max:employeeDependentsService.getEmpCount(params.emp.toString())]
                exportList = employeeDependentsService.getEmp(params.emp.toString(),allExportParamMap)
            }else{
                exportList = employeeDependentsService.getDependentList(employee,params,0)
            }
            exportService.export(params.exportFormat, response.outputStream,exportList, fields, labels,[:],parameters)
        }

        render(view:'dependentsList',model:[dependentsList:employeeDependentsList,employeeDependentsInstanceTotal: count,searchEmp:params.emp])
    }

    def ajaxCall={
        def employeeInstanceList
        def exportParams = params.emp
        def employee = employeeService.getEmpByStatus()
        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        params.sort = params.sort?:'employee.firstName'
        params.order = params.order?:'asc'
        def count
        if(params.emp){
            employeeInstanceList = employeeDependentsService.getEmp(params.emp.toString(),params)
            count = employeeDependentsService.getEmpCount(params.emp.toString())
        }
        else{
            employeeInstanceList = employeeDependentsService.getDependentList(employee,params,params.max)
            count = employeeDependentsService.getDependentListCount(employee)
        }
        render(template: "ajaxDependentsList", model:[employeeDependentsList: employeeInstanceList,employeeInstanceTotal:count,exportParams:exportParams])
    }
}
