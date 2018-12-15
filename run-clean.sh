#!/usr/bin/env bash
rm ~/loan-app-db.mv.db
mvn clean package
java -jar target/loan-0.0.1-SNAPSHOT.jar