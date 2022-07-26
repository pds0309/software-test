/* Write your PL/SQL query statement below */
SELECT FIRSTNAME AS "firstName" , LASTNAME as "lastName" , CITY as "city", STATE as "state"
FROM PERSON P 
LEFT OUTER JOIN ADDRESS A
ON P.PERSONID = A.PERSONID