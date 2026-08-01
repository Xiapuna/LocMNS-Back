INSERT INTO loan_state (name) VALUES
    ('VALIDATED'),
    ('ONGOING'),
    ('REQUESTED_EXTENSION'),
    ('REQUESTED_RETURN'),
    ('RETURNED');

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
    ('Salle 204'),
    ('Salle 205'),
    ('Salle 110'),
    ('Salle 111'),
    ('Salle Réunion A'),
    ('Salle Réunion B'),
    ('Open Space 1'),
    ('Open Space 2'),
    ('Local Stockage'),
    ('Atelier Électronique');


INSERT INTO type (name) VALUES
    ('Vidéo-projecteur'),
    ('Enceinte Bluetooth'),
    ('Switch réseau'),
    ('Routeur WiFi'),
    ('Trépied photo'),
    ('Clavier mécanique'),
    ('Souris ergonomique'),
    ('Batterie externe'),
    ('Lampe LED'),
    ('Scanner portable');


INSERT INTO request (date, content) VALUES
    ('27 février 2026', 'Bonjour, l''écran de l''ordinateur portable ne s''allume plus'),
    ('14 février 2023', 'Bonjour, Jen''arrive plus à utiliser l''écran'),
    ('27 février 2026', 'Bonjour, Le fil du chargeur est dénudé');

INSERT INTO documentation (name, link) VALUES
   ('Guide Epson EB-X41', 'https://doc-epson-x41.com'),
   ('Notice JBL Charge 5', 'https://doc-jbl-charge5.com'),
   ('Guide Netgear GS308', 'https://doc-netgear-gs308.com'),
   ('Manuel TP-Link Archer C6', 'https://doc-tplink-c6.com'),
   ('Guide Manfrotto Compact', 'https://doc-manfrotto-compact.com'),
   ('Notice Logitech MX Keys', 'https://doc-logitech-mxkeys.com'),
   ('Guide Logitech MX Master 3', 'https://doc-logitech-mxmaster3.com'),
   ('Notice Anker PowerCore 20k', 'https://doc-anker-powercore.com'),
   ('Guide Philips Hue Go', 'https://doc-philips-huego.com'),
   ('Notice Epson WorkForce ES-50', 'https://doc-epson-es50.com');


INSERT INTO model (name, description, documentation_id, type_id) VALUES
    ('Epson EB-X41', 'Vidéo-projecteur polyvalent pour salles de classe', 1, 1),
    ('JBL Charge 5', 'Enceinte Bluetooth puissante et portable', 2, 2),
    ('Netgear GS308', 'Switch réseau 8 ports non manageable', 3, 3),
    ('TP-Link Archer C6', 'Routeur WiFi double bande performant', 4, 4),
    ('Manfrotto Compact', 'Trépied photo léger et robuste', 5, 5),
    ('Logitech MX Keys', 'Clavier mécanique silencieux haut de gamme', 6, 6),
    ('Logitech MX Master 3', 'Souris ergonomique professionnelle', 7, 7),
    ('Anker PowerCore 20k', 'Batterie externe haute capacité', 8, 8),
    ('Philips Hue Go', 'Lampe LED portable connectée', 9, 9),
    ('Epson WorkForce ES-50', 'Scanner portable compact et rapide', 10, 10);


INSERT INTO equipment (name, condition, location_id, model_id) VALUES
    ('Vidéo-projecteur n°3001', 'Très bon état', 4, 1),
    ('Vidéo-projecteur n°3002', 'Bon état', 5, 1),
    ('Enceinte Bluetooth n°4001', 'État neuf', 6, 2),
    ('Enceinte Bluetooth n°4002', 'Très bon état', 7, 2),
    ('Switch réseau n°5001', 'Bon état', 8, 3),
    ('Switch réseau n°5002', 'Très bon état', 9, 3),
    ('Routeur WiFi n°6001', 'État neuf', 10, 4),
    ('Routeur WiFi n°6002', 'Bon état', 1, 4),
    ('Trépied photo n°7001', 'Très bon état', 2, 5),
    ('Trépied photo n°7002', 'Bon état', 3, 5),
    ('Clavier mécanique n°8001', 'État neuf', 4, 6),
    ('Clavier mécanique n°8002', 'Très bon état', 5, 6),
    ('Souris ergonomique n°9001', 'Très bon état', 6, 7),
    ('Souris ergonomique n°9002', 'Bon état', 7, 7),
    ('Batterie externe n°10001', 'État neuf', 8, 8),
    ('Batterie externe n°10002', 'Très bon état', 9, 8),
    ('Lampe LED n°11001', 'Très bon état', 10, 9),
    ('Lampe LED n°11002', 'Bon état', 1, 9),
    ('Scanner portable n°12001', 'État neuf', 2, 10),
    ('Scanner portable n°12002', 'Très bon état', 3, 10);


INSERT INTO app_user (first_name, name, email, password, role_id, accreditation_id) VALUES
    ('Celia', 'GODFRIN', 'celia.godfrin@gmail.com', '$2a$10$unnTseHmf2ID7q78sMYjX.bKWuNA8SneNyAMPnyXhujzuLqXg7rbG', 1, 5),
    ('Nicolas', 'LAUNAY', 'nicolas.launay@gmail.com', '$2a$10$GYD4FezrD/Fy/kd4bZcnye/2O7iGxgRrKyPCrds3xdibxXMiquWhO', 2, 2),
    ('Franck', 'DOYEN', 'franck.doyen@gmail.com', '$2a$10$AQ90byLmxSwK45LxCFPJ1.mJd6l8jjLQqQOaXyHgs/RTWYOi.6U32', 2, 3);

INSERT INTO loan (start_date, real_end_date, end_date, app_user_id, equipment_id) VALUES
    ('2026-05-13', '2026-06-27', '2026-06-27', 3, 1),
    ('2026-10-11', '2026-10-28', '2026-11-05', 3, 2),
    ('2026-02-27', '2026-02-28', '2026-03-02', 3, 3),
    ('2026-05-09', '2026-05-21', '2026-05-21', 1, 2),
    ('2026-06-12', '2026-06-13', '2026-06-13', 2, 3),
    ('2026-01-05', '2026-01-07', '2026-01-07', 1, 1),
    ('2026-01-05', '2026-01-07', '2026-01-07', 1, 1),
    ('2026-01-12', '2026-01-15', '2026-01-15', 2, 3),
    ('2026-02-03', '2026-02-05', '2026-02-05', 3, 2),
    ('2026-02-18', '2026-02-20', '2026-02-20', 1, 3),
    ('2026-03-01', '2026-03-04', '2026-03-04', 2, 1),
    ('2026-03-10', '2026-03-12', '2026-03-12', 3, 3),
    ('2026-03-22', '2026-03-25', '2026-03-25', 1, 2),
    ('2026-04-02', '2026-04-05', '2026-04-05', 2, 2),
    ('2026-04-14', '2026-04-16', '2026-04-16', 3, 1),
    ('2026-04-28', '2026-04-30', '2026-04-30', 1, 3),
    ('2026-05-03', '2026-05-06', '2026-05-06', 2, 1),
    ('2026-05-18', '2026-05-20', '2026-05-20', 3, 2),
    ('2026-06-01', '2026-06-03', '2026-06-03', 1, 1),
    ('2026-06-10', '2026-06-13', '2026-06-13', 2, 3),
    ('2026-06-22', '2026-06-24', '2026-06-24', 3, 1),
    ('2026-07-05', '2026-07-07', '2026-07-07', 1, 2),
    ('2026-07-14', '2026-07-17', '2026-07-17', 2, 3),
    ('2026-07-29', '2026-07-31', '2026-07-31', 3, 2),
    ('2026-08-08', '2026-08-10', '2026-08-10', 1, 3),
    ('2026-08-19', '2026-08-22', '2026-08-22', 2, 1),
    ('2026-05-22', '2026-05-30', '2026-05-30', 1, 1);


INSERT INTO type_accreditation (accreditation_id, type_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (1, 6),
    (2, 7),
    (3, 8),
    (4, 9),
    (5, 10);

INSERT INTO loan_history (date_changement, loan_id, loan_state_id) VALUES
   ('2026-05-13 10:00:00', 1, 2),  -- ONGOING
   ('2026-10-11 10:00:00', 2, 1),  -- VALIDATED
   ('2026-02-27 10:00:00', 3, 5),  -- RETURNED
   ('2026-05-09 10:00:00', 4, 5),  -- RETURNED
   ('2026-06-12 10:00:00', 5, 1),  -- VALIDATED
   ('2026-01-05 10:00:00', 6, 5),  -- RETURNED
   ('2026-01-05 10:00:00', 7, 5),  -- RETURNED
   ('2026-01-12 10:00:00', 8, 5),  -- RETURNED
   ('2026-02-03 10:00:00', 9, 5),  -- RETURNED
   ('2026-02-18 10:00:00', 10, 5), -- RETURNED
   ('2026-03-01 10:00:00', 11, 5), -- RETURNED
   ('2026-03-10 10:00:00', 12, 5), -- RETURNED
   ('2026-03-22 10:00:00', 13, 5), -- RETURNED
   ('2026-04-02 10:00:00', 14, 5), -- RETURNED
   ('2026-04-14 10:00:00', 15, 5), -- RETURNED
   ('2026-04-28 10:00:00', 16, 5), -- RETURNED
   ('2026-05-03 10:00:00', 17, 5), -- RETURNED
   ('2026-05-18 10:00:00', 18, 5), -- RETURNED
   ('2026-06-01 10:00:00', 19, 1), -- VALIDATED
   ('2026-06-10 10:00:00', 20, 1), -- VALIDATED
   ('2026-06-22 10:00:00', 21, 1), -- VALIDATED
   ('2026-07-05 10:00:00', 22, 1), -- VALIDATED
   ('2026-07-14 10:00:00', 23, 1), -- VALIDATED
   ('2026-07-29 10:00:00', 24, 1), -- VALIDATED
   ('2026-08-08 10:00:00', 25, 1), -- VALIDATED
   ('2026-08-19 10:00:00', 26, 1), -- VALIDATED
   ('2026-05-22 10:00:00', 27, 1); -- VALIDATED

