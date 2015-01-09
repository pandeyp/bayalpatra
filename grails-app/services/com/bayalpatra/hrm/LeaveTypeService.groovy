package com.bayalpatra.hrm

import grails.transaction.Transactional

@Transactional
class LeaveTypeService {

    def getLeaveType() {

        def leaveType = LeaveType.findAll("from LeaveType as l WHERE l.leaveType NOT IN (:type) AND l.status!= :status order by l.leaveType asc",[type:[AnnapurnaConstants.DAY_OFF_LEAVE,AnnapurnaConstants.NIGHT_OFF_LEAVE,AnnapurnaConstants.SUBSTITUTE_LEAVE,AnnapurnaConstants.FESTIVAL_OFF_LEAVE],status:AnnapurnaConstants.STATUS_DISABLED])

        return leaveType
    }
}
