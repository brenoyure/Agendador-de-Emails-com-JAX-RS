CREATE DATABASE IF NOT EXISTS agendamentoemailbd;

USE agendamentoemailbd;

CREATE TABLE IF NOT EXISTS agendamentoemail (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assunto VARCHAR(255) NOT NULL,
    mensagem VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    enviado BOOLEAN NOT NULL
);
