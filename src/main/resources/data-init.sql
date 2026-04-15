INSERT INTO role (name) VALUES
    ('ADMIN'),
    ('USER'),
    ('STUDENT');

INSERT INTO app_user (first_name, name, email, password) VALUES
    ('Célia', 'GODFRIN', 'celia.godfrin@gmail.com', 'root1'),
    ('Nicolas', 'LAUNAY', 'nicolas.launay@gmail.com', 'root2'),
    ('Franck', 'DOYEN', 'franck.doyen@gmail.com', 'root3');

INSERT INTO type (name) VALUES
    ('PC portable'),
    ('Ecran interactif'),
    ('Chargeur');

INSERT INTO request (date, content) VALUES
    ('27 février 2026', 'Bonjour, l''écran de l''ordinateur portable ne s''allume plus'),
    ('14 février 2023', 'Bonjour, Jen''arrive plus à utiliser l''écran'),
    ('27 février 2026', 'Bonjour, Le fil du chargeur est dénudé');

INSERT INTO model (name, description) VALUES
    ('Huawei 500', 'Une description du produit'),
    ('Speechi 1600', 'Une autre description du produit');

INSERT INTO location (name) VALUES
    ('Salle 203'),
    ('Accueil'),
    ('Salle 101');

INSERT INTO loan (start_date, real_end_date, end_date) VALUES
    ('16 Aout 2025', '30 Aout 2025', '30 Aout 2025'),
    ('02 Février 2026', '03 Fevrier 2026', '05 Fevrier 2026'),
    ('25 Mars 2026', '01 Avril 2026', '31 Mars 2026');

INSERT INTO equipment (name, condition) VALUES
    ('Ordinateur n°1234', 'Etat de l''ordinateur1'),
    ('Ordinateur n°5678', 'Etat de l''ordinateur2'),
    ('Ordinateur n°9012', 'Etat de l''ordinateur3');

INSERT INTO documentation (name, link) VALUES
    ('Documentation Speechi 1600', 'https://liendeladoc1.com'),
    ('Documentation Huawei 500', 'https://liendeladoc2.com');

INSERT INTO accreditation (name) VALUES
    ('Niveau1'),
    ('Niveau2'),
    ('Niveau3'),
    ('Niveau4'),
    ('Niveau5');