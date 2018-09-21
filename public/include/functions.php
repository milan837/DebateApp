<?php

#functions 

//----------------------------- verification url generator ----------------------------//

function verificationUrl($db,$email){ 
	$randomNumber=rand(1000,99999);
	echo $url="http://www.debate.com/verification/email=$email&code=$randomNumber";
	$subject="Verification link for debate.com account activation";
	$body="hello dear";
	if(sendMail($db,$email,$subject,$body) == true){
		$updateLink=$db->query("update `users` set verification_url='$url' where email='$email'");
		if($update){
			return "link updated";
		}else{
			return "link not updated";
		}
	}else{
		return "mail not send";
	}
}

//----------------------------- send mail ----------------------------//
function sendMail($db,$to,$subject,$body){
	$headers = "From: milanshrestha837@gmail.com" . "\r\n" .
	"CC: ".$to;

	return mail($to,$subject,$body,$headers);
}