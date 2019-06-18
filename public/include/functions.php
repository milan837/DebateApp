<?php

#functions 

//----------------------------- verification url generator ----------------------------//

function verificationUrl($db,$email,$randomNumber){ 
	$url="http://www.debate.com/verification/email=$email&code=$randomNumber\n <h1>Activation Code: $randomNumber</h1>";
	$subject="Verification code for debate.com account activation";
	//if(sendMail($db,$email,$subject,$url) == true){
		$updateLink=$db->query("update `users` set verification_code='$randomNumber' where email='$email'");
		if($updateLink){
			return "link updated";
		}else{
			return "link not updated";
		}
	// }else{
	// 	return "mail not send";
	// }
}

//----------------------------- send mail ----------------------------//
function sendMail($db,$to,$subject,$body){
	$headers = "From: milanshrestha837@gmail.com" . "\r\n" .
	"CC: ".$to;

	return mail($to,$subject,$body,$headers);
}

//----------------------------- category name ----------------------------//
function getCategoryName($db,$id){
	$query=$db->query("select * from `category` where category_id='$id'");
	if($query){
		$result=$query->fetch();
		return $result['name'];
	}else{
		return false;
	}
}

//----------------------------- category id ----------------------------//
function getCategoryId($db,$name){
	$query=$db->query("select * from `category` where name='$name'");
	if($query){
		$result=$query->fetch();
		return $result['category_id'];
	}else{
		return "no";
	}
}

//--------------------------- total user in team --------------------//
function getTotalDebateTeamUsers($db,$id){
	$query=$db->query("select count(*) from `users` where active='$id'");
	if($query){
		$result=$query->fetch();
		return $result['count(*)'];
	}else{
		return false;
	}
}

//--------------------------- total user in debate --------------------//
function getTotalDebateUsers($db,$id){
	$query=$db->query("select count(*) from `debate` where debate_id='$id'");
	if($query){
		$result=$query->fetch();
		return $result['count(*)'];
	}else{
		return false;
	}
}

//-------------------------- tota memebrs ------------------------------//
function getSquardTotalMembers($db,$id){
	$query=$db->query("select count(*) from `squard_users` where squard_id='$id'");
	if($query){
		$result=$query->fetch();
		return $result['count(*)'];
	}else{
		return false;
	}
}

