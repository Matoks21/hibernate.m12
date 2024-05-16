-- V2__populate_db.sql
-- Додати клієнтів
INSERT INTO client (name) VALUES
    ('John One'),
    ('Alice Two'),
    ('Michael Three'),
    ('Emily Four'),
    ('William Five'),
    ('Emma Six'),
    ('James Seven'),
    ('Olivia Eight'),
    ('Daniel Nine'),
    ('Sophia Ten');

-- Додати планети
INSERT INTO planet (id, name) VALUES
    ('MARS', 'Mars'),
    ('VEN', 'Venus'),
    ('EARTH', 'Earth'),
    ('JUP', 'Jupiter'),
    ('SAT', 'Saturn');

-- Додати квитки
INSERT INTO ticket (client_id, from_planet_id, to_planet_id) VALUES
    (1, 'EARTH', 'MARS'),
    (2, 'VEN', 'JUP'),
    (3, 'MARS', 'EARTH'),
    (4, 'SAT', 'VEN'),
    (5, 'EARTH', 'JUP'),
    (6, 'MARS', 'SAT'),
    (7, 'JUP', 'EARTH'),
    (8, 'VEN', 'MARS'),
    (9, 'SAT', 'EARTH'),
    (10, 'EARTH', 'VEN');

