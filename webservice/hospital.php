<?php
  /*$host = "localhost";
  $user = "root";
  $pass = "";
*/

  include("connection.php");
   
 $lat = $_GET['lat'];
 $long = $_GET['long'];
 $dept = $_GET['dept'];
 
 
 $query="Select * From $tableHosp Where id In (Select hosp_id From $tableRel Where dept_id = (Select dept_id From $tableDept Where dept_name = '$dept'))";
 
 $res=mysql_query($query,$con);
	
  $counter = 0;
  while($row=mysql_fetch_assoc($res)){
	  	 
	 $data[] = $row;
	 
	 $distance = calc_distance($lat, $long, $row['lat'], $row['long']);
	 //echo  "<br />". $distance. "<br />";
	 
	 if ($counter == 0)	
	 {				
		 $min_hosp = $row;					//assign first row as the min be default...
		 $min_distance = $distance;
	 }
	 else
	 {
		 if ($distance < $min_distance)
		 {
			 $min_distance = $distance;
			 $min_hosp = $row;
		 }
	 }
	 
	 $counter++;
  }

	$data_nearest[] = $min_hosp;	
	header("Content-type: application/json");		
	//echo json_encode($data);
	echo json_encode($data_nearest);
	
	
	
	function calc_distance($latFrom, $lonFrom, $latTo, $longTo)
	{
		$r=6372.795477598;

		$latDelta=$latFrom-$latTo;
		$lonDelta=$lonFrom-$longTo;
			
	    $latDelta = deg2rad($latTo - $latFrom);
	    $lonDelta = deg2rad($longTo - $lonFrom);
	  
	    $latTo = deg2rad($latTo);
	    $latFrom = deg2rad($latFrom);
	  
	
		$a=sin($latDelta/2)*sin($latDelta/2)+sin($lonDelta/2)*sin($lonDelta/2)*cos($latFrom)*cos($latTo);
		$c=2*atan2(sqrt($a),sqrt(1-$a));
		$d=$c * $r;
		
		return $d;
	}
	
 ?>
 
