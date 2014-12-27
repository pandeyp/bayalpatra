package com.bayalpatra.hrm

class LeaveType {

    String leaveType
    String paidUnpaid//P or U
    Double days = 0
    String status//Active or Disable

    String toString(){return leaveType}

    static searchable = true

    static constraints = {
        leaveType(blank:false,unique:true)
        days(scale:1)
        status(inList:["Active", "Disabled"],blank:false)
        paidUnpaid(inList:["Paid", "Unpaid"],blank:false)
    }

    static mapping = { sort "leaveType":"asc" }

    //for audit trail
    static auditable = true

}
