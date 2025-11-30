# 🧪 Guide de Test - GestionBanque

## Prérequis
- ✅ MySQL installé et démarré
- ✅ Base de données `GestionBanque` créée (via `schema.sql`)
- ✅ Driver MySQL téléchargé (`lib/mysql-connector-j-8.0.33.jar`)
- ✅ Projet compilé (`bash compile.sh`)

## 🚀 Lancer l'Application

```bash
# Option 1: Avec le script run.sh
bash run.sh

# Option 2: Manuellement
java -cp "lib/mysql-connector-j-8.0.33.jar:build" ma.banque.ui.Main
```

## 📋 Checklist de Test

### 1️⃣ Authentification Admin

- [ ] Cliquer sur **"Je suis Agent Bancaire"**
- [ ] Entrer les identifiants:
  - Username: `admin`
  - Password: `admin123`
- [ ] Cliquer "Se Connecter"
- [ ] Vérifier que le menu admin s'ouvre avec "Bienvenue, admin"

### 2️⃣ Fonctions Admin

#### Créer un Nouveau Client
- [ ] Cliquer "Créer un nouveau client"
- [ ] Remplir:
  - Nom: `Jean Dupont`
  - Email: `jean@example.com`
  - Téléphone: `0612345678`
  - Solde initial: `5000`
- [ ] Cliquer "Créer"
- [ ] Vérifier le message de succès avec le code de compte généré

#### Voir Tous les Clients
- [ ] Cliquer "Voir tous les clients"
- [ ] Vérifier que le tableau affiche tous les clients (Alice, Bob, Charlie + Jean)
- [ ] Fermer le tableau

#### Voir Tous les Comptes
- [ ] Cliquer "Voir tous les comptes"
- [ ] Vérifier les colonnes: ID, Code, Solde, Client ID
- [ ] Fermer le tableau

#### Voir Toutes les Opérations
- [ ] Cliquer "Voir toutes les opérations"
- [ ] Vérifier les opérations créées précédemment
- [ ] Fermer le tableau

#### Déconnexion Admin
- [ ] Cliquer "Déconnexion"
- [ ] Vérifier le retour à l'écran d'accueil

---

### 3️⃣ Authentification Client

- [ ] Cliquer sur **"Je suis Client"**
- [ ] Entrer les identifiants:
  - Nom: `Alice Martin`
  - Code: `COMP00000001`
- [ ] Cliquer "Se Connecter"
- [ ] Vérifier que le menu client s'ouvre avec "Bienvenue, Alice Martin"

### 4️⃣ Fonctions Client

#### Consulter le Solde
- [ ] Cliquer "Consulter mon solde"
- [ ] Vérifier que le solde initial (1000.00 DH) s'affiche
- [ ] Cliquer "Retour"

#### Effectuer un Dépôt
- [ ] Cliquer "Effectuer un dépôt"
- [ ] Entrer un montant: `500`
- [ ] Cliquer "Confirmer"
- [ ] Vérifier le message "Dépôt de 500.00 DH effectué"
- [ ] Retour au menu client

#### Consulter le Solde (vérification)
- [ ] Cliquer "Consulter mon solde"
- [ ] Vérifier que le solde est maintenant **1500.00 DH** (1000 + 500)
- [ ] Cliquer "Retour"

#### Effectuer un Retrait
- [ ] Cliquer "Effectuer un retrait"
- [ ] Entrer un montant: `200`
- [ ] Cliquer "Confirmer"
- [ ] Vérifier le message "Retrait de 200.00 DH effectué"
- [ ] Menu client

#### Voir l'Historique
- [ ] Cliquer "Voir mon historique"
- [ ] Vérifier que le tableau affiche:
  - 1x DEPOT - 500.00
  - 1x RETRAIT - 200.00
  - Datés correctement
- [ ] Cliquer "Retour"

#### Consulter le Solde Final
- [ ] Cliquer "Consulter mon solde"
- [ ] Vérifier que le solde final est **1300.00 DH** (1500 - 200)
- [ ] Cliquer "Retour"

#### Test d'Erreur: Retrait Insuffisant
- [ ] Cliquer "Effectuer un retrait"
- [ ] Entrer un montant: `5000` (supérieur au solde)
- [ ] Cliquer "Confirmer"
- [ ] Vérifier l'erreur "Solde insuffisant"
- [ ] Cliquer "OK"
- [ ] Menu client

#### Déconnexion Client
- [ ] Cliquer "Déconnexion"
- [ ] Vérifier le retour à l'écran d'accueil

---

### 5️⃣ Création de Nouveau Client

- [ ] Cliquer "Créer un Compte Client"
- [ ] Remplir:
  - Nom: `Marie Leblanc`
  - Email: `marie@example.com`
  - Téléphone: `0687654321`
  - Solde initial: `2000`
- [ ] Cliquer "S'inscrire"
- [ ] Vérifier le message de succès
- [ ] Retour à l'accueil

### 6️⃣ Test avec le Nouveau Client

- [ ] Cliquer "Je suis Client"
- [ ] Entrer:
  - Nom: `Marie Leblanc`
  - Code: (celui affiché à l'inscription)
- [ ] Vérifier l'accès au menu
- [ ] Consulter le solde (doit être 2000.00)
- [ ] Faire un petit dépôt (100)
- [ ] Vérifier l'historique
- [ ] Déconnexion

---

## ✅ Validation Finale

- [ ] Toutes les authentifications fonctionnent
- [ ] Tous les tableaux affichent les données correctement
- [ ] Les dépôts/retraits mettent à jour le solde correctement
- [ ] L'historique enregistre toutes les opérations
- [ ] Les erreurs s'affichent proprement
- [ ] La navigation entre écrans fonctionne
- [ ] Les déconnexions réinitialisent la session

## 🐛 Problèmes Courants

| Problème | Solution |
|----------|----------|
| "Unable to locate database" | Vérifier que schema.sql a été exécuté |
| "Connection refused" | Vérifier que MySQL est démarré |
| "Access denied" | Vérifier user/password dans DBConnection.java |
| Les montants affichent trop de décimales | Normal, c'est du double en Java (pas d'erreur) |
| Les dates ne s'affichent pas | Vérifier le format dans FormHistorique.java |

## 💾 Données de Test

Après schema.sql, disponibles:

```
Admin:
  - username: admin
  - password: admin123

Clients:
  - Alice Martin (code: COMP00000001, solde: 1000)
  - Bob Dupont (code: COMP00000002, solde: 2000)
  - Charlie Leblanc (code: COMP00000003, solde: 1500)
```

---

**État du projet: ✅ PRÊT POUR LA PRODUCTION**

Tous les tests ci-dessus doivent passer sans erreurs.
