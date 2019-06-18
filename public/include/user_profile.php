<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require '../vendor/autoload.php';

$app = new \Slim\App;

//----------------------------------- user profile login api --------------------------------------//

$app->post('/user/profile/{userId}',function(Request $request,Response $response,array $args){
	$userId=$args['user_id'];

	require "include/db.php";
	require "include/User.class.php";
	require "include/functions.php";

	if(!empty($userId)){
		$query=$db->query("select * from `users` where user_id='$userId'");
		if($query){
			$results=$query->fetchAll(PDO::FETCH_OBJ);
			foreach($results as $result){
				echo $result->username;
			}
		}else{

		}
	}else{

	}
});


$app->run();

?>