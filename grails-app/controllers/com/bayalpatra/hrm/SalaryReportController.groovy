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


        if(params.emp && params.salaryMonth && params.salaryClass){
            employee = Employee.findById(params.emp);
            salaryReportInstanceList = salaryReportService.getSalaryByEmpAndMonthAndClass(employee,params.salaryMonth,params.salaryClass,params)
            count = salaryReportService.getCountByEmpAndMonthAndClass(employee,params.salaryMonth,params.salaryClass)
            salaryTotal = salaryReportService.getTotalSalaryByEmpAndMonth(employee, params.salaryMonth)

            if(params?.format && params.format != "html"){
                response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report.${params.extension}")

                def  reqListForExport = salaryReportService.getSalaryByEmpAndMonthAndClassA(employee, params.salaryMonth, params.salaryClass)

                List fields = [
                        "empName",
                        "account_number",
                        "identifier",
                        "start_salary",
                        "additional_salary",
                        "basic_salary",
                        "grade",
                        "dashain_exgratia",
                        "seniority_allowance",
                        "other_allowance",
                        "extra_allowance",
                        "dearness_allowance",
                        "gratuty",
                        "emolu_pf",
                        "emolument",
                        "pf",
                        "cit",
                        "insurance_deduction",
                        "social_security_tax",
                        "tax",
                        "total",
                        "unpaid_days_salary",
                        "join_date",
                        "salary_date",
                        "salary_month"
                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.middle_name +" " + domain.last_name + " " + domain.employee_id
                }

                Map labels = ["empName":"Employee","account_number":"Account Number","identifier":"Salary Class", "start_salary":"Start Salary", "additional_salary":"Additional Salary","basic_salary":"Basic Salary", "grade":"Grade", "dashain_exgratia":" DashainExgratia",  "seniority_allowance": "Seniority Allowance",
                              "other_allowance":"Other Allowance","extra_allowance":"Extra Allowance","dearness_allowance":"Dearness Allowance","gratuty":"Gratuity","emolu_pf":"PF","emolument":"Emolument","pf":"Total PF","cit":"CIT","insurance_deduction":"Insurance Deduction","social_security_tax":"Social Security Tax","tax":"Tax","total":"Total","unpaid_days_salary":"Less Salary(Unpaid Leave)","join_date":"Join Date","salary_date":"Salary Date","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }

                def sstAmount = {
                    domain,value ->
                        if(domain.social_security_tax){
                            def taxAmount = domain.tax - domain.social_security_tax
                            return  taxAmount
                        }
                        else{
                            return domain.tax
                        }
                }
                Map formatter = [salary_month: fDate,salary_date: fDate,join_date: fDate,tax:sstAmount,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20
                ]]

                exportService.export(params.format, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }

        }
        else if(params.emp && params.salaryMonth){

            employee = Employee.findById(params.emp);
            salaryReportInstanceList = salaryReportService.getSalaryByEmpAndMonth(employee,params.salaryMonth,params)
            count = salaryReportService.getCountByEmpAndMonth(employee,params.salaryMonth)
            salaryTotal = salaryReportService.getTotalSalaryByEmpAndMonth(employee, params.salaryMonth)

            if(params?.format && params.format != "html"){
                response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report.${params.extension}")

                def  reqListForExport = salaryReportService.getSalaryByEmpAndMonthA(employee, params.salaryMonth)

                List fields = [
                        "empName",
                        "account_number",
                        "identifier",
                        "start_salary",
                        "additional_salary",
                        "basic_salary",
                        "grade",
                        "dashain_exgratia",
                        "seniority_allowance",
                        "other_allowance",
                        "extra_allowance",
                        "dearness_allowance",
                        "gratuty",
                        "emolu_pf",
                        "emolument",
                        "pf",
                        "cit",
                        "insurance_deduction",
                        "social_security_tax",
                        "tax",
                        "total",
                        "unpaid_days_salary",
                        "join_date",
                        "salary_date",
                        "salary_month"
                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.middle_name +" " + domain.last_name + " " + domain.employee_id
                }

                Map labels = ["empName":"Employee","account_number":"Account Number","identifier":"Salary Class", "start_salary":"Start Salary", "additional_salary":"Additional Salary","basic_salary":"Basic Salary", "grade":"Grade", "dashain_exgratia":" DashainExgratia",  "seniority_allowance": "Seniority Allowance",
                              "other_allowance":"Other Allowance","extra_allowance":"Extra Allowance","dearness_allowance":"Dearness Allowance","gratuty":"Gratuity","emolu_pf":"PF","emolument":"Emolument","pf":"Total PF","cit":"CIT","insurance_deduction":"Insurance Deduction","social_security_tax":"Social Security Tax","tax":"Tax","total":"Total","unpaid_days_salary":"Less Salary(Unpaid Leave)","join_date":"Join Date","salary_date":"Salary Date","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }

                def sstAmount = {
                    domain,value ->
                        if(domain.social_security_tax){
                            def taxAmount = domain.tax - domain.social_security_tax
                            return  taxAmount
                        }
                        else{
                            return domain.tax
                        }
                }
                Map formatter = [salary_month: fDate,salary_date: fDate,join_date: fDate,tax:sstAmount,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20
                ]]

                exportService.export(params.format, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }
        }
        else if(params.salaryClass && params.salaryMonth){
            salaryReportInstanceList = salaryReportService.getSalaryByClassAndMonth(params.salaryClass,params.salaryMonth,params)
            count = salaryReportService.getCountBySalaryClassAndMonth(params.salaryClass,params.salaryMonth)
            salaryTotal = salaryReportService.getTotalSalaryByClassAndMonth(params.salaryClass,params.salaryMonth)

            if(params?.format && params.format != "html"){

                response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report_By_Department.${params.extension}")
                def  reqListForExport = salaryReportService.getSalaryByStatusMonthAndClassReport(params.salaryMonth, params.salaryClass)

                List fields = [
                        "empName",
                        "account_number",
                        "identifier",
                        "start_salary",
                        "additional_salary",
                        "basic_salary",
                        "grade",
                        "dashain_exgratia",
                        "seniority_allowance",
                        "other_allowance",
                        "extra_allowance",
                        "dearness_allowance",
                        "gratuty",
                        "emolu_pf",
                        "emolument",
                        "pf",
                        "cit",
                        "insurance_deduction",
                        "social_security_tax",
                        "tax",
                        "total",
                        "unpaid_days_salary",
                        "join_date",
                        "salary_date",
                        "salary_month"
                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.middle_name +" " + domain.last_name + " " + domain.employee_id
                }

                Map labels = ["empName":"Employee","account_number":"Account Number","identifier":"Salary Class", "start_salary":"Start Salary", "additional_salary":"Additional Salary","basic_salary":"Basic Salary", "grade":"Grade", "dashain_exgratia":" DashainExgratia",  "seniority_allowance": "Seniority Allowance",
                              "other_allowance":"Other Allowance","extra_allowance":"Extra Allowance","dearness_allowance":"Dearness Allowance","gratuty":"Gratuity","emolu_pf":"PF","emolument":"Emolument","pf":"Total PF","cit":"CIT","insurance_deduction":"Insurance Deduction","social_security_tax":"Social Security Tax","tax":"Tax","total":"Total","unpaid_days_salary":"Less Salary(Unpaid Leave)","join_date":"Join Date","salary_date":"Salary Date","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }

                def sstAmount = {
                    domain,value ->
                        if(domain.social_security_tax){
                            def taxAmount = domain.tax - domain.social_security_tax
                            return  taxAmount
                        }
                        else{
                            return domain.tax
                        }
                }
                Map formatter = [salary_month: fDate,salary_date: fDate,join_date: fDate,tax:sstAmount,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20
                ]]

                exportService.export(params.format, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }
        }
        // condition if params.emp and params.salaryClass is ignored here but their function still exists in the service.
        else if(params.emp){

            employee = Employee.findById(params.emp)
            salaryReportInstanceList = salaryReportService.getSalaryByEmp(employee,params)
            count = salaryReportService.getCountByEmp(employee)
            salaryTotal = salaryReportService.getTotalSalaryByEmp(employee)

            if(params?.format && params.format != "html"){
                response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report.${params.extension}")

                def  reqListForExport = salaryReportService.getSalaryByEmpA(employee)

                List fields = [
                        "empName",
                        "account_number",
                        "identifier",
                        "start_salary",
                        "additional_salary",
                        "basic_salary",
                        "grade",
                        "dashain_exgratia",
                        "seniority_allowance",
                        "other_allowance",
                        "extra_allowance",
                        "dearness_allowance",
                        "gratuty",
                        "emolu_pf",
                        "emolument",
                        "pf",
                        "cit",
                        "insurance_deduction",
                        "social_security_tax",
                        "tax",
                        "total",
                        "unpaid_days_salary",
                        "join_date",
                        "salary_date",
                        "salary_month"
                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.middle_name +" " + domain.last_name + " " + domain.employee_id
                }

                Map labels = ["empName":"Employee","account_number":"Account Number","identifier":"Salary Class", "start_salary":"Start Salary", "additional_salary":"Additional Salary","basic_salary":"Basic Salary", "grade":"Grade", "dashain_exgratia":" DashainExgratia",  "seniority_allowance": "Seniority Allowance",
                              "other_allowance":"Other Allowance","extra_allowance":"Extra Allowance","dearness_allowance":"Dearness Allowance","gratuty":"Gratuity","emolu_pf":"PF","emolument":"Emolument","pf":"Total PF","cit":"CIT","insurance_deduction":"Insurance Deduction","social_security_tax":"Social Security Tax","tax":"Tax","total":"Total","unpaid_days_salary":"Less Salary(Unpaid Leave)","join_date":"Join Date","salary_date":"Salary Date","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }

                def sstAmount = {
                    domain,value ->
                        if(domain.social_security_tax){
                            def taxAmount = domain.tax - domain.social_security_tax
                            return  taxAmount
                        }
                        else{
                            return domain.tax
                        }
                }
                Map formatter = [salary_month: fDate,salary_date: fDate,join_date: fDate,tax:sstAmount,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20
                ]]

                exportService.export(params.format, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }

        }
        else if(params.salaryMonth){

            salaryReportInstanceList = salaryReportService.getSalaryByMonth(params.salaryMonth,params)
            count = salaryReportService.getCountByMonth(params.salaryMonth)
            salaryTotal = salaryReportService.getTotalSalaryByMonth(params.salaryMonth)

            if(params?.format && params.format != "html"){
                response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report.${params.extension}")

                List fields = [
                        "employee",
                        "salaryClass",
                        "startSalary",
                        "additionalSalary",
                        "basicSalary",
                        "grade",
                        "dashainExgratia",
                        "seniorityAllowance",
                        "otherAllowance",
                        "extraAllowance",
                        "dearnessAllowance",
                        "gratuty",
                        "emoluPf",
                        "emolument",
                        "pf",
                        "cit",
                        "insurance_deduction",
                        "socialSecurityTax",
                        "tax",
                        "total",
                        "unpaidDaysSalary",
                        "employee.joinDate",
                        "salaryDate",
                        "salaryMonth"
                ]
                Map labels = ["employee":"Employee","salaryClass":"Salary Class", "startSalary":"Start Salary", "additionalSalary":"Additional Salary","basicSalary":"Basic Salary", "grade":"Grade", "dashainExgratia":" DashainExgratia",  "seniorityAllowance": "Seniority Allowance",
                              "otherAllowance":"Other Allowance","extraAllowance":"Extra Allowance","dearnessAllowance":"Dearness Allowance","gratuty":"Gratuity","emoluPf":"Pf","emolument":"Emolument","pf":"Total PF","cit":"Cit","insurance_deduction":"Insurance Deduction","socialSecurityTax":"Social Security Tax","tax":"Tax","total":"Total","unpaidDaysSalary":"Less Salary(Unpaid Leave)","employee.joinDate":"Join Date","salaryDate":"Salary Date","salaryMonth":"Salary Month"]
                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }
                def sstAmount = {
                    domain,value ->
                        if(domain.socialSecurityTax){
                            def taxAmount = domain.tax - domain.socialSecurityTax
                            return  taxAmount
                        }
                        else{
                            return domain.tax
                        }
                }
                Map formatter = [salaryMonth:fDate,salaryDate:fDate,"employee.joinDate":fDate,tax:sstAmount]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,0.15
                ]]
                exportService.export(params.format, response.outputStream, salaryReportService.getSalaryByMonthA(params.salaryMonth), fields, labels,formatter,parameters)
            }
        }
        else if(params.salaryClass){

            salaryReportInstanceList = salaryReportService.getSalaryByClass(params.salaryClass,params)
            count = salaryReportService.getCountByClass(params.salaryClass)
            salaryTotal = salaryReportService.getTotalSalaryByClass(params.salaryClass)

            if(params?.format && params.format != "html"){

                response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report_By_Department.${params.extension}")
                def  reqListForExport = salaryReportService.getSalaryByClassA(params.salaryClass)

                List fields = [
                        "empName",
                        "account_number",
                        "identifier",
                        "start_salary",
                        "additional_salary",
                        "basic_salary",
                        "grade",
                        "dashain_exgratia",
                        "seniority_allowance",
                        "other_allowance",
                        "extra_allowance",
                        "dearness_allowance",
                        "gratuty",
                        "emolu_pf",
                        "emolument",
                        "pf",
                        "cit",
                        "insurance_deduction",
                        "social_security_tax",
                        "tax",
                        "total",
                        "unpaid_days_salary",
                        "join_date",
                        "salary_date",
                        "salary_month"
                ]

                def getEmpFullName = {
                    domain, value ->
                        return domain.first_name +" "+ domain.middle_name +" " + domain.last_name + " " + domain.employee_id
                }

                Map labels = ["empName":"Employee","account_number":"Account Number","identifier":"Salary Class", "start_salary":"Start Salary", "additional_salary":"Additional Salary","basic_salary":"Basic Salary", "grade":"Grade", "dashain_exgratia":" DashainExgratia",  "seniority_allowance": "Seniority Allowance",
                              "other_allowance":"Other Allowance","extra_allowance":"Extra Allowance","dearness_allowance":"Dearness Allowance","gratuty":"Gratuity","emolu_pf":"PF","emolument":"Emolument","pf":"Total PF","cit":"CIT","insurance_deduction":"Insurance Deduction","social_security_tax":"Social Security Tax","tax":"Tax","total":"Total","unpaid_days_salary":"Less Salary(Unpaid Leave)","join_date":"Join Date","salary_date":"Salary Date","salary_month":"Salary Month"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }

                def sstAmount = {
                    domain,value ->
                        if(domain.social_security_tax){
                            def taxAmount = domain.tax - domain.social_security_tax
                            return  taxAmount
                        }
                        else{
                            return domain.tax
                        }
                }
                Map formatter = [salary_month: fDate,salary_date: fDate,join_date: fDate,tax:sstAmount,empName:getEmpFullName]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.15,
                        0.20,
                        0.20,
                        0.20
                ]]
                exportService.export(params.format, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }
        }
        else if(params.employeeIs){
            Employee employ= Employee.findById(params.employeeIs)
            salaryReportInstanceList = SalaryReport.findAllByEmployee(employ)
            count =salaryReportInstanceList.size()
            salaryTotal=null
            isEmp=true

            if(params?.format && params.format != "html"){

                response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
                response.setHeader("Content-disposition", "attachment; filename= Salary_Report.${params.extension}")
                List fields = [
                        "salaryMonth",
                        "startSalary",
                        "additionalSalary",
                        "basicSalary",
                        "grade",
                        "tax",
                        "total"


                ]
                Map labels = [ "salaryMonth":"Salary Month",
                               "startSalary":"Start Salary",
                               "additionalSalary":"Additional Salary",
                               "basicSalary":"basic Salary",
                               "grade":"Grade",
                               "tax":"Tax",
                               "total":"Total"]

                def fDate = {   domain, value ->

                    return value. format("yyyy-MM-dd")
                }
                def sstAmount = {
                    domain,value ->
                        if(domain.socialSecurityTax){
                            def taxAmount = domain.tax - domain.socialSecurityTax
                            return  taxAmount
                        }
                        else{
                            return domain.tax
                        }
                }
                Map formatter = [salaryMonth:fDate,salaryDate:fDate,"employee.joinDate":fDate,tax:sstAmount]
                Map parameters =["column.widths": [
                        0.15,
                        0.15,
                        0.15 ,
                        0.15 ,
                        0.15,
                        0.15,
                        0.15
                ]]
                exportService.export(params.format, response.outputStream, salaryReportInstanceList, fields, labels,formatter,parameters)
            }

            return [salaryReportInstanceList: salaryReportInstanceList, salaryReportInstanceTotal: count, isEmp:isEmp,employeeInstance:employ,salaryMonth:params.salaryMonth,employee:params.emp,salaryClass:params.salaryClass, salaryTotal: salaryTotal]
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

/*                def sstAmount = {
                    domain,value ->
                        if(domain.social_security_tax){
                            def taxAmount = domain.tax - domain.social_security_tax
                            return  taxAmount
                        }
                        else{
                            return domain.tax
                        }
                }*/
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

                exportService.export(params.format, response.outputStream, reqListForExport, fields, labels,formatter,parameters)
            }

        }

        [salaryReportInstanceList: salaryReportInstanceList, salaryReportInstanceTotal: count,salMonthList:salMonthList,salaryMonth:params.salaryMonth,employee:params.emp,isEmp:isEmp,employeeInstance:employee,employeeList:employeeList,salaryClass:params.salaryClass, salaryTotal: salaryTotal]           /* ,isAjax:isAjax*/

    }

/*    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond SalaryReport.list(params), model: [salaryReportInstanceCount: SalaryReport.count()]
    }

    def show(SalaryReport salaryReportInstance) {
        respond salaryReportInstance
    }

    def create() {
        respond new SalaryReport(params)
    }

    @Transactional
    def save(SalaryReport salaryReportInstance) {
        if (salaryReportInstance == null) {
            notFound()
            return
        }

        if (salaryReportInstance.hasErrors()) {
            respond salaryReportInstance.errors, view: 'create'
            return
        }

        salaryReportInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'salaryReport.label', default: 'SalaryReport'), salaryReportInstance.id])
                redirect salaryReportInstance
            }
            '*' { respond salaryReportInstance, [status: CREATED] }
        }
    }

    def edit(SalaryReport salaryReportInstance) {
        respond salaryReportInstance
    }

    @Transactional
    def update(SalaryReport salaryReportInstance) {
        if (salaryReportInstance == null) {
            notFound()
            return
        }

        if (salaryReportInstance.hasErrors()) {
            respond salaryReportInstance.errors, view: 'edit'
            return
        }

        salaryReportInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'SalaryReport.label', default: 'SalaryReport'), salaryReportInstance.id])
                redirect salaryReportInstance
            }
            '*' { respond salaryReportInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(SalaryReport salaryReportInstance) {

        if (salaryReportInstance == null) {
            notFound()
            return
        }

        salaryReportInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'SalaryReport.label', default: 'SalaryReport'), salaryReportInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'salaryReport.label', default: 'SalaryReport'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }*/
}
