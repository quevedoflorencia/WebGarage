INSERT INTO estado_reserva(id, descripcion)
VALUES
    (null, 'Pendiente'),
    (null, 'Confirmada'),
    (null, 'Pagada'),
    (null, 'Activa'),
    (null, 'Cancelada'),
    (null, 'Vencida');

INSERT INTO tipo_vehiculo(id,descripcion, icono)
VALUES
    (null, 'Moto', 'fa-motorcycle'),
    (null, 'Auto', 'fa-car-side'),
    (null, 'Camioneta', 'fa-truck');

INSERT INTO Usuario(id, nombre, email, password, rol, activo)
VALUES
    (null, 'Test', 'test@unlam.edu.ar', 'test', 'ADMIN', true),
    (null, 'Alan', 'adigio@outlook.com', '1234', 'ADMIN', true);