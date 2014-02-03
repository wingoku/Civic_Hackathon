<html>
<head>
<meta http-equiv="refresh" content="10">
</head>

<?php
include("connection.php");

$user="11";
$pass="Stars8333";

$base_url="http://api.smilesn.com";
$session_url=$base_url."/session?username=".$user."&password=".$pass;

$session_obj=  (array) json_decode(file_get_contents($session_url));
$session_id = $session_obj['sessionid'];


//-----------------Sending URL-----------------------
/*$rec_num="03439861825";
$msg="Hello Dude!";
$sendr_num="8333";

$send_url=$base_url."/sendsms?sid=".$session_id."&receivenum=".$rec_num."&textmessage=".$msg."&sendernum=".$sendr_num;
$send_obj = (array) json_decode(file_get_contents($send_url));

print_r($send_obj);
return;
*/
//---------------Receiving Part----------------------

//check if we have pending SMS with us...

$rec_url=$base_url."/receivesms?sid=".$session_id;

//while(1)
//{
	$rec_obj= (array) json_decode(file_get_contents($rec_url));	
	//print_r($rec_obj);
	
	foreach($rec_obj['status'] as $message)
	{
		$reply_list="Reply With Your Choice\n ";
		$reply_urdu="\n انتخاب کیجئے\n";
		$message = (array) $message;
		$message_text = $message['text'];
		$message_num = $message['sender_num'];
//		echo $message_text;
//		echo $message_num;
	//return ;
//}
$message_num= str_replace("+92","0",$message_num);
	

		if ($message_text == "")
		{
			//empty message; send a list of commands....
			
		  $query=mysql_query("select * from department");
			$count=1;
			while($row=mysql_fetch_assoc($query))
			{
	 			$reply_list=$reply_list.$count." : ".$row['dept_name']."\n";
				$reply_urdu=$reply_urdu.$count." : ".$row['name_urdu']."\n";
				
				$count+=1;
			}
				//echo $string;
			
			$reply_list=$reply_list.$reply_urdu;
			
			$reply_list=urlencode($reply_list);
			$response=send_sms($base_url,$session_id, $message_num,$reply_list);
			print_r($response);
		}
		else
			{
				$lat=34.00192;
				$long=71.48522;
				
										
				///$dept = $_GET['dept'];
 
 
 //$query="Select * From $tableHosp Where id In (Select hosp_id From $tableRel Where dept_id = (Select dept_id From $tableDept Where dept_name = '$dept'))";

$query="Select * From $tableHosp Where id In (Select hosp_id From $tableRel Where dept_id = $message_text)";
 
 $res=mysql_query($query,$con);
	
  $counter = 0;
  while($row=mysql_fetch_assoc($res)){
	  	 
	 $data[] = $row;
	 
	 $distance = calc_distance($lat, $long, $row['lat'], $row['long']);
	// echo  "<br />". $distance. "<br />";
	 
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

		
	//header("Content-type: application/json");		
	//echo json_encode($data);
//	echo json_encode($min_hosp['address']);
	
	$address=$min_hosp['name']."\n".$min_hosp['address'];
	
	$address=urlencode($address);
			$response=send_sms($base_url,$session_id, $message_num,$address);
			print_r($response);
			
		}
	}
		//return;
/*		}
	}
	
	sleep(10);
}
*/

//$message_num="03339036256";
//$msg=urlencode("");


//$response=send_sms($base_url,$session_id, $message_num,$msg);
//print_r($response);

function send_sms($base_url, $sessionid, $rec_num, $msg)
{
	$sender_num = "8333";
	$send_url=$base_url."/sendsms?sid=".$sessionid."&receivenum=".$rec_num."&textmessage=".$msg."&sendernum=".$sender_num;
	echo $send_url;
	$send_obj = (array) json_decode(file_get_contents($send_url));

	return $send_obj;
}


//-----------Calculate DIstance----------------------

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
</html>