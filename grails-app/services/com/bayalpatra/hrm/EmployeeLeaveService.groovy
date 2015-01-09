package com.bayalpatra.hrm

import commons.BayalpatraConstants
import commons.DateUtils
import grails.transaction.Transactional

@Transactional
class EmployeeLeaveService {


    def validateLeaveRange={fromDate,toDate,employee->
        def temp
        def empLeave=EmployeeLeaveDetail.findAllByEmployeeAndStatusNot(employee,'Denied')

        if(fromDate<employee.joinDate || toDate<employee.joinDate){
            temp=0
            return temp;
        }else{
            empLeave.each (){
                if(fromDate>=it.fromDate && fromDate<=it.toDate || toDate>=it.fromDate && toDate<=it.toDate) {
                    temp = 1
                }
            }
            return temp;
        }
    }


    def validateLeaveRangeEdit={employeeLeaveDetailInstance,fromDate,toDate,employee->

        def temp
        def empLeave=EmployeeLeaveDetail.findAllByEmployeeAndIdNot(employee,employeeLeaveDetailInstance)

        empLeave.each (){

            if(fromDate<it.employee.joinDate || toDate<it.employee.joinDate){
                temp=0
            }

            if(fromDate>=it.fromDate && fromDate<=it.toDate || toDate>=it.fromDate && toDate<=it.toDate) {
                temp = 1
            }

        }

        return temp;

    }


    def getEmployeeLeaveDetail(status,employee,isAdmin,params,max,offset,sort,order){
        params.max=max
        params.offset=offset
        def employeeLeaveList
        if(employee){
            def bereavementLeave = LeaveType.findByLeaveType('Bereavement')
            def maternityLeave = LeaveType.findByLeaveType('Maternity Leave')

            if(isAdmin){
                employeeLeaveList = EmployeeLeaveDetail.findAll("from EmployeeLeaveDetail eld where eld.status in(:status) and eld.employee in (:employee) order by ${sort} ${order}",[status:status,employee:employee],params)
            }else{
                if(BayalpatraConstants.CLIENT_NAME==BayalpatraConstants.CLIENT_DEERWALK){
                    // people with role other than admin cannot approve leave greater than 7 days
                    employeeLeaveList = EmployeeLeaveDetail.findAll("from EmployeeLeaveDetail eld where eld.status in(:status) and eld.employee in (:employee) and eld.leaveDifference <=:leaveDiff order by ${sort} ${order}",[status:status,employee:employee,leaveDiff:Double.valueOf(7)],params)
                }else if(BayalpatraConstants.CLIENT_NAME==BayalpatraConstants.CLIENT_BAYALPATRA){
                    // people with role other than admin cannot approve leave greater than 7 days,unpaid leave and maternity and bereavement leave
                    employeeLeaveList = EmployeeLeaveDetail.findAll("from EmployeeLeaveDetail eld where eld.status in(:status) and eld.employee in (:employee) and eld.leaveDifference <=:leaveDiff AND eld.leaveType.paidUnpaid = :paid AND eld.leaveType NOT IN (:type) order by ${sort} ${order}",[status:status,employee:employee,leaveDiff:Double.valueOf(7),paid:AnnapurnaConstants.PAID_LEAVE,type:[bereavementLeave,maternityLeave]],params)
                }
            }
        }
        return employeeLeaveList
    }


    def getLeaveDetailForExport(status,employee){   //new function added for export
        def employeeLeaveList
        if(employee){
            employeeLeaveList = EmployeeLeaveDetail.findAll("from EmployeeLeaveDetail eld where eld.status=:status and eld.employee in (:employee) order by eld.employee.firstName",[status:status,employee:employee])
        }
        return employeeLeaveList

    }


    def getCountLeaveDetailRoleDeptHead(status,employee){
        def count=0
        if(employee){
            def countAll = EmployeeLeaveDetail.executeQuery("select count(*) from EmployeeLeaveDetail eld where eld.status in(:status) and eld.employee in (:employee)",[status:status,employee:employee])
            count=countAll[0]
        }
        return count
    }

    // This function is for daily Leave detail of Employee

    def getDailyLeaveDetail(Date date){
        def leaveDetail = EmployeeLeaveDetail.findAll("from EmployeeLeaveDetail as e where e.status=:status AND e.toDate >= :date AND e.fromDate <= :date ",[date:date,status:BayalpatraConstants.LEAVE_APPROVED])
        return leaveDetail
    }

    def getCountDailyLeaveDetail(Date date){
        def count = EmployeeLeaveDetail.executeQuery("select count(*) from EmployeeLeaveDetail as e where e.status=:status AND e.toDate >= :date AND e.fromDate <= :date",[date:date,status:BayalpatraConstants.LEAVE_APPROVED])
        return count[0]
    }

/**
 * @param date in which we should check for leave of the provided employee
 * @param employee
 * @return leave Detail of the employee given for the privided date if exists
 */
    def getLeaveByEmployeeAndDate(Date date,Employee employee){
        def leave
        if(employee){
            leave = EmployeeLeaveDetail.find("from EmployeeLeaveDetail eld where (:date between eld.fromDate and eld.toDate) and eld.employee=:emp and eld.status=:status",[date:date,emp:employee,status:AnnapurnaConstants.LEAVE_APPROVED])
        }
        return leave
    }

    def getAdminDeptHeadEmail(){

        def adminDept = Department.findByName(BayalpatraConstants.DEPARTMENT_ADMIN)
        def employeesInDepartment = Employee.findAllByDepartment(adminDept)
        def emailArray = new ArrayList<String>()
        def reqEmail

        if(employeesInDepartment){

            def userList = User.findAll("from User as u where u.employee in (:employeeList)", [employeeList : employeesInDepartment])
            def roleList = UserRole.executeQuery("select ur from UserRole ur where ur.user in (:userList) and ur.role = :usrRole",[userList: userList, usrRole: Role.findByAuthority(AnnapurnaConstants.ROLE_DEPARTMENT_HEAD)])
            roleList.eachWithIndex { val, i ->
                reqEmail = roleList[i].user.employee.email
                emailArray.add(reqEmail)
            }
        }

        return emailArray
    }



    def getLeaveYearList(){
        def yearList  = EmployeeLeaveDetail.executeQuery("SELECT DISTINCT(year(l.fromDate)) as leaveYear from EmployeeLeaveDetail l")
        return  yearList
    }



    def getCountForFilter(employee,month,year){
        def count
        def status = [BayalpatraConstants.LEAVE_APPROVED,BayalpatraConstants.LEAVE_DENIED]
        if(employee && month && year){
            count  =  EmployeeLeaveDetail.executeQuery("SELECT count(*) from EmployeeLeaveDetail eld where (year(eld.fromDate)=:year OR year(eld.toDate)=:year) AND (month(eld.fromDate)=:month OR month(eld.toDate)=:month) AND eld.status in(:status) AND eld.employee IN (:employee)",[year:year,month: month,employee:employee, status:status])
        }else if(month && year)  {
            count  =  EmployeeLeaveDetail.executeQuery("SELECT count(*) from EmployeeLeaveDetail eld where (year(eld.fromDate)=:year OR year(eld.toDate)=:year) AND (month(eld.fromDate)=:month OR month(eld.toDate)=:month) AND eld.status in(:status)",[year:year,month: month, status:status])
        } else if(employee && year)  {
            count  =  EmployeeLeaveDetail.executeQuery("SELECT count(*) from EmployeeLeaveDetail eld where (year(eld.fromDate)=:year OR year(eld.toDate)=:year) AND eld.status in(:status) AND eld.employee IN (:employee)",[year:year,employee:employee, status:status])
        } else if(employee && month)  {
            count  =  EmployeeLeaveDetail.executeQuery("SELECT count(*) from EmployeeLeaveDetail eld where (month(eld.fromDate)=:month OR month(eld.toDate)=:month) AND eld.status in(:status) AND eld.employee IN (:employee)",[month:month,employee:employee, status:status])
        } else if(employee){
            count  =  EmployeeLeaveDetail.executeQuery("SELECT count(*) from EmployeeLeaveDetail eld where  eld.status in(:status) AND eld.employee IN (:employee)",[employee:employee, status:status])
        } else if(year){
            count  =  EmployeeLeaveDetail.executeQuery("SELECT count(*) from EmployeeLeaveDetail eld where (year(eld.fromDate)=:year OR year(eld.toDate)=:year) AND eld.status in(:status)",[year:year,status:status])
        } else if(month)  {
            count  =  EmployeeLeaveDetail.executeQuery("SELECT count(*) from EmployeeLeaveDetail eld where (month(eld.fromDate)=:month OR month(eld.toDate)=:month) AND eld.status in(:status)",[month:month, status:status])
        }
        return count[0]
    }

    def getLeaveListForFilter(employee,month,year,params,max,offset,sort,order){
        def leaveList
        def status = [BayalpatraConstants.LEAVE_APPROVED,BayalpatraConstants.LEAVE_DENIED]
        params.max=max
        params.offset=offset
        if(employee && month && year){
            leaveList  =  EmployeeLeaveDetail.findAll("from EmployeeLeaveDetail eld where (year(eld.fromDate)=:year OR year(eld.toDate)=:year) AND (month(eld.fromDate)=:month OR month(eld.toDate)=:month) AND eld.status in(:status) AND eld.employee IN (:employee) order by ${sort} ${order}",[year:year,month: month,employee:employee, status:status],params)
        }else if(month && year)  {
            leaveList  =  EmployeeLeaveDetail.findAll("from EmployeeLeaveDetail eld where (year(eld.fromDate)=:year OR year(eld.toDate)=:year) AND (month(eld.fromDate)=:month OR month(eld.toDate)=:month) AND eld.status in(:status) order by ${sort} ${order}",[year:year,month: month, status:status],params)
        } else if(employee && year)  {
            leaveList  =  EmployeeLeaveDetail.findAll("from EmployeeLeaveDetail eld where (year(eld.fromDate)=:year OR year(eld.toDate)=:year) AND eld.status in (:status) AND eld.employee IN (:employee) order by ${sort} ${order}",[year:year,employee:employee, status:status],params)
        } else if(employee && month)  {
            leaveList  =  EmployeeLeaveDetail.findAll("from EmployeeLeaveDetail eld where (month(eld.fromDate)=:month OR month(eld.toDate)=:month) AND eld.status in(:status) AND eld.employee IN (:employee) order by ${sort} ${order}",[month:month,employee:employee, status:status],params)
        } else if(employee){
            leaveList  =  EmployeeLeaveDetail.findAll("from EmployeeLeaveDetail eld where  eld.status in(:status) AND eld.employee IN (:employee) order by ${sort} ${order}",[employee:employee, status:status],params)
        } else if(year){
            leaveList  =  EmployeeLeaveDetail.findAll("from EmployeeLeaveDetail eld where (year(eld.fromDate)=:year OR year(eld.toDate)=:year) AND eld.status in(:status) order by ${sort} ${order}",[year:year,status:status],params)
        } else if(month)  {
            leaveList  =  EmployeeLeaveDetail.findAll("from EmployeeLeaveDetail eld where (month(eld.fromDate)=:month OR month(eld.toDate)=:month) AND eld.status in(:status) order by ${sort} ${order}",[month:month, status:status],params)
        }
        else{
            leaveList  =  EmployeeLeaveDetail.findAll()
        }
        return  leaveList
    }

    def filterUnapprovedLeave(params,originalList){
        def startDate
        def endDate
        def employee
        def resultList = []
        if (params.selectedEmp) employee = Employee.findById(params.selectedEmp)
        if (params.fromDate) startDate = DateUtils.stringToDate(params.fromDate)
        if (params.toDate) endDate = DateUtils.stringToDate(params.toDate)
        if (employee && startDate && endDate){
            originalList.findAll{it.employee==employee && it.fromDate>=startDate && it.toDate<=endDate}.each{
                if(!resultList.contains(it)) resultList<<it
            }
        }else if (employee && startDate){
            originalList.findAll{it.employee==employee && it.fromDate>=startDate}.each{
                if(!resultList.contains(it)) resultList<<it
            }
        }else if (employee && endDate){
            originalList.findAll{it.employee==employee && it.toDate<=endDate}.each{
                if(!resultList.contains(it)) resultList<<it
            }
        }else if (startDate && endDate){
            originalList.findAll{it.fromDate>=startDate && it.toDate<=endDate}.each{
                if(!resultList.contains(it)) resultList<<it
            }
        }else if (employee){
            originalList.findAll{it.employee==employee}.each{
                if(!resultList.contains(it)) resultList<<it
            }
        }else if (startDate){
            originalList.findAll{it.fromDate>=startDate}.each{
                if(!resultList.contains(it)) resultList<<it
            }
        }else if (endDate){
            originalList.findAll{it.toDate<=endDate}.each{
                if(!resultList.contains(it)) resultList<<it
            }
        }else{
            resultList = originalList
        }
        return resultList
    }

}
