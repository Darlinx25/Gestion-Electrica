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

**Clientes:**

Consultar clientes:
```
curl -v http://localhost:8080/Gestion-Electrica/carga/clientes
```
Alta cliente comun
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/clientes -H "Content-Type: application/json" -d '{"cedula":"1234567890","nombreCompleto":"pablito guitiérrez","telefono":"091234567","password":"123","esProfesional":false}'
```

Alta medio de pago
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/clientes/medios-pago -H "Content-Type: application/json" -d '{"clienteId":1, "medio":"TARJETA_DEBITO", "numero":"1234","fechaVencimiento":"2028-10-23", "digitoVerificacion":"123"}'
```



**Cargador:**

Alta Estacion:
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/cargas/estacion \-H "Content-Type: application/json" \-d '{"descripcion":"prueba estacion","calle":"Lenguas de Diamante","departamento":"Maldonado","longitud":2,"latitud":3}'
```

Alta Cargador:
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/cargas/cargador -H "Content-Type: application/json" -d '{"tipo": "RAPIDA", "tieneCable": true, "tipoConector": "TIPO2", "estado": "DISPONIBLE", "potenciaMinima": 150, "estacionId": 1}'
```
Inicio Carga:
```
curl -X POST -v "http://localhost:8080/Gestion-Electrica/carga/cargas/iniciar" -H "Content-Type: application/json" -d '{"clienteId": 1,"medioPagoId":1,"cargadorId":1}'
```
Actualizar Porcentaje Avance de Carga:
```
curl -X POST -v "http://localhost:8080/Gestion-Electrica/carga/cargas/actualizar" -H "Content-Type: application/json" -d '{"cargaId": 1,"porcentajeAvance":25}'
```

Fin Carga:
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/cargas/finalizar -H "Content-Type: application/json" -d '{"clienteId":1, "carga":50.0}'
```
Consultar Pagos:
```
curl -v "http://localhost:8080/Gestion-Electrica/carga/pagos/pagosRealizados/1?ini=2026-05-19T20:00:00&fin=2027-05-19T20:10:00"
```
Consultar cargador:
```
curl -v http://localhost:8080/Gestion-Electrica/carga/cargas
```
Consultar Historico:
```
curl -v "http://localhost:8080/Gestion-Electrica/carga/cargas/historico/1?ini=2026-05-19T20:00:00&fin=2026-05-19T20:10:00"
```

**Consola administrador Wildfly**
```
http://localhost:9990
```


