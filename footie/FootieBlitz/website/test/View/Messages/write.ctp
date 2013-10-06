<?PHP
echo $this->Html->script('http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js');
echo $this->Html->css('/css/autocomplete.css');
?>
<h1>Please write a message</h1>
<label for="MessageReciever">Reciever</label>
<?php
//'url'=> $this->here sætter url til det som den er i forvejen så evt. parametre ikke går tabt
echo $this->Form->create('Message', array('action' => 'write', 'url'=> $this->here)); 

echo $this->AutoComplete->input(
    'User.username',
    array(
        'autoCompleteUrl'=>$this->Html->url( 
            array(
                'controller'=>'messages',
                'action'=>'auto_complete',
            )
        ),
        'autoCompleteRequestItem'=>'autoCompleteText',
    )
);
echo $this->Form->input('subject');
?><label for="MessageReciever">Text</label><?php
echo $this->Form->textarea('text');
echo $this->Form->end('Send message');

?>
<?php 


echo $this->Js->writeBuffer(); 
?> 


