# bookmark-application   
Aplicação responsável por armazenar os **_MEUS PRODUTOS FAVORITOS_**   
Ações possíveis através do app:   
1. Cadastro de novos usuários;
2. Autenticação;
3. Registro de favoritos;
4. Remoção de favoritos;
5. Envio de email;
6. **Os app's, profile-app, product-app e product-app-producer, possuem testes integrados à containers**

**_Refinamentos_**
A aplicação precisa de refinamento técnico:
1. Uso de tokens na senha dos usuários;
2. Ajustes na exibição da lista de favoritos;
3. Leitura da lista de favoritos do servidor após autenticação (**O backend já está pronto**);
4. Envio de email de confirmação para liberação do cadastro do usuário;
5. Ajustes em possíveis falhas de navegação;
6. Implementar contingência no serviço **Product-app*;*
7. Definição dos **Dockerfiles** para que tudo seja executado em container;
8. Definição dos **Pods** para que os containers sejam orquestrados por **Kubernetes**;

# Profile-app   
Serviço responsável pela gestão dos usuários. Nele estão concentradas as operações:
1. Recuperar todos os **clientes**;
2. Salvar novos **clientes**;
3. Validar clientes por **email e senha**;
4. Atualzar dados de **clientes já cadastrados**;
5. Excluir um **cliente**;
6. Recuperar um cliente por **email**
7. Recuperar um cliente por **chave identificadora**

Serviço utiliza uma base de dados **_postgres_**. Antes de colocar o serviço no ar, é necessário subir o **postgres**. Execute o comando para colocar o serviço no ar:
```
docker run --rm  --name customer-db \
-p 5432:5432 \
-e POSTGRES_DB=profile-db \
-e POSTGRES_USER=sa \
-e POSTGRES_PASSWORD=123 \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-v /home/containers/customer-db/mount:/var/lib/postgresql/data \
-d postgres
```

# Product-app   
Possui apenas um path, responsável por consultar produtos favoritos por **cliente**;
A api possui uma integração com a **fakeapi products** através de **open-feign** presente no pacote **spring-cloud**. Utiliza **resilience4j** para **fallbacks**;   
O configurador cria uma tabela de **contingência dos produtos**, caso a **API deixe de responder**;
Serviço utiliza uma base de dados **_postgres_**. Antes de colocar o serviço no ar, é necessário subir o **postgres**. Execute o comando para colocar o serviço no ar:
```
docker run --rm  --name product-db \
-p 5433:5432 \
-e POSTGRES_DB=product-db \
-e POSTGRES_USER=sa \
-e POSTGRES_PASSWORD=456 \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-v /home/containers/product-db/mount:/var/lib/postgresql/data \
-d postgres
```
