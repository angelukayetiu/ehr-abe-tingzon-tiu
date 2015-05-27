<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>

<html lang="en">
<head>
<title>EHR CSG  - ${param.title}</title>
<meta charset="utf-8">

<c:url var="PROJECT_ROOT" value="/"/>
<link rel="stylesheet" href="${PROJECT_ROOT}/css/reset.css">
<link rel="stylesheet" href="${PROJECT_ROOT}/css/template.css">
<link rel="stylesheet" href="${PROJECT_ROOT}/css/template-table.css">
<link rel="stylesheet" href="${PROJECT_ROOT}/css/forms.css">
<link rel="stylesheet" href="${PROJECT_ROOT}/css/form-popup.css">
<link rel="stylesheet" href="${PROJECT_ROOT}/css/jquery-ui.css">
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css'>

<script src="${PROJECT_ROOT}/js/jquery-1.9.1.min.js"></script>
<script src="${PROJECT_ROOT}/js/scripts.js"></script>
<script src="${PROJECT_ROOT}/js/jquery-ui.js"></script>
</head>

<body>

	<div id="wrap">
		<header>
			<h1>
				Health Repository
				<a href="#">UP EHR</a>

			</h1>
			<div class="toplinks">
				<ul>
					<c:url var="settingsUrl" value="/user/change-pass"></c:url>
					<li class="${param.selected == 'settings' ? 'selected' : ''}"><a
						href="${settingsUrl}">Settings</a></li>
					<li><a href="">My Account</a></li>
				</ul>
				<c:url var="iconUser" value="/images/icon_user.jpg" />
				<span class="userInfo"> <a href=""><img src="${iconUser}"
						alt=""></a>
				</span>
				<div class="userDetails">
					<c:url var="imageUser" value="/images/img_user.jpg" />
					<figure>
						<img src="${imageUser}" alt="">
					</figure>
					<h4>${loggedInUser.firstName} ${loggedInUser.lastName}</h4>
					<p>${loggedInUser.email}</p>
					<h5>My Account</h5>
					<p>${loggedInUser.email}</p>
					<c:url var="logout" value="/logout"></c:url>
					<form action="${logout}" method="get">
						<p class="button">
							<span class="btn"><input type="submit" value="Sign Out"
								class="btnExit"></span>
						</p>
					</form>
				</div>
			</div>
		  </header>
		</div>

		<nav>
			<ul class="menu">
				<c:url var="homeUrl" value="/home/${loggedInUser.username}" />
				<c:url var="uploadUrl" value="/upload/${loggedInUser.username}" />				
				
				<li class="${param.selected == 'home' ? 'selected' : ''}"><a
					href="${homeUrl}">Home</a></li>
				<li class="${param.selected == 'upload' ? 'selected' : ''}"><a
					href="${uploadUrl}">Upload</a></li>

			</ul>

			<div class="rOptions">
				<form action="${loggedInUser.username}/search" class="searchbar">
					<input type="text" name="searchName" value="" placeholder="Search">
				</form>
			</div>
		</nav>
</body>

<!-- START: NEW QUOTA -->

 <!--  END: NEW QUOTA -->