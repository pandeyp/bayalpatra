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
                <th>Action</th>
                %{--<th>Delete</th>--}%
			</tr>
		</thead>
		<tbody>
			<g:each in="${userList}" status="i" var="userList">
				<tr>
                    %{--<td><input type="checkbox"> </td>--}%

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
						</g:link>
					</td>

                    %{--<td id="linked"><g:link controller="user" action="edit"--}%
                                            %{--id='${userList.user.username}'--}%
                                            %{--params="[offset:params.offset,module:userList.module]">--}%
                        %{--<em>Edit</em>--}%
                    %{--</g:link>--}%
                    </td>
				</tr>
			</g:each>
		</tbody>
	</table>
</div>
<div class="paginateButtons">
    <g:paginate total="${userCount}" action="userList" params="[emp:emp,departments:department,unit:unit,role:role]"/>
</div>

