#!/bin/bash
# Script de lancement de l'application GestionBanque

CLASSPATH="lib/mysql-connector-j-8.0.33.jar:build"

echo "================================"
echo "GestionBanque - Application"
echo "================================"
echo ""

# Vérifier que les classes compilées existent
if [ ! -d "build" ]; then
    echo "✗ Le dossier 'build' n'existe pas"
    echo "  Exécutez d'abord: bash compile.sh"
    exit 1
fi

# Tester la connexion BD
echo "Test de la connexion à la base de données..."
java -cp "$CLASSPATH" ma.banque.TestConnexion

if [ $? -ne 0 ]; then
    echo ""
    echo "⚠️  Attention: La connexion à la BD a échoué"
    echo "   Vérifiez :"
    echo "   1. Que MySQL est démarré"
    echo "   2. Que la base 'GestionBanque' existe"
    echo "   3. Les paramètres dans src/utils/DBConnection.java"
    echo ""
    read -p "Continuer quand même ? (y/n) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
fi

echo ""
echo "Lancement de l'application..."
java -cp "$CLASSPATH" ma.banque.ui.Main
