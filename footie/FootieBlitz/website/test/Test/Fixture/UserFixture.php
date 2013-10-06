<?php
/* User Fixture generated on: 2012-02-14 08:36:53 : 1329205013 */

/**
 * UserFixture
 *
 */
class UserFixture extends CakeTestFixture {

/**
 * Fields
 *
 * @var array
 */
	public $fields = array(
		'id' => array('type' => 'integer', 'null' => false, 'default' => NULL, 'length' => 11, 'key' => 'primary'),
		'username' => array('type' => 'string', 'null' => false, 'length' => 60),
		'password' => array('type' => 'string', 'null' => false, 'length' => 128),
		'email' => array('type' => 'string', 'null' => false),
		'created' => array('type' => 'integer', 'null' => false),
		'access' => array('type' => 'integer', 'null' => false),
		'login' => array('type' => 'integer', 'null' => false),
		'status' => array('type' => 'integer', 'null' => false, 'default' => '0'),
		'signature' => array('type' => 'string', 'null' => false),
		'locale' => array('type' => 'string', 'null' => false, 'default' => 'eng', 'length' => 3),
		'timezone' => array('type' => 'string', 'null' => false, 'default' => '(-8)', 'length' => 32),
		'totalPosts' => array('type' => 'integer', 'null' => false, 'default' => '0'),
		'totalTopics' => array('type' => 'integer', 'null' => false, 'default' => '0'),
		'currentLogin' => array('type' => 'datetime', 'null' => true),
		'lastLogin' => array('type' => 'datetime', 'null' => true),
		'userpoints' => array('type' => 'integer', 'null' => true, 'default' => '100'),
		'indexes' => array('PRIMARY' => array('unique' => true, 'column' => 'id'), 'users_username_key' => array('unique' => true, 'column' => array('username', 'email'))),
		'tableParameters' => array()
	);

/**
 * Records
 *
 * @var array
 */
	public $records = array(
		array(
			'id' => 1,
			'username' => 'Lorem ipsum dolor sit amet',
			'password' => 'Lorem ipsum dolor sit amet',
			'email' => 'Lorem ipsum dolor sit amet',
			'created' => 1,
			'access' => 1,
			'login' => 1,
			'status' => 1,
			'signature' => 'Lorem ipsum dolor sit amet',
			'locale' => 'L',
			'timezone' => 'Lorem ipsum dolor sit amet',
			'totalPosts' => 1,
			'totalTopics' => 1,
			'currentLogin' => '2012-02-14 08:36:53',
			'lastLogin' => '2012-02-14 08:36:53',
			'userpoints' => 1
		),
	);
}
