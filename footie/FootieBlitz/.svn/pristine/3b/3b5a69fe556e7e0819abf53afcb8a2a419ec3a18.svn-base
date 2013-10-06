<?php

echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->script('finances');

//debug($this->data);
//debug($wages);
//debug($balancegraph); 
?>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
function drawChart() {
	var weeklyExpenses = fillChartData();
	//alert(weeklyExpenses[0][0] + " " + weeklyExpenses[1][0]);
	var data = google.visualization.arrayToDataTable(weeklyExpenses);
	// [
	  // ['Week', 'Income', 'Expenses', 'Balance'],
	  // ['22',  660, 1120, 100],
	  // ['23',  1030, 540, 500]
	// ]

	var options = {
	  title: 'Club Finances over the last 15 weeks',
	  hAxis: {title: 'Weeks',  titleTextStyle: {color: '#FF0000'}},
	};

	var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
	chart.draw(data, options);
}
</script> 
 <?PHP
 
echo '<input type="hidden" id="clubID" value=' . $this->data['Club']['id'] . '>';


?>
<div id="divIncExp" style="float:left; width:45%;">
	<h3>Financial overview</h3>
	
	<div id="divdetails">
	</div>
	
	<div id="divdetailsmsg">
	</div>

</div>

<div id="divWeekly" style="float: right; width:45%;">
	<h3>Weekly details</h3>
	
	<div id="divWeekDetails">
	</div>
	
	<div id="divWeekmsg">
	</div>

</div>

<div style="clear: both">
</div>



<div id="divGeneralInfo" style="float: left; width:25%;">
<?PHP
echo '<table>'; 
echo '<tr><td>Current balance</td><td>' . "£ " . number_format($this->data['Club']['money'], 0, ',', '.') . '</td></tr>';
echo '<tr><td>Wages</td><td>' . "£ " . number_format($wages[0][0]['dailywages'], 0, ',', '.') . '</td></tr>'; 
echo '</table>';
?>
</div>
<div id="chart_div" style="width: 900px; height: 500px;"></div>
<?PHP
////////////
	//Income/Expenses/Balance-Graph
///////////
	$i = 0;
	foreach ($expenses as $expense)
	{
		$amountExpenses = 0;
		$week = 0;
		$amountIncome = 0;
		$amountBalance = 0;
		
		if(!empty($expense[0]['sum'])){ 
			$amountExpenses = $expense[0]['sum'];
		} 
		if(!empty($expense[0]['week'])){ 
			$week = $expense[0]['week'];
		} 
		if(!empty($income[$i][0]['sum'])){ 
			$amountIncome = $income[$i][0]['sum'];
		} 
		if(!empty($balance[$i][0]['amount'])){ 
			$amountBalance = $balance[$i][0]['amount'];
		} 
		//Hvis der ikke findes en balance for en dato, skal den tage den sidste balance den kan finde
		//Derved bliver balancen ikke pludselig 0, hvis nu der ikke skulle findes en balance opdatering for sidste dag/uge.
		else{
			$o = $i -1;
			$balanceFound = false;
			while($o > 0 && $balanceFound == false){			
				if(!empty($balance[$o][0]['amount'])){
					$a = $o -1;
					$amountBalance = $balance[$a][0]['amount'];
					$balanceFound = true;
				}
				$o -= 1;
			}
		}
		
		echo '<input type="hidden" class="weeks" value=' . $week . '>';
		echo '<input type="hidden" class="expenses" value=' . $amountExpenses . '>';
		echo '<input type="hidden" class="incomes" value=' . $amountIncome . '>';
		echo '<input type="hidden" class="balance" value=' . $amountBalance . '>';
		$i++;
	}
?>