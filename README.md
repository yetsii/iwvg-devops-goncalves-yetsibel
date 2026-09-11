# IWVG DevOps - Yetsibel Goncalves

[![CI](https://github.com/yetsii/iwvg-devops-goncalves-yetsibel/actions/workflows/continuous-integration.yml/badge.svg)](https://github.com/yetsii/iwvg-devops-goncalves-yetsibel/actions/workflows/continuous-integration.yml)
[![SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=yetsii_iwvg-devops-goncalves-yetsibel&metric=alert_status)](https://sonarcloud.io/project/overview?id=yetsii_iwvg-devops-goncalves-yetsibel)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE.md)
[![Java 21](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot 3.5.5](https://img.shields.io/badge/Spring_Boot-3.5.5-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)

Proyecto desarrollado en la asignatura de Ingeniería Web: Visión General (IWVG) de la UPM, enfocado en prácticas de DevOps, integración continua y despliegue con tecnologías modernas.

## Descripción

Este repositorio contiene una aplicación Spring Boot que expone servicios REST básicos y una API documentada con OpenAPI/Swagger. La aplicación incluye:

- Endpoint principal para información del proyecto
- Endpoint para generación de badge dinámico
- Endpoints de Actuator para health e info
- Seguridad básica con Spring Security
- Configuración para perfiles `dev`, `prod` y pruebas
- Integración con GitHub Actions, SonarCloud y Docker

## Tecnologías

- Java 21
- Maven
- Spring Boot 3.5.5
- Spring Web
- Spring Security
- Actuator
- Springdoc OpenAPI
- Docker / Docker Compose
- GitHub Actions
- SonarCloud

## Requisitos previos

- Java 21
- Maven 3.9+
- Git
- Docker (opcional, para ejecución en contenedor)

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

La aplicación estará disponible en:

- http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

### Con Docker

```bash
docker compose up --build -d
```

También puedes construir la imagen manualmente:

```bash
docker build -t iwvg-devops-goncalves-yetsibel:latest .
docker run -d --name iwvg-devops -p 8080:8080 iwvg-devops-goncalves-yetsibel:latest
```

## Endpoints principales

- `/` → Información básica de la aplicación
- `/version-badge` → Badge SVG generado dinámicamente
- `/actuator/health` → Estado del servicio
- `/actuator/info` → Información del artefacto y build
- `/swagger-ui.html` → Documentación OpenAPI

## CI/CD

El proyecto incluye una pipeline de integración continua en GitHub Actions que ejecuta:

- compilación
- tests
- análisis de seguridad con CodeQL
- análisis con SonarCloud
- notificaciones por Slack en caso de fallo

Archivo de workflow:
- `.github/workflows/continuous-integration.yml`

## Estructura del proyecto

```text
.
├── src
│   ├── main
│   │   ├── java
│   │   └── resources
│   └── test
├── .github
│   └── workflows
├── docs
├── Dockerfile
├── docker-compose.yml
├── docker-compose-db.yml
├── LICENSE.md
├── pom.xml
├── README.md
└── ...
```

## Licencia

Este proyecto se distribuye bajo la licencia MIT. Consulta el archivo [LICENSE.md](LICENSE.md) para más información.

## Autor

Yetsibel Goncalves
