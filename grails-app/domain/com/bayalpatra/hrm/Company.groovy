package com.bayalpatra.hrm

class Company {
    String name
    /*Integer numEmployee*/
    String taxId
    /*String naiCs*/
    String phone
    String fax
    String country
    String address1
    String address2
    String district
    String zone
    String city
    String comment
    /*String logo*/

    static searchable = true

    static hasmany = [departments:Department]

    String toString(){
        return name
    }

    static constraints = {
        name(blank:false)
        /*numEmployee()*/
        taxId(blank:false)
        /*naiCs()*/
        phone(blank:true,nullable:true)
        fax(blank:true,nullable:true)
        country(blank:true,nullable:true)
        address1(blank:true,nullable:true)
        address2(blank:true,nullable:true)
        district(blank:true,nullable:true)
        zone(blank:true,nullable:true)
        city(blank:true,nullable:true)
        comment(blank:true,nullable:true)

    }
    //for audit trail
    static auditable = true
}
