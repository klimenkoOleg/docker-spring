# docker-spring

<h1>Описание</h1>

1. Хелм чарт находится в директории<br>
chart-dir-to-run

2. Установка в этой директории через ./install.sh или вручную через так:<br> 
helm install myapp ./hello-chart --atomic

3. servicemonitor входит в состав хелм чарта, находится в файле 
    chart-dir-to-run/hello-chart/templates/servicemonitor.yaml

4. Дашборды c конфигмапом дашборды для графаны лежат в отдельном манифесте <br> 
chart-dir-to-run/dashboard.yaml

5. Отдельно stresstest.yaml - манифсет с Job-ой, находится в<br>
chart-dir-to-run/stresstest.yaml 

6. Скриншоты дашборды после нагрузки лежат в директории<br>
chart-dir-to-run/stress-screens <br>

В левом столбце дашбордов - метрики с разбивкой по API методам:
1. Latency (response time) с квантилями по 0.5, 0.95, 0.99, max
2. RPS
3. Error Rate - количество 500ых ответов

В правом столбце - метрики в целом по сервису, взятые с nginx-ingress-controller:<br>
1. Latency (response time) с квантилями по 0.5, 0.95, 0.99, max
2. RPS
3. Error Rate - количество 500ых ответов

<h1> General description</h1>


1. Based on Java 11 / Spring Boot 2.2.6.RELEASE
2. Exposes one REST endpoint: GET /health 
The endpoint returns: {"status": "OK"}
3. Contains Dockerfile to build docker image, layered
4. To build docker image, use: docker-build.sh (Mac OS, Linux)
5. To run docker container, use: docker-run.sh (Mac OS, Linux)


Java 11 base Docker images are huge! 
Please be patient during Docker build.
