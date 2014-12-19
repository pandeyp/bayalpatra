package com.bayalpatra.hrm

class Department {

    String name
    Integer level
    String description

    static constraints = {
        name (blank: false,nullable: false)
        level (blank: false,nullable: false)
        description (blank: true,nullable: true)
    }
}
