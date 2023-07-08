FROM openjdk:11-slim as build
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:11-slim
WORKDIR application

#Скопировать каждый слой, полученный командой jarmode
COPY --from=build application/dependencies/ ./
COPY --from=build application/spring-boot-loader/ ./
COPY --from=build application/snapshot-dependencies/ ./
COPY --from=build application/application/ ./

#Использовать org.springframework.boot. loader.JarLauncher для запуска приложения
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]