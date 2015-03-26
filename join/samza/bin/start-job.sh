DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
ROOT_DIR=${DIR}/..

export SAMZA_CONTAINER_NAME=$1

#rhoover - their run-job.sh script requires cwd be ${ROOT_DIR}/deploy/samza
(cd ${ROOT_DIR}/deploy/samza && exec ./bin/run-job.sh --config-factory=org.apache.samza.config.factories.PropertiesConfigFactory --config-path=file://${ROOT_DIR}//deploy/samza/config/$1.properties)
