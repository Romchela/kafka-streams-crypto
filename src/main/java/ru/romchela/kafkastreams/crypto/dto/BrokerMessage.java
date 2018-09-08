package ru.romchela.kafkastreams.crypto.dto;

/**
 * Message which comes to kafka topic
 */
public class BrokerMessage {

    private String product;

    private String source;

    private Long price;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
