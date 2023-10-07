FROM openjdk:17
VOLUME /data/apps
ADD app-1.0.jar app-1.0.jar
RUN ls
EXPOSE 9600
ENV TZ Asia/Shanghai
ENV Dprofiles "test"
ENV JAVA_OPTS="\
-server \
-Xmx128m \
-Xms128m \
-Xmn128m"
ENTRYPOINT java ${JAVA_OPTS} -jar --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.io=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.util.concurrent=ALL-UNNAMED --add-opens=java.rmi/sun.rmi.transport=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.math=ALL-UNNAMED /app-1.0.jar --spring.profiles.active=${Dprofiles}