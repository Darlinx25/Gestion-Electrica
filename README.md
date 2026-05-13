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

**Consola administrador Wildfly**
```
http://localhost:9990
```


