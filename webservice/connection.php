<?php

		//Connect to databse...		
		 
		/*$host = "localhost";
		$user = "dreamsso_hack";
		$password = "hack123";
		$db = "dreamsso_hackathon";
		*/
		
		$host = "166.62.8.75";
		$user = "hackathon";
		$password = "m@Sood5192";
		$db = "hackathon";
		
		
		/*$host = "localhost";
		$user = "root";
		$password = "";
		$db = "hackathon2";
		*/
		
		//Table Names...
		$tableHosp = "hospital";
  		$tableDept = "department";
		$tableRel = "relation";
  
  
		$con = mysql_connect($host, $user, $password);
		if (!$con)
		{
			print "Failed to connect to database";
			return 0;
		}
		
		$check = mysql_select_db($db , $con);
	
		if (!$check)
		{
			print "Database not available";
			return 0;	
		}

?>