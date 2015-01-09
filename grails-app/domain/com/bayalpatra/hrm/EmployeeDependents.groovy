package com.bayalpatra.hrm

class EmployeeDependents {


    String fatherName
    Date fatherDob
    String fatherAddress
    String motherName
    Date motherDob
    String spouseName
    Date spouseDob
    String spouseAddress
    String childName1
    Date child1Dob
    String childName2
    Date child2Dob
    Employee employee

    static searchable = true

    static constraints = {

        fatherName(blank:true,nullable:true)
        fatherDob(blank:true,nullable:true)
        fatherAddress(blank:true,nullable:true)
        motherName(blank:true,nullable:true)
        motherDob(blank:true,nullable:true)
        spouseName(blank:true,nullable:true)
        spouseDob(blank:true,nullable:true)
        spouseAddress(blank:true,nullable:true)
        childName1(blank:true,nullable:true)
        child1Dob(blank:true,nullable:true)
        childName2(blank:true,nullable:true)
        child2Dob(blank:true,nullable:true)

    }
    static auditable = true

}
