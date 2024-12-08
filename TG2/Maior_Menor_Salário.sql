SELECT Nome, Salario
FROM Funcionario
WHERE Salario = (SELECT MAX(Salario) FROM Funcionario)
   OR Salario = (SELECT MIN(Salario) FROM Funcionario);
