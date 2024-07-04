INSERT INTO estado_reserva(id,descripcion) VALUES (null, 'Pendiente');
INSERT INTO estado_reserva(id,descripcion) VALUES (null, 'Confirmada');
INSERT INTO estado_reserva(id,descripcion) VALUES (null, 'Pagada');
INSERT INTO estado_reserva(id,descripcion) VALUES (null, 'Activa');
INSERT INTO estado_reserva(id,descripcion) VALUES (null, 'Cancelada');
INSERT INTO estado_reserva(id,descripcion) VALUES (null, 'Vencida');

INSERT INTO tipo_vehiculo(id,descripcion, icono) VALUES (null, 'Auto', 'fa-car-side');
INSERT INTO tipo_vehiculo(id,descripcion, icono) VALUES (null, 'Camioneta', 'fa-truck');
INSERT INTO tipo_vehiculo(id,descripcion, icono) VALUES (null, 'Moto', 'fa-motorcycle');

INSERT INTO Usuario(id, nombre, email, password, rol, activo) VALUES(null, 'Test', 'test@unlam.edu.ar', 'test', 'ADMIN', true);
INSERT INTO Usuario(id, nombre, email, password, rol, activo) VALUES(null, 'Alan', 'adigio@outlook.com', '1234', 'ADMIN', true);

INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 1, 1, 2000.00, 55);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 1, 2, 5500.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 1, 3, 1200.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 2, 1, 3500.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 2, 2, 6000.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 3, 1, 3500.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 3, 3, 1500.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 4, 1, 3000.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 4, 2, 4500.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 5, 1, 3500.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 5, 2, 4500.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 5, 3, 2000.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 6, 1, 3500.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 6, 2, 4500.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 7, 1, 3500.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 7, 2, 5500.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 7, 3, 1000.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 8, 2, 4500.00, 1);
INSERT INTO garage_tipo_vehiculo(id, id_garage, id_tipo_vehiculo, precioHora, capacidad) VALUES (null, 8, 3, 2000.00, 1);

INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 1, 1, '2024-06-20', '13:00', '14:00', 2,1);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 1, 1, '2024-06-20', '20:00', '23:00', 2,1);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 1, 1, '2024-06-20', '16:00', '17:00', 2,2);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 1, 1, '2024-05-05', '11:00', '12:00', 2,2);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 1, 1, '2024-05-06', '02:00', '16:00', 2,3);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 1, 1, '2024-03-07', '03:00', '23:00', 2,3);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 1, 1, '2024-01-20', '04:00', '13:00', 2,1);


INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 1, 1, '2024-12-20', '04:00', '13:00', 2,1);

INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 1, 1, '2024-12-20', '04:00', '08:00', 2,1);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 1, 1, '2024-12-19', '08:00', '11:00', 2,1);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 1, 1, '2024-12-18', '11:00', '13:00', 2,1);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 1, 1, '2024-12-17', '13:00', '14:00', 2,1);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 2, 1, '2024-07-09', '09:00', '11:00', 2,3);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 2, 1, '2024-07-10', '12:00', '13:00', 2,3);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 3, 1, '2024-07-11', '12:00', '14:00', 2,3);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 3, 1, '2024-09-09', '15:00', '17:00', 2,3);
INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id,garageTipoVehiculo_id) VALUES (null, 3, 1, '2024-10-08', '10:00', '12:00', 2,3);

