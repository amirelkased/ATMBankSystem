package com.feecse.atm;

import com.feecse.bank.Account;
import com.feecse.bank.Bank;
import com.feecse.bank.Customer;
import com.feecse.transaction.DepositTransaction;
import com.feecse.transaction.Transaction;
import com.feecse.transaction.WithdrawalTransaction;

import java.util.Scanner;

public class Operation {
    private final Scanner scanner = new Scanner(System.in);
    private Account activeAccount;
    private Customer customer;

    public boolean login(Bank bank) {
        System.out.println("Enter Card Number:");
        String cardNumber = scanner.nextLine();
        if (cardNumber.length() != 16) {
            System.out.println("Invalid card number!");
            return false;
        }
        Customer customer = null;
        for (int i = 0; i < 3; i++) {
            System.out.println("Enter Pin:");
            String pin = scanner.nextLine();
            customer = bank.checkCard(cardNumber, pin);
            if (customer != null) {
                break;
            }
            System.out.println("Wrong PinCode...");
        }
        if (customer != null) {
            this.customer = customer;
            activeAccount = customer.getCustomerAccount();
            System.out.printf("Dear %s Login Successfully!\n", customer.getName());
            return true;
        }
        System.out.println("Ops! pin code is wrong!");
        return false;
    }

    public void checkBalance() {
        if (activeAccount != null) {
            System.out.println("Current Balance: " + activeAccount.checkBalance());
        }
    }

    public boolean withdraw() {
        try {
            // Check Amount
            System.out.println("Enter needed Amount:");
            double amount = scanner.nextDouble();
            double balance = activeAccount.checkBalance();
            if (amount <= 0 || balance < amount) {
                System.out.println("Ops! Please check you have sufficient funds..");
                return false;
            }
            scanner.nextLine();
            System.out.println("For what is this withdrawal?");
            String description = scanner.nextLine();

            // Confirm
            System.out.printf("Are you sure to withdraw %.2f: y / n ?\n"
                    , amount);
            if (scanner.nextLine().toLowerCase().charAt(0) == 'n') {
                System.out.println("Operation is canceled!");
                return true;
            }

            // Do operation
            Transaction withdrawal = new WithdrawalTransaction(description, amount);
            activeAccount.withdraw(withdrawal);
            System.out.println("Withdrawal successful!");
            checkBalance();
            return true;
        } catch (Exception e) {
            System.out.println("Please enter a valid value!");
            return false;
        }
    }


    public boolean deposit() {
        try {
            // Check Amount
            System.out.println("Enter your Amount:");
            double amount = scanner.nextDouble();
            if (amount <= 0) {
                System.out.println("Ops! Enter valid amount.");
                return false;
            }
            scanner.nextLine();
            System.out.println("For what is this deposit?");
            String description = scanner.nextLine();

            // Confirm
            System.out.printf("Are you sure to deposit %.2f: y / n ?\n"
                    , amount);
            if (scanner.next().toLowerCase().charAt(0) == 'n') {
                System.out.println("Operation is canceled!");
                return true;
            }

            // Do operation
            Transaction deposit = new DepositTransaction(description, amount);
            activeAccount.deposit(deposit);
            System.out.println("Deposit successful!");
            checkBalance();
            return true;
        } catch (Exception e) {
            System.out.println("Please enter valid value!");
            return false;
        }
    }


    public boolean transferMoney(Bank bank) {
        try {
            // Check Amount
            System.out.println("Enter Amount:");
            double amount = scanner.nextDouble();
            double balance = activeAccount.checkBalance();
            if (amount <= 0 || balance < amount) {
                System.out.println("Ops! Please check you have sufficient funds..");
                return false;
            }
            scanner.nextLine();

            // Take recipient details
            System.out.println("Enter another Card Number:");
            String recipientCardNumber = scanner.nextLine();
            if (recipientCardNumber.length() != 16) {
                System.out.println("Ops! need 16 number.");
                return false;
            }

            Customer recipient = bank.lookupByCardNumber(recipientCardNumber);
            if (recipient != null) {
                // Confirm
                System.out.printf("Are you sure to transfer %.2f to account of %s : y / n ?\n"
                        , amount, recipient.getName());
                if (scanner.next().toLowerCase().charAt(0) == 'n') {
                    System.out.println("Operation is canceled!");
                    return true;
                }
                // Withdraw from active account
                String description = "Transfer money to " + recipient.getName();
                Transaction withdrawal = new WithdrawalTransaction(description, amount);
                activeAccount.withdraw(withdrawal);

                // Deposit into recipient
                description = "Receive money from " + customer.getName();
                Transaction deposit = new DepositTransaction(description, amount);
                recipient.getCustomerAccount().deposit(deposit);

                System.out.printf("Successfully transferred %.2f from %s to %s\n",
                        amount, customer.getName(), recipient.getName());
                return true;

            } else {
                System.out.println("Transfer failed. Please check if the recipient exists.");
                return false;
            }
        } catch (Exception ignored) {
            return false;
        }
    }

    public void printReceipt() {
        System.out.println("Your last processes:");
        for (Transaction transaction : activeAccount.getTransactions()) {
            System.out.println(transaction);
        }
    }

    public void resetPinCode(Bank bank) {
        try {
            System.out.println("Enter your old pin:");
            String oldPin = scanner.nextLine();
            System.out.println("Enter new pin:");
            String newPin = scanner.nextLine();
            if (checkPin(oldPin) && checkPin(newPin) && bank.changePin(customer, oldPin, newPin)) {
                System.out.println("Reset Pin code successfully!");
            } else {
                System.out.println("Ops! make sure your old and new pin 4 number or match old pin.");
            }
        } catch (Exception e) {
            System.out.println("Ops! make sure your old and new pin 4 number or match old pin.");
        }
    }

    private boolean checkPin(String pin) {
        for (int i = 0; i < 4; ++i) {
            if (!(pin.charAt(i) >= '0' && pin.charAt(i) <= '9') || pin.length() != 4) {
                return false;
            }
        }
        return true;
    }

    public void logout() {
        System.out.println("Please receive your card!");
        activeAccount = null;
    }
}
