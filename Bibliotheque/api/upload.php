<?php

//file_put_contents('log.txt', print_r($_FILES, TRUE));
if ((isset($_FILES['file'])) && (isset($_POST['destination']))) {
    file_put_contents('log.txt', move_uploaded_file($_FILES['file']['tmp_name'], $_POST["destination"]));
}
?>