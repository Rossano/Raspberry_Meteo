<?php
	include ('./login.php');
	
	$dBMeteo = mysql_connect($host, $user, $passord) or die('Could not connect '.mysql_error());
	mysql_connect_dB($database) or die ('Could not select database '.mysql_error());
	
	$sql = "SELECT * FROM ".$table." ORDER BY id DESC LIMIT 1;";
	$result = mysql_query($sql);
	
	if($result) {
		while ($row = mysql_fetch_assoc($result)) {
			$temp = $row["tTemp"];
			$hum = $row["tHum"];
			$_POST['temperature'] = (string)$temp;
			$_POST['humidity'] = (string)$hum;
		}
	}
?>