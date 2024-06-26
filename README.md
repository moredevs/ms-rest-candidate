# Servicio de Marvel con JWT Authentication

Este proyecto es una aplicación Spring Boot que actúa como un servicio RESTful para consumir datos de Marvel. El controlador principal es `ComicController`, que maneja las solicitudes relacionadas con los personajes de Marvel y también proporciona una funcionalidad de bitácora mediante el endpoint `/api/v1/public/get-query-logs`.

## Controlador `ComicController`

### Obtener Personajes

#### Endpoint
- Método: GET
- URL: `/api/v1/public/characters`
- Parámetros de consulta:
    - `page` (Opcional, valor predeterminado: 0): Página de resultados.
    - `limit` (Opcional, valor predeterminado: 20): Número máximo de personajes por página.

#### Respuesta Exitosa
- Código de estado: 200 OK
- Tipo de respuesta: JSON
- Estructura de respuesta: [ResponseApi](#responseapi)

### Obtener un Personaje Específico

#### Endpoint
- Método: GET
- URL: `/api/v1/public/characters/{characterId}`
- Path variable:
    - `characterId`: ID del personaje.

#### Respuesta Exitosa
- Código de estado: 200 OK
- Tipo de respuesta: JSON
- Estructura de respuesta: [ResponseApiCharacter](#responseapicharacter)

### Obtener Registros de Consulta (Bitácora)

#### Endpoint
- Método: GET
- URL: `/api/v1/public/get-query-logs`

#### Respuesta Exitosa
- Código de estado: 200 OK
- Tipo de respuesta: JSON
- Estructura de respuesta: Lista de [QueryLogDto](#querylogdto)

## Modelos de Respuesta

### `ResponseApi`

```json
{
  "data": {
    // Datos de los personajes
  },
  "code": 200,
  "status": "OK"
}
```
## ResponseApiCharacter
```json
{
"data": {
// Detalles del personaje
},
"code": 200,
"status": "OK"
} 
```
##  QueryLogDto
```json
 
{
  "id": 1,
  "serviceName": "GET_CHARACTERS",
  "queryTime": "2023-12-01T00:17:33.355779"
}
```


## Preparación

Antes de ejecutar este servicio, asegúrate de tener el JAR del servicio que consume Marvel (`marvel-api-consumer-1.0.0.jar`). Debes agregar este JAR a tu repositorio local de Maven utilizando el siguiente comando:

```bash
mvn install:install-file -Dfile=marvel-api-consumer-1.0.0.jar -DgroupId=com.api.consumer -DartifactId=marvel-api-consumer -Dversion=1.0.0 -Dpackaging=jar