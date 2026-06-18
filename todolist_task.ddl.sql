CREATE DATABASE todolist_task;

go
use todolist_task;

BEGIN TRANSACTION;
CREATE TABLE todolist(
	id INT IDENTITY PRIMARY KEY ,
	task_name VARCHAR(1000),
	deadline SMALLDATETIME,
	priority VARCHAR(100),
	isStart VARCHAR(100) DEFAULT null, 
	completion VARCHAR(100) DEFAULT 'ñ¢äÆóπ',
	completion_date SMALLDATETIME DEFAULT null,
	isDelete VARCHAR (100) DEFAULT null
	);
commit;

INSERT INTO todolist(task_name, deadline, priority) VALUES ('test', '2026-07-16 12:00', 'Åö');
INSERT INTO todolist(task_name, deadline, priority, completion, completion_date) VALUES ('test2','2026-08-10 11:00', 'ÅöÅö', 'äÆóπ', '2026-06-20 20:00');


SELECT * FROM todolist;

DELETE FROM todolist;

DROP TABLE todolist;
