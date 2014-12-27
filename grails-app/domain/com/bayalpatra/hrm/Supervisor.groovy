package com.bayalpatra.hrm

class Supervisor {

    Employee employee

    public String toString(){
        return employee
    }


    String hasMany = [employee:Employee]

    static searchable = true

    static constraints = {
        employee(blank:false,unique:true)
    }

    static mapping = { sort "employee":"asc" }
    //for audit trail
    static auditable = true
}
