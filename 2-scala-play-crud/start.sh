docker build -t scala .
docker run -p 9000:9000 -d scala
nginx http 9000
