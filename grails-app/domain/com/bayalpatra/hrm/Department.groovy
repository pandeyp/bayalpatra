package com.bayalpatra.hrm

class Department {

    String name

    Integer parentId
    Integer rootId
    String idNumber

    static searchable = true

    static hasMany = [employee:Employee, user:User]

    String toString(){
        return name
    }

    static constraints = {
        name(blank:false,unique: true)
        parentId(nullable:false)
        rootId(nullable:true)
        idNumber(nullable:true)
        name(unique:'parentId')
    }

    static mapping = {
        sort "name":"asc"
//        sort "parentId":"asc"
    }
    //for audit trail
    static auditable = true

}
