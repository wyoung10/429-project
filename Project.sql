CREATE TABLE ArticleType (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    barcodePrefix CHAR(2) UNIQUE NOT NULL,
    alphaCode VARCHAR(5) NOT NULL,
    status ENUM('Active', 'Inactive') NOT NULL DEFAULT 'Active',
	CONSTRAINT chk_article_barcode_prefix_format CHECK(barcodePrefix REGEXP '[0-9]{2}')
);

CREATE TABLE Color (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    barcodePrefix CHAR(2) UNIQUE NOT NULL,
    alphaCode VARCHAR(5) NOT NULL,
    status ENUM('Active', 'Inactive') NOT NULL DEFAULT 'Active',
	CONSTRAINT chk_color_barcode_prefix_format CHECK(barcodePrefix REGEXP '[0-9]{2}')
);

CREATE TABLE Inventory (
    barcode CHAR(8) PRIMARY KEY,
    gender ENUM('M', 'W') NOT NULL,
    size VARCHAR(20) NOT NULL,
    articleTypeId INTEGER NOT NULL,
    color1Id INTEGER NOT NULL,
    color2Id INTEGER,
    brand VARCHAR(50),
    notes TEXT,
    status ENUM('Donated', 'Received', 'Removed') NOT NULL DEFAULT 'Donated',
    donorLastName VARCHAR(50),
    donorFirstName VARCHAR(50),
    donorPhone CHAR(12),
    donorEmail VARCHAR(50),
    receiverNetId VARCHAR(50),
    receiverLastName VARCHAR(50),
    receiverFirstName VARCHAR(50),
    dateDonated CHAR(10) NOT NULL,
    dateTaken CHAR(10),
    CONSTRAINT fk_inventory_article_type FOREIGN KEY (articleTypeId) REFERENCES ArticleType(id),
    CONSTRAINT fk_inventory_color1 FOREIGN KEY (color1Id) REFERENCES Color(id),
    CONSTRAINT fk_inventory_color2 FOREIGN KEY (color2Id) REFERENCES Color(id),
	CONSTRAINT chk_date_donated_format CHECK(dateDonated REGEXP '[0-9]{4}-[0-9]{2}-[0-9]{2}'),
	CONSTRAINT chk_date_taken_format CHECK(dateTaken REGEXP '[0-9]{4}-[0-9]{2}-[0-9]{2}'),
	CONSTRAINT chk_donor_phone_format CHECK(donorPhone REGEXP '[0-9]{3}-[0-9]{3}-[0-9]{4}'),
	CONSTRAINT chk_donor_email_format CHECK (donorEmail REGEXP '^[^@]+@[^@]+\\.[^@]{2,}$')
);

-- Example data

INSERT INTO ArticleType (description, barcodePrefix, alphaCode)
VALUES 
('Pant Suit', '01', 'PS'),
('Skirt Suit', '02', 'SS'),
('Blazer', '03', 'BL'),
('Dress', '04', 'D'),
('Shoe', '05', 'SH'),
('Shirt', '06', 'ST'),
('3-Piece Pant Suit', '07', '3PS'),
('Pants', '08', 'P'),
('Trench', '09', 'TR'),
('Top', '10', 'TP'),
('Belt', '11', 'BE'),
('Suit', '12', 'SU'),
('Scarf', '13', 'SC'),
('Coat', '14', 'C'),
('Sweater', '15', 'SW'),
('Jacket', '16', 'JK'),
('Skirt', '17', 'SK'),
('Vest', '18', 'V'),
('Tie', '19', 'T')
;
INSERT INTO ArticleType (description, barcodePrefix, alphaCode, status)
VALUES ('Inactive type', '20', 'IA', 'Inactive')
;

INSERT INTO Color (description, barcodePrefix, alphaCode)
VALUES ('Black', '01', 'BK'),
('Blue', '02', 'BL'),
('Brown', '03', 'GR'),
('Beige', '04', 'BE'),
('Grey', '05', 'GR'),
('White', '06', 'WH'),
('Pink', '07', 'P'),
('Red', '08', 'R'),
('Green', '09', 'G'),
('Cream', '10', 'CR'),
('Teal', '11', 'TE'),
('Navy', '12', 'NV'),
('Purple', '13', 'PR'),
('Maroon', '14', 'M'),
('Tan', '15', 'TA'),
('Orange', '16', 'OR'),
('Yellow', '17', 'Y'),
('More than 4 colors', '18', '4+C'),
('Print', '19', 'PN'),
('Pin Stripes', '20', 'PS'),
('Patterns', '21', 'PA')
;
INSERT INTO Color (description, barcodePrefix, alphaCode, status)
VALUES ('Inactive color', '22', 'IA', 'Inactive')
;

INSERT INTO Inventory (barcode, gender, size, articleTypeId, color1Id, color2Id, brand, notes, status, donorLastName, donorFirstName, donorPhone, donorEmail, receiverNetId, receiverLastName, receiverFirstName, dateDonated, dateTaken)
VALUES 
('00801001', 'M', '2', 8, 1, 20, 'Old Navy', NULL, 'Received', NULL, NULL, '585-395-2234', 'astandis1@brockport.edu', 'astandis', 'Standish', 'Adam', '2023-05-08', '2023-05-09'),
('10803001', 'W', '4', 8, 3, NULL, 'Loft', NULL, 'Donated', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-05-06', NULL),
('11703001', 'W', '6', 17, 3, NULL, 'Express Design Studio', NULL, 'Donated', 'Williams', 'David', '585-395-3456', 'dwilli3@brockport.edu', NULL, NULL, NULL, '2023-05-04', NULL),
('11701001', 'W', '6', 17, 1, NULL, 'Christopher & Banks', NULL, 'Received', 'Brown', 'Emily', NULL, 'ebrown9@brockport.edu', NULL, NULL, NULL, '2023-05-02', NULL),
('10204001', 'W', '4', 2, 4, 1, 'Talbots', NULL, 'Donated', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-05-01', NULL),
('10601001', 'W', '16', 6, 1, 6, 'Madison Michelle', 'Comes with black tank top', 'Received', 'Davis', 'Sarah', '585-395-9012', NULL, 'jalston', 'Alston', 'Jeff', '2023-05-03', '2023-05-10'),
('10204002', 'W', '4', 2, 4, NULL, 'Removed Item', NULL, 'Removed', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-04-18', NULL)
;
