mvn clean package docker:build

docker run -it --name package-image -p 8888:8080 example/package-image:1.0.0

curl 127.0.0.1:8888