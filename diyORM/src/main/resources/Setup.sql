DROP TABLE Test CASCADE;
CREATE TABLE Test (
id Serial,
username varchar(50),
pass varchar(50)
);

INSERT INTO Test (username,pass) VALUES ('ryan','password');

SELECT * FROM Test;

DELETE FROM test WHERE id=1;
