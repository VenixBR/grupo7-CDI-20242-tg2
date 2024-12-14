INSERT INTO Centro (Cod_Centro, Sigla, Nome)
VALUES 
  (01, 'CT', 'Centro de Tecnologia'),
  (02, 'CCR', 'Centro de Ciências Rurais'),
  (03, 'CCSH', 'Centro de Ciências Sociais e Humanas'),
  (04, 'CAL', 'Centro de Artes e Letras'),
  (05, 'CCNE', 'Centro de Ciências Naturais e Exatas'),
  (06, 'CTISM', 'Colégio Técnico Industrial de Santa Maria'),
  (07, 'CEFD', 'Centro de Educação Física e Desporto'),
  (08, 'CE', 'Centro de Educação'),
  (09, 'POLI', 'Colégio Politécnico'),
  (10, 'CCS', 'Centro de Ciências da Saúde');

INSERT INTO Biblioteca (Cod_Biblioteca, Endereco, Nome, Sigla)
VALUES 
  (01, 'Street 1', 'Biblioteca Central', 'BC'),
  (02, 'Street 2', 'Biblioteca Setorial do Centro de Tecnologia', 'BSCT'),
  (03, 'Street 3', 'Biblioteca Setorial do Centro de Ciências Rurais', 'BSCCR'),
  (04, 'Street 4', 'Biblioteca Setorial do Centro de Ciências Sociais e Humanas', 'BSCCSH'),
  (05, 'Street 5', 'Biblioteca Setorial do Centro de Artes e Letras', 'BSCAL'),
  (06, 'Street 6', 'Biblioteca Setorial do Centro de Ciências Naturais e Exatas', 'BSCCNE'),
  (07, 'Street 7', 'Biblioteca Setorial do Centro do Colégio Técnico Industrial de Santa Maria', 'BSCTISM'),
  (08, 'Street 8', 'Biblioteca Setorial do Centro de Educação Física e Desporto', 'BSCEFD'),
  (09, 'Street 9', 'Biblioteca Setorial do Centro de Educação', 'BSCE'),
  (10, 'Street 10', 'Biblioteca Setorial do Centro do Colégio Politécnico', 'BSPOLI'),
  (11, 'Street 11', 'Biblioteca Setorial do Centro de Ciências da Saúde', 'BSCCS');

INSERT INTO Pertence (fk_Cod_Biblioteca, fk_Cod_Centro)
VALUES
  (01, 01),
  (02, 02),
  (03, 03);

INSERT INTO Aluno (Matricula, Endereco, Nome, fk_Cod_Centro)
VALUES
  (202220000, 'Avenida Augusta', 'José Mourinho', 01),
  (202220001, 'Avenida Paulista', 'Carlos Ancelotti', 02),
  (202220002, 'Rua das Flores', 'Pep Guardiola', 03),
  (202220003, 'Rua do Sol', 'Roger Machado', 04),
  (202220004, 'Avenida Brasil', 'Zinedine Zidane', 05),
  (202220005, 'Rua da Liberdade', 'Diego Simeone', 03),
  (202220006, 'Rua do Porto', 'Renato Gaúcho', 07),
  (202220007, 'Rua Rio de Janeiro', 'Antonio Conte', 08),
  (202220008, 'Avenida São João', 'Arsène Wenger', 03),
  (202220009, 'Rua do Carmo', 'Frank Lampard', 10),
  (202220010, 'Rua das Palmeiras', 'David Moyes', 06),
  (202220011, 'Avenida dos Eucaliptos', 'Claudio Ranieri', 02),
  (202220012, 'Rua da Paz', 'Marcelo Bielsa', 03),
  (202220013, 'Rua do Parque', 'Tite', 04),
  (202220014, 'Avenida do Mar', 'Luis Enrique', 05);

INSERT INTO Funcionario (Cod_Funcionario, Salario, Nome)
VALUES
  (01, '9398.98', 'Lionel Messi'),
  (02, '10000.00', 'Cristiano Ronaldo'),
  (03, '9500.50', 'Neymar Jr.'),
  (04, '8700.75', 'Kylian Mbappé'),
  (05, '11000.00', 'Luka Modrić'),
  (06, '9900.20', 'Sergio Ramos'),
  (07, '9600.35', 'Zlatan Ibrahimović'),
  (08, '9200.60', 'Kevin De Bruyne'),
  (09, '9400.80', 'Mohamed Salah'),
  (10, '10500.90', 'Robert Lewandowski'),
  (11, '9800.40', 'Virgil van Dijk'),
  (12, '9150.30', 'Harry Kane'),
  (13, '8800.45', 'Alan Patrick'),
  (14, '9250.00', 'Erling Haaland'),
  (15, '10050.60', 'Enner Valência');

INSERT INTO Autor (Cod_Autor, Nome, Pais)
VALUES
  (01, 'J.K. Rowling', 'Reino Unido'),
  (02, 'George Orwell', 'Reino Unido'),
  (03, 'Clarice Lispector', 'Brasil'),
  (04, 'Gabriel García Márquez', 'Colômbia'),
  (05, 'Leo Tolstoy', 'Rússia'),
  (06, 'Carlos Drummond', 'Brasil'),
  (07, 'Jane Austen', 'Reino Unido'),
  (08, 'Mark Twain', 'Estados Unidos'),
  (09, 'Machado de Assis', 'Brasil'),
  (10, 'Ernest Hemingway', 'Estados Unidos');

INSERT INTO Publicacao (Cod_Publicacao, Tipo, Ano, Nome, fk_Cod_Biblioteca)
VALUES
  (01, 'Acadêmico', '2020-01-15', 'Introdução à Ciência de Dados', 01),
  (02, 'Acadêmico', '2019-11-22', 'Matemática Avançada', 02),
  (03, 'Acadêmico', '2018-08-13', 'Fundamentos de Engenharia', 03),
  (04, 'Literatura', '2021-05-17', 'Romance Clássico', 04),
  (05, 'Autoajuda', '2022-06-19', 'O Poder do Hábito', 05),
  (06, 'Literatura', '2017-02-08', 'O Senhor dos Anéis', 06),
  (07, 'Acadêmico', '2020-07-30', 'Física para Engenheiros', 07);

INSERT INTO Academico (Edicao, Area, fk_Cod_Publicacao)
VALUES
  (01, 'Ciência de Dados', 01),
  (02, 'Matemática', 02),
  (03, 'Engenharia Elétrica', 03),
  (04, 'Romance', 04),
  (05, 'Psicologia', 05),
  (06, 'Ficção Fantástica', 06),
  (07, 'Física', 07);

INSERT INTO Literatura (Genero_Textual, fk_Cod_Publicacao)
VALUES
  ('Ficção Científica', 04),
  ('Romance', 06),
  ('Aventura', 07);

INSERT INTO Autoajuda (Assunto, fk_Cod_Publicacao)
VALUES
  ('Desenvolvimento Pessoal', 5);

INSERT INTO Escrito (fk_Cod_Publicacao, fk_Cod_Autor)
VALUES
  (01, 01),
  (02, 02),
  (03, 03),
  (04, 07),
  (05, 04),
  (06, 06),
  (07, 08);


INSERT INTO Emprestimo (Cod_Emprestimo, Data_, Hora, fk_Cod_Publicacao, fk_Matricula, fk_Cod_Funcionario)
VALUES
  (01, '2024-01-12', '14:30:00', 1, 202220000, 01),
  (02, '2024-01-13', '10:00:00', 2, 202220001, 02),
  (03, '2024-01-14', '16:15:00', 3, 202220002, 03),
  (04, '2024-02-20', '11:20:00', 4, 202220003, 04),
  (05, '2024-02-21', '14:45:00', 5, 202220004, 05),
  (06, '2024-03-05', '09:30:00', 6, 202220005, 06),
  (07, '2024-03-10', '13:00:00', 7, 202220006, 07);
