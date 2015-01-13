package com.bayalpatra.hrm


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SalaryReportController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond SalaryReport.list(params), model: [salaryReportInstanceCount: SalaryReport.count()]
    }

    def show(SalaryReport salaryReportInstance) {
        respond salaryReportInstance
    }

    def create() {
        respond new SalaryReport(params)
    }

    @Transactional
    def save(SalaryReport salaryReportInstance) {
        if (salaryReportInstance == null) {
            notFound()
            return
        }

        if (salaryReportInstance.hasErrors()) {
            respond salaryReportInstance.errors, view: 'create'
            return
        }

        salaryReportInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'salaryReport.label', default: 'SalaryReport'), salaryReportInstance.id])
                redirect salaryReportInstance
            }
            '*' { respond salaryReportInstance, [status: CREATED] }
        }
    }

    def edit(SalaryReport salaryReportInstance) {
        respond salaryReportInstance
    }

    @Transactional
    def update(SalaryReport salaryReportInstance) {
        if (salaryReportInstance == null) {
            notFound()
            return
        }

        if (salaryReportInstance.hasErrors()) {
            respond salaryReportInstance.errors, view: 'edit'
            return
        }

        salaryReportInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'SalaryReport.label', default: 'SalaryReport'), salaryReportInstance.id])
                redirect salaryReportInstance
            }
            '*' { respond salaryReportInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(SalaryReport salaryReportInstance) {

        if (salaryReportInstance == null) {
            notFound()
            return
        }

        salaryReportInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'SalaryReport.label', default: 'SalaryReport'), salaryReportInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'salaryReport.label', default: 'SalaryReport'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
