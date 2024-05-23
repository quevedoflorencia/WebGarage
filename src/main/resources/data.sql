INSERT INTO Usuario(id, nombre, email, password, rol, activo) VALUES(null, 'Test', 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre) VALUES (null, 'Suipacha', 5, '10:00:00', '17:00:00');
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre) VALUES (null, 'Cid Campeador', 10, '07:00:00', '23:00:00');
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre) VALUES (null, 'Congreso', 15, '08:30:00', '19:00:00');
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre) VALUES (null, 'Obelisco', 88, '09:00:00', '23:30:00');
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre) VALUES (null, 'Pompeya', 25, '05:00:00', '22:00:00');
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre) VALUES (null, 'Liniers', 30, '00:00:00', '23:59:00');
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre) VALUES (null, 'Palermo', 35, '06:00:00', '22:59:00');

INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '04:00', '06:00');
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '20:00', '23:00');
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '10:00', '12:00');
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '11:00', '12:00');
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-06', '02:00', '16:00');
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-03-07', '03:00', '23:00');
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-01-20', '04:00', '13:00');