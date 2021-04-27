
var sessionUserDetails = {};
var $remidersListTemplate;
var $noRemindersFound;

$( function() {


	showLoginElements();

	/*Reminders List Templates*/
	$("#reminderList").empty();
	$remidersListTemplate =  $("#remidersListTemplate").html() ;
	$noRemindersFound =  $("#noRemindersFound").html() ;



	/** simple authentication method */
	$( "#button-signIn" ).on("click", function(event){
		event.preventDefault();
		username = $( "input#input-username" ).val();
		hashPassword = hashing( $( "input#input-password" ).val() );

		$.ajax({
				type: 'GET',
				crossOrigin: true,
				url: 'http://127.0.0.1:9000/api/users-db-services/users/id/'+username,
				success: function(userId){

					$.ajax({
							type: 'GET',
							crossOrigin: true,
							url: 'http://127.0.0.1:9000/api/users-db-services/users/'+userId,
							success: function(userDetails){
								if ( userDetails["hashpassword"] == hashPassword ){
										setSessionAttrs(userDetails["id"],userDetails["username"]);
										showSessionElements();
										//TODO  Notification : console.log("Login <"+username+"> was successful.");

								}
								else{
									$("#span-invalidUser").show();
								}

							}
						});
				}
		});
	});



	/*  former method ,  -disabled  , ...microsservice  was moved to API GW   */
	$( "#button-signIn-disabled" ).on("click", function(event){
		event.preventDefault();
		username = $( "input#input-username" ).val();
		password = hashing( $( "input#input-password" ).val() );

		$.ajax({
				type: 'GET', /* demo - not proper method in production*/
				url: 'http://127.0.0.1:9021/auth/'+username+'/'+password,
				success: function(access){
					console.log(">"+access+"<");
					if( access ){
						showSessionElements();
					}
					else {
						$("#span-invalidUser").show();
					}
				}
		});
	});

	/** Shows the elements to register a new user*/
	$("#span-signUp").on("click", function(){
		showSignUpElements();
	})


	/** Register a new user */
	$("#button-signUp").on("click",function(event){
			event.preventDefault();

			username = $( "input#input-username" ).val();
			email =  $( "input#input-email" ).val();
			hashPassword = hashing( $( "input#input-password" ).val() );

			newUser = {};
			newUser["id"]=0;
			newUser["username"]=username;
			newUser["hashpassword"]=hashPassword;
			newUser["isActive"]=true;
			// newUser["email"]=email;
			console.log(newUser);
			console.log(JSON.stringify(newUser));

			$.ajax({
					type: 'POST',
					url: 'http://127.0.0.1:9000/api/users-db-services/users',
					data:JSON.stringify(newUser),
					contentType: 'application/json; charset=utf-8',
					dataType: 'json',
					success: function(data){
						//TODO Notification
					},
					statusCode: {
							201: function(){
								setSessionAttrs(0,newUser["username"]);
								showSessionElements();
							},
							403: function() {
       				  	alert("Username already exist");
    					}
					},
					failure: function(errMsg){
						alert(errMsg);
					}
			});
	});





	/** Add new reminder*/
	$("#button-add-new-reminder-modal").on("click",function(){
		reminderText = $( "input#input-reminder-text" ).val();
		reminderDate = $( "input#input-reminder-date" ).val();

		newReminder = {};
		newReminder["id"] = 0;
		newReminder["name"] = reminderText;
		newReminder["date"] = reminderDate;
		newReminder["isComplete"] = false;
		newReminder["userid"] = sessionUserDetails["userId"] ;

		// console.log(newReminder);
		// console.log(JSON.stringify(newReminder));

		$.ajax({
				type: 'POST',
				url: 'http://127.0.0.1:9000/api/reminders-db-services/reminders',
				data:JSON.stringify(newReminder),
				contentType: 'application/json; charset=utf-8',
				dataType: 'json',
				success: function(data){
					//TODO new reminder added notification .
				},
				statusCode: {
						201: function(){
							alert("Reminder sucessfully added.");
							$("#newReminderModal").modal("toggle");
							getReminders();
						}
				},
				failure: function(errMsg){
					alert(errMsg);
				}
		});
	});



});





/** show elements for a new users's session*/
function showSessionElements(){
	$("#form-authentication").hide();
	$("#span-newUser").hide();
	$("#span-session-username").text(sessionUserDetails["userName"]);
	$("#span-signUp").empty();
	$("#span-add-reminder-symbol").show();

	/*load reminders*/
	getReminders();
}



/** Get/Build reminders list */
function getReminders(){
	console.log("Getting reminders of " + sessionUserDetails["userName"]);

	$.ajax({
			type: 'GET',
			// crossOrigin: true,
			// headers: {"Access-Control-Allow-Origin":"*"},
			url: 'http://127.0.0.1:9000/api/reminders-fetch-services/rest/reminders/'+sessionUserDetails["userName"],
			success: function(data){
				console.log(data);
				if(data.length > 0){
					$("#reminderList").empty();
					$("#div-alert-noRemindersFoun").hide();
					$.each( data , function(index,value){
						$("#reminderList").append(Mustache.render($remidersListTemplate,{ value : value }));
					});
				}
				else{
					$("#reminderList").append(Mustache.render($noRemindersFound,{ value : " ***Reminders basket is empty*** " }));
				}
			},
			failure: function(errMsg){
					console.log(sessionUserDetails["userName"]);
					alert(errMsg);
			}
	});

}

function setSessionAttrs(id,user){
	if (id == 0){
			$.ajax({
					type: 'GET',
					url: 'http://127.0.0.1:9000/api/users-db-services/users/id/'+username,
					success: function(userId){
							sessionUserDetails["userId"] = userId;
					}
			});
	}
	else{
		sessionUserDetails["userId"] = id;
	}
	sessionUserDetails["userName"] = user;
}


function showLoginElements(){
	$(".welcome-msg-new-users").hide();
	$("#span-invalidUser").hide();
	$("#button-signUp").hide();
	$("#input-email").removeAttr('required');
	$("#input-email").hide();
	$("#span-session-username").empty();
	$("#span-add-reminder-symbol").hide();
}

function showSignUpElements(){
	$(".welcome-msg-new-users").show();
	$("#span-invalidUser").hide();
	$("#button-signUp").show();
	$("#button-signIn").hide();
	$("#input-email").attr('required');
	$("#input-email").show();
}


/** hash user's password , this function might have collisions*/
function hashing(str) {
	hash=0;
	if( str.length == 0) {
		return hash;
	}

  var buffer = new TextEncoder("utf-8").encode(str);
	buffer.forEach((value, index) => {
		hash+=( (value^index) * (value+index) )^index ;
	});
	return (hash<<10).toString(16);
}
