package com.bayalpatra.hrm

import commons.BayalpatraConstants

class Employee {

    String employeeId
    String firstName
    String lastName
    String middleName
    String nationality
    Date dateOfBirth
    String maritalStatus
    String gender
    String country
    String permanentAddress
    String temporaryAddress
    String homePhone
//    String workPhone
    String mobile
    String email
    String alterEmail
    Date joinDate
    Date promotionDate
    Date updatedJoinDate
//    String filename
    String status

//    Integer volunteerDays
//    Integer suspensionDays
    Date effectiveDate
    Employee updatedBy
//    String statusChangedTo
//    Integer gradeReward = 0
//    Integer councilNumber
    Department department
    Designation designation
    SalaryClass salaryclass
//    Unit unit
    Supervisor supervisor
    Boolean isDoc
    Date terminatedDate
//    String changeDepartment
//    Date effectiveDateForDepartment
//    Employee updatedDepartmentBy

    static searchable = true

    static hasMany = [employeeDependents:EmployeeDependents,employeeTraining:EmployeeTraining,employeeLeaveDetail:EmployeeLeaveDetail,empEducation:EmployeeEducation]

    String fullName
    static transients = ['fullName']
    String getFullName (){
        return firstName+" "+middleName+" "+lastName+"-"+employeeId
    }

    String toString(){
        return getFullName()
    }

    static constraints = {
        employeeId(nullable:false)
        firstName(blank:false,matches: "[a-zA-Z ]+")
        lastName(blank:false,matches: "[a-zA-Z ]+")
        middleName(blank:true,matches: "[a-zA-Z ]+")
        nationality()
        dateOfBirth(blank:false)
        maritalStatus(blank:false)
        gender(blank:false)
        country(blank:false)
        permanentAddress(blank:false)
        temporaryAddress()
        homePhone()
//        workPhone()
        mobile()
        email(blank:false,email:true)
        alterEmail(blank:true,email:true)
        isDoc(blank: true,nullable: true)
        effectiveDate(blank:true, nullable: true)
//        statusChangedTo(blank: true, nullable: true )
        updatedBy(blank: true, nullable: true)
        terminatedDate(blank: true, nullable: true)
//        changeDepartment(blank: true, nullable: true)
//        effectiveDateForDepartment(blank: true, nullable: true)
//        updatedDepartmentBy(blank: true, nullable: true)


            status(blank:false,inList:[
                    BayalpatraConstants.CLEARED,
                    BayalpatraConstants.CONTRACT,
                    BayalpatraConstants.DAILY_WAGES,
                    BayalpatraConstants.PERMANENT,
                    BayalpatraConstants.SUSPENDED,
                    BayalpatraConstants.TEMPORARY,
                    BayalpatraConstants.TERMINATED,
                    BayalpatraConstants.VOLUNTEER
            ])

        joinDate(validator: {val, obj ->
            if (val <= obj.properties['dateOfBirth']) {

                return 'joinDateshouldbegreater'
            }
        })
/*

        councilNumber(nullable:true)
        filename(nullable:true)
        volunteerDays(nullable:true)
        suspensionDays(nullable: true)
        gradeReward(nullable:true)
        unit(blank:true, nullable:true)
*/
        supervisor(blank:true, nullable:true)

    }
    static mapping = { sort "firstName":"asc" }

    //for audit trail
    static auditable = true
}
