package com.bayalpatra.hrm

import commons.BayalpatraConstants
import commons.DateUtils
import grails.transaction.Transactional
import groovy.sql.Sql
import org.apache.log4j.Logger
import org.codehaus.groovy.runtime.DateGroovyMethods

@Transactional
class LeaveService {
    public void updateLeaveBalanceReportOfEachEmployeeAfterLeaveApproved(EmployeeLeaveDetail employeeLeaveDetail){
        def currentYear = DateUtils.getYearFromDate(DateUtils.getCurrentDate())
        def hrmLeaveBalanceReport = LeaveBalanceReport.findByEmployeeAndYear(employeeLeaveDetail.employee,currentYear)
        if(hrmLeaveBalanceReport){
            if(employeeLeaveDetail.leaveType.leaveType.equals(BayalpatraConstants.SICK_LEAVE)){
                hrmLeaveBalanceReport.sickLeave = hrmLeaveBalanceReport?.sickLeave - employeeLeaveDetail?.leaveDifference
            } else if(employeeLeaveDetail.leaveType.leaveType.equals(BayalpatraConstants.FLOATING_LEAVE)){
                hrmLeaveBalanceReport.floatingLeave = hrmLeaveBalanceReport?.floatingLeave - employeeLeaveDetail?.leaveDifference
            } else if(employeeLeaveDetail.leaveType.leaveType.equals(BayalpatraConstants.PERSONAL_LEAVE)){
                hrmLeaveBalanceReport.personalLeave = hrmLeaveBalanceReport?.personalLeave - employeeLeaveDetail?.leaveDifference
            }
        }
    }

    public void updateLeaveBalanceReportOfEachEmployeeAfterLeaveCancelled(EmployeeLeaveDetail employeeLeaveDetail){
        def currentYear = DateUtils.getYearFromDate(DateUtils.getCurrentDate())
        def hrmLeaveBalanceReport = LeaveBalanceReport.findByEmployeeAndYear(employeeLeaveDetail.employee,currentYear)
        if(hrmLeaveBalanceReport){
            if(employeeLeaveDetail.leaveType.leaveType.equals(BayalpatraConstants.SICK_LEAVE)){
                hrmLeaveBalanceReport.sickLeave = hrmLeaveBalanceReport?.sickLeave + employeeLeaveDetail?.leaveDifference
            } else if(employeeLeaveDetail.leaveType.leaveType.equals(BayalpatraConstants.FLOATING_LEAVE)){
                hrmLeaveBalanceReport.floatingLeave = hrmLeaveBalanceReport?.floatingLeave + employeeLeaveDetail?.leaveDifference
            } else if(employeeLeaveDetail.leaveType.leaveType.equals(BayalpatraConstants.PERSONAL_LEAVE)){
                hrmLeaveBalanceReport.personalLeave = hrmLeaveBalanceReport?.personalLeave + employeeLeaveDetail?.leaveDifference
            }
        }
    }
/*

    */
/**
     * All methods within this service are to be made transactional.
     * All services have transaction demarcation enabled by default - to disable it, simply set the transactional property to false
     *//*

    static transactional = true
    //get the logger instance
    private static final Logger LOGGER = Logger.getLogger(LeaveService.class)

    //Inject dataSource object
    def dataSource

    // Inject dutyReportService
    def dutyRosterReportService

    // Inject employeeService
    def employeeService

    def calculateLeaveReport() {

        */
/**
         * 0.FOR EACH EMPLOYEE
         * 1.generate earnedLeave
         * 2.generate extraDays and extraTime (both in days in Double dataType)
         * 3.generate paidLeaveDays = earnedLeave + publicHoliday + maternityLeave + bereavementLeave
         * 4.generate unpaidLeaveDays = studyLeave + scholarshipLeave + minusLeave (earnedLeave after it completes)
         * 5. add the paidLeaveDays while calculating Salary in SalaryService
         * 6. deduct the unpaidLeaveDays while calculating SalaryService, ServiceDays, Allowance acquisition (??)
          * 7.display report with fields employeeName,leaveMonth,earnedLeave,extraDays,extraHour,paidLeaveDays,unpaidLeaveDays,balanceDays
         * 8.calculate balanceDays as earnedDays for year and deduct the leaveDays taken if its a PersonalLeave, SickLeave,etc.
         * *//*


        // global variable for collecting the date values for global access
        Date currentDate=DateUtils.getCurrentDate()
        Date leaveMonth = DateUtils.toMonthYearFormat(currentDate)
        Date previousMonth = DateUtils.toMonthYearFormat(getPreviousMonth())
        LOGGER.info('Inside Leave Report Calculation Method')
        try{
            def employeeDetails = Employee.findAll("from Employee e where e.status not in (:status)",[status:[BayalpatraConstants.TERMINATED,BayalpatraConstants.CLEARED]])
            for(Employee emp:employeeDetails){
//                if(emp.getStatus().equals(BayalpatraConstants.PERMANENT)||emp.getStatus().equals(BayalpatraConstants.CONTRACT)||emp.getStatus().equals(BayalpatraConstants.DAILY_WAGES)|| emp.getStatus().equals(BayalpatraConstants.VOLUNTEER)||emp.getStatus().equals(BayalpatraConstants.TEMPORARY)){
                LeaveReport previousMonthLeaveReport = LeaveReport.findByEmployeeAndLeaveDate(emp,previousMonth)
                def openingBalance = previousMonthLeaveReport?.balanceDays
                //calculates earnedLeave
                def blockAllowance=BlockAllowance.findByEmployee(emp).earnLeave
                def generatedEarnedLeave=0
                if(!blockAllowance){
                    generatedEarnedLeave=calculateEarnedLeaveDays(emp)
                }
                //calculates extraDay
                def generatedExtraDay = calculateExtraDays(emp,currentDate)
                //calculates extraHour
                def generatedExtraHours=calculateExtraHours(emp)
                //calculates paidLeaveDays
                def generatedPaidLeaveDays=calculatePaidLeaveDays(emp)
                //calculates unpaidLeaveDays
                def generatedUnpaidLeaveDays=calculateUnpaidLeaveDays(emp)
                //retrieve annual leave
                def generatedAnnualLeaveDays = calculateAnnualLeaveDays(emp)
                //calculate balanceLeaveDays
//                    def generatedBalanceDays=calculateBalanceDays(emp)
                def generatedBalanceDays = Parser.parseToDouble(openingBalance) + Parser.parseToDouble(generatedEarnedLeave) - Parser.parseToDouble(generatedPaidLeaveDays)

                LeaveReport leaveReport = new LeaveReport(employee:emp,leaveDate:leaveMonth,earnedLeave:generatedEarnedLeave,extraDay:generatedExtraDay,
                        extraTime:generatedExtraHours,paidLeave:generatedPaidLeaveDays,unpaidLeave:generatedUnpaidLeaveDays,annualLeaveDays:generatedAnnualLeaveDays,
                        balanceDays:generatedBalanceDays,openingBalance:openingBalance)
                leaveReport.save(failOnError: true)
            }
//            }
        }
        catch(Exception e){
            LOGGER.error('Exception:'+e.getMessage())
        }
    }

    */
/**
     * Generate EarnedLeave on the basis that:
     * an employee earns 2.5 days for every 30 days of service
     * @param employee
     * @return service
     * *//*

    def calculateEarnedLeaveDays(Employee emp){

        Date currentDate = DateUtils.getCurrentDate()
        Integer currentMonth=DateUtils.getMonthFromDate(DateUtils.getCurrentDate())
        Integer currentYear=DateUtils.getYearFromDate(DateUtils.getCurrentDate())

        Date joinDate = emp.getUpdatedJoinDate()
        Integer joinMonth = DateUtils.getMonthFromDate(joinDate)
        Integer joinYear = DateUtils.getYearFromDate(joinDate)

        Double earnedLeave=3
        def earnedLeaveInstance=EarnedLeaveSetting.findByDesignation(emp.designation)
        if(earnedLeaveInstance){
            earnedLeave=earnedLeaveInstance.earnedLeave
        }

        Double initialEarnedLeaveDays = earnedLeave
        Integer suspendedDays = 0
        Double noOfDaysInAMonth = DateUtils.getNumberDaysInMonth(currentDate)

        def suspendedRecord = SuspendedEmployeeDetails.findByEmployee(emp)

        if(suspendedRecord){

            Integer suspendedFromDateMonth = DateUtils.getMonthFromDate(suspendedRecord.startDate)
            Integer suspendedFromDateYear = DateUtils.getYearFromDate(suspendedRecord.startDate)
            Integer suspendedToDateMonth = DateUtils.getMonthFromDate(suspendedRecord.endDate)
            Integer suspendedToDateYear = DateUtils.getYearFromDate(suspendedRecord.endDate)

            //Three cases when both from and to date are in this month,
            // From date in this month but to date not in this month,
            // To date in this month but from date not in this month

            if(suspendedFromDateMonth == currentMonth && suspendedFromDateYear == currentYear && suspendedToDateMonth == currentMonth && suspendedToDateYear == currentYear){
                // compute the diffence between from and two days

                suspendedDays = DateUtils.getDaysFromTwoDates(suspendedRecord.startDate, suspendedRecord.endDate)

            }else if(suspendedFromDateMonth == currentMonth && suspendedFromDateYear == currentYear && suspendedToDateMonth != currentMonth){
                // compute difference between from date and end day of month

                Date lastDayOfThisMonth = DateUtils.getLastDateOfAnyMonth(currentDate)
                suspendedDays = DateUtils.getDaysFromTwoDates(suspendedRecord.startDate,lastDayOfThisMonth)

            }else if(suspendedToDateMonth == currentMonth && suspendedToDateYear == currentYear && suspendedFromDateMonth != currentMonth){
                //compute difference between to date and start day of month

                Date firstDayOfThisMonth = DateUtils.getFirstDateOfAnyMonth(currentDate)
                suspendedDays = DateUtils.getDaysFromTwoDates(firstDayOfThisMonth, suspendedRecord.endDate)
            }
        }


        //compute earned leave for days if joined on the month of leave calculation
        if(currentMonth == joinMonth && currentYear == joinYear){
            Double serviceDays=DateUtils.getServiceDays(joinDate)
            Integer fullEarnedLeaveDays
            if(joinDate != suspendedRecord.startDate){
                fullEarnedLeaveDays = DateUtils.getDaysFromTwoDates(joinDate,suspendedRecord.startDate)
            }
            if(suspendedRecord){
                initialEarnedLeaveDays = (earnedLeave - ((earnedLeave/noOfDaysInAMonth) * suspendedDays)) + (fullEarnedLeaveDays * (earnedLeave/noOfDaysInAMonth))
            }else{
                initialEarnedLeaveDays = serviceDays *(earnedLeave/noOfDaysInAMonth)
            }
        }

        //checks whether leaveReport has already been generated;
        // if no, assign earnedDays = 2.5 days if employee joined earlier; OR 2.5/noOfDaysInMonth * serviceDays if joined this month
        // if yes, assign balanceDays of last month to earned days of this month
        Double earnedLeaveDays = 0.0
        Double earnedLeaveWhenSuspended

        def leaveReportList=LeaveReport.executeQuery("FROM LeaveReport lr WHERE lr.employee.id="+emp.id)

        if(!leaveReportList){        // leave report not generated
            earnedLeaveDays=initialEarnedLeaveDays
        }
        else{                        //leave report already generated
            for(LeaveReport lr:leaveReportList){                       //TO DO:- Not get all list but only previous months leave for employee
                Date leaveDate=lr.leaveDate
                Integer leaveYear =DateUtils.getYearFromDate(leaveDate)
                Integer leaveMonth=DateUtils.getMonthFromDate(leaveDate)

                */
/* If some person is suspended for 15 days in a month then earned leave should be 1.29 instead of 2.5
                 (2.5-(2.5/31*15))=1.29
                 *//*


                if(suspendedRecord){
                    if(currentYear == leaveYear && currentMonth == leaveMonth+1){
                        earnedLeaveDays = lr.balanceDays
                        earnedLeaveWhenSuspended = (earnedLeave - ((earnedLeave/noOfDaysInAMonth) * suspendedDays))
                        earnedLeaveDays = earnedLeaveDays + earnedLeaveWhenSuspended
                    }else if(currentYear == leaveYear +1 && currentMonth == leaveMonth-11){
                        earnedLeaveDays = lr.balanceDays
                        earnedLeaveWhenSuspended = (earnedLeave - ((earnedLeave/noOfDaysInAMonth) * suspendedDays))
                        earnedLeaveDays = earnedLeaveDays + earnedLeaveWhenSuspended
                    }
                }else{
                    // add outstanding leaves to 2.5 to make this months earned leave
                    // for example, current leaveMonth is December-2011 and we are retrieving balanceDays of November-2011
                    if(currentYear == leaveYear && currentMonth == leaveMonth+1){
                        earnedLeaveDays = lr.balanceDays
                        earnedLeaveDays +=earnedLeave
                    }
                    // for example, current leaveMonth is January-2012 and we are retrieving balanceDays of December-2011
                    else if(currentYear == leaveYear +1 && currentMonth == leaveMonth-11){
                        earnedLeaveDays = lr.balanceDays
                        earnedLeaveDays +=earnedLeave
                    }
                }
            }
        }

        return earnedLeaveDays
    }

    */
/**
     * Generates extra days on the basis that
     * an employee earns 1 extra day for working 2 shifts, 2 days for working 3 shifts;
     * plus 1 day if had a shift in DayOff or NightOff or FestivalOff
     * minus 1 day if taken Substitute Leave
     * @param employee
     * @return extraDay
     **//*

    def calculateExtraDays(Employee emp,Date date){
        //        Date currentDate = DateUtils.getCurrentDate()
        Date firstDateOfMonth=DateUtils.getFirstDateOfAnyMonth(date)
        def startOfMonth=DateUtils.dateToString(firstDateOfMonth)
        Date lastDateOfMonth=DateUtils.getLastDateOfAnyMonth(date)
        def lastOfMonth=DateUtils.dateToString(lastDateOfMonth)
        Integer totalExtraDays = 0
        */
/*def monthlyDutyRosterList =DutyRoster.executeQuery("FROM DutyRoster dr WHERE dr.employee.id='"+emp.id+"'AND dr.date >='"+startOfMonth+"'AND dr.date <='"+lastOfMonth+"'")
       for(DutyRoster dr:monthlyDutyRosterList){
       def extraDayFromDR=dutyRosterReportService.getExtraDayLeaveDayAndShift(dr)
       totalExtraDays = totalExtraDays +  extraDayFromDR[1]
       }
       return totalExtraDays*//*


        def monthlyDRList = HrmMonthlyRosterReport.executeQuery("FROM HrmMonthlyRosterReport hmr WHERE hmr.employee.id='"+emp.id+"' AND hmr.createdDate >='"+startOfMonth+"'AND hmr.createdDate <='"+lastOfMonth+"'")
        for(HrmMonthlyRosterReport monthlyReport :monthlyDRList){
            def extraDayFromMonthlyReport = monthlyReport.getExtra()
            totalExtraDays = totalExtraDays + extraDayFromMonthlyReport
        }
        return totalExtraDays
    }

    */
/**
     * Generate Extra Day as a difference between dutyRoster workdays and timeCard workDays
     * @param employee
     * @return extraHours
     **//*


    def calculateExtraHours(Employee emp){
        // global variable for collecting the date values for global access
        Date currentDate = DateUtils.getCurrentDate()
        Date firstDateOfMonth=DateUtils.getFirstDateOfAnyMonth(currentDate)
        def startOfMonth=DateUtils.dateToString(firstDateOfMonth)
        Date lastDateOfMonth=DateUtils.getLastDateOfAnyMonth(currentDate)
        def lastOfMonth=DateUtils.dateToString(lastDateOfMonth)
        def sql=new Sql(dataSource)
        Double extraHours
        //collects totalHoursOfPresenceFromTimeCard for last week
        def totalPresence=Timecard.executeQuery("SELECT COALESCE(SUM(totalHours),0) FROM Timecard WHERE employee.id="+emp.id+"AND punchInTime >='"+startOfMonth+"' AND punchInTime <='"+lastOfMonth+"' ")
        Double totalHoursOfPresenceFromTimeCard = totalPresence.get(0)
        //collects totalHoursOfWorkShiftFromDutyRoster for last week
        String queryForDutyRoster="SELECT COALESCE(SUM(morning),0) AS morningTotal, COALESCE(SUM(DAY),0) AS dayTotal, COALESCE(SUM(evening),0) AS eveningTotal, COALESCE(SUM(night),0) AS nightTotal FROM duty_roster WHERE employee_id=" + emp.id + " AND date >='"+ startOfMonth + "' AND date <='"+ lastOfMonth + "'"
        def resultForDutyRoster=sql.firstRow(queryForDutyRoster)
        Double morningTotal=resultForDutyRoster.get("morningTotal")
        Double dayTotal=resultForDutyRoster.get("dayTotal")
        Double eveningTotal=resultForDutyRoster.get("eveningTotal")
        Double nightTotal=resultForDutyRoster.get("nightTotal")
        Double totalHoursOfWorkShiftFromDR = (getTotalHoursByShift(BayalpatraConstants.MORNING_SHIFT) * morningTotal) + (getTotalHoursByShift(BayalpatraConstants.DAY_SHIFT) * dayTotal) + (getTotalHoursByShift(BayalpatraConstants.EVENING_SHIFT) * eveningTotal) + (getTotalHoursByShift(BayalpatraConstants.NIGHT_SHIFT) * nightTotal)
        //checks whether employee worked more than allocated time or less than that
        if(totalHoursOfPresenceFromTimeCard >= totalHoursOfWorkShiftFromDR){
            extraHours=totalHoursOfPresenceFromTimeCard - totalHoursOfWorkShiftFromDR
        }
        else{
            extraHours = totalHoursOfWorkShiftFromDR - totalHoursOfPresenceFromTimeCard
            extraHours=extraHours*(-1)
        }
        return extraHours
    }

    */
/**
     * Generates total Paid Leave Days
     * @param emp
     * @return Paid Leave Days in {@link Double} format
     *//*

    def calculatePaidLeaveDays(Employee emp){
        Date currentDate=DateUtils.getCurrentDate()
        Double paidLeaveDays = getPaidUnpaidLeaveCount(BayalpatraConstants.PAID_LEAVE,emp,currentDate,BayalpatraConstants.LEAVE_TYPE_ALL,true,false)
        return paidLeaveDays
    }

    */
/**
     * Generates total Unpaid Leave Days
     * @param emp
     * @return Unpaid Leave Days in {@link Double} format
     *//*

    def calculateUnpaidLeaveDays(Employee emp){
        Date currentDate=DateUtils.getCurrentDate()
        Double unpaidLeaveDays = getPaidUnpaidLeaveCount(BayalpatraConstants.UNPAID_LEAVE,emp,currentDate,BayalpatraConstants.LEAVE_TYPE_ALL,true,false)
        return unpaidLeaveDays
    }


    */
/**
     * Retrieve Annual leave
     * @param employee
     * @return annual leave days in {@link Double} format
     * *//*


    def calculateAnnualLeaveDays(Employee emp){
        Date currentDate = DateUtils.getCurrentDate()
        Double annualLeaveDays=getPaidUnpaidLeaveCount(BayalpatraConstants.PAID_LEAVE,emp,currentDate, BayalpatraConstants.ANNUAL_LEAVE,true,false)
        return annualLeaveDays
    }
    */
/**
     * Generates balance days as earnedLeaveDays minus (personal leaveDays) taken
     * @param emp
     * @return Balance Leave Days in {@link Double} format
     *//*

    def calculateBalanceDays(Employee emp){
        def earnedLeaveDays=calculateEarnedLeaveDays(emp)
        Date currentDate=DateUtils.getCurrentDate()
        Double personalLeaveDays=getPaidUnpaidLeaveCount(BayalpatraConstants.PAID_LEAVE,emp,currentDate, BayalpatraConstants.ANNUAL_LEAVE,true,false)
        Double balanceLeaveDays = earnedLeaveDays - personalLeaveDays
        return balanceLeaveDays
    }

    */
/**
     * Generates total hours of work for any given shift, ie morning,day,evening or night
     * @param shiftType
     * @return Allocated hours for the given shift
     *//*

    def getTotalHoursByShift(String shiftType) {
        Double hoursInAShift = 0.0
        def shiftSetting = ShiftSetting.findByShift(shiftType)
        if(shiftSetting){
            Double shiftFromHours =shiftSetting.fromShift.getTime()/(60*60*1000)
            Double shiftToHours =shiftSetting.toShift.getTime()/(60*60*1000)
            hoursInAShift = shiftToHours - shiftFromHours
        }
        return hoursInAShift
    }

    */
/**
     * Get Paid or Unpaid leave days of a particular employee
     * @param paidOrUnpaid
     * @param empId
     * @param date
     * @return Total paid or unpaid leave in {@link Integer} format
     *//*

    def getPaidUnpaidLeaveCount(String paidOrUnpaid, Employee emp, Date date, String leaveType,Boolean isFullMonth,Boolean takeFirst){
        Double totalLeaveDays = 0.0
        Date firstDateOfMonth
        Date lastDateOfMonth
        if(isFullMonth){
            firstDateOfMonth=DateUtils.getFirstDateOfAnyMonth(date)
            lastDateOfMonth=DateUtils.getLastDateOfAnyMonth(date)
        }else{
            if (takeFirst){
                firstDateOfMonth=DateUtils.getFirstDateOfAnyMonth(date)
                lastDateOfMonth=date
            }else{
                firstDateOfMonth=date
                lastDateOfMonth=DateUtils.getLastDateOfAnyMonth(date)
            }

        }
        def employeeLeaveDetail = EmployeeLeaveDetail.executeQuery("SELECT eld FROM EmployeeLeaveDetail eld, LeaveType lt WHERE eld.leaveType.id = lt.id AND lt.paidUnpaid='" + paidOrUnpaid + "'AND eld.status='" +BayalpatraConstants.LEAVE_APPROVED+"'AND eld.employee.id ='" + emp.id + "' AND eld.leaveType.leaveType LIKE '" + leaveType + "'")
        if(employeeLeaveDetail){
            for(EmployeeLeaveDetail empLeaveDetail : employeeLeaveDetail){
                Date fromDate=empLeaveDetail.fromDate
                Date toDate=empLeaveDetail.toDate
                if(fromDate <=lastDateOfMonth && toDate>=firstDateOfMonth){
                    // for example current month is: October && leave from: Sept 25 - October 10, 2011
                    // take leave from October 01 -October 10; ignore September 25-lastDay -already calculated
                    if(fromDate<firstDateOfMonth && toDate<=lastDateOfMonth){
                        Date endDate=DateUtils.formatDateToYYYYMMDD(empLeaveDetail.getToDate())
                        if(empLeaveDetail.getLeaveDays() != 0){
                            totalLeaveDays += DateUtils.getDaysFromTwoDates(firstDateOfMonth, endDate)
                            totalLeaveDays -= empLeaveDetail.getLeaveDays()
                        }else{
                            totalLeaveDays += DateUtils.getDaysFromTwoDates(firstDateOfMonth, endDate)
                        }
                    }
                    // for example current month is: October && leave from: October 25 - November 10, 2011
                    // take leave from October 25 -October lastDay; ignore November 01-November 10 -to be calculated later
                    else if(toDate>lastDateOfMonth && fromDate>=firstDateOfMonth ){
                        Date startDate=DateUtils.formatDateToYYYYMMDD(empLeaveDetail.getFromDate())
                        if(empLeaveDetail.getLeaveDays() != 0){
                            totalLeaveDays += DateUtils.getDaysFromTwoDates(startDate, lastDateOfMonth)
                            totalLeaveDays -= empLeaveDetail.getLeaveDays()
                        }else{
                            totalLeaveDays += DateUtils.getDaysFromTwoDates(startDate, lastDateOfMonth)
                        }
                    }
                    // if leave is taken between October 01 and October lastDay and current month is October
                    else if(fromDate>=firstDateOfMonth && toDate<=lastDateOfMonth){
                        Date startDate=DateUtils.formatDateToYYYYMMDD(empLeaveDetail.getFromDate())
                        Date endDate=DateUtils.formatDateToYYYYMMDD(empLeaveDetail.getToDate())
                        if(empLeaveDetail.getLeaveDays() != 0){
                            totalLeaveDays += DateUtils.getDaysFromTwoDates(startDate,endDate)
                            totalLeaveDays -= empLeaveDetail.getLeaveDays()
                        }else{
                            totalLeaveDays += DateUtils.getDaysFromTwoDates(startDate,endDate)
                        }
                    }
                }
            }
        }
        return totalLeaveDays
    }


    */
/**
     * TODO:implement this function to update the annual leave balance and call from the cron job
     *//*

    public void updateLeaveBalanceReportOfAllEmployeeAfterEveryYear(){
        //TODO: create a cron and call this method....need to implement this method

        if(serviceDays >= BayalpatraConstants.ONEYEAR){
            hrmLeaveBalanceReport.employee = emp
            hrmLeaveBalanceReport.year = DateUtils.getYearFromDate(DateUtils.getCurrentDate())
            hrmLeaveBalanceReport.floatingLeave = getAnnualLeaveDaysByLeaveType(BayalpatraConstants.FLOATING_LEAVE)
            hrmLeaveBalanceReport.sickLeave = getAnnualLeaveDaysByLeaveType(BayalpatraConstants.SICK_LEAVE)
            hrmLeaveBalanceReport.personalLeave = getAnnualLeaveDaysByLeaveType(BayalpatraConstants.PERSONAL_LEAVE)
            hrmLeaveBalanceReport.save(failOnError:true)
        }

        def empList = employeeService.getEmpByStatus();

    }

    */
/**
     * After each employee registration update his/her leave balance by employee join date. Get the leave days from the leave type settings table.
     * @param emp
     *//*

    public void updateLeaveBalanceReportOfEachEmployeeAfterRegistration(Employee emp,String action,int probationDays){
        Integer serviceDays = DateUtils.getDaysFromTwoDates(emp.updatedJoinDate, DateUtils.stringToDate((DateUtils.getYearFromDate(DateUtils.getCurrentDate())+1)+"-01-01"))-probationDays
        HrmLeaveBalanceReport hrmLeaveBalanceReport = new HrmLeaveBalanceReport()
        if(emp.getStatus().equals(BayalpatraConstants.PROBATION)){

            hrmLeaveBalanceReport.employee = emp
            hrmLeaveBalanceReport.year = DateUtils.getYearFromDate(DateUtils.getCurrentDate())
            hrmLeaveBalanceReport.floatingLeave = 0
            hrmLeaveBalanceReport.sickLeave = 0
            hrmLeaveBalanceReport.personalLeave = emp.getVolunteerDays()/BayalpatraConstants.ONEMONTH
            hrmLeaveBalanceReport.save(failOnError:true)
        }else{

            if(action==BayalpatraConstants.CREATE){
                hrmLeaveBalanceReport.employee = emp
                hrmLeaveBalanceReport.year = DateUtils.getYearFromDate(DateUtils.getCurrentDate())
                hrmLeaveBalanceReport.floatingLeave = (getAnnualLeaveDaysByLeaveType(BayalpatraConstants.FLOATING_LEAVE)/BayalpatraConstants.ONEYEAR)*serviceDays
                hrmLeaveBalanceReport.sickLeave = (getAnnualLeaveDaysByLeaveType(BayalpatraConstants.SICK_LEAVE)/BayalpatraConstants.ONEYEAR)*serviceDays
                hrmLeaveBalanceReport.personalLeave = (getAnnualLeaveDaysByLeaveType(BayalpatraConstants.PERSONAL_LEAVE)/BayalpatraConstants.ONEYEAR)*serviceDays
                hrmLeaveBalanceReport.save(failOnError:true)
            }else{
                HrmLeaveBalanceReport empLeaveReport = HrmLeaveBalanceReport.findByEmployee(emp)
                empLeaveReport.year = DateUtils.getYearFromDate(DateUtils.getCurrentDate())
                empLeaveReport.floatingLeave = (getAnnualLeaveDaysByLeaveType(BayalpatraConstants.FLOATING_LEAVE)/BayalpatraConstants.ONEYEAR)*serviceDays
                empLeaveReport.sickLeave = (getAnnualLeaveDaysByLeaveType(BayalpatraConstants.SICK_LEAVE)/BayalpatraConstants.ONEYEAR)*serviceDays
                empLeaveReport.personalLeave = (getAnnualLeaveDaysByLeaveType(BayalpatraConstants.PERSONAL_LEAVE)/BayalpatraConstants.ONEYEAR)*serviceDays
            }


        }
    }

    */
/**
     * After the employee's leave is approved, then call this function to adjust the balance leave
     * @param employeeLeaveDetail
     *//*

    public void updateLeaveBalanceReportOfEachEmployeeAfterLeaveApproved(EmployeeLeaveDetail employeeLeaveDetail){
        def currentYear = DateUtils.getYearFromDate(DateUtils.getCurrentDate())
        def hrmLeaveBalanceReport = HrmLeaveBalanceReport.findByEmployeeAndYear(employeeLeaveDetail.employee,currentYear)
        if(hrmLeaveBalanceReport){
            if(employeeLeaveDetail.leaveType.leaveType.equals(BayalpatraConstants.SICK_LEAVE)){
                hrmLeaveBalanceReport.sickLeave = hrmLeaveBalanceReport?.sickLeave - employeeLeaveDetail?.leaveDifference
            } else if(employeeLeaveDetail.leaveType.leaveType.equals(BayalpatraConstants.FLOATING_LEAVE)){
                hrmLeaveBalanceReport.floatingLeave = hrmLeaveBalanceReport?.floatingLeave - employeeLeaveDetail?.leaveDifference
            } else if(employeeLeaveDetail.leaveType.leaveType.equals(BayalpatraConstants.PERSONAL_LEAVE)){
                hrmLeaveBalanceReport.personalLeave = hrmLeaveBalanceReport?.personalLeave - employeeLeaveDetail?.leaveDifference
            }
        }
    }

    public void updateLeaveBalanceReportOfEachEmployeeAfterLeaveCancelled(EmployeeLeaveDetail employeeLeaveDetail){
        def currentYear = DateUtils.getYearFromDate(DateUtils.getCurrentDate())
        def hrmLeaveBalanceReport = HrmLeaveBalanceReport.findByEmployeeAndYear(employeeLeaveDetail.employee,currentYear)
        if(hrmLeaveBalanceReport){
            if(employeeLeaveDetail.leaveType.leaveType.equals(BayalpatraConstants.SICK_LEAVE)){
                hrmLeaveBalanceReport.sickLeave = hrmLeaveBalanceReport?.sickLeave + employeeLeaveDetail?.leaveDifference
            } else if(employeeLeaveDetail.leaveType.leaveType.equals(BayalpatraConstants.FLOATING_LEAVE)){
                hrmLeaveBalanceReport.floatingLeave = hrmLeaveBalanceReport?.floatingLeave + employeeLeaveDetail?.leaveDifference
            } else if(employeeLeaveDetail.leaveType.leaveType.equals(BayalpatraConstants.PERSONAL_LEAVE)){
                hrmLeaveBalanceReport.personalLeave = hrmLeaveBalanceReport?.personalLeave + employeeLeaveDetail?.leaveDifference
            }
        }
    }

    */
/**
     * Get the leave days by leave type
     * @param leaveType
     * @return leave Days in <code>Double</code> format
     *//*

    public Double getAnnualLeaveDaysByLeaveType(String leaveType){
        def leaveDays = LeaveType.findByLeaveType(leaveType)
        return leaveDays.Days
    }

    */
/**
     * Get SORTED employee balance leave report for DW
     * @param params
     * @return list of leaves for each employee
     *//*

    def getEmployeeLeaveBalance(params) {
        def max = params.max
        def offset = Integer.valueOf(params.offset)
        def leaveBalanceList = HrmLeaveBalanceReport.findAll("from HrmLeaveBalanceReport h order by h.year desc,h.employee.firstName asc",[max:max,offset:offset])
        return leaveBalanceList
    }

    def getCountEmployeeLeaveBalance(){
        def count = HrmLeaveBalanceReport.executeQuery("SELECT COUNT(*) FROM HrmLeaveBalanceReport h")
        return count[0]


    }

    def getMonthList(){
        def leaveMonthList = EmployeeLeaveDetail.executeQuery("SELECT DISTINCT(SUBSTRING(l.fromDate,1,7)) as leaveMonth from EmployeeLeaveDetail l")
        return leaveMonthList
    }

    def getYearList(){
        def leaveYearList = EmployeeLeaveDetail.executeQuery("SELECT DISTINCT(SUBSTRING(l.fromDate,1,4)) as leaveYear from EmployeeLeaveDetail l")
        return leaveYearList
    }

    def filteredLeaveDetails(filterDept, filterUnit, filterYear, filterMonth){
        def empLeaveList = EmployeeLeaveDetail.findAll()
        def filteredList = []
        if(filterUnit=='undefined') filterUnit=null
        if(filterDept=='undefined') filterDept=null
        if(filterYear=='undefined') filterYear=null
        if(filterMonth=='undefined') filterMonth=null
        if (filterDept){
            empLeaveList.each{empLeave->
                def refDept = Departments.findById(filterDept)
                if(employeeService.checkIfEmpIsInDepartment(empLeave.employee,refDept) && !filteredList.contains(empLeave)) filteredList.add(empLeave)
            }
            empLeaveList=filteredList
        }else if (filterUnit){
            empLeaveList.each{empLeave->
                if(empLeave.employee.unit?.id == Parser.parseToDouble(filterUnit) && !filteredList.contains(empLeave)){
                    filteredList.add(empLeave)
                }
            }
            empLeaveList=filteredList
        }

        if (filterMonth){
            filteredList=[]
            empLeaveList.each{empLeave->
                def compareMonth = empLeave.fromDate.toString().substring(0,7)
                if(compareMonth == filterMonth && !filteredList.contains(empLeave)) filteredList.add(empLeave)
            }
            empLeaveList=filteredList
        }

        if (filterYear){
            filteredList=[]
            empLeaveList.each{empLeave->
                def compareYear = empLeave.fromDate.toString().substring(0,4)
                if (compareYear == filterYear && !filteredList.contains(empLeave)) filteredList.add(empLeave)
            }
        }
        if (!filterDept && !filterUnit && !filterMonth && !filterYear) filteredList=empLeaveList
        return filteredList
    }

    def filteredLeaveDetails(filterDept, filterUnit, filterYear, filterMonth, empList){
        def resultList=[]
        def allList = filteredLeaveDetails(filterDept, filterUnit, filterYear, filterMonth)
        empList.each{emp->
            allList.each{empLeave->
                if(empLeave.employee.id==emp.id) resultList.add(empLeave)
            }
        }
        return resultList
    }

    def getListForLeaveFilter(criteria,fromDate,toDate,params,max){
        def leaveType=LeaveType.findAll("from LeaveType  where leaveType in (:leaveType)",[leaveType:criteria])
        params.order="desc"
        params.sort="fromDate"
        if (max){
            params.max= max
        }
        def leaveInstance
        if(fromDate && toDate){
            leaveInstance=EmployeeLeaveDetail.executeQuery("select el from EmployeeLeaveDetail el where el.leaveType in (:leaveType) and date_format(el.fromDate,'%Y-%m-%d') between :fromdate and :toDate order by " +  params.sort +" "+ params.order,[leaveType:leaveType,fromdate:fromDate,toDate:toDate],params)

        }
        else if(fromDate){

            leaveInstance=EmployeeLeaveDetail.executeQuery("select el from EmployeeLeaveDetail el where el.leaveType in (:leaveType) and date_format(el.fromDate,'%Y-%m-%d')>=:fromdate order by " +  params.sort +" "+ params.order,[leaveType:leaveType,fromdate:fromDate],params)
        }else if(toDate){
            leaveInstance=EmployeeLeaveDetail.executeQuery("select el from EmployeeLeaveDetail el where el.leaveType in (:leaveType) and date_format(el.fromDate,'%Y-%m-%d')<=:todate order by " +  params.sort +" "+ params.order,[leaveType:leaveType,todate:toDate],params)
        }
        else{
            leaveInstance=EmployeeLeaveDetail.executeQuery("select el from EmployeeLeaveDetail el where el.leaveType in (:leaveType) order by " +  params.sort +" "+ params.order,[leaveType:leaveType],params)
        }
        return leaveInstance
    }

    def getCountListForLeaveFilter(criteria,fromDate,toDate){
        def leaveType=LeaveType.findAll("from LeaveType  where leaveType in (:leaveType)",[leaveType:criteria])

        def leaveInstance
        if(fromDate && toDate){
            leaveInstance=EmployeeLeaveDetail.executeQuery("select el from EmployeeLeaveDetail el where el.leaveType in (:leaveType) and date_format(el.fromDate,'%Y-%m-%d') between :fromdate and :toDate",[leaveType:leaveType,fromdate:fromDate,toDate:toDate])

        }
        else if(fromDate){

            leaveInstance=EmployeeLeaveDetail.executeQuery("select el from EmployeeLeaveDetail el where el.leaveType in (:leaveType) and date_format(el.fromDate,'%Y-%m-%d')>=:fromdate",[leaveType:leaveType,fromdate:fromDate])
        }else if(toDate){
            leaveInstance=EmployeeLeaveDetail.executeQuery("select el from EmployeeLeaveDetail el where el.leaveType in (:leaveType) and date_format(el.fromDate,'%Y-%m-%d')<=:todate ",[leaveType:leaveType,todate:toDate])
        }
        else{
            leaveInstance=EmployeeLeaveDetail.executeQuery("select el from EmployeeLeaveDetail el where el.leaveType in (:leaveType) ",[leaveType:leaveType])
        }
        return leaveInstance.size()
    }

//    def createReportMapForLeave(eachReport){
//        def recMap=[:]
//        def date
//        date=DateGroovyMethods.format(eachReport?.fromDate, 'yyyy-MM-dd')
//        if(eachReport?.toDate){
//            date+=" to "+DateGroovyMethods.format(eachReport?.toDate, 'yyyy-MM-dd')
//        }
//
//        recMap.putAt('employee',eachReport.employee)
//        recMap.putAt('designation',eachReport.employee?.designation)
//        recMap.putAt('departments',eachReport.employee?.departments)
//        recMap.putAt('reason',eachReport.leaveReason)
//        recMap.putAt('date',date)
//        recMap.putAt('days',eachReport.leaveDifference)
//        return recMap
//    }

    def createReportMapForLeave(eachReport){
        def recMap=[:]
        def unitDepartment
        def reason
        def dateFrom=DateGroovyMethods.format(eachReport?.fromDate, 'yyyy-MM-dd')
        def dateTo=DateGroovyMethods.format(eachReport?.toDate, 'yyyy-MM-dd')
        if(eachReport?.employee?.unit){
            unitDepartment=eachReport.employee?.unit
        }else{

            unitDepartment=eachReport.employee?.departments
        }
        if(eachReport.leaveType.toString().substring(0,5)=="Deput"){
            reason=eachReport.leaveType
        }else{
            reason=eachReport.leaveDifference
        }


        recMap.putAt('employee',eachReport.employee)
        recMap.putAt('designation',eachReport.employee?.designation)
        recMap.putAt('departments',unitDepartment)
        recMap.putAt('dateFrom',dateFrom)
        recMap.putAt('dateTo',dateTo)
        recMap.putAt('days',reason)
        recMap.putAt('reason',eachReport?.leaveReason)
        return recMap
    }


    def getPreviousMonth(){
        Date currDate = DateUtils.getCurrentDate()
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(currDate)
        calendar.add(Calendar.MONTH, -1) // this function add() rolls month such as jan to dec ie. by 1 unit ; ALSO rolls year 2012 jan to 2011 dec
        Date yearMonthDateOnly = DateUtils.toMonthYearFormat(calendar.getTime())
        return yearMonthDateOnly
    }

*/

}
