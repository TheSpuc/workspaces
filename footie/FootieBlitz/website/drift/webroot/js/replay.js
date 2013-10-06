
$(document).ready(function(){

	$( "#tabs" ).tabs();
	
	
	$("#divmstats").load("/matchstats/stats/" + $('#matchID').val(), function(response, status, xhr) {
		  if (status == "error") {
		    var msg = "Error: ";
		    $("#divmstats").html(msg + xhr.status + " " + xhr.statusText);
		  }
	});

	$("#divpstats").load("/matchplayerstats/stats/" + $('#matchID').val(), function(response, status, xhr) {
		  if (status == "error") {
		    var msg = "Error: ";
		    $("#divmstats").html(msg + xhr.status + " " + xhr.statusText);
		  }
	});
});