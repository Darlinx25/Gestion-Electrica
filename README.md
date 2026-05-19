# Taller Java 

## Iteración I

**Compilar, empaquetar y ejecutar**
```
mvn clean package && docker compose up --build 
```
**Acceso a la Base de Datos**
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

Fin Carga:
```
curl -X POST http://localhost:8080/Gestion-Electrica/carga/cargas/finalizar -H "Content-Type: application/json" -d '{"clienteId":1, "carga":50.0}'
```

Consultar cargador:
```
curl -v http://localhost:8080/Gestion-Electrica/carga/cargas
```

Consultar Historico:
```
curl "http://localhost:8080/Gestion-Electrica/carga/cargas/historico/1?ini=2026-05-19T20:00:00&fin=2026-05-19T20:10:00"
```



**Consola administrador Wildfly**
```
http://localhost:9990
```


