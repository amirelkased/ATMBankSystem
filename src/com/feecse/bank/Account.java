package com.feecse.bank;

import com.feecse.transaction.DepositTransaction;
import com.feecse.transaction.Transaction;
import com.feecse.transaction.WithdrawalTransaction;

import java.util.ArrayDeque;

public class Account {

    private final String accountNumber;
    private double balance;
    private final ArrayDeque<Transaction> transactions = new ArrayDeque<>();

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(Transaction transactionDeposit) {
        if (transactionDeposit instanceof DepositTransaction) {
            this.balance += transactionDeposit.getAmount();
            handelTransactionsForNewItem();
            transactions.offer(transactionDeposit);
        }
    }

    public void withdraw(Transaction transactionWithdraw) {
        if (transactionWithdraw instanceof WithdrawalTransaction) {
            this.balance -= transactionWithdraw.getAmount();
            handelTransactionsForNewItem();
            transactions.offer(transactionWithdraw);
        }
    }

    private void handelTransactionsForNewItem() {
        if (transactions.size() > 9) {
            transactions.pollLast();
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double checkBalance() {
        return balance;
    }

    public ArrayDeque<Transaction> getTransactions() {
        return transactions.clone();
    }
}
