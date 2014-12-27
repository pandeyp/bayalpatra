package com.bayalpatra.hrm

import com.bayalpatra.hrm.Employee

class Designation {

    String jobTitleName
    String jobDescription
    String hasMany = [employee:Employee]

    static searchable = true

    public String toString(){
        return jobTitleName
    }
    static constraints = {
        jobTitleName(blank:false,unique: true)
        jobDescription()
    }

    static mapping = {
        sort "jobTitleName":"asc"


    }

    //for audit trail
    static auditable = true

}
