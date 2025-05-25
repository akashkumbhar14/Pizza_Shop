DROP DATABASE IF EXISTS pizzastore_db;
CREATE DATABASE pizzastore_db;
USE pizzastore_db;

DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS menu_history;

CREATE TABLE customer(
    cid INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(15),
    email VARCHAR(50),
    password VARCHAR(30),
    mobile CHAR(10)
);

CREATE TABLE menu(
    mid INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    description TEXT,
    price DOUBLE,
    category ENUM('Veg Pizza', 'Non Veg Pizza', 'Garlic Bread', 'Drinks')
);

CREATE TABLE menu_history (
    history_id INT PRIMARY KEY AUTO_INCREMENT,
    mid INT,
    name VARCHAR(30),
    description TEXT,
    price DOUBLE,
    category ENUM('Veg Pizza', 'Non Veg Pizza', 'Garlic Bread', 'Drinks'),
    deleted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders(
    oid INT PRIMARY KEY AUTO_INCREMENT,
    cid INT,
    mid INT,
    FOREIGN KEY (cid) REFERENCES customer(cid),
    FOREIGN KEY (mid) REFERENCES menu(mid)
);

DELIMITER $$
CREATE TRIGGER trg_menu_delete
BEFORE DELETE ON menu
FOR EACH ROW
BEGIN
    INSERT INTO menu_history 
        (mid, name, description, price, category) 
    VALUES 
        (OLD.mid, OLD.name, OLD.description, OLD.price, OLD.category);
END $$
DELIMITER ;

INSERT INTO menu(name, description, price, category) 
VALUES
    ("Margherita", "Classic delight with 100% real mozzarella cheese", 109, 'Veg Pizza'),
    ("Farmhouse", "Delightful combination of onion, capsicum, tomato & grilled mushroom", 459, 'Veg Pizza'),
    ("Peppy Paneer", "Flavorful trio of juicy paneer, crisp capsicum with spicy red paprika", 259, 'Veg Pizza'),
    ("Mexican Green Wave", "Mexican herbs sprinkled on onion, capsicum, tomato & jalapeno", 259, 'Veg Pizza'),
    ("Veggie Paradise", "The awesome foursome! Golden corn, black olives, capsicum, red paprika", 689, 'Veg Pizza'),
    ("Paneer Makhani", "Paneer, Onion & Capsicum with Desi Makhani Sauce", 129, 'Veg Pizza'),
    ("Chicken Tikka", "Succulent chicken tikka with onions and capsicum", 349, 'Non Veg Pizza'),
    ("Pepperoni", "Classic pepperoni with spicy tomato sauce", 399, 'Non Veg Pizza'),
    ("Garlic Bread", "Buttery garlic bread with herbs", 89, 'Garlic Bread'),
    ("Garlic Bread with Cheese", "Buttery garlic bread with cheese and herbs", 129, 'Garlic Bread'),
    ("Coca-Cola", "Refreshing Coca-Cola", 50, 'Drinks'),
    ("Pepsi", "Refreshing Pepsi", 50, 'Drinks');

SELECT 
    o.oid AS Order_ID,
    c.name AS Customer_Name,
    COALESCE(m.name, mh.name) AS Pizza_Name, 
    m.price AS Price,
    mh.category 
FROM 
    orders o
JOIN 
    customer c ON o.cid = c.cid
LEFT JOIN 
    menu m ON o.mid = m.mid
LEFT JOIN 
    menu_history mh ON o.mid = mh.mid AND m.mid IS NULL;