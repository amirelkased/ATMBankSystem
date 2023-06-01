package com.feecse.transaction;

public class DepositTransaction extends Transaction {

    public DepositTransaction(String description, double amount) {
        this.type = "deposit";
        this.description = description;
        this.amount = amount;
    }
}
