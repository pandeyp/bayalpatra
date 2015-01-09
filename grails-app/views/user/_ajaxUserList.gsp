<div class="list">
	<table>
		<thead>
			<tr>
                %{--<th>Select</th>--}%
                <th>Employee</th>
                <th>Username</th>
                <th>Department/Unit</th>
                <th>Role</th>
                <th>Action</th>
                %{--<th>Delete</th>--}%
			</tr>
		</thead>
		<tbody>
			<g:each in="${userList}" status="i" var="usrList">
				<tr>
                    %{--<td><input type="checkbox"> </td>--}%

					<td>
                        <g:if test="${usrList.user.employee}">
                            ${usrList.user.employee?.fullName}
                        </g:if>

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
						</g:link>
					</td>

                    %{--<td id="linked"><g:link controller="user" action="edit"--}%
                                            %{--id='${userList.user.username}'--}%
                                            %{--params="[offset:params.offset,module:userList.module]">--}%
                        %{--<em>Edit</em>--}%
                    %{--</g:link>
                    </td>--}%
				</tr>
			</g:each>
		</tbody>
	</table>
</div>
<div class="paginateButtons">
    <g:paginate total="${userCount}" action="userList" params="[emp:emp,departments:department,role:role]"/>
</div>

