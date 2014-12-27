package com.bayalpatra.hrm

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class RoleService {

    static transactional = true
    def springSecurityService
    def dataSource
    def session

    def getRole(){

        def role = springSecurityService.getAuthentication().authorities.toString()
        return role

    }


    def insertRole(user,role){
        def sql = new Sql(dataSource)
        def query = "insert into user_role(user_id,role_id) values (${user},${role})"
        sql.execute(query)
    }


    /**
     * Update user's role
     * @param user
     * @param role
     * @return Update role of respective user
     */
    def updateRole(userId,roleId){

        def sql = new Sql(dataSource)

        def query1 = "SELECT ur.role_id FROM user_role ur WHERE ur.user_id = " + userId
        sql.execute(query1)
        def role = sql.firstRow(query1)
        if(role){
            def query2 = "UPDATE user_role ur set ur.role_id="+roleId+" where ur.user_id=" + userId
            sql.execute(query2)
        }else{
            insertRole(userId,roleId)
        }


    }


    /**
     * get role of user according to module he is in
     * @param user
     * @param module
     * @return role of user according to module given,if there is no role for that module then returns role of HR module
     */

/*    def getModuleWiseRole(User user,String module){

        def getAuthorityNameQuery
        def authority
        def sql = new Sql(dataSource)

        def getRoleIdQuery = "SELECT ur.role_id FROM user_role ur WHERE ur.user_id ="+user.id+ " AND ur.module = '" + module + "'"
        def roleId = sql.firstRow(getRoleIdQuery)

        if(roleId){
            getAuthorityNameQuery = "SELECT r.authority FROM role r WHERE r.id =" +roleId[0]
            authority = sql.firstRow(getAuthorityNameQuery)


        }else{

            def getHrRole  = sql.firstRow("SELECT ur.role_id FROM user_role ur WHERE ur.user_id ="+user.id+ " AND ur.module = 'HR'")
            getAuthorityNameQuery = "SELECT r.authority FROM role r WHERE r.id =" +getHrRole[0]
            authority = sql.firstRow(getAuthorityNameQuery)
        }


        return authority[0]


    }*/

}
