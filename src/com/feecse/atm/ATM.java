package com.feecse.atm;

import com.feecse.bank.Bank;
import com.feecse.script.Initilazer;

import java.util.Scanner;

public class ATM {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Operation operation = new Operation();
    public static final Bank bank = new Bank();

    public static void main(String[] args) {

        Initilazer.startSystemData(bank);

        while (true) {
            boolean needLogoutSystem = false;
            while (true) {
                if (operation.login(bank)) {
                    break;
                } else {
                    operation.logout();
                }
            }
            while (true) {
                try {
                    System.out.println("Select Operation Number:");
                    manu();
                    int op = scanner.nextInt();
                    switch (op) {
                        case 0 -> needLogoutSystem = true;
                        case 1 -> operation.checkBalance();
                        case 2 -> {
                            for (int i = 0; i < 3; i++) {
                                if (operation.withdraw()) {
                                    break;
                                } else if (i == 2) {
                                    needLogoutSystem = true;
                                }
                            }
                        }
                        case 3 -> {
                            for (int i = 0; i < 3; i++) {
                                if (operation.deposit()) {
                                    break;
                                } else if (i == 2) {
                                    needLogoutSystem = true;
                                }
                            }
                        }
                        case 4 -> {
                            for (int i = 0; i < 3; i++) {
                                if (operation.transferMoney(bank)) {
                                    break;
                                } else if (i == 2) {
                                    needLogoutSystem = true;
                                }
                            }
                        }
                        case 5 -> operation.printReceipt();
                        case 6 -> {
                            operation.resetPinCode(bank);
                            needLogoutSystem = true;
                        }
                        default -> System.out.println("Enter number from the list only!");
                    }
                } catch (Exception e) {
                    System.out.println("Enter valid number only!");
                }

                if (needLogoutSystem) {
                    System.out.println("Do you need to print receipt? (y / n)");
                    if (scanner.next().charAt(0) == 'y') {
                        operation.printReceipt();
                    }
                    operation.logout();
                    break;
                }
            }
        }
    }

    private static void manu() {
        System.out.println("""
                1- Check Balance
                2- Withdraw
                3- Deposit
                4- Transfer Money
                5- Print Receipt
                6- Reset Pincode
                0- Logout""");
    }
}
