$( function() {

	$( "#loadReminders" ).submit(function(event){
		event.preventDefault();		
		username = $( "input#username_input" ).val();
		$("#reminderList").empty();
		var $remidersListTemplate =  $("#remidersListTemplate").html() ;
		var $noRemindersFound =  $("#noRemindersFound").html() ;

		$.ajax({
				type: 'GET',
			 	url: 'http://localhost:9000/api/users-db-service/rest/reminders/username/'+username,			  			  
			 	success: function(data){
			 		if(data.length > 0){
			 			$.each( data , function(index,value){ 			  			
			  			$("#reminderList").append(Mustache.render($remidersListTemplate,{ value : value }));
			  	 	});
			 		}
			 		else{
			 			$("#reminderList").append(Mustache.render($noRemindersFound,{ value : " ***Reminders basket is empty*** " }));
			 		}
			  	}
		});

	
	});

});

