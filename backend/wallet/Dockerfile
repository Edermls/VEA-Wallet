FROM maven:3.9-eclipse-temurin-17

WORKDIR /app

COPY . .

RUN mvn -f backend/pom.xml clean package -DskipTests

EXPOSE 8080

CMD sh -c "java -jar backend/target/*.jar"