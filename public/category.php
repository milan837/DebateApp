<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require '../vendor/autoload.php';

$app = new \Slim\App;

$app->post('/category/list',function(Request $request,Response $response,array $args){
	$paginationId=$request->getParsedBody()['pagination_id'];

	require "include/db.php";
	if($paginationId != ""){
		if($paginationId == 1){
			$query=$db->query("select * from `category` order by category_id limit 5");
		}else{
			$query=$db->query("select * from `category` where category_id > $paginationId order by category_id limit 5");
		}
		$responseData=array();
		$results=$query->fetchAll(PDO::FETCH_OBJ);
		foreach($results as $result){
			array_push($responseData,array(
				"category_id"=>$result->category_id,
				"name"=>$result->name,
				"image"=>$result->image_url,
				"total_debate"=>"50"
			));
		}
	}else{
		echo '{"status":"empty fields"}';
	}

	return json_encode(array("response"=>$responseData));
});


$app->run();