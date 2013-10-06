<?PHP
echo "Forgot password";
echo $this->Form->create('ForgotPassword');
echo $this->Form->input('email', array('type' => 'text', 'label' => 'Email'));
echo $this->Form->end('Reset password');
	
?>