# Imagen base con Java 17 y Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build

# Definir directorio de trabajo
WORKDIR /app

# Copiar todo el proyecto al contenedor
COPY . .

# Compilar la app (sin ejecutar tests)
RUN mvn clean package -DskipTests

# -------------------------
# Fase de ejecución
# -------------------------
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copiar el jar generado en la fase de build
COPY --from=build /app/target/crudmixto-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto (Render ignora este pero es útil localmente)
EXPOSE 8081

# Ejecutar la app
CMD ["java", "-jar", "app.jar"]

