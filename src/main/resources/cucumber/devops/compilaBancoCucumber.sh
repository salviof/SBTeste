#!/bin/bash
source ./SBProjeto.prop
SLUG_REQUISITO=$1
mysqldump -alv -u root -psenhaDev#123  $NOME_BANCO > $SLUG_REQUISITO.cucumber.sql

