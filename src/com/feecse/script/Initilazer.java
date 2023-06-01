package com.feecse.script;

import com.feecse.bank.Bank;
import com.feecse.bank.Card;
import com.feecse.bank.Customer;

import java.util.Map;
import java.util.Random;

public class Initilazer {
    public static void startSystemData(Bank bank) {

        Customer[] initialCustomers = generateRandomCustomers(10);
        for (Customer customer : initialCustomers) {
            Card newCard = new Card();
            bank.addCustomer(newCard, customer);
        }
        // Print Data
        printAllCustomerDetails(bank);
    }

    public static Customer[] generateRandomCustomers(int count) {
        Random random = new Random();
        String[] names = {"Amir", "Mohamed", "Ahmed", "Michael", "Yasin"};
        String[] addresses = {"Menouf", "Cairo", "Alexandria", "Giza", "Luxor"};
        Customer[] customers = new Customer[count];

        for (int i = 0; i < count; i++) {
            String name = names[random.nextInt(names.length)];
            name = name.concat(" ").concat(names[random.nextInt(names.length)]);
            String address = addresses[random.nextInt(addresses.length)];
            String customerID = generateRandomID();
            double balance = random.nextDouble(1000);
            balance = Math.round(balance * 100) / 100.0;
            Customer customer = new Customer(name, address, customerID, balance);
            customers[i] = customer;
        }

        return customers;
    }

    public static String generateRandomID() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

    private static void printAllCustomerDetails(Bank bank) {
        for (Map.Entry<Card, Customer> cardCustomerEntry : bank.getCustomers().entrySet()) {
            System.out.printf("Name: %-15s, Card Number: %s, Pin: %-5s\n", cardCustomerEntry.getValue().getName()
                    , cardCustomerEntry.getKey().getCardNumber(), cardCustomerEntry.getKey().getPin());
        }
    }
}
