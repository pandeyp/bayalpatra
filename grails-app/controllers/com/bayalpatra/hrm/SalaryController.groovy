package com.bayalpatra.hrm


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SalaryController {

    def exportService
    def salaryService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {

        params.sort = params.sort?:'designation.jobTitleName'
        params.order = params.order?:'asc'


        if (!params.offset){
            session.designation=null
            session.fromSalary=null
            session.toSalary=null
        }

        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        def fromSalary
        def toSalary
        def designation
        def salaryInstance
        def count
        if (params.designation||params.fromSalary||params.toSalary){
            if(params.designation){
                designation=Designation.findAll("from Designation  d where d.jobTitleName like '"+params.designation+"%'")
            }
            if (params.fromSalary)
                fromSalary=Double.parseDouble(params.fromSalary)
            if (params.toSalary)
                toSalary=Double.parseDouble(params.toSalary)

            salaryInstance=salaryService.getSalaryList(designation,fromSalary,toSalary,params)
            count=salaryService.getSalaryListCount(designation,fromSalary,toSalary)

        }else{
            salaryInstance=Salary.list(params)
            count = Salary.list()
        }

        [salaryInstanceList: salaryInstance, salaryInstanceTotal: count.size(),designation:params.designation,fromSalary:params.fromSalary,toSalary:params.toSalary]
    }

    def ajaxCallForSalary={
        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        def fromSalary
        def toSalary
        def designation
        session.designation=params.designation
        session.fromSalary=params.fromSalary
        session.toSalary=params.toSalary

        if(params.designation){
            designation=Designation.findAll("from Designation  d where d.jobTitleName like '"+params.designation+"%'")
        }
        if (params.fromSalary)
            fromSalary=Double.parseDouble(params.fromSalary)
        if (params.toSalary)
            toSalary=Double.parseDouble(params.toSalary)

        def salaryInstance=salaryService.getSalaryList(designation,fromSalary,toSalary,params)
        def count=salaryService.getSalaryListCount(designation,fromSalary,toSalary)
        render(template: "ajaxSalaryList",model:[salaryInstanceList: salaryInstance, salaryInstanceTotal: count.size(),designation:params.designation,fromSalary:params.fromSalary,toSalary:params.toSalary] )
    }

    def exportToExcel={
        def fromSalary
        def toSalary
        def designation
        def salaryInstance
        if (session.designation||session.fromSalary||session.toSalary){
            if(session.designation){
                designation=Designation.findAll("from Designation  d where d.jobTitleName like '"+session.designation+"%'")
            }
            if (session.fromSalary)
                fromSalary=Double.parseDouble(session.fromSalary)
            if (session.toSalary)
                toSalary=Double.parseDouble(session.toSalary)

            salaryInstance=salaryService.getSalaryList(designation,fromSalary,toSalary,params)

        }else{
            salaryInstance=Salary.list(params)
        }

        if(params?.exportFormat && params.exportFormat != "html"){

            response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
            response.setHeader("Content-disposition", "attachment; filename=Salary_List.${params.extension}")

            List fields = [
                    "level",
                    "designation",
                    "basicSalary"
            ]
            Map labels = ["level":"Level", "designation":"Designation", basicSalary:"Basic Salary "]
            Map parameters =["column.widths": [
                    0.15,
                    0.15,
                    0.15
            ]]

            exportService.export(params.exportFormat, response.outputStream,salaryInstance, fields, labels,[:],parameters)
        }
    }

    def create = {
        def salaryInstance = new Salary()
        if(params.designation){
            salaryInstance.designation = Designation.findById(Integer.valueOf(params.designation))
        }
//        salaryInstance.properties = params
        return [salaryInstance: salaryInstance]
    }

    def save = {
        def salaryInstance = new Salary(params)
        if (salaryInstance.save(flush: true)) {
//            flash.message = "${message(code: 'default.created.message', args: [message(code: 'salary.label', default: 'Salary'), salaryInstance.id])}"
            redirect(action: "list", id: salaryInstance.id)
        }
        else {
            render(view: "create", model: [salaryInstance: salaryInstance])
        }
    }

    def show = {
        def salaryInstance = Salary.get(params.id)
        if (!salaryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'salary.label', default: 'Salary'), params.id])}"
            redirect(action: "list")
        }
        else {
            [salaryInstance: salaryInstance]
        }
    }

    def edit = {
        def salaryInstance = Salary.get(params.id)
        if (!salaryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'salary.label', default: 'Salary'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [salaryInstance: salaryInstance,offset:params.offset]
        }
    }

    def update = {
        def salaryInstance = Salary.get(params.id)
        if (salaryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (salaryInstance.version > version) {

                    salaryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'salary.label', default: 'Salary')] as Object[], "Another user has updated this Salary while you were editing")
                    render(view: "edit", model: [salaryInstance: salaryInstance])
                    return
                }
            }
            salaryInstance.properties = params
            if (!salaryInstance.hasErrors() && salaryInstance.save(flush: true)) {
//                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'salary.label', default: 'Salary'), salaryInstance.id])}"
                redirect(action: "list", id: salaryInstance.id,params:[offset:params.offset])
            }
            else {
                render(view: "edit", model: [salaryInstance: salaryInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'salary.label', default: 'Salary'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def salaryInstance = Salary.get(params.id)
        if (salaryInstance) {
            try {
                salaryInstance.delete(flush: true)
//                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'salary.label', default: 'Salary'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'salary.label', default: 'Salary'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'salary.label', default: 'Salary'), params.id])}"
            redirect(action: "list")
        }
    }

    def getSalaryNotSetList = {

        def max = 30;
        def sortingParam = request.getParameter("sort") ?: 'jobTitleName';
        def sortingOrder = request.getParameter("order") ?: 'asc';
        def offset = request.getParameter("offset") ?:'0';

        def salaryNotSetList = salaryService.getSalaryNotSetList(params,max,offset,sortingParam,sortingOrder);
        def count = salaryService.getCountSalaryNotSetList();
        if(params?.exportFormat && params.exportFormat != "html"){

            response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
            response.setHeader("Content-disposition", "attachment; filename=Salary_List.${params.extension}")
            List fields = [
                    "jobTitleName",
                    "jobDescription",

            ]
            Map labels = [ "jobTitleName":"Job Title Name", "jobDescription":"Job Description"]
            Map parameters =["column.widths": [
                    0.15,
                    0.15

            ]]

            exportService.export(params.exportFormat, response.outputStream,salaryNotSetList, fields, labels,[:],parameters)
        }

        render (view: "salaryNotSetList", model: [designationInstanceList:salaryNotSetList,designationInstanceTotal:count])



    }


/*    def runSalaryReportJob={
        salaryService.generateSalaryBySalaryGroup()
        render('Salary Reports Generated')
    }*/

}
