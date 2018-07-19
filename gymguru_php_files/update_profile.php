<?php
	require_once "connect.php";

	if(!$con){
		echo "Database Connection Failed!!!";
	}else{
		if($_SERVER["HTTP_USER_AGENT"]=="GymGuru"){
			if($_SERVER['REQUEST_METHOD']=="POST"){
				if($_isset($_POST['fullname']) && $_isset($_POST['photo']) && $_isset($_POST['dob']) && $_isset($_POST['weight']) && $_isset($_POST['target_weight']) && $_isset($_GET['userID'])){
					$fullname=$_POST['fullname'];
					$photo=$_POST['photo'];
					$dob=$_POST['dob'];
					$weight=$_POST['weight'];
					$target_weight=$_POST['target_weight'];
					$userID=$_GET['userID'];

					$update="UPDATE `users_91841` SET `fullname`='$fullname',`photo`='$photo',`dob`='$dob',`weight`='$weight',`target_weight`='$target_weight' WHERE `id`='$userID';";
					$updateSql=mysqli_query($con, $update);		
					if($updateSql){
						echo "success";
					}else{
						echo "Error";
					}				
										
					mysqli_close($con);
				}else{
					echo "Missing required fields";
				}
			}else{
				echo "Improper Request Method!!!";
			}
		}else{
			echo "Invalid Platform!!!";
		}
	}

?>