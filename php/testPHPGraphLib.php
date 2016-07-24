<?php
	
	include("phpgraphlib.php");
	
	$graph = new PHPGraphLib(400, 300);
	
	$data = array("Alex"=>99, "Simon"=>98, "Mattia"=>57, "Pier"=>70);
	$graph->addData($data);
	$graph->setTextColor("blue");
	$graph->createGraph();
	
?>
