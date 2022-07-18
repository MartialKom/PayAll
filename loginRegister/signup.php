<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['nom']) && isset($_POST['prenom']) && isset($_POST['date_naiss'])&& isset($_POST['num_cni']) && isset($_POST['tel']) && isset($_POST['mot_de_passe'])) {
    if ($db->dbConnect()) {
        if ($db->signUp("user", $_POST['nom'], $_POST['prenom'], $_POST['date_naiss'], $_POST['num_cni'], $_POST['tel'], $_POST['mot_de_passe'])) {
            echo "Sign Up Success";
        } else echo "Sign up Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
