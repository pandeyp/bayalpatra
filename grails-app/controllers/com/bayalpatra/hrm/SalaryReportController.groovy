package com.bayalpatra.hrm


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SalaryReportController {
    def salaryService
    def exportService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def list = {

        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        def offset = request.getParameter("offset") ?:'0';
        params.offset = offset

        def salaryReportInstanceList
        def model
        boolean isEmp=false
        def salMonthList = salaryService.populateSalMonth()
        def count
        def employee
        def salaryTotal

        // list of non-terminated and non-Cleared employees
        def employeeList = salaryService.getNonTerminatedNonClearedEmployee()


        if(params.emp && params.salaryMonth){

            employee = Employee.findById(params.emp);
            salaryReportInstanceList = salaryService.getSalaryByEmpAndMonth(employee,params.salaryMonth,params)
            count = salaryService.getCountByEmpAndMonth(employee,params.salaryMonth)
            salaryTotal = salaryService.getTotalSalaryByEmpAndMonth(employee, params.salaryMonth)

            if(params?.exportFormat && params.exportFormat != "html"){
                response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report.${params.extension}")

                def  reqListForExport = salaryService.getSalaryByEmpAndMonthA(employee, params.salaryMonth)

                List fields = [
                        "empName",
                        "account_number",
                        "basic_salary",
                        "pf",
                        "cit",
                        "tax",
                        "total",
                        "join_date",
                        "salary_date",
                        "salary_month"
                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.last_name + " " + domain.employee_id
                }


                Map labels = ["empName":"Employee","account_number":"Account Number","basic_salary":"Basic Salary",
                              "pf":"Total PF","cit":"CIT","tax":"Tax","total":"Total","join_date":"Join Date","salary_date":"Salary Date","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }


                Map formatter = [salary_month: fDate,salary_date: fDate,join_date: fDate,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20

                ]]

                exportService.export(params.exportFormat, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }

        }

        else if(params.emp){

            params.max = Math.min(params.max ? params.int('max') : 30, 100)
            employee = Employee.findById(params.emp)
            salaryReportInstanceList = salaryService.getSalaryByEmp(employee,params)
            count = salaryService.getCountByEmp(employee)
            salaryTotal = salaryService.getTotalSalaryByEmp(employee)


            if(params?.exportFormat && params.exportFormat != "html"){
                response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report.${params.extension}")

                def  reqListForExport = salaryService.getSalaryByEmpA(employee)

                List fields = [
                        "empName",
                        "account_number",
                        "basic_salary",
                        "pf",
                        "cit",
                        "tax",
                        "total",
                        "join_date",
                        "salary_date",
                        "salary_month"
                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.last_name + " " + domain.employee_id
                }


                Map labels = ["empName":"Employee","account_number":"Account Number","basic_salary":"Basic Salary",
                              "pf":"Total PF","cit":"CIT","tax":"Tax","total":"Total","join_date":"Join Date","salary_date":"Salary Date","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }


                Map formatter = [salary_month: fDate,salary_date: fDate,join_date: fDate,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20

                ]]

                exportService.export(params.exportFormat, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }
        }
        else if(params.salaryMonth){

            salaryReportInstanceList = salaryService.getSalaryByMonth(params.salaryMonth,params)
            count = salaryService.getCountByMonth(params.salaryMonth)
            salaryTotal = salaryService.getTotalSalaryByMonth(params.salaryMonth)

            if(params?.exportFormat && params.exportFormat != "html"){
                response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report.${params.extension}")

                def  reqListForExport = salaryService.getSalaryByStatusAndMonthReport(params.salaryMonth)

                List fields = [
                        "empName",
                        "account_number",
                        "basic_salary",
                        "pf",
                        "cit",
                        "tax",
                        "total",
                        "join_date",
                        "salary_date",
                        "salary_month"
                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.last_name + " " + domain.employee_id
                }


                Map labels = ["empName":"Employee","account_number":"Account Number","basic_salary":"Basic Salary",
                              "pf":"Total PF","cit":"CIT","tax":"Tax","total":"Total","join_date":"Join Date","salary_date":"Salary Date","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }


                Map formatter = [salary_month: fDate,salary_date: fDate,join_date: fDate,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20

                ]]

                exportService.export(params.exportFormat, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }
        }
        else if(params.employeeIs){
            Employee employ= Employee.findById(params.employeeIs)
            salaryReportInstanceList = SalaryReport.findAllByEmployee(employ)
            count =salaryReportInstanceList.size()
            salaryTotal=null
            isEmp=true

            if(params?.exportFormat && params.exportFormat != "html"){

                response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report.${params.extension}")
                def  reqListForExport = salaryService.getSalaryFromEmpProfile(employ)

                List fields = [
                        "salary_month",
                        "empName",
                        "basic_salary",
                        "pf",
                        "cit",
                        "tax",
                        "total",
                        "account_number",

                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.last_name + " " + domain.employee_id
                }


                Map labels = ["empName":"Employee","account_number":"Account Number","basic_salary":"Basic Salary",
                              "pf":"Total PF","cit":"CIT","tax":"Tax","total":"Total","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }


                Map formatter = [salary_month: fDate,join_date: fDate,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20

                ]]

                exportService.export(params.exportFormat, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }

            return [salaryReportInstanceList: salaryReportInstanceList, salaryReportInstanceTotal: count, isEmp:isEmp,employeeInstance:employ,salaryMonth:params.salaryMonth,employee:params.emp,salaryTotal: salaryTotal]
        }
        else{

            salaryReportInstanceList=salaryService.getSalary(params)
            count = salaryService.getAllSalaryCount()
            salaryTotal = salaryService.getSalaryTotal()

            if(params?.exportFormat && params.exportFormat != "html"){

                response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report_By_Department.${params.extension}")
                def  reqListForExport = salaryService.getSalaryA()

                List fields = [
                        "empName",
                        "account_number",
                        "basic_salary",
                        "pf",
                        "cit",
                        "tax",
                        "total",
                        "join_date",
                        "salary_date",
                        "salary_month"
                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.last_name + " " + domain.employee_id
                }


                Map labels = ["empName":"Employee","account_number":"Account Number","basic_salary":"Basic Salary",
                              "pf":"Total PF","cit":"CIT","tax":"Tax","total":"Total","join_date":"Join Date","salary_date":"Salary Date","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }


                Map formatter = [salary_month: fDate,salary_date: fDate,join_date: fDate,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20

                ]]

                exportService.export(params.exportFormat, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }

        }

        [salaryReportInstanceList: salaryReportInstanceList, salaryReportInstanceTotal: count,salMonthList:salMonthList,salaryMonth:params.salaryMonth,employee:params.emp,isEmp:isEmp,employeeInstance:employee,employeeList:employeeList,salaryClass:params.salaryClass, salaryTotal: salaryTotal]
    }

    def ajaxCall = {

        def salaryReportInstanceList
        def model
        def count
        def salMonthList = salaryService.populateSalMonth()
        def employeeList = salaryService.getNonTerminatedNonClearedEmployee()
        def salaryTotal


        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        def offset = request.getParameter("offset") ?:'0';
        params.offset = offset

        if(params.emp && params.salaryMonth){
            def employee = Employee.findById(params.emp);
            salaryReportInstanceList = salaryService.getSalaryByEmpAndMonth(employee,params.salaryMonth,params)
            count = salaryService.getCountByEmpAndMonth(employee,params.salaryMonth)
            salaryTotal = salaryService.getTotalSalaryByEmpAndMonth(employee, params.salaryMonth)

            if(params?.exportFormat && params.exportFormat != "html"){
                response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report.${params.extension}")

                def  reqListForExport = salaryService.getSalaryByEmpAndMonthA(employee, params.salaryMonth)

                List fields = [
                        "empName",
                        "account_number",
                        "basic_salary",
                        "pf",
                        "cit",
                        "tax",
                        "total",
                        "join_date",
                        "salary_date",
                        "salary_month"
                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.last_name + " " + domain.employee_id
                }


                Map labels = ["empName":"Employee","account_number":"Account Number","basic_salary":"Basic Salary",
                              "pf":"Total PF","cit":"CIT","tax":"Tax","total":"Total","join_date":"Join Date","salary_date":"Salary Date","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }


                Map formatter = [salary_month: fDate,salary_date: fDate,join_date: fDate,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20

                ]]

                exportService.export(params.exportFormat, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }

        }
        else if(params.emp){
            params.max = Math.min(params.max ? params.int('max') : 30, 100)
            def employee = Employee.findById(params.emp)
            salaryReportInstanceList = salaryService.getSalaryByEmp(employee,params)
            count = salaryService.getCountByEmp(employee)
            salaryTotal = salaryService.getTotalSalaryByEmp(employee)


            if(params?.exportFormat && params.exportFormat != "html"){
                response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report.${params.extension}")

                def  reqListForExport = salaryService.getSalaryByEmpA(employee)

                List fields = [
                        "empName",
                        "account_number",
                        "basic_salary",
                        "pf",
                        "cit",
                        "tax",
                        "total",
                        "join_date",
                        "salary_date",
                        "salary_month"
                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.last_name + " " + domain.employee_id
                }


                Map labels = ["empName":"Employee","account_number":"Account Number","basic_salary":"Basic Salary",
                              "pf":"Total PF","cit":"CIT","tax":"Tax","total":"Total","join_date":"Join Date","salary_date":"Salary Date","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }


                Map formatter = [salary_month: fDate,salary_date: fDate,join_date: fDate,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20

                ]]

                exportService.export(params.exportFormat, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }
        }
        else if(params.salaryMonth){

            salaryReportInstanceList = salaryService.getSalaryByMonth(params.salaryMonth,params)
            count = salaryService.getCountByMonth(params.salaryMonth)
            salaryTotal = salaryService.getTotalSalaryByMonth(params.salaryMonth)

            if(params?.exportFormat && params.exportFormat != "html"){
                response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report.${params.extension}")

                def  reqListForExport = salaryService.getSalaryByStatusAndMonthReport(params.salaryMonth)

                List fields = [
                        "empName",
                        "account_number",
                        "basic_salary",
                        "pf",
                        "cit",
                        "tax",
                        "total",
                        "join_date",
                        "salary_date",
                        "salary_month"
                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.last_name + " " + domain.employee_id
                }


                Map labels = ["empName":"Employee","account_number":"Account Number","basic_salary":"Basic Salary",
                              "pf":"Total PF","cit":"CIT","tax":"Tax","total":"Total","join_date":"Join Date","salary_date":"Salary Date","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }


                Map formatter = [salary_month: fDate,salary_date: fDate,join_date: fDate,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20

                ]]

                exportService.export(params.exportFormat, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }

        }
        else {
            salaryReportInstanceList=salaryService.getSalary(params)
            count = salaryService.getAllSalaryCount()
            salaryTotal = salaryService.getSalaryTotal()

            if(params?.exportFormat && params.exportFormat != "html"){

                response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report_By_Department.${params.extension}")
                def  reqListForExport = salaryService.getSalaryA()

                List fields = [
                        "empName",
                        "account_number",
                        "basic_salary",
                        "pf",
                        "cit",
                        "tax",
                        "total",
                        "join_date",
                        "salary_date",
                        "salary_month"
                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.last_name + " " + domain.employee_id
                }

                Map labels = ["empName":"Employee","account_number":"Account Number","basic_salary":"Basic Salary",
                              "pf":"Total PF","cit":"CIT","tax":"Tax","total":"Total","join_date":"Join Date","salary_date":"Salary Date","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }

                Map formatter = [salary_month: fDate,salary_date: fDate,join_date: fDate,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20
                ]]

                exportService.export(params.exportFormat, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }
        }

        render(template: "ajaxFilteredSalaryList", model:[salaryReportInstanceList: salaryReportInstanceList,salaryReportInstanceTotal: count,salMonthList:salMonthList, salaryMonth:params.salaryMonth,employee:params.emp,salaryClass:params.salaryClass,offset:params.offset,max:params.max,employeeList:employeeList,salaryTotal:salaryTotal])
    }
}
