package com.bayalpatra.commons

import grails.transaction.Transactional
import groovy.sql.Sql

import java.sql.SQLException

@Transactional
class DispatchSalaryService {

    def dataSource

    public void populateSalaryReport(){
        Sql sql = new Sql(dataSource)
        try{
            sql.call("call sp_populate_salary_report()")
        }catch (SQLException ex){
            println("error: "+ex.printStackTrace())
        }

    }


}
