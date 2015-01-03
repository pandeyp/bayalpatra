package com.bayalpatra.hrm

import com.bayalpatra.hrm.Supervisor
import grails.transaction.Transactional

@Transactional
class SupervisorService {

    def getSupervisorList(employee,params,max,offset,sort,order) {
        params.max = max
        params.offset=offset
        def supervisorList = Supervisor.findAll("from Supervisor s where s.employee in (:employee) order by ${sort} ${order}",[employee:employee],params)
        return supervisorList
    }

    def getSupervisorCount(employee){
        def count = Supervisor.executeQuery("select count(*) from Supervisor s where s.employee in (:employee)",[employee:employee])
        return count[0]
    }


}
