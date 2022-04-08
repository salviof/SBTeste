#!/bin/bash
source ./SBProjeto.prop
SLUG_REQUISITO=$1
docker exec  devopsTestes1 -w /devopsDBScript/ mysqladmin -u root -psenhaDev#123 drop $NOME_BANCO -f
docker exec  devopsTestes1 -w /devopsDBScript/ mysqladmin -u root -psenhaDev#123 create $NOME_BANCO
docker exec  devopsTestes1 /bin/bash -c 'mysql -u root '$NOME_BANCO' -psenhaDev#123 < /devopsDBScript/'$SLUG_REQUISITO'.cucumber.sql'