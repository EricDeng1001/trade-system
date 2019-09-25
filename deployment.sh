docker-compose -f src/main/docker/mysql.yml up -d
docker-compose -f src/main/docker/kafka.yml up -d
docker-compose -f src/main/docker/elasticsearch.yml up -d
java -jar target/trade-system.jar