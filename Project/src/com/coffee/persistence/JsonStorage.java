package com.coffee.persistence;

import com.coffee.model.Drink;
import com.coffee.model.Order;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading orders to JSON files.
 */
public class JsonStorage {
    private List<Order> waiting;
    private List<Order> completed;

    public JsonStorage() {
        this.waiting = new ArrayList<>();
        this.completed = new ArrayList<>();
    }

    public void save(String date, List<Order> waiting, List<Order> completed) throws Exception {
        JSONObject root = new JSONObject();
        root.put("Date", date);
        JSONArray ordersArray = new JSONArray();
        for (Order o : waiting)
            ordersArray.put(o.toJson());
        for (Order o : completed)
            ordersArray.put(o.toJson());
        root.put("Orders", ordersArray);

        File dir = new File("data");
        if (!dir.exists())
            dir.mkdir();

        try (FileWriter writer = new FileWriter("data/" + date + ".json")) {
            writer.write(root.toString(4));
        }
    }

    public void load(String date) throws Exception {
        String path = "data/" + date + ".json";
        File file = new File(path);
        if (!file.exists())
            throw new Exception("Backup file not found for date: " + date);

        String content = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject root = new JSONObject(content);
        JSONArray ordersArray = root.getJSONArray("Orders");
        waiting.clear();
        completed.clear();

        for (int i = 0; i < ordersArray.length(); i++) {
            JSONObject obj = ordersArray.getJSONObject(i);
            Order order = new Order(obj.getString("orderID"), obj.getString("customerName"));
            order.setDone(obj.getBoolean("isDone"));
            order.setAddress(obj.optString("customerAddress", "From Counter"));
            JSONArray drinksArray = obj.getJSONArray("drinkList");
            for (int j = 0; j < drinksArray.length(); j++) {
                JSONObject dObj = drinksArray.getJSONObject(j);
                order.addDrink(new Drink(dObj.getInt("drinkCode"), dObj.getInt("size")));
            }
            if (order.isDone())
                completed.add(order);
            else
                waiting.add(order);
        }
    }

    public List<Order> getWaiting() {
        return waiting;
    }

    public List<Order> getCompleted() {
        return completed;
    }
}
