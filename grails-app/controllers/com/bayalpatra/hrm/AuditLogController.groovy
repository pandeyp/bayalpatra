package com.bayalpatra.hrm

import org.codehaus.groovy.grails.plugins.orm.auditable.AuditLogEvent

class AuditLogController {


    def index = { redirect(action: list, params: params) }

    def employeeService
    def exportService
    def auditLogService

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete: 'POST', save: 'POST', update: 'POST']

    def list = {
        Map employeeNames = [:]
/*        def module
        if (session['module']=="HR"){
            module="package hrm"
        }else if ((session['module']=="Clinical")){
            module="package clinical"
        }else if ((session['module']=="Inventory")){
            module="package inventory"
        }else{
            module="package account"
        }*/
        def max = 30
        def offset

        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        if(params.offset){
            offset=params.offset
        }else{
            offset = 0
            session.startDate=null
            session.endDate=null
            session.updatedBy=null
            session.className=null
            session.eventName=null
            session.modules=null
            session.size=null
        }

        def auditLog
        def auditLogLength
        def auditLogSize
        def uName

//	params.sort = params.sort?:'dateCreated'
//  params.order = params.order?:'desc'
        if (params.updatedBy){
            def user=User.findByEmployee(Employee.findById(params.updatedBy))
            uName=user?.username
        }

        if (params.updatedBy || params.startDate ||params.endDate||params.className||params.eventName ){

            auditLog=employeeService.getListForAuditLog(params.module,params.startDate,params.endDate,uName,params.className,params.eventName,max,params)
            auditLog.each{
                def empName = User.findByUsername(it.actor)?.employee?.toString()
                if (empName){
                    employeeNames.put(it.id,empName)
                }
            }
            auditLogLength=employeeService.getCountForAuditLog(params.module,params.startDate,params.endDate,uName,params.className,params.eventName)
            auditLogSize=auditLogLength.size()
        }else{
            auditLog=AuditLogEvent.findAll("from AuditLogEvent ae where ae.actor not in (:admin) order by ae.dateCreated desc",[admin:"admin"],params)
            auditLog.each{
                def empName = User.findByUsername(it.actor)?.employee?.toString()
                if (empName){
                    employeeNames.put(it.id,empName)
                }
            }
            auditLogLength=AuditLogEvent.findAll("from AuditLogEvent ae where ae.actor not in (:admin) order by ae.dateCreated desc",[admin:"admin"])
            auditLogSize=auditLogLength.size()
        }

        def className=AuditLogEvent.executeQuery("select distinct ae.className from AuditLogEvent ae")
        def eventName=AuditLogEvent.executeQuery("select distinct ae.eventName from AuditLogEvent ae")

        [auditLogEventInstanceList: auditLog,auditLogEventInstanceTotal: auditLogSize,employeeNames: employeeNames,className:className,eventName:eventName,updatedByVal: params.user,classNameVal:params.className,eventNameVal:params.eventName,startDateVal:params.startDate,endDateVal:params.endDate,updatedBy: params.updatedBy]
    }

    def ajaxCall={
        println("params in audi log ajax call---->"+params)

        Map employeeNames = [:]
        def user
        def uName
        def max = 30
        def offset
        def auditLogSize=0

        params.max = Math.min(params.max ? params.int('max') : 30, 100)
        if(params.offset){
            offset=params.offset
        }else{
            offset = 0
        }
        if (params.updatedBy){
            user=User.findByEmployee(Employee.findById(params.updatedBy))
            uName=user?.username
        }
        session.modules=params.module
        session.startDate=params.startDate
        session.endDate=params.endDate
        session.updatedBy=params.updatedBy
        session.className=params.className
        session.eventName=params.eventName

        def auditLogList = employeeService.getListForAuditLog(params.startDate,params.endDate,uName,params.className,params.eventName,max,params)
        auditLogList.each{
            def empName = User.findByUsername(it.actor)?.employee?.toString()
            if (empName){
                employeeNames.put(it.id,empName)
            }
        }
        def auditLogLength=employeeService.getCountForAuditLog(params.startDate,params.endDate,uName,params.className,params.eventName)

        if (auditLogLength){

            auditLogSize=auditLogLength.size()
        }
        session.size=auditLogSize
        render(template: "ajaxCall",model: [auditLogEventInstanceList: auditLogList,auditLogEventInstanceTotal: auditLogSize,employeeNames: employeeNames, updatedBy: uName,user: params.updatedBy ,startDate: params.startDate,endDate: params.endDate,className: params.className,eventName: params.eventName])


    }

    def exportToExcel={
        def masterExportList=[]
/*        def module
        if (session['module']=="HR"){
            module="package hrm"
        }else if ((session['module']=="Clinical")){
            module="package clinical"
        }else if ((session['module']=="Inventory")){
            module="package inventory"
        }else{
            module="package account"
        }*/
        def startDate =session.startDate
        def endDate=session.endDate
        def updatedBy=session.updatedBy
        def className=session.className
        def eventName=session.eventName
        def modules=session.modules
        def size=session.size
        def uName


        Map employeeNames = [:]
        def auditLog
        def auditLogLength
        def auditLogSize
        if (updatedBy){
            def user=User.findByEmployee(Employee.findById(updatedBy))
            uName=user?.username
        }
        if (updatedBy || startDate ||endDate||className||eventName ){
            auditLog=employeeService.getListForAuditLog(modules,startDate,endDate,uName,className,eventName,size,params)

            auditLog.each{
                def empName = User.findByUsername(it.actor)?.employee?.toString()
                if (empName){
                    employeeNames.put(it.id,empName)
                }

            }

        }else{
            auditLog=AuditLogEvent.findAll("from AuditLogEvent ae where ae.actor not in (:admin) order by ae.dateCreated desc",[admin:"admin"],params)
            auditLog.each{
                def empName = User.findByUsername(it.actor)?.employee?.toString()
                if (empName){
                    employeeNames.put(it.id,empName)
                }
            }

        }


        if(params?.exportFormat && params.exportFormat != "html"){

            masterExportList.add(["actor":"","className":"","eventName":"","instance":"","propertyName":"","oldValue":"","newValue":"","dateCreated":""])
            masterExportList.add(["actor":"Updated By","className":"Class Name","eventName":"Event Name","instance":"Instance","propertyName":"Propert Name","oldValue":"Old Value","newValue":"New Value","dateCreated":"Date Created"])
            auditLog.each{eachReport->
                masterExportList.add(auditLogService.createReportMapForAuditLog(eachReport,employeeNames))
            }
            response.contentType = grailsApplication.config.grails.mime.types[params.exportFormat]
            response.setHeader("Content-disposition", "attachment; filename=Audit_Log_Report.${params.extension}")
            List fields=[
                    "actor",
                    "className",
                    "eventName",
                    "instance",
                    "propertyName",
                    "oldValue",
                    "newValue",
                    "dateCreated"
            ]
            Map labels=["actor":"Audit Log Report","className":"","eventName":"","instance":"","propertyName":"","oldValue":"","newValue":"","dateCreated":""]
            Map parameters =["column.widths": [0.15, 0.15,0.15,0.15,0.15, 0.15,0.15,0.15]]

            exportService.export(params.exportFormat, response.outputStream,masterExportList, fields, labels,[:],parameters)
        }
    }

}
