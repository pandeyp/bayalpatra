package com.bayalpatra.hrm

class DashboardController {

    def springSecurityService

    def index = {

        if (springSecurityService.isLoggedIn()) {
/*
            def role = springSecurityService.getAuthentication().authorities.toString()
            def currentUser = User.findById(springSecurityService.principal.id)
            if(currentUser.employee){

                session["employeeName"] = currentUser.employee.firstName + " " + currentUser.employee.middleName+ " " + currentUser.employee.lastName
                session["department"] = currentUser.employee.departments.id

                if(currentUser.employee.unit){
                    session["unit"] = currentUser.employee.unit
                }
            }
            else{
                session["employeeName"] = currentUser.username
            }
*/

            render (view: "../dashboard/dashboard_hrm")

/* Logic for employee role.

if(role==AnnapurnaConstants.ROLE_EMPLOYEE){
                forward (controller :'employee',action:'edit',params:[employeeIs:currentUser.employee.id, statusFlag: true])
            }
            else {
                render view: '../dashboard/dashboard_hrm'
            }*/
        }
        else {
            redirect(controller:'login', action:'auth')
        }

    }

}
