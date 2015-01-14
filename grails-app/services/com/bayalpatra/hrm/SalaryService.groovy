package com.bayalpatra.hrm

import grails.transaction.Transactional
import commons.BayalpatraConstants
import groovy.sql.Sql
import org.apache.log4j.Logger

@Transactional
class SalaryService {
    def dataSource
    private static final Logger LOGGER = Logger.getLogger(SalaryService)

    def getSalaryList(designation,fromSalary,toSalary,params){
        params.sort = 'designation.jobTitleName'
        params.order = 'asc'
        def salaryInstance
        if(designation && fromSalary && toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation) and s.basicSalary between :fromSalary and :toSalary ",[designation: designation,fromSalary:fromSalary,toSalary:toSalary],params)

        }else if(designation && fromSalary ){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation) and s.basicSalary >= :fromSalary ",[designation: designation,fromSalary:fromSalary],params)

        }else if(designation && toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation) and s.basicSalary <= :toSalary ",[designation: designation,toSalary:toSalary],params)

        }else if(fromSalary && toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.basicSalary between :fromSalary and :toSalary ",[fromSalary:fromSalary,toSalary:toSalary],params)

        }else if(fromSalary){
            salaryInstance=Salary.findAll("from Salary s where s.basicSalary >= :fromSalary ",[fromSalary:fromSalary],params)

        }else if(toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.basicSalary <= :toSalary ",[toSalary:toSalary],params)

        }else if(designation){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation)",[designation:designation],params)
        }else{
            salaryInstance=Salary.list(params)

        }

        return salaryInstance

    }

    def getSalaryListCount(designation,fromSalary,toSalary){

        def salaryInstance
        def designationInstance

        if(designation){
            designationInstance=Designation.findAll("from Designation  d where d.jobTitleName like '"+designation+"%'")
        }


        if(designation && fromSalary && toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation) and s.basicSalary between :fromSalary and :toSalary ",[designation: designation,fromSalary:fromSalary,toSalary:toSalary])

        }else if(designation && fromSalary ){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation) and s.basicSalary >= :fromSalary ",[designation: designation,fromSalary:fromSalary])

        }else if(designation && toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation) and s.basicSalary <= :toSalary ",[designation: designation,toSalary:toSalary])

        }else if(fromSalary && toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.basicSalary between :fromSalary and :toSalary ",[fromSalary:fromSalary,toSalary:toSalary])

        }else if(fromSalary){
            salaryInstance=Salary.findAll("from Salary s where s.basicSalary >= :fromSalary ",[fromSalary:fromSalary])

        }else if(toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.basicSalary <= :toSalary ",[toSalary:toSalary])

        }else if(designation){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation)",[designation:designation])
        }else{
            salaryInstance=Salary.list()

        }

        return salaryInstance

    }

    def getSalaryNotSetList(params,max,offset,sort,order){

        params.max=max
        params.offset=Integer.valueOf(offset)
        def salNotSetList

        salNotSetList = Designation.findAll("FROM Designation d where d.id NOT IN (SELECT s.designation.id FROM Salary s) AND d.id IN (SELECT DISTINCT(e.designation.id) from Employee e) order by ${sort} ${order}",[max:params.max, offset:params.offset])

        return salNotSetList;

    }

    def getCountSalaryNotSetList(){

        def count = Designation.executeQuery("SELECT COUNT(*) FROM Designation d where d.id NOT IN (SELECT s.designation.id FROM Salary s) AND d.id IN (SELECT DISTINCT(e.designation.id) from Employee e)")
        return count[0]

    }

    def populateSalMonth(){
        def salMonthList = SalaryReport.executeQuery("SELECT DISTINCT(SUBSTRING(s.salaryMonth,1,7)) as salaryMonth from SalaryReport s order by s.salaryMonth asc")
        return salMonthList
    }

    def getNonTerminatedNonClearedEmployee(){
        def employeeList = Employee.findAll("FROM Employee e WHERE e.status not in (:status) order by e.firstName",[status:[BayalpatraConstants.TERMINATED,BayalpatraConstants.CLEARED]])
        return employeeList
    }

    def getSalary(params){
        def max = Integer.valueOf(params.max)
        def offset = Integer.valueOf(params.offset)
        def salaryReportList = SalaryReport.findAll("from SalaryReport s where s.employee.status not in (:status) order by s.salaryMonth desc,s.employee.firstName asc ",[status: [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ],max:max,offset:offset])
        return salaryReportList
    }

    def getAllSalaryCount(){
        def count = SalaryReport.executeQuery("select count(*) from SalaryReport s where s.employee.status not in (:status)",[status: [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]])
        return  count[0]
    }

    def getSalaryTotal(){

        def total = SalaryReport.executeQuery("SELECT SUM(s.total) from SalaryReport s where s.employee.status not in (:status) ",[status: [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]])

        def totalStr

        if(total.get(0)!=null){
            totalStr = BigDecimal.valueOf(total.get(0)).toPlainString()
            totalStr = totalStr.substring(0,totalStr.length()-3)
        }

        return totalStr;
    }

    def getSalaryA(){

        def sql=new Sql(dataSource)

        try{
            return sql.rows("select e.first_name, e.last_name, e.employee_id, b.basic_salary, s.pf, s.cit, s.tax, s.total, e.join_date, s.salary_date, s.salary_month, h.account_number from salary_report s left outer join hrm_employee_account h on s.employee_id=h.employee_id join employee e on s.employee_id=e.id join salary b on e.designation_id = b.designation_id where e.status not in ('Terminated', 'Cleared') order by s.salary_month desc, e.first_name asc;");
        }catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage())
        }
        sql.close();
    }

    def getSalaryByEmp(employee,params){
        def salaryReportList = SalaryReport.findAll("from SalaryReport s where s.employee=:employee and s.employee.status not in (:status) order by s.salaryMonth desc",[employee:employee,status: [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]],params)
        return salaryReportList
    }

    def getCountByEmp(employee){
        def count =SalaryReport.executeQuery("select count(*) from SalaryReport s where s.employee=:employee and s.employee.status not in (:status)",[employee:employee, status: [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]])
        return  count[0]
    }

    def getTotalSalaryByEmp(emp){

        def total = SalaryReport.executeQuery("SELECT sum(s.total) from SalaryReport s where s.employee=:employee and s.employee.status not in (:status)",[employee:emp,status: [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]])

        return total.get(0) as String
    }

    def getSalaryByEmpA(employee){

        def sql=new Sql(dataSource)

        try{
            return sql.rows("select e.first_name, e.last_name, e.employee_id, b.basic_salary, s.pf, s.cit, s.tax, s.total, e.join_date, s.salary_date, s.salary_month, h.account_number from salary_report s left outer join hrm_employee_account h on s.employee_id=h.employee_id join employee e on s.employee_id=e.id join salary b on e.designation_id = b.designation_id where e.status not in ('Terminated', 'Cleared') and s.employee_id='"+employee.id+"'order by s.salary_month desc, e.first_name asc");
        }catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage())
        }
        sql.close();
    }

    def getSalaryByMonth(salaryMonth,params){
        def salaryReportList = SalaryReport.findAll("from SalaryReport s where SUBSTRING(s.salaryMonth,1,7)=:salaryMonth and s.employee.status not in (:status) order by s.employee.firstName asc",[salaryMonth:salaryMonth, status: [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]],params)
        return salaryReportList
    }

    def getCountByMonth(salaryMonth){
        def count =SalaryReport.executeQuery("select count(*) from SalaryReport s where SUBSTRING(s.salaryMonth,1,7)=:salaryMonth and s.employee.status not in (:status)",[salaryMonth:salaryMonth,status: [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]])
        return  count[0]
    }

    def getTotalSalaryByMonth(salaryMonth){

        def total = SalaryReport.executeQuery("SELECT sum(s.total) from SalaryReport s where SUBSTRING(s.salaryMonth,1,7)=:salaryMonth and s.employee.status not in (:status)",[salaryMonth:salaryMonth, status: [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]])

        return total.get(0) as String

    }

    def getSalaryByStatusAndMonthReport(salMonth){
        def sql=new Sql(dataSource)

        try{
            return sql.rows("select e.first_name, e.last_name, e.employee_id, b.basic_salary, s.pf, s.cit, s.tax, s.total, e.join_date, s.salary_date, s.salary_month, h.account_number from salary_report s left outer join hrm_employee_account h on s.employee_id=h.employee_id join employee e on s.employee_id=e.id join salary b on e.designation_id = b.designation_id where e.status not in ('Terminated', 'Cleared') and SUBSTRING( s.salary_month, 1, 7 ) = '"+salMonth+"'order by s.salary_month desc, e.first_name asc");
        }catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage())
        }
        sql.close();

    }

    def getSalaryByEmpAndMonth(employee,salaryMonth,params) {
        def salaryReportList = SalaryReport.findAll("from SalaryReport s where SUBSTRING(s.salaryMonth,1,7)=:salaryMonth and s.employee=:employee and s.employee.status not in (:status) order by s.salaryMonth desc,s.employee.firstName asc",[employee:employee,salaryMonth:salaryMonth,status: [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]],params)
        return salaryReportList
    }

    def getCountByEmpAndMonth(employee,salaryMonth){
        def count =SalaryReport.executeQuery("select count(*) from SalaryReport s where SUBSTRING(s.salaryMonth,1,7)=:salaryMonth and s.employee=:employee and s.employee.status not in (:status)",[salaryMonth:salaryMonth,employee:employee,status: [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]])
        return  count[0]
    }

    def getTotalSalaryByEmpAndMonth(employee, salaryMonth){
        def total = SalaryReport.executeQuery("SELECT sum(s.total) from SalaryReport s where SUBSTRING(s.salaryMonth,1,7)=:salaryMonth and s.employee=:employee and s.employee.status not in (:status)",[employee:employee,salaryMonth:salaryMonth,status: [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]])

        return total.get(0) as String
    }

    def getSalaryByEmpAndMonthA(employee,salaryMonth) {

        def sql=new Sql(dataSource)

        try{
            return sql.rows("select e.first_name, e.last_name, e.employee_id, b.basic_salary, s.pf, s.cit, s.tax, s.total, e.join_date, s.salary_date, s.salary_month, h.account_number from salary_report s left outer join hrm_employee_account h on s.employee_id=h.employee_id join employee e on s.employee_id=e.id join salary b on e.designation_id = b.designation_id where e.status not in ('Terminated', 'Cleared') and s.employee_id='"+employee.id+"' and SUBSTRING( s.salary_month, 1, 7 ) = '"+salaryMonth+"'order by s.salary_month desc, e.first_name asc");
        }catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage())
        }
        sql.close();

    }

    def getSalaryFromEmpProfile(employee){

        def sql=new Sql(dataSource)

        try{
            return sql.rows("select e.first_name, e.last_name, e.employee_id, b.basic_salary, s.pf, s.cit, s.tax, s.total,s.salary_month, h.account_number from salary_report s left outer join hrm_employee_account h on s.employee_id=h.employee_id join employee e on s.employee_id=e.id join salary b on e.designation_id = b.designation_id where e.status not in ('Terminated', 'Cleared') and s.employee_id='"+employee.id+"'order by s.salary_month desc, e.first_name asc");
        }catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage())
        }
        sql.close();
    }

}