<?php
$header = getallheaders(); 
if($header['Authorization'] == "b3df7b0a616d66fe088ab75b4b3d72a5")
{
	include_once 'db.php';
	$user_name=$_POST["user"];
	$password=$_POST["pass"];
	$sql="select id from USERS where uname like '$user_name' and passwd like '$password';";
	$result=mysqli_query($conn,$sql);
	if(mysqli_num_rows($result)>0)
	{
		$row = $result->fetch_assoc(); 
	    echo $row["id"];
	}
	else{
		echo "INVALID";
	}
}
else{
	header("Location: ./404.html");
}
?>