<html>

<head>
	<title><g:message code='spring.security.ui.forgotPassword.title'/></title>
	<meta name='layout' content='main_hrm'/>
</head>

<body>

%{--<g:hasErrors bean="${companyInstance}">
    <div class="errors">
        <g:renderErrors bean="${companyInstance}" as="list" />
    </div>
</g:hasErrors>--}%
<p/>

<s2ui:form width='350' height='220' elementId='forgotPasswordFormContainer'
		   titleCode='spring.security.ui.forgotPassword.header' center='true'>

	<g:form action='forgotPassword' name="forgotPasswordForm" autocomplete='off'>

		<g:if test='${emailSent}'>
			<br/>
			<g:message code='spring.security.ui.forgotPassword.sent'/>
		</g:if>

		<g:else>


			<p><g:message code='spring.security.ui.forgotPassword.description'/></p>

			<table width="100%">
				<tr>
					<g:if test="${flash.default}">
						<div class="message">${flash.default}</div>
					</g:if>
				</tr>
				<tr>
					<td><label for="username"><g:message code='spring.security.ui.forgotPassword.username'/></label></td>
					<td><g:textField name="username" size="20" class="userNameBox" /></td>
				</tr>
			</table>

		%{--<s2ui:submitButton elementId='reset' form='forgotPasswordForm' messageCode='spring.security.ui.forgotPassword.submit'/>--}%
			<p class="resetPassword"><input type="submit" class="darkgreen_btn" value="Reset Password" id="button" name="button" style="margin-right: 5px;"></p>
		</g:else>

	</g:form>
</s2ui:form>

<script>
	$(document).ready(function() {
		$('#username').focus();
	});
</script>

</body>
</html>
