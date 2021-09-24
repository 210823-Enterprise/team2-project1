DROP TABLE Test CASCADE;
DROP TABLE Test2 CASCADE;
DROP TABLE Test3 CASCADE;
DROP TABLE Testafdasf CASCADE;
CREATE TABLE Test (
id Serial,
username varchar(50),
pass varchar(50)
);

INSERT INTO Test (username,pass) VALUES ('ryan','password');

SELECT * FROM Test;

DELETE FROM test WHERE id=1;
