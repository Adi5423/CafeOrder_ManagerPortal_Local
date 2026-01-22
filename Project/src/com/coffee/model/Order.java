package com.coffee.model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer's order.
 */
public class Order {
    private final String orderID;
    private String customerName;
    private final List<Drink> drinks;
    private boolean isDone;
    private String address;

    public Order(String customerName) {
        this.orderID = String.valueOf(System.currentTimeMillis()).substring(7);
        this.customerName = customerName;
        this.drinks = new ArrayList<>();
        this.isDone = false;
        this.address = "From Counter";
    }

    public Order(String id, String customerName) {
        this.orderID = id;
        this.customerName = customerName;
        this.drinks = new ArrayList<>();
        this.isDone = false;
        this.address = "From Counter";
    }

    public void addDrink(Drink drink) {
        drinks.add(drink);
    }

    public void removeDrink(int index) {
        if (index >= 0 && index < drinks.size())
            drinks.remove(index);
    }

    public double getTotalPrice() {
        return drinks.stream().mapToDouble(Drink::getPrice).sum();
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "#" + orderID + " - " + customerName;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("orderID", orderID);
        json.put("customerName", customerName);
        json.put("isDone", isDone);
        json.put("customerAddress", address);
        JSONArray drinksJson = new JSONArray();
        for (Drink d : drinks)
            drinksJson.put(d.toJson());
        json.put("drinkList", drinksJson);
        json.put("totalPrice", getTotalPrice());
        return json;
    }
}
