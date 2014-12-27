package com.bayalpatra.hrm

class EmployeeLeaveDetail {


    Employee employee
    LeaveType leaveType
    Date fromDate
    Date toDate
    Double leaveDays
    Double leaveDifference
    String approvedBy
    String status='Unapproved'
    String whichHalf
    String leaveReason

    static searchable = true

    static constraints = {
        approvedBy(blank:true,nullable:true)
        whichHalf(blank:true,nullable:true)
        leaveDays(scale:1)
        fromDate(nullable:false)
        toDate(validator: {val, obj ->
            if (val && val.before(obj.fromDate)) {
                return 'toDateshouldbegreater'
            }
        })
        leaveReason(blank: true,nullable: true,maxSize: 300)
    }
    static mapping = {
        sort "employee":"asc"
        sort "fromDate":"desc"
        sort "toDate":"desc"

    }

}
