# Proyecto de Microservicios - Sistema de Publicaciones Académicas

## 📋 Descripción General

Este proyecto implementa una arquitectura de microservicios para un sistema de publicaciones académicas utilizando Spring Boot, Spring Cloud, y otras tecnologías modernas. El sistema permite la gestión de publicaciones (libros y artículos), autores, notificaciones y catálogos de manera distribuida y escalable.

## 🏗️ Arquitectura del Sistema

El proyecto está basado en una arquitectura de microservicios que incluye los siguientes componentes:

- **Servidor Eureka**: Registro y descubrimiento de servicios
- **API Gateway**: Punto de entrada único para todas las solicitudes
- **Servicio de Autenticación**: Gestión de usuarios y seguridad con JWT
- **Microservicio de Publicaciones**: Gestión de libros y artículos
- **Microservicio de Catálogo**: Catálogo de publicaciones
- **Microservicio de Notificaciones**: Sistema de notificaciones
- **Servicio de Sincronización**: Sincronización entre servicios
- **Base de Datos CockroachDB**: Cluster de base de datos distribuida

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Cloud 2025.0.0**
- **Spring Security** (OAuth2 + JWT)
- **Spring Data JPA**
- **Netflix Eureka** (Service Discovery)
- **Spring Cloud Gateway**
- **RabbitMQ** (Mensajería asíncrona)
- **Resilience4j** (Circuit Breaker)
- **Maven** (Gestión de dependencias)

### Base de Datos
- **CockroachDB** (Base de datos distribuida)
- **PostgreSQL** (Para autenticación)

### Infraestructura
- **Docker & Docker Compose**
- **Kubernetes** (Despliegue)

## 📁 Estructura del Proyecto

```
microservicios/
├── 📁 api-gateway/                    # Gateway de la aplicación
│   ├── 📁 src/main/java/
│   ├── 📄 Dockerfile
│   └── 📄 pom.xml
├── 📁 authservice/                    # Servicio de autenticación
│   ├── 📁 src/main/java/
│   ├── 📄 seguridad.md               # Documentación de seguridad
│   ├── 📄 Dockerfile
│   └── 📄 pom.xml
├── 📁 ms-eureka-server/              # Servidor de registro Eureka
│   ├── 📁 src/main/java/
│   ├── 📄 Dockerfile
│   └── 📄 pom.xml
├── 📁 ms-catalogo/                   # Microservicio de catálogo
│   ├── 📁 src/main/java/
│   ├── 📄 Dockerfile
│   └── 📄 pom.xml
├── 📁 ms-publicaciones/              # Microservicio de publicaciones
│   ├── 📁 src/main/java/
│   │   └── 📁 publicaciones/
│   │       ├── 📁 Controlador/       # Controllers REST
│   │       ├── 📁 Services/          # Servicios de negocio
│   │       ├── 📁 listener/          # Event listeners
│   │       └── MsPublicacionesApplication.java
│   ├── 📄 Dockerfile
│   └── 📄 pom.xml
├── 📁 ms-notificaciones/             # Microservicio de notificaciones
│   ├── 📁 src/main/java/
│   │   └── 📁 ec/edu/espe/ms_notificaciones/
│   │       ├── 📁 controllers/       # Controllers REST
│   │       ├── 📁 services/          # Servicios de negocio
│   │       ├── 📁 listener/          # Event listeners
│   │       └── MsNotificacionesApplication.java
│   ├── 📄 docker-compose.yml
│   ├── 📄 Dockerfile
│   └── 📄 pom.xml
├── 📁 sincronizacion/                # Servicio de sincronización
│   ├── 📁 src/main/java/
│   │   └── 📁 ec/edu/espe/sincronizacion/
│   │       ├── 📁 services/          # Servicios de sincronización
│   │       ├── 📁 listener/          # Event listeners
│   │       └── SincronizacionApplication.java
│   ├── 📄 Dockerfile
│   └── 📄 pom.xml
├── 📁 kubernetes/                    # Configuraciones de Kubernetes
│   └── 📁 taller-kubernetes-main/
│       ├── 📁 app-publicaciones/     # Despliegue de publicaciones
│       ├── 📁 app-tareas/           # Despliegue de tareas
│       └── 📁 primereact/           # Frontend React
├── 📄 docker-compose.yml            # Orquestación de contenedores
├── 📄 pom.xml                       # POM principal (multi-módulo)
├── 📄 notas.txt                     # Notas de desarrollo
└── 📄 test.py                       # Scripts de prueba
```

## 🔧 Microservicios Detallados

### 1. 🌐 MS-Eureka-Server (Puerto 8761)
**Propósito**: Servidor de registro y descubrimiento de servicios
- **Tecnologías**: Spring Cloud Netflix Eureka Server
- **Funcionalidad**: Permite que los microservicios se registren y se descubran entre sí
- **Ubicación**: `ms-eureka-server/`

### 2. 🚪 API Gateway (Puerto 8080)
**Propósito**: Punto de entrada único para todas las solicitudes
- **Tecnologías**: Spring Cloud Gateway, Resilience4j
- **Funcionalidades**:
  - Enrutamiento de solicitudes
  - Balanceador de carga
  - Circuit Breaker
  - Filtros de seguridad
- **Ubicación**: `api-gateway/`

### 3. 🔐 AuthService (Puerto 8081)
**Propósito**: Servicio de autenticación y autorización
- **Tecnologías**: Spring Security, JWT, PostgreSQL
- **Funcionalidades**:
  - Autenticación OAuth2 con JWT
  - Gestión de usuarios y roles
  - Validación de tokens
  - Refresh tokens
- **Base de Datos**: PostgreSQL (`authservice_db`)
- **Endpoints principales**:
  - `POST /auth/login` - Iniciar sesión
  - `POST /auth/refreshToken` - Renovar token
  - `POST /auth/validateToken` - Validar token
  - `POST /auth/logout` - Cerrar sesión
- **Ubicación**: `authservice/`

### 4. 📚 MS-Publicaciones
**Propósito**: Gestión de publicaciones académicas (libros y artículos)
- **Tecnologías**: Spring Boot, JPA, RabbitMQ, CockroachDB
- **Funcionalidades**:
  - CRUD de libros y artículos
  - Gestión de autores
  - Publicación de eventos
  - Sincronización con catálogo
- **Componentes principales**:
  - `LibroController`, `ArticuloController`, `AutorController`
  - `LibroService`, `ArticuloService`, `AutorService`
  - `CatalogoProducer`, `NotificacionProducer`, `RelojProducer`
  - `RelojListener`
- **Ubicación**: `ms-publicaciones/`

### 5. 📖 MS-Catálogo
**Propósito**: Catálogo centralizado de publicaciones
- **Tecnologías**: Spring Boot, RabbitMQ, Resilience4j
- **Funcionalidades**:
  - Catálogo de todas las publicaciones
  - Búsqueda y filtrado
  - Sincronización con publicaciones
- **Ubicación**: `ms-catalogo/`

### 6. 🔔 MS-Notificaciones
**Propósito**: Sistema de notificaciones del sistema
- **Tecnologías**: Spring Boot, RabbitMQ
- **Funcionalidades**:
  - Envío de notificaciones
  - Gestión de eventos
  - Procesamiento asíncrono
- **Componentes principales**:
  - `NotificacionController`
  - `NotificacionService`, `RelojProducer`
  - `NotificacionListener`, `RelojListener`
- **Ubicación**: `ms-notificaciones/`

### 7. 🔄 Sincronización
**Propósito**: Servicio de sincronización entre microservicios
- **Tecnologías**: Spring Boot, RabbitMQ
- **Funcionalidades**:
  - Sincronización de datos entre servicios
  - Gestión de eventos de reloj
  - Coordinación de procesos distribuidos
- **Componentes principales**:
  - `SincronizacionService`
  - `RelojListener`
- **Ubicación**: `sincronizacion/`

## 🗄️ Base de Datos

### CockroachDB Cluster
El proyecto utiliza un cluster de CockroachDB con 3 nodos para garantizar alta disponibilidad y distribución de datos:

- **Nodo 1**: Puerto 26257 (Admin UI: 8080)
- **Nodo 2**: Puerto 26258 (Admin UI: 8081)
- **Nodo 3**: Puerto 26259 (Admin UI: 8082)

**Configuración en docker-compose.yml**:
- Cluster de 3 nodos CockroachDB
- Inicialización automática del cluster
- Volúmenes persistentes para cada nodo
- Red interna para comunicación entre nodos

## 🐳 Docker y Containerización

### Docker Compose Principal
El archivo `docker-compose.yml` en la raíz configura:
- Cluster CockroachDB (3 nodos)
- Inicialización automática del cluster
- Redes y volúmenes necesarios

### Dockerfiles Individuales
Cada microservicio incluye su propio `Dockerfile` para:
- Construcción de imágenes optimizadas
- Configuración de Java 17
- Exposición de puertos específicos

## ☸️ Kubernetes

La carpeta `kubernetes/` contiene configuraciones para despliegue en Kubernetes:
- **app-publicaciones/**: Despliegue del microservicio de publicaciones
- **app-tareas/**: Despliegue de tareas relacionadas
- **primereact/**: Frontend en React

## 🚀 Instalación y Ejecución

### Prerrequisitos
- Java 17+
- Maven 3.6+
- Docker y Docker Compose
- Git

### Pasos de Instalación

1. **Clonar el repositorio**:
```bash
git clone <repository-url>
cd microservicios
```

2. **Compilar todos los microservicios**:
```bash
mvn clean install
```

3. **Levantar la base de datos CockroachDB**:
```bash
docker-compose up -d
```

4. **Ejecutar los microservicios en orden**:

```bash
# 1. Eureka Server
cd ms-eureka-server
mvn spring-boot:run

# 2. Auth Service
cd ../authservice
mvn spring-boot:run

# 3. API Gateway
cd ../api-gateway
mvn spring-boot:run

# 4. Microservicios de negocio
cd ../ms-publicaciones
mvn spring-boot:run

cd ../ms-catalogo
mvn spring-boot:run

cd ../ms-notificaciones
mvn spring-boot:run

cd ../sincronizacion
mvn spring-boot:run
```

### Ejecución con Docker
```bash
# Construir todas las imágenes
docker-compose build

# Levantar todos los servicios
docker-compose up -d
```

## 🔗 Endpoints y Puertos

| Servicio | Puerto | Descripción |
|----------|---------|-------------|
| Eureka Server | 8761 | http://localhost:8761 |
| API Gateway | 8080 | http://localhost:8080 |
| Auth Service | 8081 | http://localhost:8081 |
| MS-Publicaciones | 8082 | http://localhost:8082 |
| MS-Catálogo | 8083 | http://localhost:8083 |
| MS-Notificaciones | 8084 | http://localhost:8084 |
| Sincronización | 8085 | http://localhost:8085 |
| CockroachDB Node 1 | 26257 | Admin UI: http://localhost:8080 |
| CockroachDB Node 2 | 26258 | Admin UI: http://localhost:8081 |
| CockroachDB Node 3 | 26259 | Admin UI: http://localhost:8082 |

## 🔑 Autenticación

### Configuración de Roles
El sistema maneja tres tipos de roles:
- **PARTICIPANTE**: Usuario básico
- **MODERADOR**: Usuario con permisos de moderación
- **ADMINISTRADOR**: Usuario con todos los permisos

### Ejemplos de uso de la API de Autenticación

**Login**:
```bash
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password"
}
```

**Validar Token**:
```bash
POST /auth/validateToken
Content-Type: application/json

{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Refresh Token**:
```bash
POST /auth/refreshToken
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

## 📝 Mensajería y Eventos

El sistema utiliza RabbitMQ para la comunicación asíncrona entre microservicios:

- **Eventos de Publicaciones**: Notificación cuando se crean/actualizan publicaciones
- **Eventos de Catálogo**: Sincronización del catálogo
- **Eventos de Notificaciones**: Envío de notificaciones a usuarios
- **Eventos de Reloj**: Sincronización temporal entre servicios

## 🔍 Monitoreo y Observabilidad

### Spring Boot Actuator
Todos los microservicios incluyen Spring Boot Actuator para monitoreo:
- Health checks: `/actuator/health`
- Métricas: `/actuator/metrics`
- Info: `/actuator/info`

### Circuit Breaker
Implementado con Resilience4j para:
- Prevenir cascadas de fallos
- Timeout de solicitudes
- Reintentos automáticos

## 🧪 Testing

El proyecto incluye:
- Tests unitarios para cada microservicio
- Tests de integración
- Scripts de prueba (`test.py`)

## 📄 Documentación Adicional

- **seguridad.md**: Documentación detallada del servicio de autenticación
- **notas.txt**: Notas de desarrollo y comandos útiles

## 🤝 Contribución

Para contribuir al proyecto:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📞 Contacto

- **Institución**: Escuela Politécnica del Ejército (ESPE)
- **Asignatura**: Aplicaciones Distribuidas
- **Período**: Mayo - Septiembre 2025

## 📋 Notas de Desarrollo

### Comandos Útiles CockroachDB
```bash
# Conectar a CockroachDB
docker exec -it crdb-node1 ./cockroach sql --insecure

# Configurar roles de usuario
ALTER TABLE users ADD CONSTRAINT users_role_check CHECK (role IN ('PARTICIPANTE', 'MODERADOR', 'ADMINISTRADOR'));
UPDATE users SET role = 'ADMINISTRADOR' WHERE email = 'admin@espe.edu.ec';
```

### Estado del Proyecto
- ✅ Arquitectura de microservicios implementada
- ✅ Autenticación JWT funcional
- ✅ Base de datos distribuida CockroachDB
- ✅ Comunicación asíncrona con RabbitMQ
- ✅ Service Discovery con Eureka
- ✅ API Gateway implementado
- ✅ Containerización con Docker
- ✅ Configuración de Kubernetes