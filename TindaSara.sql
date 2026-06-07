CREATE DATABASE TiendaSara;
USE TiendaSara;

CREATE TABLE Categorias (
    Id INT PRIMARY KEY IDENTITY(1,1),
    Descripcion VARCHAR(100) NOT NULL
);

CREATE TABLE Marcas (
    Id INT PRIMARY KEY IDENTITY(1,1),
    Descripcion VARCHAR(100) NOT NULL
);

CREATE TABLE Productos (
    Id INT PRIMARY KEY IDENTITY(1,1),
    Descripcion VARCHAR(200) NOT NULL,
    Precio DECIMAL(10,2) NOT NULL,
    Cantidad INT NOT NULL,
    idCategoria INT NOT NULL,
    idMarca INT NOT NULL,
    FOREIGN KEY (idCategoria) REFERENCES Categorias(Id),
    FOREIGN KEY (idMarca) REFERENCES Marcas(Id)
);

CREATE TABLE Carrito (
    Id INT PRIMARY KEY IDENTITY(1,1),
    FolioVenta VARCHAR(50) NOT NULL UNIQUE,
    TotalCompra DECIMAL(10,2) NOT NULL,
    Estatus VARCHAR(50) NOT NULL,
    Fecha DATETIME NOT NULL DEFAULT GETDATE()
);

CREATE TABLE CarritoDetalle (
    Id INT PRIMARY KEY IDENTITY(1,1),
    IdCarrito INT NOT NULL,
    IdProducto INT NOT NULL,
    Cantidad INT NOT NULL,
    Subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (IdCarrito) REFERENCES Carrito(Id),
    FOREIGN KEY (IdProducto) REFERENCES Productos(Id)
);

INSERT INTO Categorias (Descripcion) VALUES ('Electrónica');
INSERT INTO Categorias (Descripcion) VALUES ('Ropa');
INSERT INTO Categorias (Descripcion) VALUES ('Alimentos');
INSERT INTO Categorias (Descripcion) VALUES ('Libros');
INSERT INTO Categorias (Descripcion) VALUES ('Hogar');

INSERT INTO Marcas (Descripcion) VALUES ('Samsung');
INSERT INTO Marcas (Descripcion) VALUES ('Nike');
INSERT INTO Marcas (Descripcion) VALUES ('Coca-Cola');
INSERT INTO Marcas (Descripcion) VALUES ('Penguin');
INSERT INTO Marcas (Descripcion) VALUES ('IKEA');

INSERT INTO Productos (Descripcion, Precio, Cantidad, idCategoria, idMarca) 
VALUES ('Monitor 24 pulgadas', 250.00, 10, 1, 1);

INSERT INTO Productos (Descripcion, Precio, Cantidad, idCategoria, idMarca) 
VALUES ('Camiseta deportiva', 45.00, 25, 2, 2);

INSERT INTO Productos (Descripcion, Precio, Cantidad, idCategoria, idMarca) 
VALUES ('Botella Coca-Cola 2L', 3.50, 50, 3, 3);

INSERT INTO Productos (Descripcion, Precio, Cantidad, idCategoria, idMarca) 
VALUES ('Harry Potter Libro 1', 25.00, 15, 4, 4);

INSERT INTO Productos (Descripcion, Precio, Cantidad, idCategoria, idMarca) 
VALUES ('Lámpara de escritorio', 55.00, 20, 5, 5);

INSERT INTO Carrito (FolioVenta, TotalCompra, Estatus, Fecha) 
VALUES ('FOL-001', 295.00, 'Pendiente', GETDATE());

INSERT INTO Carrito (FolioVenta, TotalCompra, Estatus, Fecha) 
VALUES ('FOL-002', 48.50, 'Completado', GETDATE());

INSERT INTO Carrito (FolioVenta, TotalCompra, Estatus, Fecha) 
VALUES ('FOL-003', 80.00, 'En proceso', GETDATE());

INSERT INTO Carrito (FolioVenta, TotalCompra, Estatus, Fecha) 
VALUES ('FOL-004', 125.50, 'Pendiente', GETDATE());

INSERT INTO Carrito (FolioVenta, TotalCompra, Estatus, Fecha) 
VALUES ('FOL-005', 330.00, 'Completado', GETDATE());

INSERT INTO CarritoDetalle (IdCarrito, IdProducto, Cantidad, Subtotal) 
VALUES (1, 1, 1, 250.00);

INSERT INTO CarritoDetalle (IdCarrito, IdProducto, Cantidad, Subtotal) 
VALUES (1, 3, 1, 3.50);

INSERT INTO CarritoDetalle (IdCarrito, IdProducto, Cantidad, Subtotal) 
VALUES (2, 2, 1, 45.00);

INSERT INTO CarritoDetalle (IdCarrito, IdProducto, Cantidad, Subtotal) 
VALUES (3, 4, 2, 50.00);

INSERT INTO CarritoDetalle (IdCarrito, IdProducto, Cantidad, Subtotal) 
VALUES (4, 5, 2, 110.00);

SELECT 
    p.Id,
    p.Descripcion AS Producto,
    p.Precio,
    p.Cantidad,
    m.Descripcion AS Marca,
    c.Descripcion AS Categoria
FROM Productos p
INNER JOIN Marcas m ON p.idMarca = m.Id
INNER JOIN Categorias c ON p.idCategoria = c.Id;

SELECT 
    ca.Id,
    ca.FolioVenta,
    ca.TotalCompra,
    ca.Estatus,
    ca.Fecha,
    cd.Cantidad,
    p.Descripcion AS Producto,
    p.Precio,
    cd.Subtotal
FROM Carrito ca
INNER JOIN CarritoDetalle cd ON ca.Id = cd.IdCarrito
INNER JOIN Productos p ON cd.IdProducto = p.Id;

SELECT 
    ca.Id,
    ca.FolioVenta,
    ca.TotalCompra,
    ca.Estatus,
    ca.Fecha,
    cd.Cantidad AS CantidadDetalle,
    p.Descripcion AS Producto,
    p.Precio,
    cd.Subtotal,
    m.Descripcion AS Marca,
    c.Descripcion AS Categoria
FROM Carrito ca
INNER JOIN CarritoDetalle cd ON ca.Id = cd.IdCarrito
INNER JOIN Productos p ON cd.IdProducto = p.Id
INNER JOIN Marcas m ON p.idMarca = m.Id
INNER JOIN Categorias c ON p.idCategoria = c.Id;