# Spring Boot: Application PetClinic

## Características

### 1. Pruebas unitarias — versión 1.0.0

### 2. Pruebas de integración — versión 2.0.0

## Semana 12: pruebas de integración de VisitController

Las pruebas de integración usan Spring Boot, MockMvc y el perfil H2 para verificar los endpoints HTTP de visitas.

### Integrante A — Huanco Grecia Milagros

En Git, sus commits aparecen como Grecia Huanco (`greciahuancob@gmail.com`).

- Agregó `VisitDTO` y `VisitMapper` para transferir los datos de las visitas.
- Implementó la consulta de todas las visitas mediante `GET /visits`.
- Implementó la consulta por identificador mediante `GET /visits/{id}`.
- Añadió en `VisitControllerTest.java`:
  - `testFindAllVisits`: comprueba que `GET /visits` responda `200 OK` y devuelva una lista JSON.
  - `testFindVisitOK`: comprueba que `GET /visits/1` responda `200 OK` y que la descripción sea `rabies shot`.
  - `testFindVisitKO`: comprueba que `GET /visits/666` responda `404 Not Found`.

Commits relacionados:

- `afdd02a` — Implementar listado y consulta de visitas por ID.
- `8e5aad2` — Agregar pruebas de integración para listar y consultar visitas.

### Integrante B — Barzola Veliz

En Git, sus commits aparecen como José Barzola Veliz (`desroberto5@gmail.com`).

- Agregó `VisitRepository` y las operaciones de creación, actualización y eliminación en `VisitService` y `VisitServiceImpl`.
- Implementó en `VisitController`:
  - `POST /visits`: crea una visita y responde `201 Created`.
  - `PUT /visits/{id}`: actualiza la visita y responde `200 OK` con los datos modificados.
  - `DELETE /visits/{id}`: elimina la visita y responde `200 OK`.
- Añadió en `VisitControllerTest.java`:
  - `testCreateVisit`: comprueba la creación y los datos principales de la respuesta.
  - `testUpdateVisit`: crea una visita, la actualiza y comprueba el cambio.
  - `testDeleteVisit`: crea una visita y luego la elimina.

Commits relacionados:

- `9524374` — Add Visit HTTP endpoints and integration tests.
- `26e51c4` — Merge main Visit queries with Barzola CRUD endpoints; integra los cambios de ambos integrantes y resuelve conflictos.

## Validación

La ejecución final de la suite con H2 terminó correctamente: 33 pruebas, 0 fallos y 0 errores.

Para ejecutar las pruebas desde PowerShell:

```powershell
.\mvnw.cmd clean test "-Dspring.profiles.active=h2"
```
