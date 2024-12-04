CREATE TABLE Biblioteca (
  Cod_Biblioteca INT PRIMARY KEY AUTO_INCREMENT,
  Endereco VARCHAR(100) NOT NULL,
  Nome VARCHAR(100) NOT NULL,
  Sigla VARCHAR(10) NOT NULL
);

CREATE TABLE Centro (
  Cod_Centro INT PRIMARY KEY AUTO_INCREMENT,
  Sigla VARCHAR(10) UNIQUE NOT NULL,
  Nome VARCHAR(50) NOT NULL
);

CREATE TABLE Pertence (
  fk_Cod_Biblioteca INT NOT NULL,
  fk_Cod_Centro INT NOT NULL,
  FOREIGN KEY (fk_Cod_Biblioteca) REFERENCES Biblioteca(Cod_Biblioteca),
  FOREIGN KEY (fk_Cod_Centro) REFERENCES Centro(Cod_Centro)
);

CREATE TABLE Aluno (
  Matricula INT PRIMARY KEY AUTO_INCREMENT,
  Endereco VARCHAR(100) NOT NULL,
  Nome VARCHAR(50) NOT NULL,
  fk_Cod_Centro INT NOT NULL,
  FOREIGN KEY (fk_Cod_Centro) REFERENCES Centro(Cod_centro)
);

CREATE TABLE Funcionario (
  Cod_Funcionario INT PRIMARY KEY AUTO_INCREMENT,
  Salario DECIMAL(10,2) NOT NULL,
  Nome VARCHAR(50) NOT NULL
);

CREATE TABLE Autor (
  Cod_Autor INT PRIMARY KEY AUTO_INCREMENT,
  Nome VARCHAR(50) NOT NULL,
  Pais VARCHAR(50) NOT NULL
);

CREATE TABLE Publicacao (
  Cod_Publicacao INT PRIMARY KEY AUTO_INCREMENT,
  Tipo VARCHAR(50) NULL,
  Ano DATE NOT NULL,
  Nome VARCHAR(100) NOT NULL,
  fk_Cod_Biblioteca INT NOT NULL, 
  FOREIGN KEY (fk_Cod_Biblioteca) REFERENCES Biblioteca(Cod_Biblioteca)
);

CREATE TABLE Academico (
  Edicao INT NOT NULL,
  Area VARCHAR(50) NOT NULL,
  fk_Cod_Publicacao INT NOT NULL,
  FOREIGN KEY (fk_Cod_Publicacao) REFERENCES Publicacao(Cod_Publicacao)
);

CREATE TABLE Literatura (
  Genero_Textual VARCHAR(50) NOT NULL,
  fk_Cod_Publicacao INT NOT NULL,
  FOREIGN KEY (fk_Cod_Publicacao) REFERENCES Publicacao(Cod_Publicacao)
);

CREATE TABLE Autoajuda (
  Assunto VARCHAR(50) NOT NULL,
  fk_Cod_Publicacao INT NOT NULL,
  FOREIGN KEY (fk_Cod_Publicacao) REFERENCES Publicacao(Cod_Publicacao)
);

CREATE TABLE Escrito (
  fk_Cod_Publicacao INT NOT NULL, 
  fk_Cod_Autor INT NOT NULL,
  FOREIGN KEY (fk_Cod_Publicacao) REFERENCES Publicacao(Cod_Publicacao),
  FOREIGN KEY (fk_Cod_Autor) REFERENCES Autor(Cod_Autor)
);

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
