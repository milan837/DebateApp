<?php
#------------------------------- login page api ------------------------------

use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require '../vendor/autoload.php';

$app = new \Slim\App;

//----------------------------------- E-mail login api --------------------------------------//

$app->post('/auth/login/email',function(Request $request,Response $response,array $args){
	require 'include/db.php';
	$email=$request->getParsedBody()['email'];
	$password=md5(trim($request->getParsedBody()['password']));

	if(!empty($email) && !empty($password)){
		$checkQuery=$db->query("select * from `users` where email='$email' and password='$password'");
		if($checkQuery->rowCount() == 1){
			$fetchUserId=$db->query("select user_id from `users` where email='$email' and password='$password'");
			$result=$fetchUserId->fetch();
			
			$userId=$result['user_id'];
			$status="ok";
			$responseData=array(
				"user_id"=>$userId,
				"status"=>$status
			);

		}else{
			$status="invalid users";
		}
	}else{
		$status="missing field";
	}

	if($status == "ok"){
		return json_encode(array("response"=>$responseData));
	}else{
		return json_encode(array("response"=>$status));
	}

});


//----------------------------------- E-mail sign up api --------------------------------------//
$app->post('/auth/signup/email',function(Request $request,Response $response,array $args){
	require "include/db.php";
	require "include/functions.php";

	$username=$request->getParsedBody()['username'];
	$email=$request->getParsedBody()['email'];
	$password=md5(trim($request->getParsedBody()['password']));

	if(!empty($username) && !empty($email) && !empty($password)){
		$checkEmail=$db->query("select * from `users` where email='$email' and active='1'");

		if($checkEmail->rowCount() <= 0){
			$insertData=$db->query("insert into `users`(username,email,password) values('$username','$email','$password')");
			if($insertData){ 
				if(verificationUrl($db,$email) == "link updated"){
					$responseData=array(
						"status"=>"ok",
						"user_id"=>$db->lastInsertId()
					);
					$status="ok";	
				}else{
					$status="verification url is not send";
				}
			}else{
				$status="error insert data";
			}
		}else{
			$status="email already exists";
		}
	}else{
		$status="missing field";
	}

	if($status == "ok"){
		return json_encode(array("response"=>$responseData));
	}else{
		return json_encode(array("response"=>$status));
	}
});


//----------------------------------- forget password api --------------------------------------//
$app->get('/auth/forgetpassword/{email}',function(Request $request,Response $response,array $args){
	require "include/db.php";
	require "include/functions.php";
	$email=$args['email'];

	if(!empty($email)){
		$checkEmailQuery=$db->query("select * from `users` where email='$email' and active=1");
		if($checkEmailQuery->rowCount() == 1){
			if(verificationUrl($db,$email) == "link updated"){
				$status="ok";
			}else{
				$status="email not send";
			}
		}else{
			$status="email not exits";
		}
	}else{
		$status="email missing";
	}

	return json_encode(array("response"=>$status));
});

//----------------------------------- change password api --------------------------------------//
$app->post('/auth/changepassword',function(Request $request,Response $response,array $args){
	require "include/db.php";

	$oldPassword=md5(trim($request->getParsedBody()['old_password']));
	$newPassword=md5(trim($request->getParsedBody()['new_password']));
	$userId=$request->getParsedBody()['user_id'];

	if(!empty($oldPassword) && !empty($newPassword) && !empty($userId)){
		$checkQuery=$db->query("select * from `users` where user_id='$userId' and password='$oldPassword'");
		if($checkQuery->rowCount() == 1){
			$updatePassword=$db->query("update `users` set password='$newPassword' where user_id='$userId'");
			if($updatePassword){
				$status="ok";
			}else{
				$status="update error";
			}
		}else{
			$status="password not match";
		}
	}else{
		$status="missing fields";
	}

	return json_encode(array("response"=>$status));
});

$app->run();