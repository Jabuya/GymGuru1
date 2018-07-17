<?php

	require_once "connect.php";

	if(!$con){
		echo "Database Connection Failed!!!";
	}else{
		if($_SERVER["HTTP_USER_AGENT"]=="GymGuru"){
			if($_SERVER['REQUEST_METHOD']=="POST"){
				if($_isset($_POST['email']) && $_isset($_POST['password'])){
					$email=$_POST['email'];

					$checkSql=mysqli_query($con,"SELECT * FROM users_91841 WHERE email = '$email");					
					if(mysqli_num_rows($checkSql)==1){						
						$checkRow=mysqli_fetch_assoc($checkSql);
						$pass=$checkRow['password'];

						if(password_verify($_POST["password"],$pass)){
						    echo "Login success!!!"; 
						}else{
						    echo "Error. Login failed!!!";
						}

					}else {
						echo "Error. Login failed!!!";
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