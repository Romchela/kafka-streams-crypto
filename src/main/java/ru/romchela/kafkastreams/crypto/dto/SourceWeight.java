package ru.romchela.kafkastreams.crypto.dto;

/**
 * This model is needed to read name and weight of sources from configuration
 */
public class SourceWeight {

    private String name;
    private Double weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
