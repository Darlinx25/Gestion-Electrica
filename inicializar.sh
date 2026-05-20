curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/clientes -H "Content-Type: application/json" -d '{"cedula":"1234567890","nombreCompleto":"pablito guitiérrez","telefono":"091234567","password":"123","esProfesional":false}'

curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/clientes/medios-pago -H "Content-Type: application/json" -d '{"clienteId":1, "medio":"TARJETA_DEBITO", "numero":"1234","fechaVencimiento":"2028-10-23", "digitoVerificacion":"123"}'

curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/cargas/estacion \-H "Content-Type: application/json" \-d '{"descripcion":"prueba estacion","calle":"Lenguas de Diamante","departamento":"Maldonado","longitud":2,"latitud":3}'

curl -X POST -v http://localhost:8080/Gestion-Electrica/carga/cargas/cargador -H "Content-Type: application/json" -d '{"tipo": "RAPIDA", "tieneCable": true, "tipoConector": "TIPO2", "estado": "DISPONIBLE", "potenciaMinima": 150, "estacionId": 1}'
