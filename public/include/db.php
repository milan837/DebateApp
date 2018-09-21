<?php

try{
	$db=new PDO("mysql:host=localhost;dbname=debate","root","");
}catch(Exception $e){
	echo $e->getMessage();
}

?>