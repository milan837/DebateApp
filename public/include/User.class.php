<?php
class User{
	public $db;
	public $id;

	function __construct($db,$id){
		$this->db=$db;
		$this->id=$id;
	}

	function result(){
		$query=$this->db->query("select * from `users` where user_id='".$this->id."'");
		$result=$query->fetch();
		return $result;
	}

	function getUsername(){
		$result=$this->result();
	return $result['username'];
	}

	function getProfilePicture(){
		$result=$this->result();
		if(empty($result['profile_picture'])){
			return "";
		}else{	
			return $result['profile_picture'];
		}
	}

	function getBadges(){
		$result=$this->result();	
	return $result['badges'];
	}

	function getTotalDebates(){
		$result=$this->result();
	return $result['total_debates'];
	}

	function getLevel(){
		$result=$this->result();
	return $result['level'];
	}
}
?>