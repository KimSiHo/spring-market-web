FROM openjdk:11.0

EXPOSE 8080

JAR_FILE=target/jpashop-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} jpaShop.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/jpaShop.jar"]
