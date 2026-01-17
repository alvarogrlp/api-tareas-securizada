# Sistema de Autenticacion y Seguridad para API REST

Este proyecto consiste en una base solida de seguridad para aplicaciones web modernas. Su funcion principal es gestionar el acceso de usuarios mediante un sistema de llaves digitales, permitiendo que solo las personas autorizadas puedan consultar o modificar la informacion del sistema.

## Que hace este proyecto

El sistema permite que un usuario se identifique con sus credenciales y reciba una llave digital temporal. Con esta llave, el usuario puede realizar peticiones de forma segura sin tener que enviar su contraseña constantemente.

Puntos clave del proyecto:
- Inicio de sesion seguro: Validacion de identidad del usuario.
- Proteccion de datos: Bloqueo automatico de accesos no autorizados a las secciones privadas.
- Panel de pruebas: Incluye una interfaz visual para que los responsables del proyecto puedan probar el funcionamiento de forma sencilla.
- Control de conexiones: Configuracion preparada para permitir la conexion desde diferentes aplicaciones frontales o paginas web.

## Tecnologias utilizadas

Para el desarrollo se han empleado herramientas estandar en el sector tecnologico:
- Java: Lenguaje de programacion principal.
- Spring Boot: Marco de trabajo para la creacion del servidor.
- Spring Security: Herramienta especifica para gestionar las reglas de acceso y proteccion.
- JWT: Formato de las llaves digitales utilizadas para la navegacion segura.
- Swagger / OpenAPI: Interfaz visual para documentacion y pruebas.
- Maven: Gestor para la organizacion del proyecto y sus librerias.

## Como ponerlo en marcha

Para poner en funcionamiento este sistema y realizar pruebas de acceso, siga estos pasos:

1. **Preparacion**: Asegurese de tener instalada la herramienta Maven en su ordenador (es el motor que hace funcionar el proyecto).
2. **Arranque**: Abra una ventana de comandos en la carpeta raiz del proyecto y escriba: `mvn spring-boot:run`
3. **Acceso**: Una vez que el sistema este en marcha, podra ver la interfaz de control abriendo su navegador web y pegando la siguiente direccion:
   http://localhost:8080/swagger-ui.html

## Pasos para realizar una prueba de acceso

Si desea comprobar como el sistema valida a un usuario:

1. Entre en la direccion mencionada arriba (Interfaz de pruebas).
2. Busque la opcion que dice **/api/auth/login** y haga clic en el boton de Try it out.
3. Introduzca las credenciales de prueba:
   - **Usuario**: user
   - **Contraseña**: pass
4. Haga clic en el boton de Execute.
5. El sistema le devolvera un codigo largo (llave digital). A partir de ese momento, podra usar esa llave para acceder al resto de apartados protegidos del sistema.
