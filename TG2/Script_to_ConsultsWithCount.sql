#Consulta quantidade de livros cadastrados
SELECT COUNT(*) AS total_livros
 FROM Publicacao;
 
#Consulta a quantidade de Autores de um país(Brasil)
SELECT COUNT(*) AS Total_Autores
FROM Autor
WHERE Pais = 'Brasil';

#Consulta o total de alunos cadastrados em cada centro
SELECT 
    c.Cod_Centro, 
    c.Sigla, 
    COUNT(a.Matricula) AS Total_Alunos
FROM Aluno a
JOIN Centro c ON a.fk_Cod_Centro = c.Cod_Centro
GROUP BY c.Cod_Centro, c.Sigla

#Consulta a quantidade de emprestimos para cada tipo de publicação
SELECT Tipo AS Tipo_Publicacao, 
    COUNT(*) AS Total_Emprestimos
FROM Publicacao, Emprestimo e
WHERE Cod_Publicacao = e.fk_Cod_Publicacao
GROUP BY Tipo;

#Consulta a partir da matricula do aluno quantos emprestimos ele tem em cada tipo de publicacao
SELECT p.Tipo AS Tipo_Publicacao, 
    COUNT(*) AS Total_Emprestimos
FROM Publicacao p , Emprestimo e
WHERE Cod_Publicacao = e.fk_Cod_Publicacao AND e.fk_Matricula = 202220003
GROUP BY Tipo;

#Listar quantidade de livros por biblioteca
SELECT b.Nome AS Biblioteca, 
       COUNT(p.Cod_Publicacao) AS Total_Livros
FROM Biblioteca b
LEFT JOIN Publicacao p ON b.Cod_Biblioteca = p.fk_Cod_Biblioteca
GROUP BY b.Nome;
