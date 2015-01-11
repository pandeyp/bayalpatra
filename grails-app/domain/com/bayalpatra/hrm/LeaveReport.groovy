package com.bayalpatra.hrm

class LeaveReport {

    Employee employee
    Date leaveDate
    Double earnedLeave
    Double extraTime
    Double extraDay
    Double paidLeave
    Double unpaidLeave
    Double balanceDays
    Double annualLeaveDays
    Double openingBalance

    static searchable = true

    static transients = ['totalLeaveBalance']

    static constraints = {
        employee(blank:false, unique:'leaveDate')
        earnedLeave(scale:2)
        extraTime(scale:2)
        paidLeave(scale:2)
        unpaidLeave(scale:2)
        balanceDays(scale:2)
        annualLeaveDays(scale:2)
        openingBalance(scale: 2)
    }
    static mapping = {
        sort leaveDate:"desc"
        sort employee:"asc"
    }

    Double getTotalLeaveBalance(){
        Double totalLeaveBalance = 0
        totalLeaveBalance = Parser.parseToDouble(this.openingBalance) + Parser.parseToDouble(this.earnedLeave)
        return totalLeaveBalance
    }

}
