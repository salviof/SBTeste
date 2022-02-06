#!/bin/bash
source ./SBProjeto.prop
SLUG_REQUISITO=$1
docker exec devopsTestes1 /bin/bash -c 'mysqldump -alv -u root -psenhaDev#123  '$NOME_BANCO' > /devopsDBScript/'$SLUG_REQUISITO'.cucumber.sql'

