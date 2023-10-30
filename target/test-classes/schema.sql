DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS carrito;
DROP TABLE IF EXISTS membresia_vip;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS fecha_especial;

CREATE TABLE usuario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre_completo VARCHAR(50) NOT NULL,
    usuario VARCHAR(50) NOT NULL,
    contrasenia VARCHAR(50) NOT NULL
);

CREATE TABLE fecha_especial (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fecha DATE NOT NULL,
    descripcion VARCHAR(100)
);

CREATE TABLE producto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    precio DOUBLE NOT NULL,
    imagen VARCHAR(100)
);

CREATE TABLE membresia_vip (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    fecha DATE NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE carrito (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    fecha date NOT NULL,
    total DOUBLE NOT NULL,
    tipo_promocion VARCHAR(50) NOT NULL CHECK (tipo_promocion IN ('COMUN','FECHA_ESPECIAL','VIP')),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE item (
    id INT PRIMARY KEY AUTO_INCREMENT,
    carrito_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    FOREIGN KEY (carrito_id) REFERENCES carrito(id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);


------------------------------------------------------------------------------------------

INSERT INTO usuario (nombre_completo, usuario, contrasenia) VALUES
('Alejandro Nuñez', 'ale', '1234'),
('Agustin Dedico', 'agus', '1234'),
('Federico Lloves', 'fede', '1234'),
('Bennet Imhof', 'ben', '1234'),
('Yanet Parola', 'yanet', '1234');

INSERT INTO fecha_especial (fecha, descripcion) VALUES
('2023-05-01','Día del Trabajador'),
('2023-05-25','Día de la Revolución de Mayo'),
('2023-06-20','Paso a la Inmortalidad del Gral. Manuel Belgrano'),
('2023-07-09','Día de la Independencia'),
('2023-08-17','Paso a la Inmortalidad del Gral. José de San Martín'),
('2023-12-08','Inmaculada Concepción de María'),
('2023-12-24','Navidad');


INSERT INTO producto (nombre, precio, imagen) VALUES
('Uno', 2000.0, null),
('Dos', 2000.0, null),
('Poker', 1500.0, null),
('Ajedrez', 3000.0, null),
('Damas', 1000.0, null);

INSERT INTO membresia_vip (usuario_id, fecha) VALUES
(3, '2023-10-27'),
(3, '2023-09-20'),
(2, '2023-10-25'),
(1, '2023-10-27');


INSERT INTO carrito (usuario_id, fecha, total, tipo_promocion) VALUES
( 1, '2023-10-27', 0.0, 'COMUN'),
( 2, '2023-10-28', 0.0, 'VIP');


INSERT INTO item (carrito_id, producto_id, cantidad) VALUES
(1,1,1),
(1,2,1),
(1,3,2);