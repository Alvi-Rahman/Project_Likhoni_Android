<?php

    $server=    "fdb19.awardspace.net";
    $username=  "2591192_ony";
    $pass=  "ony_16101200";
    $dbname=    "2591192_ony";

    $conn = new mysqli($server, $username, $pass,$dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 


$name=$_POST["name"];
$email=$_POST["email"];
$password=$_POST["password"];


$sql= "insert into myfirst values('$name','$email','$password');";


    if(mysqli_query($con,$sql)){
        echo "values added";
    }
    else{
        echo "values not added";
    }


?>