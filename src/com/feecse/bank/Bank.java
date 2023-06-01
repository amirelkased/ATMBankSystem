package com.feecse.bank;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    private final Map<Card, Customer> customers = new HashMap<>();

    public void addCustomer(Card card, Customer customer) {
        if (card != null && customer != null) {
            customers.putIfAbsent(card, customer);
        }
    }

    public Customer checkCard(String cardNumber, String pin) {
        for (Map.Entry<Card, Customer> entry : customers.entrySet()) {
            Card card = entry.getKey();
            if (card.getCardNumber().equals(cardNumber) && card.getPin().equals(pin)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Customer lookupByCardNumber(String cardNumber) {
        for (Map.Entry<Card, Customer> cardCustomerEntry : customers.entrySet()) {
            if (cardCustomerEntry.getKey().getCardNumber().equals(cardNumber)) {
                return cardCustomerEntry.getValue();
            }
        }
        return null;
    }

    public boolean changePin(Customer customer, String oldPin, String newPin) {
        for (Map.Entry<Card, Customer> customerEntry : customers.entrySet()) {
            if (customerEntry.getValue() == customer && customerEntry.getKey().getPin().equals(oldPin)) {
                customerEntry.getKey().setPin(newPin);
                return true;
            }
        }
        return false;
    }

    public Map<Card, Customer> getCustomers() {
        return customers;
    }
}
