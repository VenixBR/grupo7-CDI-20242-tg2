#ROUND(num, quantas casas) arredonda o número
#TRUNCATE(num, quantas casas) trunca

SELECT ROUND(AVG(Salario),2) AS Media_Salarial
FROM Funcionario;
