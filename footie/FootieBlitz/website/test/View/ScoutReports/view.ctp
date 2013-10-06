<div style="width: 500px;">
<?php 
echo '<h1>Scout report on ' . $report['Person']['firstname'] . ' ' . $report['Person']['lastname'] . '</h1>';
echo '<b>Filed on ' . substr($report['ScoutReport']['created'], 0, 10) . '</b>';
echo '<br /><br /><p>' . $report['ScoutReport']['report'] . '</p>'; 

echo '<br /><br />' . $this->Html->link('[view player]', array('controller' => 'person', 'action' => 'persondetails', $report['Person']['id']));
//debug($report);

?>
</div>


