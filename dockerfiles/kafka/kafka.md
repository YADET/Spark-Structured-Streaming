# kafka infra


```bash
$ cd /compose
$ docker-compose -f kafka-single-node.yml up -d
$ docker-compose -f kafka-single-node.yml up
$ docker ps
$ nc -vz localhost 9092
$ docker exec -it compose_kafka_1 bash
$ docker-compose -f kafka-single-node.yml down
```