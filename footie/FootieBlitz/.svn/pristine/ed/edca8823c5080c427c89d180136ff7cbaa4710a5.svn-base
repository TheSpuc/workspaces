<!-- app/View/Users/add.ctp -->
<div class="users form">
<?php 

if ($countTotal < 50){
	
	echo $this->Form->create('User');?>
    <fieldset>
        <legend><?php echo __('Register User'); ?></legend>
    <?php
//     	echo $captcha;
    	echo $this->Form->input('captcha', array('label' => 'Calculate this: '.$captcha, 'div' => 'required'));
		echo $this->Form->input('email');
        echo $this->Form->input('username');
        echo $this->Form->input('password');
		
    ?>
    </fieldset>
<?php echo $this->Form->end(__('Submit'));
}

//debug($userset);
?>
</div>
