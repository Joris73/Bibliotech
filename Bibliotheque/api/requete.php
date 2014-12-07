<?php

if ((isset($_GET["requete"])) && (!empty($_GET["requete"]))) {
    $requete = filter_input(INPUT_GET, "requete", FILTER_UNSAFE_RAW);
    //echo htmlspecialchars($requete);
    try {
        include_once("connectionBD.php");
        $res = requete($requete);
        $tabLigne = array();
        while ($data = $res->fetch(PDO::FETCH_ASSOC)) { // NUM to ASSOC
            array_push($tabLigne, $data);
        }
        if (empty($tabLigne) && $cnx->lastInsertId() != 0) {
            $colonne = array();
            $colonne[] = $cnx->lastInsertId();
            array_push($tabLigne, $colonne);
        }
        print(json_encode($tabLigne));
        $res->closeCursor();
    } catch (Exception $e) {
        print(json_encode($e->getMessage()));
    }
}
?>