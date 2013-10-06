<?php 

$this->Html->addCrumb($settings['site_name'], array('controller' => 'forum', 'action' => 'index'));
$this->Html->addCrumb(__d('forum', 'Users'), array('controller' => 'forumusers', 'action' => 'index'));
$this->Html->addCrumb(__d('forum', 'Edit Profile'), array('controller' => 'forumusers', 'action' => 'edit')); ?>

<div class="title">
	<h2><?php echo __d('forum', 'Edit Profile'); ?></h2>
</div>

<?php echo $this->Form->create('Profile', array(
	'url' => array('controller' => 'forumusers', 'action' => 'edit')
)); ?>

<div class="container">
	<div class="containerContent">
		<table>
			<tr>
				<td style="width:50%;">
					<?php 
					echo $this->Form->input('locale', array('options' => $config['locales'], 'label' => __d('forum', 'Language')));
					?>
				</td><td>
					<?PHP
					echo $this->Form->input('email', array('type' => 'text', 'value' => '', 'label' => __d('forum', 'Email')));					
					?>
				</td>
			</tr><tr>
				<td>
					<?PHP
					echo $this->Form->input('signature', array('type' => 'textarea', 'rows' => 5, 'label' => __d('forum', 'Signature'))); 
					?>
				</td><td>
					<?PHP
					echo $this->Form->input('newpassword', array('type' => 'password', 'value' => '', 'label' => __d('forum', 'New Password')));
					echo $this->Form->input('repassword', array('type' => 'password', 'label' => __d('forum', 'Re-enter new password')));
					?>
				</td>
			</tr><tr>
				<td>
					<?PHP
					echo $this->Form->input('timezone', array('options' => $config['timezones'], 'label' => __d('forum', 'Timezone')));					
					?>
				</td>			
				<td>
					<?PHP
					
					?>
				</td>
			</tr>
		</table>
		<?PHP
		echo $this->Form->input('currentpassword', array('type' => 'password', 'label' => __d('forum', 'Current Password*')));
		echo $this->element('markitup', array('textarea' => 'ProfileSignature')); 
		echo "* You have to supply your current password to make changes to your profile";
	?>
		
	</div>
</div>

<?php 
echo $this->Form->submit(__d('forum', 'Update Account'), array('class' => 'button'));
echo $this->Form->end();
?>
