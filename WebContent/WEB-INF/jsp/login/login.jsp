<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title> CSG EHR - Login </title>
<meta charset="utf-8">
<c:url var="cssReset" value="/css/reset.css" />
<c:url var="cssTemplate" value="/css/template.css" />
<c:url var="cssForms" value="/css/forms.css" />
<c:url var="cssFormPopup" value="/css/form-popup.css" />
<c:url var="jqueryUiCssUrl" value="/css/jquery-ui.css" />

<c:url var="jqueryUrl" value="/js/jquery-1.9.1.min.js" />
<c:url var="scriptsUrl" value="/js/scripts.js" />

<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/template.css">
<link rel="stylesheet" href="css/forms.css">
<link rel="stylesheet" href="css/form-popup.css">
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css'>

<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/scripts.js"></script>


<!--[if lt IE 9]>
	<script src="js/html5shiv.js"></script>
	<![endif]-->
</head>

<body>

	<div id="wrap">
		<header>
			<h1>
				<a href="#">CSG EHR</a>
			</h1>
		</header>
	</div>

	<%-- 	${error} --%>

	<div class="content-wrap">
		<div class="wrapper">
			<!-- login section -->
			<div class="loginForm">

				<c:url var="validateLogin" value='/validateLogin' />
				<form name="loginForm" action="${validateLogin}" method="POST">
					<table>
						<c:if test="${not empty error}">
							<span class="error">${errorMessage}</span>
						</c:if>
						
						<c:if test="${loginMessage!=null}">
							<span class="error">${loginMessage}</span>
						</c:if>

						<c:if test="${successRegisterMessage!=null}">
							<span class="success">${successRegisterMessage}</span>
						</c:if>
					
						<c:if test="${successForgotPasswordMessage!=null}">
							<span class="success">${successForgotPasswordMessage}</span>
						</c:if>
					
						<tr>
							<td><label>Username:</label></td>
							<td><input type="text" name="username" required autofocus
								placeholder="Enter username"></td>
						</tr>
						<tr>
							<td><label>Password:</label></td>
							<td><input type="password" name="password" required
								placeholder="Enter password"></td>
						</tr>
						<tr>
							<td><span class="btn"><input type="submit"
									value="Login" class="btnNew"></span></td>
							<td><span class="forgot"><a href="forgotPassword" title="">Forgot
										Password</a></span></td>
						</tr>
						<tr>
							<td>
							<span class="forgot"><a href="register" title="">Not yet a user?</a></span>							</td>						
						</tr>
					</table>

				</form>

			</div>
			<!-- end login section -->
		</div>
		<!-- end wrapper-->
	</div>
	<!-- end content-wrap-->



	<footer>
		<span class="copy"><a href="">&copy; 2015 Tingzon Tiu</a></span>
	</footer>

	<!--end wrap-->

	<script src="js/jquery-1.9.1.min.js"></script>
	<script src="js/scripts.js"></script>
</body>
</html>