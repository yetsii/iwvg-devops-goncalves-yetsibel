# IWVG DevOps - Yetsibel Goncalves

[![CI](https://github.com/yetsii/iwvg-devops-goncalves-yetsibel/actions/workflows/continuous-integration.yml/badge.svg)](https://github.com/yetsii/iwvg-devops-goncalves-yetsibel/actions/workflows/continuous-integration.yml)
[![SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=yetsii_iwvg-devops-goncalves-yetsibel&metric=alert_status)](https://sonarcloud.io/project/overview?id=yetsii_iwvg-devops-goncalves-yetsibel)
[![Render](https://img.shields.io/badge/Render-Deploy-46E3B7?logo=render&logoColor=white)](https://iwvg-devops-goncalves-yetsibel.onrender.com)
[![AWS](https://img.shields.io/badge/AWS-Lightsail-FF9900?logo=amazonaws&logoColor=white)](http://51.95.169.47:10000/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE.md)
[![Java 21](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot 3.5.5](https://img.shields.io/badge/Spring_Boot-3.5.5-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)

Proyecto desarrollado en la asignatura de Ingeniería Web: Visión General (IWVG) de la UPM, centrado en la construcción de una API REST con Spring Boot, integración continua y despliegues automatizados en entornos cloud.

## Descripción

Este repositorio contiene una aplicación Spring Boot con una API REST completa para gestionar usuarios, desplegarla con contenedores y automatizar su validación y entrega mediante GitHub Actions.

### Implementaciones realizadas

- API REST con endpoints para consultar, desactivar y eliminar usuarios
- Persistencia con JPA y PostgreSQL
- Seguridad REST con Spring Security en modo stateless
- Profiles de configuración para `dev`, `pre` y `prod`
- Documentación automática con Springdoc OpenAPI / Swagger UI
- Endpoints de monitorización con Spring Boot Actuator
- Generación de badge SVG dinámico desde la aplicación
- Contenedores Docker y Docker Compose para ejecutar la app y la base de datos
- Integración continua con Maven, CodeQL, SonarCloud y notificaciones en Slack
- Despliegue continuo en Render y AWS Lightsail
- Publicación de imágenes en GitHub Container Registry (GHCR)

## Tecnologías

- Java 21
- Maven
- Spring Boot 3.5.5
- Spring Web
- Spring Data JPA
- PostgreSQL
- Spring Security
- Spring Boot Actuator
- Springdoc OpenAPI
- Docker / Docker Compose
- GitHub Actions
- SonarCloud
- Render
- AWS Lightsail
- Slack notifications

## Requisitos previos

- Java 21
- Maven 3.9+
- Git
- Docker y Docker Compose (opcional, para ejecución local con contenedores)
- PostgreSQL (si se ejecuta sin Docker)

## Instalación

```bash
git clone https://github.com/yetsii/iwvg-devops-goncalves-yetsibel.git
cd iwvg-devops-goncalves-yetsibel
mvn clean install
```

## Ejecución local

### Con Maven

```bash
mvn spring-boot:run
```

La aplicación queda disponible en:

- http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- Health: http://localhost:8080/actuator/health
- Info: http://localhost:8080/actuator/info

### Con Docker Compose

```bash
docker compose up --build -d
```

También puede compilarse y ejecutarse manualmente:

```bash
docker build -t iwvg-devops-goncalves-yetsibel:latest .
docker run -d --name iwvg-devops -p 8080:8080 iwvg-devops-goncalves-yetsibel:latest
```

## Endpoints principales

- `/` → información básica de la aplicación
- `/version-badge` → badge SVG dinámico generado por la app
- `/user/{id}` → consulta un usuario por su id
- `/user/{id}` (DELETE) → elimina un usuario
- `/user/{id}/active?active=true|false` → activa o desactiva un usuario
- `/actuator/health` → estado del servicio
- `/actuator/info` → información del artefacto y build
- `/swagger-ui.html` → documentación OpenAPI/Swagger
- `/v3/api-docs` → JSON de la especificación OpenAPI

## Perfiles y entorno

El proyecto incluye perfiles para adaptar la aplicación a cada entorno:

- `dev`: configuración local de desarrollo
- `pre`: configuración orientada a Render
- `prod`: configuración orientada a AWS Lightsail

La configuración principal se encuentra en `src/main/resources/application.yml`, y los perfiles específicos en:

- `src/main/resources/application-dev.yml`
- `src/main/resources/application-pre.yml`
- `src/main/resources/application-prod.yml`

## CI/CD y despliegues

### Integración continua

El workflow `.github/workflows/continuous-integration.yml` ejecuta:

- compilación con Maven
- tests unitarios, de integración y funcionales
- análisis de seguridad con CodeQL
- análisis estático con SonarCloud
- notificaciones en Slack en caso de éxito o fallo

### Despliegue en Render

El workflow `.github/workflows/cd-staging.yml` se dispara en la rama `staging` y:

- compila la imagen Docker
- publica la imagen en GitHub Container Registry
- dispara el deploy del servicio en Render mediante un deploy hook

### Despliegue en AWS Lightsail

El workflow `.github/workflows/cd-main.yml` se dispara en `master` y:

- genera la imagen Docker del proyecto
- la publica en GHCR
- conecta por SSH con AWS Lightsail
- ejecuta el contenedor con la configuración de producción y comprobación de salud

### Integración con Slack

La pipeline de CI envía mensajes a Slack con el estado de la ejecución:

- notificación de éxito
- notificación de fallo

Esto permite supervisar rápidamente la calidad y disponibilidad de la integración.

## Estructura del proyecto

```text
.
├── .github/
│   └── workflows/
│       ├── cd-main.yml
│       ├── cd-postgres-main.yml
│       ├── cd-staging.yml
│       └── continuous-integration.yml
├── docs/
│   └── docs.txt
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── es/
│   │   │       └── upm/
│   │   │           └── miw/
│   │   │               └── devops/
│   │   │                   ├── code/
│   │   │                   ├── controller/
│   │   │                   ├── dto/
│   │   │                   ├── model/
│   │   │                   ├── repository/
│   │   │                   ├── rest/
│   │   │                   │   └── exceptionshandler/
│   │   │                   ├── service/
│   │   │                   ├── Application.java
│   │   │                   └── SecurityConfiguration.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-pre.yml
│   │       ├── application-prod.yml
│   │       └── data.sql
│   └── test/
│       ├── java/
│       │   └── es/
│       │       └── upm/
│       │           └── miw/
│       │               └── devops/
│       │                   ├── code/
│       │                   ├── controller/
│       │                   ├── model/
│       │                   └── service/
│       └── resources/
│           ├── application-test.yml
│           └── logback-test.xml
├── .gitignore
├── Dockerfile
├── LICENSE.md
├── README.md
├── cp.txt
├── docker-compose.yml
├── docker-compose-db.yml
├── pom.xml
├── target/
└── .idea/
```

## Licencia

Este proyecto se distribuye bajo la licencia MIT. Consulta el archivo [LICENSE.md](LICENSE.md) para más información.

## Autor

Yetsibel Goncalves
