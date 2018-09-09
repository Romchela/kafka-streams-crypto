#! /bin/sh

URL="http://localhost:8080/api/v1/"$1"/price"
HTTP_RESPONSE=$(curl --silent --write-out "HTTPSTATUS:%{http_code}" -X GET $URL)
HTTP_BODY=$(echo $HTTP_RESPONSE | sed -e 's/HTTPSTATUS\:.*//g')
HTTP_STATUS=$(echo $HTTP_RESPONSE | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')
if [ ! $HTTP_STATUS -eq 200  ]; then
	echo "Error [HTTP status: $HTTP_STATUS]"
	exit 1
fi
if [[ $HTTP_BODY == $2 ]] ; then
	echo "OK for "$1"!"
else
	echo "FAILED for "$1"!"
	echo "Expected: "$2
	echo "Answer: "$HTTP_BODY
fi
