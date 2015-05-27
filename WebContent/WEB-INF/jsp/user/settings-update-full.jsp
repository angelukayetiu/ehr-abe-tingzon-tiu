<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<!doctype html>
<html lang="en">
<jsp:include page="/WEB-INF/jsp/home/home-header.jsp" >
    <jsp:param name="title" value="Settings" />
    <jsp:param name="selected" value="settings" />
</jsp:include>

<c:url var="PROJECT_ROOT" value="/"/>

<c:url var="cssSettings" value="/css/settings.css"/>
<link rel="stylesheet" href="${cssSettings}">
<c:url var="templateTableUrl" value="/css/template-table.css" />
<link rel="stylesheet" href="${templateTableUrl}">

<div class="pageCtrl"> <!--no buttons here--></div>

<div class="content-wrap">
	
	<div class="wrapper">
		<nav class="searchNav">
			<c:if test="${loggedInUser.recordsOfficer}">
				<form class="searchbar" action="${PROJECT_ROOT}user/search/${action eq 'displayActiveUsers' ? 'active' : 'inactive'}" method="GET">
					<ul>
						<li><input type="text" name="searchKey" value="" placeholder="Search" class="search"/></li>
						<li><span class="btn"><input type="button" value="Users List" class="btnReturn"></span></li>
						<li><span class="btn"><input type="button" value="Edit" class="btnEdit"></span></li>
							<c:if test="${selectedUser.enabled}">	
								<li><span class="btn"><input type="button" value="Deactivate" class="btnClose cancelnewuser"></span></li>
							</c:if>
							<c:if test="${!selectedUser.enabled}">		
								<li><span class="btn"><input type="button" value="Activate" class="btnNew bntNewUser" name = "btnActivate"></span></li>
							</c:if>
							<li><span class="btn"><input type="button" value="Reset Password" name = "btnResetPass"></span></li>						
					</ul>
					<input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/>
				</form> 
			</c:if>	
			<div id="actionDialog"></div>
			<jsp:include page="/WEB-INF/jsp/user/settings-nav.jsp"/>
		</nav>
		
		<header class="setHead">
			<h2>Settings</h2>
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
				<section>
				<h4>Information</h4>
				<form class="settingForm">
					<label>Email</label>:<span>${selectedUser.phone}</span><br>
					<label>Occupation</label>:<span>${selectedUser.mobile}</span><br>
					<label>Hospital</label>:<span>${selectedUser.website}</span><br>
					<label>Role</label>:${selectedUser.role.name}<span></span><br>
				</form>
				</section>
			</div>
		</section>
		
	<div class="crmFormBot clrfix">

	</div>		
	
	</div>
	<!-- end wrapper-->
</div>
<!-- end content-wrap-->
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