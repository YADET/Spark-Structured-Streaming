# Spark-Structured-Streaming

This project illustrates how to ingest nested json dataset JSON streams from kafka into mysql. 
Two docker images for building kafka and mysql are provided. First initialize them.

Kafka publisher is in python so remember to activate env and add python plugin if you are using intellij.

run following commands to perepare your env:

```bash
$ cd /compose
$ docker-compose -f kafka-single-node.yml up -d
$ docker-compose -f kafka-single-node.yml up
$ docker ps
$ nc -vz localhost 9092
$ docker exec -it compose_kafka_1 bash
$ docker-compose -f kafka-single-node.yml down
```

```bash
$ docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=root -d mysql
$ docker ps
$ docker start mysql
$ 
```

```bash
CREATE DATABASE test;

USE test;

CREATE TABLE camera ( id smallint unsigned not null auto_increment, 
device_id varchar(20) not null, has_person varchar(20) not null, start_time varchar(20) not null,
constraint pk_camera primary key (id));

INSERT INTO camera ( id, device_id, has_person, start_time ) VALUES ( null, 'awJo6rH' , 'true', '2016-12-29 00:00:00');

select * from camera;
```
