# NSIAGO'ASSUR - Backend Documentation

## Description
NSIAGO'ASSUR est une application de gestion d'assurance automobile permettant aux clients de simuler et souscrire à différents produits d'assurance.

## Technologies Utilisées
- Java 17
- Spring Boot 3.2.1
- PostgreSQL
- Spring Data JPA
- Spring Web
- Swagger/OpenAPI pour la documentation API
- Lombok
- iText pour la génération de PDF

## Structure de la Base de Données

### Tables Principales

#### 1. vehicle_categories
```sql
CREATE TABLE vehicle_categories (
    code VARCHAR(255) PRIMARY KEY,
    libelle VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);
```

#### 2. fiscal_powers
```sql
CREATE TABLE fiscal_powers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    min_power INTEGER NOT NULL,
    max_power INTEGER NOT NULL,
    premium DECIMAL NOT NULL
);
```

#### 3. guarantees
```sql
CREATE TABLE guarantees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    rate DECIMAL,
    minimum_premium DECIMAL,
    max_vehicle_age INTEGER,
    is_based_on_new_value BOOLEAN NOT NULL,
    is_based_on_venal_value BOOLEAN NOT NULL,
    covered_value_percentage DECIMAL
);
```

#### 4. insurance_products
```sql
CREATE TABLE insurance_products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);
```

#### 5. simulations
```sql
CREATE TABLE simulations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    quote_reference VARCHAR(255) NOT NULL UNIQUE,
    product_id BIGINT NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price DECIMAL NOT NULL,
    vehicle_age INTEGER NOT NULL,
    vehicle_value DECIMAL NOT NULL,
    category_code VARCHAR(255) NOT NULL,
    fiscal_power INTEGER NOT NULL
);
```

#### 6. subscriptions
```sql
CREATE TABLE subscriptions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    simulation_id BIGINT NOT NULL,
    registration_number VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    number_of_seats INTEGER NOT NULL,
    number_of_doors INTEGER NOT NULL,
    first_registration_date DATE NOT NULL,
    subscriber_first_name VARCHAR(255) NOT NULL,
    subscriber_last_name VARCHAR(255) NOT NULL,
    subscriber_address VARCHAR(255) NOT NULL,
    subscriber_phone VARCHAR(255) NOT NULL,
    subscriber_id_number VARCHAR(255) NOT NULL,
    subscriber_city VARCHAR(255) NOT NULL,
    subscription_date DATE NOT NULL,
    status VARCHAR(255) NOT NULL,
    attestation_number VARCHAR(255)
);
```

## Architecture du Backend

### Structure des Packages
```
com.nsiago.assurance/
├── controller/         # Contrôleurs REST
├── domain/            # Entités JPA
├── dto/               # Objets de transfert de données
├── repository/        # Repositories JPA
└── service/           # Logique métier
```

## API Endpoints

### Simulations

#### Créer une simulation
```http
POST /api/v1/simulations
Content-Type: application/json

{
    "productName": "string",
    "vehicleCategoryCode": "string",
    "vehicleAge": integer,
    "fiscalPower": integer,
    "vehicleValue": number
}
```

#### Récupérer une simulation
```http
GET /api/v1/simulations/{quoteReference}
```

### Souscriptions

#### Créer une souscription
```http
POST /api/v1/subscriptions
Content-Type: application/json

{
    "quoteReference": "string",
    "registrationNumber": "string",
    "color": "string",
    "numberOfSeats": integer,
    "numberOfDoors": integer,
    "firstRegistrationDate": "date",
    "subscriberFirstName": "string",
    "subscriberLastName": "string",
    "subscriberAddress": "string",
    "subscriberPhone": "string",
    "subscriberIdNumber": "string",
    "subscriberCity": "string"
}
```

#### Récupérer une souscription
```http
GET /api/v1/subscriptions/{id}
```

#### Récupérer le statut d'une souscription
```http
GET /api/v1/subscriptions/status/{id}
```

#### Générer l'attestation d'assurance
```http
GET /api/v1/subscriptions/{id}/attestation
```

## Configuration

### Application Properties
```yaml
server:
  port: 8080
  servlet:
    context-path: /api/v1

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/assurance_auto
    username: postgres
    password: admin
    driver-class-name: org.postgresql.Driver
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
    show-sql: true

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
```

## Fonctionnalités Principales

### 1. Calcul des Primes
- Basé sur la puissance fiscale
- Ajout des garanties selon le produit
- Prise en compte de l'âge du véhicule
- Application des minimums de prime

### 2. Génération d'Attestation
- Format PDF
- Inclusion des informations du véhicule
- Informations du souscripteur
- Détails de la couverture

## Installation et Démarrage

1. Cloner le repository
```bash
git clone [url-du-repo]
```

2. Configurer la base de données PostgreSQL
```bash
createdb assurance_auto
```

3. Configurer les propriétés de l'application dans `application.yml`

4. Lancer l'application
```bash
./mvnw spring-boot:run
```

5. Accéder à la documentation Swagger
```
http://localhost:8080/api/v1/swagger-ui.html
```

## Données de Test

### Catégories de Véhicules
- 201: Promenade et Affaire
- 202: Véhicules Motorisés à 2 ou 3 roues
- 203: Transport public de voyage
- 204: Véhicule de transport avec taximètres

### Produits d'Assurance
- Papillon: RC, DOMMAGE, VOL
- Douby: RC, DOMMAGE, TIERCE COLLISION
- Douyou: RC, DOMMAGE, COLLISION, INCENDIE
- Toutourisquou: Tous risques

## Sécurité
- Validation des données entrantes
- Gestion des erreurs
- Logs des opérations

## Support
Pour toute question ou assistance, contactez l'équipe de développement. 