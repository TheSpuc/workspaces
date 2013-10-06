<?php 

echo '<h1>Change your offer to become the agent for ' . $person['Person']['firstname'] . ' ' . $person['Person']['lastname'] . '</h1>';

echo '<strong>Agent fees:</strong><br />';

echo $this->Form->create('AgentContract');

$percent = array();
for ($counter = 0; $counter <= 50; $counter += 1) {
	$percent[$counter] = $counter . '%';
}

echo $this->Form->input('Wage', array('options' => $percent, 'default' => $contract['AgentContract']['percent_wage']));
echo $this->Form->input('Sign on fee', array('options' => $percent, 'default' => $contract['AgentContract']['percent_fee']));

echo $this->Form->end('Change offer');

?>