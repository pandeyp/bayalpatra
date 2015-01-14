package com.bayalpatra.hrm

class HrmEmployeeAccount {

    Employee employee
    String accountNumber
    String panNumber
    String pfNumber
    String citNumber


    static constraints = {
        accountNumber(nullable:true,blank:true,unique: true)
        panNumber(nullable:true,blank:true,unique: true)
        pfNumber(nullable:true,blank:true,unique: true)
        citNumber(nullable:true,blank:true,unique: true)
        employee(nullable:false,blank:false,unique: true)
    }
    static mapping = {
        sort employee:"asc"
    }
    //for audit trail
    static auditable = true
}
