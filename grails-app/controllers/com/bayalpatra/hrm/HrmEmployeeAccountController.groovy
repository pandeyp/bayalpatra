package com.bayalpatra.hrm
import commons.BayalpatraConstants

class HrmEmployeeAccountController {

    def exportService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        def hrmEmployeeAccountInstanceList
        def count
        def requiredEmployee = params.emp?.trim();

        if (params.emp){
            hrmEmployeeAccountInstanceList = HrmEmployeeAccount.findAll("from HrmEmployeeAccount h where h.employee.firstName LIKE '" + requiredEmployee + "%' AND h.employee.status  NOT IN (:status)",[status:[
                    BayalpatraConstants.CLEARED
            ]],params)
            count = HrmEmployeeAccount.executeQuery("select count (*) from HrmEmployeeAccount h where h.employee.firstName LIKE '" + requiredEmployee + "%' AND h.employee.status  NOT IN (:status)",[status:[
                    BayalpatraConstants.CLEARED
            ]])
        }else{

            hrmEmployeeAccountInstanceList = HrmEmployeeAccount.findAll("from HrmEmployeeAccount h where h.employee.status != :status order by h.employee.firstName asc",[status:BayalpatraConstants.CLEARED], params)
            count = HrmEmployeeAccount.executeQuery("select count(*) from HrmEmployeeAccount h where h.employee.status != :status",[status:BayalpatraConstants.CLEARED])
        }
        if(params?.exportFormat && params.exportFormat != "html"){
            if (params.emp){
                hrmEmployeeAccountInstanceList = HrmEmployeeAccount.findAll("from HrmEmployeeAccount h where h.employee.firstName LIKE '" + requiredEmployee + "%' AND h.employee.status  NOT IN (:status)",[status:[
                        BayalpatraConstants.CLEARED
                ]])

            }else{

                hrmEmployeeAccountInstanceList = HrmEmployeeAccount.findAll("from HrmEmployeeAccount h where h.employee.status != :status order by h.employee.firstName asc",[status:BayalpatraConstants.CLEARED])
            }
            response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
            response.setHeader("Content-disposition", "attachment; filename=Employee_Account_List.${params.extension}")
            List fields = [
                    "employee",
                    "accountNumber",
                    "panNumber",
                    "pfNumber",
                    "citNumber"

            ]
            Map labels = ["employee":"Employee ", "accountNumber":"Account #", "panNumber":"Pan #", "pfNumber":"PF #", "citNumber":"Cit #"]
            Map parameters =["column.widths": [
                    0.15,
                    0.15,
                    0.15 ,
                    0.15 ,
                    0.15,
                    0.15,
                    0.15

            ]]

            exportService.export(params.exportFormat, response.outputStream,hrmEmployeeAccountInstanceList, fields, labels,[:],parameters)
        }

        [hrmEmployeeAccountInstanceList: hrmEmployeeAccountInstanceList, hrmEmployeeAccountInstanceTotal: count.get(0),emp: params.emp]
    }

    def create = {
        def hrmEmployeeAccountInstance = new HrmEmployeeAccount()
        hrmEmployeeAccountInstance.properties = params
        return [hrmEmployeeAccountInstance: hrmEmployeeAccountInstance]
    }

    def save = {
        def hrmEmployeeAccountInstance = new HrmEmployeeAccount(params)
        if (hrmEmployeeAccountInstance.save(flush: true)) {
//            flash.message = "${message(code: 'default.created.message', args: [message(code: 'hrmEmployeeAccount.label', default: 'HrmEmployeeAccount'), hrmEmployeeAccountInstance.id])}"
            redirect(action: "list", id: hrmEmployeeAccountInstance.id)
        }
        else {
            render(view: "create", model: [hrmEmployeeAccountInstance: hrmEmployeeAccountInstance])
        }
    }

    def show = {
        def hrmEmployeeAccountInstance = HrmEmployeeAccount.get(params.id)
        if (!hrmEmployeeAccountInstance) {
//            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'hrmEmployeeAccount.label', default: 'HrmEmployeeAccount'), params.id])}"
            redirect(action: "list")
        }
        else {
            [hrmEmployeeAccountInstance: hrmEmployeeAccountInstance]
        }
    }

    def edit = {
        def hrmEmployeeAccountInstance = HrmEmployeeAccount.get(params.id)
        if (!hrmEmployeeAccountInstance) {
//            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'hrmEmployeeAccount.label', default: 'HrmEmployeeAccount'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [hrmEmployeeAccountInstance: hrmEmployeeAccountInstance]
        }
    }

    def update = {
        def hrmEmployeeAccountInstance = HrmEmployeeAccount.get(params.id)
        if (hrmEmployeeAccountInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (hrmEmployeeAccountInstance.version > version) {

                    hrmEmployeeAccountInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'hrmEmployeeAccount.label', default: 'HrmEmployeeAccount')] as Object[], "Another user has updated this HrmEmployeeAccount while you were editing")
                    render(view: "edit", model: [hrmEmployeeAccountInstance: hrmEmployeeAccountInstance])
                    return
                }
            }
            hrmEmployeeAccountInstance.properties = params
            if (!hrmEmployeeAccountInstance.hasErrors() && hrmEmployeeAccountInstance.save(flush: true)) {
//                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'hrmEmployeeAccount.label', default: 'HrmEmployeeAccount'), hrmEmployeeAccountInstance.id])}"
                redirect(action: "list", id: hrmEmployeeAccountInstance.id)
            }
            else {
                render(view: "edit", model: [hrmEmployeeAccountInstance: hrmEmployeeAccountInstance])
            }
        }
        else {
//            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'hrmEmployeeAccount.label', default: 'HrmEmployeeAccount'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def hrmEmployeeAccountInstance = HrmEmployeeAccount.get(params.id)
        if (hrmEmployeeAccountInstance) {
            try {
                hrmEmployeeAccountInstance.delete(flush: true)
//                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'hrmEmployeeAccount.label', default: 'HrmEmployeeAccount'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
//                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'hrmEmployeeAccount.label', default: 'HrmEmployeeAccount'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
//            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'hrmEmployeeAccount.label', default: 'HrmEmployeeAccount'), params.id])}"
            redirect(action: "list")
        }
    }

    def searchEmployee = {

        def hrmEmployeeAccountInstanceList
        def count
        def requiredEmployee = params.emp?.trim();
        params.max = Math.min(params.max ? params.int('max') : 30, 100)
//        params.offset = request.getParameter('offset');

        if (params.emp){
            hrmEmployeeAccountInstanceList = HrmEmployeeAccount.findAll("from HrmEmployeeAccount h where h.employee.firstName LIKE '" + requiredEmployee + "%' AND h.employee.status  NOT IN (:status)",[status:[
                    BayalpatraConstants.CLEARED
            ]],params)
            count = HrmEmployeeAccount.executeQuery("select count (*) from HrmEmployeeAccount h where h.employee.firstName LIKE '" + requiredEmployee + "%' AND h.employee.status  NOT IN (:status)",[status:[
                    BayalpatraConstants.CLEARED
            ]])
        }else{
            hrmEmployeeAccountInstanceList =   HrmEmployeeAccount.findAll("from HrmEmployeeAccount h where h.employee.status != :status order by h.employee.firstName asc",[status:BayalpatraConstants.CLEARED], params)
            count = HrmEmployeeAccount.executeQuery("select count(*) from HrmEmployeeAccount h where h.employee.status != :status",[status:BayalpatraConstants.CLEARED])
        }

        render(template:"ajaxEmployeeAccountList", model:[hrmEmployeeAccountInstanceList: hrmEmployeeAccountInstanceList,hrmEmployeeAccountInstanceTotal:count.get(0),emp: params.emp])

    }
}
