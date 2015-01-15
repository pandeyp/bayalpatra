<head>
	<meta name='layout' content='main_hrm' />
	<title>Login</title>
	<style type='text/css' media='screen'>
	#login {
		margin:15px 0px; padding:0px;
		text-align:center;
	}
	#login .inner {
		width:260px;
		margin:0px auto;
		text-align:left;
		padding:10px;
		border-top:1px dashed #499ede;
		border-bottom:1px dashed #499ede;
		background-color:#EEF;
	}
	#login .inner .fheader {
		padding:4px;margin:3px 0px 3px 0;color:#2e3741;font-size:14px;font-weight:bold;
	}
	#login .inner .cssform p {
		clear: left;
		margin: 0;
		padding: 5px 0 8px 0;
		padding-left: 105px;
		border-top: 1px dashed gray;
		margin-bottom: 10px;
		height: 1%;
	}
	#login .inner .cssform input[type='text'] {
		width: 120px;
	}
	#login .inner .cssform label {
		font-weight: bold;
		float: left;
		margin-left: -105px;
		width: 100px;
	}
	#login .inner .login_message {color:red;}
	#login .inner .text_ {width:120px;}
	#login .inner .chk {height:12px;}
	</style>
</head>

<body>
<div class="body_part">
	<div class="login_page">
		<div id="login-box">
		<div class="content-loginhead1">
			<table cellspacing="0" cellpadding="0" border="0">
				<tbody>
				<tr>
					<td><img src="${createLinkTo(dir:'images', file:'login_icon.png')}" alt=""/>
						<h2>Login</h2></td>
				</tr>
				</tbody>
			</table>
		</div>
		<div class="curvedlogin">

			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>

			<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
				<g:hiddenField name="spring-security-redirect" value="/"/>
				<span>
					<label for='username'>Username:</label>
					<input type='text' class='text_' name='j_username' id='username' />
				</span>
				<span>
					<label for='password'>Password:</label>
					<input type='password' class='text_' name='j_password' id='password' />
				</span>
				<span style="margin: 10px 0pt 0pt 110px;">
					<input type="submit" class="darkgreen_btn" value="Login" id="button" name="button">
					<g:link controller='register' action='forgotPassword' class="forgotBtn"><g:message code='spring.security.ui.login.forgotPassword'/></g:link>
				</span>

				<div class="clear"></div>

			</form>
		</div>
	</div>
		<div id="login-box">
			<div class="content-loginhead2">
				<table width="279" cellspacing="0" cellpadding="0" border="0">
					<tbody>
					<tr>
						<td><h2>About Bayalpatra Application</h2></td>
					</tr>
					</tbody>
				</table>
			</div>
			<div class="curvedlogin" style="text-align: justify; color:#005E93; line-height:17px;">

				Bayalpatra is a modular application designed to manage various aspects of HR a hospital. It is a user-controlled, cloud-based application with extensive reporting capabilities and a special focus on role-based security. </div>
		</div></div>
	<script type='text/javascript'>
		<!--
		(function(){
			document.forms['loginForm'].elements['j_username'].focus();
		})();
		// -->
	</script>



</body>

