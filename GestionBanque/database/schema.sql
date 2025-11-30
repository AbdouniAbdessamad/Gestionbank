-- ========================================
-- SCRIPT DE CRÉATION DE LA BASE DE DONNÉES
-- Gestion Bancaire
-- ========================================

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS GestionBanque;
USE GestionBanque;

-- ========================================
-- TABLE ADMIN
-- ========================================
CREATE TABLE IF NOT EXISTS Admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'ADMIN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ========================================
-- TABLE CLIENT
-- ========================================
CREATE TABLE IF NOT EXISTS Client (
    client_id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    telephone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ========================================
-- TABLE COMPTE
-- ========================================
CREATE TABLE IF NOT EXISTS Compte (
    numero_compte INT AUTO_INCREMENT PRIMARY KEY,
    code_compte VARCHAR(50) UNIQUE NOT NULL,
    solde DECIMAL(15, 2) DEFAULT 0.00,
    client_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES Client(client_id) ON DELETE CASCADE
);

-- ========================================
-- TABLE OPERATION
-- ========================================
CREATE TABLE IF NOT EXISTS Operation (
    operation_id INT AUTO_INCREMENT PRIMARY KEY,
    numero_compte INT NOT NULL,
    type_operation VARCHAR(50),
    montant DECIMAL(15, 2),
    date_operation DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (numero_compte) REFERENCES Compte(numero_compte) ON DELETE CASCADE
);

-- ========================================
-- INDEX POUR AMÉLIORER LES PERFORMANCES
-- ========================================
CREATE INDEX idx_client_nom ON Client(nom);
CREATE INDEX idx_compte_code ON Compte(code_compte);
CREATE INDEX idx_compte_client ON Compte(client_id);
CREATE INDEX idx_operation_numero ON Operation(numero_compte);
CREATE INDEX idx_operation_date ON Operation(date_operation);

-- ========================================
-- DONNÉES DE TEST
-- ========================================

-- Admin par défaut (username: admin, password: admin123)
INSERT INTO Admin (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');

-- Exemple de clients
INSERT INTO Client (nom, email, telephone) VALUES 
('Alice Martin', 'alice.martin@email.com', '0612345678'),
('Bob Dupont', 'bob.dupont@email.com', '0623456789'),
('Charlie Leblanc', 'charlie.leblanc@email.com', '0634567890');

-- Comptes pour les clients
INSERT INTO Compte (code_compte, solde, client_id) VALUES 
('COMP00000001', 5000.00, 1),
('COMP00000002', 3500.75, 2),
('COMP00000003', 10000.00, 3);

-- Quelques opérations d'exemple
INSERT INTO Operation (numero_compte, type_operation, montant, date_operation) VALUES 
(1, 'CREATION', 5000.00, NOW()),
(1, 'DEPOT', 1000.00, DATE_SUB(NOW(), INTERVAL 5 DAY)),
(2, 'CREATION', 3500.75, NOW()),
(2, 'RETRAIT', 500.00, DATE_SUB(NOW(), INTERVAL 3 DAY)),
(3, 'CREATION', 10000.00, NOW());

-- ========================================
-- AFFICHER LES TABLES CRÉÉES
-- ========================================
SELECT 'Base de données GestionBanque créée avec succès !' AS Status;
SHOW TABLES;
