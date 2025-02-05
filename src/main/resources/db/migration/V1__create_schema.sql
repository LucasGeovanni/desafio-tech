CREATE TABLE revenda (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         uuid UUID NOT NULL UNIQUE,
                         cnpj VARCHAR(14) NOT NULL UNIQUE,
                         razao_social VARCHAR(255) NOT NULL,
                         nome_fantasia VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL
);

CREATE TABLE telefone (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          uuid UUID NOT NULL UNIQUE,
                          ddd VARCHAR(3) NOT NULL,
                          numero VARCHAR(15) NOT NULL,
                          tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('CELULAR', 'FIXO')),
                          revenda_id BIGINT NOT NULL,
                          FOREIGN KEY (revenda_id) REFERENCES revenda(id) ON DELETE CASCADE
);

CREATE TABLE endereco (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          uuid UUID NOT NULL UNIQUE,
                          rua VARCHAR(255) NOT NULL,
                          numero VARCHAR(10) NOT NULL,
                          bairro VARCHAR(100) NOT NULL,
                          cep VARCHAR(8) NOT NULL,
                          cidade VARCHAR(100) NOT NULL,
                          estado VARCHAR(2) NOT NULL,
                          complemento VARCHAR(255),
                          revenda_id BIGINT NOT NULL,
                          FOREIGN KEY (revenda_id) REFERENCES revenda(id) ON DELETE CASCADE
);

CREATE TABLE pedido (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        uuid UUID NOT NULL UNIQUE,
                        revenda_id BIGINT NOT NULL,
                        cliente VARCHAR(255) NOT NULL,
                        data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        status VARCHAR(50) NOT NULL CHECK (status IN ('ERRO', 'PENDENTE', 'AGUARDANDO_FORNECEDOR', 'FINALIZADO')),
                        FOREIGN KEY (revenda_id) REFERENCES revenda(id) ON DELETE CASCADE
);

CREATE TABLE controle_integracao (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     uuid UUID NOT NULL UNIQUE,
                                     data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     status VARCHAR(50) NOT NULL CHECK (status IN ('CONCLUIDO', 'PENDENTE', 'AGUARDAR_RECEBIMENTO', 'ERRO', 'REPROCESSAR')),
                                     quantidade_tentativas INT DEFAULT 0,
                                     revenda_id BIGINT NOT NULL,
                                     CONSTRAINT fk_controle_integracao_revenda FOREIGN KEY (revenda_id) REFERENCES revenda(id) ON DELETE CASCADE
);

CREATE TABLE controle_integracao_pedido (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 uuid UUID NOT NULL UNIQUE,
                                 controle_integracao_id BIGINT NOT NULL,
                                 pedido_id BIGINT NOT NULL,
                                 CONSTRAINT fk_controle_integracao_pedido FOREIGN KEY (controle_integracao_id) REFERENCES controle_integracao(id) ON DELETE CASCADE,
                                 CONSTRAINT fk_controle_integracao_pedido_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE
);

CREATE TABLE item_pedido (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             produto VARCHAR(255) NOT NULL,
                             quantidade INT NOT NULL,
                             pedido_id BIGINT NOT NULL,
                             FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE
);

INSERT INTO revenda (uuid, cnpj, razao_social, nome_fantasia, email)
VALUES ('123e4567-e89b-12d3-a456-426614174000', '12345678000195', 'Revenda Teste Ltda', 'Revenda Teste', 'contato@revendateste.com');

INSERT INTO telefone (uuid, ddd, numero, tipo, revenda_id)
VALUES ('223e4567-e89b-12d3-a456-426614174001', '11', '987654321', 'CELULAR', 1);

INSERT INTO endereco (uuid, rua, numero, bairro, cep, cidade, estado, complemento, revenda_id)
VALUES ('323e4567-e89b-12d3-a456-426614174002', 'Rua Exemplo', '100', 'Centro', '01000000', 'São Paulo', 'SP', NULL, 1);
