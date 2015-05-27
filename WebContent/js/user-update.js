$(document)
	.ready(
		function() {
			var $dialog = $('#actionDialog');
			$dialog.dialog({
			autoOpen : false,
			modal : true,
			resizable : false
		});

		var id = $("input[name='userId']").val();
		var loggedInId = $("input[name='loggedInId']").val();
		var role = $("input[name='role']").val();

		$("input[class='btnClose cancelnewuser']").click(function() {
			if( id != loggedInId ){
				showChooseItemDialog( $dialog, "disable?userId=" + id,
				"Are you sure you want to deactivate selected user?");
			}else{
				showCancelDialog($dialog, "Own account cannot be deactivated.");
			}
		});
			
		$("input[class='btnReturn']").click(function(){
			window.location.replace("create");
		});
		
		$("input[name='btnActivate']").click(function() {
			showChooseItemDialog( $dialog,"enable?userId=" + id,
			"Are you sure you want to activate selected user?");
		});
		
		$("input[name='btnResetPass']").click(function() {
			if( id != loggedInId ){
				showChooseItemDialog( $dialog, "reset-password?userId=" + id,
				"Are you sure you want to reset password of selected user?");
			}else{
				showCancelDialog($dialog, "Own password cannot be reset.");
			}
		});

		$("input[name='btnCancelChangePass']").click(
			function() {
				showChooseItemDialog($dialog, "create",
				"Are you sure you want to cancel changing your password?");
		});

		$("input[name='btnCancelUpdate']").click(
			function() {
				showChooseItemDialog($dialog,"update-display?userId=" + id,
				"Are you sure you want to cancel updating this user?");
		});
		$("input[class='btnEdit']").click(function(){
			window.location.replace("update?userId=" + id);
		});
		
		$("input[class='btnExit']").click(function() {
			window.location.replace("logout");
		});
		
});

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

