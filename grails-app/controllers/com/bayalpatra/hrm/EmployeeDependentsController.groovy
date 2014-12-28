package com.bayalpatra.hrm


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EmployeeDependentsController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond EmployeeDependents.list(params), model: [employeeDependentsInstanceCount: EmployeeDependents.count()]
    }

    def show(EmployeeDependents employeeDependentsInstance) {
        respond employeeDependentsInstance
    }

    def create() {
        respond new EmployeeDependents(params)
    }

    @Transactional
    def save(EmployeeDependents employeeDependentsInstance) {
        if (employeeDependentsInstance == null) {
            notFound()
            return
        }

        if (employeeDependentsInstance.hasErrors()) {
            respond employeeDependentsInstance.errors, view: 'create'
            return
        }

        employeeDependentsInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'employeeDependents.label', default: 'EmployeeDependents'), employeeDependentsInstance.id])
                redirect employeeDependentsInstance
            }
            '*' { respond employeeDependentsInstance, [status: CREATED] }
        }
    }

    def edit(EmployeeDependents employeeDependentsInstance) {
        respond employeeDependentsInstance
    }

    @Transactional
    def update(EmployeeDependents employeeDependentsInstance) {
        if (employeeDependentsInstance == null) {
            notFound()
            return
        }

        if (employeeDependentsInstance.hasErrors()) {
            respond employeeDependentsInstance.errors, view: 'edit'
            return
        }

        employeeDependentsInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'EmployeeDependents.label', default: 'EmployeeDependents'), employeeDependentsInstance.id])
                redirect employeeDependentsInstance
            }
            '*' { respond employeeDependentsInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(EmployeeDependents employeeDependentsInstance) {

        if (employeeDependentsInstance == null) {
            notFound()
            return
        }

        employeeDependentsInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'EmployeeDependents.label', default: 'EmployeeDependents'), employeeDependentsInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'employeeDependents.label', default: 'EmployeeDependents'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
