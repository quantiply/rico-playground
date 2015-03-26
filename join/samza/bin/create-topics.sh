DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

${DIR}/../deploy/confluent/bin/kafka-topics --zookeeper localhost:2181 --topic samza_metrics --create --partitions 1 --replication-factor 1

${DIR}/../deploy/confluent/bin/kafka-topics --zookeeper localhost:2181 --topic users_json --create --partitions 1 --replication-factor 1 --config "cleanup.policy=compact" --config "segment.bytes=104857600"
${DIR}/../deploy/confluent/bin/kafka-topics --zookeeper localhost:2181 --topic requests_json --create --partitions 1 --replication-factor 1
${DIR}/../deploy/confluent/bin/kafka-topics --zookeeper localhost:2181 --topic requests_join_user_json --create --partitions 1 --replication-factor 1

