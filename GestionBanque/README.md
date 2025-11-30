# Gestion Bancaire - Application Java Swing

## 📋 Description
Application de gestion bancaire développée en **Java Swing** avec **MySQL**. Elle permet aux administrateurs de gérer les clients et leurs comptes, et aux clients de consulter leur solde et effectuer des opérations bancaires.

## ✨ Fonctionnalités

### Pour l'Admin
- 👤 Se connecter avec username/password
- ➕ Créer de nouveaux clients
- 💳 Créer des comptes bancaires avec codes auto-générés
- 👥 Voir la liste de tous les clients
- 🏦 Voir tous les comptes de la banque
- 📊 Consulter l'historique de toutes les opérations

### Pour le Client
- 👤 Se connecter avec son nom et code de compte
- 💰 Consulter le solde de son compte
- 💵 Effectuer un dépôt
- 🏧 Effectuer un retrait
- 📈 Voir l'historique de ses opérations

## 🏗️ Architecture

```
ma/banque/
├── model/              # Modèles de données
│   ├── Admin.java
│   ├── Client.java
│   ├── Compte.java
│   └── Operation.java
│
├── service/           # Logique métier (SANS DAO)
│   ├── AuthService.java      # Authentification
│   └── BanqueService.java    # Gestion comptes/opérations
│
├── ui/                # Interface utilisateur Swing
│   ├── Main.java
│   ├── HomeView.java
│   ├── LoginAdminView.java
│   ├── LoginClientView.java
│   ├── RegisterClientView.java
│   ├── MenuAdminView.java
│   ├── MenuClientView.java
│   ├── FormConsulterSolde.java
│   ├── FormDeposer.java
│   ├── FormRetirer.java
│   ├── FormHistorique.java
│   ├── FormCreerCompte.java
│   ├── UIHelper.java         # Utilitaires UI
│   └── assets/
│
└── utils/             # Classes utilitaires
    ├── DBConnection.java     # Gestion connexion MySQL
    └── DataStore.java        # Session utilisateur
```

## 🗄️ Base de Données

### Schéma MySQL

```sql
CREATE DATABASE GestionBanque;

CREATE TABLE Admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    role VARCHAR(20) DEFAULT 'ADMIN'
);

CREATE TABLE Client (
    client_id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100),
    email VARCHAR(100),
    telephone VARCHAR(20)
);

CREATE TABLE Compte (
    numero_compte INT AUTO_INCREMENT PRIMARY KEY,
    code_compte VARCHAR(50) UNIQUE,
    solde DECIMAL(10,2) DEFAULT 0,
    client_id INT,
    FOREIGN KEY (client_id) REFERENCES Client(client_id)
);

CREATE TABLE Operation (
    operation_id INT AUTO_INCREMENT PRIMARY KEY,
    numero_compte INT,
    type_operation VARCHAR(20),
    montant DECIMAL(10,2),
    date_operation DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (numero_compte) REFERENCES Compte(numero_compte)
);
```

### Insérer un admin pour tester

```sql
INSERT INTO Admin(username, password, role) VALUES ('admin', 'admin123', 'ADMIN');
```

## 🚀 Guide de Démarrage

### Prérequis
- **Java 11+** installé
- **MySQL** installé et démarré
- **Driver MySQL** dans le classpath (`mysql-connector-java`)

### Étapes

1. **Configurer la BD** :
   - Créer la base `GestionBanque`
   - Exécuter le script SQL ci-dessus
   - Ajouter un admin : `INSERT INTO Admin(username, password, role) VALUES ('admin', 'admin123', 'ADMIN');`

2. **Adapter les paramètres de connexion** :
   - Ouvrir `src/utils/DBConnection.java`
   - Modifier `URL`, `USER`, `PASSWORD` selon votre configuration MySQL

3. **Tester la connexion** :
   ```bash
   javac src/ma/banque/TestConnexion.java
   java -cp src ma.banque.TestConnexion
   ```

4. **Lancer l'application** :
   ```bash
   javac -cp src src/ma/banque/ui/Main.java
   java -cp src ma.banque.ui.Main
   ```

## 📖 Utilisation

### Écran d'Accueil
- **Je suis Client** : Connexion client (nom + code de compte)
- **Je suis Agent Bancaire** : Connexion admin (username + password)
- **Créer un Compte Client** : Créer un nouveau client via formulaire

### Admin - Créer un Client
1. Cliquer sur "Créer un nouveau client"
2. Remplir : Nom, Email, Téléphone, Solde initial
3. Un **code de compte** est auto-généré (ex: COMP12345678)
4. Le code est fourni au client pour se connecter

### Client - Opérations
1. Se connecter avec **Nom** et **Code de Compte**
2. Consulter solde
3. Déposer/Retirer de l'argent
4. Voir l'historique complet

## 🎨 Interface

- **Couleur primaire** : Bleu (#3498DB)
- **Design moderne** : Boutons avec hover effects, champs stylisés
- **Responsive** : Fenêtres centrées sur écran

## 🔒 Sécurité

- ⚠️ Les mots de passe sont stockés en clair dans la BD (à améliorer avec bcrypt pour production)
- ✅ Validation des entrées utilisateur
- ✅ Gestion des erreurs avec messages utilisateur clairs

## 📝 Notes de Développement

- **Architecture sans DAO** : Toute la logique BD est dans `AuthService` et `BanqueService`
- **JDBC pur** : Pas de framework ORM
- **Transactions** : Gestion appropriée des transactions pour opérations critiques
- **DataStore** : Gère la session de l'utilisateur connecté

## 🐛 Troubleshooting

### "Connection refused"
→ Vérifier que MySQL est lancé et l'adresse URL dans `DBConnection.java`

### "Base de données non trouvée"
→ Créer la base `GestionBanque` et exécuter le schéma SQL

### "Driver MySQL non trouvé"
→ Ajouter `mysql-connector-java` au classpath du projet

### Erreur lors des dépôts/retraits
→ Vérifier que le compte existe et que le client est bien connecté

## 📦 Dépendances

- **Java SE** (standard)
- **MySQL JDBC Driver** (mysql-connector-java)

## 👨‍💼 Auteur

Projet bancaire - Gestion Bancaire Informatisée

## 📄 Licence

Libre d'utilisation à titre éducatif.

---

**✅ Prêt à l'emploi !** L'application est complètement fonctionnelle. Consultez les sections ci-dessus pour démarrer.
