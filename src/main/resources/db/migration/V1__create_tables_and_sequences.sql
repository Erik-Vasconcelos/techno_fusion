CREATE TABLE IF NOT EXISTS funcionario(
    id INTEGER PRIMARY KEY,
    nome CHARACTER VARYING(100) NOT NULL,
    sexo CHAR(1) NOT NULL,
    data_nascimento DATE NOT NULL,
    email CHARACTER VARYING(100) UNIQUE NOT NULL,
    salario NUMERIC(10, 2) NOT NULL,
    imagem TEXT,
    perfil CHARACTER VARYING(30) NOT NULL,
    login CHARACTER VARYING(30) UNIQUE NOT NULL,
    senha CHARACTER VARYING(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS telefone (
    id INTEGER PRIMARY KEY,
    numero CHARACTER VARYING(20) NOT NULL,
    funcionario_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS marca (
    id INTEGER PRIMARY KEY,
    nome CHARACTER VARYING(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS produto (
    id INTEGER PRIMARY KEY,
    descricao CHARACTER VARYING(255) NOT NULL,
    modelo CHARACTER VARYING(255),
    caracteristicas TEXT,
    imagem TEXT,
    valor DECIMAL(10, 2) NOT NULL,
    desconto DECIMAL(10, 2),
    marca_id INTEGER,
    cadastrador_id INTEGER,
    FOREIGN KEY (marca_id) REFERENCES marca(id),
    FOREIGN KEY (cadastrador_id) REFERENCES funcionario(id)
);

CREATE SEQUENCE IF NOT EXISTS funcionario_id_seq INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;

CREATE SEQUENCE IF NOT EXISTS telefone_id_seq INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;

CREATE SEQUENCE IF NOT EXISTS marca_id_seq INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;

CREATE SEQUENCE IF NOT EXISTS produto_id_seq INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;