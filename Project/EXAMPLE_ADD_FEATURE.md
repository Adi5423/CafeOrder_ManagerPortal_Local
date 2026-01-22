# Example: Adding a Customer Phone Number Feature

This guide shows you step-by-step how to add a new feature to track customer phone numbers.

## 📋 What We'll Do

1. Add phone number field to the Order class
2. Add a text field to the GUI
3. Update the persistence layer to save/load phone numbers

---

## Step 1: Modify the Order Class

**File:** `src/main/model/Order.java`

### Add Field

```java
private String customerPhone;
```

### Add to Constructor

```java
public Order(int orderID, String name, String phone) {
    this.orderID = orderID;
    this.customerName = name;
    this.customerPhone = phone;  // Add this line
    this.drinkList = new ArrayList<>();
    // ... rest of constructor
}
```

### Add Getter and Setter

```java
public String getCustomerPhone() {
    return customerPhone;
}

public void setCustomerPhone(String phone) {
    this.customerPhone = phone;
}
```

---

## Step 2: Update GUI

**File:** `src/main/ui/GUI.java`

### 2.1 Add Text Field Declaration

Find the field declarations at the top of the class and add:

```java
private JTextField customerPhoneTextField;
```

### 2.2 Initialize the Text Field

In the `setTextField()` method, add:

```java
customerPhoneTextField = new JTextField("Customer Phone");
```

### 2.3 Set Position

In the `setBoundOfLeft()` method, add:

```java
customerPhoneTextField.setBounds(150, 65, 200, 20);  // Position below customer name
```

### 2.4 Add to Frame

In the `addToFrame()` method, add:

```java
this.add(customerPhoneTextField);
```

### 2.5 Update Order Creation

In the `addOrderAction()` method, modify to pass phone number:

```java
public void addOrderAction() {
    if (drinks.size() >= 1) {
        String customerName = customerNameTextField.getText();
        String customerPhone = customerPhoneTextField.getText();

        if (!customerName.equals("Customer Name") && !customerName.equals("")) {
            // Pass phone number when creating order
            orders = main.createOrder(customerName, customerPhone, drinks);
            drinks.clear();
            customerNameTextField.setText("Customer Name");
            customerPhoneTextField.setText("Customer Phone");  // Reset field
            infoText.setText("Order is added to waitingList.");
            clearOrderInfo();
            updateBothList();
        } else {
            infoText.setText("Please enter customer Name.");
        }
    } else {
        infoText.setText("Please select some drinks first.");
    }
}
```

### 2.6 Display Phone in Order Info

In the `runOrderInfo()` method, update to show phone:

```java
toDisplay.append("<html>Customer Name: " + order.getCustomerName()
    + "<br/>Customer Phone: " + order.getCustomerPhone()  // Add this line
    + "<br/>Customer Code: " + order.getOrderID()
    + "<br/>Total Price: " + order.getTotalPrice()
    // ... rest of display
```

---

## Step 3: Update TakeOrderGUI

**File:** `src/main/ui/TakeOrderGUI.java`

Update the `createOrder()` method to accept phone parameter:

```java
public ArrayList<Order> createOrder(String customerName, String phoneNumber, ArrayList<Drink> drinkList) {
    Order order = new Order(trackID, customerName, phoneNumber);
    for (Drink drink : drinkList) {
        order.addDrinks(drink);
    }
    this.setOrderWaiting(order);
    return this.orderWaiting;
}
```

---

## Step 4: Update JSON Persistence

**File:** `src/main/persistence/OrderWriterGUI.java`

In the `orderToJson()` method, add:

```java
json.put("customerPhone", order.getCustomerPhone());
```

**File:** `src/main/persistence/OrderReaderGUI.java`

In the order parsing method, add:

```java
String phone = jsonObject.getString("customerPhone");
order.setCustomerPhone(phone);
```

---

## Step 5: Test Your Changes

1. **Compile:**

   ```powershell
   javac -d out -cp "lib/*;src" -encoding UTF-8 src/main/model/*.java src/main/persistence/*.java src/main/ui/*.java
   ```

2. **Run:**

   ```powershell
   java -cp "out/production/Project-Starter;lib/*" ui.Main
   ```

3. **Test:**
   - Enter customer name
   - Enter customer phone number
   - Add drinks and create order
   - Save the order
   - Load it back and verify phone number is saved

---

## 🎉 You've Successfully Added a Feature!

This same pattern can be used to add other features:

- Customer email
- Order notes
- Discount codes
- Loyalty points
- etc.

---

## 🔍 Debugging Tips

### If the phone number doesn't show:

1. Check that you added it to the display string in `runOrderInfo()`
2. Verify the getter method is correct

### If saving/loading fails:

1. Check that JSON keys match between writer and reader
2. Verify the field is being set in the Order constructor

### If compilation fails:

1. Check for typos in method names
2. Ensure all imports are present
3. Verify method signatures match when calling

---

## 📝 General Pattern for Adding Features

1. **Data Layer** (Model): Add field, getter, setter
2. **UI Layer** (GUI): Add input field, position it, add to frame
3. **Logic Layer** (TakeOrderGUI): Update methods to handle new field
4. **Persistence Layer** (Reader/Writer): Add to JSON save/load
5. **Test**: Compile, run, verify

Happy coding! 🚀
