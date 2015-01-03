package com.bayalpatra.hrm

import com.bayalpatra.commons.BayalpatraEmail
import commons.BayalpatraConstants
import commons.DateUtils
import grails.transaction.Transactional
import org.apache.log4j.Logger
import org.codehaus.groovy.runtime.DateGroovyMethods

@Transactional
class EmployeeService {

    /**
     * All methods within this service are to be made transactional.
     * All services have transaction demarcation enabled by default - to disable it, simply set the transactional property to false
     */
    static transactional = true
    def springSecurityService

    //get the logger instance
    private static final Logger LOGGER = Logger.getLogger(EmployeeService);

    def shiftService
    def employeeHistoryService

    /**
     * This method responsible for changing the status of the user from volunteer to permanent automatically, if the volunteer service period exceeded
     * @param employee
     */
    def changeEmployeeStatusFromVolunteerToPermanent(){
        def emp= Employee.findAllByStatus(BayalpatraConstants.VOLUNTEER)
        /*changeEmployeeStatusFromSuspended()*/
        try{
            for(Employee e : emp){
                def empHistory=EmployeeHistory.findAllByEmployee(e,[sort:"id",order:"desc"])[0]
                Integer volunteerDays = e.getVolunteerDays()
                int serviceDays = DateUtils.getServiceDays(e.getJoinDate())
                if(volunteerDays){
                    if(serviceDays >= volunteerDays){
                        Employee.executeUpdate("UPDATE Employee e SET e.status='" + BayalpatraConstants.CONTRACT + "' WHERE e.id=" + e.id);
                        def employeeHistory=new EmployeeHistory()
                        employeeHistory.properties=[employee: e,fieldType:BayalpatraConstants.FIELD_SERVICE_TYPE,oldValue:BayalpatraConstants.VOLUNTEER,fromDate:e.joinDate,user:null]
                        employeeHistory.save(flush: true,failOnError: true)
                    }
                }
            }
        }catch(Exception e){
            LOGGER.error(e)
        }
    }

   /* def changeStatus(){
        def effectiveDate
        def currentDate
        currentDate=DateUtils.getCurrentDate()
        currentDate=DateUtils.formatDateToYYYYMMDD(currentDate)
        def emp=Employee.findAll()
        def fromDate
        def updatedJoinDate
        def suspendedDetail
        def suspensionDetail
        emp.each {
            Employee employee = Employee.get(it.id)
            if(it.statusChangedTo){
                if(it.effectiveDate){
                    effectiveDate=DateUtils.formatDateToYYYYMMDD(it.effectiveDate)
                }
                if(currentDate>=effectiveDate)
                {
                    EmployeeHistory employeeHistoryList = EmployeeHistory.findByFieldTypeAndEmployee(BayalpatraConstants.FIELD_SERVICE_TYPE,employee)
                    if(employeeHistoryList){
                        fromDate = employeeHistoryService.getFromDate(BayalpatraConstants.FIELD_SERVICE_TYPE,employee)
                    }
                    employeeHistoryService.createEmployeeHistory(it,it.status,BayalpatraConstants.FIELD_SERVICE_TYPE,fromDate,currentDate,employee.updatedBy)
                    if (employee.statusChangedTo==BayalpatraConstants.SUSPENDED)
                    {
                        suspendedDetail = new SuspendedEmployeeDetails()
                        suspendedDetail.employee=it
                        suspendedDetail.startDate=new Date()
                        suspendedDetail.startDate.clearTime()
                    }
                    else{
                        if(employee.statusChangedTo==BayalpatraConstants.TERMINATED){
                            employee.terminatedDate=employee.effectiveDate
                        }

                        if(employee.status==BayalpatraConstants.SUSPENDED)
                        {
                            def unClosedSuspension = SuspendedEmployeeDetails.findByEmployee(employee)
                            if(unClosedSuspension){
                                suspensionDetail = unClosedSuspension
                                suspensionDetail.endDate=new Date()
                                suspensionDetail.endDate.clearTime()
                            }
                        }
                        employee.effectiveDate=null
                    }

                    employee.status=employee.statusChangedTo
                    employee.statusChangedTo=null

                    if (suspensionDetail)
                    {
                        suspensionDetail.save(failOnError: true)
                    }
                    else if (suspendedDetail)
                    {
                        suspendedDetail.save(failOnError: true)
                    }

                }
            }

        }
    }*/




    def changeEmployeeStatusFromSuspended(){
        def employee=Employee.findAllByStatus(BayalpatraConstants.SUSPENDED)
        try{
            for(Employee e:employee){
                def empHistory=EmployeeHistory.findAllByEmployeeAndFieldType(e,BayalpatraConstants.FIELD_SERVICE_TYPE,[sort: 'updatedDate',order: "desc"])[0]
                Integer suspensionDays = e.getSuspensionDays()
                int serviceDays = DateUtils.getServiceDays(e.getEffectiveDate())
                if(suspensionDays){
                    if(serviceDays >= suspensionDays){
                        def updatedJoinDate =e.updatedJoinDate.plus(suspensionDays)
                        e.updatedJoinDate= updatedJoinDate
                        e.status= empHistory?.oldValue
                        e.suspensionDays=null
                        e.effectiveDate=null
                        def suspensionDetail = SuspendedEmployeeDetails.findByEmployee(e)
                        suspensionDetail.endDate=new Date()
                        suspensionDetail.endDate.clearTime()
                        def employeeHistory=new EmployeeHistory()
                        employeeHistory.properties=[employee: e,fieldType:BayalpatraConstants.FIELD_SERVICE_TYPE,oldValue:BayalpatraConstants.SUSPENDED,fromDate:empHistory?.toDate,user: e.updatedBy]
                        employeeHistory.save(flush: true,failOnError: true)
                    }
                }
            }
        }catch(Exception e){
            LOGGER.error(e)
        }
    }

    def checkBoundPeriod={Employee emp ->
        def maxBoundPeriod = EmployeeTraining.executeQuery("SELECT MAX(et.boundPeriodTo) as Date FROM EmployeeTraining et WHERE et.employee.id=" + emp.id)
        Date currentDate = DateUtils.getCurrentDate()
        if(maxBoundPeriod[0] != null){
            if(maxBoundPeriod[0] > currentDate){
                return true
            }
        }
        return false
    }
    /**
     * for collecting latest bound period from and bound period to for displaying in Edit Employee if status is Terminated
     * @return
     */
    def getBoundFrom(){
        def x= EmployeeTraining.executeQuery("SELECT MAX(boundPeriodFrom) from  EmployeeTraining");
        return x.get(0);
    }

    def getBoundTo(){
        def y= EmployeeTraining.executeQuery("SELECT MAX(boundPeriodTo) from  EmployeeTraining");
        return y.get(0);
    }



    def getEmployeeId(){
        def empId
        def maxEmpId = Employee.executeQuery("select max(e.id) from Employee e")
        if(maxEmpId[0]!=null){
            empId = maxEmpId[0]+1
        }else{
            empId = 1
        }
        return empId
    }


    def createEmployeeId(deptId,empId){
        def employeeId = deptId+empId.toString().padLeft(5,'0');
        return employeeId
    }

    def updateEmployeeId(deptId,empId){
        def employeeId = deptId+empId
        return employeeId
    }

    def getEmpForFilter(department,startDate,endDate,params,max,offset,sortingParam,orderParam){
        params.max=max
        params.offset=offset

        def employeeList
        def status = [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]

        String query = "from Employee e where e.status not in (:status) "

        if(startDate && endDate){

            if(department){
                query += " and e.joinDate between :startDate and :endDate and e.department.id in(:department) order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[startDate:startDate,endDate:endDate,department:department, status:status],params)
            }else{
                query += " and e.joinDate between :startDate and :endDate order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[startDate:startDate,endDate:endDate,status:status],params)
            }

        }else if(startDate){
            if(department){
                query += " and e.joinDate>=:date and e.department.id in(:department) order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[date:startDate,department:department, status:status],params)
            }else{
                query += " and e.joinDate>=:date order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[date:startDate,status:status],params)
            }

        }else if(endDate){
            if(department){
                query += " and e.joinDate<=:date and e.department.id in(:department) order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[date:endDate,department:department, status:status],params)
            }else{
                query += " and e.joinDate<=:date order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[date:endDate,status:status],params)
            }
        }else if(department){
            query += " and e.department.id in(:department) order by ${sortingParam} ${orderParam}"
            employeeList = Employee.findAll(query,[department:department,status:status],params)
        }else{
            params.offset=params.offset?:0
            query += " order by ${sortingParam} ${orderParam}"
            employeeList = Employee.findAll(query,[status:status],params)
        }

        return employeeList

    }


    def getEmpForSelective(employeeName,department,supervisor,user,unit){

        def employeeList
        def status = [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]
        String query = "from Employee e where e.firstName LIKE '" + employeeName + "%' and e.status not in (:status)"

        if(department){
            query += " and e.department.id in(:department)"
            employeeList = Employee.findAll(query,[department:department,status:status])
        }else if(supervisor && user){
            query+= " and (e.supervisor= :supervisor or e.id=:id) order by e.firstName"
            employeeList = Employee.findAll(query,[supervisor:supervisor,id:user?.employee?.id,status:status])
        }else if(unit){
            query+= " and e.unit= :unit order by e.firstName"
            employeeList = Employee.findAll(query,[unit:unit,status:status])
        }else{
            query += " order by 'firstName' asc"
            employeeList = Employee.findAll(query,[status:status])
        }

        return employeeList
    }

    def getDoctorListDeptWise(dept){
//        def doctorList =  Employee.findAll("from Employee e where e.status not in (:status) and e.departments.id in(:department) and e.isDoc=true",[status:[
//                BayalpatraConstants.TERMINATED,
//                BayalpatraConstants.SUSPENDED,
//                BayalpatraConstants.CLEARED
//        ],department:dept])
//        ClnConsultationFeeSetting.findAllByActive(true)?.name
        def doctorList =  ClnConsultationFeeSetting.findAll("from ClnConsultationFeeSetting e where e.name.status not in (:status) and e.name.department.id in(:department) and e.name.isDoc=true and e.active=true",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.SUSPENDED,
                BayalpatraConstants.CLEARED
        ],department:dept])

    }
    def getDoctorList(){
        def doctorList =  Employee.findAll("from Employee e where e.status not in (:status)  and e.isDoc=true order by e.firstName ",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.SUSPENDED,
                BayalpatraConstants.CLEARED
        ]] )

    }

    def getEmpCountForFilter(department,startDate,endDate){
        def count
        def status = [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]
        String query = "select count(*) from Employee e where e.status not in (:status)"
        if(startDate && endDate){
            if(department){
                query += " and e.joinDate between :startDate and :endDate and e.department.id in(:department)"
                count = Employee.executeQuery(query,[startDate:startDate,endDate:endDate,department:department, status:status])
            }else{
                query += " and e.joinDate between :startDate and :endDate"
                count = Employee.executeQuery(query,[startDate:startDate,endDate:endDate,status:status])
            }

        }else if(startDate){
            if(department){
                query += " and e.joinDate>=:date and e.department.id in(:department)"
                count = Employee.executeQuery(query,[date:startDate,department:department, status:status])
            }else{
                query += " and e.joinDate>=:date"
                count = Employee.executeQuery(query,[date:startDate,status:status])
            }

        }else if(endDate){
            if(department){
                query += " and e.joinDate<=:date and e.department.id in(:department)"
                count = Employee.executeQuery(query,[date:endDate,department:department, status:status])
            }else{
                query += " and e.joinDate<=:date"
                count = Employee.executeQuery(query,[date:endDate,status:status])
            }
        }else if(department){
            query += " and e.department.id in(:department)"
            count = Employee.executeQuery(query,[department:department,status:status])
        }else{
            count = Employee.executeQuery(query,[status:status])

        }

        return count[0]
    }

    def getEmpNotAssignedToSup(supervisorSets){

        def employeeList = Employee.findAll("from Employee e where e.status not in (:status) and e.id not in (:superv) order by e.firstName",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.SUSPENDED,
                BayalpatraConstants.CLEARED
        ],superv:supervisorSets])

        return employeeList
    }

    def getEmpByStatus(name){

        def employeeList = Employee.findAll("from Employee as e where e.status not in (:status) and e.firstName like '${name==null?'':name}%' order by e.firstName",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.SUSPENDED,
                BayalpatraConstants.CLEARED
        ]])
        return employeeList
    }



    def getEmpByStatusAndDepartment(department){

        def employeeList = Employee.findAll("from Employee as e where e.status not in (:status) and e.department.id in (:department) order by e.firstName",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.SUSPENDED,
                BayalpatraConstants.CLEARED
        ],department:department])

        return employeeList
    }


    def getEmpByStatusAndUnit(unit){
        def employeeList = Employee.findAll("from Employee as e where e.status not in (:status) and e.unit= :unit order by e.firstName",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.SUSPENDED,
                BayalpatraConstants.CLEARED
        ],unit:unit])

        return employeeList
    }

    def getEmpByUnit(unit,params,max,offset,sortingParam,orderParam){
        params.max=max
        params.offset=offset
        def employeeList = Employee.findAll("from Employee as e where e.status not in (:status) and e.unit= :unit order by ${sortingParam} ${orderParam}",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ],unit:unit],params)
        return employeeList
    }

    def getCountByUnit(unit){
        def count = Employee.executeQuery("select count(*) from Employee e where e.unit=:unit and e.status not in (:status)",[unit:unit,status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]])
        return count[0]
    }


    def getFileName(firstName,lastName,imageFile){
        def String fileName;
        Date currentDate = DateUtils.getCurrentDate()
        def Random randomGenerator = new Random()
        def String fileNamePrefix = currentDate.time.toString()+"_"+randomGenerator.nextInt(1000000);
        fileName = firstName+'_'+lastName+'_'+fileNamePrefix+ ".jpg";
        def root = ApplicationHolder.getApplication().getMainContext().getResource("/").getFile().getAbsolutePath()
        def String empImagePath = root+"/images/employee/";
        def File uploadedImage = new File(empImagePath+fileName)
        imageFile.transferTo(uploadedImage) //Writing Original File
        return fileName
    }

    /**
     * This method is used to populate the employee List when the user enters name in the employee search text field.
     * @param employeeName
     * @return List of {@link Employee}(s)
     */
    def getEmp(String employeeName, offset ){

        def employeeInstanceList = Employee.findAll("from Employee e where e.firstName LIKE '" + employeeName + "%' and e.status  not in (:status) order by firstName asc",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED//,[max:20,offset: offset]
        ]], [max:30, offset:offset])
        return employeeInstanceList
    }

    def getEmpList(String employeeName, offset,max){

        def employeeInstanceList = Employee.findAll("from Employee e where e.firstName LIKE '" + employeeName + "%' and e.status  not in (:status) order by firstName asc",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED//,[max:20,offset: offset]
        ]], [max:max, offset:offset])
        return employeeInstanceList
    }

    def getEmpListCount(String employeeName){
        def employeeInstanceList = Employee.findAll("from Employee e where e.firstName LIKE '" + employeeName + "%' and e.status  not in (:status) order by firstName asc",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED//,[max:20,offset: offset]
        ]])
        return employeeInstanceList.size()
    }
    def getTerminatedEmp(String employeeName,params){
        def employeeInstanceList = Employee.findAll("from Employee e where e.firstName LIKE '" + employeeName + "%' and e.status in (:status) order by firstName asc",[status:[BayalpatraConstants.TERMINATED,BayalpatraConstants.CLEARED]],[params:params])
        return employeeInstanceList
    }

//filters for terminated employee list
    def getTerminatedEmpForFilter(type,startDate,endDate,params,max,offset,sortingParam,orderParam){
        params.max=max
        params.offset=offset

        def employeeList
        def status = [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]

        String query = "from Employee e where e.status in (:status)"
        if(type=='Appointment'){
            if(startDate && endDate){
                query += " and e.joinDate between :startDate and :endDate order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[startDate:startDate,endDate:endDate,status:status],params)
            }else if(startDate){
                query += " and e.joinDate>=:date order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[date:startDate,status:status],params)
            }else if(endDate){
                query += " and e.joinDate<=:date order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[date:endDate,status:status],params)
            }else{
                params.offset=params.offset?:0
                query += " order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[status:status],params)
            }
        }else{
            if(startDate && endDate){
                query += " and e.terminatedDate between :startDate and :endDate order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[startDate:startDate,endDate:endDate,status:status],params)
            }else if(startDate){
                query += " and e.terminatedDate>=:date order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[date:startDate,status:status],params)
            }else if(endDate){
                query += " and e.terminatedDate<=:date order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[date:endDate,status:status],params)
            }else{
                params.offset=params.offset?:0
                query += " order by ${sortingParam} ${orderParam}"
                employeeList = Employee.findAll(query,[status:status],params)
            }
        }

        return employeeList

    }

    def getAllFilterList(type,startDate,endDate){
        def employeeList
        def status = [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]
        String query = "from Employee e where e.status in (:status)"
        if(type=='Appointment'){
            if(startDate && endDate){
                query += " and e.joinDate between :startDate and :endDate order by e.firstName asc"
                employeeList = Employee.findAll(query,[startDate:startDate,endDate:endDate,status:status])
            }else if(startDate){
                query += " and e.joinDate>=:date order by e.firstName asc"
                employeeList = Employee.findAll(query,[date:startDate,status:status])
            }else if(endDate){
                query += " and e.joinDate<=:date order by e.firstName asc"
                employeeList = Employee.findAll(query,[date:endDate,status:status])
            }else{
                query += " order by e.firstName asc"
                employeeList = Employee.findAll(query,[status:status])
            }
        }else{
            if(startDate && endDate){
                query += " and e.terminatedDate between :startDate and :endDate order by e.firstName asc"
                employeeList = Employee.findAll(query,[startDate:startDate,endDate:endDate,status:status])
            }else if(startDate){
                query += " and e.terminatedDate>=:date order by e.firstName asc"
                employeeList = Employee.findAll(query,[date:startDate,status:status])
            }else if(endDate){
                query += " and e.terminatedDate<=:date order by e.firstName asc"
                employeeList = Employee.findAll(query,[date:endDate,status:status])
            }else{
                query += " order by e.firstName asc"
                employeeList = Employee.findAll(query,[status:status])
            }
        }
        return employeeList
    }

    def getTerminatedEmpCountForFilter(type,startDate,endDate){
        def count
        def status = [
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]
        String query = "select count(*) from Employee e where e.status in (:status)"

        if(type=='Appointment'){
            if(startDate && endDate){
                query += " and e.joinDate between :startDate and :endDate"
                count = Employee.executeQuery(query,[startDate:startDate,endDate:endDate,status:status])
            }else if(startDate){
                query += " and e.joinDate>=:date"
                count = Employee.executeQuery(query,[date:startDate,status:status])
            }else if(endDate){
                query += " and e.joinDate<=:date"
                count = Employee.executeQuery(query,[date:endDate,status:status])
            }else{
                count = Employee.executeQuery(query,[status:status])
            }

        }else{
            if(startDate && endDate){
                query += " and e.terminatedDate between :startDate and :endDate "
                count = Employee.executeQuery(query,[startDate:startDate,endDate:endDate,status:status])
            }else if(startDate){
                query += " and e.terminatedDate>=:date"
                count = Employee.executeQuery(query,[date:startDate,status:status])

            }else if(endDate){
                query += " and e.terminatedDate<=:date"
                count = Employee.executeQuery(query,[date:endDate,status:status])
            }else{

                count = Employee.executeQuery(query,[status:status])
            }
        }


        return count[0]
    }

    //end for filters for terminated employee list

    def getTermedAndClearedEmployee(params){
        def employeeList = Employee.findAll("from Employee as e where e.status in (:status) order by e.firstName",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]],params)
        return employeeList
    }

    def getCountByTermedAndClearedEmployee(){
        def count = Employee.executeQuery("select count(*) from Employee e where e.status in (:status)",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]])
        return count[0]
    }

    /**
     * @return list of {@link Employee}(s) whose probation period is completed
     */
    def getProbationCompletedEmployees(){
        List<Employee> probationCompletedEmployeeList = new ArrayList<Employee>()
        def probationEmployeeList=Employee.findAllByStatus(BayalpatraConstants.PROBATION)
        for(Employee emp : probationEmployeeList){
            Integer serviceDays = DateUtils.getServiceDays(emp.joinDate)

            //send email to the respective Supervisor And/OR HR member
            if(serviceDays == emp.getVolunteerDays()){
                Employee supervisor = getSupervisorByEmployee(emp)
                //create a email object and save to send the email notification
                BayalpatraEmail annapurnaEmail = new BayalpatraEmail()
                annapurnaEmail.toAddress = supervisor.email
                annapurnaEmail.subject = "Probation period completion - " + emp.fullName
                annapurnaEmail.messageBody = "Dear " + supervisor.getFirstName() +",<br><br>Probation period of this employee is completed. Please take necessary action.<br><br>Thank You"
                annapurnaEmail.save(flush:true, failOnError: true)
                println annapurnaEmail.errors
            }
            if(serviceDays >= emp.getVolunteerDays()){
                probationCompletedEmployeeList.add(emp)
            }
        }
        return probationCompletedEmployeeList
    }

    def getCountOfProbationalEmployees(){
        def probEmployeeList = getProbationCompletedEmployees()
        def count
        count = probEmployeeList.size()
        return count
    }

    /**
     * Get Supervisor by employee
     * @param employee
     * @return Supervisor as {@link Employee}
     */
    def getSupervisorByEmployee(Employee emp){
        def employee = Employee.findBySupervisor(emp.getSupervisor())
        return employee
    }

    /**
     * Get Salutation by employee gender
     * @param gender
     * @return Salutation as String
     */
    def salutations(String gender,Boolean isDoctor,String maritalStatus){
        String salutation = "";
        if(isDoctor){
            salutation="Dr."
        }
        else{
            if(gender==BayalpatraConstants.MALE){
                salutation = "Mr."
            }else if(gender==BayalpatraConstants.FEMALE){
                if(maritalStatus==BayalpatraConstants.MARRIED){
                    salutation = "Mrs."
                }else{
                    salutation = "Ms."
                }
            }
        }
        return salutation

    }
    public String getSalutation(String gender){
        String salutation = "";
        if(gender.equals(BayalpatraConstants.MALE)){
            salutation = "Sir"
        }else if(gender.equals(BayalpatraConstants.FEMALE)){
            salutation = "Madam"
        }
        return salutation
    }

    /**
     * Used for user search
     * @param employeeName
     * @return list of {@link User}
     */
    def getUser(String employeeName){
        def userInstanceList = UserRole.findAll("FROM UserRole ur WHERE ur.user.enabled=1 AND ur.user.employee.firstName LIKE '" + employeeName + "%' AND ur.user.employee.status  NOT IN (:status) and ur.role.authority!='"+BayalpatraConstants.ROLE_NONE+"'",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]])
        return userInstanceList
    }

    def getDepartmentList(employee){
        def userInstanceList = UserRole.findAll("FROM UserRole ur WHERE ur.user.enabled=1 AND ur.user.employee in (:employee) AND ur.user.employee.status  NOT IN (:status) and ur.role.authority!='"+BayalpatraConstants.ROLE_NONE+"'",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ],employee:employee])
        return userInstanceList
    }

    def getEmpForOwnership(){
        def employeeList = Employee.findAll("from Employee as e where e.status not in (:status) order by e.firstName",[status:[
                BayalpatraConstants.TERMINATED,
                BayalpatraConstants.CLEARED
        ]])
        return employeeList
    }

    def getEmployeeBySupervisor(Supervisor supervisor,User user,params){
        def employeeInstanceList

        try{
            employeeInstanceList = Employee.findAll("from Employee as e where e.status not in (:status) and (e.supervisor= :supervisor or e.id=:id) order by e.firstName",[status:[
                    BayalpatraConstants.TERMINATED,
                    BayalpatraConstants.CLEARED
            ],supervisor:supervisor,id:user?.employee?.id],params)
        }catch (Exception e){
            e.message()
        }


        return employeeInstanceList
    }


    def getCountBySupervisor(Supervisor supervisor,User user){
        def count
        try{
            count = Employee.executeQuery("select count(*) from Employee as e where e.status not in (:status) and (e.supervisor= :supervisor or e.id=:id) order by e.firstName",[status:[
                    BayalpatraConstants.TERMINATED,
                    BayalpatraConstants.CLEARED
            ],supervisor:supervisor,id:user?.employee?.id])
        }catch (Exception e){
            e.message()
        }


        return count[0]
    }

    def updateSupervisor(Employee employee){
        def supervisor = Supervisor.findByEmployee(employee)
        if(supervisor){
            def employeeList = Employee.findAllBySupervisor(supervisor)
            employeeList.each{
                it.supervisor = null
            }
        }
    }

    //find all other employees of that dept,
    // find user/employee whose role is dept head
    //first find dept head for the same level
    //if not found, find one level up and so on until dept head is found.


    def getToAddresses(Employee emp){

        def reqEmail
        def reqUser
        def emailArray = new ArrayList<String>()

        def employeesInDepartment = Employee.findAllByDepartment(emp.department)

        def userList = User.findAll("from User as u where u.employee in (:employeeList)", [employeeList : employeesInDepartment])

        def roleList = UserRole.executeQuery("select ur from UserRole ur where ur.user in (:userList) and ur.role = :usrRole",[userList: userList, usrRole: Role.findByAuthority(BayalpatraConstants.ROLE_DEPARTMENT_HEAD)])

        def countOfList = roleList.size();


        if(countOfList!=0){
            roleList.eachWithIndex { val, i ->
                reqUser = roleList[i].user.employee.email
                emailArray.add(reqUser)
            }
        }
        else{
            // if no heads in a department, find the parent
            emailArray = findParentDeptHead(emp.department)
        }
        return emailArray
    }

    def getDeptHead(Department dept){

        def reqUser
        def deptHeadsArray = new ArrayList<String>()
        def userList = User.findAll("from User as u where u.employee.department in (:dept)", [dept : dept])


        def roleList = UserRole.executeQuery("select ur from UserRole ur where ur.user in (:userList) and ur.role = :usrRole",[userList: userList, usrRole: Role.findByAuthority(BayalpatraConstants.ROLE_DEPARTMENT_HEAD)])

        def countOfList = roleList.size();


        if(countOfList!=0){
            roleList.eachWithIndex { val, i ->
                reqUser = roleList[i].user.employee.firstName+" "+roleList[i].user.employee.middleName+" "+roleList[i].user.employee.lastName
                deptHeadsArray.add(reqUser)
            }
        }
        else{
            // if no heads in a department, find the parent
            deptHeadsArray = findParentDeptHeadName(dept)
        }
        return deptHeadsArray
    }
    def findParentDeptHeadName(Department dept){

        def deptHead = new ArrayList<String>()
        def name
        def employeesInParentDepartment

        def parentDepartment = Department.findById(dept.parentId)


        if(parentDepartment!=null){                            // this closure handles case when searching for parent of 'PHECT' department.
            employeesInParentDepartment = Employee.findAllByDepartment(parentDepartment)
        }


        if(employeesInParentDepartment){

            def userList = User.findAll("from User as u where u.employee in (:employeeList)", [employeeList : employeesInParentDepartment])
            def roleList = UserRole.executeQuery("select ur from UserRole ur where ur.user in (:userList) and ur.role = :usrRole",[userList: userList, usrRole: Role.findByAuthority(BayalpatraConstants.ROLE_DEPARTMENT_HEAD)])

            def countOfList = roleList.size();

            if(countOfList==0){
                findParentDeptHead(parentDepartment)
            }else{
                roleList.eachWithIndex { val, i ->
                    name = roleList[i].user.employee.firstName+" "+roleList[i].user.employee.middleName+" "+roleList[i].user.employee.lastName

                    deptHead.add(name)
                }
            }

            return deptHead;              //fix issue in previous function handling one and multiple within the same block.

        }else if(parentDepartment!=null){
            findParentDeptHeadName(parentDepartment)
        }else{
            return [];
        }


    }


    def findParentDeptHead(Department dept){

        def emailArray = new ArrayList<String>()
        def reqEmail
        def employeesInParentDepartment

        def parentDepartment = Department.findById(dept.parentId)


        if(parentDepartment!=null){                            // this closure handles case when searching for parent of 'PHECT' department.
            employeesInParentDepartment = Employee.findAllByDepartment(parentDepartment)
        }


        if(employeesInParentDepartment){

            def userList = User.findAll("from User as u where u.employee in (:employeeList)", [employeeList : employeesInParentDepartment])
            def roleList = UserRole.executeQuery("select ur from UserRole ur where ur.user in (:userList) and ur.role = :usrRole",[userList: userList, usrRole: Role.findByAuthority(BayalpatraConstants.ROLE_DEPARTMENT_HEAD)])

            def countOfList = roleList.size();

            if(countOfList==0){
                findParentDeptHead(parentDepartment)
            }else{
                roleList.eachWithIndex { val, i ->
                    reqEmail = roleList[i].user.employee.email
                    emailArray.add(reqEmail)
                }
            }

            return emailArray;              //fix issue in previous function handling one and multiple within the same block.

        }else if(parentDepartment!=null){
            findParentDeptHead(parentDepartment)
        }else{
            return [];
        }


    }

/*
    def getUnitInchargeEmail(Unit u){


        def employeesInUnit = Employee.findAllByUnit(u);
        def emailArray = new ArrayList<String>()
        def reqUser

        if(employeesInUnit){

            def userList = User.findAll("from User as u where u.employee in (:employeeList)", [employeeList : employeesInUnit])
            def roleList = UserRole.executeQuery("select ur from UserRole ur where ur.user in (:userList) and ur.role = :usrRole",[userList: userList, usrRole: Role.findByAuthority(BayalpatraConstants.ROLE_UNIT_INCHARGE)])

            roleList.eachWithIndex { val, i ->
                reqUser = roleList[i].user.employee.email
                emailArray.add(reqUser)
            }
        }

        return emailArray
    }
*/

    def checkIfEmpIsInDepartment(Employee emp,Department dept){
        def result=false
        def childDept = Department.findAllByParentId(dept.id)
        childDept.each{eachDept->
            if(emp.department.id==eachDept.id) result=true
        }
        return result
    }

    def checkCashierShiftValid(Employee employee){
        def finalResult = false
        def todayRoster = DutyRoster.findWhere("employee":employee,"date":DateUtils.formatDateToYYYYMMDD(new Date()))
        def shift = shiftService.getShift(todayRoster)
        shift.each{
            def shiftSetting = ShiftSetting.findByShift(it)
            if(shiftSetting){
                def result = shiftService.checkIfCurrentShift(it)
                if(result) finalResult = true
            }
        }
        ClnCashierExtraTime.findAllByCashier(employee).each{
            if(it.fromDate.before(new Date()) && it.endDate.after(new Date())) finalResult=true
        }
        return finalResult
    }

    def getCurrentShiftOfEmployee(Employee employee){
        def currentShift = ""
        def dutyRoster = DutyRoster.findWhere("employee":employee,"date":DateUtils.formatDateToYYYYMMDD(new Date()))
        shiftService.getShift(dutyRoster).each{
            if(shiftService.checkIfCurrentShift(it)) currentShift = it
        }
        return currentShift
    }

    def getValuesToExport(employeeList){
        def reportList = []
        def fullName=""
        def sal
        employeeList.each {
            fullName =it.firstName
            if(it.middleName){
                fullName=fullName+" "+it.middleName
            }
            fullName=fullName+" "+it.lastName
            sal=salutations(it.gender,it.isDoc,it.maritalStatus)

            def map = [:]
            map.put("employeeId", it.employeeId)
            map.put("salutation", sal)
            map.put("name",fullName)
            map.put("designation",it.designation)
            map.put("departments",it.department)
            map.put('unit', it.unit )
            map.put("joinDate",it.joinDate)
            map.put("supervisor",it.supervisor)
            map.put("status",it.status)
            map.put("permanentAddress",it.permanentAddress)
            map.put("email",it.email)
            map.put("dateOfBirth",it.dateOfBirth)
            map.put("maritalStatus",it.maritalStatus)
            map.put("gender",it.gender)
            map.put("mobile",it.mobile)
            reportList.add(map)
        }

        return reportList
    }

    def getListForAuditLog(module,sDate,eDate,uName,className,eventName,max,params){
        def auditLog
        def startDate
        def endDate
        if (max){
            params.max= max
        }

        if (sDate && eDate && uName && className && eventName){
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) and al.className in (:class) and al.eventName in (:event) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[actor: uName,module: module,class:className,sdate:startDate,edate:endDate,event: eventName],params)

        }else if (uName && className && eventName){
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.className in (:class) and al.eventName in (:event) and al.actor in (:actor) order by al.dateCreated desc ",[event: eventName,module: module,class:className,actor:uName],params)

        }

        else if(sDate && eDate && uName && className){
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) and al.className in (:class) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[actor: uName,module: module,class:className,sdate:startDate,edate:endDate],params)

        }else if (sDate && eDate && uName && eventName){
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) and al.eventName in (:event) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[actor: uName,module: module,event:eventName,sdate:startDate,edate:endDate],params)

        }else if (sDate && eDate && className && eventName){
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.className in (:class) and al.eventName in (:event) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[event: eventName,module: module,class:className,sdate:startDate,edate:endDate],params)

        }

        else if (sDate && eDate && uName){
            startDate = DateUtils.stringToDate(sDate)
            endDate = DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[actor: uName,module: module,sdate:startDate,edate:endDate],params)

        }else if (sDate && eDate && className){
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.className in (:class) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[module: module,class:className,sdate:startDate,edate:endDate],params)

        }else if (sDate && eDate && eventName){
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.eventName in (:event) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[module: module,event:eventName,sdate:startDate,edate:endDate],params)

        }

        else if (uName && className){

            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) and al.className in (:class) order by al.dateCreated desc ",[actor: uName,module: module,class:className],params)

        }else if (uName && eventName){

            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) and al.eventName in (:event) order by al.dateCreated desc ",[actor: uName,module: module,event:eventName],params)

        }else if (eventName && className){

            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.eventName in (:event) and al.className in (:class) order by al.dateCreated desc ",[event:eventName,module: module,class:className],params)

        }
        else if (sDate && eDate) {
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)

            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[module: module,sdate:startDate,edate:endDate],params)

        }
        else if (uName) {
            auditLog = AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) order by al.dateCreated desc ", [actor: uName, module: module],params)

        }
        else if (className) {
            auditLog = AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.className in (:class) order by al.dateCreated desc ", [module: module, class: className],params)


        }
        else if (eventName) {
            auditLog = AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.eventName in (:event) order by al.dateCreated desc ", [module: module, event: eventName],params)


        }
        else{
            auditLog = AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) order by al.dateCreated desc ", [module: module],params)

        }
        return auditLog
    }



    def getCountForAuditLog(module,sDate,eDate,uName,className,eventName){
        def auditLog
        def startDate
        def endDate

        if (sDate && eDate && uName && className && eventName){
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) and al.className in (:class) and al.eventName in (:event) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[actor: uName,module: module,class:className,sdate:startDate,edate:endDate,event: eventName])

        }else if(sDate && eDate && uName && className){
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) and al.className in (:class) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[actor: uName,module: module,class:className,sdate:startDate,edate:endDate])

        }else if (sDate && eDate && uName && eventName){
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) and al.eventName in (:event) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[actor: uName,module: module,event:eventName,sdate:startDate,edate:endDate])

        }else if (sDate && eDate && className && eventName){
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.className in (:class) and al.eventName in (:event) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[event: eventName,module: module,class:className,sdate:startDate,edate:endDate])

        }
        else if (sDate && eDate && uName){
            startDate = DateUtils.stringToDate(sDate)
            endDate = DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[actor: uName,module: module,sdate:startDate,edate:endDate])

        }else if (sDate && eDate && className){
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.className in (:class) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[module: module,class:className,sdate:startDate,edate:endDate])

        }else if (sDate && eDate && eventName){
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)
            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.eventName in (:event) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[module: module,event:eventName,sdate:startDate,edate:endDate])

        }

        else if (uName && className){

            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) and al.className in (:class) order by al.dateCreated desc ",[actor: uName,module: module,class:className])

        }else if (uName && eventName){

            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) and al.eventName in (:event) order by al.dateCreated desc ",[actor: uName,module: module,event:eventName])

        }else if (eventName && className){

            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.eventName in (:event) and al.className in (:class) order by al.dateCreated desc ",[event:eventName,module: module,class:className])

        }
        else if (sDate && eDate) {
            startDate = DateUtils.stringToDate(sDate)
            endDate=DateUtils.stringToDate(eDate).plus(1)

            auditLog=AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.dateCreated between :sdate and :edate order by al.dateCreated desc ",[module: module,sdate:startDate,edate:endDate])

        }
        else if (uName) {
            auditLog = AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.actor in (:actor) order by al.dateCreated desc ", [actor: uName, module: module])

        }
        else if (className) {
            auditLog = AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.className in (:class) order by al.dateCreated desc ", [module: module, class: className])


        }
        else if (eventName) {
            auditLog = AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) and al.eventName in (:event) order by al.dateCreated desc ", [module: module, event: eventName])


        }
        else{
            auditLog = AuditLogEvent.findAll("from AuditLogEvent al where al.module in (:module) order by al.dateCreated desc ", [module: module])

        }
        return auditLog
    }

    def getEmployeeInstances(criteria,startDate,endDate,params,max){
        def empInstance
        def currentDate=new Date()
        def date=DateGroovyMethods.format(currentDate, 'yyyy')

        if (max){
            params.max= max
        }
        if(criteria=="Recruitment") {
            params.order="desc"
            params.sort="joinDate"

            if(startDate && endDate){
                empInstance=Employee.executeQuery("select e from Employee e where date_format(e.joinDate,'%Y-%m-%d') between :fromdate and :todate order by " +  params.sort +" "+ params.order,[fromdate: startDate,todate: endDate],params)

            }else if(startDate){
                empInstance=Employee.executeQuery("select e from Employee e where date_format(e.joinDate,'%Y-%m-%d')>=:fromdate order by " +  params.sort +" "+ params.order,[fromdate: startDate],params)

            }
            else if(endDate){
                empInstance=Employee.executeQuery("select e from Employee e where date_format(e.joinDate,'%Y-%m-%d')<=:todate order by " +  params.sort +" "+ params.order,[todate: endDate],params)

            }else{
                empInstance=Employee.executeQuery("select e from Employee e where date_format(e.joinDate,'%Y')>=:fromdate order by " +  params.sort +" "+ params.order,[fromdate: date],params)

            }
        } else {
            def designation
            def empName=EmployeeHistory.executeQuery("select distinct e.employee from EmployeeHistory e order by e.employee.firstName asc")

            if(criteria=="Transfer") {
                designation="Department"
            }else{
                designation="Designation"
            }
            params.order="asc"
            params.sort="employee.firstName,id"

            if(startDate && endDate){
                empInstance=EmployeeHistory.executeQuery("select e from EmployeeHistory e where e.fieldType in (:designation) and date_format(e.fromDate,'%Y-%m-%d') between :fromdate and :todate order by " +  params.sort +" "+ params.order,[designation: designation,fromdate: startDate,todate: endDate],params)

            }else if(startDate){
                empInstance=EmployeeHistory.executeQuery("select e from EmployeeHistory e where e.fieldType in (:designation) and date_format(e.fromDate,'%Y-%m-%d')>=:fromdate order by " +  params.sort +" "+ params.order,[designation: designation,fromdate: startDate],params)
            }
            else if(endDate){
                empInstance=EmployeeHistory.executeQuery("select e from EmployeeHistory e where e.fieldType in (:designation) and date_format(e.fromDate,'%Y-%m-%d')<=:todate order by " +  params.sort +" "+ params.order,[designation: designation,todate: endDate],params)

            }else{
                empInstance=EmployeeHistory.executeQuery("select e from EmployeeHistory e where e.fieldType in (:designation) order by " +  params.sort +" "+ params.order,[designation: designation],params)


            }
//                def last=null
//                def first
//                empInstance.each{current->
//                    first=current.iterator().hasNext()
//                       if(first) {
//                           if(last)
//                           {fieldValues.put(last.id,last.oldValue+" to "+current.oldValue)}
//
//                           last=current
//                       }else{
//                           fieldValues.put(current.id,current.oldValue+" to "+current.employee.designation)
//                       }
//
//                }
//                }






            /* def finalMap = [:]
             def endMap=[:]
             empInstance.each {
               finalMap.put(it.id,it?.employeeId)
             }
 
             empInstance.each{
                 if(!finalMap.containsKey(it?.employee))
                     finalMap.put(it?.employee,[])
 
                 finalMap.get(it?.employee)?.add(it?.oldValue)
                 }
             def deptValues
             finalMap.each{newValues->
                        def list=newValues.value
                 for(int i=0;i<list.size()-1;i++){
                     if(list.size()==i+1){
                         endMap.put(newValues.key,list[i])
                     } else{
                         endMap.put(newValues.key,list[i]+" to "+list[i+1])
                     }
 
                 }
             }*/
//            Map empHistory=[:]
//            def empName=EmployeeHistory.executeQuery("select distinct e.employee from EmployeeHistory")
//            empInstance.each{
//                empName=it.employee
//                empHistory.putAt(it,it.employee)
//
//            }

        }

        return empInstance
    }

    def getFromToMap(criteria,startDate,endDate){


        def empInstance
        def designation
        def fieldValues=[:]
        if(criteria=="Transfer") {
            designation="Department"
        }else{
            designation="Designation"
        }
        def empName=EmployeeHistory.executeQuery("select distinct e.employee from EmployeeHistory e where e.fieldType =:designation order by e.employee.firstName asc",[designation:designation])

        empName.each{
            if(startDate && endDate){
                empInstance=EmployeeHistory.executeQuery("select e from EmployeeHistory e where e.fieldType in (:designation) and date_format(e.fromDate,'%Y-%m-%d') between :fromdate and :todate and e.employee in (:empName) order by e.fromDate asc",[designation: designation,fromdate: startDate,todate: endDate,empName:it])

            }else if(startDate){
                empInstance=EmployeeHistory.executeQuery("select e from EmployeeHistory e where e.fieldType in (:designation) and date_format(e.fromDate,'%Y-%m-%d')>=:fromdate and e.employee in (:empName) order by e.fromDate asc",[designation: designation,fromdate: startDate,empName:it])
            }
            else if(endDate){
                empInstance=EmployeeHistory.executeQuery("select e from EmployeeHistory e where e.fieldType in (:designation) and date_format(e.fromDate,'%Y-%m-%d')<=:todate and e.employee in (:empName) order by e.fromDate asc",[designation: designation,todate: endDate,empName:it])

            }else{
                empInstance=EmployeeHistory.executeQuery("select e from EmployeeHistory e where e.fieldType in (:designation) and e.employee in (:empName) order by e.fromDate asc",[designation: designation,empName:it])

            }
            def last=null
            empInstance.each{current->

                if(last){
                    fieldValues.put(last,last.oldValue+"-"+current.oldValue)
                }
                last =current



            }
            if(empInstance){
                if(designation=="Designation"){
                    fieldValues.put(last,last.oldValue+"-"+last.employee.designation+"*")
                }else{
                    fieldValues.put(last,last.oldValue+"-"+last.employee.department+"*")
                }

            }

        }
        return fieldValues
    }




    def getEmployeeCount(criteria,startDate,endDate){
//        params.criteria=="Recruitment"||params.criteria=="Termination"
        def empInstance
        def currentDate=new Date()
        def date=DateGroovyMethods.format(currentDate, 'yyyy')

        if(criteria=="Recruitment"){
            if(startDate && endDate){
                empInstance=Employee.executeQuery("select e from Employee e where date_format(e.joinDate,'%Y-%m-%d') between :fromdate and :todate " ,[fromdate: startDate,todate: endDate])

            }else if(startDate){
                empInstance=Employee.executeQuery("select e from Employee e where date_format(e.joinDate,'%Y-%m-%d')>=:fromdate " ,[fromdate: startDate])

            }
            else if(endDate){
                empInstance=Employee.executeQuery("select e from Employee e where date_format(e.joinDate,'%Y-%m-%d')<=:todate " ,[todate: endDate])

            }else{
                empInstance=Employee.executeQuery("select e from Employee e where date_format(e.joinDate,'%Y')>=:fromdate ",[fromdate: date])

            }
        } else {
            def designation
            if(criteria=="Transfer") {
                designation="Department"
            }else{
                designation="Designation"
            }
            if(startDate && endDate){
                empInstance=EmployeeHistory.executeQuery("select e from EmployeeHistory e where e.fieldType in (:designation) and date_format(e.toDate,'%Y-%m-%d') between :fromdate and :todate ",[designation: designation,fromdate: startDate,todate: endDate])

            }else if(startDate){
                empInstance=EmployeeHistory.executeQuery("select e from EmployeeHistory e where e.fieldType in (:designation) and date_format(e.toDate,'%Y-%m-%d')>=:fromdate ",[designation: designation,fromdate: startDate])

            }
            else if(endDate){
                empInstance=EmployeeHistory.executeQuery("select e from EmployeeHistory e where e.fieldType in (:designation) and date_format(e.toDate,'%Y-%m-%d')<=:todate  " ,[designation: designation,todate: endDate])

            }else{
                empInstance=EmployeeHistory.executeQuery("select e from EmployeeHistory e where e.fieldType in (:designation) ",[designation: designation])

            }
        }

        return empInstance.size()
    }

    def getEmployeeStatus(criteria,startDate,endDate,params,max){
        def empInstance

        if (max){
            params.max= max
        }
        if(criteria=="Terminated"){
            params.order="desc"
            params.sort="firstName"
            if(startDate && endDate){
                empInstance=Employee.executeQuery("select e from Employee e where e.status=:status and date_format(e.terminatedDate,'%Y-%m-%d') between :fromdate and :todate order by " +  params.sort +" "+ params.order,[status: criteria, fromdate: startDate,todate: endDate],params)

            }else if(startDate){
                empInstance=Employee.executeQuery("select e from Employee e where e.status=:status and date_format(e.terminatedDate,'%Y-%m-%d')>=:fromdate order by " +  params.sort +" "+ params.order,[status: criteria, fromdate: startDate],params)

            }
            else if(endDate){
                empInstance=Employee.executeQuery("select e from Employee e where e.status=:status and date_format(e.terminatedDate,'%Y-%m-%d')<=:todate order by " +  params.sort +" "+ params.order,[status: criteria,todate: endDate],params)

            }else{
                empInstance=Employee.executeQuery("select e from Employee e where e.status=:status order by " +  params.sort +" "+ params.order,[status: criteria],params)
            }

        }else{
            params.order="desc"
            params.sort="startDate"
            if(startDate && endDate){
                empInstance=SuspendedEmployeeDetails.executeQuery("select s from SuspendedEmployeeDetails s where date_format(s.startDate,'%Y-%m-%d') between :fromdate and :todate order by " +  params.sort +" "+ params.order,[fromdate: startDate,todate: endDate],params)

            }else if(startDate){
                empInstance=SuspendedEmployeeDetails.executeQuery("select s from SuspendedEmployeeDetails s where date_format(s.startDate,'%Y-%m-%d')>=:fromdate order by " +  params.sort +" "+ params.order,[fromdate: startDate],params)

            }
            else if(endDate){
                empInstance=SuspendedEmployeeDetails.executeQuery("select s from SuspendedEmployeeDetails s where date_format(s.startDate,'%Y-%m-%d')<=:todate order by " +  params.sort +" "+ params.order,[todate: endDate],params)

            }else{
                empInstance=SuspendedEmployeeDetails.findAll([sort: params.sort, order: params.order, max: params.max, offset: params.offset])
            }
        }


        return empInstance
    }

    def getEmployeeStatusCount(criteria,startDate,endDate){
        def empInstance
        if(criteria=="Terminated"){
            if(startDate && endDate){
                empInstance=Employee.executeQuery("select e from Employee e where e.status=:status and date_format(e.terminatedDate,'%Y-%m-%d') between :fromdate and :todate ",[status: criteria, fromdate: startDate,todate: endDate])

            }else if(startDate){
                empInstance=Employee.executeQuery("select e from Employee e where e.status=:status and date_format(e.terminatedDate,'%Y-%m-%d')>=:fromdate ",[status: criteria, fromdate: startDate])

            }
            else if(endDate){
                empInstance=Employee.executeQuery("select e from Employee e where e.status=:status and date_format(e.terminatedDate,'%Y-%m-%d')<=:todate " ,[status: criteria,todate: endDate])

            }else{
                empInstance=Employee.executeQuery("select e from Employee e where e.status=:status ",[status: criteria])
            }

        }else{
            if(startDate && endDate){
                empInstance=SuspendedEmployeeDetails.executeQuery("select s from SuspendedEmployeeDetails s where date_format(s.startDate,'%Y-%m-%d') between :fromdate and :todate ",[fromdate: startDate,todate: endDate])

            }else if(startDate){
                empInstance=SuspendedEmployeeDetails.executeQuery("select s from SuspendedEmployeeDetails s where date_format(s.startDate,'%Y-%m-%d')>=:fromdate ",[fromdate: startDate])

            }
            else if(endDate){
                empInstance=SuspendedEmployeeDetails.executeQuery("select s from SuspendedEmployeeDetails s where date_format(s.startDate,'%Y-%m-%d')<=:todate ",[todate: endDate])

            }else{
                empInstance=SuspendedEmployeeDetails.findAll()
            }

        }


        return empInstance.size()
    }


    def createMapForRecruitment(eachReport){
        def recMap=[:]
        recMap.putAt('employee',eachReport.fullName)
        recMap.putAt('designation',eachReport.designation)
        recMap.putAt('departments',eachReport.department)
        recMap.putAt('dateOfAppointment',DateGroovyMethods.format(eachReport.joinDate, 'yyyy-MM-dd'))
        recMap.putAt('volunteerDays',eachReport.volunteerDays)
        recMap.putAt('serviceType',eachReport.status)
        return recMap
    }

    def createMapForTransferOrPromotion(eachReport,criteria,fromToMap){
        def recMap=[:]
        def label1
        def label2
        def label3
        def stringName=fromToMap.getAt(eachReport)
        def fromVal=stringName.split("-")
        if(criteria=="Promotion"){

            label1=fromVal[0]
            label2=fromVal[1]
            label3=eachReport?.employee?.department
        }else{
            label1=eachReport?.employee?.designation
            label2=fromVal[0]
            label3=fromVal[1]
        }
        recMap.putAt('employee',eachReport.employee.fullName)
        recMap.putAt('label1',label1)
        recMap.putAt('label2',label2)
        recMap.putAt('label3',label3)
        recMap.putAt('dateOfAppointment',DateGroovyMethods.format(eachReport.employee.joinDate, 'yyyy-MM-dd'))
        recMap.putAt('volunteerDays',eachReport.employee.volunteerDays)
        recMap.putAt('serviceType',eachReport.employee.status)
        return recMap
    }

    def createMapForStatus(eachReport){
        def recMap=[:]
        def tDate
        if(eachReport.terminatedDate){
            tDate=DateGroovyMethods.format(eachReport.terminatedDate, 'yyyy-MM-dd')
        }
        def deptUnit
        if(eachReport?.unit){
            deptUnit=eachReport?.unit
        }else{
            deptUnit=eachReport?.department
        }

        recMap.putAt('employee',eachReport)
        recMap.putAt('designation',eachReport?.designation)
        recMap.putAt('departments',deptUnit)
        recMap.putAt('dateFrom',tDate)
        recMap.putAt('dateTo',"")
        recMap.putAt('days',"")
        recMap.putAt('reason',"")
        return recMap
    }

    def createMapForStatusSus(eachReport){
        def recMap=[:]
        def startDate=DateGroovyMethods.format(eachReport.startDate, 'yyyy-MM-dd')
        def endDate=""
        def days =""
        if(eachReport.endDate){
            endDate=DateGroovyMethods.format(eachReport.endDate, 'yyyy-MM-dd')
            days = DateUtils.getDaysFromTwoDates(eachReport.getStartDate(),eachReport.getEndDate())
        }
        def deptUnit
        if(eachReport?.employee?.unit){
            deptUnit=eachReport?.employee?.unit
        }else{
            deptUnit=eachReport?.employee.department
        }
        recMap.putAt('employee',eachReport?.employee)
        recMap.putAt('designation',eachReport?.employee.designation)
        recMap.putAt('departments',deptUnit)
        recMap.putAt('dateFrom',startDate)
        recMap.putAt('dateTo',endDate)
        recMap.putAt('days',days)
        recMap.putAt('reason',"")
        return recMap
    }

    def getTrainingList(criteria,startDate,endDate,params,max){
        def empInstance
        params.order="desc"
        params.sort="startDate"
        if(startDate && endDate){
            empInstance=EmployeeTraining.executeQuery("select e from EmployeeTraining e where date_format(e.startDate,'%Y-%m-%d') between :fromdate and :todate order by " +  params.sort +" "+ params.order,[fromdate: startDate,todate: endDate])

        }else if(startDate){
            empInstance=EmployeeTraining.executeQuery("select e from EmployeeTraining e where date_format(e.startDate,'%Y-%m-%d')>=:fromdate order by " +  params.sort +" "+ params.order,[fromdate: startDate])

        }
        else if(endDate){
            empInstance=EmployeeTraining.executeQuery("select e from EmployeeTraining e where date_format(e.startDate,'%Y-%m-%d')<=:todate order by " +  params.sort +" "+ params.order ,[todate: endDate])
        }else{
            empInstance=EmployeeTraining.findAll([sort: params.sort, order: params.order, max: params.max, offset: params.offset])
        }
        return empInstance
    }

    def getTrainingListCount(criteria,startDate,endDate){
        def empInstance
        if(startDate && endDate){
            empInstance=EmployeeTraining.executeQuery("select e from EmployeeTraining e where date_format(e.startDate,'%Y-%m-%d') between :fromdate and :todate ",[status: criteria, fromdate: startDate,todate: endDate])

        }else if(startDate){
            empInstance=EmployeeTraining.executeQuery("select e from EmployeeTraining e where date_format(e.startDate,'%Y-%m-%d')>=:fromdate ",[status: criteria, fromdate: startDate])

        }
        else if(endDate){
            empInstance=EmployeeTraining.executeQuery("select e from EmployeeTraining e where date_format(e.startDate,'%Y-%m-%d')<=:todate " ,[status: criteria,todate: endDate])
        }else{
            empInstance=EmployeeTraining.findAll()
        }
        return empInstance.size()
    }

    def createReportMapForTraining(eachReport){
        def recMap=[:]
        def unitDepartment
        def dateFrom=DateGroovyMethods.format(eachReport?.startDate, 'yyyy-MM-dd')
        def dateTo=DateGroovyMethods.format(eachReport?.endDate, 'yyyy-MM-dd')
        if(eachReport?.employee?.unit){
            unitDepartment=eachReport.employee?.unit
        }else{

            unitDepartment=eachReport.employee?.department
        }
        def days=DateUtils.getDaysFromTwoDates(eachReport.getStartDate(),eachReport.getEndDate())



        recMap.putAt('employee',eachReport.employee)
        recMap.putAt('designation',eachReport.employee?.designation)
        recMap.putAt('departments',unitDepartment)
        recMap.putAt('dateFrom',dateFrom)
        recMap.putAt('dateTo',dateTo)
        recMap.putAt('days',days)
        recMap.putAt('reason',eachReport?.title)
        return recMap
    }

    def getEmployeeWithServiceMoreSixMth(employee){
        def eligibilityMap = [:]
        def employeeList
        if (employee){
            employeeList = getEmpForSelective(employee.toString(),null,null,null,null)
        }
        employeeList.each {emp->
            if (DateUtils.getServiceDays(emp.joinDate)>=BayalpatraConstants.SIXMONTH){
                eligibilityMap.put(emp,true)
            }else{
                eligibilityMap.put(emp,false)
            }
        }

        return eligibilityMap

    }

}
