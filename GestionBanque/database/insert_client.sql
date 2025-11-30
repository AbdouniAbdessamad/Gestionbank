-- ========================================
-- SCRIPT D'INSERTION DE CLIENTS
-- Gestion Bancaire - GestionBanque
-- ========================================

-- Utiliser la base de données
USE GestionBanque;

-- ========================================
-- AJOUTER UN NOUVEAU CLIENT
-- ========================================

-- Exemple 1: Client avec tous les détails
INSERT INTO Client (nom, email, telephone)
VALUES ('Jean Dupont', 'jean.dupont@email.com', '0612345678');

-- Exemple 2: Client minimal (nom seulement)
INSERT INTO Client (nom)
VALUES ('Marie Bernard');

-- Exemple 3: Plusieurs clients à la fois
INSERT INTO Client (nom, email, telephone) VALUES
('Pierre Moreau', 'pierre.moreau@email.com', '0687654321'),
('Anne Sophie', 'anne.sophie@email.com', '0698765432'),
('Luc Petit', NULL, '0645678901');

-- ========================================
-- CRÉER UN COMPTE POUR UN CLIENT
-- ========================================

-- D'abord, obtenir l'ID du client (remplacer 4 par le vrai ID)
-- Vous pouvez le trouver avec: SELECT client_id FROM Client WHERE nom = 'Jean Dupont';

-- Créer un compte pour Jean Dupont (client_id = 4)
INSERT INTO Compte (code_compte, solde, client_id)
VALUES ('COMP00000004', 5000.00, 4);

-- ========================================
-- VÉRIFIER LES CLIENTS CRÉÉS
-- ========================================

-- Voir tous les clients
SELECT * FROM Client;

-- Voir un client spécifique
SELECT * FROM Client WHERE nom = 'Jean Dupont';

-- Voir les comptes associés aux clients
SELECT 
    c.client_id,
    c.nom,
    c.email,
    c.telephone,
    co.numero_compte,
    co.code_compte,
    co.solde
FROM Client c
LEFT JOIN Compte co ON c.client_id = co.client_id
ORDER BY c.client_id;

-- ========================================
-- SUPPRIMER UN CLIENT (à utiliser avec prudence)
-- ========================================

-- Supprimer Jean Dupont et tous ses comptes/opérations
-- DELETE FROM Client WHERE nom = 'Jean Dupont';
-- (Les opérations seront supprimées par CASCADE)

-- ========================================
-- METTRE À JOUR UN CLIENT
-- ========================================

-- Modifier l'email d'un client
UPDATE Client SET email = 'nouveau.email@example.com' WHERE nom = 'Jean Dupont';

-- Modifier le téléphone
UPDATE Client SET telephone = '0699999999' WHERE client_id = 4;

-- ========================================
-- AJOUTER DES OPÉRATIONS AU COMPTE
-- ========================================

-- Dépôt de 1000 DH sur le compte de Jean Dupont
-- (remplacer 4 par le numéro du compte réel)
INSERT INTO Operation (numero_compte, type_operation, montant, date_operation)
VALUES (4, 'DEPOT', 1000.00, NOW());

-- Retrait de 500 DH
INSERT INTO Operation (numero_compte, type_operation, montant, date_operation)
VALUES (4, 'RETRAIT', 500.00, NOW());

-- ========================================
-- COMMANDES UTILES POUR VÉRIFICATION
-- ========================================

-- Voir le nombre total de clients
SELECT COUNT(*) as nombre_clients FROM Client;

-- Voir le nombre total de comptes
SELECT COUNT(*) as nombre_comptes FROM Compte;

-- Voir le nombre total d'opérations
SELECT COUNT(*) as nombre_operations FROM Operation;

-- Voir les clients avec leurs soldes
SELECT 
    c.nom,
    co.code_compte,
    co.solde,
    COUNT(op.operation_id) as nombre_operations
FROM Client c
LEFT JOIN Compte co ON c.client_id = co.client_id
LEFT JOIN Operation op ON co.numero_compte = op.numero_compte
GROUP BY c.client_id, c.nom, co.code_compte, co.solde;

-- ========================================
-- EXEMPLE COMPLET: AJOUTER UN CLIENT AVEC COMPTE
-- ========================================

-- 1. Ajouter le client
INSERT INTO Client (nom, email, telephone)
VALUES ('Sophie Martin', 'sophie.martin@email.com', '0645123456');

-- 2. Récupérer son ID (noter le dernier ID généré)
-- SELECT LAST_INSERT_ID() as nouveau_client_id;

-- 3. Créer son compte (remplacer X par l'ID obtenu)
-- INSERT INTO Compte (code_compte, solde, client_id)
-- VALUES ('COMP00000005', 3000.00, X);

-- ========================================
-- DONNÉES DE TEST (À EXÉCUTER APRÈS schema.sql)
-- ========================================

-- Ces clients existent déjà dans schema.sql:
-- - Alice Martin (COMP00000001, solde: 1000)
-- - Bob Dupont (COMP00000002, solde: 2000)
-- - Charlie Leblanc (COMP00000003, solde: 1500)

-- Vous pouvez les vérifier avec:
SELECT * FROM Client;
SELECT * FROM Compte;
SELECT * FROM Operation;
