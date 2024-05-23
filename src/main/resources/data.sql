INSERT INTO Usuario(id, nombre, email, password, rol, activo) VALUES(null, 'Test', 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre, latitud, longitud) VALUES (null, 'Suipacha', 5, '10:00:00', '17:00:00', "1000", "-1000");
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre, latitud, longitud) VALUES (null, 'Ramos Mejia', 20, '11:00:00', '22:00:00',"-34.64536566775859", "-58.56192234666206");
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre) VALUES (null, 'Congreso', 15, '08:30:00', '19:00:00');
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre) VALUES (null, 'Obelisco', 88, '09:00:00', '23:30:00');
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre) VALUES (null, 'Pompeya', 25, '05:00:00', '22:00:00');
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre) VALUES (null, 'Liniers', 30, '00:00:00', '23:59:00');
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre) VALUES (null, 'Palermo', 35, '06:00:00', '22:59:00');


INSERT INTO tipo_vehiculo(id,descripcion) VALUES (null, 'Auto');
INSERT INTO tipo_vehiculo(id,descripcion) VALUES (null, 'Camioneta');
INSERT INTO tipo_vehiculo(id,descripcion) VALUES (null, 'Moto');


INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora) VALUES (null, 1, 1, 2000.00);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora) VALUES (null, 1, 2, 5500.00);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora) VALUES (null, 1, 3, 1200.00);

INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora) VALUES (null, 2, 1, 3500.00);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora) VALUES (null, 2, 2, 6000.00);


INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '04:00', '06:00');
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '20:00', '23:00');
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '10:00', '12:00');
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '11:00', '12:00');
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-06', '02:00', '16:00');
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-03-07', '03:00', '23:00');
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-01-20', '04:00', '13:00');