<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require '../vendor/autoload.php';

$app = new \Slim\App;


//----------------------------------- add to circle --------------------------------------//
$app->post("/action/squard/add",function(Request $request,Response $response,array $args){
	$userId=$request->getParsedBody()['user_id'];
	$squardUserId=$request->getParsedBody()['squard_user_id'];
	$time="23456789";

	require "include/db.php";

	if($userId != "" && $squardUserId != ""){
		$query=$db->query("select * from `squard` where user_id='$userId' and squard_user_id='$squardUserId'");

		if($query->rowCount() == 0){
			$addQuery=$db->query("insert into `squard`(user_id,squard_user_id,time_joined) values('$userId','$squardUserId','$time')");
			if($addQuery){
				$status="ok";
			}else{
				$status="not insert";
			}
		}else{
			$status="already added";
		}
	}else{
		echo '{"status":"error"}';
	}

	return json_encode(array("status"=>$status));
});

//----------------------------------- debate join button api ---------------------------------//
$app->post('/debate/join/button',function(Request $request,Response $response,array $arg){
	$debateId=$request->getParsedBody()['debate_id'];
	$userId=$request->getParsedBody()['user_id'];

	require "include/db.php";

	if($debateId != "" && $userId != ""){
		$query=$db->query("select * from `debate_active_users` where user_id='$userId' and debate_id='$debateId' ");
		
		if($query->rowCount() != 0){
			$result=$query->fetch();
			$responseData=array(
				"status"=>"1",
				"debate_team_id"=>$result['debate_team_id']
			);
		}else{
			$responseData=array(
				"status"=>"0",
				"debate_team_id"=>"0"
			);
		}

	}else{
		$responseData=array(
			"status"=>"missing fields",
			"debate_team_id"=>"0"
		);
	}

	return json_encode(array("response"=>$responseData));

});

//-------------------------------------- insert join btn -------------------------------------//
$app->post('/debate/join/select/team',function(Request $request,Response $response,array $args){
	$debateId=$request->getParsedBody()['debate_id'];
	$userId=$request->getParsedBody()['user_id'];
	$debateTeamId=$request->getParsedBody()['debate_team_id'];

	require "include/db.php";
	
	if($debateId != "" && $userId != "" && $debateTeamId != ""){
		$query=$db->query("insert into `debate_active_users`(user_id,debate_id,debate_team_id) values('$userId','$debateId','$debateTeamId')");
		if($query){
			$responseData=array(
				"status"=>"ok"
			);
		}else{
			$responseData=array(
				"status"=>"not ok"
			);
		}
	}else{
		$responseData=array(
			"status"=>"missing fields"
		);
	}

	return json_encode(array("response"=>$responseData));

});

//----------------------------------- debate chat exit -----------------------------------//
$app->post('/debate/chat/exit',function(Request $request,Response $response,array $args){
	$debateId=$request->getParsedBody()['debate_id'];
	$userId=$request->getParsedBody()['user_id'];

	require "include/db.php";
	if($debateId != "" && $userId != ""){
		$deleteQuery=$db->query("delete from `debate_active_users` where debate_id='$debateId' and user_id='$userId'");
		if($deleteQuery){
			$responseData=array(
				"status"=>"ok"
			);
		}else{
			$responseData=array(
				"status"=>"not ok"
			);
		}
	}else{
		$responseData=array(
			"status"=>"missing fields"
		);
	}

	return json_encode(array("response"=>$responseData));

});


//---------------------------- remove friend from squard ----------------------------
$app->post('/squard/remove/user',function(Request $request,Response $response,array $args){
	$userId=$request->getParsedBody()['user_id'];
	$squardUserId=$request->getParsedBody()['squard_user_id'];

	require "include/db.php";

	if($userId != "" && $squardUserId != ""){
		$query=$db->query("delete from `squard` where user_id='$userId' and  squard_user_id='$squardUserId'");
		if($query){
			$responseData=array(
				"status"=>"ok"
			);
		}else{
			$responseData=array(
				"status"=>"query error"
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
