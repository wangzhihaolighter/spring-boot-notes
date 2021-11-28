docker run -d --name tomcat \
  -p 9090:8080 \
  -e ALLOW_EMPTY_PASSWORD=yes \
  bitnami/tomcat:latest

docker cp /path/to/app.war tomcat:/app

curl --request GET -sL --url 'http://localhost:9090/app/'
