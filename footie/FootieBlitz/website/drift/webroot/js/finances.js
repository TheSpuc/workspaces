
$(document).ready(function(){
	
	
	$("#divdetails").load("/clubs/financedetails/" + $('#clubID').val() + '/season/', function(response, status, xhr) {
		  if (status == "error") {
		    var msg = "Error: ";
		    $("#divdetailsmsg").html(msg + xhr.status + " " + xhr.statusText);
		  }
	});
	
	$("#divWeekDetails").load("/clubs/financedetails/" + $('#clubID').val() + "/week", function(response, status, xhr) {
		  if (status == "error") {
		    var msg = "Error: ";
		    $("#divWeekmsg").html(msg + xhr.status + " " + xhr.statusText);
		  }
	});
	
	function onClick(){

	    $('#divdetails').load($(this).attr('href'), onSuccess);
	} 

	function onClick2(){

	    $('#divWeekDetails').load($(this).attr('href'), onSuccess);
	} 
	
	function onSuccess(){
	   // alert('tjek');
	}

	$('.ajax-link').live('click', onClick);
	$('.ajax-link2').live('click', onClick2);

});
function fillChartData(){
	var head = ['Week', 'Income', 'Expenses', 'Balance'];
	var weeklyExpenses = [head];
	var weeks = document.getElementsByClassName('weeks');
	var expenses = document.getElementsByClassName('expenses');
	var incomes = document.getElementsByClassName('incomes');
	var balance = document.getElementsByClassName('balance');
	
	for(var i = 0; i < weeks.length; i++){
			var o = i+1;
			weeklyExpenses[o] = [weeks[i].value];
			weeklyExpenses[o][1] = parseInt(incomes[i].value);
			weeklyExpenses[o][2] = parseInt(expenses[i].value);
			weeklyExpenses[o][3] = parseInt(balance[i].value);
			//alert(weeklyExpenses[o][0] + ', ' + weeklyExpenses[o][1] + ', ' + weeklyExpenses[o][2] + ', ' + weeklyExpenses[o][3]);
	}
	
	return weeklyExpenses;
}