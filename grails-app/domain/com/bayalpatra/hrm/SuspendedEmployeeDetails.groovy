package com.bayalpatra.hrm

class SuspendedEmployeeDetails {

    Employee employee
    Date startDate
    Date endDate

    static constraints = {
        employee(blank:false,nullable: false)
        startDate(blank:false,nullable: false)
        endDate(blank:true,nullable: true)
    }
}
