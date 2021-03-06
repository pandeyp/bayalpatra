<%@ page import="com.bayalpatra.hrm.User" contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta name="layout" content="main_hrm" />
<export:resource />
<g:javascript library="jquery" plugin="jquery"/>
<jqui:resources/>
<script type="text/javascript">



	function filterList(){
	    var employee = $("#employee").val()
        var departments=$("#departments").val()
        var role=$("#role").val()
        var unit=$("#unit").val()
	    var parameter = "emp="+employee+"&departments="+departments+"&role="+role;
		${remoteFunction(controller:'user',action:'searchUser',update:'userGrid', params: "parameter")}
	}

    $(document).ready(function() {
        $('#username').focus();
    });
</script>

</head>
<body>

	<div class="body">
		<div align="right" class="printthis">
			<export:formats formats="['excel']" action="exportToExcel" params="['exportFormat':'excel']" title="Export to Excel" />
			<div class="print">
				<a href="#" title="print"> <img
					src="${resource(dir: 'images', file: "print_icon.png")}"
					alt="Print Table" onclick="window.print()"> </a>
			</div>
		</div>

		<h4>User List</h4>
		<br>
		<div>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>


                    <th class="thead2" width="10%">Employee :</th>
					<th class="thead2" onclick="$('#departments').val('') && $('#role').val('') && $('#unit').val('')"><g:textField name="employee" id="employee"
							value="${employee}" onKeyUp="filterList()"/>
					</th>
                    <th class="thead2">Department</th>
                    <th class="thead2" onclick="$('#employee').val('') && $('#role').val('') && $('#unit').val('')"><g:select from="${com.bayalpatra.hrm.Department.list()}" name="departments" id="departments" value="${department}" optionKey="id" noSelection="['':'--Select--']" onchange="filterList()"></g:select> </th>
                    %{--<th class="thead2">Unit</th>
                    <th class="thead2" onclick="$('#departments').val('') && $('#role').val('') && $('#employee').val('')"><g:select from="${hrm.Unit.list()}" name="unit" id="unit" value="${unit}" optionKey="id" noSelection="['':'--Select--']" onchange="filterList()"></g:select> </th>
--}%
                    <th class="thead2">Role</th>
                    <th class="thead2" onclick="$('#departments').val('') && $('#employee').val('') && $('#unit').val('')"><g:select from="${authority}" name="role" id="role" value="${role}" onchange="filterList()" noSelection="['':'--Select--']"></g:select></th>
                </tr>
			</table>
		</div>

        <div  id="userGrid">
		<div class="list">
			<table>
				<thead>
					<tr>
                        <th>Employee</th>
						<th>Username</th>
						<th>Department</th>
						<th>Role</th>
						<th colspan="2">Action</th>
					</tr>
				</thead>
				<tbody>
					<g:each in="${userList}" status="i" var="usrList">
						<tr>
							<td>
	 							${usrList.user.employee?.fullName}
							</td>

							<td>
								${usrList.user.username}
							</td>

								<td>
									${usrList?.user?.employee?.department}
								</td>

							<td>
								${usrList.role.authority}
							</td>

							<td id="linked"><g:link controller="user" action="edit"
									id='${usrList.user.username}'
									params="[offset:params.offset]">
									<em>Edit</em>
								</g:link></td>
						</tr>
					</g:each>
				</tbody>

			</table>
            </div>
			<div class="paginateButtons">
				<g:paginate total="${userCount}" params="[emp:emp,department:department,role:role]"/>
			</div>
		</div>
	</div>
</body>
</html>