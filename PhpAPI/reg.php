<?php
$header = getallheaders(); 
if($header['Authorization'] == "b3df7b0a616d66fe088ab75b4b3d72a5")
{
	include_once 'db.php';
	$user_name=$_POST["user"];
	$password=$_POST["pass"];
	$cpass=$_POST["cpass"];
	$email=$_POST["email"];
	$phoneno=$_POST["phone"];
	$gender=$_POST["gender"];
	$dob=$_POST["dob"];

	$sql="insert into USERS(uname,passwd,cpass,email,dob,phno,gender)values('$user_name','$password','$cpass','$email','$dob',$phoneno,'$gender');";
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