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
			$fetchUserData=$db->query("select user_id,username from `users` where email='$email' and password='$password'");
			$result=$fetchUserData->fetch();
			
			$userId=$result['user_id'];
			$status="ok";
			$responseData=array(
				"user_id"=>$userId,
				"username"=>$result['username'],
				"status"=>$status
			);

		}else{
			$status="invalid users";
			$responseData=array(
				"user_id"=>"none",
				"username"=>"none",
				"status"=>$status
			);
		}
	}else{
		$status="missing field";
		$responseData=array(
				"user_id"=>"none",
				"username"=>"none",
				"status"=>$status
			);
	}

		return json_encode(array("response"=>$responseData));
});

//----------------------------------- login with facebook api --------------------------------------//
$app->post('/auth/login/fb',function(Request $request,Response $response,array $args){
	require "include/db.php";
	$facebookId=$request->getParsedBody()['facebook_id'];
	$username=$request->getParsedBody()['username'];
	$imageUrl=$request->getParsedBody()['image_url'];

	if(!empty($facebookId) && !empty($username) && !empty($imageUrl)){
		$checkQuery=$db->query("select * from `users` where facebook_id='$facebookId'");
		if($checkQuery->rowCount() == 1){
			//already register
			$result=$checkQuery->fetch();
			$responseData=array(
				"user_id"=>$result['user_id'],
				"username"=>$username,
				"status"=>"ok"
			);
		}else{
			$insertQuery=$db->query("insert into `users` (username,facebook_id,profile_picture,active) values('$username','$facebookId','$imageUrl','1')");
			if($insertQuery){
				$responseData=array(
					"user_id"=>$db->lastInsertId(),
					"username"=>$username,
					"status"=>"ok"
				);
			}else{
				$responseData=array(
					"user_id"=>"none",
					"username"=>"none",
					"status"=>"server busy"
				);
			}
		}
	}else{
		$responseData=array(
			"user_id"=>"none",
			"username"=>"none",
			"status"=>"missing fields"
		);
	}

	return json_encode(array("response"=>$responseData));

});

//----------------------------------- E-mail sign up api --------------------------------------//
$app->post('/auth/signup/email',function(Request $request,Response $response,array $args){
	require "include/db.php";
	require "include/functions.php";

	$email=$request->getParsedBody()['email'];
	$password=md5(trim($request->getParsedBody()['password']));

	if(!empty($email) && !empty($password)){
		$checkEmail=$db->query("select * from `users` where email='$email' and active='1'");
		if($checkEmail->rowCount() <= 0){
			$insertData=$db->query("insert into `users`(email,password) values('$email','$password')");
			$userId=$db->lastInsertId();
			if($insertData){ 
				//$code=rand(1000,99999);
				$code="1234";
				if(verificationUrl($db,$email,$code) == "link updated"){
					$responseData=array(
						"status"=>"ok",
						"user_id"=>$userId,
						"email"=>$email,
						"code"=>$code
					);
					$status="ok";	
				}else{
					$status="verification url is not send";
					$responseData=array(
						"status"=>$status,
						"user_id"=>"none",
						"email"=>"none",
						"code"=>"none"
					);
				}
			}else{
				$status="error insert data";
				$responseData=array(
					"status"=>$status,
					"user_id"=>"none",
					"email"=>"none",
					"code"=>"none"
				);
			}
		}else{
			$status="email already exists";
			$responseData=array(
				"status"=>$status,
				"user_id"=>"none",
				"email"=>"none",
				"code"=>"none"
			);
		}
	}else{
		$status="missing field";
		$responseData=array(
			"status"=>$status,
			"user_id"=>"none",
			"email"=>"none",
			"code"=>"none"
		);
	}

	
	return json_encode(array("response"=>$responseData));
	
});

//----------------------------------- verification api --------------------------------------//
$app->post('/auth/verification/email',function(Request $request,Response $response,array $args){
	require "include/db.php";

	$email=$request->getParsedBody()['email'];
    $code=$request->getParsedBody()['code'];
    $userId=$request->getParsedBody()['user_id'];

	if(!empty($email) && !empty($code) && !empty($userId)){
		$checkQuery=$db->query("select * from `users` where email='$email' and verification_code='$code'");
		if($checkQuery->rowCount() > 0){
			$updateQuery=$db->query("update `users` set active='1' where email='$email' and verification_code='$code'");
			if($updateQuery){
				$responseData=array(
					"status"=>"ok",
					"user_id"=>$userId
				);
			}else{
				$responseData=array(
					"status"=>"verification faield",
					"user_id"=>$userId
				);
			}
		}else{
			$responseData=array(
				"status"=>"incorrect code",
				"user_id"=>$userId
			);
		}
	}else{
		$responseData=array(
			"status"=>"missing Field",
			"user_id"=>$userId
		);
	}

	return json_encode(array("response"=>$responseData));
});

//----------------------------------- save user info api --------------------------------------//
$app->post('/auth/save_info',function(Request $request,Response $response,array $args){
	require "include/db.php";
	$user_id=$request->getParsedBody()['user_id'];
	$username=$request->getParsedBody()['username'];
	$file=$_FILES['image'];

	if(!empty($user_id) && !empty($username) && !empty($file)){
		//naming file
		$imageName="profile_".rand(1000,99999)."_".$username."_".$user_id."_".$file['name'];
		$fType=explode(".",$file['type']);
		$type=end($fType);

		//validation of image file 
		if($type == "image/jpeg" || $type == "image/jpg" || $type=="image/png"){
			$uploads="../../../uploads/profile";
			if(is_dir($uploads)){
				if(move_uploaded_file($_FILES['image']['tmp_name'], $uploads."/".$imageName)){
					$updateQuery=$db->query("update `users` set username='$username',profile_picture='$imageName' where user_id='$user_id'");
					if($updateQuery){
						$responseData=array(
							"user_id"=>$user_id,
							"username"=>$username,
							"image_url"=>"http://192.168.137.1/debate/uploads/profile/".$imageName,
							"status"=>"ok"
						);
					}else{
						$responseData=array(
							"user_id"=>"none",
							"username"=>"none",
							"status"=>"cannot upload"
						);
					}
				}else{
					$responseData=array(
						"user_id"=>"none",
						"username"=>"none",
						"status"=>"faield uploaded"
					);
				}
			}else{
				$responseData=array(
					"user_id"=>"none",
					"username"=>"none",
					"status"=>"dir not found"
				);
			}
		}else{
			$responseData=array(
					"user_id"=>"none",
					"username"=>"none",
					"status"=>"file not supported"
				);
		}
	}else{
		$responseData=array(
			"user_id"=>"none",
			"username"=>"none",
			"status"=>"missing fields"
		);
	}

	return json_encode(array("response"=>$responseData));
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
				$responseData=array("status"=>"ok");
			}else{
				$responseData=array("status"=>"update_error");
			}
		}else{
			$responseData=array("status"=>"password not match");
		}
	}else{
		$responseData=array("status"=>"missing fields");
	}

	return json_encode(array("response"=>$responseData));
});

//-------------------------------- change username----------------------------------
$app->post('/auth/update/username',function(Request $request,Response $response,array $arg){
	$userId=$request->getParsedBody()['user_id'];
	$username=$request->getParsedBody()['username'];

	require "include/db.php";
	
	if($userId != ""  && $username != ""){
		$query=$db->query("update `users` set username='$username' where user_id='$userId'");
		if($query){
			$responseData=array(
				"status"=>"ok"
			);
		}else{
			$responseData=array(
				"status"=>"update failed"
			);
		}
	}else{
		$responseData=array(
			"status"=>"missing fields"
		);
	}
	
	return json_encode(array("response"=>$responseData));
});

$app->run(); 