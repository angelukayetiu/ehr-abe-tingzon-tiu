<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    

<!doctype html>
<html lang="en">

<jsp:include page="/WEB-INF/jsp/home/home-header.jsp" >
    <jsp:param name="title" value="Settings" />
    <jsp:param name="selected" value="settings" />
</jsp:include>


<c:url var="PROJECT_ROOT" value="/"/>
<link rel="stylesheet" href="${PROJECT_ROOT}/css/settings.css">

<div class="pageCtrl"> <!--no buttons here--></div>

<div class="content-wrap">
	
	<div class="wrapper">
	
		<nav class="searchNav">
		<c:if test="${loggedInUser.recordsOfficer}">
			<form class="searchbar" action="${PROJECT_ROOT}user/search/${action eq 'displayActiveUsers'? 'active' : 'inactive'}" method="GET">
				<ul>
					<li class="button"><input name="searchKey" type="text" value="" placeholder="Search" class="search"/></li>
					<li><span class="btn"><input type="button" value="New User" class="btnNew bntNewUser" name = "btnNewUser"></span></li>
					<c:if test="${action eq 'displayActiveUsers'}">	
						<li><span class="btn"><input type="button" value="Deactivate" class="btnClose cancelnewuser"></span></li>
					</c:if>
					<c:if test="${action eq 'displayInactiveUsers'}">	
						<li><span class="btn"><input type="button" value="Activate" name = "btnActivate"></span></li>
					</c:if>
					<li><span class="btn"><input type="button" value="Reset Password" name = "btnResetPass"></span></li>
				</ul>
				<input type="submit" style="position: absolute; left: -9999px; width: 1px; height: 1px;"/>
			</form> 
		</c:if>	
		<div id="actionDialog"></div>
		
		<!-- end searchNav -->
		
		<jsp:include page="/WEB-INF/jsp/user/settings-nav.jsp"/>
		
		</nav>
	
		<header class="setHead">
			<h2>Settings</h2>
			<ul>
				<c:url var="userAddUrl" value="/user/create" />
				<li class="selected"><a href="${userAddUrl}">Users</a>|</li>
				<li><a href="">Roles</a></li>
			</ul>
			
			<div class="userStat">
<%-- 			<c:url var="inactiveUsersUrl" value="/user/view-inactive" /> --%>
<%-- 			<c:url var="activeUsersUrl" value="/user/create" /> --%>
			<c:url var="inactiveUsersUrl" value="/user/search/inactive?searchKey=${searchKey}" />
			<c:url var="activeUsersUrl" value="/user/search/active?searchKey=${searchKey}" />
			
			<spring:hasBindErrors htmlEscape="true" name="createUser">
			    <c:if test="${errors.errorCount gt 0}">
			    <h4>Unsuccessfully created user:</h4>
		    	<c:forEach var="fieldError" items="${errors.fieldErrors}">
		    		<span class = "error"><spring:message code="${fieldError.code}.${fieldError.objectName}.${fieldError.field}"/></span><br />
		    	</c:forEach>
			  </c:if>   
			</spring:hasBindErrors>
				
			<c:if test="${loggedInUser.recordsOfficer}">
				<select onChange="document.location = this.value">
					<c:if test="${action eq 'displayActiveUsers'}">
						<option value="">Active Users (${fn:length(activeUsers)})</option>
						<option value="${inactiveUsersUrl}">Inactive Users (${fn:length(inactiveUsers)})</option>
					</c:if>
					<c:if test="${action eq 'displayInactiveUsers'}">
						<option value="">Inactive Users (${fn:length(inactiveUsers)})</option>
			 			<option value="${activeUsersUrl}">Active Users (${fn:length(activeUsers)})</option>
					</c:if>				  
				</select>
			</c:if>
			
			</div>
		</header>

		<div class="tableWrapper">
			<form name = "checkedUsers">
			<table class="tableContent">
				<c:forEach items="${action == 'displayActiveUsers'? activeUsers : inactiveUsers}" var="user">
				<tr class="frow">
					<td class="firstcell">
						<div class="bluSideBtn"><span><input type="checkbox" name = "listOfUsers" id="userId" value="${user.id}"></span></div>
					</td>
					<c:url var="imageUser" value="/images/img_user.jpg"/>
					<td class="secondcell"><img src="${imageUser}" alt=""></td>
					<td class="thirdcell">				
						<c:url var="updateUser" value="/user/update-display?userId=${user.id}" />
							<p class="name">			
							<a href= "${updateUser}">${user.firstName} ${user.lastName}</a></p><br>
							<p class="info">${user.email}</p>
							<p class="info">${user.role.name}</p>	
					</td>
				</tr>
				</c:forEach>
			</table>
			</form>
		</div>
		<!-- end tableWrapper-->			
		
	<!--form: add new user-->
	<!-- 
	<div class="addPopUp newuser">
	<form:form method = "POST" action = "/user/create" commandName="createUser">
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
	-->
	<div class="crmFormBot clrfix">
	</div>		
	
	</div>
</div>

	<input type="hidden" value = "${loggedInUser.id}" name="loggedInId"/>
	
<footer>	
	<span class="copy"><a href="">&copy; 2013 Orange &amp; Bronze Software Labs, Inc.</a></span>
</footer>
	<c:url var="changeStatusUrl" value="/js/user-changestatus.js" />
	<script src="${changeStatusUrl}"></script>
</body>
</html>