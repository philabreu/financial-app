CREATE TABLE lancamento (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL,
	data_vencimento DATE NOT NULL,
	data_pagamento DATE,
	valor DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(100),
	tipo_lancamento VARCHAR(20) NOT NULL,
	categoria_id BIGINT(20) NOT NULL,
	pessoa_id BIGINT(20) NOT NULL,
	FOREIGN KEY (categoria_id) REFERENCES categoria(id),
	FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Salário mensal', '2017-06-10', null, 6500.00, 'Distribuição de lucros', 'RECEITA', 1, 1);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Supermercado', '2017-02-10', '2017-02-10', 100.32, null, 'DESPESA', 2, 2);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Academia', '2017-06-10', null, 120, null, 'DESPESA', 3, 3);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Conta de luz', '2017-02-10', '2017-02-10', 110.44, null, 'DESPESA', 4, 4);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Publicidade', '2017-06-10', null, 200.30, null, 'RECEITA', 5, 5);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Restaurante', '2017-03-10', '2017-03-10', 1010.32, null, 'DESPESA', 5, 6);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Venda vídeo game', '2017-06-10', null, 500, null, 'RECEITA', 4, 7);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Clube', '2017-03-10', '2017-03-10', 400.32, null, 'DESPESA', 3, 8);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Impostos', '2017-06-10', null, 123.64, 'Multas', 'DESPESA', 2, 9);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Multa', '2017-04-10', '2017-04-10', 665.33, null, 'DESPESA', 1, 10);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Lucro ações', '2017-06-10', null, 8.32, null, 'RECEITA', 3, 9);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Papelaria', '2017-04-10', '2017-04-10', 2100.32, null, 'DESPESA', 5, 8);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Almoço', '2017-06-10', null, 1040.32, null, 'DESPESA', 2, 7);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Café', '2017-04-10', '2017-04-10', 4.32, null, 'DESPESA', 2, 6);
INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo_lancamento, categoria_id, pessoa_id) values ('Dividendos', '2017-06-10', null, 10.20, null, 'RECEITA', 2, 5);