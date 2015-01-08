package com.bayalpatra.hrm

import commons.BayalpatraConstants

class SupervisorController {
    def exportService

    EmployeeService employeeService
    SupervisorService supervisorService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {

        def max = 30
        def sortingParam = request.getParameter("sort") ?: 'employee.firstName';
        def sortingOrder = request.getParameter("order") ?: 'asc';
        def offset = request.getParameter("offset") ?:'0';

        def employee = employeeService.getEmpByStatus()
        def supervisorInstanceList = supervisorService.getSupervisorList(employee,params,max,offset,sortingParam,sortingOrder)
        //def supervisorInstanceList = supervisorService.getSupervisors(employee)
        def count = supervisorService.getSupervisorCount(employee)

        if (params.offset=='0'){
            session.supervisor=null

        }

        [supervisorInstanceList: supervisorInstanceList, supervisorInstanceTotal: count]
    }

    def ajaxCallForSupervisor={
        def supervisor=params.supervisor
        session.supervisor=params.supervisor
        def supervisorInstanceList = Supervisor.findAll("from Supervisor s where s.employee.firstName like'"+supervisor+"%'order by s.employee.firstName asc")
        render(template: "ajaxSupervisorList",model: [supervisorInstanceList:supervisorInstanceList])


    }
    def exportToExcel={
        def max = 30
        def sortingParam = request.getParameter("sort") ?: 'employee.firstName';
        def sortingOrder = request.getParameter("order") ?: 'asc';
        def offset = request.getParameter("offset") ?:'0';

        def employee = employeeService.getEmpByStatus()
        if(params?.exportFormat && params.exportFormat != "html"){
            params.supervisor=session.supervisor
            def supervisorList
            if (params.supervisor){
                def supervisor=params.supervisor
                supervisorList = Supervisor.findAll("from Supervisor s where s.employee.firstName like'"+supervisor+"%'order by s.employee.firstName asc")
            }else{
                supervisorList=supervisorService.getSupervisorList(employee,params,0,0,'employee.firstName','asc')
            }
            response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
            response.setHeader("Content-disposition", "attachment; filename=Supervisor.${params.extension}")
            List fields=[
                    "employee"
            ]
            Map labels=["employee":"Supervisor"]
            Map parameters =["column.widths": [0.15]]
            exportService.export(params.exportFormat, response.outputStream,supervisorList, fields, labels,[:],parameters)
        }
    }

    def create = {
        def onlyEmployee
        def Employees
        def supervisorInstance = new Supervisor()
        def employee
        def supervisorList=Supervisor.findAll()?.employee?.id
        supervisorInstance.properties = params

        if (supervisorList){
            onlyEmployee = employeeService.getEmpNotAssignedToSup(supervisorList)
            return [supervisorInstance: supervisorInstance,employee: onlyEmployee]
        }
        else {
            employee = employeeService.getEmpByStatus()
            return [supervisorInstance: supervisorInstance,employee: employee]
        }

    }

    def save = {
        def supervisorInstance = new Supervisor(params)
        def employee = employeeService.getEmpByStatus()

        if (supervisorInstance.save(flush: true)) {
            def user = User.findByEmployee(supervisorInstance.getEmployee())
            def userRole = UserRole.findByUser(user)
            userRole?.role = Role.findByAuthority(BayalpatraConstants.ROLE_SUPERVISOR)

//            flash.message = "${message(code: 'default.created.message', args: [message(code: 'supervisor.label', default: 'Supervisor'), supervisorInstance.id])}"
            redirect(action: "list", id: supervisorInstance.id)
        }
        else {
            render(view: "create", model: [supervisorInstance: supervisorInstance,employee: employee])
        }
    }

    def show = {
        def supervisorInstance = Supervisor.get(params.id)
        if (!supervisorInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'supervisor.label', default: 'Supervisor'), params.id])}"
            redirect(action: "list")
        }
        else {
            [supervisorInstance: supervisorInstance]
        }
    }

    def edit = {
        def supervisorInstance = Supervisor.get(params.id)
        def employee = employeeService.getEmpByStatus()
        if (!supervisorInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'supervisor.label', default: 'Supervisor'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [supervisorInstance: supervisorInstance, offset:params.offset,employee: employee]
        }
    }

    def update = {
        def employee = employeeService.getEmpByStatus()
        def supervisorInstance = Supervisor.get(params.id)
//    println "supervisorInstance "+supervisorInstance

        if (supervisorInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (supervisorInstance.version > version) {

                    supervisorInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'supervisor.label', default: 'Supervisor')] as Object[], "Another user has updated this Supervisor while you were editing")
                    render(view: "edit", model: [supervisorInstance: supervisorInstance,employee: employee])
                    return
                }
            }
            supervisorInstance.properties = params
            println "emp "+ supervisorInstance.getEmployee()
            if (!supervisorInstance.hasErrors() && supervisorInstance.save(flush: true)) {
                def user = User.findByEmployee(supervisorInstance.getEmployee())
                println "user "+ user
                def userRole = UserRole.findByUser(user)
                userRole?.role = Role.findByAuthority(BayalpatraConstants.ROLE_SUPERVISOR)
                redirect(action: "list", id: supervisorInstance.id,params:[offset:params.offset])
            }
            else {
                render(view: "edit", model: [supervisorInstance: supervisorInstance,employee: employee])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'supervisor.label', default: 'Supervisor'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def supervisorInstance = Supervisor.get(params.id)
        if (supervisorInstance) {
            try {
                supervisorInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'supervisor.label', default: 'Supervisor'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'supervisor.label', default: 'Supervisor'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'supervisor.label', default: 'Supervisor'), params.id])}"
            redirect(action: "list")
        }
    }
}
