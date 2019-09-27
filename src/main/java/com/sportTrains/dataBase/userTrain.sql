BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "userTrain" (
	"name"	TEXT,
	"type"	TEXT,
	"allTime"	TEXT,
	"token"	TEXT,
	"dateTren"	TEXT
);
INSERT INTO "userTrain" ("name","type","allTime","token","dateTren") VALUES (NULL,NULL,NULL,NULL,NULL),
 ('name','type','4','token','10.10.10');
COMMIT;
