#! /bin/sh
if [ "$#" -ne 1 ]; then
	echo "FAILED: Please, pass test name in parameters"
	exit -1
fi

cd ..

# run kafka in docker container
docker-compose up --force-recreate --abort-on-container-exit > /dev/null 2>&1 &

sleep 8

pid=$!
if ps | grep "$pid[^[]" >/dev/null then
	echo "Kafka is running..."
else
	if wait $pid then
		echo "Kafka is running..."
	else
		echo "Kafka failed on start!"
		exit 1
    fi
fi

python3 tests/producer.py a kraken 5

# run spring-boot application
mvn spring-boot:run > /dev/null 2>&1 &
pid=$!

sleep 10

if ps | grep "$pid[^[]" >/dev/null then
	echo "Spring-boot application is running..."
else
	if wait $pid then
		echo "Spring-boot application is running..."
	else
		echo "Spring-boot application failed on start!"
		exit 1
	fi
fi

cd tests

# run test
bash $1

# delete app process and stop kafka in docker container
docker stop $(docker ps -q --filter ancestor=spotify/kafka) >/dev/null
kill -9 $pid
