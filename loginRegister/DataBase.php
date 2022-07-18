<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $nom, $password)
    {
        $name = $this->prepareData($nom);
        $password = $this->prepareData($password);
        $this->sql = "select * from " . $table . " where nom = '" . $name . "' and mdp='".sha1($password)."' ";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
           $login =true;
        } else $login = false;

        return $login;
    }

    function signUp($table, $fullname, $lastname, $date_naiss, $numcni,$tel, $password)
    {
        $nom = $this->prepareData($fullname);
        $prenom = $this->prepareData($lastname);
        $mdp = $this->prepareData($password);
        $date = $this->prepareData($date_naiss);
        $num_cni = $this->prepareData($numcni);
        $numero = $this->prepareData($tel);
        $mdp = sha1($mdp);
        $this->sql =
            "INSERT INTO " . $table . " (nom, prenom, date_naissance, num_cni,tel,mdp) VALUES ('". $nom . "','" . $prenom . "','" . $date . "','" . $num_cni . "','" . $numero . "','" . $mdp . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else echo mysqli_error($this->connect);
    }

    function send ($table,$numexp, $numR, $montant, $codeS)
    {
        $telexp = $this->prepareData($numexp);
        $telr = $this->prepareData($numR);
        $mont = $this->prepareData($montant);
        $code = $this->prepareData($codeS);

        $this->sql = "select * from " . $table . " where tel = '" . $telexp . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);

        $soldeExp = $row['solde'];
        $codeexp = $row['code'];

        if($telexp == $telr){
            return false;
        }

        else if((int)$soldeExp < (int)$mont )
        {
            return false;
        } else
        {
            if((int)$codeexp = (int)$code )
            {
               $req = "select * from " . $table . " where tel = '" . $telr . "'";
                $result1 = mysqli_query($this->connect, $req);
                if (mysqli_num_rows($result1) != 0)
                {
                    $roww = mysqli_fetch_assoc($result1);
                    $solder = $roww['solde'];
                    $nsolder = (int)$solder+$mont;

                    $req2= "update " . $table . " set solde = '" . $nsolder . "' where tel = '" . $telr . "'";
                    if(mysqli_query($this->connect, $req2))
                        { $log = true;} else echo mysqli_error($this->connect);

                    $nsoldeexp = (int)$soldeExp-$mont;

                   $req3= "update " . $table . " set solde = '" . $nsoldeexp . "' where tel = '" . $telexp . "'";
                    if(mysqli_query($this->connect, $req3)){
                        $log=true;
                    }else echo mysqli_error($this->connect);

                    $log = true;
                } else return false;

            } else return false;

            return $log;
        }
       
    }

    function retrait($table, $numeroR, $montant, $codeS)
    {
        $telR = $this->prepareData($numeroR);
        $mont = $this->prepareData($montant);
        $codeR = $this->prepareData($codeS);

        $this->sql = "select * from " . $table . " where tel = '" . $telR . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);

        $soldeR = $row['solde'];
        $code = $row['code'];

        if((int)$soldeR < (int)$mont )
        {
            return false;
        } else 
        {
            if((int)$codeR = (int)$code )
            {
                $nsolde = (int)$soldeR-$mont;
                $req = "update " . $table . " set solde = '" . $nsolde . "' where tel = '" . $telR . "'";
                if(mysqli_query($this->connect, $req)){
                    $log = true;
                } else echo mysqli_error($this->connect);
                
            }else return false;

            return $log;
        }

    }

    function modifCompte($table,$numero,$prenom,$nom,$dateNaiss,$numcni,$Nmdp)
    {
        $tel = $this->prepareData($numero);
        $lastname = $this->prepareData($prenom);
        $name = $this->prepareData($nom);
        $date = $this->prepareData($dateNaiss);
        $cni = $this->prepareData($numcni);
        $mdp = $this->prepareData($Nmdp);
        $mdp = password_hash($mdp, PASSWORD_DEFAULT);

        $sql = "select * from ".$table." where tel = '".$tel."'  " ;
        $result = mysqli_query($this->connect, $sql);
        $row = mysqli_fetch_assoc($result);

        if((int)$cni = $row['num_cni'])
        {
            $this->sql = "update ".$table." set mdp = '".$mdp."' where tel = '".$tel."' ";
            
            if(mysqli_query($this->connect, $this->sql))
            {
                return true;
            } else 
            {
                echo mysqli_error($this->connect);
            }
        } else return false;

    }

    function getTel($table, $nom)
    {

        $name = $this->prepareData($nom);
        $this->sql = "select tel from ".$table." where nom = '".$name."'  ";
        $result =mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        echo "".$row['tel'];
        //echo "123";
    }

function consulterSolde($table,$numero, $code)
{
    $codeS = $this->prepareData($code);
    $tel = $this->prepareData($numero);

    $this->sql = "select solde from ".$table." where tel =".$tel." and code =".$codeS." ";
    //echo ($this->sql);
    $result = mysqli_query($this->connect, $this->sql);
    $row = mysqli_fetch_assoc($result);
    if(mysqli_num_rows($result) != 0)
    {
       echo "".$row['solde'];
       //echo "1234";
    } else echo "Mauvais code";
    
}

}

?>
