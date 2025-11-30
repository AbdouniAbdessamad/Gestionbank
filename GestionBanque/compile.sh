#!/bin/bash
# Script de compilation du projet GestionBanque

echo "================================"
echo "Compilation GestionBanque"
echo "================================"
echo ""

# Vérifier que le driver MySQL existe
if [ ! -f "lib/mysql-connector-java.jar" ]; then
    echo "⚠️  ATTENTION: lib/mysql-connector-java.jar n'existe pas"
    echo "   Téléchargez le driver MySQL depuis:"
    echo "   https://dev.mysql.com/downloads/connector/j/"
    echo ""
fi

# Créer le dossier de sortie
mkdir -p build

# Compiler
echo "Compilation en cours..."
javac -d build \
      -cp "lib/mysql-connector-j-8.0.33.jar:." \
      src/utils/*.java \
      src/ma/banque/model/*.java \
      src/ma/banque/service/*.java \
      src/ma/banque/ui/*.java \
      src/ma/banque/*.java

if [ $? -eq 0 ]; then
    echo ""
    echo "✓ Compilation réussie !"
    echo ""
    echo "Pour lancer l'application :"
    echo "  java -cp lib/*:build ma.banque.ui.Main"
    echo ""
    echo "Pour tester la connexion BD :"
    echo "  java -cp lib/*:build ma.banque.TestConnexion"
else
    echo ""
    echo "✗ Erreurs de compilation détectées"
    exit 1
fi
