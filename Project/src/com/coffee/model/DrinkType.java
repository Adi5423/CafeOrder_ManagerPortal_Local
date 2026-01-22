package com.coffee.model;

/**
 * Enumeration of available drinks with their names and base prices.
 */
public enum DrinkType {
    AMERICANO("Americano", 4.00),
    APPLE_MILK("Apple Milk", 2.50),
    CAPPUCCINO("Cappuccino", 5.25),
    CARAMEL_LATTE("Caramel Latte", 4.75),
    COLD_BREW("Cold Brew", 5.75),
    CHOCOLATE_MILK("Chocolate Milk", 4.25),
    ICED_TEA("Iced Tea", 3.00),
    ICED_COFFEE("Iced Coffee", 2.50),
    LATTE("Latte", 4.50),
    MACCHIATO("Macchiatto", 4.75),
    MILK_TEA("Milk Tea", 3.25),
    MOCHA("Mocha", 5.00),
    SHAKES("Shakes", 3.50);

    private final String name;
    private final double basePrice;

    DrinkType(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public static DrinkType fromCode(int code) {
        if (code < 1 || code > values().length) {
            return AMERICANO;
        }
        return values()[code - 1];
    }
}
