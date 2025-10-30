#!/bin/bash

# Variáveis
ACR_NAME="acrechobeacon"
RG_NAME="rg-echobeacon"
IMAGE_NAME="appechobeacon:latest"
LOCATION="eastus"

echo "=== Criando Resource Group ==="
az group create --name $RG_NAME --location $LOCATION

echo "=== Criando Azure Container Registry ==="
az acr create --resource-group $RG_NAME --name $ACR_NAME --sku Basic --location $LOCATION

echo "=== Habilitando admin user no ACR ==="
az acr update --name $ACR_NAME --admin-enabled true

# 🔐 Fazer login no ACR
echo "=== Fazendo login no ACR ==="
az acr login --name $ACR_NAME

# ⬇️⬆️ Importar imagem base do Postgres
echo "=== Importando imagem postgres:17-alpine para o ACR ==="
docker pull postgres:17-alpine
docker tag postgres:17-alpine $ACR_NAME.azurecr.io/postgres:17-alpine
docker push $ACR_NAME.azurecr.io/postgres:17-alpine

echo "✅ Imagem postgres importada com sucesso!"
