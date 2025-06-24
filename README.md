# 📚 LiterAlura

Proyecto Java con Spring Boot que permite consultar libros desde la API de **Gutendex**, almacenar su información en una base de datos PostgreSQL y realizar operaciones de consulta enriquecidas desde consola. Este proyecto fue desarrollado como parte del desafío final del curso **"Java: trabajando con lambdas, streams y Spring Framework"**.

---

## 🚀 Tecnologías utilizadas

- **Java 21**
- **Spring Boot 3.4.6**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Lombok (opcional)**
- **Gutendex API**: https://gutendex.com/books/

---

## 🧱 Arquitectura del Proyecto

El proyecto sigue una arquitectura basada en capas:

```
src/
└── main/
    ├── java/com/alura/literalura/
    │   ├── client/              → Comunicación con APIs externas (Gutendex)
    │   ├── controller/          → Controlador REST (no principal en este proyecto CLI)
    │   ├── dto/                 → Clases DTO para mapear respuestas externas
    │   ├── model/               → Clases @Entity para persistencia
    │   ├── repository/          → Interfaces JpaRepository
    │   ├── service/             → Lógica de negocio
    │   └── MainApplication.java → Aplicación con menú interactivo por consola
    └── resources/
        └── application.properties → Configuración de base de datos y JPA
```

---

## 🌐 API Externa

Se utiliza la **Gutendex API**, una API REST gratuita de dominio público que permite buscar libros por título.

- URL base: `https://gutendex.com/books/`
- Consulta utilizada: `/?search={titulo}`

---

## 📦 Base de datos

- **Motor:** PostgreSQL
- **Puerto:** 5432
- **Nombre de BD:** `literalura`
- **Credenciales por defecto:**  
  - Usuario: `postgres`  
  - Contraseña: `1234`

- Las entidades `Book` y `Author` se guardan en las tablas respectivas.  
- Relaciones:
  - `Book` tiene una relación `@ManyToOne` con `Author`.

---

## ✅ Funcionalidades implementadas

### 🔍 Consulta y persistencia

- Buscar libro por título → Guarda título, autor, idioma, descargas.
- Almacena solo el primer autor y primer idioma recibido.
- Evita duplicados en base de datos.
- Persiste datos en PostgreSQL.

### 📋 Listados

- Todos los libros registrados.
- Todos los autores registrados.
- Filtrar libros por idioma (ej: EN, ES, PT).
- Listar autores vivos en un año ingresado.
- Contar autores vivos en ese año.
- Contar libros por idioma utilizando derived queries.

### 📊 Derived Queries (Spring Data JPA)

- `findByTitleAndAuthorName(String title, String authorName)`
- `findByName(String name)`
- `countByLanguageIgnoreCase(String language)`
- `findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(int year1, int year2)`

---

## 🖥 Menú de consola interactivo

Al ejecutar el proyecto, se presenta el siguiente menú:

```text
📚 MENÚ PRINCIPAL - LiterAlura
--------------------------------
1 - Buscar y registrar libro por título
2 - Listar todos los libros registrados
3 - Listar todos los autores
4 - Listar autores vivos en un año específico
5 - Listar libros por idioma
0 - Salir
```

---

## ⚠️ Validaciones importantes

- Se maneja el caso en que el idioma del libro sea `null` → se almacena como `"unknown"`.
- Se utiliza `Optional` para evitar errores de tipo `NullPointerException`.
- El menú maneja errores de entrada (`NumberFormatException`).
- Verificaciones al consumir la API si no se encontró resultado o si ya está registrado.

---

## 📁 Configuración en `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=1234
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🧪 Requisitos para correr el proyecto

- Java 21+
- PostgreSQL corriendo en localhost:5432
- Base de datos `literalura` creada previamente
- Maven (`mvnw install` o desde IDE)
- Ejecutar `MainApplication.java`

---

## 📌 Mejores prácticas aplicadas

- Arquitectura por capas clara y mantenible.
- Uso de `DTOs` para desacoplar las entidades de las respuestas externas.
- Manejo de errores y valores nulos.
- Relación entre entidades bien definida.
- JavaDoc en clases públicas y métodos clave.

---

## 📖 Créditos

Este proyecto forma parte del programa **Oracle Next Education** y ha sido desarrollado como una solución al desafío del módulo:

> **Java: trabajando con lambdas, streams y Spring Framework**  
> **Java: persistencia de datos y consultas con Spring Data JPA**

---

## ✨ Próximas mejoras sugeridas

- Exportar listados a archivo `.txt` o `.csv`.
- Implementar interfaz web con Spring Web + Thymeleaf.
- Agregar paginación en consultas.
- Validaciones con Bean Validation.
