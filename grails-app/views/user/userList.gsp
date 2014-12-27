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
	    var parameter = "emp="+employee+"&departments="+departments+"&role="+role+"&unit="+unit;
		${remoteFunction(controller:'user',action:'searchUser',update:'userGrid', params: "parameter")}
	}

//    $(function () {
//        $('.editHref').click(
//
//                function () {
//                    var getList = new Array();
//                    var id = $(this).attr('atr');
//                    getList.push(id);
//                    callAjax(getList);
//
//                }
//        );
//        $('.chkSelectAll').click(function () {
//            //  $('input[name=foo]').is(':checked')
//
//            if ($(this).is(':checked')) {
//                $('table tbody input:checkbox').attr('checked', true);
//
//            }
//            else {
//
//                $('table tbody input:checkbox').attr('checked', false);
//
//            }
//        });
//        $('.delCheck').click(function(){
//            $('table tbody input:checkbox').each(function () {
//                if ($(this).is(':checked')) {
//
//
//                    return false;
//                }
//                else
//                {}
//            });
//        });
//        $('.btnDelete').click(function () {
//            var getList = new Array();
//            var i = 0;
//            $('table tbody input:checkbox').each(function () {
//                if ($(this).is(':checked')) {
//
//                    getList.push($(this).attr('id'));
//
//                }
//            });
//            if (getList.length > 0) {
//                var str = $.param(getList);
//
//                callAjax(getList);
//            }
//            else{
//
//                alert('Choose Atleast One!!');
//
//            }});
//
//
//    });
//    function callAjax(getList) {
//        if (!confirm('Delete These Entries!!!'))
//            return;
//
//
//        $.ajax({
//            url: 'deleteEntry',
//            data: {'ids': JSON.stringify(getList)},
//            async: true,
//            success: function (data) {
//                location.reload();
//            },
//            error: function (data) {
//                alert('error');
//            },
//            complete: function (data) {
//            }
//        });
//    }
    $(document).ready(function() {
        $('#username').focus();
        <s2ui:initCheckboxes/>
    });
</script>

</head>
<body>

	<div class="body">
		<div align="right" class="printthis">
			<export:formats formats="['excel']" action="exportToExcel" params="" title="Export to Excel" />
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
                    <th class="thead2" onclick="$('#employee').val('') && $('#role').val('') && $('#unit').val('')"><g:select from="${hrm.Departments.list()}" name="departments" id="departments" value="${department}" optionKey="id" noSelection="['':'--Select--']" onchange="filterList()"></g:select> </th>
                    <th class="thead2">Unit</th>
                    <th class="thead2" onclick="$('#departments').val('') && $('#role').val('') && $('#employee').val('')"><g:select from="${hrm.Unit.list()}" name="unit" id="unit" value="${unit}" optionKey="id" noSelection="['':'--Select--']" onchange="filterList()"></g:select> </th>

                    <th class="thead2">Role</th>
                    <th class="thead2" onclick="$('#departments').val('') && $('#employee').val('') && $('#unit').val('')"><g:select from="${authority}" name="role" id="role" value="${role}" onchange="filterList()" noSelection="['':'--Select--']"></g:select></th>
                </tr>
			</table>
		</div>
        %{--<g:if test="${userList}">--}%
            %{--<input type="checkbox" class="chkSelectAll"  >Select All<input type="button"  class="btnDelete" value="Delete">--}%
        %{--</g:if>--}%
        <div  id="userGrid">
		<div class="list">
			<table>
				<thead>
					<tr>
                        %{--<th>Select</th>--}%

                        <th>Employee</th>
						<th>Username</th>
						<th>Department/Unit</th>
						<th>Role</th>
						<th>Module</th>
						<th colspan="2">Action</th>
					</tr>
				</thead>
				<tbody>
					<g:each in="${userList}" status="i" var="userList">
						<tr>
                            %{--<td><input type="checkbox"  class='delCheck'id="${fieldValue(bean: userList, field: "id")}"/></td>--}%
							<td>
                                <g:if test="${userList.user.employee}">
								    ${userList.user.employee?.fullName}
                                </g:if>
                                <g:else>
                                    ${userList.user.visitingDoctor.toString()}
                                </g:else>
							</td>

							<td>
								${userList.user.username}
							</td>
							<g:if test="${userList?.user?.employee?.unit}">
								<td>
									${userList?.user?.employee?.unit}
								</td>
							</g:if>
							<g:else>
								<td>
									${userList?.user?.employee?.departments}
								</td>
							</g:else>

							<td>
								${userList.role.authority}
							</td>
							<td>
								${userList.module}
							</td>
							<td id="linked"><g:link controller="user" action="edit"
									id='${userList.user.username}'
									params="[offset:params.offset,module:userList.module]">
									<em>Edit</em>
								</g:link></td>
                            %{--<td>--}%
                                %{--<a href="#" atr="${fieldValue(bean: userList, field: "id")}"--}%
                                   %{--class="editHref" style="color:red">delete</a>--}%
                            %{--</td>--}%
						</tr>
					</g:each>
				</tbody>

			</table>
            </div>
			<div class="paginateButtons">
				<g:paginate total="${userCount}" params="[emp:emp,department:department,unit:unit,role:role]"/>
			</div>
		</div>
	</div>
</body>
</html>