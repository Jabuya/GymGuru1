<?php
	require_once "connect.php";

	$email=$_GET['email'];
	$code=$_GET['code'];
	$dbcode="";

	$checkQuery="SELECT * FROM users_91841 WHERE email = '$email";
	$checkResult=mysqli_query($con, $checkQuery);

	if($checkResult){
		while($checkRow=mysqli_fetch_assoc($checkResult)){
			$dbcode=$checkRow['code'];
		}

		if($dbcode==$code){
			$updateQry="UPDATE users_91841 SET confirmed='1', code='0' WHERE email='$email";
			$updateResult=mysqli_query($con, $updateQry);
			echo "Congratulations! Your account has been activated!!!";
		}else{
			echo "Sorry, the code did not match. Email verification failed!!!";
		}
	}else{
		echo "No result was returned";
	}
?>