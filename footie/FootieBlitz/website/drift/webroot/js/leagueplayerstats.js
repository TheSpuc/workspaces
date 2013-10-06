$(document).ready(function(){ 
	
	loadTable();
	
	$("#slSeason").change(function() { 
		loadTable();
	});
	
	$("#slTable").change(function() { 
		loadTable();
	});
	
	function loadTable(){
		$("#divtable").html("Loading...");
		$("#divtable").load("/leagues/loadleagueplayerstats/" + $('#leagueid').val() + '/' + $("#slTable").val() + '/' + $("#slSeason").val(), function(response, status, xhr) {
			  if (status == "error") {
			    var msg = "Error: ";
			    $("#divtable").html(msg + xhr.status + " " + xhr.statusText);
			  }	  			
			  
			  $("#tblStats").tablesorter(); 
		});

	}
});