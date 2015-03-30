DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
ROOT_DIR=${DIR}/..

NUM_USERS=1000
NUM_REQUESTS=200

${ROOT_DIR}/../data/generate_users.py $NUM_USERS | ${ROOT_DIR}/deploy/confluent/bin/kafka-console-producer --sync --topic users_json --broker-list localhost:9092 --property "parse.key=true"

${ROOT_DIR}/../data/generate_requests.py --print-key $NUM_REQUESTS $NUM_USERS | ${ROOT_DIR}/deploy/confluent/bin/kafka-console-producer --sync --topic requests_json --broker-list localhost:9092 --property "parse.key=true"
