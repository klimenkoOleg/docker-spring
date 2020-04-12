#FROM openjdk:11.0.6-jre-slim as build
FROM openjdk:11.0.6-jdk as build
MAINTAINER Oleg Klimenko oklimenko@gmail.com
WORKDIR /workspace/app
#ENV BUILD_DIR=/workspace/app/
#WORKDIR $BUILD_DIR

COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle
#  cache Java dependencies step:
# 1) DO NOT copy src
# 2) run gradle to download all dependenciesc => store it in separate layer
RUN ./gradlew build -Dorg.gradle.console=verbose --full-stacktrace
# || return 0
COPY src src
#run gradle again, now with src in place
RUN ./gradlew build -Dorg.gradle.console=verbose --full-stacktrace
RUN mkdir build/dependency &&\
    cd build/dependency &&\
    jar -xf ../libs/*.jar


FROM openjdk:11.0.6-jre-slim
MAINTAINER Oleg Klimenko oklimenko@gmail.com
VOLUME /tmp
ENV APP_HOME=/workspace/app
ARG DEPENDENCY=/workspace/app/build/dependency
ENV MAIN_FULL_CLASS_NAME=com.oklimenko.dockerspring.DockerSpringApplication
#If the application Java dependencies don’t change, then the first layer (from BOOT-INF/lib) will not change
#so the build will be faster
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
# 1) just remember!!! use sh -c if any of variables to be processed!
# 2) ${0} for the "command" (in this case the first program argument) and ${@} for the "command arguments" (the rest of the program arguments).
ENTRYPOINT ["sh","-c","exec java ${JAVA_OPTS} -cp app:app/lib/* ${MAIN_FULL_CLASS_NAME} ${0} ${@}"]
