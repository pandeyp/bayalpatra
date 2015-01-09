package com.bayalpatra.hrm

import com.bayalpatra.commons.BayalpatraEmail
import commons.BayalpatraConstants
import commons.DateUtils

class EmployeeTrainingController {

    def employeeService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        [employeeTrainingInstanceList: EmployeeTraining.list(params), employeeTrainingInstanceTotal: EmployeeTraining.count()]
    }

    def create = {


        def employee = Employee.findById(params.employeeIs);

        def empTrainingList = EmployeeTraining.findAllByEmployee(employee);

        def employeeTrainingInstance = new EmployeeTraining()
        employeeTrainingInstance.properties = params
        return [employeeTrainingInstance: employeeTrainingInstance, employeeInstance: employee, empTrainingList :empTrainingList]
    }

    def save = {

        def emp = Employee.findById(params.employee.id)

        def empTrainingList = EmployeeTraining.findAllByEmployee(emp);
        def employeeTrainingInstance = new EmployeeTraining(params)
        if (employeeTrainingInstance.save(flush: true)) {
            //create a email object and save to send the email notification

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
            BayalpatraEmail bpE = new BayalpatraEmail()
            bpE.toAddress = emp.email
            bpE.ccAddress = ccAdd

            bpE.subject = " Notification for Training of "+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName
            bpE.messageBody = "This is to notify you that you have been nominated for the training starting from "+DateUtils.dateToString(employeeTrainingInstance.startDate)+" to<br>"+DateUtils.dateToString(employeeTrainingInstance.endDate)+" for "+DateUtils.getDaysFromTwoDates(employeeTrainingInstance.getStartDate(),employeeTrainingInstance.getEndDate())+" Days. Please contact HR for the training detail.<br><br><b>Training Details:</b><br>Name: "+emp?.firstName+" "+emp?.middleName+" "+emp?.lastName+"<br>ID: "+emp?.employeeId+"<br>Designation: "+emp?.designation+"<br>Department: "+emp?.department
            bpE.messageBody+="<br>Training from: "+DateUtils.dateToString(employeeTrainingInstance.startDate)+" to "+DateUtils.dateToString(employeeTrainingInstance.endDate)+"<br><br>Please contact HR and the unit incharge/HOD to start your regular duty as soon as training will be over.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL"
            bpE.save(flush:true, failOnError: true)



            //flash.message = "${message(code: 'default.created.message', args: [message(code: 'employeeTraining.label', default: 'EmployeeTraining'), employeeTrainingInstance.id])}"
            redirect(action: "create", params :['employeeIs': employeeTrainingInstance.employee.id])
//          redirect(action: "show", id: employeeTrainingInstance.id)
        }
        else {
            render(view: "create", model: [employeeTrainingInstance: employeeTrainingInstance,employeeInstance:emp,empTrainingList:empTrainingList])
        }
    }

    def show = {
        def employeeTrainingInstance = EmployeeTraining.get(params.id)
        if (!employeeTrainingInstance) {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeeTraining.label', default: 'EmployeeTraining'), params.id])}"
            redirect(action: "list")
        }
        else {
            [employeeTrainingInstance: employeeTrainingInstance]
        }
    }

    def edit = {
        def employeeTrainingInstance = EmployeeTraining.get(params?.id)
        def employee= employeeTrainingInstance.employee
        if (!employeeTrainingInstance) {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeeTraining.label', default: 'EmployeeTraining'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [employeeTrainingInstance: employeeTrainingInstance,employeeInstance: employee]
        }
    }

    def update = {

        def employeeTrainingInstance = EmployeeTraining.get(params.id)
        if (employeeTrainingInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (employeeTrainingInstance.version > version) {

                    employeeTrainingInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'employeeTraining.label', default: 'EmployeeTraining')] as Object[], "Another user has updated this EmployeeTraining while you were editing")
                    render(view: "edit", model: [employeeTrainingInstance: employeeTrainingInstance])
                    return
                }
            }
            employeeTrainingInstance.properties = params
            if (!employeeTrainingInstance.hasErrors() && employeeTrainingInstance.save(flush: true)) {
                //flash.message = "${message(code: 'default.updated.message', args: [message(code: 'employeeTraining.label', default: 'EmployeeTraining'), employeeTrainingInstance.id])}"
                redirect(action: "create", params:['employeeIs': employeeTrainingInstance.employee.id])

            }
            else {
                render(view: "edit", model: [employeeTrainingInstance: employeeTrainingInstance, employeeInstance: employeeTrainingInstance.employee])
            }
        }
        else {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeeTraining.label', default: 'EmployeeTraining'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {

        def employeeTrainingInstance = EmployeeTraining.get(params?.id)
        if (employeeTrainingInstance) {
            try {

                employeeTrainingInstance.delete(flush: true)
                //flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'employeeTraining.label', default: 'EmployeeTraining'), params.id])}"
//                redirect(action: "create")
                redirect(action: "create", params:['employee.id': employeeTrainingInstance.employee.id])
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                //flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'employeeTraining.label', default: 'EmployeeTraining'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeeTraining.label', default: 'EmployeeTraining'), params.id])}"
            redirect(action: "create", params:['employee.id': employeeTrainingInstance.employee.id])
        }
    }
}
