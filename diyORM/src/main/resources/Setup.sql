DROP TABLE Test CASCADE;
DROP TABLE Test2 CASCADE;
DROP TABLE Test3 CASCADE;
DROP TABLE Testafdasf CASCADE;
CREATE TABLE Test2 (
id Serial,
username varchar(50),
pass varchar(50),
tester DATE
);
DROP Table IF EXISTS FieldCheck; 
CREATE TABLE FieldCheck (id Serial,
username varchar(255),
pass varchar(255),
testChar CHAR,
date DATE);

INSERT INTO Test (username,pass) VALUES ('ryan','password');

SELECT * FROM Test;

SELECT * FROM fieldcheck;

DELETE FROM test WHERE id=1;
