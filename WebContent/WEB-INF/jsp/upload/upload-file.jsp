<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/jsp/home/home-header.jsp">
	<jsp:param name="title" value="Upload" />
	<jsp:param name="selected" value="Upload" />
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
	</nav>
		<section class="settingBody clrfix">		
			<div class = "left">
				<h4 class="name">${loggedInUser.firstName} ${loggedInUser.lastName}</h4>
				<p>${loggedInUser.email}</p>
				<c:url var="imageUser" value="/images/img_user.jpg"/>
				<img src="${imageUser}" alt="">
			</div>
			
			<div class="right">
				<div class="forPasswrd">	
					<h5>Upload file</h5>
					<font color = "green">${success}</font>	
					
						<form:form method="post" enctype="multipart/form-data">
	<input type="hidden" value = "${loggedInUser.username}" name="loggedInUsername"/>
						<span class="btn">
						<input  required="required"  type="file" name="assetFile" class="btnSave" id = "btnChangePass"/></span><br>
						<span class="error">
						<label>Policy: </label>
						</span><br>
						<input required="required" name = "policy" value="" placeholder="policy" type = "text" id = "btnChangePass" />
						<br>		
						<div class="botBtns">
							<ul>
								<li><span class="btn"><input type="submit" value="Upload" class="btnSave" id = "btnChangePass" ></span></li>
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