package com.bayalpatra.hrm

class Salary {

    String level
    Designation designation
    Double basicSalary

    static searchable = true

    static constraints = {
        level(blank:false)
        designation(blank:false,unique:true)
        basicSalary(blank:false,scale:2)
    }

    static mapping = {
        sort "level":"asc"
    }
    //for audit trail
    static auditable = true
}
