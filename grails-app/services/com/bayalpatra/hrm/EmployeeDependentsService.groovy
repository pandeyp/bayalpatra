package com.bayalpatra.hrm

import commons.DateUtils
import grails.transaction.Transactional

@Transactional
class EmployeeDependentsService {

/*    def getImageFileName(name,imageFile) {
        def String fileName;
        Date currentDate = DateUtils.getCurrentDate()
        def Random randomGenerator = new Random()
        def String fileNamePrefix = currentDate.time.toString()+"_"+randomGenerator.nextInt(1000000);
        fileName = name+'_'+fileNamePrefix+ ".jpg";
        def root = ApplicationHolder.getApplication().getMainContext().getResource("/").getFile().getAbsolutePath()
        def String empImagePath = root+"/images/dependents/";
        def File uploadedImage = new File(empImagePath+fileName)
        imageFile.transferTo(uploadedImage) //Writing Original File
        return fileName

    }*/

    /**
     * This method is used to populate the employee List when the user enters name in the employee search text field.
     * @param employeeName
     * @return List of {@link Employee}(s)
     */
    def getEmp(String employeeName,params){
        def employeeInstanceList = EmployeeDependents.findAll("FROM EmployeeDependents ed WHERE ed.employee.firstName LIKE '" + employeeName + "%' AND ed.employee.status NOT IN (:status)",[status:[AnnapurnaConstants.TERMINATED,AnnapurnaConstants.CLEARED,AnnapurnaConstants.SUSPENDED]],params)
        return employeeInstanceList
    }

    def getEmpCount(String employeeName){
        return EmployeeDependents.findAll("FROM EmployeeDependents ed WHERE ed.employee.firstName LIKE '" + employeeName + "%' AND ed.employee.status NOT IN (:status)",[status:[AnnapurnaConstants.TERMINATED,AnnapurnaConstants.CLEARED,AnnapurnaConstants.SUSPENDED]]).size()
    }

    def getDependentList(employee,params,max){

        params.max=max
        def dependentList = EmployeeDependents.findAll("from EmployeeDependents ed where ed.employee in (:employee) order by ed.employee.firstName",[employee:employee],params)
        return dependentList

    }

    def getDependentListCount(employee){
        def count = EmployeeDependents.executeQuery("select count(*) from EmployeeDependents ed where ed.employee in (:employee)",[employee:employee])
        return count[0]

    }
}
