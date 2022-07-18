<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['numtel']) && isset($_POST['code']) ) {
    if ($db->dbConnect()) {
      $db->consulterSolde("user",$_POST['numtel'], $_POST['code']);
       
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>