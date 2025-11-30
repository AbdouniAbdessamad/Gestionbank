# GUIDE DE DÉMARRAGE - GestionBanque

## ✅ Checklist Avant de Démarrer

- [ ] MySQL est installé et démarré
- [ ] Base de données `GestionBanque` créée
- [ ] Driver MySQL (`mysql-connector-java.jar`) dans le dossier `lib/`
- [ ] Java 11+ installé
- [ ] IDE ou terminal pour exécuter les commandes

---

## 🚀 DÉMARRAGE RAPIDE

### Option 1: Avec les scripts (Linux/Mac)

```bash
# 1. Créer la base de données
mysql -u root -p < schema.sql

# 2. Compiler le projet
bash compile.sh

# 3. Lancer l'application
bash run.sh
```

### Option 2: Avec IntelliJ IDEA (Recommandé)

1. **Ouvrir le projet**
   - File → Open → Sélectionnez le dossier `GestionBanque`

2. **Configurer le classpath**
   - File → Project Structure → Libraries
   - Ajoutez `mysql-connector-java.jar` du dossier `lib/`

3. **Créer la BD**
   - Ouvrez MySQL Workbench ou MySQL CLI
   - Exécutez le contenu de `schema.sql`

4. **Configurer la connexion** (si nécessaire)
   - Ouvrez `src/utils/DBConnection.java`
   - Modifiez les paramètres (URL, USER, PASSWORD)

5. **Tester la connexion**
   - Faites un clic droit sur `TestConnexion.java`
   - Cliquez "Run"

6. **Lancer l'application**
   - Faites un clic droit sur `Main.java`
   - Cliquez "Run"

### Option 3: Ligne de commande (Windows/Linux/Mac)

```bash
# Naviguer dans le dossier du projet
cd /chemin/vers/GestionBanque

# Créer la base de données
mysql -u root -p < schema.sql
# Entrez votre mot de passe MySQL

# Compiler
javac -d build -cp "lib/*:." \
  src/utils/*.java \
  src/ma/banque/model/*.java \
  src/ma/banque/service/*.java \
  src/ma/banque/ui/*.java \
  src/ma/banque/*.java

# Tester la connexion
java -cp "lib/*:build" ma.banque.TestConnexion

# Lancer l'application
java -cp "lib/*:build" ma.banque.ui.Main
```

---

## 🔑 Accès de Test

Après l'exécution de `schema.sql`, utilisez ces identifiants :

### Admin
```
Nom d'utilisateur: admin
Mot de passe: admin123
```

### Clients (Exemples)
```
Nom: Alice Martin
Code: COMP00000001

Nom: Bob Dupont
Code: COMP00000002

Nom: Charlie Leblanc
Code: COMP00000003
```

---

## 🛠️ Paramètres de Configuration

### Fichier: `src/utils/DBConnection.java`

```java
// Modifier ces paramètres selon votre setup MySQL
private static final String URL = "jdbc:mysql://localhost:3306/GestionBanque?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";          // Votre utilisateur MySQL
private static final String PASSWORD = "";         // Votre mot de passe MySQL
```

---

## ❌ Dépannage

### Erreur: "Unable to load authentication plugin"

**Solution:**
```java
// Dans DBConnection.java, changez:
// De:
jdbc:mysql://localhost:3306/GestionBanque

// À:
jdbc:mysql://localhost:3306/GestionBanque?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
```

### Erreur: "Access denied for user 'root'"

**Solutions:**
1. Vérifiez le mot de passe MySQL dans `DBConnection.java`
2. Testez la connexion directement:
   ```bash
   mysql -u root -p -e "SELECT 1;"
   ```

### Erreur: "Database 'GestionBanque' doesn't exist"

**Solution:**
```bash
mysql -u root -p < schema.sql
```

### Erreur: "mysql-connector-java.jar not found"

**Solution:**
1. Téléchargez le driver depuis:
   https://dev.mysql.com/downloads/connector/j/
2. Placez le JAR dans le dossier `lib/`
3. Recompile le projet

---

## 📊 Structure des Données

### Hiérarchie Utilisateurs

```
┌─────────────────────────────────┐
│      UTILISATEUR                │
├─────────────────────────────────┤
│ Admin (username/password)       │
│ └─ Gère toute la banque        │
│                                 │
│ Client (nom/code_compte)        │
│ └─ Accède à son compte(s)       │
└─────────────────────────────────┘
```

### Flux des Opérations

```
Client
  ├─ Dépôt     → +montant au solde
  ├─ Retrait   → -montant au solde (avec vérification)
  ├─ Virement  → vers un autre compte
  └─ Historique → toutes les opérations

Admin
  ├─ Créer client → nouveau compte généré
  ├─ Voir clients → tableau de tous les clients
  ├─ Voir comptes → tableau de tous les comptes
  └─ Voir opérations → tableau de toutes les opérations
```

---

## 📝 Journaux et Logs

Tous les logs de la BD sont affichés en console. Cherchez:

```
✓ Connexion à la base de données réussie !
✓ [Opération] Dépôt de 1000.00 DH effectué
✗ Erreur lors de [Opération]: [Message]
```

---

## 📦 Contenu du Projet

```
GestionBanque/
├── src/
│   ├── utils/
│   │   ├── DBConnection.java
│   │   └── DataStore.java
│   ├── ma/banque/
│   │   ├── model/
│   │   │   ├── Admin.java
│   │   │   ├── Client.java
│   │   │   ├── Compte.java
│   │   │   └── Operation.java
│   │   ├── service/
│   │   │   ├── AuthService.java
│   │   │   └── BanqueService.java
│   │   ├── ui/
│   │   │   ├── UIHelper.java
│   │   │   ├── Main.java
│   │   │   ├── HomeView.java
│   │   │   ├── LoginAdminView.java
│   │   │   ├── LoginClientView.java
│   │   │   ├── RegisterClientView.java
│   │   │   ├── MenuAdminView.java
│   │   │   ├── MenuClientView.java
│   │   │   ├── FormConsulterSolde.java
│   │   │   ├── FormDeposer.java
│   │   │   ├── FormRetirer.java
│   │   │   └── FormHistorique.java
│   │   └── TestConnexion.java
│   └── ...
├── lib/
│   └── mysql-connector-java.jar    (À télécharger)
├── schema.sql                      (Script de création BD)
├── compile.sh                      (Script de compilation)
├── run.sh                          (Script de lancement)
├── README.md                       (Documentation)
├── README_FR.md                    (Documentation en français)
└── INSTALLATION.md                 (Ce fichier)
```

---

## 🎯 Prochaines Étapes

1. ✅ Cloner/télécharger le projet
2. ✅ Installer MySQL et le driver Java
3. ✅ Exécuter `schema.sql` pour créer la BD
4. ✅ Compiler avec `compile.sh` ou votre IDE
5. ✅ Lancer `TestConnexion` pour vérifier
6. ✅ Lancer l'application avec `Main.java`
7. ✅ Tester avec les identifiants par défaut
8. ✅ Créer des nouveaux comptes clients
9. ✅ Tester les opérations (dépôt, retrait)

---

## 🆘 Besoin d'Aide?

### Vérifications à faire:

1. **MySQL est-il démarré?**
   ```bash
   mysql -u root -p -e "SELECT 'MySQL OK';"
   ```

2. **La base existe-t-elle?**
   ```bash
   mysql -u root -p -e "USE GestionBanque; SHOW TABLES;"
   ```

3. **Le driver est-il au bon endroit?**
   ```bash
   ls -la lib/mysql-connector-java.jar
   ```

4. **Java est-il correctement installé?**
   ```bash
   java -version
   ```

---

**Bon développement! 🎉**
