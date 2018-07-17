<?php
	
	define('DB_HOST', 'localhost');
	define('DB_USER', 'id6399318_android_jabuya');
	define('DB_PASSWORD', '12345678');
	define('DB_NAME', 'id6399318_android_jabuya');

	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME) or die("Error: " . mysqli_error());
?>