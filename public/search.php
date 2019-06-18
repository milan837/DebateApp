<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require '../vendor/autoload.php';

$app = new \Slim\App;

$app->post('/search/auto', function (Request $request, Response $response, array $args) {
    $keyword=$request->getParsedBody()['keyword'];
    $paginationId=$request->getParsedBody()['pagination_id'];

		require "include/db.php";
    	require "include/functions.php";
		require "include/User.class.php";

    if($keyword != "" && $paginationId != ""){
    
    	if($paginationId == "0"){
    		$query=$db->query("select * from `users` where username like '%".$keyword."%' order by user_id desc limit 5");
    	}else{
    		$query=$db->query("select * from `users` where username like '%".$keyword."%' and user_id < $paginationId order by user_id desc limit 5");
    	}

    	$responseData=array();

    	if($query){
    		$results=$query->fetchAll(PDO::FETCH_OBJ);
    		foreach($results as $result){
    			array_push($responseData,array(
    				"user_id"=>$result->user_id,
    				"username"=>$result->username,
    				"profile_picture"=>$result->profile_picture,
    				"badges"=>$result->badges,
    				"levels"=>$result->level,
    				"total_debates"=>$result->total_debates
    			));
    		}


    	}else{
    		echo '{"status"=>"internal error"}';
    	}
    }else{
    	echo '{"status"=>"empty fields"}';
    }

    return json_encode(array("response"=>$responseData));

});


$app->run();