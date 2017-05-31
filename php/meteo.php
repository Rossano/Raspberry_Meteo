<?php

	class simpleCMS {
		var $user = "meteoUser";
		var $passwd = "ciccio";
		var $host = "localhost";
		var $database = "HomeWheatherStation";
		var $table = "sensorMeas";
		
		public function display_records() {
			$req = "SELECT * from " . $this->table;
			$query = mysql_query($req);
			
			if($query != false && mysql_num_rows($query) > 0) {
				while ($row = mysql_fetch_assoc($query)) {
					$title = stripslashes($row['title']);
					$bodytext = stripslashes($row['bodytext']);
					$entry_display .= //<<<ENTRY_DISPLAY
						"<h2>$title</h2>
						<p>$bodytext</p>";
					//ENTRY_DISPLAY;
				}
			}
			else {
				$entry_display =//<<<ENTRY_DISPLAY
					"<h2>Under Construction</h2>
					<p>No entries have been made on this page.
					Please check back soon or refresh
					</p>";
					//ENTRY_DISPLAY;	
			}

			$entry_display = //.<<<ADMIN_OPTION
				"<p class=\"admin_link\">
					<a href=\"{$_SERVER['PHP_SELF']}?admin=1\">Refresh</a>
				</p>";
				//ADMIN_OPTION;

			return $entry_display;
		}
		
		public function write () {
			
			
		}
		
		public function connect () {
			mysql_connect($this->host, $this->passwd) 
				or die ("Database connection failed ! ".mysql_error());
			mysql_select_dB($this->table) or die ("Table not existing! ".mysql_error());
			return $this->dBfetch();
		}
		
		public function dBfetch() {
			//$sql = <<<MySQL_QUERY ("SELECT MAX(id) FROM " . $table) MySQL_QUERY;
			$sql = "SELECT MAX(id) FROM " . $table;
			return mysql_query($sql);
		}
		
		public function display_form() {
			return //<<< DISPLAY_FORM
				"<form action=\"{$_SERVER['PHP_SELF']}\" method=\"post\">
					<label for=\"Title\">Titolo</label>
					<input name=\"title\" id=\"title\" type= \"text\" maxlength=\"150\" />
					<label for=\"body_text\">Body Text</label>
					<text_area name=\"bodyText\" id=\"bodyText\" />
					<input type=\"submit\" value=\"Crea questa Entry\" />
				</form>";
				//DISPLAY_FORM;
		}
	}
	
?>