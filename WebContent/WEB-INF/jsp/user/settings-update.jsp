<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<!doctype html>
<html lang="en">
<jsp:include page="/WEB-INF/jsp/home/home-header.jsp" >
    <jsp:param name="title" value="Settings" />
    <jsp:param name="selected" value="settings" />
</jsp:include>

<c:url var="cssSettings" value="/css/settings.css"/>
<link rel="stylesheet" href="${cssSettings}">

<div class="pageCtrl"> <!--no buttons here--></div>

<div class="content-wrap">
	
	<div class="wrapper">
		<nav class="searchNav">
			<form class="searchbar">
				<ul>
					<li><input type="text" value="" placeholder="Search" class="search"/></li>
					<li><span class="btn"><input type="button" value="Users List" class="btnReturn"></span></li>	
					<li><span class="btn"><input type="button" value="Edit" class="btnEdit"></span></li>
					<c:if test="${loggedInUser.recordsOfficer}">
						<c:if test="${selectedUser.enabled}">	
							<li><span class="btn"><input type="button" value="Deactivate" class="btnClose cancelnewuser"></span></li>
						</c:if>						
						<c:if test="${!selectedUser.enabled}">		
							<li><span class="btn"><input type="button" value="Activate" class="btnNew bntNewUser" name = "btnActivate"></span></li>
						</c:if>
						<li><span class="btn"><input type="button" value="Reset Password" name = "btnResetPass"></span></li>						
					</c:if>
				</ul>
			</form> 
			<div id="actionDialog"></div>
			<jsp:include page="/WEB-INF/jsp/user/settings-nav.jsp"/>
		</nav>
	
		<header class="setHead">
			<h2>Settings and Permission</h2>
			<ul>
				<c:url var="userAddUrl" value="/user/create" />
				<li class="selected"><a href="${userAddUrl}">Users</a>|</li>
			</ul>
		</header>
		
		<section class="settingBody clrfix">		
			<div class = "left">
				<h4 class="name">${selectedUser.firstName} ${selectedUser.lastName}</h4>
				<p>${selectedUser.email}</p>
				<c:url var="imageUser" value="/images/img_user.jpg"/>
				<img src="${imageUser}" alt="">
			</div>
			
			<div class="right">
				<h4>Information</h4>
				<form:form class="settingForm" action="/user/update" commandName="updateUser">
					<form:input path = "id" type = "hidden" value = "${selectedUser.id}"/>
					<br><label>Email</label>:
					<form:input path="email" type="text" required = "true"
					pattern = "[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})"
					oninvalid="setCustomValidity('Email is invalid.')"
				    onchange="try{setCustomValidity('')}catch(e){}"/>			
					<span class="errormsg quota">
					<form:errors class = "error" path="email" />
					</span><br>
					<label>Occupation</label>:
		<form:input path="occupation" type = "text" required = "true" pattern = "[a-zA-Z0-9]+" 
		oninvalid="setCustomValidity('Occupation must be alphanumeric only.')"
	    onchange="try{setCustomValidity('')}catch(e){}"/>			
					<span class="errormsg quota">
		<form:errors class = "error" path="occupation"/>
					</span><br>
		<label>Hospital:</label>
		<form:input path="hospital" type = "text" required = "true" pattern = "[a-zA-Z0-9]+" 
		oninvalid="setCustomValidity('Hospital must be alphanumeric only.')"
	    onchange="try{setCustomValidity('')}catch(e){}"/>		
		<form:errors class = "error" path="hospital"/>			<br>
					
					<c:if test="${loggedInUser.recordsOfficer}">
						<label>Role</label>:
						<form:select path="role" style="margin-left: 13px;">
					    <form:options items="${roles}" itemLabel="name" />
			 			</form:select>
					</c:if>
										
					<div class="botBtns">
					<ul>
						<li><span class="btn"><input type="submit" value="Save" class="btnSave" id = "btnSave"></span></li>
						<li><span class="btn"><input type="button" value="Cancel" name = "btnCancelUpdate" class="btnCancel"></span></li>
					</ul>
					</div>	
				</form:form>
					
			</div>
		</section>
		
	<div class="crmFormBot clrfix">
	</div>		
	</div>
</div>
<footer>	
	<span class="copy"><a href="">&copy; 2013 Orange &amp; Bronze Software Labs, Inc.</a></span>
</footer>

	<input type="hidden" value = "${selectedUser.id}" name="userId"/>
	<input type="hidden" value = "${loggedInUser.id}" name="loggedInId"/>
	<input type="hidden" value = "${loggedInUser.role.name}" name="role"/>

	<c:url var="jqueryUrl" value="/js/jquery-1.9.1.min.js" />
	<script src="${jqueryUrl}"></script>
	<c:url var="scriptsUrl" value="/js/scripts.js" />
	<script src="${scriptsUrl}"></script>
	<c:url var="jqueryUiJsUrl" value="/js/jquery-ui.js" />
	<script src="${jqueryUiJsUrl}"></script>
	<c:url var="updateUserUrl" value="/js/user-update.js" />
	<script src="${updateUserUrl}"></script>
</body>
</html>