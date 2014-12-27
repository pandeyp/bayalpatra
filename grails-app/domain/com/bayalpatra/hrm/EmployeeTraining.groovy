package com.bayalpatra.hrm

class EmployeeTraining {
    String title
    Date startDate
    Date endDate
    Date boundPeriodFrom
    Date boundPeriodTo

    Employee employee

    static searchable = true

    static constraints = {
        title(blank:false)
        startDate(nullable:false)
        endDate(validator: {val, obj ->
            if (val && val.before(obj.startDate))
            {

                return 'endDateshouldbegreater'
            }
        })
        boundPeriodFrom(blank: true,nullable: true)
        boundPeriodTo(blank: true,nullable: true, validator: {val, obj ->
            if (val && val.before(obj.boundPeriodFrom))
            {

                return 'boundPeriodToshouldbegreater'
            }
        })


    }

    //for audit trail
    static auditable = true

}
