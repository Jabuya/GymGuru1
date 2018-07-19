<?php
	require_once "connect.php";

	if(!$con){
		echo "Database Connection Failed!!!";
	}else{
		if($_SERVER["HTTP_USER_AGENT"]=="GymGuru"){
			if($_SERVER['REQUEST_METHOD']=="POST"){
				if($_isset($_POST['f_name']) && $_isset($_POST['l_name']) && $_isset($_POST['email']) && $_isset($_POST['pwd'])){
					$fullname=$_POST['f_name']." ".$_POST['l_name'];
					$email=$_POST['email'];
					$hash_pwd=password_hash($_POST['pwd'], PASSWORD_DEFAULT);

					$checkSql=mysqli_query($con,"SELECT * FROM users_91841 WHERE email = '$email");					
					if(mysqli_num_rows($checkSql)>0){
						echo "Registration failed! A user with that e-mail already exists!!!";
					}else{
						$code=rand();

						$insert="INSERT INTO user_91841(fullname, email, password, confirmed, code, photo, home_gym_id, dob, gender, weight, target_weight) VALUES('$fullname', '&email', '$hash_pwd', 'false', '$code', 'NULL', 'NULL', 'NULL', 'NULL', 'NULL', 'NULL')";
						$insertSql=mysqli_query($con, $insert);						
						if($insertSql){
							$from="From: DoNotReply@gymguru.com";
							$to=$email;
							$subj="GymGuru Email Verification";
							$msg="
								$fullname, 

								Please click on the link below to verify your e-mail and activate your account. 
								If the link does not work, copy it (Ctrl+C) and paste it (Ctrl+V) to your browser. 

								https://jabuyaandroid.000webhostapp.com/gymguru/emailver.php?email=$email&code=$code

							";
							mail($to, $subj, $msg, $from);
							echo "Registration request received! A link has been send to your email! Please check your e-mail to complete your registration!!!";
						}else{
							echo "Error: ".$insert."<br>".mysqli_error($con);
						}
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