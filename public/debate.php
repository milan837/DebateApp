<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require '../vendor/autoload.php';

$app = new \Slim\App;

//-----------------------------------  login api --------------------------------------//

$app->post('/debate/profile',function(Request $request,Response $response,array $args){
	
	$debateId=$request->getParsedBody()['debate_id'];
	$paginationId=$request->getParsedBody()['pagination_id'];

	if($debateId != "" && $paginationId !=""){

		require "include/db.php";
		require "include/functions.php";
		require "include/Team.class.php";

		$query=$db->query("select * from `debate` where debate_id='$debateId'");
		if($query){
			$result=$query->fetch(); 

			//team a and b obj creation for fetching there data
			$teamA=new Team($db,$result['team_a_id']);
			$teamB=new Team($db,$result['team_b_id']);

			$debateInfo=array(				
				"id"=>$result['debate_id'],
				"title"=>$result['title'],
				"hash_tags"=>$result['hash_tags'],
				"time"=>$result['start_time'],
				"team_a_id"=>$result['team_a_id'],
				"team_a_name"=>$teamA->getName(),
				"team_a_total_users"=>getTotalDebateTeamUsers($db,$result['team_a_id']),
				"team_b_id"=>$result['team_b_id'],
				"team_b_name"=>$teamB->getName(),
				"team_b_total_users"=>getTotalDebateTeamUsers($db,$result['team_b_id']),
			);

			//user class
			require "include/User.class.php";

			//--------------------------- team A response
			$teamAUsers=array();

			// $teamAQuery=$db->query("select * from `debate_team_users` where debate_team_id='1' order by debate_team_user_id desc");

			if($paginationId == "0"){
				$teamAQuery=$db->query("select * from `users` order by user_id desc limit 5");
			}else{
				$teamAQuery=$db->query("select * from `users` where user_id < $paginationId order by user_id desc limit 5");
			}


			$teamAResult=$teamAQuery->fetchAll(PDO::FETCH_OBJ);
			foreach($teamAResult as $item){
				$user=new User($db,$item->user_id);
				array_push($teamAUsers,array(
					"user_id"=>" ".$item->user_id,
					"username"=>" ".$user->getUsername(),
					"profile_picture"=>"http://192.168.137.1/debate/uploads/profile/".$user->getProfilePicture(),
					"badges"=>$user->getBadges(),
					"total_debates"=>$user->getTotalDebates(),
					"level"=>$user->getLevel(),
					"debate_team_id"=>" "
				));
			}

			$responseData=array(
				"debate_info"=>$debateInfo,
				"team_users"=>$teamAUsers
			);

		}else{
			echo "errror";
		}
	}else{
		echo "missing field";
	}
	return json_encode(array("response"=>$responseData));
});

//------------------------------- paginated call for team  users 
$app->post('/debate/profile/team/list',function(Request $request,Response $response,array $args){
	$teamId=$request->getParsedBody()['team_id'];
	$paginationId=$request->getParsedBody()['pagination_id'];

	require "include/db.php";
	require "include/functions.php";
	require "include/User.class.php";

	if($teamId != "" && $paginationId != ""){
		
		// if($paginationId == 0){
		// 	$query=$db->query("select * from `debate_team_users` where debate_team_id='$teamId' limit 5");
		// }else{
		// 	$query=$db->query("select * from `debate_team_users` where debate_team_id='$teamId' and debate_team_user_id < $paginationId limit 5");
		// }

		if($paginationId == "0"){
			$query=$db->query("select * from `users` order by user_id desc limit 5");
		}else{
			$query=$db->query("select * from `users` where user_id < $paginationId order by user_id desc limit 5");
		}
		
			
		$results=$query->fetchAll(PDO::FETCH_OBJ);
		$debateList=array();

		foreach ($results as $result) {
			$user=new User($db,$result->user_id);
			array_push($debateList,array(
				"user_id"=>$result->user_id,
				"username"=>$user->getUsername(),
				"profile_picture"=>"http://192.168.137.1/debate/uploads/profile/".$user->getProfilePicture(),
				"badges"=>$user->getBadges(),
				"total_debates"=>$user->getTotalDebates(),
				"level"=>$user->getLevel(),
				"debate_team_id"=>"result->debate_team_user_id"
			));
		}

		$responseData=array("team_user"=>$debateList);

	}else{
		echo "empty";
	}

	return json_encode(array("response"=>$responseData));

});

$app->run();