ALTER TABLE funcionario
ALTER COLUMN id
SET DEFAULT nextval('funcionario_id_seq'::regclass);

ALTER TABLE telefone
ALTER COLUMN id
SET DEFAULT nextval('telefone_id_seq'::regclass);

ALTER TABLE marca
ALTER COLUMN id
SET DEFAULT nextval('marca_id_seq'::regclass);

ALTER TABLE produto
ALTER COLUMN id
SET DEFAULT nextval('produto_id_seq'::regclass);

ALTER TABLE telefone
ADD CONSTRAINT fk_telefone_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(id);