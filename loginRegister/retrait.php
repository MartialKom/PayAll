<?php
require "DataBase.php";
$db = new DataBase();
if ( isset($_POST['numR']) && isset($_POST['montant'])&& isset($_POST['codeS'])) {
    if ($db->dbConnect()) {
        if ($db->retrait("user", $_POST['numR'], $_POST['montant'], $_POST['codeS'])) {
            echo "Retrait reussi";
        } else echo "Echec du retrait";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>