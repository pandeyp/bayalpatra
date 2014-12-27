package com.bayalpatra.hrm

class SalaryClass {

    String identifier
    Integer dayOfMonth

    static searchable = true

    String toString(){
        return identifier
    }
    static constraints = {
        identifier(blank:false,unique: true)
        dayOfMonth(blank:false,range:0..31)
    }

    static mapping = {
        sort "identifier":"asc"

    }
    //for audit trail
    static auditable = true

}
