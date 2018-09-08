#! /bin/sh

echo "Test simple_test started!"

python3 producer.py btc kraken 6400
python3 producer.py btc binance 7000

URL="http://localhost:8080/api/v1/btc/price"

HTTP_RESPONSE=$(curl --silent --write-out "HTTPSTATUS:%{http_code}" -X GET $URL)

HTTP_BODY=$(echo $HTTP_RESPONSE | sed -e 's/HTTPSTATUS\:.*//g')

HTTP_STATUS=$(echo $HTTP_RESPONSE | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')

echo "$HTTP_BODY"

if [ ! $HTTP_STATUS -eq 200  ]; then
  echo "Error [HTTP status: $HTTP_STATUS]"
  exit 1
fi

if [[ $HTTP_BODY == 6618.2 ]] ; then
	echo "OK!"
else
	echo "FAILED!"
fi
