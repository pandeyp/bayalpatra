package com.bayalpatra.commons



class SendEmailJob {

    def bayalpatraEmailService

    static triggers = {
      //simple repeatInterval: 5000l // execute job once in 5 seconds
        //Fire after every 5 mins
       // cron name: 'sendEmailJob', cronExpression: "0 0/10 * * * ?"
        //"0 15 10 L * ?"	 	Fire at 10:15am on the last day of every month
        cron name: 'sendEmailJob', cronExpression: "0 0/59 * * * ?"    //each 59 minutes
    }

    def execute() {
        bayalpatraEmailService.processEmail()
        println "Email sending job run successfully on ${new Date()}";
    }
}
