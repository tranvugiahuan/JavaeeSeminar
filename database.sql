IF DB_ID(N'OrderDemoDb') IS NULL
BEGIN
    CREATE DATABASE OrderDemoDb;
END
GO

USE OrderDemoDb;
GO

IF OBJECT_ID(N'dbo.OrderDetails', N'U') IS NOT NULL
BEGIN
    DROP TABLE dbo.OrderDetails;
END
GO

IF OBJECT_ID(N'dbo.Orders', N'U') IS NOT NULL
BEGIN
    DROP TABLE dbo.Orders;
END
GO

IF OBJECT_ID(N'dbo.Products', N'U') IS NOT NULL
BEGIN
    DROP TABLE dbo.Products;
END
GO

CREATE TABLE dbo.Products (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    productName NVARCHAR(150) NOT NULL,
    price DECIMAL(18, 2) NOT NULL,
    stockQuantity INT NOT NULL
);
GO

CREATE TABLE dbo.Orders (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    customerName NVARCHAR(150) NOT NULL,
    orderDate DATETIME2 NOT NULL,
    totalAmount DECIMAL(18, 2) NOT NULL
);
GO

CREATE TABLE dbo.OrderDetails (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    orderId BIGINT NOT NULL,
    productId BIGINT NOT NULL,
    productName NVARCHAR(150) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(18, 2) NOT NULL,
    CONSTRAINT FK_OrderDetails_Orders
        FOREIGN KEY (orderId) REFERENCES dbo.Orders(id),
    CONSTRAINT FK_OrderDetails_Products
        FOREIGN KEY (productId) REFERENCES dbo.Products(id)
);
GO

INSERT INTO dbo.Products (productName, price, stockQuantity)
VALUES
    (N'Laptop Dell Inspiron', 15500000, 5),
    (N'Bàn phím cơ', 850000, 12),
    (N'Chuột Logitech', 420000, 20),
    (N'Màn hình 24 inch', 3200000, 8),
    (N'Tai nghe Bluetooth', 690000, 15),
    (N'USB 64GB', 180000, 30);
GO
