# Dev Setup

## Build

	#Do this one time only
	./bin/grid install all
	
	mvn package && rm -rf deploy/samza && mkdir -p deploy/samza && tar -xvf ./target/join-samza-0.0.1-dist.tar.gz -C deploy/samza

## Deployment

	#Start grid
	rm -rf /tmp/kafka-logs/ && rm -rf /tmp/zookeeper/
	./bin/grid start all
	./bin/create-topics.sh
	./bin/load-topics.sh
                
	#Run the join job
	./bin/start-job.sh requests-join-user-json 

