import com.bayalpatra.hrm.Requestmap
import com.bayalpatra.hrm.Role
import com.bayalpatra.hrm.User
import com.bayalpatra.hrm.UserRole

class BootStrap {

    def init = { servletContext ->
        def adminRole
        adminRole=Role.findByAuthority('ROLE_ADMIN')?:new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def employeeRole
        employeeRole=Role.findByAuthority('ROLE_EMPLOYEE')?:new Role(authority: 'ROLE_EMPLOYEE').save(flush: true)
        def supervisorRole
        supervisorRole=Role.findByAuthority('ROLE_SUPERVISOR')?: new Role(authority: 'ROLE_SUPERVISOR').save(flush: true)

        def testAdmin = User.findByUsername('admin')?:new User(id: '1', username: 'admin', enabled: true, password: 'admin').save(flush: true)

        if (!testAdmin.authorities.contains(adminRole)) {
            UserRole.create testAdmin,adminRole
        }

        for (String url in [
                '/', '/index', '/index.gsp', '/favicon.ico',
                '/assets/**', '/js/**', '/css/**', '/images/**',
                '/login', '/login.*', '/login/*',
                '/logout', '/logout.*', '/logout/*',
                /*'/rssUser','/rssUser*//*','/RSS_Image*//**',*/'/register/**','/image/**'
        ]) {
            Requestmap.findByUrl(url)?"":new Requestmap(url: url, configAttribute: 'permitAll').save()
        }
/*        new Requestmap(url: '/profile*//**',    configAttribute: 'ROLE_USER').save()
        new Requestmap(url: '/admin*//**',      configAttribute: 'ROLE_ADMIN').save()
        new Requestmap(url: '/admin/role*//**', configAttribute: 'ROLE_SUPERVISOR').save()
        new Requestmap(url: '/admin/user*//**', configAttribute: 'ROLE_ADMIN,ROLE_SUPERVISOR').save()
        new Requestmap(url: '/j_spring_security_switch_user',configAttribute: 'ROLE_SWITCH_USER,isFullyAuthenticated()').save()*/


        Requestmap.findByUrl('/user/**')?"":new Requestmap(url: '/user/**', configAttribute: 'ROLE_ADMIN').save()
        Requestmap.findByUrl('/role/**')?"":new Requestmap(url: '/role/**', configAttribute: 'ROLE_ADMIN').save()
        Requestmap.findByUrl('/plugins*//**')?"":new Requestmap(url: '/plugins*//**', configAttribute: 'permitAll').save()

        Requestmap.findByUrl('/department*//**')?"":new Requestmap(url: '/department*//**', configAttribute: 'ROLE_ADMIN').save()

        /*Requestmap.findByUrl('/registrationCode*//**')?"":new Requestmap(url: '/registrationCode*//**', configAttribute: 'ROLE_ADMIN').save()
        Requestmap.findByUrl('/securityInfo*//**')?"":new Requestmap(url: '/securityInfo*//**', configAttribute: 'ROLE_ADMIN').save()
        Requestmap.findByUrl('/category*//**')?"":new Requestmap(url: '/category*//**', configAttribute: 'ROLE_ADMIN,ROLE_CUSTOMER,ROLE_EMPLOYEE').save()
        Requestmap.findByUrl('/RSS_Audio*//**')?"":new Requestmap(url: '/RSS_Audio*//**', configAttribute: 'ROLE_ADMIN,ROLE_CUSTOMER,ROLE_EMPLOYEE').save()
        Requestmap.findByUrl('/news*//**')?"":new Requestmap(url: '/news*//**', configAttribute: 'ROLE_ADMIN,ROLE_CUSTOMER,ROLE_EMPLOYEE').save()
        Requestmap.findByUrl('/audioClips*//**')?"":new Requestmap(url: '/audioClips*//**', configAttribute: 'ROLE_ADMIN,ROLE_CUSTOMER,ROLE_EMPLOYEE').save()
        Requestmap.findByUrl('/downloader*//**')?"":new Requestmap(url: '/downloader*//**', configAttribute: 'ROLE_ADMIN,ROLE_CUSTOMER,ROLE_EMPLOYEE').save()
        Requestmap.findByUrl('/dynatables*//**')?"":new Requestmap(url: '/dynatables*//**', configAttribute: 'ROLE_ADMIN,ROLE_CUSTOMER,ROLE_EMPLOYEE').save()
        Requestmap.findByUrl('/dataTables*//**')?"":new Requestmap(url: '/dataTables*//**', configAttribute: 'ROLE_ADMIN,ROLE_CUSTOMER,ROLE_EMPLOYEE').save()
        Requestmap.findByUrl('/RSS_CompiledNews*//**')?"":new Requestmap(url: '/RSS_CompiledNews*//**', configAttribute: 'ROLE_ADMIN,ROLE_CUSTOMER,ROLE_EMPLOYEE').save()
        Requestmap.findByUrl('/compiledNews*//**')?"":new Requestmap(url: '/compiledNews*//**', configAttribute: 'ROLE_ADMIN,ROLE_CUSTOMER,ROLE_EMPLOYEE').save()
        Requestmap.findByUrl('/plugins*//**')?"":new Requestmap(url: '/plugins*//**', configAttribute: 'permitAll').save()
        Requestmap.findByUrl('/engnep*//**')?"":new Requestmap(url: '/engnep*//**', configAttribute: 'ROLE_ADMIN,ROLE_CUSTOMER,ROLE_EMPLOYEE').save()

         SpringSecurityUtils.clientRegisterFilter('authenticationProcessingFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 10)

         println(" .------..------..------.\n" +
         "|R.--. ||S.--. ||S.--. |\n" +
         "| :(): || :/\\: || :/\\: |\n" +
         "| ()() || :\\/: || :\\/: |\n" +
         "| '--'R|| '--'S|| '--'S|\n" +
         "`------'`------'`------' ")
         println "!!! Build Success !!!"
         */


    }

    def destroy = {
    }
}
