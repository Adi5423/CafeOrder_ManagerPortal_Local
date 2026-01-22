package com.coffee.model;

import org.json.JSONObject;

/**
 * Represents a single drink in an order.
 */
public class Drink {
    private final DrinkType type;
    private final int size;
    private final double price;

    public Drink(DrinkType type, int size) {
        this.type = type;
        this.size = size;
        this.price = calculatePrice();
    }

    public Drink(int code, int size) {
        this(DrinkType.fromCode(code), size);
    }

    private double calculatePrice() {
        double base = type.getBasePrice();
        switch (size) {
            case 2:
                return base + 1.0;
            case 3:
                return base + 2.0;
            default:
                return base;
        }
    }

    public DrinkType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getSizeName() {
        switch (size) {
            case 1:
                return "Small";
            case 2:
                return "Medium";
            case 3:
                return "Large";
            default:
                return "Unknown";
        }
    }

    @Override
    public String toString() {
        return type.getName() + " (" + getSizeName() + ") - $" + String.format("%.2f", price);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("drinkCode", type.ordinal() + 1);
        json.put("size", size);
        json.put("price", price);
        return json;
    }
}
