/* Write your PL/SQL query statement below */
SELECT E.NAME AS "Employee"
FROM EMPLOYEE E 
JOIN EMPLOYEE M
ON E.MANAGERID = M.ID
WHERE E.SALARY > M.SALARY