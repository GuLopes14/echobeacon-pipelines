# 🏍️ Echo Beacon MVC - DEVOPS

O projeto **Echo Beacon** foi desenvolvido para a empresa **Mottu** com o objetivo de implementar uma solução tecnológica que melhore a organização e localização das motos no pátio da empresa; a solução integra hardware, software e banco de dados para facilitar a gestão e identificação de veículos de forma eficiente.

---

# 👔 Integrantes
* **Gustavo Lopes Santos da Silva** - RM: 556859
* **Renato de Freitas David Campiteli** - RM: 555627
* **Gabriel Santos Jablonski** - RM: 555452

## 🛠️ Descrição do Projeto

O projeto em desenvolvimento para a empresa **Mottu** visa implementar uma solução tecnológica para melhorar a organização e a localização das motos no pátio da empresa, facilitando a gestão e a identificação de cada veículo de forma mais eficiente. O sistema será composto por uma série de componentes integrados, incluindo **Arduino**, um **aplicativo móvel** e um **banco de dados centralizado**.

A solução será composta por pequenas placas eletrônicas, chamadas de **"EchoBeacon"**, que serão instaladas em cada moto. Essas placas conterão:
- Um **sistema de som** (buzzer).
- Um **LED** para sinalização visual.

Quando uma moto chega ao pátio, informações como **placa**, **chassi** e detalhes sobre qualquer problema específico do veículo serão registradas em um banco de dados integrado. Esses dados poderão ser acessados por um sistema desenvolvido em **Java**.

Além disso, os funcionários responsáveis pela organização e monitoramento das motos no pátio terão acesso a um **aplicativo móvel**, que estará conectado ao banco de dados. Através desse aplicativo, eles poderão:
- Consultar informações detalhadas sobre as motos, como **placa**, **chassi** e **problemas**.
- Ativar o **buzzer** e/ou o **LED** da moto selecionada, emitindo um som e sinal visual para facilitar sua localização, mesmo em um ambiente com várias motos.

Essa solução visa resolver o problema de localizar rapidamente as motos no pátio. Sem uma identificação clara e imediata, os funcionários enfrentam dificuldades para encontrar a moto correta entre tantas outras. Com a implementação desse sistema, a **Mottu** poderá organizar melhor suas motos e otimizar o tempo gasto na identificação e localização dos veículos dentro do pátio, garantindo uma gestão mais ágil e eficiente.

---

## 🎯 Objetivos

- **Facilitar a localização de motos no pátio da empresa.**
- **Otimizar o tempo dos funcionários na identificação de veículos.**
- **Garantir uma gestão mais eficiente e organizada.**

---

## Diagrama de fluxo da aplicação

![Diagrama de Fluxo da Aplicação](images/diagramadevops.png)

## 📽️ Link para o vídeo da explicação

- [Vídeo explicativo](https://www.youtube.com/watch?v=JHqJ7gNTkQo)

## 🚀 Tecnologias Utilizadas

- **Backend**: Java 21 com Spring Boot 3.5.5
- **Framework Web**: Spring MVC com Thymeleaf
- **Banco de Dados**: PostgreSQL com Flyway para migrations
- **Autenticação**: OAuth2 com Google
- **Containerização**: Docker
- **Cloud**: Microsoft Azure (ACR + ACI)
- **Build Tool**: Gradle
- **Testes**: JUnit 5

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas:

- **Java 21** ou superior
- **Docker** e **Docker Compose**
- **Azure CLI** (para deploy na nuvem)
- **Git** (para controle de versão)
- **Gradle** (ou use o wrapper incluído no projeto)

---

## 🛠️ Instalação e Configuração Local

### Clone o Repositório
```bash
git clone https://github.com/fiap-2tds-dtcc-fev25/2tdsa-cs-3-echo-beacon.git
cd 2tdsa-cs-3-echo-beacon
```

---

## 🌐 Deploy na Azure

### Pré-requisitos para Deploy
1. **Azure CLI** instalado e configurado
2. **Docker** instalado
3. **Credenciais Google OAuth2** configuradas
4. **Subscription** do Azure ativa

### 1. Configurar Variáveis de Deploy
Edite o arquivo `scripts/deploy.sh` de acordo com que te mandei no **privado do teams** e configure:
```bash
# Substitua pelos seus valores
GOOGLE_CLIENT_ID=seu_google_client_id_aqui
GOOGLE_CLIENT_SECRET=seu_google_client_secret_aqui
ADMIN_EMAIL=seu_email@gmail.com
```

### 2. Build e Push da Imagem Docker

#### Usar Script Automatizado (Recomendado)
```bash
# Dar permissão de execução (Linux/Mac)
chmod +x scripts/build.sh
chmod +x scripts/deploy.sh

# Executar build
./scripts/build.sh
```

### 3. Deploy da Aplicação
```bash
# Executar deploy completo
./scripts/deploy.sh
```

### 4. Verificar Deploy
```bash
# Listar containers
az container list --resource-group rg-cp4-rm556859 --output table

# Ver logs da aplicação
az container logs --resource-group rg-cp4-rm556859 --name aci-app-cp4-rm556859

# Ver logs do banco
az container logs --resource-group rg-cp4-rm556859 --name aci-db-cp4-rm556859
```

### 5. Acessar Aplicação Deployada
- **URL**: `http://aci-app-cp4-rm556859.eastus.azurecontainer.io:8080`

## 🧪 Como Testar a Aplicação

### 1. **Login e Autenticação**
1. Acesse `http://localhost:8080`
2. Clique em **"Login"**
3. Será redirecionado para o Google OAuth2
4. Faça login com sua conta Google
5. Após login bem-sucedido, será redirecionado para a página inicial

### 2. **Testando como Usuário COMUM (USER)**

#### ✅ **Funcionalidades Disponíveis:**
- **Visualizar motos** - Listar todas as motos cadastradas
- **Cadastrar nova moto** - Adicionar moto ao sistema
- **Cadastrar EchoBeacon** - Adicionar dispositivo EchoBeacon
- **Vincular EchoBeacon à moto** - Associar dispositivo a uma moto

#### ❌ **Funcionalidades BLOQUEADAS:**
- **Editar moto** - Botão não aparece
- **Excluir moto** - Botão não aparece
- **Editar EchoBeacon** - Acesso negado

### 3. **Testando como Usuário ADMIN**

#### ✅ **Funcionalidades Adicionais:**
- **Editar moto** - Modificar dados de motos existentes
- **Excluir moto** - Remover motos do sistema
- **Gerenciar EchoBeacons** - Controle total sobre dispositivos

### 4. **Validações e Casos de Erro**

#### 🚫 **Teste de Validações:**
```bash
1. Tente cadastrar moto com placa inválida (ex: "123")
2. Tente cadastrar moto com chassi muito curto
3. Deixe campos obrigatórios em branco
4. Tente cadastrar moto com placa já existente
5. Verifique as mensagens de erro exibidas
```

#### 🔒 **Teste de Segurança:**
```bash
1. Como usuário comum, tente acessar diretamente:
   - http://localhost:8080/motos/editar/1
   - http://localhost:8080/motos/excluir/1
2. Deve ser redirecionado ou receber erro 403 (Forbidden)
```

---

## 🗑️ Limpeza de Recursos

### Remover Recursos da Azure
```bash
# Usar script de limpeza
chmod +x scripts/deleterg.sh
./scripts/deleterg.sh

# Ou manualmente
az group delete --name rg-cp4-rm556859 --yes --no-wait
```

---

## 🧩 Pipelines CI/CD (Azure DevOps)

Este repositório contém um pipeline YAML completo em `azure-pipelines.yml` que implementa CI/CD automatizado:

### 📊 Visão Geral do Pipeline

**CI (Continuous Integration):**
- Build com Gradle (task oficial `Gradle@4`)
- Execução automática de testes JUnit
- Publicação de resultados de testes no Azure DevOps
- Publicação do artefato JAR no Azure DevOps
- Build e push da imagem Docker para o Azure Container Registry (ACR)

**CD (Continuous Deployment):**
- Deploy automático no Azure Container Instances (ACI)
- Execução condicional (só roda se CI passar com sucesso)
- Provisionamento de Postgres e aplicação Spring Boot

---

### 1️⃣ Pré-requisitos no Azure DevOps

#### Service Connection do Azure
Crie uma **Azure Resource Manager Service Connection** com acesso à sua subscription:
1. Vá em **Project Settings** > **Service Connections** > **New service connection**
2. Selecione **Azure Resource Manager**
3. Use **Service Principal (automatic)**
4. Configure o acesso ao Resource Group ou Subscription
5. Nomeie como: `echobeacon-az` (ou ajuste a variável `azureServiceConnection` no YAML)

#### Variáveis Secretas do Pipeline
Configure as seguintes variáveis no pipeline (marque como **secret**):

| Variável | Tipo | Descrição | Exemplo |
|----------|------|-----------|---------|
| `GOOGLE_CLIENT_ID` | Secret | ID do cliente OAuth2 Google | `123456789-abc.apps.googleusercontent.com` |
| `GOOGLE_CLIENT_SECRET` | Secret | Secret do cliente OAuth2 Google | `GOCSPX-xxxxxxxxxxxxx` |
| `ADMIN_EMAILS` | Secret | E-mails dos administradores | `admin@gmail.com,admin2@gmail.com` |

#### Variáveis Opcionais (já têm valores padrão no YAML)
- `rm`: `556859` (número do RM)
- `resourceGroup`: `rg-echobeacon-rm$(rm)`
- `acrName`: `acrechobeaconrm$(rm)`
- `dockerImageName`: `appcp4`
- `dockerImageTag`: `latest`

---

### 2️⃣ Conectar o Pipeline ao GitHub

1. No Azure DevOps, vá em **Pipelines** > **New pipeline**
2. Selecione **GitHub** como fonte
3. Selecione este repositório: `GuLopes14/echobeacon-pipelines`
4. Escolha **Existing Azure Pipelines YAML file**
5. Selecione `azure-pipelines.yml`
6. **Save** ou **Run**

---

### 3️⃣ Fluxo de Execução do Pipeline

#### Trigger
- Dispara automaticamente a cada **push** ou **pull request** na branch `main`

#### Stage 1: CI (Continuous Integration)
```yaml
Job: Build_Test_Publish
├── Checkout do código
├── Instalar JDK 21 (JavaToolInstaller@0)
├── Build com Gradle@4 (clean build)
│   └── Executa testes automaticamente
├── Publicar resultados de testes JUnit
├── Publicar JAR como artefato (build/libs)
└── Build e Push da imagem Docker
    └── Executa scripts/build.sh via AzureCLI@2
    └── Cria Resource Group + ACR (se não existir)
    └── Push da imagem: acrechobeaconrm$(rm).azurecr.io/appcp4:latest
```

#### Stage 2: CD (Continuous Deployment)
```yaml
Job: Deploy_ACI (dependsOn: CI)
├── Checkout do código
└── Deploy no Azure Container Instances
    └── Executa scripts/deploy.sh via AzureCLI@2
    └── Provisiona container PostgreSQL
    └── Provisiona container da aplicação Spring Boot
    └── Configura variáveis de ambiente (DB, OAuth2)
```

---

### 4️⃣ Estrutura de Arquivos do Pipeline

```
📁 Raiz do Projeto
├── azure-pipelines.yml        # Pipeline CI/CD principal
├── scripts/
│   ├── build.sh               # Script de build e push Docker
│   ├── deploy.sh              # Script de deploy no ACI
│   └── deleterg.sh            # Script de limpeza de recursos
├── gradlew                    # Gradle Wrapper
└── build.gradle               # Configuração do Gradle
```

---

### 5️⃣ Variáveis do Pipeline (Referência Completa)

#### Variáveis de Infraestrutura
```yaml
azureServiceConnection: 'echobeacon-az'  # Nome da service connection
vmImage: 'ubuntu-latest'                 # Imagem do agente
rm: '556859'                             # RM do representante
resourceGroup: 'rg-echobeacon-rm556859'  # Nome do resource group
acrName: 'acrechobeaconrm556859'         # Nome do ACR
```

#### Variáveis de Aplicação
```yaml
dockerImageName: 'appcp4'                # Nome da imagem Docker
dockerImageTag: 'latest'                 # Tag da imagem
artifactName: 'drop'                     # Nome do artefato publicado
```

#### Variáveis Secretas (configurar na UI)
- `GOOGLE_CLIENT_ID`
- `GOOGLE_CLIENT_SECRET`
- `ADMIN_EMAILS`

---

### 6️⃣ Monitoramento e Logs

#### Visualizar Execução do Pipeline
1. Acesse **Pipelines** no Azure DevOps
2. Clique no pipeline **echobeacon-pipelines**
3. Visualize os stages CI e CD em tempo real

#### Verificar Artefatos Publicados
1. Na execução do pipeline, vá em **Published** (topo da página)
2. Baixe o artefato `drop` contendo o JAR

#### Logs dos Containers no Azure
```bash
# Ver logs da aplicação
az container logs --resource-group rg-echobeacon-rm556859 \
                  --name aci-app-echobeacon-rm556859

# Ver logs do PostgreSQL
az container logs --resource-group rg-echobeacon-rm556859 \
                  --name aci-db-echobeacon-rm556859
```

---

### 7️⃣ URLs Importantes

Após o deploy bem-sucedido:

- **Aplicação**: `http://aci-app-echobeacon-rm556859.eastus.azurecontainer.io:8080`
- **Health Check**: `http://aci-app-echobeacon-rm556859.eastus.azurecontainer.io:8080/actuator/health`
- **Database**: `aci-db-echobeacon-rm556859.eastus.azurecontainer.io:5432`

---

### 8️⃣ Troubleshooting

#### Pipeline falha no JavaToolInstaller
- Certifique-se de que a task `JavaToolInstaller@0` está disponível
- Ou use uma imagem de agente com JDK 21 pré-instalado

#### Erro de Service Connection
```
Error: No Azure subscription found
```
**Solução**: Verifique se a service connection `echobeacon-az` existe e tem permissões adequadas.

#### Erro ao Push da Imagem Docker
```
Error: unauthorized: authentication required
```
**Solução**: O script `build.sh` faz `az acr login`. Certifique-se de que a service connection tem acesso ao ACR.

#### Container não inicia no ACI
```bash
# Ver status do container
az container show --resource-group rg-echobeacon-rm556859 \
                  --name aci-app-echobeacon-rm556859 \
                  --query instanceView.state

# Ver eventos do container
az container show --resource-group rg-echobeacon-rm556859 \
                  --name aci-app-echobeacon-rm556859 \
                  --query instanceView.events
```

---

### 9️⃣ Melhorias Futuras Sugeridas

- [ ] Adicionar cache do Gradle para builds mais rápidos
- [ ] Implementar versionamento de imagens por `Build.BuildId`
- [ ] Adicionar stage de homologação (staging environment)
- [ ] Implementar health checks automatizados pós-deploy
- [ ] Adicionar notificações (Slack/Teams) em caso de falha
- [ ] Implementar rollback automático em caso de falha no deploy

---

### 🔟 Segurança do Pipeline

- ✅ Credenciais armazenadas como secrets no Azure DevOps
- ✅ Service Principal com permissões mínimas necessárias
- ✅ Autenticação via Azure CLI (sem exposição de tokens)
- ✅ Imagens Docker escaneadas antes do deploy (opcional: adicionar Azure Defender)
- ❌ **Não commitar** valores sensíveis no repositório

---

##  Estrutura do Banco de Dados

- O banco de dados PostgreSQL está hospedado na Azure Container Instance (ACI) e pode ser acessado com as seguintes credenciais:
```
psql -h aci-db-echobeacon-rm556859.eastus.azurecontainer.io  -p 5432 -U echobeacon -d echobeacon
```
O projeto utiliza **Flyway** para gerenciar as migrations do banco de dados:

### Migrations Incluídas:
1. **V1__create_echo_beacon.sql** - Tabela de dispositivos EchoBeacon
2. **V2__create_moto.sql** - Tabela de motos
3. **V3__create_echobeacon_user.sql** - Tabela de usuários
4. **V4__insert_initial_echo_beacon.sql** - Dados iniciais de EchoBeacons
5. **V5__insert_initial_moto.sql** - Dados iniciais de motos


## 🛡️ Segurança

### Configurações de Segurança Implementadas:
- **OAuth2** com Google para autenticação
- **ADMIN_EMAILS** para controle de acesso administrativo
- **HTTPS** recomendado para produção
- **Variáveis de ambiente** para credenciais sensíveis

### Boas Práticas:
1. Nunca commitar credenciais nos arquivos de código
2. Usar variáveis de ambiente para configurações sensíveis
3. Configurar HTTPS em produção
4. Manter as dependências atualizadas

---