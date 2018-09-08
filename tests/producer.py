import json
import sys
import time
from kafka import KafkaProducer

if len(sys.argv) != 4:
	print('3 arguments required: product, source and price');
	exit(-1);

producer = KafkaProducer(bootstrap_servers='localhost:9092', value_serializer=lambda v: json.dumps(v).encode('utf-8'))

future = producer.send('topic', key=b'1111', value={'product': sys.argv[1], 'source': sys.argv[2], 'price': sys.argv[3]})
future.get(timeout=60)

time.sleep(2)
