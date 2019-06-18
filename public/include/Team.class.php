<?php
class Team{
	public $db;
	public $id;

	function __construct($db,$id){
		$this->db=$db;
		$this->id=$id;
	}

	function result(){
		$query=$this->db->query("select * from `debate_team` where debate_team_id='".$this->id."'");
		$result=$query->fetch();
		return $result;
	}

	function getName(){
		$result=$this->result();
		if(empty($result['name'])){
			return "";
		}else{	
			return $result['name'];
		}
	}

	function getImageUrl(){
		$result=$this->result();
		if(empty($result['image_url'])){
			return "";
		}else{	
			return $result['image_url'];
		}
	}

	function getTotalScore(){
		$result=$this->result();
		if(empty($result['total_score'])){
			return "";
		}else{	
			return $result['total_score'];
		}
	}

	function getAdminId(){
		$result=$this->result();
		if(empty($result['admin_id'])){
			return "";
		}else{	
			return $result['admin_id'];
		}
	}
}
?>