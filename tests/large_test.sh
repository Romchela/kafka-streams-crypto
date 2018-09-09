#! /bin/sh

echo "Test large_test started!"

python3 producer.py BTC Binance 6200.67
python3 producer.py BTC EXMO 5870.98
python3 producer.py BTC LiveCoin 5000
python3 producer.py BTC YoBit 5890.9
python3 producer.py BTC HitBTC 6000
python3 producer.py BTC Poloniex 4500.6
python3 producer.py ETH Binance 140.77
python3 producer.py ETH EXMO 150
python3 producer.py ETH LiveCoin 200.01
python3 producer.py ETH YoBit 301.07
python3 producer.py ETH HitBTC 100
python3 producer.py ETH Poloniex 155.6
python3 producer.py XRP Binance 0.28
python3 producer.py XRP EXMO 0.2
python3 producer.py XRP LiveCoin 0.34
python3 producer.py XRP YoBit 0.9
python3 producer.py XRP HitBTC 1
python3 producer.py XRP Poloniex 0.45
python3 producer.py BCH Binance 400
python3 producer.py BCH EXMO 500
python3 producer.py BCH LiveCoin 546.78
python3 producer.py BCH YoBit 510.9
python3 producer.py BCH HitBTC 499.9
python3 producer.py BCH Poloniex 678
python3 producer.py EOS Binance 4.56
python3 producer.py EOS EXMO 5
python3 producer.py EOS LiveCoin 6
python3 producer.py EOS YoBit 7.65
python3 producer.py EOS HitBTC 7.04
python3 producer.py EOS Poloniex 7

bash check_product.sh btc 5723.814
bash check_product.sh eth 178.084
bash check_product.sh xrp 0.582
bash check_product.sh bch 508.954
bash check_product.sh eos 6.271
