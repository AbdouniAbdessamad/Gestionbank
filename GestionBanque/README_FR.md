# GestionBanque - Application Bancaire de Bureau

Une application Java Swing pour gérer les comptes bancaires avec une base de données MySQL.

## 📋 Fonctionnalités

### Pour l'Admin
- ✓ Se connecter avec username/password
- ✓ Créer de nouveaux clients et leurs comptes bancaires
- ✓ Consulter la liste de tous les clients
- ✓ Consulter la liste de tous les comptes
- ✓ Consulter toutes les opérations de la banque

### Pour les Clients
- ✓ Se connecter avec nom et code de compte
- ✓ Consulter le solde de son compte
- ✓ Effectuer un dépôt
- ✓ Effectuer un retrait
- ✓ Consulter l'historique des opérations
- ✓ (Optionnel) Effectuer un virement

## 🏗️ Architecture

```
ma/banque/
├── model/              # Classes métier
│   ├── Admin.java
│   ├── Client.java
│   ├── Compte.java
│   └── Operation.java
│
├── service/           # Couche métier (JDBC pur, pas de DAO)
│   ├── AuthService.java          # Authentification
│   └── BanqueService.java        # Gestion des opérations bancaires
│
├── ui/                # Interface utilisateur Swing
│   ├── UIHelper.java             # Utilitaires UI (styles, couleurs, etc.)
│   ├── Main.java                 # Point d'entrée
│   ├── HomeView.java             # Accueil
│   ├── LoginAdminView.java       # Connexion admin
│   ├── LoginClientView.java      # Connexion client
│   ├── RegisterClientView.java   # Enregistrement client
│   ├── MenuAdminView.java        # Menu admin
│   ├── MenuClientView.java       # Menu client
│   ├── FormConsulterSolde.java   # Consultation du solde
│   ├── FormDeposer.java          # Formulaire dépôt
│   ├── FormRetirer.java          # Formulaire retrait
│   └── FormHistorique.java       # Historique des opérations
│
└── utils/
    ├── DBConnection.java         # Gestion de la connexion MySQL
    └── DataStore.java            # Stockage des données de session
```

## 🛠️ Installation

### Prérequis
- **Java 11+** (avec JDK)
- **MySQL Server** (5.7+)
- **Driver MySQL** (mysql-connector-java.jar)

### Étapes d'installation

#### 1. Installer la base de données

Ouvrez MySQL CLI et exécutez le script :

```sql
source /chemin/vers/schema.sql
```

Ou copiez-collez le contenu du fichier `schema.sql` dans MySQL Workbench.

#### 2. Configurer la connexion

Modifiez les paramètres dans `src/utils/DBConnection.java` :

```java
private static final String URL = "jdbc:mysql://localhost:3306/GestionBanque?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = ""; // Votre mot de passe MySQL
```

#### 3. Ajouter le driver MySQL au classpath

Le fichier `mysql-connector-java.jar` doit être dans le dossier `lib/`.

#### 4. Compiler et exécuter

```bash
# Compilez le projet
javac -cp lib/mysql-connector-java.jar:. src/**/*.java

# Testez la connexion
java -cp lib/mysql-connector-java.jar:src ma.banque.TestConnexion

# Lancez l'application
java -cp lib/mysql-connector-java.jar:src ma.banque.ui.Main
```

## 📝 Données de Test Par Défaut

Après exécution du script `schema.sql`, vous avez :

### Admin
- **Username**: `admin`
- **Password**: `admin123`

### Clients (exemples)
- **Alice Martin** - Code: `COMP00000001` - Solde: 5000 DH
- **Bob Dupont** - Code: `COMP00000002` - Solde: 3500,75 DH
- **Charlie Leblanc** - Code: `COMP00000003` - Solde: 10000 DH

## 🎯 Utilisation

### Navigation Admin
1. Lancez l'app → Cliquez "Je suis Agent Bancaire"
2. Username: `admin` | Password: `admin123`
3. Menu admin :
   - Créer un nouveau client
   - Voir tous les clients
   - Voir tous les comptes
   - Voir toutes les opérations

### Navigation Client
1. Lancez l'app → Cliquez "Je suis Client"
2. Nom: `Alice Martin` | Code: `COMP00000001`
3. Menu client :
   - Consulter solde
   - Faire un dépôt
   - Faire un retrait
   - Voir historique

### Créer un nouveau client
1. Écran d'accueil → "Créer un Compte Client"
2. Remplissez les infos
3. Le système génère un code de compte unique (format: `COMP00000XXX`)

## 🏛️ Architecture Base de Données

### Tables

**Admin**
```
admin_id (PK) | username (UNIQUE) | password | role | created_at
```

**Client**
```
client_id (PK) | nom | email | telephone | created_at
```

**Compte**
```
numero_compte (PK) | code_compte (UNIQUE) | solde | client_id (FK)
```

**Operation**
```
operation_id (PK) | numero_compte (FK) | type_operation | montant | date_operation
```

## 🔒 Sécurité

- Les mots de passe sont stockés en clair (à améliorer avec du hashing en production)
- Les connexions utilisent JDBC avec requêtes paramétrées (protection SQL Injection)
- Les transactions sont gérées pour garantir la cohérence des données

## 🎨 Interface

- **Look & Feel Moderne**: Swing avec couleurs cohérentes
- **Responsif**: Windows centrées, boutons hover effects
- **Messages clairs**: Dialogues d'erreur et succès
- **Gestion des erreurs**: Try-catch avec logs

## 📄 Fichiers Importants

- `schema.sql` - Script de création de la BD
- `src/utils/DBConnection.java` - Configuration de la connexion
- `src/ma/banque/ui/Main.java` - Point d'entrée
- `README.md` - Ce fichier

## 🚀 Améliorations Possibles

- [ ] Hashing des mots de passe (BCrypt)
- [ ] Virement entre comptes
- [ ] Gestion des clôtures de compte
- [ ] Logging (SLF4J, Log4j)
- [ ] Tests unitaires (JUnit)
- [ ] Interface graphique améliorée (FlatLaf)
- [ ] Export en PDF des relevés
- [ ] Interface web (Spring Boot)

## 📞 Support

Pour toute question ou problème :
1. Vérifiez que MySQL est démarré
2. Vérifiez les paramètres dans `DBConnection.java`
3. Consultez les logs dans la console
4. Exécutez `TestConnexion` pour tester la connexion BD

## 📜 Licence

Projet étudiant - 2025

---

**Bon usage ! 🎉**
