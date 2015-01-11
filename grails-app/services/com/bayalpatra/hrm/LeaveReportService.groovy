package com.bayalpatra.hrm

import grails.transaction.Transactional
import org.codehaus.groovy.runtime.DateGroovyMethods

@Transactional
class LeaveReportService {

    def getLeaveReport(employee,params){
        def leaveReport
        if(employee){
            leaveReport = LeaveReport.findAll("from LeaveReport lr where lr.employee in (:employee)",[employee:employee],params)
        }
        return leaveReport
    }

    def getLeaveReportByEmpAndMonth(employee,leaveMonth,params) {
        def leaveReportList
        if(employee){
            leaveReportList = LeaveReport.findAll("from LeaveReport lr where SUBSTRING(lr.leaveDate,1,7)=:leaveMonth and lr.employee=:employee",[employee:employee,leaveMonth:leaveMonth],params)
        }
        return leaveReportList
    }
    def getLeaveReportByDeptAndMonth(dept,leaveMonth,params) {
        def leaveReportList
        if(dept){
            leaveReportList = LeaveReport.findAll("from LeaveReport lr where SUBSTRING(lr.leaveDate,1,7)=:leaveMonth and lr.employee.departments in (:dept)",[dept:dept,leaveMonth:leaveMonth],params)
        }
        return leaveReportList
    }
    def getLeaveCountByDeptAndMonth(dept,leaveMonth) {
        def leaveReportList
        if(dept){
            leaveReportList = LeaveReport.findAll("from LeaveReport lr where SUBSTRING(lr.leaveDate,1,7)=:leaveMonth and lr.employee.departments in (:dept)",[dept:dept,leaveMonth:leaveMonth])
        }
        return leaveReportList.size()
    }

    def getLeaveReportByUnitAndMonth(unit,leaveMonth,params) {
        def leaveReportList

        String query = "from LeaveReport lr where lr.employee.unit in (:unit)"

        if(leaveMonth){
            query += " and SUBSTRING(mr.createdDate,1,7)=:reportMonth order by employee.firstName asc"
            leaveReportList = LeaveReport.findAll(query,[unit:unit,reportMonth:leaveMonth])
        }else{
            query += " order by employee.firstName asc"
            leaveReportList = LeaveReport.findAll(query,[unit:unit],params)
        }


        return leaveReportList
    }

    def getLeaveReportByEmp(employee,params) {
        def leaveReportList
        if(employee){
            leaveReportList = LeaveReport.findAll("from LeaveReport lr where lr.employee=:employee order by lr.leaveDate desc",[employee:employee],params)
        }
        return leaveReportList
    }

    def getLeaveReportByMonth(emp,leaveMonth,params) {
        def leaveReportList = LeaveReport.findAll("from LeaveReport lr where SUBSTRING(lr.leaveDate,1,7)=:leaveMonth and lr.employee in (:employee) order by lr.employee.firstName asc",[leaveMonth:leaveMonth,employee: emp],params)
        return leaveReportList
    }

    def getLeaveReportByDepartment(department,params){
        def leaveReport=LeaveReport.findAll("from LeaveReport lr where lr.employee.departments in (:department) order by employee.firstName asc",[department:department],params)
        return leaveReport
    }

    def getCountByDepartment(department){
        def count=0
        if(department){
            def countAll =LeaveReport.executeQuery("select count(*) from LeaveReport lr where lr.employee.departments in (:department) ",[department:department])
            count=countAll[0]
        }
        return  count
    }

    def getLeaveReportByUnit(unit){
        def leaveReport=LeaveReport.findAll("from LeaveReport lr where lr.employee.unit in (:unit) order by employee.firstName asc",[unit:unit])
        return leaveReport
    }

    def getCountByUnit(unit){
        def count=0
        if(unit){
            def countAll =LeaveReport.executeQuery("select count(*) from LeaveReport lr where lr.employee.unit in (:unit) ",[unit:unit])
            count=countAll[0]
        }
        return  count
    }

    def getCountByEmpAndMonth(employee,leaveMonth){
        def count=0
        if(employee){
            def countAll =LeaveReport.executeQuery("select count(*) from LeaveReport lr where SUBSTRING(lr.leaveDate,1,7)=:leaveMonth and lr.employee=:employee",[leaveMonth:leaveMonth,employee:employee])
            count=countAll[0]
        }
        return  count
    }

    def getCountByEmp(employee){
        def count=0
        if(employee){
            def countAll =LeaveReport.executeQuery("select count(*) from LeaveReport lr where lr.employee=:employee",[employee:employee])
            count=countAll[0]
        }
        return count
    }

    def getCountByRole(employee){
        def count=0
        if(employee){
            def countAll = LeaveReport.executeQuery("select count(*) from LeaveReport lr where lr.employee in (:employee)",[employee:employee])
            count=countAll[0]
        }
        return count
    }

    def getCountByMonth(emp,leaveMonth){
        def count =LeaveReport.executeQuery("select count(*) from LeaveReport lr where SUBSTRING(lr.leaveDate,1,7)=:leaveMonth and lr.employee in (:employee)",[leaveMonth:leaveMonth,employee: emp])
        return count[0]
    }

    /**
     * generates Leave Month
     * */
    def populateLeaveMonth(){
        def leaveMonthList = LeaveReport.executeQuery("SELECT DISTINCT(SUBSTRING(lr.leaveDate,1,7)) as leaveMonth from LeaveReport lr")
        return leaveMonthList
    }

    def getReportByEmployeeAndReportMonthLeave(employee,beginDate,params){
        def reportList
        try{
            if(beginDate){
                reportList = LeaveReport.findAll("FROM LeaveReport dr where (SUBSTRING(dr.leaveDate,1,7))=:leaveDate and dr.employee in (:employee) order by dr.employee.firstName asc",[leaveDate:beginDate,employee:employee],params)
            }else{
                reportList = LeaveReport.findAll("FROM LeaveReport d WHERE d.employee in (:employee) order by d.employee.firstName asc",[employee:employee],params)
            }
        }catch(Exception e){
            e.getMessage()
        }

        return reportList
    }

    def getLeaveBalanceForReport(employee,startDate,endDate,params){

        def nameOrder="lr.leaveDate asc"
        def dateOrder="lr.employee.firstName asc"
        def leaveReport
        if(employee){
            if(startDate && endDate){
                leaveReport = LeaveReport.findAll("from LeaveReport lr where lr.employee in (:employee) and date_format(lr.leaveDate,'%Y-%m-%d') between :fromDate and :toDate order by "+dateOrder+", "+nameOrder,[employee:employee,fromDate:startDate,toDate:endDate],params)

            }else if(startDate){
                leaveReport = LeaveReport.findAll("from LeaveReport lr where lr.employee in (:employee) and date_format(lr.leaveDate,'%Y-%m-%d') >=:fromDate order by "+dateOrder+", "+nameOrder,[employee:employee,fromDate:startDate],params)

            }else if(endDate){
                leaveReport = LeaveReport.findAll("from LeaveReport lr where lr.employee in (:employee) and date_format(lr.leaveDate,'%Y-%m-%d') <=:toDate order by "+dateOrder+", "+nameOrder,[employee:employee,toDate:endDate],params)

            }else{
                leaveReport = LeaveReport.findAll("from LeaveReport lr where lr.employee in (:employee) order by "+dateOrder+", "+nameOrder,[employee:employee],params)

            }
        }
        return leaveReport
    }

    def getLeaveBalanceForReportCount(employee,startDate,endDate){

        def leaveReport
        if(employee){
            if(startDate && endDate){
                leaveReport = LeaveReport.findAll("from LeaveReport lr where lr.employee in (:employee) and date_format(lr.leaveDate,'%Y-%m-%d') between :fromDate and :toDate",[employee:employee,fromDate:startDate,toDate:endDate])

            }else if(startDate){
                leaveReport = LeaveReport.findAll("from LeaveReport lr where lr.employee in (:employee) and date_format(lr.leaveDate,'%Y-%m-%d') >=:fromDate",[employee:employee,fromDate:startDate])

            }else if(endDate){
                leaveReport = LeaveReport.findAll("from LeaveReport lr where lr.employee in (:employee) and date_format(lr.leaveDate,'%Y-%m-%d') <=:toDate",[employee:employee,toDate:endDate])

            }else{
                leaveReport = LeaveReport.findAll("from LeaveReport lr where lr.employee in (:employee)",[employee:employee])

            }
        }
        return leaveReport.size()
    }



    def createReportMapForLeave(eachReport){
        def recMap=[:]
        recMap.putAt('employee',eachReport.employee)
        recMap.putAt('openingBalance',eachReport.openingBalance)
        recMap.putAt('totalLeaveBalance',eachReport.totalLeaveBalance)
        recMap.putAt('leaveDate',eachReport.leaveDate)
        recMap.putAt('earnedLeave',eachReport.earnedLeave)
        recMap.putAt('extraTime',eachReport.extraTime)
        recMap.putAt('paidLeave',eachReport.paidLeave)
        recMap.putAt('unpaidLeave',eachReport.unpaidLeave)
        recMap.putAt('balanceDays',eachReport.balanceDays)
        return recMap
    }

    def createReportMapForLeaveBalance(eachReport){
        def recMap=[:]
        def leaveDate=DateGroovyMethods.format(eachReport?.leaveDate, 'MMM-yy')
        def department
        if(eachReport.employee.unit){
            department=eachReport.employee.unit
        }else{
            department=eachReport.employee.departments
        }
        recMap.putAt('employee',eachReport.employee)
        recMap.putAt('designation',eachReport.employee?.designation)
        recMap.putAt('departments',department)
        recMap.putAt('month',leaveDate)
        recMap.putAt('lastMonthBalance',eachReport.openingBalance)
        recMap.putAt('leaveUsed',eachReport.paidLeave)
        recMap.putAt('earnedLeave',eachReport.earnedLeave)
        recMap.putAt('currentBalance',eachReport.balanceDays)
        return recMap
    }

    def createReportMapForOverTime(eachReport){
        def recMap=[:]
        def department
        if(eachReport.employee.unit){
            department=eachReport.employee.unit
        }else{
            department=eachReport.employee.departments
        }
        def leaveDate=DateGroovyMethods.format(eachReport?.leaveDate, 'MMM-yy')
        recMap.putAt('employee',eachReport.employee)
        recMap.putAt('designation',eachReport.employee?.designation)
        recMap.putAt('departments',department)
        recMap.putAt('month',leaveDate)
        recMap.putAt('overTime',eachReport.extraTime)
        return recMap
    }


}
