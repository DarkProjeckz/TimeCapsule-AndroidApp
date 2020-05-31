<?php
$header = getallheaders(); 
if($header['Authorization'] == "b3df7b0a616d66fe088ab75b4b3d72a5")
{
	include_once 'db.php';
	$title=$_POST["title"];
	$date=$_POST["date"];
	$mem=$_POST["mem"];
	$uid = $_POST["id"];
	$sql="insert into EVENTS (user_id,title,dat,cont) values ($uid,'$title','$date','$mem');";
	 if ($conn->query($sql) === TRUE) {
	   echo "SUCCESS";
	 } else {
	  echo "FAILED";
	 }
	$conn->close();
	
}
else{
	header("Location: ./404.html");
}
?>  