<?php
class Debate{
	public $db;
	public $id;

	function __construct($db,$id){
		$this->db=$db;
		$this->id=$id;
	}

	function result(){
		$query=$this->db->query("select * from debate where user_id='$id'");
		$result=$query->fetch();
		return $result;
	}
	
	function getTitle(){
		return $this->result()['title'];
	}

	function getStartTime(){
		return $this->result()['start_time'];
	}

	function getTeamAName(){
		return $this->result()['team_a_id'];
	}

	function getTeamBName(){
		return $this->result()['team_b_id'];
	}

	function getCategoryName(){
		return $this->result()['category_id'];
	}

	function getUserId(){
		return $this->result()['user_id'];
	}

	function getHsahTags(){
		return $this->result()['hash_tags'];
	}

	function getUserLimit(){
		return $this->result()['user_limit'];
	}
} 
?>