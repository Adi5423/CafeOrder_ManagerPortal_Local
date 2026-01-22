# CoffeeShop Order System - Development Guide

## 🚀 Quick Start

### Compile the Project

```powershell
javac -d out -cp "lib/*;src" -encoding UTF-8 src/main/model/*.java src/main/persistence/*.java src/main/ui/*.java
```

### Run the Application

```powershell
java -cp "out/production/Project-Starter;lib/*" ui.Main
```

---

## 📁 Project Structure

```
Project/
├── src/
│   ├── main/
│   │   ├── model/          # Business logic classes
│   │   │   ├── Drink.java
│   │   │   ├── Order.java
│   │   │   ├── Product.java
│   │   │   ├── Event.java
│   │   │   └── EventLog.java
│   │   ├── persistence/    # Data saving/loading
│   │   │   ├── OrderReader.java
│   │   │   ├── OrderReaderGUI.java
│   │   │   ├── OrderWriter.java
│   │   │   └── OrderWriterGUI.java
│   │   └── ui/             # User interface
│   │       ├── Main.java
│   │       ├── GUI.java
│   │       ├── TakeOrder.java
│   │       ├── TakeOrderGUI.java
│   │       └── coffee.png
│   └── test/               # Test files
├── lib/                    # External libraries (.jar files)
├── data/                   # Saved order files
├── out/                    # Compiled .class files
└── README.md
```

---

## 🛠️ How to Make Changes

### 1. **Modify Existing Code**

#### Example: Change a drink price

1. Open `src/main/model/Drink.java` or `Product.java`
2. Find the price values and modify them
3. Save the file
4. Recompile and run:
   ```powershell
   javac -d out -cp "lib/*;src" -encoding UTF-8 src/main/model/*.java src/main/persistence/*.java src/main/ui/*.java
   java -cp "out/production/Project-Starter;lib/*" ui.Main
   ```

#### Example: Add a new button to the GUI

1. Open `src/main/ui/GUI.java`
2. Add a new button in the `setLeft()` or `setRight()` method:
   ```java
   newButton = new JButton("New Feature");
   ```
3. Set its position in `setBoundOfLeft()`:
   ```java
   newButton.setBounds(x, y, width, height);
   ```
4. Add it to the frame in `addToFrame()`:
   ```java
   this.add(newButton);
   ```
5. Add a listener in `setListener()`:
   ```java
   newButton.addActionListener(this);
   ```
6. Handle the action in `actionPerformed()`:
   ```java
   else if (e.getSource() == newButton) {
       newButtonAction();
   }
   ```
7. Recompile and run

### 2. **Add a New Class**

1. Create a new `.java` file in the appropriate package:
   - Model classes → `src/main/model/`
   - UI classes → `src/main/ui/`
   - Persistence → `src/main/persistence/`

2. Example: Create `src/main/model/Customer.java`:

   ```java
   package model;

   public class Customer {
       private String name;
       private String id;

       public Customer(String name, String id) {
           this.name = name;
           this.id = id;
       }

       // Add getters and setters
   }
   ```

3. Recompile using the same command

### 3. **Add New Drink Types**

1. Open `src/main/model/Product.java` or `Drink.java`
2. Add the new drink to the enum or list
3. Update the GUI to include the new radio button
4. Recompile and run

---

## 🔄 Common Development Workflow

### One-Command Compile & Run

Create a PowerShell script `run.ps1`:

```powershell
# Compile
javac -d out -cp "lib/*;src" -encoding UTF-8 src/main/model/*.java src/main/persistence/*.java src/main/ui/*.java

# Check if compilation succeeded
if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful! Starting application..." -ForegroundColor Green
    # Run
    java -cp "out/production/Project-Starter;lib/*" ui.Main
} else {
    Write-Host "Compilation failed!" -ForegroundColor Red
}
```

Then just run:

```powershell
.\run.ps1
```

---

## 🧪 Testing Your Changes

### Running Unit Tests

```powershell
# Compile tests
javac -d out -cp "lib/*;out;src" -encoding UTF-8 src/test/**/*.java

# Run tests (if using JUnit)
java -cp "out;lib/*" org.junit.runner.JUnitCore test.TestClassName
```

---

## 📝 Using the Application

1. **Add an Order:**
   - Enter customer name
   - Select drinks using radio buttons
   - Select size (Small/Medium/Large)
   - Click "Enter" to add each drink
   - Click "Add Order" to save

2. **Save Orders:**
   - Enter date in format: `yyyymmdd` (e.g., `20260122`)
   - Click "Save"

3. **Load Orders:**
   - Enter date in format: `yyyymmdd`
   - Click "Read"

4. **Modify an Order:**
   - Enter customer code
   - Click "Select"
   - Add more drinks
   - Click "Modify Order" to confirm

5. **Complete an Order:**
   - Enter customer code
   - Click "Done Order"

6. **Add Delivery Address:**
   - Only available when order total ≥ $20
   - Click on order in waiting list
   - Enter address
   - Click "Save address"

---

## 🐛 Troubleshooting

### Issue: "Cannot find symbol" error

- **Solution:** Make sure all imports are correct and all referenced classes exist

### Issue: Application doesn't start

- **Solution:** Check that the classpath includes `out/production/Project-Starter`

### Issue: Image not loading

- **Solution:** Ensure `coffee.png` is in `src/main/ui/` and gets copied to `out/production/Project-Starter/ui/`

### Issue: Changes not reflected

- **Solution:** Always recompile after making changes

---

## 💡 Tips for Further Development

1. **Use an IDE:** Consider using IntelliJ IDEA or Eclipse for easier development
2. **Version Control:** Use Git to track your changes
3. **Code Organization:** Keep related functionality together
4. **Comments:** Add comments to explain complex logic
5. **Testing:** Write tests for new features
6. **Refactoring:** As mentioned in Phase 4 Task 3, consider creating abstract classes for common functionality

---

## 📚 Key Files to Understand

- **`Main.java`**: Entry point - creates and shows GUI
- **`GUI.java`**: Main user interface with all buttons and displays
- **`TakeOrderGUI.java`**: Order management logic
- **`Order.java`**: Order data model
- **`Drink.java`**: Drink data model
- **`OrderWriter.java/OrderReader.java`**: Persistence layer for saving/loading

---

## 🎯 Next Steps

1. Run the application and explore its features
2. Try making small changes (like button text or colors)
3. Add a new feature (like customer phone number)
4. Consider implementing the refactoring mentioned in Phase 4 Task 3

Good luck with your development! 🚀
