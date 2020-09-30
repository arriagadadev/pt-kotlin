# Prueba Técnica - Android Developer (Kotlin)
Este repositorio contiene las instrucciones de la prueba técnica para el puesto de **Android Developer (Kotlin)**.

## INTRODUCCIÓN
Con esta prueba técnica se busca evaluar las capacidades técnicas que tienen relación con las principales funciones que son requeridas para el cargo.

#### ¿Qué se busca evaluar?
  + Creatividad para resolver los requerimientos
  + Calidad del código entregado (estructura y buenas prácticas)
  + Eficiencia de los algoritmos entregados
  + Familiaridad con el stack de desarrollo y método de autentificación de APIs
  
## Ejercicio

Crear una aplicación que transmita la posición del GPS (sólo longitud y latitud) a un endpoint.

#### Requerimientos generales

La aplicación debe cumplir con los siguientes **requisitos funcionales:**

    - Login para introducir email y password (datos de prueba serán proporcionados junto con la prueba técnica)
    - Recibir el token que retorna el login y utilizarlo en las siguientes consultas
    - Obtener el IMEI del dispositivo (Android version < 10) o solicitar el número de teléfono al usuario para utilizarlo como identificador en las consultas
    - Obtener información del GPS (latitud y longitud) y transmitirlo a un endpoint cada 30 segundos
    - Si la consulta falla se debe mostrar en pantalla de alguna manera que existe un error (error handle)

Por fines demostrativos y en honor al tiempo, no es necesario validar el formulario del login.

#### Documentación de los endpoints necesarios para la prueba técnica

**Login**
Endpoint: /api/login
Method: Post
Content-Type: application/json
Accept: application/json
Body: 
```json
{
  "email": "String",
  "access_token": "String"
}
```
Response example: 
```json
{
  "user": {
    "id": "Integer",
    "name": "String",
    "email": "String",
    "organization_id": "Integer",
    "email_verified_at": null,
    "created_at": "String",
    "updated_at": "String"
  },
  "access_token": "string"
}
```

**GPS**
Endpoint: /api/pt-kotlin
Method: Post
Authentication: OAuth2
Header: Authorization: Bearer TOKEN
Content-Type: application/json
Accept: application/json
Body: 
```json
{
  "identifier": "String",
  "gps": {
    "latitude": "Decimal(10,8)",
    "longitude": "Decimal(11, 8)"
  }
}
```
Response example: 
```json
{
  "status": "OK"
}
```

#### Cómo entregar
**Antes de comenzar a desarrollar la prueba técnica:**
    * Realizar un `Fork` de este repositorio (https://github.com/arriagadadev/pt-kotlin).
    * Clonar el fork a su máquina local
    * Crear un `branch` en su cuenta de GitHub utilizando su nombre completo.
**Al finalizar:**
    * Realizar un `Commit` de su proyecto, **enviar un `Pull Request` al branch anteriormente creado**
