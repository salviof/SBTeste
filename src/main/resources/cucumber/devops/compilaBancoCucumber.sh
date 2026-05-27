#!/bin/bash
source ./SBProjeto.prop
SLUG_REQUISITO=$1
# =============================================
# Executa o mysqldump ignorando warnings
# mas mantendo erros reais (exit code correto)
# =============================================
docker exec devopsTestes1 bash -c '
    mysqldump -alv --force -u root -psenhaDev#123 '"${NOME_BANCO}"' \
    > "/devopsDBScript/'"${SLUG_REQUISITO}"'.cucumber.sql" 2>&1 \
    | grep -v "^mysqldump: \[Warning\]" || true
'

echo "Backup realizado com sucesso: ${SLUG_REQUISITO}.cucumber.sql"