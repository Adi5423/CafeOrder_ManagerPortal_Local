package com.coffee.controller;

import com.coffee.model.Drink;
import com.coffee.model.Order;
import com.coffee.model.Event;
import com.coffee.model.EventLog;
import com.coffee.persistence.JsonStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing business logic of orders.
 */
public class OrderService {
    private List<Order> waitingOrders;
    private List<Order> completedOrders;
    private final JsonStorage storage;

    public OrderService() {
        this.waitingOrders = new ArrayList<>();
        this.completedOrders = new ArrayList<>();
        this.storage = new JsonStorage();
    }

    public void createOrder(String customerName, List<Drink> drinks) {
        Order order = new Order(customerName);
        drinks.forEach(order::addDrink);
        waitingOrders.add(order);
        EventLog.getInstance().logEvent(new Event("Order #" + order.getOrderID() + " created for " + customerName));
    }

    public void markAsDone(Order order) {
        if (waitingOrders.remove(order)) {
            order.setDone(true);
            completedOrders.add(order);
            EventLog.getInstance().logEvent(new Event("Order #" + order.getOrderID() + " marked as completed"));
        }
    }

    public void removeOrder(Order order) {
        if (waitingOrders.remove(order) || completedOrders.remove(order)) {
            EventLog.getInstance().logEvent(new Event("Order #" + order.getOrderID() + " removed from system"));
        }
    }

    public void save(String date) throws Exception {
        storage.save(date, waitingOrders, completedOrders);
        EventLog.getInstance().logEvent(new Event("Orders saved to file: " + date + ".json"));
    }

    public void load(String date) throws Exception {
        storage.load(date);
        this.waitingOrders = storage.getWaiting();
        this.completedOrders = storage.getCompleted();
        EventLog.getInstance().logEvent(new Event("Orders loaded from file: " + date + ".json"));
    }

    public List<Order> getWaitingOrders() {
        return waitingOrders;
    }

    public List<Order> getCompletedOrders() {
        return completedOrders;
    }

    public double getTotalReceivable() {
        return waitingOrders.stream().mapToDouble(Order::getTotalPrice).sum();
    }

    public double getTotalReceived() {
        return completedOrders.stream().mapToDouble(Order::getTotalPrice).sum();
    }

    public double getAverageOrderValue() {
        int totalCount = waitingOrders.size() + completedOrders.size();
        if (totalCount == 0)
            return 0.0;
        return (getTotalReceivable() + getTotalReceived()) / totalCount;
    }

    public int getTotalDrinksSold() {
        int count = 0;
        for (Order o : waitingOrders)
            count += o.getDrinks().size();
        for (Order o : completedOrders)
            count += o.getDrinks().size();
        return count;
    }

    public String getMostPopularDrink() {
        List<Drink> allDrinks = new ArrayList<>();
        waitingOrders.forEach(o -> allDrinks.addAll(o.getDrinks()));
        completedOrders.forEach(o -> allDrinks.addAll(o.getDrinks()));
        return allDrinks.stream()
                .collect(Collectors.groupingBy(d -> d.getType().getName(), Collectors.counting()))
                .entrySet().stream()
                .max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .map(e -> e.getKey()).orElse("N/A");
    }

    public String getMostPopularSize() {
        List<Drink> allDrinks = new ArrayList<>();
        waitingOrders.forEach(o -> allDrinks.addAll(o.getDrinks()));
        completedOrders.forEach(o -> allDrinks.addAll(o.getDrinks()));
        return allDrinks.stream()
                .collect(Collectors.groupingBy(Drink::getSizeName, Collectors.counting()))
                .entrySet().stream()
                .max((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .map(e -> e.getKey()).orElse("N/A");
    }
}
