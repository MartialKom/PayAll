<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['nom']) && isset($_POST['mot_de_passe'])) {
    if ($db->dbConnect()) {
        if ($db->logIn("user", $_POST['nom'], $_POST['mot_de_passe'])) {
            $db->getTel("user",$_POST['nom']);
        } else echo "Username or Password wrong";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
