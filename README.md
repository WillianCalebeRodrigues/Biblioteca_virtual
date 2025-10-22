Biblioteca Virtual
Este projeto é uma aplicação de gerenciamento de biblioteca virtual desenvolvida utilizando Quarkus, um framework Java otimizado para microsserviços e ambientes nativos de nuvem. A aplicação permite o cadastro e gerenciamento de livros, autores e empréstimos, com uma interface web construída com Jakarta Faces (JSF) e componentes PrimeFaces.
Tecnologias Utilizadas
Java 21
Quarkus 3.28.4
Maven
Hibernate ORM com Panache (para JPA)
PostgreSQL (driver JDBC)
Jakarta Faces (JSF)
PrimeFaces 13.0.8
Estrutura do Projeto
O projeto segue a estrutura padrão de um projeto Quarkus Maven:
src/main/java/br/upf/entity: Contém as classes de entidade JPA (Autor, Livro, Emprestimo).
src/main/java/br/upf/service: Contém as classes de serviço com a lógica de negócio.
src/main/resources/META-INF/resources: Contém os arquivos XHTML para a interface JSF.
pom.xml: Arquivo de configuração do Maven, definindo dependências e plugins.
Configuração do Banco de Dados
O projeto está configurado para utilizar PostgreSQL. As configurações do banco de dados devem ser definidas no arquivo src/main/resources/application.properties. Exemplo:
properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=seu_usuario
quarkus.datasource.password=sua_senha
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/biblioteca_db
quarkus.hibernate-orm.database.generation=drop-and-create


Como Executar o Projeto
Pré-requisitos
Java Development Kit (JDK) 21 ou superior.
Maven (gerenciador de projetos).
PostgreSQL (servidor de banco de dados) em execução, com um banco de dados chamado biblioteca_db criado e um usuário configurado conforme application.properties.
Passos de Execução
Clone o repositório (se aplicável) ou descompacte o projeto.
Navegue até o diretório raiz do projeto (biblioteca_virtual/biblioteca_virtual).
Configure o banco de dados no arquivo src/main/resources/application.properties.
Inicie a aplicação em modo de desenvolvimento Quarkus:
Bash
./mvnw quarkus:dev
A aplicação será iniciada e estará acessível em http://localhost:8080.
