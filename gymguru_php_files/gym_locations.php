<?php
	require_once "connect.php";

	if(!$con){
		echo "Database Connection Failed!!!";
	}else{

		$select="SELECT * FROM gym_locations_91841";
		$selectSql=mysqli_query($con, $select);
		$countSelect=mysqli_num_rows($con, $selectSql);

		while($rowSelect=mysqli_fetch_array($con, $selectSql)){
			$r[]=$rowSelect;
		}

		if($countSelect < 0){
			$r[$countSelect]="No gym found";
			print(json_encode($r));
		}else{
			$r[$countSelect]="Success";
			print(json_encode($r));
		}
		
	}

?>
