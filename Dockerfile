FROM maven:3.6.0 as build

WORKDIR /.

COPY . .

RUN mvn clean compile assembly:single

FROM openjdk:11

WORKDIR /target

COPY --from=build /./target .

EXPOSE 7070

CMD java -jar sepotipayi-soap.jar
