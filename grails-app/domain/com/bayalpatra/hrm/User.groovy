package com.bayalpatra.hrm

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	Date createdDate
	Date lastUpdatedDate

	static transients = ['springSecurityService']

	static constraints = {
		createdDate(nullable:true)
		lastUpdatedDate(nullable:true)
		username blank: true, nullable: true, matches: "[a-zA-Z0-9]+", unique: true
		password blank: false
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
