package com.bayalpatra.hrm

class EmployeeEducationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        [employeeEducationInstanceList: EmployeeEducation.list(params), employeeEducationInstanceTotal: EmployeeEducation.count()]
    }

    def create = {

        def employee = Employee.findById(params.employeeIs);

        def empEducationList = EmployeeEducation.findAllByEmployee(employee);


        def employeeEducationInstance = new EmployeeEducation()
        employeeEducationInstance.properties = params
        return [employeeEducationInstance: employeeEducationInstance, employeeInstance: employee, empEducationList :empEducationList]

    }

    def save = {

//      def x= "${employeeEducationInstance?.date}";

        Employee emp = Employee.findById(params.employee.id)
        def empEducationList = EmployeeEducation.findAllByEmployee(emp);
        def employeeEducationInstance = new EmployeeEducation(params)
        //println('params---->'+params)
        if (employeeEducationInstance.save(flush: true)) {

            //flash.message = "${message(code: 'default.created.message', args: [message(code: 'employeeEducation.label', default: 'EmployeeEducation'), employeeEducationInstance.id])}"
            redirect(action: "create", params :['employeeIs': employeeEducationInstance.employee.id])
//            redirect(action: "show", id: employeeEducationInstance.id)
        }
        else {
            render(view: "create",model: [employeeEducationInstance: employeeEducationInstance, employeeInstance:emp,empEducationList :empEducationList])

        }
    }

    def show = {
        def employeeEducationInstance = EmployeeEducation.get(params.id)
        if (!employeeEducationInstance) {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeeEducation.label', default: 'EmployeeEducation'), params.id])}"
            redirect(action: "list")
        }
        else {
            [employeeEducationInstance: employeeEducationInstance]
        }
    }

    def edit = {

        def employeeEducationInstance = EmployeeEducation.get(params.educationIs)
        def employee= employeeEducationInstance.employee
//      println(employee)
        if (!employeeEducationInstance) {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeeEducation.label', default: 'EmployeeEducation'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [employeeEducationInstance: employeeEducationInstance,employeeInstance: employee]
        }
    }

    def update = {
        def employeeEducationInstance = EmployeeEducation.get(params.educationIs)
        if (employeeEducationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (employeeEducationInstance.version > version) {

                    employeeEducationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'employeeEducation.label', default: 'EmployeeEducation')] as Object[], "Another user has updated this EmployeeEducation while you were editing")
                    render(view: "edit", model: [employeeEducationInstance: employeeEducationInstance])
                    return
                }
            }
            employeeEducationInstance.properties = params

            if (!employeeEducationInstance.hasErrors() && employeeEducationInstance.save(flush: true)) {
                //flash.message = "${message(code: 'default.updated.message', args: [message(code: 'employeeEducation.label', default: 'EmployeeEducation'), employeeEducationInstance.id])}"
                redirect(action: "create", params:['employeeIs': employeeEducationInstance.employee.id])
//               redirect(action: "show", id: employeeEducationInstance.id)
            }
            else {
                //render(view: "edit", model: [employeeEducationInstance: employeeEducationInstance], id:['employee.id': employeeEducationInstance.employee.id])
                render(view: "edit", model: [employeeEducationInstance: employeeEducationInstance, employeeInstance: employeeEducationInstance.employee])
            }
        }
        else {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeeEducation.label', default: 'EmployeeEducation'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def employeeEducationInstance = EmployeeEducation.get(params.id)
        if (employeeEducationInstance) {
            try {
                employeeEducationInstance.delete(flush: true)
                //flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'employeeEducation.label', default: 'EmployeeEducation'), params.id])}"
//                redirect(action: "list")
                redirect(action: "create", params:['employee.id': employeeEducationInstance.employee.id])
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                //flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'employeeEducation.label', default: 'EmployeeEducation'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeeEducation.label', default: 'EmployeeEducation'), params.id])}"
            redirect(action: "create", params:['employee.id': employeeEducationInstance.employee.id])
        }
    }

}
