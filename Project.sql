CREATE TABLE ArticleType (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255),
    barcodePrefix VARCHAR(255) UNIQUE,
    alphaCode VARCHAR(255),
    status ENUM('Active', 'Inactive') DEFAULT 'Active'
);

CREATE TABLE Color (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255),
    barcodePrefix VARCHAR(255) UNIQUE,
    alphaCode VARCHAR(255),
    status ENUM('Active', 'Inactive') DEFAULT 'Active'
);

CREATE TABLE Inventory (
    barcode VARCHAR(50) PRIMARY KEY,
    gender VARCHAR(7),
    size VARCHAR(255),
    articleTypeId INTEGER,
    color1Id INTEGER,
    color2Id INTEGER,
    brand VARCHAR(50),
    notes TEXT,
    status ENUM('Donated', 'Received', 'Removed') DEFAULT 'Donated',
    donorLastname VARCHAR(50),
    donorFirstname VARCHAR(50),
    donorPhone VARCHAR(50),
    donorEmail VARCHAR(50),
    receiverNetid VARCHAR(50),
    receiverLastname VARCHAR(50),
    receiverFirstname VARCHAR(50),
    dateDonated CHAR(10),
    dateTaken CHAR(10),
    FOREIGN KEY (articleTypeId) REFERENCES ArticleType(id),
    FOREIGN KEY (color1Id) REFERENCES Color(id),
    FOREIGN KEY (color2Id) REFERENCES Color(id)
);
