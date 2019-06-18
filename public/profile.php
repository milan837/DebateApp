<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require '../vendor/autoload.php';

$app = new \Slim\App;

//----------------------------------- user profile login api --------------------------------------//

$app->post('/user/profile',function(Request $request,Response $response,array $args){
	$userId=$request->getParsedBody()['user_id'];
	$paginationId=$request->getParsedBody()['pagination_id'];

	require "include/db.php";
	require "include/User.class.php";
	require "include/functions.php";
	require "include/Team.class.php";

	if($userId != "" && $paginationId != ""){
		$query=$db->query("select * from `users` where user_id='$userId'");
		if($query){
			$results=$query->fetchAll(PDO::FETCH_OBJ);
			foreach($results as $result){
				$userData=array(
					"user_id"=>$result->user_id,
					"username"=>$result->username,
					"badges"=>$result->badges,
					"level"=>$result->level,
					"winner"=>"262",
					"profile_picture"=>"http://192.168.137.1/debate/uploads/profile/".$result->profile_picture
				);
			}
		}

		//--------------------- debate list of praticular users		
		$debatesData=array();
		if($paginationId == "0"){
			$query=$db->query("select * from `debate` order by debate_id desc limit 5");
		}else{
			$query=$db->query("select * from `debate` where debate_id < $paginationId order by debate_id desc limit 5");
		}
    	// $query=$db->query("select * from `debate` where debate_id in (select DISTINCT debate_id from debate_team,debate_team_users where debate_team_users.user_id='$userId' and debate_team_users.debate_team_id=debate_team.debate_id)");

     	$results=$query->fetchAll(PDO::FETCH_OBJ);
     	foreach($results as $result){

			//team a and b obj creation for fetching there data
			$teamA=new Team($db,$result->team_a_id);
			$teamB=new Team($db,$result->team_b_id);
			
			array_push($debatesData,array(
				"id"=>$result->debate_id,
				"title"=>$result->title,
				"time"=>$result->start_time,
				"team_a_name"=>$teamA->getName(),
				"team_a_image"=>"http://192.168.137.1/debate/dashboard/".$teamA->getImageUrl(),
				"team_a_score"=>$teamA->getTotalScore(),
				"team_b_name"=>$teamB->getName(),
				"team_b_image"=>"http://192.168.137.1/debate/dashboard/".$teamB->getImageUrl(),
				"team_b_score"=>$teamB->getTotalScore()
			));
		}

		// //-------- mysquard list
		// $squardData=array();
		// $squardQuery=$db->query("select * from `squard`
		// 	where squard.user_id='$userId'");
		
		// $squardResult=$squardQuery->fetchAll(PDO::FETCH_OBJ);
		// foreach($squardResult as $item){
		// 	array_push($squardData,array(
		// 		"id"=>$item->squard_id,
		// 		"name"=>$item->name,
		// 		"total_members"=>getSquardTotalMembers($db,$item->squard_id),
		// 		"description"=>$item->description,
		// 		"hash_tags"=>$item->hash_tags
		// 	));
		// }

	}else{
		echo "user id is not mention";
	}

	$responseData=array(
		"user_details"=>$userData,
		"debates"=>$debatesData
	);

	return json_encode(array("response"=>$responseData));
});

//------------------------------ user squard user list ------------------------------------//
$app->post('/user/profile/my_squard',function(Request $request,Response $response,array $args){

	 $paginationId=$request->getParsedBody()['pagination_id'];
	 $userId=$request->getParsedBody()['user_id'];

	require "include/db.php";
	require "include/functions.php";
	require "include/User.class.php";

	if($userId != "" & $paginationId != ""){
		
		//-------- mysquard list
		$squardData=array();
		if($paginationId == 0){
			$squardQuery=$db->query("select * from `squard` where user_id='$userId' order by squard_user_id desc limit 10");
		}else{
			$squardQuery=$db->query("select * from `squard` where user_id='$userId' and squard_user_id < $paginationId order by squard_user_id desc limit 10");
		}
		
		$squardResult=$squardQuery->fetchAll(PDO::FETCH_OBJ);
		foreach ($squardResult as $result) {
			$user=new User($db,$result->squard_user_id);
			array_push($squardData,array(
				"user_id"=>$result->squard_user_id,
				"username"=>$user->getUsername(),
				"profile_picture"=>"http://192.168.137.1/debate/uploads/profile/".$user->getProfilePicture(),
				"badges"=>$user->getBadges(),
				"total_debates"=>$user->getTotalDebates(),
				"level"=>$user->getLevel()
			));
		}

	}else{
		echo "empty";
	}

	return json_encode(array("response"=>$squardData));

});

$app->run();

?>