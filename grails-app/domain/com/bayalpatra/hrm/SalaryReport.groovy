package com.bayalpatra.hrm

class SalaryReport {

    Employee employee
    Date salaryDate
    Date salaryMonth
    Double tax
    Double total


    static constraints = {
        employee(blank:false, unique:'salaryMonth')
        tax(scale:2)
        salaryDate(blank:false)
        salaryMonth(blank:false)
        total(blank:false,scale:2)
    }
}
