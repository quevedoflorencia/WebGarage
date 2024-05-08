INSERT INTO User(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Garage(id, name, capacity, openingTime, closingTime) VALUES (null, 'Suipacha', 5, '10:00:00', '17:00:00');

INSERT INTO Reservation(id, day, startTime, finishTime, clientName, userId) VALUES (null, '2024-05-05',4,6,'Roberto',1);
INSERT INTO Reservation(id, day, startTime, finishTime, clientName, userId) VALUES (null, '2024-05-05',20,23,'Pedro',2);
INSERT INTO Reservation(id, day, startTime, finishTime, clientName, userId) VALUES (null, '2024-05-05',10,12,'Alberto',3);
INSERT INTO Reservation(id, day, startTime, finishTime, clientName,userId) VALUES (null, '2024-05-05',11,12,'Juan',4);

INSERT INTO Reservation(id, day, startTime, finishTime, clientName, userId) VALUES (null, '2024-05-06',2,16,'Roberto',1);
INSERT INTO Reservation(id, day, startTime, finishTime, clientName, userId) VALUES (null, '2024-03-07',3,23,'Roberto',1);
INSERT INTO Reservation(id, day, startTime, finishTime, clientName, userId) VALUES (null, '2024-01-20',4,13,'Roberto',1);