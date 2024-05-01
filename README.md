# Spring Boot Multi-Database with Liquibase

## Overview
This Spring Boot application demonstrates the configuration of multiple databases with Liquibase integration. It is designed to mimic a real-world scenario where credit card data is distributed across different databases. This ensures that if one database is compromised, the other segments remain secure.

## Features
- **Multi-Database Configuration**: Each domain (Card, CardHolder, and PAN) uses its own database to enhance security and isolation.
- **Liquibase Integration**: Database migration management for each database to ensure schema consistency across environments. Each database has its changelog files organized in a directory named after the database.
- **Dedicated Liquibase User**: Each database utilizes a dedicated user for executing Liquibase to promote security and proper access control.
- **Data Encryption at Rest**: Ensures that sensitive data is encrypted when stored, further securing data from unauthorized access.
- **Domain-Driven Design**: Clear separation of domains to encapsulate the logic of each banking segment.

## Technology Stack
- Spring Boot
- Java
- MySQL
- Liquibase

## Project Structure
- `src/main/java` - Contains the source files for the application.
- `src/main/resources/db` - Contains Liquibase changelogs organized by database name (cardholder, creditcard, pan).
- `scripts/` - Includes SQL setup scripts for initial database setup.
- `src/test/java` - Contains simple tests to ensure writes/reads work against multiple databases.


## Setup and Installation
1. Ensure you have Java and Maven installed.
2. Set up your MySQL database using the scripts provided in `scripts/mysql-setup.sql`.
3. Configure your database connections in `src/main/resources/application.yaml`.