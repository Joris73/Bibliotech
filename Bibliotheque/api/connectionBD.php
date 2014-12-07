<?php

$host = 'localhost';
$login = 'root';
$mdp = '';
$bdd = 'bibliotheque';

$cnx = null;

function connection() {
    global $host, $login, $mdp, $bdd, $cnx;
    try {
        $cnx = new PDO("mysql:host=" . $host . ";dbname=" . $bdd, $login, $mdp);
        $cnx->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_SILENT);
        //$cnx->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
        requete("SET NAMES UTF8");
    } catch (Exception $e) {
        throw new Exception("Erreur de connection à la base de données");
    }
}

function requete($sql) {
    global $cnx;
    $res = $cnx->prepare($sql);
    if (!$res) {
        throw new Exception("Erreur SQL : " . implode(":", $cnx->errorInfo()));
    } else {
        if (!$res->execute(array(1)))
            throw new Exception("Erreur SQL : " . implode(":", $res->errorInfo()));
        return $res;
    }
}

connection();
?> 