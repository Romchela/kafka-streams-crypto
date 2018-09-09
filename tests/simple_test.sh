#! /bin/sh

echo "Test simple_test started!"

python3 producer.py btc kraken 6400
python3 producer.py btc binance 7000

bash check_product.sh btc 6733.33
