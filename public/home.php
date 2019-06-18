<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require '../vendor/autoload.php';

$app = new \Slim\App;

$app->get('/home/main/{userId}', function (Request $request, Response $response, array $args) {
    $userId = $args['userId'];

    if(!empty($userId)){
    	require "include/db.php";
    	require "include/functions.php";
    	require "include/Team.class.php";

    	//------------------------- user details 
    	$query=$db->query("select * from `users` where user_id='$userId'");
    	$result=$query->fetch();
    	$userDetails=array(
    		"user_id"=>$result['user_id'],
    		"username"=>$result['username'],
    		"profile_picture"=>"http://192.168.137.1/debate/uploads/profile/".$result['profile_picture'],
    		"level"=>"levels".$result['level'],
    		"badges"=>$result['badges'],
    		"email"=>$result['email'],
    		"total_debates"=>$result['total_debates']
    	);


    	// ----------------------- for top debates list
    	$query=$db->query("select * from `debate` where category_id='1' limit 5");
    	$result=$query->fetchAll(PDO::FETCH_OBJ);
	
		$topDebates=array();
		foreach($result as $item){
			//team a and b obj creation for fetching there data
			$teamA=new Team($db,$item->team_a_id);
			$teamB=new Team($db,$item->team_b_id);
			
			array_push($topDebates,array(
				"id"=>$item->debate_id,
				"title"=>$item->title,
				"time"=>$item->start_time,
				"team_a_name"=>$teamA->getName(),
				"team_a_image"=>"http://192.168.137.1/debate/dashboard/".$teamA->getImageUrl(),
				"team_a_score"=>$teamA->getTotalScore(),
				"team_b_name"=>$teamB->getName(),
				"team_b_image"=>"http://192.168.137.1/debate/dashboard/".$teamB->getImageUrl(),
				"team_b_score"=>$teamB->getTotalScore()
			));
		}

		// ----------------------- for social list
    	$query=$db->query("select * from `debate` where category_id='2' limit 5");
    	$result=$query->fetchAll(PDO::FETCH_OBJ);
	
		$social=array();
		foreach($result as $item){
			//team a and b obj creation for fetching there data
			$teamA=new Team($db,$item->team_a_id);
			$teamB=new Team($db,$item->team_b_id);
			
			array_push($social,array(
				"id"=>$item->debate_id,
				"title"=>$item->title,
				"time"=>$item->start_time,
				"team_a_name"=>$teamA->getName(),
				"team_a_image"=>"http://192.168.137.1/debate/dashboard/".$teamA->getImageUrl(),
				"team_a_score"=>$teamA->getTotalScore(),
				"team_b_name"=>$teamB->getName(),
				"team_b_image"=>"http://192.168.137.1/debate/dashboard/".$teamB->getImageUrl(),
				"team_b_score"=>$teamB->getTotalScore()
			));
		}

		// ----------------------- for news list
    	$query=$db->query("select * from `debate` where category_id='3' limit 5");
    	$result=$query->fetchAll(PDO::FETCH_OBJ);
	
		$news=array();
		foreach($result as $item){
			//team a and b obj creation for fetching there data
			$teamA=new Team($db,$item->team_a_id);
			$teamB=new Team($db,$item->team_b_id);
			
			array_push($news,array(
				"id"=>$item->debate_id,
				"title"=>$item->title,
				"time"=>$item->start_time,
				"team_a_name"=>$teamA->getName(),
				"team_a_image"=>"http://192.168.137.1/debate/dashboard/".$teamA->getImageUrl(),
				"team_a_score"=>$teamA->getTotalScore(),
				"team_b_name"=>$teamB->getName(),
				"team_b_image"=>"http://192.168.137.1/debate/dashboard/".$teamB->getImageUrl(),
				"team_b_score"=>$teamB->getTotalScore()
			));
		}

		// ----------------------- for politices list
    	$query=$db->query("select * from `debate` where category_id='4' limit 5");
    	$result=$query->fetchAll(PDO::FETCH_OBJ);
	
		$politices=array();
		foreach($result as $item){
			//team a and b obj creation for fetching there data
			$teamA=new Team($db,$item->team_a_id);
			$teamB=new Team($db,$item->team_b_id);
			
			array_push($politices,array(
				"id"=>$item->debate_id,
				"title"=>$item->title,
				"time"=>$item->start_time,
				"team_a_name"=>$teamA->getName(),
				"team_a_image"=>"http://192.168.137.1/debate/dashboard/".$teamA->getImageUrl(),
				"team_a_score"=>$teamA->getTotalScore(),
				"team_b_name"=>$teamB->getName(),
				"team_b_image"=>"http://192.168.137.1/debate/dashboard/".$teamB->getImageUrl(),
				"team_b_score"=>$teamB->getTotalScore()
			));
		}

		// ----------------------- for sports list
    	$query=$db->query("select * from `debate` where category_id='4' limit 5");
    	$result=$query->fetchAll(PDO::FETCH_OBJ);
	
		$sports=array();
		foreach($result as $item){
			//team a and b obj creation for fetching there data
			$teamA=new Team($db,$item->team_a_id);
			$teamB=new Team($db,$item->team_b_id);
			
			array_push($sports,array(
				"id"=>$item->debate_id,
				"title"=>$item->title,
				"time"=>$item->start_time,
				"team_a_name"=>$teamA->getName(),
				"team_a_image"=>"http://192.168.137.1/debate/dashboard/".$teamA->getImageUrl(),
				"team_a_score"=>$teamA->getTotalScore(),
				"team_b_name"=>$teamB->getName(),
				"team_b_image"=>"http://192.168.137.1/debate/dashboard/".$teamB->getImageUrl(),
				"team_b_score"=>$teamB->getTotalScore()
			));
		}
		
		$responseData=array(
			"user_details"=>$userDetails,
			"top_debates"=>$topDebates,
			"social"=>$social,
			"politices"=>$politices,
			"sports"=>$sports,
			"news"=>$news,
		);
    }else{
    	echo "asd";
    }
    return json_encode(array("response"=>$responseData));
});


//----------------------------------- debate list on see all btn ----------------------- //
$app->post('/home/category/debate/list',function(Request $request,Response $response,array $args){
	
	require "include/db.php";
	require "include/Team.class.php";
	require "include/functions.php";

	$lastId=$request->getParsedBody()['last_id'];
	$category=$request->getParsedBody()['keyword'];

	if(!empty($category) && $lastId != ""){
		$categoryId=trim(getCategoryId($db,$category));

		if($lastId == "0"){
			// $query=$db->query("select * from `debate` where category_id='$categoryId' order by desc limit 5");
			//TODO: append tthis in query 
			#where category_id='$categoryId'
			
			$query=$db->query("select * from `debate` order by debate_id desc limit 5");
		}else{
			$query=$db->query("select * from `debate` where debate_id < $lastId order by debate_id desc limit 5");
		}
    	$result=$query->fetchAll(PDO::FETCH_OBJ);
	
		$responseData=array();
		foreach($result as $item){
			//team a and b obj creation for fetching there data
			$teamA=new Team($db,$item->team_a_id);
			$teamB=new Team($db,$item->team_b_id);
			
			array_push($responseData,array(
				"id"=>$item->debate_id,
				"title"=>$item->title,
				"time"=>$item->start_time,
				"team_a_name"=>$teamA->getName(),
				"team_a_image"=>"http://192.168.137.1/debate/dashboard/".$teamA->getImageUrl(),
				"team_a_score"=>$teamA->getTotalScore(),
				"team_b_name"=>$teamB->getName(),
				"team_b_image"=>"http://192.168.137.1/debate/dashboard/".$teamB->getImageUrl(),
				"team_b_score"=>$teamB->getTotalScore()
			));
		}

	}else{
			$responseData=array(
				"id"=>"none",
				"title"=>"none",
				"time"=>"none",
				"team_a_name"=>"none",
				"team_a_image"=>"none",
				"team_a_score"=>"none",
				"team_b_name"=>"none",
				"team_b_image"=>"none",
				"team_b_score"=>"none"
			);
	}

	return json_encode(array("response"=>$responseData));
});

$app->run();