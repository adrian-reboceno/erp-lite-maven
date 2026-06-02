package com.ikigaisoftware.erp_lite.domain.shared;

import java.math.BigDecimal;
import java.util.Currency;
import java.math.RoundingMode;

public record Money(BigDecimal amount, Currency currency) {

    public Money {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative, got: " + amount);
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }

        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    public static Money of(double amount, Currency currency) {
        return new Money(BigDecimal.valueOf(amount), currency);
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot add money with different currencies: " + this.currency + " and " + other.currency);
        }
        return new Money(amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot subtract money with different currencies: " + this.currency + " and " + other.currency);
        }
        BigDecimal result = amount.subtract(other.amount);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Subtraction would result in negative amount");
        }
        return new Money(result, this.currency);
    }

    public Money multiply(int multiplier) {
        if (multiplier < 0) throw new IllegalArgumentException("Multiplier cannot be negative");
        return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier)), this.currency);
    }

    public Money multiply(Quantity quantity) {
        return this.multiply(quantity.value());
    }
    private void requireSameCurrency(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                    "Cannot operate on Money with different currencies: " + currency + " vs " + other.currency);
        }
    }

    @Override
    public String toString() {
        return amount + " " + currency.getCurrencyCode();
    }

}
