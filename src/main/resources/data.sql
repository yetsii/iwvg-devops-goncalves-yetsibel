INSERT INTO users (id, name, family_name) VALUES
    ('1', 'Oscar', 'Fernandez'),
    ('2', 'Ana', 'Blanco'),
    ('3', 'Oscar', 'López'),
    ('4', 'Paula', 'Torres'),
    ('5', 'Antonio', 'Blanco'),
    ('6', 'Paula', 'Torres')
ON CONFLICT (id) DO NOTHING;
