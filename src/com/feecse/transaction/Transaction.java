package com.feecse.transaction;

public abstract class Transaction {
    protected String type;
    protected String description;
    protected double amount;

    @Override
    public String toString() {
        return "Type:'%-8s', Description:'%-5s', Amount:'%.2f'".formatted(type, description, amount);
    }

    public double getAmount() {
        return amount;
    }
}
