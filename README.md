# Projeto de pagamento

Este é o repositório do Projeto pagamento fiaplanches, uma aplicação incrível desenvolvida pela equipe Grupo 48. Esse projeto consiste em uma aplicação web que fornece serviços relacionados a lanchonete FiapLanches que está informatizando seus processos onde este microServiço é responsável especificamente pelo contexto de Pagamento dessa Lanchonete

## Instruções de Instalação

Para executar o projeto localmente, siga as instruções abaixo:

1. Clone este repositório em sua máquina local:

   ```shell
   git clone https://github.com/DaniTavaresSantos/pagamento-fiaplanches.git
   ```

2. Rode o comando de dockerCompose na raíz do projeto para subir os serviços e a infra relacionados a este microserviço:

   ```shell
   docker-compose up -d
   ```   

Isso iniciará os containers necessários para executar a aplicação.

## Para testar a funcionalidade da aplicação, basta utilizar a collection do Postman localizada na pasta: Collection-Postman, na raíz do projeto.

## Importe também o Environment também localizado na pasta: Collection-Postman.

## Ordem do caminho feliz a partir dos endpoints do postman:

## funcionamento do serviço.
Este microsserviço não possui uma API REST tradicional. Em vez disso, ele participa de uma SAGA baseada em eventos como principal forma de comunicação entre diferentes partes do sistema.

Na SAGA, o processo é iniciado por um evento gerado pelo pedido do cliente. Esse evento é então enviado para um barramento de mensagens Apache Kafka, onde outros microsserviços podem consumi-lo e realizar as ações necessárias.

## Links dos Repositórios:
https://github.com/DaniTavaresSantos/product-fiaplanches
https://github.com/DaniTavaresSantos/pedido-fiaplanches
https://github.com/DaniTavaresSantos/cozinha-fiaplanches
https://github.com/DaniTavaresSantos/conta-fiaplanches
https://github.com/dalexandrias/fiap-lanches-infra
https://github.com/DaniTavaresSantos/lambda-fiaplanches

