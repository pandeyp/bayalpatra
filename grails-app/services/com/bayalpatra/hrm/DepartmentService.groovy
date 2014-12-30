package com.bayalpatra.hrm

class DepartmentService {

    static transactional = true
    def departmentTree
    def departmentTreeA
    def employeeService
    def employeeHistoryService

    def getDepartmentList(long deptId) {

        Stack deptStack = new Stack ();
        def finalDeptList = []
        deptStack.push(deptId)
        while(deptStack.size()>0){
            long id=deptStack.pop()
            finalDeptList.add(id)
            def deptByParent=getDeptByParent(id)

            deptByParent.each {
                deptStack.push(it.id)
            }
        }

        return finalDeptList

    }


    def getDeptByParent(long deptId){
        def dept = Department.findAllByParentId(deptId)
        return dept
    }



    def generateNavigation(long parentId){

        def departments= Department.findAllByParentId(parentId)

        if(departments.size()==0){
            return departmentTree
        }else{
            departmentTree +="<ul>"
            departments.each{

                departmentTree+=("<li id='li"+it.id+"'><a href='#' onclick='showValueTextbox("+it.id+",\""+it.name+"\")'>"+it.name+"</a>")

                generateNavigation(it.id)

                departmentTree+="</li>";
            }
            departmentTree +="</ul>"
            return departmentTree
        }

    }

    // This function is for not allowing to select parent department while creating SubStore Resources
    def generateNavigationA(long parentId){

        def departments= Department.findAllByParentId(parentId)


        if(departments.size()==0){
            return departmentTreeA
        }else{
            departmentTreeA +="<ul>"
            departments.each{

                departmentTreeA+=("<li id='li"+it.id+"'><a href='#' onclick='showValueTextbox("+it.id+",\""+it.name+"\",\""+it.parentId+"\")'>"+it.name+"</a>")
                generateNavigationA(it.id)
                departmentTreeA+="</li>";
            }
            departmentTreeA +="</ul>"
            return departmentTreeA
        }

    }


    def getIdNumber(){
        def idNumber = Department.executeQuery("select max(d.idNumber) from Department d")
        idNumber = Integer.parseInt(idNumber[0])
        return idNumber
    }

    def getRoot(parent){
        def dept = Department.findById(parent)
        return dept
    }

    def getDeptId(department){
        def deptId
        if(department.parentId==1){
            deptId = department.idNumber
        }else{
            deptId = Department.get(department.rootId).idNumber
        }
        return deptId
    }

    /**
     *Returns list of department which are enlisted as sub-store
     */
    def getSubStore(){
        def subStoreList = Department.findByIsSubStore(true)
        return subStoreList
    }

/*    *//**
     * get unit list from deptId
     *//*
    def getUnitList ( dept ) {
        *//*if(dept.id==1){*//*
        return Unit.findAll()
        *//*}else{
              return  Unit.findAllByDepartments(dept.id)
          }*//*
    }*/

    /**
     *@param department for which associated units are to be listed
     * @return List of units that fall under the given department
     * **/

/*    def getUnitsFromDepartment(department){
        def unitList = Unit.findAll("from Unit u where u.departments.id in (:department)",[department:department])
        return unitList
    }*/

/*    def changeDepartment(){
        def effectiveDate
        def currentDate=DateUtils.formatDateToYYYYMMDD(DateUtils.getCurrentDate())
        def depId
        def fromDate
        def employeeId
        def emp=Employee.findAll()
        emp.each {
            Employee employee = Employee.get(it.id)

            if(it.changeDepartment){
                if(it.effectiveDateForDepartment){
                    effectiveDate=DateUtils.formatDateToYYYYMMDD(it.effectiveDateForDepartment)
                }
                if(currentDate>=effectiveDate)
                {

                    def department = Departments.findByName(it.changeDepartment)
                    depId = department.idNumber
                    def empId = it.employeeId.substring(2,7)
                    employeeId = employeeService.updateEmployeeId(depId,empId)

                    EmployeeHistory employeeHistoryList = EmployeeHistory.findByFieldTypeAndEmployee(AnnapurnaConstants.FIELD_DEPARTMENT,employee)
                    if(employeeHistoryList){
                        fromDate = employeeHistoryService.getFromDate(AnnapurnaConstants.FIELD_DEPARTMENT,employee)
                    }
                    employeeHistoryService.createEmployeeHistory(employee,employee.departments.name,AnnapurnaConstants.FIELD_DEPARTMENT,fromDate,currentDate,employee.updatedDepartmentBy)



                    it.employeeId=employeeId
                    it.departments=department
                    it.changeDepartment=null
                    it.effectiveDateForDepartment=null
                }
            }

        }
    }*/

}
