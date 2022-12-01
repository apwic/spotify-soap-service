FROM maven:3.6.0 as build

WORKDIR /soap

COPY . .

RUN mvn clean compile assembly:single

FROM openjdk:11

WORKDIR /target

COPY .env .
COPY --from=build /soap/target .

CMD java -jar sepotipayi-soap.jar

EXPOSE 7070