package com.feecse.bank;

import java.util.Random;

public class Card {

    private final String cardNumber;
    private String pin;

    public Card() {
        cardNumber = createCard();
        pin = pinGenerator().substring(0, 4);
    }

    public static String createCard() {
        StringBuilder card;
        card = new StringBuilder("400000");
        while (card.length() != 16) {
            card.append(randNum());
        }
        return card.toString();
    }

    public static String pinGenerator() {
        return String.valueOf((int) (Math.random() * 10000000));
    }

    public void setPin(String newPin) {
        if (newPin.length() == 4) {
            this.pin = newPin;
        }
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    private static int randNum() {
        return new Random().nextInt(10);
    }
}
