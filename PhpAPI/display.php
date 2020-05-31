<?php
$header = getallheaders(); 
if($header['Authorization'] == "b3df7b0a616d66fe088ab75b4b3d72a5")
{
	include_once "db.php";
	$title=$_POST["title"];
	$date=$_POST["date"];
	$id=$_POST["id"];
	$sql="select cont from EVENTS where title='$title' and dat='$date' and user_id=$id;"; 
	$result=mysqli_query($conn,$sql);
	if(mysqli_num_rows($result)>0){
	    $row = $result->fetch_assoc(); 
	    echo  $row['cont'];
	}
	else{
	     echo "ERROR";
	}
}
else{
	header("Location: ./404.html");
}
?>
