<?php

	include ('./login.php');
	
	// Connect to the database
	$con = mysql_connect($host, $user, $passwd) or die ('Could not connect: ' . mysql_error());
	//if($con) {
	//	die ('Could not connect: ' . mysql_error());
	//}
	
	// Create the SQL query command
	$sql = "select MAX(id) from " . $table;
	
	// Select the database
	mysql_select_db($database) or die ('Could not select!');
	
	// Make the sql query
	$result = mysql_query($sql, $con) or die ('Could not get data: ' . mysql_error());
	//if(! $ret) {
	//	die ('Could not get data: ' . mysql_error());
	//}
	
	// Consume the result of the query
	if ($result) {
		while($row = mysql_fetch_assoc($ret)) {
			echo "Temperatura [°C]: " . $row['tTempData'];
			echo "Umidità [%]: " . $row['tHumData'];
		}	
	}	
	
	mysql_close($con);
?>