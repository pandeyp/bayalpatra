package com.bayalpatra.commons

import commons.DateUtils
import grails.transaction.Transactional
import org.apache.log4j.Logger
import org.springframework.mail.MailException

@Transactional
class BayalpatraEmailService {

    static transactional = true

    //get the logger instance
    private static final Logger LOGGER = Logger.getLogger(BayalpatraEmailService.class)

    /**
     * Process each email which status is unprocessed ie. <code>false</code> in our system case.
     * If the email is successfully sent then set the sent timestamp and change the status of the email to processed ie. <code>true</code>
     */
    public void processEmail() {

		def emailList = BayalpatraEmail.findAllByStatus(false);
		for(BayalpatraEmail email : emailList){
			try{
				sendMail {
                    from "psychovipers@gmail.com"
					to email.toAddress
					if(email.ccAddress){
                        List<String> userEmails=email.ccAddress.split(',').collect { it.trim() }
						cc userEmails.toArray()
					}
					subject email.subject
					html email.messageBody
				}
				email.setSentDate(DateUtils.getCurrentDate())
				email.setStatus(true)
				email.save(flush: true)
				println("Email Successfully Sent to: "+ email.toAddress)
			}
			catch(MailException ex) {
				LOGGER.error(ex.printStackTrace())
				println("Unable to send email at this moment: "+ DateUtils.getCurrentDate())
			}
		}
    }

}
