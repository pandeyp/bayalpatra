package com.bayalpatra.commons

import grails.transaction.Transactional

@Transactional
class AuditLogService {

    def createReportMapForAuditLog(eachReport,emp){
        def recMap=[:]
        def updatedBy=emp.getAt(eachReport.id)
        recMap.putAt('actor',updatedBy)
        recMap.putAt('className',eachReport.className)
        recMap.putAt('eventName',eachReport.eventName)
        recMap.putAt('instance',eachReport.instance)
        recMap.putAt('propertyName',eachReport.propertyName)
        recMap.putAt('oldValue',eachReport.oldValue)
        recMap.putAt('newValue',eachReport.newValue)
        recMap.putAt('dateCreated',eachReport.dateCreated)
        return recMap

    }
}
