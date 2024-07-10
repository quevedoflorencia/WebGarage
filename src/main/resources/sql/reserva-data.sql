INSERT INTO Reserva(id, garage_id, usuario_id, dia, horarioInicio, horarioFin, estado_id, garageTipoVehiculo_id)
VALUES
    (null, 7, 1, '2024-08-01', '09:00', '13:00', 2, 1),
    (null, 32, 1, '2024-08-23', '17:00', '19:00', 2, 3),
    (null, 15, 1, '2024-07-29', '11:00', '12:00', 2, 2),
    (null, 20, 1, '2024-07-28', '12:00', '16:00', 2, 3),
    (null, 20, 1, '2024-07-19', '12:00', '16:00', 3, 3),
    (null, 24, 1, '2024-07-20', '13:00', '14:00', 3, 3),

    (null, 24, 1, '2024-06-20', '13:00', '14:00', 6, 3),
    (null, 7, 1, '2024-04-13', '16:00', '17:00', 6, 1),
    (null, 32, 1, '2024-05-20', '12:00', '19:00', 6, 2),
    (null, 32, 1, '2024-03-20', '11:00', '17:00', 6, 2),

    (null, 2, 2, '2024-07-19', '09:00', '13:00', 3, 1),
    (null, 2, 2, '2024-07-20', '11:00', '15:00', 3, 1),
    (null, 3, 2, '2024-12-20', '13:00', '19:00', 2, 1),
    (null, 3, 2, '2024-12-19', '10:00', '14:00', 2, 1),
    (null, 33, 2, '2024-02-24', '17:00', '19:00', 6, 1),
    (null, 33, 2, '2024-05-02', '17:00', '19:00', 6, 1),
    (null, 32, 2, '2024-03-23', '12:00', '16:00', 6, 1),
    (null, 32, 2, '2024-01-07', '11:00', '13:00', 6, 2),
    (null, 32, 2, '2024-04-05', '11:00', '13:00', 6, 2),
    (null, 30, 2, '2024-03-23', '12:00', '16:00', 6, 3),

    (null, 1, 3, '2024-08-20', '20:00', '23:00', 2, 1),
    (null, 2, 3, '2024-09-09', '09:00', '11:00', 2, 3),
    (null, 2, 3, '2024-08-10', '12:00', '13:00', 3, 3),
    (null, 3, 3, '2024-09-21', '12:00', '14:00', 3, 3),
    (null, 3, 3, '2024-04-09', '15:00', '17:00', 6, 3),
    (null, 32, 3, '2024-05-08', '10:00', '12:00', 6, 3),
    (null, 3, 3, '2024-04-10', '15:00', '17:00', 6, 3),
    (null, 32, 3, '2024-06-08', '10:00', '12:00', 6, 3),

    (null, 1, 4, '2024-08-17', '13:00', '14:00', 2, 1),
    (null, 1, 4, '2024-07-24', '11:00', '13:00', 2, 1),
    (null, 1, 4, '2024-12-19', '08:00', '11:00', 3, 1),
    (null, 1, 4, '2024-01-15', '04:00', '08:00', 6, 1),
    (null, 1, 4, '2024-04-20', '04:00', '13:00', 6, 1),

    (null, 1, 5, '2024-12-17', '13:00', '14:00', 2, 1),
    (null, 1, 5, '2024-12-18', '11:00', '13:00', 2, 1),
    (null, 1, 5, '2024-12-19', '08:00', '11:00', 3, 1),
    (null, 1, 5, '2024-03-03', '04:00', '08:00', 6, 1),
    (null, 1, 5, '2024-06-20', '04:00', '13:00', 6, 1);



INSERT INTO Pago(id, reserva_id)
VALUE
    (null, 15);
