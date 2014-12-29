package com.bayalpatra.hrm

class DesignationController {

    def exportService
    def grailsApplication

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        if (!params.offset){
            session.jobs=null
        }

        if(!params.max) params.max = 30
        def designation
        def count
        if(params.jobs){
            designation= Designation.createCriteria().list {
                like("jobTitleName",params.jobs+"%")
                order("jobTitleName","asc")
            }
            count=Designation.createCriteria().list {
                like("jobTitleName",params.jobs+"%")
            }

        }else{
            designation= Designation.list(params)
            count=Designation.list()
        }

        [designationInstanceList: designation, designationInstanceTotal: count.size(),jobs: params.jobs]
    }

    def ajaxCallForDesignation={

        def designation
        def count
        session.jobs=params.jobs
        if(!params.max) params.max = 30
        if(params.jobs){
            designation= Designation.createCriteria().list {
                like("jobTitleName",params.jobs+"%")
                order("jobTitleName","asc")
            }
            count=Designation.createCriteria().list {
                like("jobTitleName",params.jobs+"%")
            }

        }else{
            designation= Designation.list(params)
            count=Designation.list()
        }
        render(template: "ajaxDesignationList",model:[designationInstanceList: designation, designationInstanceTotal: count.size(),jobs:params.jobs])

    }

    def exportToExcel={

        if(params?.exportFormat && params.exportFormat != "html"){
            params.jobs=session.jobs
            def designation
            def count
            if(params.jobs){
                designation= Designation.createCriteria().list {
                    like("jobTitleName",params.jobs+"%")
                    order("jobTitleName","asc")
                }

            }else{
                designation= Designation.list(params)

            }

            response.contentType = grailsApplication.config.grails.mime.types[params.format]

            response.setHeader("Content-disposition", "attachment; filename=Designation.${params.extension}")
            List fields=[
                    "jobTitleName",
                    "jobDescription"
            ]
            Map labels=["jobTitleName":"Job Title Name","jobDescription":"Job Description"]
            Map parameters =["column.widths": [0.15, 0.15]]
            exportService.export(params.exportFormat, response.outputStream,designation, fields, labels,[:],parameters)
        }
    }

    def create = {
        def designationInstance = new Designation()
        designationInstance.properties = params
        return [designationInstance: designationInstance]
    }

    def save = {
        def designationInstance = new Designation(params)
        if (designationInstance.save(flush: true)) {
            //                  flash.message = "${message(code: 'default.created.message', args: [message(code: 'designation.label', default: 'Designation'), designationInstance.id])}"
            redirect(action: "list", id: designationInstance.id)
        }
        else {
            render(view: "create", model: [designationInstance: designationInstance])
        }
    }

    def show = {
        def designationInstance = Designation.get(params.id)
        if (!designationInstance) {
            //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'designation.label', default: 'Designation'), params.id])}"
            redirect(action: "list")
        }
        else {
            [designationInstance: designationInstance]
        }
    }

    def edit = {
        def designationInstance = Designation.get(params.id)
        if (!designationInstance) {
            redirect(action: "list")
        }
        else {
            return [designationInstance: designationInstance, offset:params.offset]
        }
    }

    def update = {
        def designationInstance = Designation.get(params.id)
        if (designationInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (designationInstance.version > version) {

                    designationInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
                            message(code: 'designation.label', default: 'Designation')]
                            as Object[], "Another user has updated this Designation while you were editing")
                    render(view: "edit", model: [designationInstance: designationInstance])
                    return
                }
            }
            designationInstance.properties = params
            if (!designationInstance.hasErrors() && designationInstance.save(flush: true)) {
                redirect(action: "list", id: designationInstance.id,params:[offset:params.offset])
            }
            else {
                render(view: "edit", model: [designationInstance: designationInstance])
            }
        }
        else {
            redirect(action: "list")
        }
    }

    def delete = {
        def designationInstance = Designation.get(params.id)
        if (designationInstance) {
            try {
                designationInstance.delete(flush: true)
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
}
