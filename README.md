# Taller Java - Gestión Eléctrica

## Arquitectura

### Diagrama de módulos

<img width="920" height="728" alt="modulofinal" src="https://github.com/user-attachments/assets/36702871-e36d-4152-890c-b4ff051da780" />

## Estructura del proyecto

```
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── README.md
├── inicializar.sh
├── config.cli / config_docker.cli
└── src
    ├── main
    │   ├── java/org/tallerjava
    │   │   ├── GestionElectricaApplication.java
    │   │   │
    │   │   ├── moduloCargas
    │   │   │   ├── aplicacion/         
    │   │   │   ├── dominio/           
    │   │   │   ├── infraestructura/    
    │   │   │   └── interfase/         
    │   │   │
    │   │   ├── moduloClientes
    │   │   │   ├── aplicacion/         
    │   │   │   ├── dominio/            
    │   │   │   ├── infraestructura/    
    │   │   │   └── interfase/          
    │   │   │
    │   │   ├── moduloComun             
    │   │   │   ├── eventosCarga/
    │   │   │   └── eventosCliente/
    │   │   │
    │   │   └── moduloPagos
    │   │       ├── aplicacion/        
    │   │       ├── dominio/            
    │   │       ├── infraestructura/    
    │   │       └── interfase/         
    │   │
    │   └── webapp/WEB-INF/          
    │
    └── test/                          

```
## Descripción de la implementación

El sistema fue desarrollado siguiendo una arquitectura modular compuesta por los módulos de Clientes, Cargas y Pagos, además de un módulo común encargado de compartir eventos entre ellos. Cada módulo fue organizado en capas de dominio, aplicación, infraestructura e interfase, separando la lógica de negocio, persistencia y comunicación externa. La interacción entre módulos se realizó principalmente mediante eventos y observers para reducir el acoplamiento, por ejemplo utilizando CargaFinalizadaEvent para notificar al módulo de pagos cuando una carga termina. Se implementaron repositorios utilizando JPA y MariaDB y endpoints REST para probar los distintos casos de uso del sistema. El proyecto se ejecuta utilizando Docker y WildFly, se realizaron tests unitarios utilizando repositorios en memoria para validar la lógica de negocio de cada módulo.


## Tecnologías utilizadas
- Java
- Jakarta EE
- CDI
- JPA
- MariaDB
- Docker
- WildFly

## Integrantes
- Facundo Salaberry
- Alexis Menchaca
- Ignacio Ortega
- Kevin Jaffe

___

## Compilar, empaquetar y ejecutar
```
mvn clean package && docker compose up --build 
```
## Acceso a la Base de Datos
```
docker exec -it tallerjava-mariadb mariadb -u root -pmariapass tallerJava
```
---

## Endpoints para pruebas

**App Móvil:**

Registrar cliente común:
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/registrar -H "Content-Type: application/json" -d '{"cedula":"1234567890","nombreCompleto":"pablito guitiérrez","telefono":"091234567","password":"123","esProfesional":false}'
```
Registrar cliente profesional:
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/registrar -H "Content-Type: application/json" -d '{"cedula":"2234567890","nombreCompleto":"josefina rodríguez","telefono":"091234567","password":"123","esProfesional":true,"tipoProfesional":"UBER","porcentajeDescuento":20.5}'
```
Alta medio de pago (cuenta UTE / tarjeta débito / tarjeta crédito):
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/medios-pago -H "Content-Type: application/json" -d '{"clienteId":"1","medio":"CUENTA_UTE","numeroCuenta":"1234"}'
curl -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/medios-pago -H "Content-Type: application/json" -d '{"clienteId":"1","medio":"TARJETA_DEBITO","numero":"1234","fechaVencimiento":"2028-10-23","digitoVerificacion":"123"}'
curl -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/medios-pago -H "Content-Type: application/json" -d '{"clienteId":"1","medio":"TARJETA_CREDITO","numero":"2234","fechaVencimiento":"2028-10-23","digitoVerificacion":"223"}'
```
Realizar reclamo:
```
curl -X POST http://localhost:8080/Gestion-Electrica/API/clientes/movil/reclamo -H "Content-Type: application/json" -d '{"clienteId":1,"informacion":"El cargador no funciona correctamente"}'
```
Iniciar carga:
```
curl -X POST -v "http://localhost:8080/Gestion-Electrica/API/cargas/movil/iniciar" -H "Content-Type: application/json" -d '{"clienteId": 1,"medioPagoId":1,"cargadorId":1}'
```
Ver carga actual:
```
curl -v http://localhost:8080/Gestion-Electrica/API/cargas/movil/carga-actual/1
```
Consultar estaciones:
```
curl -v http://localhost:8080/Gestion-Electrica/API/cargas/movil/estaciones
```
Consultar histórico de cargas:
```
curl -v "http://localhost:8080/Gestion-Electrica/API/cargas/movil/historico/1?ini=2026-05-19T20:00:00&fin=2026-05-19T20:10:00"
```

**Gestor Web:**

Alta estación:
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/API/cargas/web/estacion -H "Content-Type: application/json" -d '{"descripcion":"prueba estacion","calle":"Lenguas de Diamante","departamento":"Maldonado","longitud":2,"latitud":3}'
```
Alta cargador:
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/API/cargas/web/cargador -H "Content-Type: application/json" -d '{"tipo": "RAPIDA", "tieneCable": true, "tipoConector": "TIPO2", "estado": "DISPONIBLE", "potenciaMinima": 150, "estacionId": 1}'
```
Listar clientes:
```
curl -v http://localhost:8080/Gestion-Electrica/API/clientes/web/listar
```
Consultar pagos realizados:
```
curl -v "http://localhost:8080/Gestion-Electrica/API/pagos/web/pagosRealizados/1?ini=2026-05-19T20:00:00&fin=2027-05-19T20:10:00"
```

**Cargador:**

Actualizar porcentaje avance de carga:
```
curl -X POST -v "http://localhost:8080/Gestion-Electrica/API/cargas/actualizar" -H "Content-Type: application/json" -d '{"cargaId": 1,"porcentajeAvance":25}'
```
Finalizar carga:
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/API/cargas/finalizar -H "Content-Type: application/json" -d '{"clienteId":1, "carga":50.0}'
```

**Consola administrador Wildfly**
```
http://localhost:9990
```


