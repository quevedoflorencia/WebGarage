INSERT INTO Usuario(id, nombre, email, password, rol, activo) VALUES(null, 'Test', 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre) VALUES (null, 'Suipacha', 5, '10:00:00', '17:00:00');

INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '04:00', '06:00');
INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '20:00', '23:00');
INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '10:00', '12:00');
INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '11:00', '12:00');
INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-06', '02:00', '16:00');
INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-03-07', '03:00', '23:00');
INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-01-20', '04:00', '13:00');