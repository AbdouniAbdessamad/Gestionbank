#!/bin/bash
# Quick Start Commands for GestionBanque

echo "╔════════════════════════════════════════════════════════════════╗"
echo "║          GestionBanque - Quick Start Commands                  ║"
echo "╚════════════════════════════════════════════════════════════════╝"
echo ""

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}1️⃣  SETUP DATABASE${NC}"
echo "   mysql -u root -p < schema.sql"
echo ""

echo -e "${BLUE}2️⃣  COMPILE PROJECT${NC}"
echo "   bash compile.sh"
echo ""

echo -e "${BLUE}3️⃣  TEST CONNECTION${NC}"
echo "   java -cp \"lib/mysql-connector-j-8.0.33.jar:build\" ma.banque.TestConnexion"
echo ""

echo -e "${BLUE}4️⃣  RUN APPLICATION${NC}"
echo "   java -cp \"lib/mysql-connector-j-8.0.33.jar:build\" ma.banque.ui.Main"
echo ""

echo -e "${BLUE}OR Use One-Liner:${NC}"
echo "   bash compile.sh && java -cp \"lib/mysql-connector-j-8.0.33.jar:build\" ma.banque.ui.Main"
echo ""

echo "╔════════════════════════════════════════════════════════════════╗"
echo "║              LOGIN CREDENTIALS (Test Data)                     ║"
echo "╠════════════════════════════════════════════════════════════════╣"
echo "║                                                                ║"
echo "║  👨‍💼 ADMIN                                                      ║"
echo "║     Username: admin                                           ║"
echo "║     Password: admin123                                        ║"
echo "║                                                                ║"
echo "║  👤 CLIENT (Examples)                                          ║"
echo "║     Name: Alice Martin                                        ║"
echo "║     Code: COMP00000001                                        ║"
echo "║     Balance: 1000 DH                                          ║"
echo "║                                                                ║"
echo "║     Name: Bob Dupont                                          ║"
echo "║     Code: COMP00000002                                        ║"
echo "║     Balance: 2000 DH                                          ║"
echo "║                                                                ║"
echo "║     Name: Charlie Leblanc                                     ║"
echo "║     Code: COMP00000003                                        ║"
echo "║     Balance: 1500 DH                                          ║"
echo "║                                                                ║"
echo "╚════════════════════════════════════════════════════════════════╝"
echo ""

echo -e "${GREEN}✅ Project Status: READY FOR PRODUCTION${NC}"
echo ""
echo "Files Generated:"
echo "  - TEST.md          → Comprehensive test checklist"
echo "  - CORRECTIONS.md   → Details of corrections made"
echo "  - SUMMARY.md       → Executive summary"
echo "  - INSTALLATION.md  → Setup instructions"
echo ""
