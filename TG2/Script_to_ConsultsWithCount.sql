#Consulta quantidade de livros cadastrados
SELECT COUNT(*) AS total_livros
 FROM Publicacao;
 
#Consulta a quantidade de Autores de um país(Brasil)
SELECT COUNT(*) AS Total_Autores
FROM Autor
WHERE Pais = 'Brasil';

#Consulta o total de alunos cadastrados em cada centro
SELECT fk_Cod_Centro AS Cod_Centro, 
	COUNT(*) AS Total_Alunos
FROM Aluno
GROUP BY fk_Cod_Centro;

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
