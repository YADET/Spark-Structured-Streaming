# kafka infra


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