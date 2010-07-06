/*****************************************************
We will use JQuery to drive the Styling and AJAX side of the site 
Keeping the Server Side very data and function focused 
whilst the client interactions are setup by the Client Side bus
and client functions and Styling

******************************************************/
$(function(){
	// Action Functions within the UI
	var showHistoryFn = function ()
	{	
		if($('#history').val()=='Hide History'){
			$('#data').hide();
			$('#history').val('Show History');
		}else
		{
			$("tr:odd").addClass("odd");
			$('#data').fadeIn('slow');	
			$('#history').val('Hide History');
		}
	}
	var getTableDataFn = function(){
		$.ajax({
				type: 'GET',
				url: '/listChanges',
				dataType: 'html',
				cache: false,
				success: function(html, textStatus) {								
					$('#data').html(html);
				},
				error: function(xhr, textStatus, errorThrown) {
					alert('An error occurred! ' + ( errorThrown ? errorThrown : xhr. status ));
				}			
			});
	}
	// Creating the Message Bus Architecture
	// Message Binding to Functions
	$(document).bind('SHOW_HISTORY',showHistoryFn);
	$(document).bind('GET_DATA',getTableDataFn);

	$( ".date-pick" ).datepicker({ dateFormat: 'dd-MM-yy' });
	$( ".date-pick" ).val('');
	$( "#change" ).focus();
	$('#new-change form').validate({
	 rules: {
		change: {
			required: true
		},
		operator: {
			required: true
		}
	 },
	 messages: {
		date: "Please input the date the change was made",
		change: "What change are you making",
		note: "Record a note if you wish"
		}
	});
	
	$('.ui-state-default').hover(
		function(){ 
			$(this).addClass("ui-state-hover"); 
		},
		function(){ 
			$(this).removeClass("ui-state-hover"); 
	});
	
	$('#data').hide();	

	$('#history').click(function(){
	     $(document).trigger('GET_DATA');
	     $(document).trigger('SHOW_HISTORY');
	});

	// Formatting



});


