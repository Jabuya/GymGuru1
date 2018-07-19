<?php
	require_once "connect.php";

	if(!$con){
		echo "Database Connection Failed!!!";
	}else{
		if($_SERVER["HTTP_USER_AGENT"]=="GymGuru"){
			if($_SERVER['REQUEST_METHOD']=="POST"){
				if($_isset($_POST['location']) && $_isset($_POST['date']) && $_isset($_POST['exercise']) && $_isset($_POST['reps']) && $_isset($_POST['sets']) && $_isset($_GET['userID'])){
					$location=$_POST['location'];
					$date=$_POST['date'];
					$exercise=$_POST['exercise'];
					$reps=$_POST['reps'];
					$sets=$_POST['sets'];
					$userID=$_GET['userID'];

					$insert="INSERT INTO session_91841(location_id, `date`, exercise_type_id, no_of_reps, no_of_sets, user_id) VALUES('$location', '$date', '$exercise', '$reps', '$sets', '$userID')";
					$insertSql=mysqli_query($con, $insert);		
					if($insertSql){
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