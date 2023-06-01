package com.feecse.transaction;

public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction(String description, double amount) {
        this.type = "Withdraw";
        this.description = description;
        this.amount = amount;
    }

}
