# Taller Java - Gestión Eléctrica

## Arquitectura

### Diagrama de módulos

<img width="1107" height="764" alt="image" src="https://github.com/user-attachments/assets/1a5dd375-628d-493e-b762-47540522c16d" />


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
    │   │
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
    │   │   ├── moduloPagos
    │   │   │   ├── aplicacion/
    │   │   │   ├── dominio/
    │   │   │   ├── infraestructura/
    │   │   │   └── interfase/
    │   │   │
    │   │   ├── moduloMonitoreo
    │   │   │   ├── infraestructura/
    │   │   │   └── interfase/
    │   │   │
    │   │   └── moduloComun
    │   │       ├── eventosCarga/
    │   │       ├── eventosCliente/
    │   │       └── eventosPago/
    │   │
    │   └── webapp/WEB-INF/
    │
    └── test/
        └── org/tallerjava
            ├── moduloCargas/
            ├── moduloClientes/
            └── moduloPagos/                        

```
## Descripción de la implementación

El sistema fue desarrollado siguiendo una arquitectura modular compuesta por los módulos de Clientes, Cargas y Pagos, además de un módulo común encargado de compartir eventos entre ellos. Cada módulo fue organizado en capas de dominio, aplicación, infraestructura e interfase, separando la lógica de negocio, persistencia y comunicación externa. La interacción entre módulos se realizó principalmente mediante eventos y observers para reducir el acoplamiento, por ejemplo utilizando CargaFinalizadaEvent para notificar al módulo de pagos cuando una carga termina, o CargaIniciadaEvent y CargaFinalizadaEvent para que el módulo de monitoreo registre métricas de cargas y pagos en InfluxDB y sean visualizadas en Grafana. Se implementaron repositorios utilizando JPA y MariaDB, endpoints REST para probar los distintos casos de uso del sistema y un módulo de monitoreo que expone métricas mediante Micrometer. Se incorporó procesamiento asincrónico de reclamos mediante JMS. Cuando un cliente realiza un reclamo, se encola en una queue de ActiveMQ Artemis y se responde al cliente inmediatamente. Un Message-Driven Bean consume el mensaje, lo clasifica como POSITIVO, NEGATIVO o NEUTRO usando Llama 3 vía Ollama, y persiste la etiqueta en la base de datos. Los reclamos negativos se consultan mediante un endpoint REST. El proyecto se ejecuta utilizando Docker y WildFly, se realizaron tests unitarios utilizando repositorios en memoria para validar la lógica de negocio de cada módulo. 

## Tecnologías utilizadas
- Java 17
- Jakarta EE 10 (CDI, JPA, REST, Security, Bean Validation)
- MariaDB
- InfluxDB 1.8
- Grafana
- Docker
- WildFly 27
- Jakarta Messaging (JMS / ActiveMQ Artemis)
- Ollama
- Llama 3

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

## Grafana e InfluxDB

Al ejecutar `docker compose up --build` se levantan automáticamente InfluxDB y Grafana.

**InfluxDB** (puerto 8086): base de datos de series temporales donde la aplicación registra métricas de cargas y pagos mediante Micrometer.

**Grafana** (puerto 3003): herramienta de visualización de métricas.

### Configurar datasource en Grafana

1. Ir a http://localhost:3003 e iniciar sesión (admin / admin)
2. Ir a Configuration > Data Sources > Add data source
3. Seleccionar InfluxDB
4. Configurar:
- URL: http://influxdb:8086
- Database: metricasTallerJava (root / root)
- Min time interval: 10s
5. Click en Save & Test

### Importar dashboard

1. Ir a Dashboards > Import
2. Seleccionar el archivo dashboard-1781472511425.json
3. Seleccionar el datasource InfluxDB y hacer clic en Import

<img width="1547" height="669" alt="image" src="https://github.com/user-attachments/assets/162638db-b286-40e0-b22f-992bd7271322" />

---
## Ollama

El clasificador de reclamos utiliza Ollama con el modelo Llama 3. Al ejecutar `docker compose up --build` se levanta automáticamente el servicio Ollama.

### Descargar el modelo (primera vez)

```
docker exec -it tallerjava-ollama ollama pull llama3
```
> El modelo se descarga una sola vez (~4.7 GB) y queda cacheado en el volumen `ollama_data`.

#### Consulta de reclamos a la DB:
<img width="1546" height="292" alt="image" src="https://github.com/user-attachments/assets/f059de0b-fad0-4b05-9964-1e3925347a2f" />


---

## Endpoints para pruebas
Todos los endpoints usan autenticación Basic. El usuario se autentica con -u <cédula>:<password> (clientes) o -u admin:admin (administradores).

## 1. Registro y medios de pago
### Alta Cliente común
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/registrar \
  -H "Content-Type: application/json" \
  -d '{"cedula":"1234567890","nombreCompleto":"pablito guitiérrez","telefono":"091234567","password":"123","esProfesional":false}'
```

### Alta Cliente profesional
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/registrar \
  -H "Content-Type: application/json" \
  -d '{"cedula":"2234567890","nombreCompleto":"josefina rodríguez","telefono":"091234567","password":"123","esProfesional":true,"tipoProfesional":"UBER","porcentajeDescuento":20.5}'
```

### Alta Administrador
```
curl -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/registrar \
  -H "Content-Type: application/json" \
  -d '{"cedula":"admin","nombreCompleto":"Administrador","telefono":"000","password":"admin","rol":"ADMIN","esProfesional":false}'
```
### Alta de medios de pago
```
curl -u 1234567890:123 -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/medios-pago \
  -H "Content-Type: application/json" \
  -d '{"clienteId":"1","medio":"CUENTA_UTE","numeroCuenta":"1234"}'
```
```
curl -u 1234567890:123 -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/medios-pago \
  -H "Content-Type: application/json" \
  -d '{"clienteId":"1","medio":"TARJETA_DEBITO","numero":"1234","fechaVencimiento":"2028-10-23","digitoVerificacion":"123"}'
```
```
curl -u 1234567890:123 -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/medios-pago \
  -H "Content-Type: application/json" \
  -d '{"clienteId":"1","medio":"TARJETA_CREDITO","numero":"2234","fechaVencimiento":"2028-10-23","digitoVerificacion":"223"}'
```
## 2. Cargas
### Iniciar carga
```
curl -u 1234567890:123 -X POST -v "http://localhost:8080/Gestion-Electrica/API/cargas/movil/iniciar" \
  -H "Content-Type: application/json" \
  -d '{"clienteId": 1, "medioPagoId": 1, "cargadorId": 1}'
```

### Consultar carga actual
```
curl -u 1234567890:123 -v http://localhost:8080/Gestion-Electrica/API/cargas/movil/carga-actual/1
```

### Actualizar porcentaje de avance
```
curl -u 1234567890:123 -X POST -v "http://localhost:8080/Gestion-Electrica/API/cargas/actualizar" \
  -H "Content-Type: application/json" \
  -d '{"clienteId": 1, "porcentajeAvance": 25}'
```

### Finalizar carga
```
curl -u 1234567890:123 -X POST -v http://localhost:8080/Gestion-Electrica/API/cargas/finalizar \
  -H "Content-Type: application/json" \
  -d '{"clienteId": 1, "carga": 50.0}'
```

### Histórico de cargas
```
curl -u 1234567890:123 -v "http://localhost:8080/Gestion-Electrica/API/cargas/movil/historico/1?ini=2026-05-19T20:00:00&fin=2027-05-19T20:10:00"
```

### Consultar estaciones de carga
```
curl -u 1234567890:123 -v http://localhost:8080/Gestion-Electrica/API/cargas/movil/estaciones
```
## 3. Pagos
### Pagar carga (paga la carga impaga del cliente)
```
curl -u 1234567890:123 -X POST -v "http://localhost:8080/Gestion-Electrica/API/pagos/web/pagarCarga/1?medioPagoId=2"
```

## 4. Reclamos
### Realizar Reclamos
```
curl -u 1234567890:123 -X POST -v http://localhost:8080/Gestion-Electrica/API/clientes/movil/reclamo \
  -H "Content-Type: application/json" \
  -d '{"clienteId": 1, "informacion": "El cargador no funciona correctamente"}'
```

### Consultar reclamos negativos
```
curl http://localhost:8080/Gestion-Electrica/API/clientes/web/reclamos/negativos
```

## 5. Administración (gestor web)
### Alta de estación
```
curl -u admin:admin -X POST -v http://localhost:8080/Gestion-Electrica/API/cargas/web/estacion \
  -H "Content-Type: application/json" \
  -d '{"descripcion":"prueba estacion","calle":"Lenguas de Diamante","departamento":"Maldonado","longitud":2,"latitud":3}'
```

### Alta de cargador
```
curl -u admin:admin -X POST -v http://localhost:8080/Gestion-Electrica/API/cargas/web/cargador \
  -H "Content-Type: application/json" \
  -d '{"tipo": "RAPIDA", "tieneCable": true, "tipoConector": "TIPO2", "estado": "DISPONIBLE", "potenciaMinima": 150, "estacionId": 1}'
```

### Listar clientes
```
curl -u admin:admin -v http://localhost:8080/Gestion-Electrica/API/clientes/web/listar
```

### Consultar pagos realizados
```
curl -u admin:admin -v "http://localhost:8080/Gestion-Electrica/API/pagos/web/pagosRealizados/1?ini=2026-05-19T20:00:00&fin=2027-05-19T20:10:00"
```

**Consola administrador Wildfly**
```
http://localhost:9990
```


