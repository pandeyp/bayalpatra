<html>

<head>
	<title><g:message code='spring.security.ui.resetPassword.title'/></title>
	<meta name='layout' content='main_hrm'/>
	<g:javascript library="jquery" plugin="jquery"/>
	<jqui:resources/>
</head>

<body>

<p/>

<div id="x">
<s2ui:form width='475' height='250' elementId='resetPasswordFormContainer'
		   titleCode='spring.security.ui.resetPassword.header' center='true'>

	<g:form action='resetPassword' name='resetPasswordForm' autocomplete='off' onsubmit="return validatePasswrds();">
		<g:hiddenField name='t' value='${token}'/>
		<div class="sign-in">

			<br/>
			<h5><g:message code='spring.security.ui.resetPassword.description'/></h5>

			%{--            <table>
                                    <s2ui:passwordFieldRow name='password' labelCode='resetPasswordCommand.password.label' bean="${command}"
                                                         labelCodeDefault='Password' value="${command?.password}"/>

                                    <s2ui:passwordFieldRow name='password2' labelCode='resetPasswordCommand.password2.label' bean="${command}"
                                                         labelCodeDefault='Password (again)' value="${command?.password2}"/>
                        </table>--}%

			<table width="100%">
				<tr>
					<td>Password</td>
					<td><g:passwordField name="password" value = "${command?.password}"/></td>
				</tr>
				<tr>
					<td>Password (Again)</td>
					<td><g:passwordField name="password2" value = "${command?.password}"/></td>
				</tr>
				<tr>
					<td></td>
					<td><g:submitButton name="submit" form="resetPasswordForm" class="savebutton" value="Reset" /></td>
				</tr>
			</table>

			%{--<s2ui:submitButton elementId='reset' form='resetPasswordForm' messageCode='spring.security.ui.resetPassword.submit'/>--}%

		</div>
	</g:form>

</s2ui:form>
</div>
<div id="confirmationDialog" style="display: none">
	<div class="container">
		<fieldset>
			<div id="resetError" class="errors" style="display: none;" ></div>
			<div id="resetMessage" class="message" style="display: none;" ></div>


			Password reset successful. Logging you in.....
			%{--            <table width="100%">
                            <tr>
                                <td>UserName</td>
                                <td><g:textField name="usrName" value="" /></td>
                                <td>&nbsp;</td>
                                <td><input type="button" class="darkgreen_btn" style="border-radius:3px 3px 3px 3px;" value="Reset Password" id="catFormButton" onclick="resetPassword()" /></td>
                            </tr>
                        </table>--}%
		</fieldset>
	</div>
</div>
<script>
	$(document).ready(function() {
		$('#password').focus();
	});

	function validatePasswrds(){

		var valFlag = true;
		var regularExpression = /^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$/;

		var firstPassword = $('#password').val();
		var secondPassword = $('#password2').val();

		if(!firstPassword){
			alert("Password is blank.")
			valFlag = false;
			return valFlag;
		}

		if(firstPassword.length<5){
			alert("Please enter passwords atleast 5 characters long.")
			valFlag = false;
			return valFlag;
		}

		if(firstPassword!=secondPassword){
			alert("The passwords do not match.");
			valFlag = false;
			return valFlag;
		}

		if(!regularExpression.test(firstPassword)){
			alert("Please enter passwords that contain at least a number and a character.");
			valFlag = false;
			return valFlag;
		}

		if(valFlag){
			showDialog()
		}


	}

	function showDialog(){
		$("#confirmationDialog").dialog({
			width:'auto',
			height:'auto',
			resizable:false,
			modal:true,
			title:'Reset Password Successful',
			position: [500,100],
			open: function(event, ui){
				setTimeout("$('#confirmationDialog').dialog('close')",15000);
			}
		});

	}

</script>

</body>
</html>
