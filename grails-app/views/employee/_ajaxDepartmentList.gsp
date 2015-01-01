<%--
  Created by IntelliJ IDEA.
  User: prativa
  Date: 27 Aug, 2011
  Time: 4:30:30 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
%{--<td>--}%
  %{--SubDepartments--}%
%{--</td>--}%
%{--<select name="subdepartments.id">--}%
  %{--<option value="">--Choose Sub-Department--</option>--}%
  %{--<g:each var="subDepartment" in="${subDepartmentList}">--}%
    %{--<option value="${subDepartment.id}">${subDepartment.name}</option>--}%
  %{--</g:each>--}%
%{--</select>--}%

  <g:select name="subdepartments" from="${subDepartmentList}" optionKey="id" noSelection="['':'--Choose Sub-Department--']"/>
