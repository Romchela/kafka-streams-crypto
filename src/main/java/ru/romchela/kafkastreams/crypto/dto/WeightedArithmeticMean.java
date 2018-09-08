package ru.romchela.kafkastreams.crypto.dto;

import java.math.BigDecimal;

/**
 * Model for recalculating weighted arithmetic mean of each product
 */
public class WeightedArithmeticMean {

    private BigDecimal numerator;

    private BigDecimal denominator;

    public WeightedArithmeticMean() {
        numerator = BigDecimal.ZERO;
        denominator = BigDecimal.ZERO;
    }

    public BigDecimal getNumerator() {
        return numerator;
    }

    public void setNumerator(BigDecimal numerator) {
        this.numerator = numerator;
    }

    public BigDecimal getDenominator() {
        return denominator;
    }

    public void setDenominator(BigDecimal denominator) {
        this.denominator = denominator;
    }
}
