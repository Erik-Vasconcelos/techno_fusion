# Techno Fusion - Sistema de gestão de loja de eletrônicos

Este projeto consiste em um sistema de uma loja de produtos eletrônicos desenvolvido utilizando tecnologias Java EE, Servlet, JSP e Java 17. O projeto oferece várias de funcionalidades, abrangendo a cotação de moedas em tempo real, gerenciamento de produtos, funcionários e marcas, além de recursos avançados como geração de relatórios e gráficos. No desenvolvimento, foi incluído modelagem com UML, segurança, juntamente com recursos de paginação para facilitar a navegação, além da funcionalidade de download e upload de arquivos. Destaca-se ainda a utilização da ferramenta Flyway para garantir uma gestão eficiente do banco de dados.

## :computer: Acesso ao sistema

O sistema está disponível na web através do seguinte link: **https://techno-fusion.onrender.com/app**

:warning: **OBS: Como a  hospedagem é gratuita, pode demorar para carregar o sistema**.



## 🛠️ Tecnologias e Ferramentas Utilizadas

O projeto Techno Fusion foi desenvolvido utilizando as seguintes tecnologias, ferramentas e processos:

- ``UML (Unified Modeling Language)`` 
- ``PostgreSQL`` 
- ``Java 17`` 
- ``Java EE`` 
- ``JSP``
- ``Lombok`` 
- ``Maven``
- ``JasperReport``
- ``ChartJS``
- ``AwesomeAPI(Cotação de moedas)``
- ``HTML`` 
- ``CSS (Cascading Style Sheets)`` 
- ``JavaScript`` 

## :file_folder: Diagramas

#### Caso de uso

![Diagrama de caso de uso](https://github.com/Erik-Vasconcelos/techno_fusion/blob/main/cdu-tf.png)

#### Classes de domínio

![Diagrama de classes de domínio](https://github.com/Erik-Vasconcelos/techno_fusion/blob/main/dominio-tf.png)

## 🚀 Começando

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento e teste.

**Clone o Repositório:**  Abra o terminal e execute o seguinte comando:

```git clone https://github.com/Erik-Vasconcelos/techno_fusion.git```

### 📋 Pré-requisitos

De que coisas você precisa para instalar o software e como instalá-lo?

```
Java 17
Servidor Tomcat 9 pré instalado e configurado
Lombok pré instalado
PostgreSQL
```

### 🔧 Instalação

Uma série de exemplos passo-a-passo que informam o que você deve executar para ter um ambiente de desenvolvimento em execução.

#### 1 - Crie o banco de dados

Dentro do **PgAdmin** crie uma database com o seguinte nome:

```
techno_fusion
```



#### 2 - Realize a importação do projeto para dentro de sua IDE e configure

Dentro do **src/main/resources** abra o arquivo **application.properties** e dentro dele realize as seguintes configurações:

#### Conexão com o banco de dados

**Nota:** Será realizado a configuração de conexão com o banco de dados por meio de **variáveis de ambiente**, entretanto, caso você queira manipular diretamente o código de conexão abra os seguintes arquivos e realize as alterações necessárias:

- **br/com/jdevtreinamentos/tf/infrastructure/connection/FabricaConexao.java**
- **pom.xml** (para as migrations com Flyway)



##### **Conectando**

Crie as seguintes variáveis de ambiente/sistema para realizar a conexão com o banco:

- URL de conexão:

```
nome: DATABASE_URL
valor: jdbc:postgresql://localhost:5432/techno_fusion
```

- Caso o seu banco esteja rodando em uma porta diferente da padrão (5432), mude essa propriedade na url de conexão: 

```
nome: DATABASE_URL
valor: jdbc:postgresql://localhost:<porta>/techno_fusion
```

- Usuário do banco (o padrão é 'postgres'):

```
nome: DATABASE_USERNAME
valor: <usuarios>
```

- Senha para o usuário acessar o banco:

```
nome: DATABASE_PASSWORD
valor: <senha do seu banco>
```



##### Migrations

Crie a seguinte variável de ambiente/sistema para realizar as migrations no banco de dados:

- URL de conexão com o banco para rodar as migrations(criação de tabelas e população das mesmas): 

```
nome: DATABASE_URL_MIGRATION
valor: jdbc:postgresql://localhost:5432/techno_fusion
```



#### 3  - Executando o projeto

Dentro da IDE adicione o projeto ao servidor Tomcat 9 e suba o servidor

#### Acessando o sistema

Abra o navegador e digite **localhost:8080/projeto-techno-fusion** e você poderá navegar pelo sistema.

#### Acessando a área administrativa 

 No navegador digite **localhost:8080/projeto-techno-fusion/login** e você poderá acessar com os seguintes usuários: 

**GERENTE**: 

- **login:** joao_silva
- **senha:** senha123

**FUNCIONARIO**: 

- **login:** maria_oliveira
- **senha:** senha456

Você irá acessar o painel com as funcionalidades administrativas do sistema, por exemplo, realizar uma postagem.

## :camera: Imagens

#### Página inicial do blog (Com a cotação atual das moedas)

![Página inicial](https://github.com/Erik-Vasconcelos/techno_fusion/blob/main/inicio.png)

#### Área administrativa

![Painel administrativo](https://github.com/Erik-Vasconcelos/techno_fusion/blob/main/admin.png)

##### Página de geração de gráfico

![Diagrama de caso de uso](https://github.com/Erik-Vasconcelos/techno_fusion/blob/main/grafico.png)

## ✒️ Autores

* **Erik Vasconcelos** - *Desenvolvedor* - [Linkedin](https://www.linkedin.com/in/erik-vasconcelos/)
