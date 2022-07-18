<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['numtel']) && isset($_POST['prenom']) && isset($_POST['nom'])&& isset($_POST['dateNaiss']) && isset($_POST['numcni']) && isset($_POST['nmdp']) ) {
    if ($db->dbConnect()) {
        if ($db->modifCompte("user", $_POST['numtel'], $_POST['prenom'], $_POST['nom'], $_POST['dateNaiss'], $_POST['numcni'], $_POST['nmdp'])) {
            echo "Modification reussi";
        } else echo "Echec de la modification";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>