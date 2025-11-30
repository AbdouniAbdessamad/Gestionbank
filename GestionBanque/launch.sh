#!/bin/bash
cd "$(dirname "$0")"
java -cp lib/mysql-connector-j-8.0.33.jar:build ma.banque.ui.Main &
