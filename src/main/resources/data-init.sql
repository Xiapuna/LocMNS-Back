INSERT INTO role (name) VALUES
    ('ADMIN'),
    ('USER');

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
    ('Célia', 'GODFRIN', 'celia.godfrin@gmail.com', '$2a$10$unnTseHmf2ID7q78sMYjX.bKWuNA8SneNyAMPnyXhujzuLqXg7rbG', 1, 5),
    ('Nicolas', 'LAUNAY', 'nicolas.launay@gmail.com', '$2a$10$GYD4FezrD/Fy/kd4bZcnye/2O7iGxgRrKyPCrds3xdibxXMiquWhO', 2, 2),
    ('Franck', 'DOYEN', 'franck.doyen@gmail.com', '$2a$10$AQ90byLmxSwK45LxCFPJ1.mJd6l8jjLQqQOaXyHgs/RTWYOi.6U32', 2, 3);

INSERT INTO loan (start_date, real_end_date, end_date, app_user_id, equipment_id, loan_status) VALUES
    ('2026-05-13', '2026-06-27', '2026-06-27', 3, 1, 'ONGOING'),
    ('2026-10-11', '2026-10-28', '2026-11-05', 3, 2, 'VALIDATED'),
    ('2026-02-27', '2026-02-28', '2026-03-02', 3, 3, 'RETURNED'),
    ('2026-05-09', '2026-05-21', '2026-05-21', 1, 2, 'RETURNED'),
    ('2026-06-12', '2026-06-13', '2026-06-13', 2, 3, 'VALIDATED'),
    ('2026-01-05', '2026-01-07', '2026-01-07', 1, 1, 'RETURNED'),
    ('2026-01-05', '2026-01-07', '2026-01-07', 1, 1, 'RETURNED'),
    ('2026-01-12', '2026-01-15', '2026-01-15', 2, 3, 'RETURNED'),
    ('2026-02-03', '2026-02-05', '2026-02-05', 3, 2, 'RETURNED'),
    ('2026-02-18', '2026-02-20', '2026-02-20', 1, 3, 'RETURNED'),
    ('2026-03-01', '2026-03-04', '2026-03-04', 2, 1, 'RETURNED'),
    ('2026-03-10', '2026-03-12', '2026-03-12', 3, 3, 'RETURNED'),
    ('2026-03-22', '2026-03-25', '2026-03-25', 1, 2, 'RETURNED'),
    ('2026-04-02', '2026-04-05', '2026-04-05', 2, 2, 'RETURNED'),
    ('2026-04-14', '2026-04-16', '2026-04-16', 3, 1, 'RETURNED'),
    ('2026-04-28', '2026-04-30', '2026-04-30', 1, 3, 'RETURNED'),
    ('2026-05-03', '2026-05-06', '2026-05-06', 2, 1, 'RETURNED'),
    ('2026-05-18', '2026-05-20', '2026-05-20', 3, 2, 'RETURNED'),
    ('2026-06-01', '2026-06-03', '2026-06-03', 1, 1, 'VALIDATED'),
    ('2026-06-10', '2026-06-13', '2026-06-13', 2, 3, 'VALIDATED'),
    ('2026-06-22', '2026-06-24', '2026-06-24', 3, 1, 'VALIDATED'),
    ('2026-07-05', '2026-07-07', '2026-07-07', 1, 2, 'VALIDATED'),
    ('2026-07-14', '2026-07-17', '2026-07-17', 2, 3, 'VALIDATED'),
    ('2026-07-29', '2026-07-31', '2026-07-31', 3, 2, 'VALIDATED'),
    ('2026-08-08', '2026-08-10', '2026-08-10', 1, 3, 'VALIDATED'),
    ('2026-08-19', '2026-08-22', '2026-08-22', 2, 1, 'VALIDATED'),
    ('2026-05-22', '2026-05-30', '2026-05-30', 1, 1, 'VALIDATED');;


INSERT INTO type_accreditation (accreditation_id, type_id) VALUES
    (1, 1),
    (5, 3),
    (2, 2);


