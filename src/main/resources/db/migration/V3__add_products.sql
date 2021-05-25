INSERT INTO products (id, price, title)
VALUES (1, 450.0, 'Cheese'),
       (2, 45.0, 'Beer'),
       (3, 65.0, 'Milk'),
       (4, 115.0, 'Tomato'),
       (5, 58.0, 'Bread');

ALTER SEQUENCE product_seq RESTART WITH 6;