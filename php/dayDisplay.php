<?php

	include ('./login.php');
	include ('./lib/phpgraphlib/phpgraphlib.php');
	
	$graph = new PHPGraphLib(850, 500);
	
	$dbMeteo = mysql_connect($host, $user, $passwd) or die ('Could not connect: ' . mysql_error());
	mysql_connect_db($database) or die ('Could not select database');
	
	$dataT = array();
	$dataH = array();
	$sql = "SELECT MAX(id) FROM " . $table;
	$result = mysql_query($sql) or die ('Query failed: ' . mysql_error());
	
	if ($result) {
		while ($row = mysql_fetch_assoc($result)) {
			$day = $row['tdate'];
		}	
	}
	// Get the record of the associated date
	$foo = $_POST["init"];
	$sql = "SELECT * from " . $table . " WHERE tdate = DATE($foo)";
	$result = mysql_query($sql) or die ('Query failed: ' . mysql_error());
	if ($result) {
		while ($row = mysql_fetch_assoc($result)) {
			$time = $row["ttime"];
			$temp = $row["tTemp"];
			$hum = $row["tHum"];
			$dataT[$time] = $temp;
			$dataH[$time] = $hum;
		}
	}
	
	mysql_close($dbMeteo);
	
	// Create the graph
	$graph->addData($dataT, $dataH);
	$graph->setTitle("Temperatura ed umidità del " . $foo);
	$graph->addLegend(true);
	$graph->setLegendTitle('Temperatura', 'Umidità');
	$graph->createGraph();
	
?>