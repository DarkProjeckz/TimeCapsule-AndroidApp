<?php
$header = getallheaders(); 
if($header['Authorization'] == "b3df7b0a616d66fe088ab75b4b3d72a5")
{
	 include_once "db.php";
	 $title=$_POST["title"];
	 $date=$_POST["date"];
	 $id=$_POST["id"];
	 $cont=$_POST["cont"];
	 $sql="select cont from EVENTS where title='$title' and dat='$date' and user_id=$id;"; 
	 $result=mysqli_query($conn,$sql);
	if(mysqli_num_rows($result)>0)
	{
	    $sql="update EVENTS set cont='$cont' where title='$title' and dat='$date' and user_id=$id;"; 
	    $result=mysqli_query($conn,$sql);
	    echo "SUCCESS";
	}
	else{
	     echo "FAILED";
	 }
	$conn->close();
}
else{
	header("Location: ./404.html");
}
?>