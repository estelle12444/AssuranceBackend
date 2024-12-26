-- Insertion des catégories de véhicules
INSERT INTO vehicle_categories (code, libelle, description) VALUES
('201', 'Promenade et Affaire', 'Usage personnel'),
('202', 'Véhicules Motorisés à 2 ou 3 roues', 'Motocycle, tricycles'),
('203', 'Transport public de voyage', 'Véhicule transport de personnes'),
('204', 'Véhicule de transport avec taximètres', 'Taxis');

-- Insertion des puissances fiscales
INSERT INTO fiscal_powers (min_power, max_power, premium) VALUES
(2, 2, 37601),
(3, 6, 45181),
(7, 10, 51078),
(11, 14, 65677),
(15, 23, 86456),
(24, 99, 104143);

-- Insertion des garanties
INSERT INTO guarantees (name, description, rate, minimum_premium, max_vehicle_age, is_based_on_new_value, is_based_on_venal_value, covered_value_percentage) VALUES
('RC', 'Responsabilité Civile', 0, NULL, NULL, false, false, NULL),
('DOMMAGES', 'Garantie Dommages', 2.60, NULL, 5, true, false, NULL),
('TIERCE COLLISION', 'Garantie Tierce Collision', 1.65, NULL, 8, true, false, NULL),
('TIERCE PLAFONNEE', 'Garantie Tierce Plafonnée', 4.20, 100000, 10, false, true, 50),
('VOL', 'Garantie Vol', 0.14, NULL, NULL, false, true, NULL),
('INCENDIE', 'Garantie Incendie', 0.15, NULL, NULL, false, true, NULL);

-- Insertion des produits d'assurance
INSERT INTO insurance_products (name, description) VALUES
('Papillon', 'Assurance avec RC, DOMMAGE, VOL'),
('Douby', 'Assurance avec RC, DOMMAGE, TIERCE COLLISION'),
('Douyou', 'Assurance avec RC, DOMMAGE, COLLISION, INCENDIE'),
('Toutourisquou', 'Assurance tous risques');

-- Association des garanties aux produits
-- Papillon
INSERT INTO product_guarantees (product_id, guarantee_id) 
SELECT p.id, g.id 
FROM insurance_products p, guarantees g 
WHERE p.name = 'Papillon' AND g.name IN ('RC', 'DOMMAGES', 'VOL');

-- Douby
INSERT INTO product_guarantees (product_id, guarantee_id)
SELECT p.id, g.id 
FROM insurance_products p, guarantees g 
WHERE p.name = 'Douby' AND g.name IN ('RC', 'DOMMAGES', 'TIERCE COLLISION');

-- Douyou
INSERT INTO product_guarantees (product_id, guarantee_id)
SELECT p.id, g.id 
FROM insurance_products p, guarantees g 
WHERE p.name = 'Douyou' AND g.name IN ('RC', 'DOMMAGES', 'TIERCE COLLISION', 'INCENDIE');

-- Toutourisquou
INSERT INTO product_guarantees (product_id, guarantee_id)
SELECT p.id, g.id 
FROM insurance_products p, guarantees g 
WHERE p.name = 'Toutourisquou';

-- Association des catégories aux produits
-- Papillon (201)
INSERT INTO product_eligible_categories (product_id, category_code)
SELECT p.id, c.code
FROM insurance_products p, vehicle_categories c
WHERE p.name = 'Papillon' AND c.code = '201';

-- Douby (202)
INSERT INTO product_eligible_categories (product_id, category_code)
SELECT p.id, c.code
FROM insurance_products p, vehicle_categories c
WHERE p.name = 'Douby' AND c.code = '202';

-- Douyou (201, 202)
INSERT INTO product_eligible_categories (product_id, category_code)
SELECT p.id, c.code
FROM insurance_products p, vehicle_categories c
WHERE p.name = 'Douyou' AND c.code IN ('201', '202');

-- Toutourisquou (201)
INSERT INTO product_eligible_categories (product_id, category_code)
SELECT p.id, c.code
FROM insurance_products p, vehicle_categories c
WHERE p.name = 'Toutourisquou' AND c.code = '201';

-- Insertion des simulations de test
INSERT INTO simulations (quote_reference, product_id, creation_date, end_date, price, vehicle_age, vehicle_value, category_code, fiscal_power)
SELECT 
    'QT123456789ABC',
    p.id,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP + INTERVAL '2 weeks',
    150000.00,
    3,
    5000000.00,
    '201',
    7
FROM insurance_products p
WHERE p.name = 'Papillon';

INSERT INTO simulations (quote_reference, product_id, creation_date, end_date, price, vehicle_age, vehicle_value, category_code, fiscal_power)
SELECT 
    'QT987654321XYZ',
    p.id,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP + INTERVAL '2 weeks',
    200000.00,
    2,
    7000000.00,
    '202',
    10
FROM insurance_products p
WHERE p.name = 'Douby';

-- Insertion des souscriptions de test
INSERT INTO subscriptions (
    simulation_id,
    registration_number,
    color,
    number_of_seats,
    number_of_doors,
    first_registration_date,
    subscriber_first_name,
    subscriber_last_name,
    subscriber_address,
    subscriber_phone,
    subscriber_id_number,
    subscriber_city,
    subscription_date,
    status,
    attestation_number
)
SELECT 
    s.id,
    'AB-123-CD',
    'NOIR',
    5,
    4,
    '2020-01-01',
    'John',
    'Doe',
    '123 Rue Principale',
    '+237600000000',
    'ABC123456',
    'Douala',
    CURRENT_DATE,
    'EN_COURS',
    'AT123456789ABC'
FROM simulations s
WHERE s.quote_reference = 'QT123456789ABC';

INSERT INTO subscriptions (
    simulation_id,
    registration_number,
    color,
    number_of_seats,
    number_of_doors,
    first_registration_date,
    subscriber_first_name,
    subscriber_last_name,
    subscriber_address,
    subscriber_phone,
    subscriber_id_number,
    subscriber_city,
    subscription_date,
    status,
    attestation_number
)
SELECT 
    s.id,
    'XY-789-ZW',
    'BLANC',
    2,
    2,
    '2022-06-15',
    'Jane',
    'Smith',
    '456 Avenue Centrale',
    '+237611111111',
    'XYZ789012',
    'Yaoundé',
    CURRENT_DATE,
    'EN_COURS',
    'AT987654321XYZ'
FROM simulations s
WHERE s.quote_reference = 'QT987654321XYZ'; 