CREATE TABLE Biblioteca (
  Cod_Biblioteca INT PRIMARY KEY,
  Endereco VARCHAR(100),
  Nome VARCHAR(50)
);

CREATE TABLE Centro (
  Cod_Centro INT PRIMARY KEY,
  Sigla VARCHAR(10),
  Nome VARCHAR(50)
);

CREATE TABLE Pertence (
  fk_Cod_Biblioteca INT,
  fk_Cod_Centro INT,
  FOREIGN KEY (fk_Cod_Biblioteca) REFERENCES Biblioteca(Cod_Biblioteca),
  FOREIGN KEY (fk_Cod_Centro) REFERENCES Centro(Cod_Centro)
);

CREATE TABLE Aluno (
  Matricula INT PRIMARY KEY,
  Endereco VARCHAR(100),
  Nome VARCHAR(50),
  fk_Cod_Centro INT,
  FOREIGN KEY (fk_Cod_Centro) REFERENCES Centro(Cod_centro)
);

CREATE TABLE Funcionario (
  Cod_Funcionario INT PRIMARY KEY,
  Salario DECIMAL(10,2),
  Nome VARCHAR(50)
);

CREATE TABLE Autor (
  Cod_Autor INT PRIMARY KEY,
  Nome VARCHAR(50),
  Pais VARCHAR(50)
);

CREATE TABLE Publicacao (
  Cod_Publicacao INT PRIMARY KEY,
  Tipo VARCHAR(50) NULL,
  Ano DATE,
  fk_Cod_Biblioteca INT, 
  FOREIGN KEY (fk_Cod_Biblioteca) REFERENCES Biblioteca(Cod_Biblioteca)
);

CREATE TABLE Academico (
  Edicao INT,
  Area VARCHAR(50),
  fk_Cod_Publicacao INT,
  FOREIGN KEY (fk_Cod_Publicacao) REFERENCES Publicacao(Cod_Publicacao)
);

CREATE TABLE Literatura (
  Genero_Textual VARCHAR(50),
  fk_Cod_Publicacao INT,
  FOREIGN KEY (fk_Cod_Publicacao) REFERENCES Publicacao(Cod_Publicacao)
);

CREATE TABLE Autoajuda (
  Assunto VARCHAR(50),
  fk_Cod_Publicacao INT,
  FOREIGN KEY (fk_Cod_Publicacao) REFERENCES Publicacao(Cod_Publicacao)
);

CREATE TABLE Escrito (
  fk_Cod_Publicacao INT, 
  fk_Cod_Autor INT,
  FOREIGN KEY (fk_Cod_Publicacao) REFERENCES Publicacao(Cod_Publicacao),
  FOREIGN KEY (fk_Cod_Autor) REFERENCES Autor(Cod_Autor)
);

CREATE TABLE Emprestimo (
  Cod_emprestimo INT PRIMARY KEY,
  Data_ DATE,
  Hora TIME,
  fk_Cod_Publicaca INT,
  fk_Matricula int,
  fk_Cod_Funcionario INT,
  FOREIGN KEY (fk_Cod_Publicaca) REFERENCES Publicacao(Cod_Publicacao),
  FOREIGN KEY (fk_Matricula) REFERENCES Aluno(Matricula),
  FOREIGN KEY (fk_Cod_Funcionario) REFERENCES Funcionario(Cod_Funcionario)
);
