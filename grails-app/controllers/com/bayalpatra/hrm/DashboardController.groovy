package com.bayalpatra.hrm

import commons.BayalpatraConstants

class DashboardController {

    def springSecurityService

    def index = {

        if (springSecurityService.isLoggedIn()) {

            def role = springSecurityService.getAuthentication().authorities
            def currentUser = User.findById(springSecurityService.principal.id)
            if(currentUser.employee){
                session["employeeName"] = currentUser.employee.firstName + " " + currentUser.employee.lastName
                session["department"] = currentUser.employee.department.id
            }
            else{
                session["employeeName"] = currentUser.username
            }

            if(role[0].toString()==BayalpatraConstants.ROLE_EMPLOYEE){
                forward (controller :'employee',action:'edit',params:[employeeIs:currentUser.employee.id, statusFlag: true])
            }
            else {
                render view: '../dashboard/dashboard_hrm'
            }
        }
        else {
            redirect(controller:'login', action:'auth')
        }

    }

}
