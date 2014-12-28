package com.bayalpatra.hrm

class EmployeeHistory {

    Employee employee
    Employee user
    String oldValue
    String fieldType
    Date fromDate
    Date toDate = new Date()
    Date updatedDate = new Date()

    static searchable = true

    static constraints = {
        employee(nullable:true)
        oldValue(nullable:true)
        fieldType(nullable:true)
        fromDate(nullable:true)
        toDate(nullable:true)
        user(blank:true,nullable: true)
    }
    static mapping = {
        sort "updatedDate":"asc"
    }

}
