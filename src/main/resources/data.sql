INSERT INTO User(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Garage(id, name, capacity, openingTime, closingTime) VALUES (null, 'Suipacha', 5, '10:00:00', '17:00:00');

INSERT INTO Reservation(id, day, startTime, finishTime, clientName, userId) VALUES (null, '2024-05-05', '04:00', '06:00', 'Roberto', 1);
INSERT INTO Reservation(id, day, startTime, finishTime, clientName, userId) VALUES (null, '2024-05-05', '20:00', '23:00', 'Pedro', 2);
INSERT INTO Reservation(id, day, startTime, finishTime, clientName, userId) VALUES (null, '2024-05-05', '10:00', '12:00', 'Alberto', 3);
INSERT INTO Reservation(id, day, startTime, finishTime, clientName, userId) VALUES (null, '2024-05-05', '11:00', '12:00', 'Juan', 4);
INSERT INTO Reservation(id, day, startTime, finishTime, clientName, userId) VALUES (null, '2024-05-06', '02:00', '16:00', 'Roberto', 1);
INSERT INTO Reservation(id, day, startTime, finishTime, clientName, userId) VALUES (null, '2024-03-07', '03:00', '23:00', 'Roberto', 1);
INSERT INTO Reservation(id, day, startTime, finishTime, clientName, userId) VALUES (null, '2024-01-20', '04:00', '13:00', 'Roberto', 1);