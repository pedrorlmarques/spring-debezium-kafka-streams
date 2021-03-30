# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.pt.umbrella.order-service' is invalid and this project uses 'com.pt.umbrella.orderservice' instead.

# Getting Started

### Set Up Environment  

* Start Infrastructure docker-compose up

* Install Debezium Connector 

curl http://localhost:8083/connectors -X POST -d ''


##wal -> Write Ahead Log

### wal2json

wal2json is an output plugin for logical decoding.

https://github.com/eulerto/wal2json

```java
{
    "name": "order-connector",
    "config": {
        "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
        "tasks.max": "1",
        "database.hostname": "orders-postgresql",
        "database.port": "5432",
        "database.user": "orders",
        "database.password": "orders",
        "database.dbname": "orders",
        "database.server.name": "orders-postgresql",
        "schema.include.list": "public",
        "plugin.name": "wal2json",
        "table.include.list": "public.orders"
    }
}

```

### pgoutput 

```java
{
    "name": "order-connector",
    "config": {
        "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
        "tasks.max": "1",
        "database.hostname": "orders-postgresql",
        "database.port": "5432",
        "database.user": "orders",
        "database.password": "orders",
        "database.dbname": "orders",
        "database.server.name": "orders-postgresql",
        "schema.include.list": "public",
        "plugin.name": "pgoutput",
        "table.include.list": "public.orders"
    }
}

```

### Outbox Pattern with pgoutput

````java

{
        "name": "order-outbox-connector",
        "config": {
            "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
            "tasks.max": "1",
            "database.hostname": "orders-postgresql",
            "database.port": "5432",
            "database.user": "orders",
            "database.password": "orders",
            "database.dbname": "orders",
            "database.server.name": "orders-postgresql",
            "schema.include.list": "public",
            "table.include.list": "public.outbox",
            "tombstones.on.delete": "false",
            "plugin.name": "pgoutput",
            "key.converter": "org.apache.kafka.connect.storage.StringConverter",
            "value.converter": "org.apache.kafka.connect.json.JsonConverter",
            "transforms": "outbox",
            "transforms.outbox.type": "io.debezium.transforms.outbox.EventRouter",
            "transforms.outbox.table.fields.additional.placement": "type:envelope:eventType",
            "poll.interval.ms": "100"
        }
}

````


### Outbox Pattern with pgoutput and router topic

````java

{
        "name": "order-outbox-connector",
        "config": {
            "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
            "tasks.max": "1",
            "database.hostname": "orders-postgresql",
            "database.port": "5432",
            "database.user": "orders",
            "database.password": "orders",
            "database.dbname": "orders",
            "database.server.name": "orders-postgresql",
            "schema.include.list": "public",
            "table.include.list": "public.outbox",
            "tombstones.on.delete": "false",
            "plugin.name": "pgoutput",
            "key.converter": "org.apache.kafka.connect.storage.StringConverter",
            "value.converter": "org.apache.kafka.connect.json.JsonConverter",
            "transforms": "outbox",
            "transforms.outbox.type": "io.debezium.transforms.outbox.EventRouter",
            "transforms.outbox.table.fields.additional.placement": "type:envelope:eventType",
            "transforms.outbox.route.topic.replacement" : "${routedByValue}.request",
            "poll.interval.ms": "100"
        }
}

````
