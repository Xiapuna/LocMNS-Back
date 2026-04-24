INSERT INTO role (name) VALUES
    ('ADMIN'),
    ('USER'),
    ('STUDENT');

INSERT INTO accreditation (name) VALUES
     ('Niveau1'),
     ('Niveau2'),
     ('Niveau3'),
     ('Niveau4'),
     ('Niveau5');

INSERT INTO location (name) VALUES
    ('Salle 203'),
    ('Accueil'),
    ('Salle 101');

INSERT INTO type (name) VALUES
    ('PC portable'),
    ('Ecran interactif'),
    ('Chargeur');

INSERT INTO request (date, content) VALUES
    ('27 février 2026', 'Bonjour, l''écran de l''ordinateur portable ne s''allume plus'),
    ('14 février 2023', 'Bonjour, Jen''arrive plus à utiliser l''écran'),
    ('27 février 2026', 'Bonjour, Le fil du chargeur est dénudé');

INSERT INTO documentation (name, link) VALUES
    ('Documentation Speechi 1600', 'https://liendeladoc1.com'),
    ('Documentation Huawei 500', 'https://liendeladoc2.com');

INSERT INTO model (name, description, documentation_id, type_id) VALUES
    ('Huawei 500', 'Une description du produit', 1, 2),
    ('Speechi 1600', 'Une autre description du produit', 2, 3);

INSERT INTO equipment (name, condition, location_id, model_id) VALUES
    ('Ordinateur n°1234', 'Etat de l''ordinateur1', 2, 1),
    ('Ordinateur n°5678', 'Etat de l''ordinateur2', 1, 2),
    ('Ordinateur n°9012', 'Etat de l''ordinateur3', 3, 2);

INSERT INTO app_user (first_name, name, email, password, role_id, accreditation_id) VALUES
    ('Célia', 'GODFRIN', 'celia.godfrin@gmail.com', 'root1', 2, 5),
    ('Nicolas', 'LAUNAY', 'nicolas.launay@gmail.com', 'root2', 3, 2),
    ('Franck', 'DOYEN', 'franck.doyen@gmail.com', 'root3', 1, 3);

INSERT INTO loan (start_date, real_end_date, end_date, app_user_id, equipment_id) VALUES
   ('2026-02-27', '2026-02-28', '2026-03-02', 3, 1),
   ('2026-10-11', '2026-10-28', '2026-11-05', 3, 1),
   ('2026-02-27', '2026-02-28', '2026-03-02', 3, 1),
   ('2026-05-09', '2026-05-21', '2026-05-21', 1, 2),
   ('2026-06-12', '2026-06-13', '2026-06-13', 2, 3);

INSERT INTO type_accreditation (accreditation_id, type_id) VALUES
    (1, 1),
    (5, 3),
    (2, 2);


