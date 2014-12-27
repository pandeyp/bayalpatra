/* Copyright 2009-2013 SpringSource.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bayalpatra.hrm

import commons.BayalpatraConstants
import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.authentication.dao.NullSaltSource
import grails.plugin.springsecurity.ui.AbstractS2UiController
import grails.util.GrailsNameUtils
import org.springframework.dao.DataIntegrityViolationException

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class UserController extends AbstractS2UiController {

	def saltSource
	def userCache
	def roleService

	def create() {
		def user = lookupUserClass().newInstance(params)
		[user: user, authorityList: sortedRoles()]
	}

	def save() {
		def user = lookupUserClass().newInstance(params)
		if (params.password) {
			String salt = saltSource instanceof NullSaltSource ? null : params.username
			user.password = springSecurityUiService.encodePassword(params.password, salt)
		}
		if (!user.save(flush: true)) {
			render view: 'create', model: [user: user, authorityList: sortedRoles()]
			return
		}

		addRoles(user)
		flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])}"
		redirect action: 'edit', id: user.id
	}

	def edit = {
		def user
		def roleList
		def authList


		if (params.id) {
			user = User.findByUsername(params.id)
		} else {
			user = User.findByUsername(springSecurityService.principal.username)
		}


		def userRole = UserRole.findByUser(user)
		roleList = [
				BayalpatraConstants.ROLE_ADMIN,
				BayalpatraConstants.ROLE_DEPARTMENT_HEAD,
				BayalpatraConstants.ROLE_SUPERVISOR,
				BayalpatraConstants.ROLE_EMPLOYEE
		]

		/*if (module.equalsIgnoreCase(AnnapurnaConstants.HR)) {
			roleList = [
					AnnapurnaConstants.ROLE_HR_ADMIN,
					AnnapurnaConstants.ROLE_HR_PRIMARY,
					AnnapurnaConstants.ROLE_HR_SECONDARY,
					AnnapurnaConstants.ROLE_DEPARTMENT_HEAD,
					AnnapurnaConstants.ROLE_UNIT_INCHARGE,
					AnnapurnaConstants.ROLE_SUPERVISOR,
					AnnapurnaConstants.ROLE_EMPLOYEE
			]
		}
		else if (module.equalsIgnoreCase(AnnapurnaConstants.INVENTORY)) {
			roleList = [
					AnnapurnaConstants.ROLE_ADMIN_INVENTORY,
					AnnapurnaConstants.ROLE_MAINSTORE_INV,
					AnnapurnaConstants.ROLE_SUBSTORE_INV,
					AnnapurnaConstants.ROLE_PROCUREMENT_INV,
					AnnapurnaConstants.ROLE_NONE
			]
		}else if (module.equalsIgnoreCase(AnnapurnaConstants.CLINICAL)) {
			roleList = [
					AnnapurnaConstants.ROLE_CASHIER,
					AnnapurnaConstants.ROLE_DOCTOR,
					AnnapurnaConstants.ROLE_LAB_TECHNICIAN,
					AnnapurnaConstants.ROLE_ADMIN_CLINICAL,
					AnnapurnaConstants.ROLE_NONE
			]
		}
		*/
		authList = Role.findAll("FROM Role r WHERE r.authority in(:roleList)", [roleList: roleList])

/*

		if (userRole?.role?.authority==AnnapurnaConstants.ROLE_VISITING_DOCTOR)
			authList=Role.findAll("From Role r WHERE r.authority in(:roleList)", [roleList: [AnnapurnaConstants.ROLE_VISITING_DOCTOR]])
*/

		render(view: "editUser", model: [userRole: userRole, authList: authList, offset: params.offset])
	}


	def update() {
//        println(params)
		def roleList
		def authList
		def checkforAuthority

		def currentUser = User.findById(springSecurityService.principal.id)
//        println("currentUser="+currentUser)
		def user = User.findById(params.user)
		def userRole = UserRole.findByUser(user)
		def authorityList = Role.findAll()


		/*if(user.authorities.authority[0]==AnnapurnaConstants.ROLE_VISITING_DOCTOR){
			role = AnnapurnaConstants.ROLE_VISITING_DOCTOR
		}else{
			role = roleService.getModuleWiseRole(user,AnnapurnaConstants.HR)
		}
*/
//		def currentUserRole = roleService.getModuleWiseRole(currentUser, AnnapurnaConstants.HR)
		def currentUserRole = userRole.role.authority
		def oldPassword = user.password
		user.properties = params
//        println(params)

		if (params.isSuperAdmin){
			def a
		}

		if (params.authority) {
			checkforAuthority = Role.findById(Integer.parseInt(params.authority)).authority
		}
		roleList = [
				BayalpatraConstants.ROLE_ADMIN,
				BayalpatraConstants.ROLE_DEPARTMENT_HEAD,
				BayalpatraConstants.ROLE_SUPERVISOR,
				BayalpatraConstants.ROLE_EMPLOYEE
		]

/*
		String module = params.module
		if (module?.equalsIgnoreCase(AnnapurnaConstants.HR)) {
			roleList = [
					AnnapurnaConstants.ROLE_HR_ADMIN,
					AnnapurnaConstants.ROLE_HR_PRIMARY,
					AnnapurnaConstants.ROLE_HR_SECONDARY,
					AnnapurnaConstants.ROLE_DEPARTMENT_HEAD,
					AnnapurnaConstants.ROLE_UNIT_INCHARGE,
					AnnapurnaConstants.ROLE_SUPERVISOR,
					AnnapurnaConstants.ROLE_EMPLOYEE
			]
		}
		else if (module?.equalsIgnoreCase(AnnapurnaConstants.INVENTORY)) {
			roleList = [
					AnnapurnaConstants.ROLE_ADMIN_INVENTORY,
					AnnapurnaConstants.ROLE_MAINSTORE_INV,
					AnnapurnaConstants.ROLE_SUBSTORE_INV,
					AnnapurnaConstants.ROLE_PROCUREMENT_INV,
					AnnapurnaConstants.ROLE_NONE
			]
		}
		else if (module?.equalsIgnoreCase(AnnapurnaConstants.CLINICAL)) {
			roleList = [
					AnnapurnaConstants.ROLE_CASHIER,
					AnnapurnaConstants.ROLE_DOCTOR,
					AnnapurnaConstants.ROLE_LAB_TECHNICIAN,
					AnnapurnaConstants.ROLE_ADMIN_CLINICAL,
					AnnapurnaConstants.ROLE_NONE
			]
		}
*/

		authList = Role.findAll("FROM Role r WHERE r.authority in(:roleList)", [roleList: roleList])
/*
		if (userRole?.role?.authority==AnnapurnaConstants.ROLE_VISITING_DOCTOR)
			authList=Role.findAll("From Role r WHERE r.authority in(:roleList)", [roleList: [AnnapurnaConstants.ROLE_VISITING_DOCTOR]])
*/


		if (!user) return
		if (!versionCheck('user.label', 'User', user, [user: user])) {
			return
		}

		if (params.password && !params.password.equals(oldPassword)) {
			String salt = saltSource instanceof NullSaltSource ? null : params.username
			user.password = springSecurityService.encodePassword(params.password, salt)
		}

/*
		if (checkforAuthority == BayalpatraConstants.ROLE_UNIT_INCHARGE) {
			def unit = user.employee.unit
			if (!unit) {
				flash.message = "Please set unit for the given user."
				render view: 'editUser', model: [userRole: userRole, authorityList: authorityList, user: user, authList: authList]
				return
			}
		}
*/

		if (checkforAuthority == BayalpatraConstants.ROLE_SUPERVISOR) {
			def supervisor = Supervisor.findByEmployee(user?.employee)
			if(!supervisor){
				flash.message = "Please enlist the employee in supervisor setting."
				render view: 'editUser', model: [userRole: userRole, authorityList: authorityList, user: user, authList: authList]
				return
			}
		}

		if (!user.save()) {
			render view: 'editUser', model: [userRole: userRole, authorityList: authorityList, user: user, authList: authList]
			return
		}

		if (params.authority) {
			roleService.updateRole(user.id, params.authority)
		}

		userCache.removeUserFromCache user.username

		if (currentUserRole == BayalpatraConstants.ROLE_ADMIN) {
			redirect(action: 'userList', params: [offset: params.offset])
			return
		} else if (currentUserRole == BayalpatraConstants.ROLE_DEPARTMENT_HEAD || currentUserRole == BayalpatraConstants.ROLE_SUPERVISOR) {
			redirect(controller: 'dashboard', action: 'index')
		} else if (currentUserRole == BayalpatraConstants.ROLE_EMPLOYEE) {
			redirect(controller: 'employee', action: 'edit', params: [employeeIs: currentUser?.employee?.id])
			return
		}
	}

	def delete() {
		def user = findById()
		if (!user) return

		String usernameFieldName = SpringSecurityUtils.securityConfig.userLookup.usernamePropertyName
		try {
			lookupUserRoleClass().removeAll user
			user.delete flush: true
			userCache.removeUserFromCache user[usernameFieldName]
			flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect action: 'search'
		}
		catch (DataIntegrityViolationException e) {
			flash.error = "${message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect action: 'edit', id: params.id
		}
	}

	def search() {
		[enabled: 0, accountExpired: 0, accountLocked: 0, passwordExpired: 0]
	}

	def userSearch() {

		boolean useOffset = params.containsKey('offset')
		setIfMissing 'max', 10, 100
		setIfMissing 'offset', 0

		def hql = new StringBuilder('FROM ').append(lookupUserClassName()).append(' u WHERE 1=1 ')
		def queryParams = [:]

		def userLookup = SpringSecurityUtils.securityConfig.userLookup
		String usernameFieldName = userLookup.usernamePropertyName

		for (name in [username: usernameFieldName]) {
			if (params[name.key]) {
				hql.append " AND LOWER(u.${name.value}) LIKE :${name.key}"
				queryParams[name.key] = params[name.key].toLowerCase() + '%'
			}
		}

		String enabledPropertyName = userLookup.enabledPropertyName
		String accountExpiredPropertyName = userLookup.accountExpiredPropertyName
		String accountLockedPropertyName = userLookup.accountLockedPropertyName
		String passwordExpiredPropertyName = userLookup.passwordExpiredPropertyName

		for (name in [enabled: enabledPropertyName,
		              accountExpired: accountExpiredPropertyName,
		              accountLocked: accountLockedPropertyName,
		              passwordExpired: passwordExpiredPropertyName]) {
			Integer value = params.int(name.key)
			if (value) {
				hql.append " AND u.${name.value}=:${name.key}"
				queryParams[name.key] = value == 1
			}
		}

		int totalCount = lookupUserClass().executeQuery("SELECT COUNT(DISTINCT u) $hql", queryParams)[0]

		Integer max = params.int('max')
		Integer offset = params.int('offset')

		String orderBy = ''
		if (params.sort) {
			orderBy = " ORDER BY u.$params.sort ${params.order ?: 'ASC'}"
		}

		def results = lookupUserClass().executeQuery(
				"SELECT DISTINCT u $hql $orderBy",
				queryParams, [max: max, offset: offset])
		def model = [results: results, totalCount: totalCount, searched: true]

		// add query params to model for paging
		for (name in ['username', 'enabled', 'accountExpired', 'accountLocked',
		              'passwordExpired', 'sort', 'order']) {
		 	model[name] = params[name]
		}

		render view: 'search', model: model
	}

	/**
	 * Ajax call used by autocomplete textfield.
	 */
	def ajaxUserSearch() {

		def jsonData = []

		if (params.term?.length() > 2) {
			String username = params.term
			String usernameFieldName = SpringSecurityUtils.securityConfig.userLookup.usernamePropertyName

			setIfMissing 'max', 10, 100

			def results = lookupUserClass().executeQuery(
					"SELECT DISTINCT u.$usernameFieldName " +
					"FROM ${lookupUserClassName()} u " +
					"WHERE LOWER(u.$usernameFieldName) LIKE :name " +
					"ORDER BY u.$usernameFieldName",
					[name: "${username.toLowerCase()}%"],
					[max: params.max])

			for (result in results) {
				jsonData << [value: result]
			}
		}

		render text: jsonData as JSON, contentType: 'text/plain'
	}

	protected void addRoles(user) {
		String upperAuthorityFieldName = GrailsNameUtils.getClassName(
			SpringSecurityUtils.securityConfig.authority.nameField, null)

		for (String key in params.keySet()) {
			if (key.contains('ROLE') && 'on' == params.get(key)) {
				lookupUserRoleClass().create user, lookupRoleClass()."findBy$upperAuthorityFieldName"(key), true
			}
		}
	}

	protected Map buildUserModel(user) {

		String authorityFieldName = SpringSecurityUtils.securityConfig.authority.nameField
		String authoritiesPropertyName = SpringSecurityUtils.securityConfig.userLookup.authoritiesPropertyName

		List roles = sortedRoles()
		Set userRoleNames = user[authoritiesPropertyName].collect { it[authorityFieldName] }
		def granted = [:]
		def notGranted = [:]
		for (role in roles) {
			String authority = role[authorityFieldName]
			if (userRoleNames.contains(authority)) {
				granted[(role)] = userRoleNames.contains(authority)
			}
			else {
				notGranted[(role)] = userRoleNames.contains(authority)
			}
		}

		return [user: user, roleMap: granted + notGranted]
	}

	protected findById() {
		def user = lookupUserClass().get(params.id)
		if (!user) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect action: 'search'
		}

		user
	}

	protected List sortedRoles() {
		lookupRoleClass().list().sort { it.authority }
	}

	def userList = {
		def userInstanceList
		def count
//        params.max = Math.min(params.max ? params.int('max') : 30, 100)
		if (!params.offset){
			session.emp=null
			session.departments=null
			session.unit=null
			session.role=null
		}

		if(params.emp){
			userInstanceList = employeeService.getUser(params.emp.trim())
			count = userInstanceList.size()
/*
			ClnVisitingDoctor.executeQuery("FROM ClnVisitingDoctor cd WHERE cd.firstName LIKE '"+params.emp+"%'").each{
				if((UserRole.findByUser(User.findByVisitingDoctor(it))?.role?.authority)!=AnnapurnaConstants.ROLE_NONE){
					userInstanceList << UserRole.findByUser(User.findByVisitingDoctor(it))
					count++
				}
			}
*/
			def maxLength
			if (params.max || params.offset){
				if(userInstanceList.size()-1>=Integer.parseInt(params.offset)+Integer.parseInt(params.max)){
					maxLength=Integer.parseInt(params.offset)+Integer.parseInt(params.max)
				}else{
					maxLength=userInstanceList.size()-1
				}
				def finalList = userInstanceList[Integer.parseInt(params.offset)..maxLength]
				userInstanceList = finalList
			}
		}else if (params.departments){
			def deptEmployee=Employee.findAllByDepartments(Departments.findById(params.departments))
			if(deptEmployee){
				userInstanceList=employeeService.getDepartmentList(deptEmployee)

			}else{
				userInstanceList=[]
			}
			count = userInstanceList.size()
			def maxLength
			if (params.max || params.offset){
				if(userInstanceList.size()-1>=Integer.parseInt(params.offset)+Integer.parseInt(params.max)){
					maxLength=Integer.parseInt(params.offset)+Integer.parseInt(params.max)
				}else{
					maxLength=userInstanceList.size()-1
				}
				def finalList = userInstanceList[Integer.parseInt(params.offset)..maxLength]
				userInstanceList = finalList
			}
		}else if (params.role){
			def role=Role.findByAuthority(params.role)
			userInstanceList=UserRole.findAllByRole(role)
			count = userInstanceList.size()
			def maxLength
			if (params.max || params.offset){
				if(userInstanceList.size()-1>=Integer.parseInt(params.offset)+Integer.parseInt(params.max)){
					maxLength=Integer.parseInt(params.offset)+Integer.parseInt(params.max)
				}else{
					maxLength=userInstanceList.size()-1
				}
				def finalList = userInstanceList[Integer.parseInt(params.offset)..maxLength]
				userInstanceList = finalList
			}
		}else if (params.unit){
			def deptEmployee=Employee.findAllByUnit(Unit.findById(params.unit))
			if(deptEmployee){
				userInstanceList=employeeService.getDepartmentList(deptEmployee)

			}else{
				userInstanceList=[]
			}
			count = userInstanceList.size()
			def maxLength
			if (params.max || params.offset){
				if(userInstanceList.size()-1>=Integer.parseInt(params.offset)+Integer.parseInt(params.max)){
					maxLength=Integer.parseInt(params.offset)+Integer.parseInt(params.max)
				}else{
					maxLength=userInstanceList.size()-1
				}
				def finalList = userInstanceList[Integer.parseInt(params.offset)..maxLength]
				userInstanceList = finalList
			}
		}
		else{
			params.max = params.max ?: '30'
			params.offset = params.offset ?: '0'

			Integer start = Integer.parseInt(params.offset)
			Integer end = Integer.parseInt(params.max) + start

			def userList = UserRole.findAll("FROM UserRole ur WHERE ur.user.enabled=1 and ur.role.authority!='"+BayalpatraConstants.ROLE_NONE+"'").sort {it.user.employee?.firstName}

			List<UserRole> requiredList = new ArrayList<UserRole>()

			for (int i = 0; i < userList.size(); i++) {
				if (i >= start && i <= end - 1) {
					if(userList.get(i).user.employee || userList.get(i).user.visitingDoctor) requiredList.add(userList.get(i))
				}
			}
			count = userList.size()
			userInstanceList = requiredList

		}
//        def authority=hrm.Role.findAll("from Role r where r.authority !='"+AnnapurnaConstants.ROLE_NONE+"'")?.authority
		def authority=Role.list()?.authority
		if (authority.contains(BayalpatraConstants?.ROLE_NONE)){authority.remove(authority.indexOf(BayalpatraConstants?.ROLE_NONE))}


		render(view: 'userList', model: [userList: userInstanceList, userCount: count,emp:params.emp,department:params.departments,unit:params.unit,role: params.role,authority: authority])
	}

}
