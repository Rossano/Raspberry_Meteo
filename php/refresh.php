<?php

	include ($login.php);
	
	// Connect to the database
	$con = mysql_connect($host, $user, $passwd);
	
	if($con) {
		die ('Could not connect: ' . mysql_error());
	}
	
	// Create the SQL query command
	$sql = "select MAX(id) from " . $table;
	
	// Select the database
	mysql_select_db($database);
	
	// Make the sql query
	$ret = mysql_query($sql, $con);
	if(! $ret) {
		die ('Could not get data: ' . mysql_error());
	}
	
	// Consume the result of the query
	while($row = mysql_fetch_assoc($ret)) {
		echo "Temperatura [°C]: " . $row['tTempData'];
		echo "Umidità [%]: " . $row['tHumData'];
	}
	
	mysql_close($con);
?>