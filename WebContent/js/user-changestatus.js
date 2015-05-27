$(document)
	.ready(								
		function() {
			var $dialog = $('#actionDialog');
			$dialog.dialog({
			autoOpen : false,
			modal : true,
			resizable : false
		});
								
		var PROJECT_ROOT = "/onbcrm"
		var MODULE_ROOT = PROJECT_ROOT+"/user/"
		$("input[name='btnActivate']").click(
			function() {
				activate($dialog, checkedUsers, MODULE_ROOT);
		});
				
		$("input[class='btnClose cancelnewuser']").click(
			function() {
				deactivate($dialog, checkedUsers, MODULE_ROOT);
		});
		
		$("input[name='btnResetPass']").click(
			function() {
				resetPassword($dialog, checkedUsers, MODULE_ROOT);
		});

		$("input[class='btnExit']").click(function() {
			window.location.replace(PROJECT_ROOT+"/logout");
		});
		
		$("input[class='btnCancel']").click(
			function() {
				showChooseItemDialog($dialog,"create",
				"Are you sure you want to cancel creating this user?");
		});
	});

function deactivate($dialog, form, MODULE_ROOT){
	var loggedInId = $("input[name='loggedInId']").val();
	var users = "";
	var ctr = 0;
	var selectedOwnAccount = false;
	var userListLength = form.listOfUsers.length;
	
	if( userListLength == undefined ) {	
		if(form.listOfUsers.checked){
			if (form.listOfUsers.value == loggedInId ){
				selectedOwnAccount = true;
			}else{
				users = users + form.listOfUsers.value + ",";
			}	
			ctr++;
		}
	}else{
		for (var i = 0; i < userListLength; i++){
			if (form.listOfUsers[i].checked){
				if(form.listOfUsers[i].value == loggedInId ){
					selectedOwnAccount = true;
				}else{
					users = users + form.listOfUsers[i].value + ",";
				}	
				ctr++;
			}
		}	
	}
	
	if(ctr == 0){
		showCancelDialog($dialog, "No user selected.");
	}else{
		if( selectedOwnAccount ){
			showCancelDialog($dialog, "Own account cannot be deactivated.");
		}else{
			showChooseItemDialog( $dialog, MODULE_ROOT + "disable?users=" + users,
			"Are you sure you want to deactivate selected user/s?");
		}
	}
}

function activate($dialog, form, MODULE_ROOT){
	var loggedInId = $("input[name='loggedInId']").val();
	var users = "";
	var ctr = 0;
	var userListLength = form.listOfUsers.length;
	
	if( userListLength == undefined ){
		if (form.listOfUsers.checked){
			users = users + form.listOfUsers.value + ",";
			ctr++;
		}
	}else{
		for (var i = 0; i < userListLength; i++){
			if (form.listOfUsers[i].checked){
				users = users + form.listOfUsers[i].value + ",";
				ctr++;
			}
		}
	}
	if(ctr == 0){
		showCancelDialog($dialog, "No user selected.");
	}else{
		showChooseItemDialog( $dialog, MODULE_ROOT+"enable?users=" + users,
		"Are you sure you want to activate selected user/s?");
	}
}

function resetPassword($dialog, form, MODULE_ROOT){
	var loggedInId = $("input[name='loggedInId']").val();
	var users = "";
	var ctr = 0;
	var selectedOwnAccount = false;
	var userListLength = form.listOfUsers.length;
	
	if( userListLength == undefined ) {	
		if(form.listOfUsers.checked){
			if (form.listOfUsers.value == loggedInId ){
				selectedOwnAccount = true;
			}else{
				users = users + form.listOfUsers.value + ",";
			}	
			ctr++;
		}
	}else{
		for (var i = 0; i < userListLength; i++){
			if (form.listOfUsers[i].checked){
				if(form.listOfUsers[i].value == loggedInId ){
					selectedOwnAccount = true;
				}else{
					users = users + form.listOfUsers[i].value + ",";
				}	
				ctr++;
			}
		}	
	}
	
	if(ctr == 0){
		showCancelDialog($dialog, "No user selected.");
	}else{
		if( selectedOwnAccount ){
			showCancelDialog($dialog, "Own password cannot be reset.");
		}else{
			showChooseItemDialog( $dialog, MODULE_ROOT + "reset-password?users=" + users,
				"Are you sure you want to reset password of selected user/s?");
		}
	}
}


function showChooseItemDialog($dialog, $url, $message) {
	$dialog.html($message).dialog({
		buttons : {
			"Confirm" : function() {
				window.location.replace($url);
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	}).dialog('open');
}

function showCancelDialog($dialog, $message){
	$dialog.html($message).dialog({
		buttons : {
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	}).dialog('open');
}