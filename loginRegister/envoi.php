<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['numExp']) && isset($_POST['numRec']) && isset($_POST['montant'])&& isset($_POST['codes'])) {
    if ($db->dbConnect()) {
        if ($db->Send("user", $_POST['numExp'], $_POST['numRec'], $_POST['montant'], $_POST['codes'])) {
            echo "Envoi reussi";
        } else echo "Echec de l'envoi";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
