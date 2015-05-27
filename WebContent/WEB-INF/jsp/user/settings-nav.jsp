<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>
	<aside>
		<h2>Settings</h2>
		<ul>
			<c:url var="changePasswordUrl" value="/user/change-pass" />
			<c:url var="retrieveKeysUrl" value="/user/retrieve" />
			<c:url var="editProfileUrl" value="/user/edit" />
			<li><a href="${editProfileUrl}">Edit Profile</a></li>
			<li><a href="${retrieveKeysUrl}">Retrieve Keys</a></li>
			<li><a href="${changePasswordUrl}">Change Password</a>
		</ul>
	</aside>
</body>
</html>