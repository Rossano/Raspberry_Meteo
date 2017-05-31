
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Cp1252">
<title>Test PHP</title>
    
<?php
	if($_POST["name"] || $_POST["age"]) 
	{
		echo "Welcome ".$_POST['name']."<br \>";
		echo "You are ".$_POST['age']." years old";
		exit();
	}
?>

</head>
    <body>
    <p>This page uses frames. The current browser you are using does not support frames.</p>

	
		<form action="<?php $_SERVER['PHP_SELF'] ?>" method="POST">
			Name: <input type="text" name="name" />
			Age: <input type="text" name="age" />
			<input type="submit" />
		</form>
    </body>
</html>