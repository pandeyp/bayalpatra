package com.bayalpatra.hrm

class EmployeeEducation {
    String degree
    String college
    Date date
    String remarks

    Employee employee

    static searchable = true

    static constraints = {
        // degree(inList:['Select Degree','MBBS','MD','BN','Staff Nurse','Lab Technician'])
        degree(blank:false)
        college(blank:false)

    }
    static auditable = true
}
