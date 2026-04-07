<!-- matrix -->
<html>
<head>
	<title>Matrix Multiplication</title>
	<style>
	
	
	</style>
</head>

<body>
<h2>Matrix Multiplication (N x N)</h2>
<?php
	$btnAction = isset($_POST["btnAction"]) ? $_POST["btnAction"] : '';
	
	if($btnAction == 'Reset'){
		$matrixSize = 0;
		$matrix1 = $matrix2 = $resultMatrix = [];
		$btnAction = '';
	}else{
		$matrixSize = isset($_POST["matrixSize"]) ? (int)$_POST["matrixSize"] : 0;
		$matrix1 = isset($_POST["matrix1"]) ? $_POST["matrix1"] : [];
		$matrix2 = isset($_POST["matrix2"]) ? $_POST["matrix2"] : [];
		$resultMatrix = [];
	}
?>

<span>Enter Matrix size (N) : </span>

<form method="post">

	<input type="number" name="matrixSize" min="1" value="<?= $matrixSize > 0 ? $matrixSize : '' ?>">
	<input type="submit" name="btnAction" value="Generate Matrix">
	<input type="submit" name="btnAction" value="Reset">
	
	<?php
	
	if($matrixSize > 0){
		echo "<h2>Matrix 1</h2>";
		for($row=0; $row<$matrixSize; $row++){
			for($col=0; $col<$matrixSize; $col++){
				$val1 = isset($matrix1[$row][$col]) ? $matrix1[$row][$col] : '';
				echo "<input type='number' name='matrix1[$row][$col]' value='$val1' required>";
			}
			echo "<br>";
		}
		
		echo "<h2>Matrix 2</h2>";
		for($row=0; $row<$matrixSize; $row++){
			for($col=0; $col<$matrixSize; $col++){
				$val2 = isset($matrix2[$row][$col]) ? $matrix2[$row][$col] : '';
				echo "<input type='number' name='matrix2[$row][$col]' value='$val2' required>";
			}
			echo "<br>";
		}
		echo "<br><input type='hidden' name='matrixSize' value='$matrixSize'>";
		echo "<input type='submit' name='btnAction' value='Multiply'>";
		
		if($btnAction == "Multiply"){
			$matrix1 = $_POST["matrix1"];
			$matrix2 = $_POST["matrix2"];
			$resultMatrix = array();
			
			for($row=0; $row<$matrixSize; $row++){
				for($col=0; $col<$matrixSize; $col++){
					$resultMatrix[$row][$col] = 0;
					for($k=0; $k<$matrixSize; $k++){
						$resultMatrix[$row][$col] += $matrix1[$row][$k] * $matrix2[$k][$col];
					}
				}
			}
			echo "<h2>Result Matrix (C = A x B)</h2>";
			for($row=0; $row<$matrixSize; $row++){
				for($col=0; $col<$matrixSize; $col++){
					echo "<input type='number' value='".$resultMatrix[$row][$col]."' disabled>";
				}
				echo "<br>";
			}
		}
	}
	?>

</form>
</body>
</html>

<!-- =================================================================================================== -->

<!-- NIC -->
<?php
$nic="";
$msg = "";
$dobPrint = "";
$genderPrint = "";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $nic = trim($_POST['nic']);
    $year = "";
    $dayValue = 0;
    $isValid = true;
    $action = $_POST['action'];


    if(strlen($nic) == 10){
        $year = "19" . substr($nic, 0,2);
        $dayValue = (int)substr($nic, 2,3);
        $msg = "This is a <b>OLD NIC Number";
    }elseif(strlen($nic) == 12){
        $year = substr($nic, 0, 4);
        $dayValue = (int)substr($nic, 4, 3 );
        $msg = "This is a <b>NEW NIC Number";
    }else{
        $msg = "Invalid NIC Number.";
        $isValid = false;
    }

    if($isValid){
        $gender = "Male";
        if($dayValue > 500){
            $gender = "Female";
            $dayValue -= 500;
        }
		if($dayValue > 0 && $dayValue<=366){
			$monthDays = [
				"january" => 31,
				"February" => 29,
				"March" => 31,
				"April" => 30,
				"May" => 31,
				"June" => 30,
				"July" => 31,
				"August" => 31,
				"September" => 30,
				"October" => 31,
				"November" => 30,
				"December" => 31
			];
			$currentMonth = "";
			$birthday = 0;

			foreach($monthDays as $month => $days){
				if($dayValue <= $days){
					$currentMonth = $month;
					$birthday = $dayValue;
					break;
				}
				$dayValue -= $days;
			}

			$displayDay = str_pad($birthday, 2, "0", STR_PAD_LEFT);

			$dobPrint =  "<strong>Date of birth : {$displayDay}th {$currentMonth} {$year}</strong>";
			$genderPrint =  "<br>Gender : <b>$gender</b></p>";
			if($action == "Reset"){
				$nic="";
				$msg = "";
				$dobPrint = "";
				$genderPrint = "";
			}
		}else{
			$msg = "Invalid Day sequence!! Enter correct NIC Number.";
		}
        
    }
}

?>

<html>
    <head>
        <title>NIC Decoder</title>
        <style>
            body{
                font-family: Arial;
                background-color: #f4f4f4;
            }
            .box{
                width: 400px;
                margin: 50px auto;
                border-radius: 10px;
                background-color: white;
                padding: 20px;
                border: 1px solid #ccc;
            }
            input[type=text]{
                width: 80%;
                height: 30px;
                margin-top: 5px;
                color: blue;
            }
            input[type=submit]{
                padding: 10px 20px;
                margin-right: 10px;
                border: none;
                color: white;
                cursor: pointer;
                border-radius: 5px;
            }
            input[type=submit]{
                background-color: red;
            }
            #reset{
                background-color: blue;
            }
        </style>

    </head>

<body>
    <div class="box">
        <form method="POST">
            <h3>NIC Number :</h3><input type="text" name="nic" value="<?php echo $nic;?>"
                placeholder= "ex:- 200318111080 or 761542323v" 
                title="Enter 12 digits or 9 digits ending with V or X"
                pattern="^[0-9]{9}[vVxX]$|^[0-9]{12}$"
                oninput="this.value=this.value.replace(/[^0-9vVxX]/g, '');"
                maxlength="12" required
            ><br>
            <p><?php echo $msg;?></p>
            <input type="submit" name="action" value="Submit">
            <input type="submit" id="reset" name="action" value="Reset" formnovalidate>
        </form>
        <?php 
            echo "<br>".$dobPrint.$genderPrint;
        
        ?>


    </div>
</body>

</html>

<!-- =================================================================================================== -->


<!-- Roman number -->
<!--HTML-->
<form method="post" action="romanProcess">
	<h2>Roman Calculater</h2>
	Enter here Roman Number : 
	<input type="text" name="roman" placeholder="ex:- MCMXCIX">
	<button type="submit" name="btnAction" value="RS">Convert</button>
	<br><br>
	Enter here Decimal Number : 
	<input type="text" name="number" placeholder="ex:- 1999">
	<button type="submit" name="btnAction" value="DS">Convert</button>

</form>

<!--PHP-->
<?php
function getDecimalValue($char){
	$char = strtoupper($char);
	$values = [
		'I' => 1,
		'V' => 5,
		'X' => 10,
		'L' => 50,
		'C' => 100,
		'D' => 500,
		'M' => 1000
	];
	return $values[$char];
}


function getRomanValue($number){
	$map = [
		1000 => 'M',
		900 => 'CM',
		500 => 'D',
		400 => 'CD',
		100 => 'C',
		90 => 'XC',
		50 => 'L',
		40 => 'XL',
		10 => 'X',
		9 => 'IX',
		5 => 'V',
		4 => 'IV',
		1 => 'I'
	];

	$result = "";

	foreach($map as $value => $symbol){
		while($number >= $value){
			$result .= $symbol;
			$number -= $value;
		}
	}

	return $result;
}

if($_SERVER["REQUEST_METHOD"] == "POST"){
	$submit = isset($_POST["btnAction"]) ? $_POST["btnAction"] : '';
	if(!empty($_POST["roman"]) && $submit == "RS"){
		$roman = trim($_POST["roman"]);
		
		if(!preg_match('/^[ivxlcdmIVXLCDM]+$/', $roman)){
			echo "Error: Only Roman letters allowed!!";
		}else{
			$decimal = getDecimalValue($roman[0]);
			$i=1;
			
			while(isset($roman[$i])){
				$currentVal = getDecimalValue($roman[$i]);
				$prevVal = getDecimalValue($roman[$i-1]);
				
				$decimal += $currentVal;
				
				if($currentVal > $prevVal){
					$decimal -= (2*$prevVal);
				}
				$i++;
			}
			
			echo "<h3>Roman Number : ".strtoupper($roman)."</h3>";
			echo "<h3>Decimal Equivalent : ".$decimal."</h3>";
		}
	}

	if(!empty($_POST["number"]) && $submit == "DS"){
		$num = (int)$_POST["number"];
		echo "<h3>Decimal Number : ".$num."</h3>";
		echo "<h3>Roman Equivalent : ".getRomanValue($num)."</h3>";
	}
}
?>

<!-- =================================================================================================== -->



<!--FORM VALIDATION-->
<html>
<head>
    <title>Form Validation</title>
    <style>
		body{
			display:flex;
			justify-content: center;
			background-color:#ccc;
		}
		.container{
			text-align:center;
			background-color:white;
			margin:40px auto;
			border-radius:10px;
			padding: 0 10px;
		}
		.error{
			color: red;
		}
		input[type=submit],[type=reset]{
			color:white;
			margin:5px;
			border:none;
			border-radius:2px;
			cursor:pointer;
			width:60px;
			height:20px;
		}
		input[type=submit]{
			background-color:green;
		}
		input[type=reset]{
			background-color:red;
		}
		span{
			font-size:10px;
		}
    </style>
</head>

<body>
	<div class="container">
		<h2>PHP Form Validation Example</h2>
		<form method="post" class="form">
			<table class="table" >
				
				<tr>
					<td>Name: </td>
					<td><input type="text" name="name" ><span class="error">* <?php echo $nameErr;?></span></td>
				</tr>
				
				<tr>
					<td>E-Emai: </td>
					<td><input type="text" name="email"><span class="error">* <?php echo $emailErr;?></span></td>
				</tr>
				
				<tr>
					<td>Website: </td>
					<td><input type="text" name="website"><span class="error">* <?php echo $websiteErr;?></span></td>
				</tr>
				
				<tr>
					<td>Comment: </td>
					<td><textarea name="comment" cols="40" rows="5"></textarea></td>
				</tr>
				
				<tr>
					<td>Gender: </td>
					<td>
						<input type="radio" name="gender" value="Male" checked>Male
						<input type="radio" name="gender" value="Female">Female
						<input type="radio" name="gender" value="Others">Others
						<span class="error">*</span>
						</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" name="submit" value="Submit">
						<input type="reset" name="reset" value="Reset">
					</td>
				</tr>
			</table>
		</form>
		<?php
			if ($_SERVER["REQUEST_METHOD"] == "POST" && $nameErr == "" && $emailErr == ""  && $websiteErr == "") {
				echo "<h2>Your Input:</h2>";
				echo "Name => " . $name . "<br>" . $email . "<br>" . $website . "<br>" . $comment . "<br>" . $gender;
			}
		?>
	</div>


</body>

</html>