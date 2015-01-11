package com.bayalpatra.hrm

import commons.BayalpatraConstants
import grails.transaction.Transactional

@Transactional
class LeaveTypeService {

    def getLeaveType() {

        def leaveType = LeaveType.findAll("from LeaveType as l WHERE l.leaveType NOT IN (:type) AND l.status!= :status order by l.leaveType asc",[type:[BayalpatraConstants.DAY_OFF_LEAVE,BayalpatraConstants.NIGHT_OFF_LEAVE,BayalpatraConstants.SUBSTITUTE_LEAVE,BayalpatraConstants.FESTIVAL_OFF_LEAVE],status:BayalpatraConstants.STATUS_DISABLED])

        return leaveType
    }
}
