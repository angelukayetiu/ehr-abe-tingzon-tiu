<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/jsp/home/home-header.jsp">
	<jsp:param name="title" value="Home" />
	<jsp:param name="selected" value="Home" />
</jsp:include>
<script language="JavaScript">
	function selectAll(source) {
		checkboxes = document.getElementsByName('taggedFile');
        for(var i in checkboxes)
        	checkboxes[i].checked = source.checked;
	}
</script>

<c:url var="cssHome" value="/css/homepage.css"/>
<link rel="stylesheet" href="${cssHome}">
<c:url var="cssHome2" value="/css/form-popup.css"/>
<link rel="stylesheet" href="${cssHome2}">

<div class="content-wrap">
	
	<div class="wrapper home">
	
	<section class="accDetails clrfix">
	<div class="profile">
		<h3>Howdy, ${loggedInUser.firstName}!</h3>
		<ul>
			<li class="name">${loggedInUser.firstName} ${loggedInUser.lastName}</li>
			<li>${loggedInUser.email}</li>
			<li>${loggedInUser.occupation}</li>
		</ul>
	</div>
	</section>
	<section>
	
	<font color="red">${repositoryMessage} </font> <br/>
	<c:if test="${folderList.size() ne 0}">
		<h4>Total number: ${folderList.size()}</h4>
		<form action="<c:url value="${loggedInUser.username}/viewBucket"/>" method="POST">  
		<table >
			<tr>
				<td></td>
			</tr>
			<tr>
				<th> Hospitals </th>
			</tr>
		<c:forEach items="${folderList}" var = "file">	
			<tr>
				<td style="text-align:center">
					<a href="${loggedInUser.username}/${file.getName()}" >${file.getName()}</a>
				</td>
			</tr>
		</c:forEach>
		<tr></tr>
				<tr>
				<td style="text-align:center">
			</tr>
		</table>
		</form>
	</c:if>	
	
	</section>
	
	
	
	<div class="crmFormBot clrfix"></div>		
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