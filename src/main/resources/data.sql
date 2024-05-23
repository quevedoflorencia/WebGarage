INSERT INTO Usuario(id, nombre, email, password, rol, activo) VALUES(null, 'Test', 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre, latitud, longitud) VALUES (null, 'Suipacha', 5, '10:00:00', '17:00:00', "1000", "-1000");
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre, latitud, longitud) VALUES (null, 'Ramos Mejia', 20, '11:00:00', '22:00:00',"-34.64536566775859", "-58.56192234666206");

INSERT INTO Tipo_Vehiculo(id,descripcion) VALUES (null, 'Auto');
INSERT INTO Tipo_Vehiculo(id,descripcion) VALUES (null, 'Camioneta');
INSERT INTO Tipo_Vehiculo(id,descripcion) VALUES (null, 'Moto');


INSERT INTO Garage_Tipo_Vehiculo(id, id_garage, id_tipo_vehiculo, precioHora) VALUES (null, 1, 1, 2000.00);
INSERT INTO Garage_Tipo_Vehiculo(id, id_garage, id_tipo_vehiculo, precioHora) VALUES (null, 1, 2, 5500.00);
INSERT INTO Garage_Tipo_Vehiculo(id, id_garage, id_tipo_vehiculo, precioHora) VALUES (null, 1, 3, 1200.00);

INSERT INTO Garage_Tipo_Vehiculo(id, id_garage, id_tipo_vehiculo, precioHora) VALUES (null, 2, 1, 3500.00);
INSERT INTO Garage_Tipo_Vehiculo(id, id_garage, id_tipo_vehiculo, precioHora) VALUES (null, 2, 2, 6000.00);


INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '04:00', '06:00');
INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '20:00', '23:00');
INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '10:00', '12:00');
INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-05', '11:00', '12:00');
INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-05-06', '02:00', '16:00');
INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-03-07', '03:00', '23:00');
INSERT INTO Reservacion(id, garage_id, usuario_id, dia, horarioInicio, horarioFin) VALUES (null, 1, 1, '2024-01-20', '04:00', '13:00');