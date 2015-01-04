package com.bayalpatra.hrm

import grails.transaction.Transactional

@Transactional
class EmployeeHistoryService {
    def springSecurityService

    def getFromDate(fieldType,employee) {
        def maxToDate = EmployeeHistory.executeQuery('select max(eh.toDate) from EmployeeHistory eh where eh.fieldType=:fieldType and eh.employee=:employee',[fieldType:fieldType,employee:employee])
        return maxToDate[0]
    }

    def createEmployeeHistory(employee,oldValue,fieldType,fromDate,toDate){
        EmployeeHistory employeeHistory = new EmployeeHistory()
        employeeHistory.employee = employee
        employeeHistory.oldValue = oldValue
        employeeHistory.fieldType = fieldType
        employeeHistory.fromDate = fromDate
        employeeHistory.toDate = toDate
        employeeHistory.user=User.findById(springSecurityService.getPrincipal().id).employee
        employeeHistory.save()
    }
    def createEmployeeHistory(employee,oldValue,fieldType,fromDate,toDate,updatedBy){
        EmployeeHistory employeeHistory = new EmployeeHistory()
        employeeHistory.employee = employee
        employeeHistory.oldValue = oldValue
        employeeHistory.fieldType = fieldType
        employeeHistory.fromDate = fromDate
        employeeHistory.toDate = toDate
        employeeHistory.user=updatedBy
        employeeHistory.save()
    }


}
