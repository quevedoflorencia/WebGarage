INSERT INTO Usuario(id, nombre, email, password, rol, activo) VALUES(null, 'Test', 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre, latitud, longitud, rutaFoto) VALUES (null, 'Caminito', 5, '10:00:00', '17:00:00',"-34.63906958631624", "-58.36257341577235","/img/garages/garageCaminito.jpg");
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre, latitud, longitud, rutaFoto) VALUES (null, 'Congreso', 15, '08:30:00', '19:00:00', "-34.60974164441148", "-58.392573783180104", "/img/garages/garageCongreso.jpg");
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre, latitud, longitud, rutaFoto) VALUES (null, 'Recoleta', 88, '09:00:00', '23:30:00', "-34.58768745573454", "-58.3972946640706", "/img/garages/garageRecoleta.jpg");
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre, latitud, longitud, rutaFoto) VALUES (null, 'Plaza Flores', 20, '11:00:00', '22:00:00',"-34.62869910007167", "-58.46367564003624", "/img/garages/garageFlores.jpg");
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre, latitud, longitud, rutaFoto) VALUES (null, 'San Telmo', 25, '05:00:00', '22:00:00', "-34.62595856521416", "-58.37051136645604", "/img/garages/garageSanTelmo.jpg");
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre, latitud, longitud, rutaFoto) VALUES (null, 'Retiro', 30, '00:00:00', '23:59:00', "-34.59514012441202", "-58.37602412735591", "/img/garages/garageRetiro.jpg");
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre, latitud, longitud, rutaFoto) VALUES (null, 'Obelisco', 88, '09:00:00', '23:30:00', "-34.60356592245702", "-58.38159490857141", "/img/garages/garageObelisco.jpg");
INSERT INTO Garage(id, nombre, capacidad, horarioApertura, horarioCierre, latitud, longitud, rutaFoto) VALUES (null, 'Palermo', 35, '06:00:00', '22:59:00', "-34.5695680558266", "-58.41163489626442", "/img/garages/garagePalermo.jpg");


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