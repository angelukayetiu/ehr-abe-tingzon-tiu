<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    

<!DOCTYPE html>
<html>
<head>
<title> CSG EHR - Register </title>
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
			
						<c:if test="${not empty error}">
							<span class="error">${errorMessage}</span>
						</c:if>
						
						<c:if test="${registerMessage!=null}">
							<span class="error">${registerMessage}</span>
						</c:if>
	<form:form method = "POST" action = "register" commandName="registerUser">
		<table>
			<tr><td>
			<spring:hasBindErrors htmlEscape="true" name="registerUser">
			    <c:if test="${errors.errorCount gt 0}">
			    <h4>Unsuccessfully created user:</h4>
		    	<c:forEach var="fieldError" items="${errors.fieldErrors}">
		    		<span class = "error"><spring:message code="${fieldError.code}.${fieldError.objectName}.${fieldError.field}"/></span><br />
		    	</c:forEach>
			  </c:if>   
			</spring:hasBindErrors>			
			</td></tr>
			<tr>
			<td><label>First Name:</label>		</td>
			</tr>
			<tr><td>
			<form:errors class = "error" path="firstName" />	
			</td></tr>
			<tr><td>
		<form:input path="firstName" type = "text" required = "true"
		oninvalid="setCustomValidity('Please fill out this field.')"
	    onchange="try{setCustomValidity('')}catch(e){}" />				
			</td></tr>
			<tr><td>
		<label>Last Name:</label>
			</td></tr>
			<tr><td>
		<form:errors class = "error" path="lastName" />
			</td></tr>
			<tr><td>
		<form:input path="lastName" type = "text" required = "true" 
		oninvalid="setCustomValidity('Please fill out this field.')"
	    onchange="try{setCustomValidity('')}catch(e){}"/>	
			</td></tr>
			<tr><td>
		<label>Email:</label>
			</td></tr>
			<tr><td>
		<form:errors class = "error" path="email" />
			</td></tr>
			<tr><td>
		<form:input path="email" type="text" required = "true"
		pattern = "[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})"
		oninvalid="setCustomValidity('Email is invalid.')"
	    onchange="try{setCustomValidity('')}catch(e){}"/>			
			</td></tr>
			<tr><td>
		<label>Occupation:</label>
			</td></tr>
			<tr><td>
		<form:errors class = "error" path="occupation"/>
			</td></tr>
			<tr><td>
		<form:input path="occupation" type = "text" required = "true" pattern = "[a-zA-Z0-9]+" 
		oninvalid="setCustomValidity('Occupation must be alphanumeric only.')"
	    onchange="try{setCustomValidity('')}catch(e){}"/>			
			</td></tr>
			<tr><td>
		<label>Hospital:</label>
			</td></tr>
			<tr><td>
		<form:errors class = "error" path="hospital"/>			
			</td></tr>
			<tr><td>
		<form:input path="hospital" type = "text" required = "true" pattern = "[a-zA-Z0-9]+" 
		oninvalid="setCustomValidity('Hospital must be alphanumeric only.')"
	    onchange="try{setCustomValidity('')}catch(e){}"/>			
			</td></tr>
	</table>			
		<section class="btns">
		<ul>
			<li><span class="btn"><input type="submit" value="Register" class="btnSave"></span></li>
		</ul>
		<label>
			<span class="forgot"><a href="login" title="">Already a member?</a></span>	
		</label>													
		</section> 
	</form:form>
	
			</div>
	

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