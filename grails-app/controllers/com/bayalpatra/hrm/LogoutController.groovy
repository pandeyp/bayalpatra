/* Copyright 2013-2014 SpringSource.
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

import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.security.access.annotation.Secured

import javax.servlet.http.HttpServletResponse


class LogoutController {

	/**
	 * Index action. Redirects to the Spring security logout uri.
	 */
	def index() {
		// TODO put any pre-logout code here
		println "in logout controller::" + SpringSecurityUtils.securityConfig.logout.filterProcessesUrl
		flash.message = "Successfully logged out."
		redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/j_spring_security_logout'

	}
}
