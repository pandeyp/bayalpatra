package com.bayalpatra.hrm

class SalaryReport {

    Employee employee
    Date salaryDate
    Double tax
    Double total


    static constraints = {
        employee(blank:false, unique:'salaryDate')
        tax(scale:2)
        salaryDate(blank:false)
        total(blank:false,scale:2)
    }
}
