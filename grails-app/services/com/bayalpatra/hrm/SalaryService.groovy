package com.bayalpatra.hrm

import commons.DateUtils
import grails.transaction.Transactional
import org.apache.log4j.Logger
@Transactional
class SalaryService {

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

/*

    */
/**
     * All methods within this service are to be made transactional.
     * All services have transaction demarcation enabled by default - to disable it, simply set the transactional property to false
     *//*

    static transactional = true
    //get the logger instance
    private static final Logger LOGGER = Logger.getLogger(SalaryService)
    def dataSource
    def maxDashainExergtia = TaxSetting.findAll()[0].dashainBonusCutOff
    def leaveService
    def sessionFactory
    */
/**
     * Service period count for Grade on the basis of date of appointment to date of Ashad 31st.
     * 365 days from appointment date, basic salary incerease by 1000 for the first two years
     * @param Join Date or promotion date for a particular employee
     * @param Basic salary
     * @param Integer gradeReward
     * @return Grade amount for a particular employee
     *//*

    def calculateGradeAmount(Employee emp, Double basicSalary, Integer gradeReward){
        Double gradeAmount = 0.0
        def updatedJoinDate = emp.updatedJoinDate
        def promotionDate = emp.promotionDate
        def joinDate = emp.joinDate
        int salaryMonth = DateUtils.getMonthFromDate(getSalaryMonth())
        int promotionMonth = DateUtils.getMonthFromDate(promotionDate)
        if(promotionMonth==salaryMonth){

            Calendar ca = Calendar.getInstance()
            ca.set(DateUtils.getYearFromDate(new Date()),DateUtils.getMonthFromDate(getSalaryMonth()),DateUtils.getDayFromDate(joinDate))
            ca.add(Calendar.DAY_OF_MONTH,1)
            Date secondHalfMonth = ca.getTime()
            def lastDayOfSalaryMonth=DateUtils.getLastDateOfAnyMonth(getSalaryMonth())
            int newGrade = calculateGrade(promotionDate,DateUtils.getLastDateOfAnyMonth(getSalaryMonth()),emp,gradeReward)
            int oldGrade = 0
            if(oldGrade!=newGrade){
                oldGrade = newGrade-1
            }

            int totalDayCount = DateUtils.getDayFromDate(DateUtils.getLastDateOfAnyMonth(getSalaryMonth()))
            int firstHalfDayCount = DateUtils.getDayFromDate(promotionDate)
            int secondHalfDayCount = totalDayCount-firstHalfDayCount
            Double aggGrade = (oldGrade*firstHalfDayCount+newGrade*secondHalfDayCount)/totalDayCount
            gradeAmount = (basicSalary)*(aggGrade)/60
        }else{
            int grade = calculateGrade(promotionDate,DateUtils.getLastDateOfAnyMonth(getSalaryMonth()),emp,gradeReward)
            if(grade != 0){
                gradeAmount = (basicSalary) * (grade)/60
            }
        }
        return gradeAmount
    }

    */
/**
     * Service period count for Grade on the basis of date of appointment to date of Ashad 31st.
     * 365 days from appointment date, basic salary increase by 1000 for the first two years
     * @return Grade for a particular employee
     *//*

    def calculateGrade(Date promotionDate,Date toDate, Employee emp, Integer gradeReward){
        int grade = 0
        Integer servicePeriod = DateUtils.getDaysFromTwoDates(promotionDate,toDate)-1
        def suspensionDays=0
        def suspensions=[]
        suspensions = SuspendedEmployeeDetails.findByEmployee(emp)
        suspensions.each{
            if(it.startDate > promotionDate){
                if(it.endDate) suspensionDays = suspensionDays + DateUtils.getDaysFromTwoDates(it.startDate,it.endDate)
                if(!it.endDate) suspensionDays = suspensionDays + DateUtils.getDaysFromTwoDates(it.startDate,DateUtils.getLastDateOfAnyMonth(getSalaryMonth()))
            }
        }
        servicePeriod = servicePeriod - suspensionDays

        if(servicePeriod > AnnapurnaConstants.ONEYEAR){
            grade = servicePeriod/AnnapurnaConstants.ONEYEAR
        }
        grade = grade + gradeReward

        return grade
    }


    */
/**
     * Eligible after 730 days from appointment date
     * @return PF amount for a particular employee
     *//*

    def calculatePF(Employee emp, Double basicSalary){
        int serviceDays = DateUtils.getServiceDays(emp.getUpdatedJoinDate())
        Double pfAmount = 0.0
        //eligible for the PF
        if(serviceDays >= AnnapurnaConstants.TWOYEAR){
            pfAmount = (basicSalary + calculateGradeAmount(emp, basicSalary,emp.getGradeReward()))/5
        }
        return pfAmount
    }

    */
/**
     * Service period count for <code>Additional Salary</code> on the basis of date of appointment to date of Ashad 31st.
     * @return Additional Salary for a particular employee
     *//*

    def calculateAdditionalSalary(Date updatedJoinDate){
        Integer serviceDays = DateUtils.getServiceDays(updatedJoinDate)
        Double additionalSalary = 0.0

        Calendar calendar = Calendar.getInstance()
        calendar.setTime(DateUtils.getCurrentDate())
        calendar.add(Calendar.MONTH, -1)
        Integer salaryMonth = DateUtils.getMonthFromDate(calendar.getTime())
        Integer joinMonth = DateUtils.getMonthFromDate(updatedJoinDate)

        def totalDays = DateUtils.getDayFromDate(DateUtils.getLastDateOfAnyMonth(updatedJoinDate))
        def daysCount = (totalDays-DateUtils.getDayFromDate(updatedJoinDate))+1

        if(serviceDays >= AnnapurnaConstants.ONEYEAR && serviceDays < AnnapurnaConstants.TWOYEAR){
            if(salaryMonth==joinMonth){
                additionalSalary = (1500/totalDays)*daysCount
            }else{
                additionalSalary = 1500
            }
        }
        if(serviceDays >= AnnapurnaConstants.TWOYEAR){
            if(salaryMonth==joinMonth && serviceDays <= (AnnapurnaConstants.ONEYEAR + AnnapurnaConstants.TWOYEAR)){
                def adSalaryOne = (1500/totalDays)*(totalDays-daysCount)
                def adSalaryTwo = (3500/totalDays)*daysCount
                additionalSalary = adSalaryOne+adSalaryTwo
            }else{
                additionalSalary = 3500
            }
        }
        return additionalSalary
    }

    */
/**
     * Service period count for <code>Seniority Allowance</code> on the basis of date of appointment to date of Ashad 31st.
     * After successful completion of 2 yrs, <code>Employee</code> will be eligible for the <code>Seniority Allowance</code>
     * @return Seniority allowance for a particular employee
     *//*


    def calculateSeniorityAllowance(Date updatedJoinDate, Designation designation, Double basicSalary){
        Double seniorityAllowanceAmt = 0.0
        Double prevSenAllowanceAmt = 0.0

        Calendar calendar = Calendar.getInstance()
        Calendar gCal = GregorianCalendar.getInstance()
        calendar.setTime(DateUtils.getCurrentDate())
        calendar.add(Calendar.MONTH, -1)

        Integer salaryMonth = DateUtils.getMonthFromDate(calendar.getTime())
        Integer joinMonth = DateUtils.getMonthFromDate(updatedJoinDate)
        Integer serviceDays = DateUtils.getServiceDays(updatedJoinDate)

        def totalDays = DateUtils.getDayFromDate(DateUtils.getLastDateOfAnyMonth(updatedJoinDate))
        def daysCount = (totalDays-DateUtils.getDayFromDate(updatedJoinDate))+1
        Integer serviceYears = serviceDays/365
        SeniorityAllowance seniorityAllowance= SeniorityAllowance.findByDesignation(designation)
        if(seniorityAllowance){
            def agePercetage = seniorityAllowance?.agePercentage?.sort{a,b->a.age<=>b.age}
            def prevAgePercentage=0.0
            agePercetage.each{agePer->
                if(serviceDays>=getServiceDays(agePer.age)){
                    seniorityAllowanceAmt = (basicSalary) * agePer.percentage/100
                    prevSenAllowanceAmt = (basicSalary) * prevAgePercentage/100
                    if(salaryMonth==joinMonth && serviceYears==agePer.age){
                        seniorityAllowanceAmt = ((seniorityAllowanceAmt/totalDays)*daysCount)+((prevSenAllowanceAmt/totalDays)*(totalDays-daysCount))
                    }
                }
                prevAgePercentage=agePer.percentage
            }
        }
        return seniorityAllowanceAmt
    }

    def getServiceDays(Double ageYears){
        Integer serviceDays = ageYears*365
        return serviceDays
    }

    def calcSenAllowance (Employee emp,Double bSal){
        def sAmount = 0.0
        sAmount=calculateSeniorityAllowance(emp.updatedJoinDate,emp.designation,bSal)
        return sAmount
    }


    */
/**
     * Service period count for <code>Gratuity</code> on the basis of date of appointment to date of resignation.
     * After successful completion of 3 yrs, <code>Employee</code> will be eligible for the <code>Gratuity</code>
     * @return Gratuity for a particular employee
     *//*

    def calculateGratuity(Employee emp, Double basicSalary){
        Double gratuityAmount = 0.0
        int serviceDays = DateUtils.getServiceDays(emp.getUpdatedJoinDate())
        Double gradeAmount = calculateGradeAmount(emp, basicSalary,emp.getGradeReward())
        if(serviceDays >= AnnapurnaConstants.THREEYEAR && serviceDays < AnnapurnaConstants.SEVENYEAR){
            gratuityAmount = (1/2) * (basicSalary + gradeAmount) * (serviceDays/AnnapurnaConstants.ONEYEAR)
        }
        else if(serviceDays >= AnnapurnaConstants.SEVENYEAR && serviceDays < AnnapurnaConstants.FIFTEENYEAR){
            gratuityAmount = (2/3) * (basicSalary + gradeAmount) * (serviceDays/AnnapurnaConstants.ONEYEAR)
        }
        else if(serviceDays >= AnnapurnaConstants.FIFTEENYEAR){
            gratuityAmount = (basicSalary + gradeAmount) * (serviceDays/AnnapurnaConstants.ONEYEAR)
        }
        return gratuityAmount
    }

    */
/**
     * Other allowance is assigned to individual employee
     * @return Other Allowance Amount of a particular employee
     *//*

    def calculateOtherAllowance(Long empId, Date salaryDate){
        Double allowanceAmount = 0.0
        Integer currentMonth =  DateUtils.getMonthFromDate(salaryDate)
        Integer currentYear =  DateUtils.getYearFromDate(salaryDate)
        try{
            def otherAllowances = OtherAllowance.findAll("FROM OtherAllowance as a WHERE a.employee.id=:employeeId AND a.status=0",[employeeId:empId])
            if(otherAllowances){
                for(OtherAllowance allowance : otherAllowances){
                    Integer allowanceEndMonth = DateUtils.getMonthFromDate(allowance.toDate)
                    Integer allowanceEndYear = DateUtils.getYearFromDate(allowance.toDate)

                    if(currentYear == allowanceEndYear){
                        if(currentMonth <= allowanceEndMonth){
                            allowanceAmount += allowance.getAmount()
                        }
                    }else if(currentYear < allowanceEndYear){
                        allowanceAmount += allowance.getAmount()
                    }

                }
            }
        }catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage())
        }
        return allowanceAmount
    }

    */
/**
     *Extra Allowance is calculated on the basis of designation.
     * @param designation
     * @return extraAllowance amount for the particular designation
     *//*

    def calculateExtraAllowance(Designation designation){
        Double extraAmount = 0.0
        try{
            def extraAllowance = ExtraAllowance.findAll("FROM ExtraAllowance as ea WHERE ea.designation=:designation",[designation:designation])
            if(extraAllowance){
                for(ExtraAllowance extraAllow : extraAllowance){
                    extraAmount += extraAllow.getAmount()
                }
            }
        }catch(Exception e) {
            LOGGER.error("Exception: " + e.getMessage())
        }
        return extraAmount
    }


    */
/**
     *Dearness Allowance is calculated on the basis of range of basic Salary.
     *//*


    def calculateDearnessAllowance(Double startSalary, Date joinDate){
        Double dearnessAmount= 0.0
        def dearnessAllowance= DearnessAllowance.executeQuery("FROM DearnessAllowance as da WHERE " + startSalary + " BETWEEN da.fromBasicSalary AND da.toBasicSalary")
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(DateUtils.getCurrentDate())
        calendar.add(Calendar.MONTH, -1)

        def totalDays = DateUtils.getDayFromDate(DateUtils.getLastDateOfAnyMonth(joinDate))
        def daysCount = (totalDays-DateUtils.getDayFromDate(joinDate))+1

        Integer salaryMonth = DateUtils.getMonthFromDate(calendar.getTime())
        Integer joinMonth = DateUtils.getMonthFromDate(joinDate)
        Integer salaryYear = DateUtils.getYearFromDate(new Date())
        Integer joinYear = DateUtils.getYearFromDate(joinDate)
        if(salaryMonth==joinMonth && salaryYear==joinYear){
            Calendar gCal = GregorianCalendar.getInstance()
            if(dearnessAllowance) dearnessAmount = (dearnessAllowance[0]?.getAmount()/totalDays)*daysCount
        }else{
            if(dearnessAllowance) {
                dearnessAmount = dearnessAllowance[0]?.getAmount()
            }
        }
        return dearnessAmount
    }

    */
/**
     * Generates salary report
     * @param emp
     * @param salaryDays should be (total_days_in_month/working_days)
     * @return Sets calculated salary related values to the SalaryReport domain
     *//*

    def calculateSalaryReport(Employee emp, Double salaryDays, Date salaryMonth){
        LOGGER.info("Inside Salary Calculation Method")
        Double startSalaryForTaxCalculation = 0.0
        def salary
        try{
            salary = Salary.findByDesignation(emp.getDesignation())

            if(salary){
                Double additionalSalary = 0.0
                Double basicSalary = 0.0
                Double dashainExgratia = 0.0
                Double grade = 0.0
                Double gratuity = 0.0
                Double otherAllowance = 0.0
                Double extraAllowance = 0.0
                Double seniorityAllowance = 0.0
                Double dearnessAllowanceAmount = 0.0
                Double pfAmount = 0.0
                Double emoluPfAmount = 0.0
                Double citAmount = 0.0
                Double employeeAdvance = 0.0
                Double emolumentAmount = 0.0
                Double startSalary = 0.0
                Double insurancePremium = 0.0

                //Get if there is any restriction to this employee or not
                BlockAllowance blockAllowance = BlockAllowance.findByEmployee(emp)
                if(blockAllowance){
                    if(!blockAllowance.basicSalary){
                        if(!blockAllowance.additionalSalary){
                            if(AnnapurnaConstants.CLIENT_NAME.equals(AnnapurnaConstants.CLIENT_PHECT)){
                                additionalSalary = calculateAdditionalSalary(emp.getUpdatedJoinDate())
                            }
                        }
                        startSalary = salary.startSalary
                        if(AnnapurnaConstants.CLIENT_NAME.equals(AnnapurnaConstants.CLIENT_PHECT)){
                            basicSalary = startSalary +  additionalSalary
                        }
                        if(AnnapurnaConstants.CLIENT_NAME.equals(AnnapurnaConstants.CLIENT_DEERWALK)){
                            basicSalary = startSalary
                        }
                        if(!blockAllowance.dashainExgratia){
                            dashainExgratia = calculateDashainExgratiaByMonth(emp,salaryMonth,basicSalary)
                        }
                        if(!blockAllowance.grade){
                            if(AnnapurnaConstants.CLIENT_NAME.equals(AnnapurnaConstants.CLIENT_PHECT)){
                                grade = calculateGradeAmount(emp, basicSalary,emp.getGradeReward())
                            }
                        }
                        if(!blockAllowance.gratuity){
                            gratuity = calculateGratuity(emp, basicSalary)
                        }
                        if(!blockAllowance.seniorityAllowance){
                            seniorityAllowance = calculateSeniorityAllowance(emp.getUpdatedJoinDate(), emp.getDesignation(), basicSalary)
                        }
                        if(!blockAllowance.dearnessAllowance){
                            dearnessAllowanceAmount = calculateDearnessAllowance(basicSalary,emp.joinDate)
                        }
                        if(!blockAllowance.pf){
                            pfAmount = calculatePF(emp,basicSalary)
                        }
                    }
                    if(!blockAllowance.otherAllowance){
                        otherAllowance = calculateOtherAllowance(emp.id, salaryMonth)
                    }
                    if(!blockAllowance.extraAllowance){
                        extraAllowance = calculateExtraAllowance(emp.getDesignation())
                    }
                }else{
                    additionalSalary = calculateAdditionalSalary(emp.getUpdatedJoinDate())
                    startSalary = salary.startSalary
                    basicSalary = startSalary +  additionalSalary
                    dashainExgratia = calculateDashainExgratiaByMonth(emp,salaryMonth,basicSalary)
                    grade = calculateGradeAmount(emp, basicSalary,emp.getGradeReward())
                    gratuity = calculateGratuity(emp, basicSalary)
                    seniorityAllowance = calculateSeniorityAllowance(emp.getUpdatedJoinDate(), emp.getDesignation(), basicSalary)
                    pfAmount = calculatePF(emp,basicSalary)
                    dearnessAllowanceAmount = calculateDearnessAllowance(basicSalary,emp.joinDate)
                    otherAllowance = calculateOtherAllowance(emp.id, salaryMonth)
                    extraAllowance = calculateExtraAllowance(emp.getDesignation())
                }
                emoluPfAmount = pfAmount/2
                employeeAdvance = calculateEmployeeAdvance(emp,salaryMonth)
                emolumentAmount = basicSalary+grade+otherAllowance+extraAllowance+seniorityAllowance+dearnessAllowanceAmount+emoluPfAmount
                // account information of the emp taken from HrmEmployeeAccount domain
                HrmEmployeeAccount empAccount = HrmEmployeeAccount.findByEmployee(emp)
                if(empAccount){
                    // CIT deduction % taken from empAccount
                    Double deductionPercentage = empAccount.getCit()
                    if(deductionPercentage !=  0){
                        if(pfAmount > 0 && deductionPercentage > 18){
                            deductionPercentage = 18.33
                        }
                        citAmount = calculateCIT(emolumentAmount, deductionPercentage)
                    }
                }
                //total taxable amount
                insurancePremium = HrmEmployeeAccount.findByEmployee(emp)?.insurancePremiumAmount?:0
                if(insurancePremium>20000) insurancePremium=20000
                startSalaryForTaxCalculation = basicSalary + grade + otherAllowance + seniorityAllowance + extraAllowance + dearnessAllowanceAmount + (pfAmount/2)
                //Subtract citAmount and pfAmount and multiply by 12 then add dashaina exgratia to the total
                //Dashain exgratia will be calculated at the month of Ghatasthapana only....but if any employee is eligible for the Dashain exgratia then we need to
                //include the dashain exgratia in every month tax calculation
                Integer serviceDaysAsOfGhatasthapana=DateUtils.getServiceDaysAsOfGhatasthapana(emp.getUpdatedJoinDate())
                Double dashainExgratiaForTax = 0.0
                if(serviceDaysAsOfGhatasthapana >= AnnapurnaConstants.SIXMONTH){
                    dashainExgratiaForTax= basicSalary
                }
                if(dashainExgratiaForTax>maxDashainExergtia && AnnapurnaConstants.CLIENT_NAME==AnnapurnaConstants.CLIENT_PHECT)
                    dashainExgratiaForTax=maxDashainExergtia
                startSalaryForTaxCalculation = ((startSalaryForTaxCalculation - citAmount - pfAmount) * 12) + dashainExgratiaForTax - insurancePremium
                // pass startSalaryForTaxCalculation to the tax calculation method
                Double socialSecurityTax  = calculateSocialSecurityTax(emp,startSalaryForTaxCalculation)
                Double taxAmount = calculateTax(emp, startSalaryForTaxCalculation)
                Double totalSalary = basicSalary + grade + otherAllowance + seniorityAllowance + extraAllowance + dearnessAllowanceAmount - citAmount - taxAmount - (pfAmount/2)-employeeAdvance
                //            Double extraDays = leaveService.calculateExtraDays(emp,salaryMonth)
                Double unpaidLeaveDays = leaveService.getPaidUnpaidLeaveCount(AnnapurnaConstants.UNPAID_LEAVE,emp,salaryMonth,AnnapurnaConstants.LEAVE_TYPE_ALL,true,false)
                //            Double extraSalary = calculateSalaryByDays(extraDays,totalSalary,salaryMonth)
                Double lessSalary = calculateSalaryByDays(unpaidLeaveDays,totalSalary,salaryMonth)
                if(salaryDays == AnnapurnaConstants.FULL_MONTH){
                    SalaryReport salaryReport = new SalaryReport(additionalSalary:additionalSalary, startSalary:startSalary,basicSalary:basicSalary, dashainExgratia:dashainExgratia,employee:emp,
                            grade:grade, gratuty:gratuity, otherAllowance:otherAllowance, extraAllowance:extraAllowance, seniorityAllowance:seniorityAllowance, dearnessAllowance:dearnessAllowanceAmount,
                            pf:pfAmount, emoluPf:emoluPfAmount, emolument:emolumentAmount , cit:citAmount, socialSecurityTax:socialSecurityTax ,tax:taxAmount, total:totalSalary, salaryDate:DateUtils.getCurrentDate(),
                            salaryMonth:salaryMonth, salaryClass:emp.getSalaryclass(),employeeAdvance:employeeAdvance,unpaidDaysSalary:lessSalary,insuranceDeduction: insurancePremium/12 )
                    salaryReport.save()
                }
                else{
                    SalaryReport salaryReport = new SalaryReport(additionalSalary:additionalSalary/salaryDays, startSalary:startSalary/salaryDays,basicSalary:basicSalary/salaryDays, dashainExgratia:dashainExgratia,
                            employee:emp, grade:grade/salaryDays, gratuty:gratuity, otherAllowance:otherAllowance/salaryDays, extraAllowance:extraAllowance/salaryDays, seniorityAllowance:seniorityAllowance/salaryDays,
                            dearnessAllowance:dearnessAllowanceAmount/salaryDays, pf:pfAmount/salaryDays, emoluPf:emoluPfAmount/salaryDays, emolument:emolumentAmount/salaryDays, cit:citAmount/salaryDays, socialSecurityTax:socialSecurityTax ,
                            tax:taxAmount/salaryDays, total:totalSalary/salaryDays, salaryDate:DateUtils.getCurrentDate(), salaryMonth:salaryMonth, salaryClass:emp.getSalaryclass(),employeeAdvance:employeeAdvance/salaryDays,unpaidDaysSalary:lessSalary,insuranceDeduction: insurancePremium/12 )
                    salaryReport.save(failOnError:true)
                }
                sessionFactory.currentSession.flush()
                sessionFactory.currentSession.clear()
                DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP.get().clear()
                checkStatusOtherAllowance(emp, salaryMonth)
                checkStatusEmployeeAdvance(emp,salaryMonth)
            }
        }catch (Exception e) {
            LOGGER.error("Exception: " + e.printStackTrace())
        }
    }

    */
/**
     * Checks whether the salary month is Dashain; if yes, only then return the Dashain Exgratia
     * @param employee
     * @param salaryMonth
     * @param salary
     * @return dashain Exgratia , ie a month salary
     *//*

    def calculateDashainExgratiaByMonth(Employee emp,Date salaryMonth,Double basicSalary){
        Double dashainExgratia = 0.0
        // to get the date (month-year) of Dashain
        Date getDashainMonth = DateUtils.getSettingDate(AnnapurnaConstants.GHATASTHAPANA)
        Date formattedDate=DateUtils.toMonthYearFormat(getDashainMonth)
        Integer serviceDaysAsOfGhatasthapana=DateUtils.getServiceDaysAsOfGhatasthapana(emp.getUpdatedJoinDate())
        Integer dashainMonth = DateUtils.getMonthFromDate(formattedDate)
        Integer salMonth = DateUtils.getMonthFromDate(salaryMonth)
        // if the employee has completed one or more year of service at the time of receiving Dashain Exgratia as per salaryMonth,
        // then, allocate dashain exgratia that equals one month salary
        if(serviceDaysAsOfGhatasthapana >= AnnapurnaConstants.SIXMONTH){
            if((dashainMonth-1)==salMonth){
                dashainExgratia= basicSalary
            }
        }
        if(dashainExgratia>maxDashainExergtia && AnnapurnaConstants.CLIENT_NAME==AnnapurnaConstants.CLIENT_PHECT)
            dashainExgratia=maxDashainExergtia
        return dashainExgratia
    }

    */
/**
     * Calculate Employee Advance amount
     * @param employeesa
     * @param salaryMonth
     * @return employeeAdvance deduction amount
     *//*

    def calculateEmployeeAdvance(Employee emp, Date salaryMonth){
        Double deductAmount = 0.0
        // list of employee getting employee Advance
        def employeeAdvanceList=EmployeeAdvance.executeQuery("FROM EmployeeAdvance as ea WHERE ea.employee="+emp.id)
        // checks whether salaryMonth and date of employee advance matches, if yes returns deduction amount for SalaryReport calculation
        for(EmployeeAdvance empAdvance: employeeAdvanceList){
            if(empAdvance){
                // converts date field from Employee Advance into that of salaryMonth,ie "MM-yyyy", and returns
                def formattedDate=DateUtils.toMonthYearFormat(empAdvance.getDate())
                def salDate = DateUtils.toMonthYearFormat(salaryMonth)
                if(formattedDate == salDate){
                    deductAmount= empAdvance.getDeductionAmount()
                }
            }

        }
        return deductAmount
    }

    */
/**
     * Update the status of the {@link EmployeeAdvance}  to 1 if the advance is calculated for the salaryMonth
     * @param emp
     *//*


    def checkStatusEmployeeAdvance(Employee emp,Date salaryMonth){
        def empAdvanceList=EmployeeAdvance.executeQuery("FROM EmployeeAdvance ea WHERE ea.employee.id="+emp.id)
        for(EmployeeAdvance empAdvance:empAdvanceList){
            // converts date field from Employee Advance into that of salaryMonth,ie "MM-yyyy", and returns
            def formattedDate=DateUtils.toMonthYearFormat(empAdvance.getDate())
            if(formattedDate == salaryMonth){
                EmployeeAdvance.executeUpdate("UPDATE EmployeeAdvance ea SET ea.status=1 WHERE ea.status=0 AND ea.employee.id="+emp.id+"AND ea.id="+empAdvance.id)
            }
        }
    }

    */
/**
     * Calculate CIT amount
     * @param emolumentAmount
     * @param deductionPercentage
     * @return CIT amount
     *//*


    def calculateCIT(Double emolumentAmount, Double deductionPercentage){
        Double citAmount = 0.0
        citAmount = emolumentAmount * deductionPercentage/100
        return citAmount
    }

    */
/**
     * Calculate Income Tax for a particular employee
     * @param basicSalary
     * @param maritalStatus
     * @param maleOrFemale
     * @param citAmount
     * @return Amount of tax in Decimal format
     *//*

    def calculateTax(Employee emp, Double basicSalary){
        Double taxAmount = 0.0
        def taxSettingInstance = TaxSetting.findAll()[0]
        //TODO: consider premium amount max of 20,000 while calculating tax
        //TODO: consider calculation of annual total income and total tax amount
        Double salaryPerYear = basicSalary
        if(emp.getMaritalStatus().equals(AnnapurnaConstants.SINGLE)){
            if(salaryPerYear <= taxSettingInstance.singleMinLimit){
                taxAmount += salaryPerYear*(taxSettingInstance.minTaxPercentage/100)
                // taxAmount=calculateSocialSecurityTax(emp, salaryPerYear)

            }else if(salaryPerYear <= taxSettingInstance.singleMedLimit){
                Double salaryAfterOneSixty = salaryPerYear - taxSettingInstance.singleMinLimit
                taxAmount += taxSettingInstance.singleMinLimit*taxSettingInstance.minTaxPercentage/100
                taxAmount += (salaryAfterOneSixty * taxSettingInstance.midTaxPercentage)/100
                if(emp.gender==AnnapurnaConstants.FEMALE){
                    taxAmount = taxAmount - ((salaryAfterOneSixty * taxSettingInstance.femaleRebatePercentage)/100)
                }
            }else{
                Double salaryAfterOneSixty = salaryPerYear - taxSettingInstance.singleMinLimit
                taxAmount += taxSettingInstance.singleMinLimit*taxSettingInstance.minTaxPercentage/100
                Double salaryAfterTwoSixty = salaryAfterOneSixty - taxSettingInstance.taxAmountLimitForMedium
                taxAmount += (taxSettingInstance.taxAmountLimitForMedium * taxSettingInstance.midTaxPercentage)/100
                if(emp.gender==AnnapurnaConstants.FEMALE){
                    taxAmount = taxAmount - (0.1 * ((taxSettingInstance.taxAmountLimitForMedium * taxSettingInstance.midTaxPercentage)/100))
                }
                taxAmount += (salaryAfterTwoSixty * taxSettingInstance.maxTaxPercentage)/100
                if(emp.gender==AnnapurnaConstants.FEMALE){
                    taxAmount = taxAmount - (0.1 * ((salaryAfterTwoSixty * taxSettingInstance.maxTaxPercentage)/100))
                }
            }
        }else if(emp.getMaritalStatus().equals(AnnapurnaConstants.MARRIED)){
            if(salaryPerYear <= taxSettingInstance.marriedMinLimit){
                taxAmount += salaryPerYear*taxSettingInstance.minTaxPercentage/100
                //                  taxAmount=calculateSocialSecurityTax(emp, salaryPerYear)
            }else if(salaryPerYear <= taxSettingInstance.marriedMedLimit){
                Double salaryAfterTwoLakhs = salaryPerYear - taxSettingInstance.marriedMinLimit
                taxAmount += taxSettingInstance.marriedMinLimit*taxSettingInstance.minTaxPercentage/100
                taxAmount += (salaryAfterTwoLakhs * taxSettingInstance.midTaxPercentage)/100
                if(emp.gender==AnnapurnaConstants.FEMALE){
                    taxAmount = taxAmount - (0.1*((salaryAfterTwoLakhs * taxSettingInstance.midTaxPercentage)/100))
                }
            }else{
                Double salaryAfterTwoLakhs = salaryPerYear - taxSettingInstance.marriedMinLimit
                taxAmount += taxSettingInstance.marriedMinLimit*taxSettingInstance.minTaxPercentage/100
                Double salaryAfterThreeLakhs = salaryAfterTwoLakhs - taxSettingInstance.taxAmountLimitForMedium
                taxAmount += (taxSettingInstance.taxAmountLimitForMedium * taxSettingInstance.midTaxPercentage)/100
                if(emp.gender==AnnapurnaConstants.FEMALE){
                    taxAmount = taxAmount - (0.1 * ((taxSettingInstance.taxAmountLimitForMedium * taxSettingInstance.midTaxPercentage)/100))
                }
                taxAmount += (salaryAfterThreeLakhs * taxSettingInstance.maxTaxPercentage)/100
                if(emp.gender==AnnapurnaConstants.FEMALE){
                    taxAmount = taxAmount - (0.1 * ((salaryAfterThreeLakhs * taxSettingInstance.maxTaxPercentage)/100))
                }
            }
        }
        return taxAmount/12
    }

    */
/**
     * Calculate SocialSecurity tax (sst)
     *//*

    def calculateSocialSecurityTax(Employee emp, Double basicSalary){
        LOGGER.info("Inside SocialSecurity tax Calculation Method")
        def taxSettingInstance = TaxSetting.findAll()[0]
        Double sst=0.0
        try{
            Double salaryPerYear = basicSalary
            if(emp.getMaritalStatus().equals(AnnapurnaConstants.MARRIED)){
                if(salaryPerYear <taxSettingInstance.marriedMinLimit){
                    sst += salaryPerYear*taxSettingInstance.minTaxPercentage/100
                }
                else{
                    sst += (taxSettingInstance.marriedMinLimit*taxSettingInstance.minTaxPercentage)/100
                }
            }
            else{
                if(salaryPerYear <taxSettingInstance.singleMinLimit){
                    sst += salaryPerYear*taxSettingInstance.minTaxPercentage/100
                }
                else{
                    sst += (taxSettingInstance.singleMinLimit*taxSettingInstance.minTaxPercentage)/100
                }
            }
            return sst/12
        }
        catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage())
        }
    }

    */
/**
     * Check whether this date is next month or not
     * @return <code>true</code> if the current date is next month of the <code>salaryDate</code> of {@link SalaryReport} domain otherwise <code>false</code>
     *//*

    def isNextMonth(){
        try{
            def prevSalaryDate = SalaryReport.executeQuery("SELECT MAX(sr.salaryDate) as salaryDate from SalaryReport sr")
            //if there exists no data in the salary table
            int days = -1
            if(prevSalaryDate[0]!=null){
                days = DateUtils.getServiceDays(DateUtils.stringToDate(prevSalaryDate[0].toString()))
            }
            if(days > 30 || days == -1){
                return true
            }
            else{
                return false
            }
        }catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage())
            return false
        }
    }

    */
/**
     * Get the salary month ie. previous month since we are generating salary in the next month by salary class.
     * @return Salary month in the format of "MM-YY"
     *//*

    def getSalaryMonth(){
        Date currDate = DateUtils.getCurrentDate()
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(currDate)
        calendar.add(Calendar.MONTH, -1) // this function add() rolls month such as jan to dec ie. by 1 unit ; ALSO rolls year 2012 jan to 2011 dec
        Date yearMonthDateOnly = DateUtils.toMonthYearFormat(calendar.getTime())
        return yearMonthDateOnly
    }

    */
/**
     * Generate Salary for the employee by salary group
     *//*

    def generateSalaryBySalaryGroup(){
        def fiscalDate = DateSetting.findByDateType(AnnapurnaConstants.FISCAL_YEAR)
        //  def fiscalMonth =  DateUtils.getMonthFromDate(fiscalDate)
        def today = new Date()
        Date salaryDate = getSalaryMonth()    //previous month.
        Integer salaryMonth =  DateUtils.getMonthFromDate(salaryDate)
        Integer salaryYear =  DateUtils.getYearFromDate(salaryDate)

        def salaryClass = SalaryClass.findAll()
        */
/*Date currentDate = DateUtils.getCurrentDate()
           Calendar calendar = Calendar.getInstance()
           calendar.setTime(currentDate)
           Integer today = calendar.get(Calendar.DAY_OF_MONTH)*//*



//        if(today==fiscalDate.date){
//
//            for(SalaryClass salaryc : salaryClass){
//
//                */
/*if(salaryc.dayOfMonth == today){*//*

//                def employees = Employee.findAllBySalaryclass(salaryc)
//                for(Employee emp: employees){
//                    if(emp.getStatus().equals(AnnapurnaConstants.TEMPORARY) || emp.getStatus().equals(AnnapurnaConstants.PERMANENT)
//                            || emp.getStatus().equals(AnnapurnaConstants.CONTRACT) || emp.getStatus().equals(AnnapurnaConstants.SUSPENDED)){
//                        //Consider for the employees who join at the middle of the month
//                        //Check if the employee's previous month salary sheet exists or not
//
//                        salaryDate = today.clearTime()
//                        salaryMonth =  DateUtils.getMonthFromDate(salaryDate)
//                        salaryYear =  DateUtils.getYearFromDate(salaryDate)
//
//                        Integer count = SalaryReport.countByEmployee(emp)
//                        Date joinDate = emp.getUpdatedJoinDate()
//                        Integer joinMonth = DateUtils.getMonthFromDate(joinDate)
//                        Integer joinYear = DateUtils.getYearFromDate(joinDate)
//                        Double unpaidLeaveDays = leaveService.getPaidUnpaidLeaveCount(AnnapurnaConstants.UNPAID_LEAVE,emp,salaryDate,AnnapurnaConstants.LEAVE_TYPE_ALL,true,true)
//
//                        def suspensionDetails = SuspendedEmployeeDetails.findAllByEmployee(emp)
//                        Integer noOfDaysInMonth = DateUtils.getDayFromDate(salaryDate)
//                        //If previous month salary not exists. TODO:- Refactor for one day adjustment.
//                        if(count == 0 && joinYear == salaryYear && joinMonth == salaryMonth){
//                            Integer joinDay = DateUtils.getDayFromDate(joinDate)
//                            Double daysAttendedMinusLeave = 0
//                            Double salaryDays = 0
//                            daysAttendedMinusLeave = noOfDaysInMonth - joinDay + 1 - unpaidLeaveDays - getSuspendedDaysInMonth(emp)
//                            salaryDays = noOfDaysInMonth/daysAttendedMinusLeave
//                            calculateSalaryReport(emp,salaryDays,salaryDate)
//                        }else{
//                            //If previous month salary exists
//                            def daysAttendedMinusLeave = noOfDaysInMonth - unpaidLeaveDays - getSuspendedDaysInMonth(emp)
//                            def salaryDays = noOfDaysInMonth/daysAttendedMinusLeave
//                            calculateSalaryReport(emp,salaryDays,salaryDate)
//                        }
//                    }
//                }
//            }
//
//
//
//        }else if(DateUtils.getDayFromDate(today)==1){
//            if(fiscalMonth==salaryMonth){
//                for(SalaryClass salaryc : salaryClass){
//
//                    */
/*if(salaryc.dayOfMonth == today){*//*

//                    def employees = Employee.findAllBySalaryclass(salaryc)
//                    for(Employee emp: employees){
//                        if(emp.getStatus().equals(AnnapurnaConstants.TEMPORARY) || emp.getStatus().equals(AnnapurnaConstants.PERMANENT)
//                                || emp.getStatus().equals(AnnapurnaConstants.CONTRACT) || emp.getStatus().equals(AnnapurnaConstants.SUSPENDED)){
//                            //Consider for the employees who join at the middle of the month
//                            //Check if the employee's previous month salary sheet exists or not
//
//                            Integer count = SalaryReport.countByEmployee(emp)
//                            Date joinDate = emp.getUpdatedJoinDate()
//                            Integer joinMonth = DateUtils.getMonthFromDate(joinDate)
//                            Integer joinYear = DateUtils.getYearFromDate(joinDate)
//                            Double unpaidLeaveDays = leaveService.getPaidUnpaidLeaveCount(AnnapurnaConstants.UNPAID_LEAVE,emp,fiscalDate,AnnapurnaConstants.LEAVE_TYPE_ALL,true,false)
//
//                            def suspensionDetails = SuspendedEmployeeDetails.findAllByEmployee(emp)
//                            DateUtils.getNumberDaysInMonth(salaryDate)
//                            DateUtils.getDayFromDate(fiscalDate)
//                            Integer noOfDaysInMonth = DateUtils.getDaysFromTwoDates(fiscalDate,DateUtils.getLastDateOfAnyMonth(salaryDate))
//                            //If previous month salary not exists. TODO:- Refactor for one day adjustment.
//                            if(count == 0 && joinYear == salaryYear && joinMonth == salaryMonth){
//                                Integer joinDay = DateUtils.getDayFromDate(joinDate)
//                                Double daysAttendedMinusLeave = 0
//                                Double salaryDays = 0
//                                daysAttendedMinusLeave = noOfDaysInMonth - joinDay + 1 - unpaidLeaveDays - getSuspendedDaysInMonth(emp)
//                                salaryDays = noOfDaysInMonth/daysAttendedMinusLeave
//                                calculateSalaryReport(emp,salaryDays,salaryDate)
//                            }else{
//                                //If previous month salary exists
//                                def daysAttendedMinusLeave = noOfDaysInMonth - unpaidLeaveDays - getSuspendedDaysInMonth(emp)
//                                def salaryDays = noOfDaysInMonth/daysAttendedMinusLeave
//                                calculateSalaryReport(emp,salaryDays,salaryDate)
//                            }
//                        }
//                    }
//                }
//            }else{
//                for(SalaryClass salaryc : salaryClass){
//                    */
/*if(salaryc.dayOfMonth == today){*//*

//                    def employees = Employee.findAllBySalaryclass(salaryc)
//                    for(Employee emp: employees){
//                        if(emp.getStatus().equals(AnnapurnaConstants.TEMPORARY) || emp.getStatus().equals(AnnapurnaConstants.PERMANENT)
//                                || emp.getStatus().equals(AnnapurnaConstants.CONTRACT) || emp.getStatus().equals(AnnapurnaConstants.SUSPENDED)){
//                            //Consider for the employees who join at the middle of the month
//                            //Check if the employee's previous month salary sheet exists or not
//                            Integer count = SalaryReport.countByEmployee(emp)
//                            Date joinDate = emp.getUpdatedJoinDate()
//                            Integer joinMonth = DateUtils.getMonthFromDate(joinDate)
//                            Integer joinYear = DateUtils.getYearFromDate(joinDate)
//                            Double unpaidLeaveDays = leaveService.getPaidUnpaidLeaveCount(AnnapurnaConstants.UNPAID_LEAVE,emp,salaryDate,AnnapurnaConstants.LEAVE_TYPE_ALL,true,false)
//
//                            def suspensionDetails = SuspendedEmployeeDetails.findAllByEmployee(emp)
//                            //If previous month salary not exists. TODO:- Refactor for one day adjustment.
//                            if(count == 0 && joinYear == salaryYear && joinMonth == salaryMonth){
//                                Integer noOfDaysInMonth = DateUtils.getNumberDaysInMonth(salaryDate)
//                                Integer joinDay = DateUtils.getDayFromDate(joinDate)
//                                Double daysAttendedMinusLeave = 0
//                                Double salaryDays = 0
//                                daysAttendedMinusLeave = noOfDaysInMonth - joinDay + 1 - unpaidLeaveDays - getSuspendedDaysInMonth(emp)
//                                salaryDays = noOfDaysInMonth/daysAttendedMinusLeave
//                                calculateSalaryReport(emp,salaryDays,getSalaryMonth())
//                            }else{
//                                //If previous month salary exists
//                                def daysAttendedMinusLeave = DateUtils.getNumberDaysInMonth(salaryDate) - unpaidLeaveDays - getSuspendedDaysInMonth(emp)
//                                def salaryDays = DateUtils.getNumberDaysInMonth(salaryDate)/daysAttendedMinusLeave
//                                calculateSalaryReport(emp,salaryDays,getSalaryMonth())
//                            }
//                        }
//                    }
//                }
//            }
//        }


        for(SalaryClass salaryc : salaryClass){










            */
/*if(salaryc.dayOfMonth == today){*//*

            def employees = Employee.findAllBySalaryclass(salaryc)
            for(Employee emp: employees){
                if(emp.getStatus().equals(AnnapurnaConstants.TEMPORARY) || emp.getStatus().equals(AnnapurnaConstants.PERMANENT)
                        || emp.getStatus().equals(AnnapurnaConstants.CONTRACT) || emp.getStatus().equals(AnnapurnaConstants.SUSPENDED)){
                    //Consider for the employees who join at the middle of the month
                    //Check if the employee's previous month salary sheet exists or not
                    Integer count = SalaryReport.countByEmployee(emp)
                    Date joinDate = emp.getUpdatedJoinDate()
                    Integer joinMonth = DateUtils.getMonthFromDate(joinDate)
                    Integer joinYear = DateUtils.getYearFromDate(joinDate)
                    Double unpaidLeaveDays = leaveService.getPaidUnpaidLeaveCount(AnnapurnaConstants.UNPAID_LEAVE,emp,salaryDate,AnnapurnaConstants.LEAVE_TYPE_ALL,true,false)

                    def suspensionDetails = SuspendedEmployeeDetails.findAllByEmployee(emp)
                    //If previous month salary not exists. TODO:- Refactor for one day adjustment.
                    if(count == 0 && joinYear == salaryYear && joinMonth == salaryMonth){
                        Integer noOfDaysInMonth = DateUtils.getNumberDaysInMonth(salaryDate)
                        Integer joinDay = DateUtils.getDayFromDate(joinDate)
                        Double daysAttendedMinusLeave = 0
                        Double salaryDays = 0
                        daysAttendedMinusLeave = noOfDaysInMonth - joinDay + 1 - unpaidLeaveDays - getSuspendedDaysInMonth(emp)
                        salaryDays = noOfDaysInMonth/daysAttendedMinusLeave
                        calculateSalaryReport(emp,salaryDays,getSalaryMonth())
                    }else{
                        //If previous month salary exists
                        def daysAttendedMinusLeave = DateUtils.getNumberDaysInMonth(salaryDate) - unpaidLeaveDays - getSuspendedDaysInMonth(emp)
                        def salaryDays = DateUtils.getNumberDaysInMonth(salaryDate)/daysAttendedMinusLeave
                        calculateSalaryReport(emp,salaryDays,getSalaryMonth())
                    }
                }
            }
        }
    }

    def checkSuspensionEffectSalaryMonth(Employee emp){
        def result = false
        def suspensionDetails=[]
        suspensionDetails = SuspendedEmployeeDetails.findAllByEmployee(emp)
        suspensionDetails.each{
            if(DateUtils.checkIfDateFallsInRange(it.startDate,it.endDate,getSalaryMonth())) result=true
        }
        return result
    }

    def getSuspendedDaysInMonth(Employee emp){
        int totalSusDays=0
        def suspensionList = SuspendedEmployeeDetails.findAllByEmployee(emp)
        def suspensionInSalaryMonth=[]
        suspensionList.each{eachSuspension->
            if(DateUtils.checkIfDateFallsInRange(eachSuspension.startDate,eachSuspension.endDate,getSalaryMonth())){
                suspensionInSalaryMonth.add(eachSuspension)
            }
        }
        suspensionInSalaryMonth.each{eachSus->
            if(DateUtils.checkIfDateFallsInMonth(getSalaryMonth(),eachSus.startDate) &&
                    !DateUtils.checkIfDateFallsInMonth(getSalaryMonth(),eachSus.endDate)){
                Date monthEnd = DateUtils.getLastDateOfAnyMonth(getSalaryMonth())
                totalSusDays=totalSusDays+DateUtils.getDaysFromTwoDates(eachSus.startDate,monthEnd)
            }else if(DateUtils.checkIfDateFallsInMonth(getSalaryMonth(),eachSus.endDate) &&
                    !DateUtils.checkIfDateFallsInMonth(getSalaryMonth(),eachSus.startDate)){
                Date firstDate = DateUtils.getFirstDateOfAnyMonth(getSalaryMonth())
                totalSusDays=totalSusDays+DateUtils.getDaysFromTwoDates(firstDate,eachSus.endDate)
            }else if(DateUtils.checkIfDateFallsInMonth(getSalaryMonth(),eachSus.endDate) &&
                    DateUtils.checkIfDateFallsInMonth(getSalaryMonth(),eachSus.startDate)){
                totalSusDays = totalSusDays+ DateUtils.getDaysFromTwoDates(eachSus.startDate,eachSus.endDate)
            }else{
                totalSusDays = DateUtils.getNumberDaysInMonth(getSalaryMonth())
            }
        }
        return totalSusDays
    }

    */
/**
     * Generate Salary for Terminated Employees
     * @param emp
     * @param salClass
     * @return
     *//*

    def generateSalaryForTermedEmployees(Employee emp,salClass){

        Date currentDate = DateUtils.toMonthYearFormat(new Date())
        //get current day
        Integer today=DateUtils.getDayFromDate(new Date())
        //get join day
        Integer joinDay = DateUtils.getDayFromDate(emp.getUpdatedJoinDate())
        //get the total days in salary month
        Integer noOfDaysInMonth = DateUtils.getNumberDaysInMonth(getSalaryMonth())

        //Consider for the employees who join at the middle of the month
        //Check if the employee's previous month salary sheet exists or not
        Integer count = SalaryReport.countByEmployee(emp)
        Date salaryDate = getSalaryMonth()
        Date joinDate = emp.getUpdatedJoinDate()
        Integer joinMonth = DateUtils.getMonthFromDate(joinDate)
        Integer joinYear = DateUtils.getYearFromDate(joinDate)
        Integer salaryMonth =  DateUtils.getMonthFromDate(salaryDate)
        Integer salaryYear =  DateUtils.getYearFromDate(salaryDate)
        Integer currentMonth = DateUtils.getMonthFromDate(currentDate)
        Integer noOfDaysInCurrentMonth = DateUtils.getNumberDaysInMonth(currentDate)
        //        Double unpaidLeaveDaysForCurrentMonth =leaveService.getPaidUnpaidLeaveCount(AnnapurnaConstants.UNPAID_LEAVE,emp,currentDate,AnnapurnaConstants.LEAVE_TYPE_ALL)
        //        Double unpaidLeaveDaysForSalaryMonth =leaveService.getPaidUnpaidLeaveCount(AnnapurnaConstants.UNPAID_LEAVE,emp,salaryDate,AnnapurnaConstants.LEAVE_TYPE_ALL)
        Double salaryDaysOnTermination = noOfDaysInCurrentMonth/today
        //If the employee joins previous month
        if(count == 0 && joinYear == salaryYear && joinMonth == salaryMonth){
            Double salaryDays = noOfDaysInMonth/(noOfDaysInMonth - joinDay + 1)
            //if salary of previous month already not calculated then calculate salary of previous month and this month
            if(today < salClass.dayOfMonth){
                //calculate salary of previous month
                calculateSalaryReport(emp, salaryDays, getSalaryMonth())
                //calculate salary of this month
                calculateSalaryReport(emp,salaryDaysOnTermination,currentDate)
            }
            else{
                //if salary of previous month already calculated then calculate salary this month only
                calculateSalaryReport(emp,salaryDaysOnTermination,currentDate)
            }
        }//if the employees joins this month
        else if(count == 0 && joinYear == salaryYear && joinMonth == currentMonth){
            Double currentMonthSalaryDays = noOfDaysInCurrentMonth/(today - joinDay + 1)
            //calculate salary of this month
            calculateSalaryReport(emp, currentMonthSalaryDays, currentDate)
        }else {
            if(today < salClass.dayOfMonth){
                //If previous of previous month salary exists, calculate full salary of previous month and remaining salary of this month

                calculateSalaryReport(emp, AnnapurnaConstants.FULL_MONTH, getSalaryMonth())
                calculateSalaryReport(emp,salaryDaysOnTermination,currentDate)
            }else{
                //if salary of previous month already calculated then calculate salary this month only
                calculateSalaryReport(emp,salaryDaysOnTermination,currentDate)
            }
        }
    }

    */
/**
     * Update the status of the {@link OtherAllowance} if the to date exceeded the current date
     * @param emp
     *//*

    def checkStatusOtherAllowance(Employee emp, Date salaryDate){
        Integer currentMonth =  DateUtils.getMonthFromDate(salaryDate)
        Integer currentYear =  DateUtils.getYearFromDate(salaryDate)
        def allowanceValues=OtherAllowance.executeQuery("from OtherAllowance oa where employee.id="+emp.id)
        for(OtherAllowance allowance : allowanceValues){
            Integer allowanceEndMonth = DateUtils.getMonthFromDate(allowance.toDate)
            Integer allowanceEndYear = DateUtils.getYearFromDate(allowance.toDate)
            if(currentYear >= allowanceEndYear && currentMonth >= allowanceEndMonth){
                OtherAllowance.executeUpdate("UPDATE OtherAllowance oa SET oa.status=1 WHERE oa.status=0 AND oa.employee.id="+emp.id + " AND oa.id=" + allowance.id)
            }
        }
    }

    */
/**
     * Get the salary month for populating the drop down list of salary report list view from the table.
     * @return Salary month in the format of "YYYY-MM"
     *//*

    def populateSalMonth(){
        def salMonthList = SalaryReport.executeQuery("SELECT DISTINCT(SUBSTRING(s.salaryMonth,1,7)) as salaryMonth from SalaryReport s order by s.salaryMonth asc")
        return salMonthList
    }

    */
/**
     * generate salary for a particular number of days
     * @params salaryMonth
     * @params totalSalary
     * @params extraDays
     * @return salaryForExtraDays
     * *//*

    def calculateSalaryByDays(Double days,Double totalSalary,Date date){
        Double salary
        Integer numberOfDays = DateUtils.getNumberDaysInMonth(date)
        salary = (days * totalSalary)/numberOfDays
        return salary
    }


    */
/**
     * Get the list of designations for which start salary is not set but employee exists for each particular designation.
     * @return list of designations for which start salary is not set but employee exists for each particular designation.
     * *//*

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

    def getSalaryList(designation,fromSalary,toSalary,params){
        params.sort = 'designation.jobTitleName'
        params.order = 'asc'
        def salaryInstance
        if(designation && fromSalary && toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation) and s.startSalary between :fromSalary and :toSalary ",[designation: designation,fromSalary:fromSalary,toSalary:toSalary],params)

        }else if(designation && fromSalary ){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation) and s.startSalary >= :fromSalary ",[designation: designation,fromSalary:fromSalary],params)

        }else if(designation && toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation) and s.startSalary <= :toSalary ",[designation: designation,toSalary:toSalary],params)

        }else if(fromSalary && toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.startSalary between :fromSalary and :toSalary ",[fromSalary:fromSalary,toSalary:toSalary],params)

        }else if(fromSalary){
            salaryInstance=Salary.findAll("from Salary s where s.startSalary >= :fromSalary ",[fromSalary:fromSalary],params)

        }else if(toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.startSalary <= :toSalary ",[toSalary:toSalary],params)

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
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation) and s.startSalary between :fromSalary and :toSalary ",[designation: designation,fromSalary:fromSalary,toSalary:toSalary])

        }else if(designation && fromSalary ){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation) and s.startSalary >= :fromSalary ",[designation: designation,fromSalary:fromSalary])

        }else if(designation && toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation) and s.startSalary <= :toSalary ",[designation: designation,toSalary:toSalary])

        }else if(fromSalary && toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.startSalary between :fromSalary and :toSalary ",[fromSalary:fromSalary,toSalary:toSalary])

        }else if(fromSalary){
            salaryInstance=Salary.findAll("from Salary s where s.startSalary >= :fromSalary ",[fromSalary:fromSalary])

        }else if(toSalary){
            salaryInstance=Salary.findAll("from Salary s where s.startSalary <= :toSalary ",[toSalary:toSalary])

        }else if(designation){
            salaryInstance=Salary.findAll("from Salary s where s.designation in (:designation)",[designation:designation])
        }else{
            salaryInstance=Salary.list()

        }

        return salaryInstance

    }

    def getExtraAllowanceList(designation,fromSalary,toSalary,params){
        params.sort = 'designation.jobTitleName'
        params.order = 'asc'
        def amountInstance
        if(designation && fromSalary && toSalary){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.designation in (:designation) and e.amount between :fromSalary and :toSalary ",[designation: designation,fromSalary:fromSalary,toSalary:toSalary],params)

        }else if(designation && fromSalary ){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.designation in (:designation) and e.amount >= :fromSalary ",[designation: designation,fromSalary:fromSalary],params)

        }else if(designation && toSalary){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.designation in (:designation) and e.amount <= :toSalary ",[designation: designation,toSalary:toSalary],params)

        }else if(fromSalary && toSalary){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.amount between :fromSalary and :toSalary ",[fromSalary:fromSalary,toSalary:toSalary],params)

        }else if(fromSalary){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.amount >= :fromSalary ",[fromSalary:fromSalary],params)

        }else if(toSalary){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.amount <= :toSalary ",[toSalary:toSalary],params)

        }else if(designation){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.designation in (:designation)",[designation:designation],params)
        }else{
            amountInstance=ExtraAllowance.list(params)

        }

        return amountInstance
    }

    def getExtraAllowanceCount(designation,fromSalary,toSalary){
        def amountInstance
        if(designation && fromSalary && toSalary){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.designation in (:designation) and e.amount between :fromSalary and :toSalary ",[designation: designation,fromSalary:fromSalary,toSalary:toSalary])

        }else if(designation && fromSalary ){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.designation in (:designation) and e.amount >= :fromSalary ",[designation: designation,fromSalary:fromSalary])

        }else if(designation && toSalary){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.designation in (:designation) and e.amount <= :toSalary ",[designation: designation,toSalary:toSalary])

        }else if(fromSalary && toSalary){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.amount between :fromSalary and :toSalary ",[fromSalary:fromSalary,toSalary:toSalary])

        }else if(fromSalary){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.amount >= :fromSalary ",[fromSalary:fromSalary])

        }else if(toSalary){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.amount <= :toSalary ",[toSalary:toSalary])

        }else if(designation){
            amountInstance=ExtraAllowance.findAll("from ExtraAllowance e where e.designation in (:designation)",[designation:designation])
        }else{
            amountInstance=ExtraAllowance.list()

        }

        return amountInstance
    }
*/

}

import org.codehaus.groovy.grails.plugins.DomainClassGrailsPlugin
