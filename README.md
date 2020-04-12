# docker-spring

1. Based on Java 11 / Spring Boot 2.2.6.RELEASE
2. Exposes one REST endpoint: GET /health 
The endpoint returns: {"status": "OK"}
3. Contains Dockerfile to build docker image, layered
4. To build docker image, use: docker-build.sh (Mac OS, Linux)
5. To run docker container, use: docker-run.sh (Mac OS, Linux)


Java 11 base Docker images are huge! 
Please be patient during Docker build.