package com.bayalpatra.commons

class BayalpatraEmail {

    String toAddress
    String ccAddress
    String subject
    String messageBody
    Date sentDate
    Boolean status=false

    static constraints = {
        ccAddress(nullable:true)
        toAddress(nullable:false)
        subject(nullable:false)
        messageBody(nullable:false)
        status(nullable: false)
        sentDate(nullable:true)
    }

    static mapping={
        messageBody type: 'text'
    }
}
