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
	function getReadableFileSizeString(fileSizeInBytes) {

	    var i = -1;
	    var byteUnits = [' kB', ' MB', ' GB', ' TB', 'PB', 'EB', 'ZB', 'YB'];
	    do {
	        fileSizeInBytes = fileSizeInBytes / 1024;
	        i++;
	    } while (fileSizeInBytes > 1024);

	    return Math.max(fileSizeInBytes, 0.1).toFixed(1) + byteUnits[i];
	};
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
	<c:if test="${folderList.size() == 0}">
		<span> No files in bucket</span>
	</c:if>
	<c:if test="${folderList.size() ne 0}">
		<h4>Total number: ${folderList.size()}</h4>
		<form method = "post" action="fileController">
		<input type="hidden" name="bucketName" value="${folderList.get(0).getBucketName() }"/>
		<table >
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td > <input type="checkbox" id="selectall" onClick="selectAll(this)" /></td>
				<th> Name </th>
				<th> Owner </th>
				<th> Size </th>
				<th> Last Modified </th>
			</tr>
		<c:forEach items="${folderList}" var = "file">	
		<c:if test="${not file.getKey().endsWith(\"/\")}">
				<c:if test="${not file.getKey().contains(\".cpaes\")}" >
			<tr>
				<td>
					<input type="checkbox" name ="taggedFile" value="${file.getKey()}"/>
				</td>
				<td style="text-align:center">

					<c:choose>
					<c:when test="${file.getKey().endsWith(\".cpabe\") and file.getKey().contains(\"/\")}">
						${file.getKey().substring(0,file.getKey().lastIndexOf(".")).substring(file.getKey().lastIndexOf("/")+1)}						
					</c:when>
					<c:when test="${file.getKey().contains(\"/\")}">						
						${file.getKey().substring(file.getKey().lastIndexOf("/")+1)}
					</c:when>
					<c:otherwise>
					${file.getKey().replaceAll(".cpabe","")}
					</c:otherwise>
					</c:choose>
				</td>
				<td>${file.getOwner().getDisplayName()}</td>
				<td>${file.getSize() }	</td>
				<td>${file.getLastModified()}	</td>		
			</tr>
				</c:if>
		</c:if>
		</c:forEach>

		<tr></tr>
				<tr>
				<td></td>
				<td></td>
				<td style="text-align:center">
				<input type = 'submit' name = 'submit' value="DELETE"/></td>
				<td style="text-align:center">
				<input type = 'submit' name = 'submit' value="DOWNLOAD"/></td>
				<td></td>
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