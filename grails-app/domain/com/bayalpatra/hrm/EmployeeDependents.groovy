package com.bayalpatra.hrm

class EmployeeDependents {


    String fatherName // or fatherInLawName, fatherInLawAge, fatherInLawAddress
    Date fatherDob
    //    String fatherAge
    String fatherAddress
    String motherName //or motherInLawName, motherInLawAge
    Date motherDob
    //      String motherAge
    String spouseName
    Date spouseDob
    //    String spouseAge
    String spouseAddress
    String childName1
    Date child1Dob
    //    String childAge1
    String childName2
    Date child2Dob
    //    String childAge2
    Employee employee

    static searchable = true

    static constraints = {

        fatherName(blank:false)
        fatherDob()
        //      fatherAge()
        fatherAddress()
        motherName(blank:false)
        motherDob()
        //      motherAge()
        spouseName()
        spouseDob()
        //      spouseAge()
        spouseAddress()
        childName1()
        child1Dob()
        //      childAge1()
        childName2()
        child2Dob()
        //      childAge2()
    }
    static auditable = true

}
