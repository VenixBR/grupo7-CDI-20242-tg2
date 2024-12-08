CREATE TABLE Biblioteca (
  Cod_Biblioteca INT PRIMARY KEY AUTO_INCREMENT,
  Endereco VARCHAR(100) NOT NULL,
  Nome VARCHAR(100) NOT NULL,
  Sigla VARCHAR(10) NOT NULL
);

INSERT INTO Biblioteca (Cod_Biblioteca, Endereco, Nome)
VALUES 
  (0, 'Street 1', 'Biblioteca Central'),
  (1, 'Street 2', 'Biblioteca Setorial do Centro de Tecnologia'),
  (2, 'Street 3', 'Biblioteca Setorial do Centro de Ciências Rurais'),
  (3, 'Street 4', 'Biblioteca Setorial do Centro de Ciências Sociais e Humanas'),
  (4, 'Street 5', 'Biblioteca Setorial do Centro de Artes e Letras'),
  (5, 'Street 6', 'Biblioteca Setorial do Centro de Ciências Naturais e Exatas'),
  (6, 'Street 7', 'Biblioteca Setorial do Centro do Colégio Técnico Industrial de Santa Maria'),
  (7, 'Street 8', 'Biblioteca Setorial do Centro de Educação Física e Desporto'),
  (8, 'Street 9', 'Biblioteca Setorial do Centro de Educação'),
  (9, 'Street 10', 'Biblioteca Setorial do Centro do Colégio Politécnico'),
  (10, 'Street 11', 'Biblioteca Setorial do Centro de Saúde');



CREATE TABLE Centro (
  Cod_Centro INT PRIMARY KEY AUTO_INCREMENT,
  Sigla VARCHAR(10) UNIQUE NOT NULL,
  Nome VARCHAR(50) NOT NULL
);

INSERT INTO Centro (Cod_Centro, Sigla, Nome)
VALUES 
  (1, 'CT', 'Centro de Tecnologia'),
  (2, 'CCR', 'Centro de Ciências Rurais'),
  (3, 'CCSH', 'Centro de Ciências Sociais e Humanas'),
  (4, 'CAL', 'Centro de Artes e Letras'),
  (5, 'CCNE', 'Centro de Ciências Naturais e Exatas'),
  (6, 'CTISM', 'Colégio Técnico Industrial de Santa Maria'),
  (7, 'CEFD', 'Centro de Educação Física e Desporto'),
  (8, 'CE', 'Centro de Educação'),
  (9, 'POLI', 'Colégio Politécnico'),
  (10, 'CCS', 'Centro de Ciências da Saúde');


CREATE TABLE Pertence (
  fk_Cod_Biblioteca INT NOT NULL,
  fk_Cod_Centro INT NOT NULL,
  FOREIGN KEY (fk_Cod_Biblioteca) REFERENCES Biblioteca(Cod_Biblioteca),
  FOREIGN KEY (fk_Cod_Centro) REFERENCES Centro(Cod_Centro)
);

INSERT INTO Pertence (fk_Cod_Biblioteca, fk_Cod_Centro)
VALUES
  (1, 1),
  (2, 2),
  (3, 3);


CREATE TABLE Aluno (
  Matricula INT PRIMARY KEY AUTO_INCREMENT,
  Endereco VARCHAR(100) NOT NULL,
  Nome VARCHAR(50) NOT NULL,
  fk_Cod_Centro INT NOT NULL,
  FOREIGN KEY (fk_Cod_Centro) REFERENCES Centro(Cod_centro)
);

INSERT INTO Aluno (Matricula, Endereco, Nome, fk_Cod_Centro)
VALUES
  (202220000, 'Avenida Augusta', 'José Mourinho', 1),
  (202220001, 'Avenida Paulista', 'Carlos Ancelotti', 2),
  (202220002, 'Rua das Flores', 'Pep Guardiola', 3),
  (202220003, 'Rua do Sol', 'Roger Machado', 4),
  (202220004, 'Avenida Brasil', 'Zinedine Zidane', 5),
  (202220005, 'Rua da Liberdade', 'Diego Simeone', 6),
  (202220006, 'Rua do Porto', 'Renato Gaúcho', 7),
  (202220007, 'Rua Rio de Janeiro', 'Antonio Conte', 8),
  (202220008, 'Avenida São João', 'Arsène Wenger', 9),
  (202220009, 'Rua do Carmo', 'Frank Lampard', 10),
  (202220010, 'Rua das Palmeiras', 'David Moyes', 1),
  (202220011, 'Avenida dos Eucaliptos', 'Claudio Ranieri', 2),
  (202220012, 'Rua da Paz', 'Marcelo Bielsa', 3),
  (202220013, 'Rua do Parque', 'Tite', 4),
  (202220014, 'Avenida do Mar', 'Luis Enrique', 5);



CREATE TABLE Funcionario (
  Cod_Funcionario INT PRIMARY KEY AUTO_INCREMENT,
  Salario DECIMAL(10,2) NOT NULL,
  Nome VARCHAR(50) NOT NULL
);

INSERT INTO Funcionario (Cod_Funcionario, Salario, Nome)
VALUES
  (1, '9398.98', 'Lionel Messi'),
  (2, '10000.00', 'Cristiano Ronaldo'),
  (3, '9500.50', 'Neymar Jr.'),
  (4, '8700.75', 'Kylian Mbappé'),
  (5, '11000.00', 'Luka Modrić'),
  (6, '9900.20', 'Sergio Ramos'),
  (7, '9600.35', 'Zlatan Ibrahimović'),
  (8, '9200.60', 'Kevin De Bruyne'),
  (9, '9400.80', 'Mohamed Salah'),
  (10, '10500.90', 'Robert Lewandowski'),
  (11, '9800.40', 'Virgil van Dijk'),
  (12, '9150.30', 'Harry Kane'),
  (13, '8800.45', 'Alan Patrick'),
  (14, '9250.00', 'Erling Haaland'),
  (15, '10050.60', 'Enner Valência');


CREATE TABLE Autor (
  Cod_Autor INT PRIMARY KEY AUTO_INCREMENT,
  Nome VARCHAR(50) NOT NULL,
  Pais VARCHAR(50) NOT NULL
);

INSERT INTO Autor (Cod_Autor, Nome, Pais)
VALUES
  (1, 'J.K. Rowling', 'Reino Unido'),
  (2, 'George Orwell', 'Reino Unido'),
  (3, 'Haruki Murakami', 'Japão'),
  (4, 'Gabriel García Márquez', 'Colômbia'),
  (5, 'Leo Tolstoy', 'Rússia'),
  (6, 'Franz Kafka', 'Áustria'),
  (7, 'Jane Austen', 'Reino Unido'),
  (8, 'Mark Twain', 'Estados Unidos'),
  (9, 'Chimamanda Ngozi Adichie', 'Nigéria'),
  (10, 'Ernest Hemingway', 'Estados Unidos');



CREATE TABLE Publicacao (
  Cod_Publicacao INT PRIMARY KEY AUTO_INCREMENT,
  Tipo VARCHAR(50) NULL,
  Ano DATE NOT NULL,
  Nome VARCHAR(100) NOT NULL,
  fk_Cod_Biblioteca INT NOT NULL, 
  FOREIGN KEY (fk_Cod_Biblioteca) REFERENCES Biblioteca(Cod_Biblioteca)
);

INSERT INTO Publicacao (Cod_Publicacao, Tipo, Ano, Nome, fk_Cod_Biblioteca)
VALUES
  (1, 'Acadêmico', '2020-01-15', 'Introdução à Ciência de Dados', 1),
  (2, 'Acadêmico', '2019-11-22', 'Matemática Avançada', 2),
  (3, 'Acadêmico', '2018-08-13', 'Fundamentos de Engenharia', 3),
  (4, 'Literatura', '2021-05-17', 'Romance Clássico', 4),
  (5, 'Autoajuda', '2022-06-19', 'O Poder do Hábito', 5),
  (6, 'Literatura', '2017-02-08', 'O Senhor dos Anéis', 6),
  (7, 'Acadêmico', '2020-07-30', 'Física para Engenheiros', 7);


CREATE TABLE Academico (
  Edicao INT NOT NULL,
  Area VARCHAR(50) NOT NULL,
  fk_Cod_Publicacao INT NOT NULL,
  FOREIGN KEY (fk_Cod_Publicacao) REFERENCES Publicacao(Cod_Publicacao)
);

INSERT INTO Academico (Edicao, Area, fk_Cod_Publicacao)
VALUES
  (1, 'Ciência de Dados', 1),
  (2, 'Matemática', 2),
  (3, 'Engenharia Elétrica', 3),
  (4, 'Romance', 4),
  (5, 'Psicologia', 5),
  (6, 'Ficção Fantástica', 6),
  (7, 'Física', 7);



CREATE TABLE Literatura (
  Genero_Textual VARCHAR(50) NOT NULL,
  fk_Cod_Publicacao INT NOT NULL,
  FOREIGN KEY (fk_Cod_Publicacao) REFERENCES Publicacao(Cod_Publicacao)
);

INSERT INTO Literatura (Genero_Textual, fk_Cod_Publicacao)
VALUES
  ('Ficção Científica', 4),
  ('Romance', 6),
  ('Aventura', 7);



CREATE TABLE Autoajuda (
  Assunto VARCHAR(50) NOT NULL,
  fk_Cod_Publicacao INT NOT NULL,
  FOREIGN KEY (fk_Cod_Publicacao) REFERENCES Publicacao(Cod_Publicacao)
);

INSERT INTO Autoajuda (Assunto, fk_Cod_Publicacao)
VALUES
  ('Desenvolvimento Pessoal', 5);



CREATE TABLE Escrito (
  fk_Cod_Publicacao INT NOT NULL, 
  fk_Cod_Autor INT NOT NULL,
  FOREIGN KEY (fk_Cod_Publicacao) REFERENCES Publicacao(Cod_Publicacao),
  FOREIGN KEY (fk_Cod_Autor) REFERENCES Autor(Cod_Autor)
);

INSERT INTO Escrito (fk_Cod_Publicacao, fk_Cod_Autor)
VALUES
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 7),
  (5, 4),
  (6, 6),
  (7, 8);


CREATE TABLE Emprestimo (
  Cod_Emprestimo INT PRIMARY KEY AUTO_INCREMENT,
  Data_ DATE NOT NULL,
  Hora TIME NOT NULL,
  fk_Cod_Publicacao INT NOT NULL,
  fk_Matricula int NOT NULL,
  fk_Cod_Funcionario INT NOT NULL,
  FOREIGN KEY (fk_Cod_Publicacao) REFERENCES Publicacao(Cod_Publicacao),
  FOREIGN KEY (fk_Matricula) REFERENCES Aluno(Matricula),
  FOREIGN KEY (fk_Cod_Funcionario) REFERENCES Funcionario(Cod_Funcionario)
);

INSERT INTO Emprestimo (Cod_Emprestimo, Data_, Hora, fk_Cod_Publicacao, fk_Matricula, fk_Cod_Funcionario)
VALUES
  (1, '2024-01-12', '14:30:00', 1, 202220000, 1),
  (2, '2024-01-13', '10:00:00', 2, 202220001, 2),
  (3, '2024-01-14', '16:15:00', 3, 202220002, 3),
  (4, '2024-02-20', '11:20:00', 4, 202220003, 4),
  (5, '2024-02-21', '14:45:00', 5, 202220004, 5),
  (6, '2024-03-05', '09:30:00', 6, 202220005, 6),
  (7, '2024-03-10', '13:00:00', 7, 202220006, 7);
