#!/bin/bash
source ./SBProjeto.prop
SLUG_REQUISITO=$1
mysqladmin -u root -psenhaDev#123 drop $NOME_BANCO -f
mysqladmin -u root -psenhaDev#123 create $NOME_BANCO
mysql -u root $NOME_BANCO -psenhaDev#123 < ./$SLUG_REQUISITO.cucumber.sql