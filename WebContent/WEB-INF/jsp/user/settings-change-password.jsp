<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/jsp/home/home-header.jsp">
	<jsp:param name="title" value="Settings" />
	<jsp:param name="selected" value="settings" />
</jsp:include>

<c:url var="cssSettings" value="/css/settings.css"/>
<link rel="stylesheet" href="${cssSettings}">

<div class="content-wrap">
	
	<div class="wrapper">
	
	<nav class="searchNav">
<%-- 		<form class="searchbar"> --%>
<!-- 			<ul> -->
<!-- 				<li class="button"><input type="text" value="" placeholder="Search" class="search"/></li> -->
<%-- 				<c:url var="addUser" value="/user/create"/> --%>
<!-- 				<li><span class="btn"><input type="button" value="Users List" class="btnReturn""></span></li>	 -->
<!-- 			</ul> -->
<%-- 		</form>  --%>
		<div id="actionDialog"></div>
		
		<!-- end searchNav -->
		<jsp:include page="/WEB-INF/jsp/user/settings-nav.jsp"/>
	</nav>
		
		<header class="setHead">
			<h2>Account Settings</h2>
		</header>
		
		<section class="settingBody clrfix">		
			<div class = "left">
				<h4 class="name">${loggedInUser.firstName} ${loggedInUser.lastName}</h4>
				<p>${loggedInUser.email}</p>
				<c:url var="imageUser" value="/images/img_user.jpg"/>
				<img src="${imageUser}" alt="">
			</div>
			
			<div class="right">
				<div class="forPasswrd">	
					<h5>Change Password</h5>
					<font color = "green">${success}</font>								
					<form:form class="settingForm" action="change-pass" commandName="editPassword" method="POST">
						<form:input type="hidden" path="id" value="${loggedInUser.id}"/>
						<label>Current Password</label>:
						<form:input type="password" path="currentPassword" id="password"  
						required = "true" 
		oninvalid="setCustomValidity('Password is required')"
	    onchange="try{setCustomValidity('')}catch(e){}"/>			

						<span class = "errormsg quota"><form:errors path="currentPassword"/></span><br>
						<label>New Password</label>:
						<form:input type="password" path="newPassword" id="newPass" pattern = ".{6,}"
						required = "true" 
		oninvalid="setCustomValidity('Password must be at least 6 letters or numbers.')"
	    onchange="try{setCustomValidity('')}catch(e){}"/>									
						<span class = "errormsg quota"><form:errors path="newPassword"/></span><br>
						<label>Repeat New Password</label>:
						<form:input type="password" path="passwordConfirmation" id="repeatNewPass" pattern = ".{6,}"					required = "true" 
						
		oninvalid="setCustomValidity('Password must be at least 6 letters or numbers.')"
	    onchange="try{setCustomValidity('')}catch(e){}"/>									
							
						<span class = "errormsg quota"><form:errors path="passwordConfirmation"/></span><br>
						<div class="botBtns">
							<ul>
								<li><span class="btn"><input type="submit" value="Save" class="btnSave" id = "btnChangePass" ></span></li>
								<li><span class="btn"><input type="button" value="Cancel" name = "btnCancelChangePass" class="btnCancel"></span></li>
							</ul>
						</div>	
					</form:form>	
				</div>
			</div>
		</section>
		
	<div class="crmFormBot clrfix">
	</div>		
	</div>
</div>
	<footer>
		<span class="copy"><a href="">&copy; 2015 Tingzon Tiu</a></span>
	</footer>

	<input type="hidden" value = "${selectedUser.id}" name="userId"/>
	<input type="hidden" value = "${loggedInUser.id}" name="loggedInId"/>
	<input type="hidden" value = "${loggedInUser.roleDTO.name}" name="role"/>

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