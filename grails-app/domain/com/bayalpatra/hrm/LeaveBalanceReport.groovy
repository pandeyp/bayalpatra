package com.bayalpatra.hrm

class LeaveBalanceReport {

    Employee employee
    String year
    Double paidLeave=0.0
    Double unpaidLeave=0.0
    Double floatingLeave=0.0
    Double sickLeave=0.0
    Double personalLeave=0.0

    static constraints = {
        paidLeave(nullable:true, scale:2)
        unpaidLeave(nullable:true, scale:2)
        floatingLeave(scale:2)
        sickLeave(scale:2)
        personalLeave(scale:2)
        employee(unique:'year')
    }
}
