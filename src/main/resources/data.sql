INSERT INTO users (id, name, first_name, family_name, email, identity, address, city, province, postal_code, active, admin) VALUES
    ('1', 'Oscar', 'Oscar', 'Fernandez', 'oscar.fernandez@example.com', '12345678A', 'Calle Mayor 1', 'Madrid', 'Madrid', '28001', true, true),
    ('2', 'Ana', 'Ana', 'Blanco', 'ana.blanco@example.com', '23456789B', 'Avenida de la Paz 10', 'Sevilla', 'Sevilla', '41001', true, false),
    ('3', 'Oscar', 'Oscar', 'López', 'oscar.lopez@example.com', '34567890C', 'Calle Real 22', 'Valencia', 'Valencia', '46001', false, false),
    ('4', 'Paula', 'Paula', 'Torres', NULL, '45678901D', 'Plaza del Sol 7', 'Barcelona', 'Barcelona', '08001', true, false),
    ('5', 'Antonio', 'Antonio', 'Blanco', 'antonio.blanco@example.com', '56789012E', 'Avenida del Parque 3', '', 'Barcelona', '08002', false, false),
    ('6', 'Paula', 'Paula', 'Torres', 'paula.torres@example.com', '67890123F', 'Calle de la Luna 9', 'Málaga', 'Málaga', NULL, true, false)
    ON CONFLICT (id)  DO UPDATE SET
    name = EXCLUDED.name,
    first_name = EXCLUDED.first_name,
    family_name = EXCLUDED.family_name,
    email = EXCLUDED.email,
    identity = EXCLUDED.identity,
    address = EXCLUDED.address,
    city = EXCLUDED.city,
    province = EXCLUDED.province,
    postal_code = EXCLUDED.postal_code,
    active = EXCLUDED.active,
    admin = EXCLUDED.admin;
