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
  (202220015, 'Rua das Palmeiras', 'Jürgen Klopp', 06),
  (202220016, 'Avenida dos Girassóis', 'Mauricio Pochettino', 05),
  (202220017, 'Rua do Horizonte', 'Joachim Löw', 02),
  (202220018, 'Avenida das Acácias', 'Didier Deschamps', 01),
  (202220019, 'Rua Verde', 'Julian Nagelsmann', 08),
  (202220020, 'Avenida da Amizade', 'Mano Menezes', 04),
  (202220021, 'Rua do Futuro', 'Fernando Diniz', 03),
  (202220022, 'Avenida Central', 'Abel Ferreira', 07),
  (202220023, 'Rua das Hortênsias', 'Marcelo Gallardo', 02),
  (202220024, 'Avenida das Nações', 'Rafael Benítez', 10);

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
  (16, '10100.00', 'Casemiro'),
  (17, '8800.70', 'Paul Pogba'),
  (18, '9700.90', 'Andrés Iniesta'),
  (19, '9200.55', 'Sadio Mané'),
  (20, '8900.85', 'Gerard Piqué'),
  (21, '9600.95', 'Marco Verratti'),
  (22, '9400.50', 'Jordi Alba'),
  (23, '9800.80', 'Phil Foden'),
  (24, '10200.20', 'Raheem Sterling'),
  (25, '8900.00', 'David Silva');

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
  (11, 'Edgar Allan Poe', 'Estados Unidos'),
  (12, 'Jorge Amado', 'Brasil'),
  (13, 'Fernando Pessoa', 'Portugal'),
  (14, 'Albert Camus', 'França'),
  (15, 'Haruki Murakami', 'Japão'),
  (16, 'Franz Kafka', 'Alemanha'),
  (17, 'William Shakespeare', 'Reino Unido'),
  (18, 'Virginia Woolf', 'Reino Unido'),
  (19, 'Hermann Hesse', 'Alemanha'),
  (20, 'José Saramago', 'Portugal');

INSERT INTO Publicacao (Cod_Publicacao, Tipo, Ano, Nome, fk_Cod_Biblioteca)
VALUES
  (01, 'Acadêmico', '2020', 'Introdução à Ciência de Dados', 01),
  (02, 'Acadêmico', '2019', 'Matemática Avançada', 02),
  (03, 'Acadêmico', '2018', 'Fundamentos de Engenharia', 03),
  (04, 'Literatura', '2021', 'Romance Clássico', 04),
  (05, 'Autoajuda', '2022', 'O Poder do Hábito', 05),
  (06, 'Literatura', '2017', 'O Senhor dos Anéis', 06),
  (07, 'Acadêmico', '2020', 'Física para Engenheiros', 07);
  (08, 'Literatura', '2016', '1984', 01),
  (09, 'Autoajuda', '2023', 'Como Fazer Amigos e Influenciar Pessoas', 02),
  (10, 'Acadêmico', '2021', 'Aprendizado de Máquina', 03),
  (11, 'Acadêmico', '2018', 'Cálculo Diferencial e Integral', 04),
  (12, 'Autoajuda', '2019', 'Mindset', 05),
  (13, 'Literatura', '2022', 'Dom Casmurro', 06),
  (14, 'Acadêmico', '2020', 'Estruturas de Dados', 07),
  (15, 'Acadêmico', '2017', 'Introdução à Física Moderna', 08),
  (16, 'Literatura', '2015', 'Grande Sertão: Veredas', 09),
  (17, 'Autoajuda', '2020', 'Os Sete Hábitos das Pessoas Altamente Eficazes', 10);

INSERT INTO Academico (Edicao, Area, fk_Cod_Publicacao)
VALUES
  (01, 'Ciência de Dados', 01),
  (02, 'Matemática', 02),
  (03, 'Engenharia Elétrica', 03),
  (04, 'Romance', 04),
  (05, 'Psicologia', 05),
  (06, 'Ficção Fantástica', 06),
  (07, 'Física', 07);
  (08, 'Filosofia', 10),
  (09, 'Matemática Aplicada', 11),
  (10, 'Psicologia', 12),
  (11, 'Análise de Dados', 14),
  (12, 'Física Quântica', 15);

INSERT INTO Literatura (Genero_Textual, fk_Cod_Publicacao)
VALUES
  ('Ficção Científica', 04),
  ('Romance', 06),
  ('Aventura', 07);
  ('Drama', 08),
  ('Biografia', 13),
  ('Clássico', 16);

INSERT INTO Autoajuda (Assunto, fk_Cod_Publicacao)
VALUES
  ('Desenvolvimento Pessoal', 5);
  ('Liderança', 9),
  ('Mudança de Mentalidade', 12),
  ('Produtividade', 17);

INSERT INTO Escrito (fk_Cod_Publicacao, fk_Cod_Autor)
VALUES
  (01, 01),
  (02, 02),
  (03, 03),
  (04, 07),
  (05, 04),
  (06, 06),
  (07, 08);
  (08, 02),
  (09, 15),
  (10, 03),
  (11, 06),
  (12, 01),
  (13, 09),
  (14, 18),
  (15, 17),
  (16, 09),
  (17, 04);


INSERT INTO Emprestimo (Cod_Emprestimo, Data_, Hora, fk_Cod_Publicacao, fk_Matricula, fk_Cod_Funcionario)
VALUES
  (01, '2024-01-12', '14:30:00', 1, 202220000, 01),
  (02, '2024-01-13', '10:00:00', 2, 202220001, 02),
  (03, '2024-01-14', '16:15:00', 3, 202220002, 03),
  (04, '2024-02-20', '11:20:00', 4, 202220003, 04),
  (05, '2024-02-21', '14:45:00', 5, 202220004, 05),
  (06, '2024-03-05', '09:30:00', 6, 202220005, 06),
  (07, '2024-03-10', '13:00:00', 7, 202220006, 07);
  (08, '2024-03-15', '10:00:00', 8, 202220015, 08),
  (09, '2024-03-16', '11:30:00', 9, 202220016, 09),
  (10, '2024-03-17', '12:45:00', 10, 202220017, 10),
  (11, '2024-03-18', '13:50:00', 11, 202220018, 11),
  (12, '2024-03-19', '15:00:00', 12, 202220019, 12),
  (13, '2024-03-20', '16:10:00', 13, 202220020, 13),
  (14, '2024-03-21', '17:20:00', 14, 202220021, 14),
  (15, '2024-03-22', '18:30:00', 15, 202220022, 15);
