package com.bayalpatra.hrm


import static org.springframework.http.HttpStatus.*

class DepartmentController {
    def exportService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def employeeService

    DepartmentService departmentService
    def grailsApplication

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {

        def parentName = []
        def departmentsInstanceList
        def parentDept = []
        def parents=Department.executeQuery("select distinct parentId from Department")
        parents.eachWithIndex {  val,i->
            long num=val
            parentDept[i]=num
        }
        def parentList=Department.findAll("from Department d where d.id in (:parents)",[parents:parentDept])


        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        params.sort = params.sort?:'name'
        params.order = params.order?:'asc'
        def count
        if (!params.offset){
            session.deptName=null
            session.parentName=null
        }
        if (params.deptName){
            def deptName=params.deptName.toString()
            departmentsInstanceList = Department.findAll("from Departments d where d.name LIKE '" + deptName+"%'")
            count=Department.findAll("from Departments d where d.name LIKE '" + deptName+"%'")
        }
        else if (params.parentName){

            departmentsInstanceList = Department.findAllByParentId(params.parentName,params)
            count = Department.findAllByParentId(params.parentName)
        }
        else{
            departmentsInstanceList=Department.list(params)
            count = Department.list()
        }
        departmentsInstanceList.eachWithIndex {    val,i->
            parentName[i] = Department.findById(val.parentId)

        }

        [departmentsInstanceList:departmentsInstanceList, departmentsInstanceTotal:count.size(),pName:parentName,parentDepartment:parentList,parentName:params.parentName,deptName: params.deptName]
    }

    def ajaxCallForDepartments={
        def parentName = []
        def departmentsInstanceList

        session.deptName=params.deptName
        session.parentName=params.parentName


        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        params.sort = params.sort?:'name'
        params.order = params.order?:'asc'
        def count

        if (params.deptName){
            def deptName=params.deptName.toString()
            departmentsInstanceList = Department.findAll("from Department d where d.name LIKE '" + deptName+"%'")
            count=Department.findAll("from Department d where d.name LIKE '" + deptName+"%'")
        }
        else if (params.parentName){

            departmentsInstanceList = Department.findAllByParentId(params.parentName,params)
            count = Department.findAllByParentId(params.parentName)
        }
        else{
            departmentsInstanceList=Department.list(params)
            count = Department.list()
        }



        departmentsInstanceList.eachWithIndex {    val,i->
            parentName[i] = Department.findById(val.parentId)

        }


        render(template: "ajaxCallForDepartments", model: [departmentsInstanceList:departmentsInstanceList, departmentsInstanceTotal: count.size(),pName:parentName,parentName:params.parentName,deptName: params.deptName])
    }

    def exportToExcel={
        def departmentsInstanceList
        def count
        def parentName = []

        def deptName1=session.deptName
        def parentName1=session.parentName

        if(params?.format && params.format != "html"){



            if (deptName1){
                def deptName=deptName1.toString()
                departmentsInstanceList = Department.findAll("from Departments d where d.name LIKE '" + deptName+"%'")
                count=Department.findAll("from Departments d where d.name LIKE '" + deptName+"%'")
            }
            else if (parentName1){

                departmentsInstanceList = Department.findAllByParentId(parentName1)
                count = Department.findAllByParentId(parentName1)
            }
            else{
                departmentsInstanceList=Department.list()
                count = Department.list()
            }
            departmentsInstanceList.eachWithIndex {    val,i->
                parentName[i] = Department.findById(val.parentId)

            }

            response.contentType = grailsApplication.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=Departments.${params.extension}")
            List fields=[
                    "name",
                    "parentId",
                    "idNumber",
                    "isMainStore",
                    "isSubStore"
            ]
            Map labels=["name":"Name","parentId":"Parent Name","idNumber":"Department Code","isMainStore":"Main Store","isSubStore":"Sub Store"]
            Map parameters =["column.widths": [0.15, 0.15,0.20]]
            def booleanToYesNo= {
                domain,value ->
                    return value?"Yes":"No"
            }

            def getParentName = {
                domain,value ->
                    def pName = Department.findById(value)
                    if(pName){
                        return pName.name
                    }else{
                        return null
                    }
            }

            Map formatter = [isMainStore:booleanToYesNo, isSubStore:booleanToYesNo,parentId:getParentName]
            exportService.export(params.format, response.outputStream,departmentsInstanceList, fields, labels,formatter,parameters)
        }
    }

    def create = {

        def departmentsInstance = new Department()
/*        def mainStore = Department.findByIsMainStore(true)
        def isMainStore = false
        if(mainStore){
            mainStore = true
        }*/
        departmentsInstance.properties = params
        departmentService.departmentTree = ""
        def deptTree =  departmentService.generateNavigation(0)

       // println("deptTree sent to page----->"+deptTree.toString()) org.codehaus.groovy.grails.support.encoding;

        return [departmentsInstance: departmentsInstance, deptTree : deptTree]
    }


    def save = {

        def parentDept
        def departmentsInstance = new Department(params)
        def idNum = departmentService.getIdNumber()
        departmentService.departmentTree = ""
        def deptTree = departmentService.generateNavigation(0)
        def rootDept = new Department()
        if(params.parentId){
            int parent = Integer.valueOf(params.parentId)
            if(parent!=1){
                while(parent!=1){
                    rootDept = departmentService.getRoot(parent)
                    parent = rootDept.parentId
                }
                departmentsInstance.idNumber = (rootDept.idNumber).toString().padLeft(2,'0')
            }else{
                rootDept = departmentService.getRoot(parent)
                departmentsInstance.idNumber = (idNum+1).toString().padLeft(2,'0')
            }
        }

        if(departmentsInstance.parentId){
            parentDept = Department.findById(departmentsInstance.parentId)
        }
        departmentsInstance.rootId = rootDept.id
        if (departmentsInstance.save(flush: true)) {
            redirect(action: "list", id: departmentsInstance.id)
        }
        else {
            render(view: "create", model: [departmentsInstance: departmentsInstance,parentDept:parentDept, deptTree : deptTree])
        }

    }


    def show = {
        def departmentsInstance = Department.get(params.id)
        def parentName = Department.findById(departmentsInstance.parentId)
        if (!departmentsInstance) {
            redirect(action: "list")
        }
        else {
            [departmentsInstance: departmentsInstance,pName:parentName ]
        }
    }

    def edit = {

        def departmentsInstance = Department.get(params.id)
        def parentName = Department.findById(departmentsInstance.parentId)
        departmentService.departmentTree = ""
        def deptTree =  departmentService.generateNavigation(0)

/*        def mainStoreStatus = departmentsInstance.isMainStore
        def thisIsMainStore = false
        if(mainStoreStatus){
            thisIsMainStore = true
        }
        def mainStore = Departments.findByIsMainStore(true)
        def isMainStore = false
        if(mainStore){
            isMainStore = true
        }*/

        if (!departmentsInstance) {
            redirect(action: "list")
        }
        else {
            return [departmentsInstance: departmentsInstance,pName: parentName,deptTree:deptTree, offset:params.offset]
        }
    }

    def update = {

        departmentService.departmentTree = ""
        def deptTree =  departmentService.generateNavigation(0)

        def departmentsInstance = Department.get(params.id)
        def parentName = Department.findById(departmentsInstance.parentId)
        if (departmentsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (departmentsInstance.version > version) {

                    departmentsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
                            message(code: 'departments.label', default: 'Departments')]
                            as Object[], "Another user has updated this Departments while you were editing")
                    render(view: "edit", model: [departmentsInstance: departmentsInstance])
                    return
                }
            }

            if(departmentsInstance.parentId != params.parentId.toInteger()){
                departmentsInstance.properties = params
                if(departmentsInstance.parentId!=1){
                    def rootDept = departmentService.getRoot(departmentsInstance.parentId)
                    departmentsInstance.idNumber = rootDept.idNumber
                }else{
                    departmentsInstance.rootId=1
                    def idNum = departmentService.getIdNumber()
                    departmentsInstance.idNumber = (idNum+1).toString().padLeft(2,'0')
                }
            }

            departmentsInstance.properties = params

            if (!departmentsInstance.hasErrors() && departmentsInstance.save(flush: true)) {
     /*           if(params.oldDeptCode!=departmentsInstance.rootId){
                    def departmentEmployees=[]
                    departmentEmployees = Employee.findAllByDepartment(departmentsInstance)
                    departmentEmployees.each{
                        def employeeId = it.employeeId
                        def empId = it.employeeId.substring(2,7)
                        it.employeeId= employeeService.updateEmployeeId(departmentsInstance.idNumber,empId)
                        it.save(failOnError: true)
                    }
                }*/
                redirect(action: "list",params:[offset:params.offset])
            }
            else {
                render(view: "edit", model: [departmentsInstance: departmentsInstance,deptTree:deptTree,pName: parentName])
            }
        }
        else {
            redirect(action: "list")
        }
    }

    def delete = {
        def departmentsInstance = Department.get(params.id)
        if (departmentsInstance) {
            try {
                departmentsInstance.delete(flush: true)
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                redirect(action: "show", id: params.id)
            }
        }
        else {
            redirect(action: "list")
        }
    }

    def showTree = {
        departmentService.departmentTree = ""
        def deptTree = departmentService.generateNavigation(0)
        [tree:deptTree]
    }
}
