FROM openjdk:11.0-oracle

LABEL maintainer="kim125y@naver.com"

EXPOSE 80

EXPOSE 5432

ARG JAR_FILE=target/jpashop-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} jpaShop.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=real", "-jar", "/jpaShop.jar"]
