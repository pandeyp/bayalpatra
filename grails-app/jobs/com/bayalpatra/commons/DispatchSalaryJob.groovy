package com.bayalpatra.commons


class DispatchSalaryJob {
    def dispatchSalaryService

    static triggers = {
        //simple repeatInterval: 5000l // execute job once in 5 seconds
        //Fire after every 5 mins
        // cron name: 'sendEmailJob', cronExpression: "0 0/10 * * * ?"
        //"0 15 10 L * ?"	 	Fire at 10:15am on the last day of every month
        //cron name: 'dispatchSalary', cronExpression: "0 0/1 * * * ?"    //each 120 minutes
        cron name: 'dispatchSalary', cronExpression: "0 0/120 * * * ?"    //each 120 minutes
    }

    def execute() {
        dispatchSalaryService.populateSalaryReport()
        println "Salary dispatch job run successfully on ${new Date()}";
    }
}
