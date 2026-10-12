# solicitudes-ms — Microservicio de Solicitudes de Retiro (RutaLimpia)

Microservicio de solicitudes de retiro del caso **RutaLimpia**, un servicio de recolección de reciclaje a domicilio.

Un **vecino** crea una solicitud de retiro indicando su dirección, el tipo de residuo y el horario preferido. Al registrarse, el sistema genera automáticamente un **folio de recepción** (formato `RL-XXXXXXXX`) y la solicitud queda en estado `PENDIENTE` hasta que otro proceso (fuera de este microservicio) le asigne un camión.

El campo `vecinoId` hace referencia al id de un usuario registrado en el **microservicio de Autenticación y Usuarios**; este microservicio no almacena datos del vecino, solo su id.

---

## Tecnologías

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- PostgreSQL
- Maven

---

## Estructura del proyecto

```
com.duoc.solicitudes_ms
├── controller   → Controladores REST (endpoints)
├── service      → Lógica de negocio
├── repository   → Repositorios JPA (acceso a datos)
└── model        → Entidades JPA
```

---

## Requisitos

- Java 21
- Maven
- PostgreSQL

---

## Base de datos

Crear la base de datos manualmente:

```sql
CREATE DATABASE solicitudes_db;
```

Las tablas se crean automáticamente al arrancar la aplicación gracias a la configuración `spring.jpa.hibernate.ddl-auto=update`.

---

## Configuración

Editar el archivo `src/main/resources/application.properties` y colocar el usuario y la contraseña de PostgreSQL:

```properties
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

---

## Cómo clonar y ejecutar

```bash
git clone <URL-DEL-REPOSITORIO>
cd solicitudes-ms/solicitudes-ms
```

Compilar y empaquetar:

```bash
mvn clean
mvn install
mvn package
```

Ejecutar:

```bash
mvn spring-boot:run
```

O bien:

```bash
java -jar target/solicitudes-ms-0.0.1-SNAPSHOT.jar
```

El servicio queda disponible en `http://localhost:8081`.

---

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/solicitudes` | Crea una nueva solicitud de retiro. Devuelve la solicitud con el folio generado. |
| GET | `/solicitudes` | Lista todas las solicitudes. |
| GET | `/solicitudes/{id}` | Obtiene una solicitud por su id. |
| PUT | `/solicitudes/{id}` | Actualiza una solicitud existente. |
| DELETE | `/solicitudes/{id}` | Elimina una solicitud por su id. |
| GET | `/solicitudes/estado/{estado}` | Lista solicitudes filtradas por estado. |
| GET | `/solicitudes/vecino/{vecinoId}` | Lista solicitudes de un vecino específico. |
| GET | `/solicitudes/folio/{folio}` | Busca una solicitud por su folio de recepción. |

---

## Ejemplo de uso

**Petición** — `POST /solicitudes`

```json
{
  "direccion": "Av. Principal 123",
  "tipoResiduo": "PLASTICO",
  "horarioPreferido": "09:00-12:00",
  "vecinoId": 1
}
```

**Respuesta** — `201 Created`

```json
{
  "id": 1,
  "folio": "RL-3F9A1C7B",
  "direccion": "Av. Principal 123",
  "tipoResiduo": "PLASTICO",
  "horarioPreferido": "09:00-12:00",
  "estado": "PENDIENTE",
  "fechaCreacion": "2026-10-12",
  "vecinoId": 1
}
```

---

## Códigos de respuesta

| Código | Significado |
|--------|-------------|
| 200 OK | La solicitud se procesó correctamente. |
| 201 Created | La solicitud de retiro se creó exitosamente. |
| 204 No Content | La solicitud se eliminó correctamente. |
| 400 Bad Request | Faltan campos obligatorios (dirección, tipoResiduo o vecinoId). |
| 404 Not Found | No se encontró la solicitud con el id o folio indicado. |
