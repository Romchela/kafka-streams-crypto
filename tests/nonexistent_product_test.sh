#! /bin/sh

echo "Test nonexistent_product_test started!"
status=$(curl --write-out %{http_code} --silent --output /dev/null localhost:8080/api/v1/unknown/price)
echo "Status: "$status
if [[ "$status" -eq "404" ]] ; then
	echo "OK!"
else
	echo "FAILED! Status must be 404."
fi

python3 producer.py btc kraken 6400

echo "Make query and try again for another product"
status=$(curl --write-out %{http_code} --silent --output /dev/null localhost:8080/api/v1/unknown/price)
echo "Status: "$status
if [[ "$status" -eq "404" ]] ; then
	echo "OK!"
else
	echo "FAILED! Status must be 404."
fi

