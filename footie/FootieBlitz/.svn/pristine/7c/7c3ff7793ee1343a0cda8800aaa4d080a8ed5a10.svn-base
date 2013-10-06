$(document).ready(function(){ 
	
	loadTable();
	
	$("#slSeason").change(function() { 
		loadTable();
	});
	
	$("#slTable").change(function() { 
		
		if ($("#slTable").val() == 2){
			$('#divSeson').hide();	
		}
		else{
			$('#divSeson').show();	
		}
		
		loadTable();
	});
	
	function loadTable(){
		$("#divtable").load("/leagues/loadleagueclubstats/" + $('#leagueid').val() + '/' + $("#slTable").val() + '/' + $("#slSeason").val(), function(response, status, xhr) {
			  if (status == "error") {
			    var msg = "Error: ";
			    $("#divtable").html(msg + xhr.status + " " + xhr.statusText);
			  }	  			
			  
			  $("#tblStats").tablesorter(); 
		});

	}
});