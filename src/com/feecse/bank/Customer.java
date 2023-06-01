package com.feecse.bank;

import java.util.Random;

public class Customer {

    private final String name;
    private final String address;
    private final String customerID;
    private final Account customerAccount;

    public Customer(String name, String address, String customerID, double initialbalance) {
        this.name = name;
        this.address = address;
        this.customerID = customerID;
        this.customerAccount = createAccount(initialbalance);
    }

    public Account createAccount(double initialBalance) {
        return new Account(accountNumberGen(), initialBalance);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCustomerID() {
        return customerID;
    }

    public Account getCustomerAccount() {
        return customerAccount;
    }

    private String accountNumberGen() {
        return String.valueOf(new Random().nextInt(10000));
    }
}
