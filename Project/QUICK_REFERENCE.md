# 🚀 Quick Reference Card

## ⚡ Essential Commands

### Compile

```powershell
javac -d out -cp "lib/*;src" -encoding UTF-8 src/main/model/*.java src/main/persistence/*.java src/main/ui/*.java
```

### Run

```powershell
java -cp "out/production/Project-Starter;lib/*" ui.Main
```

### Quick Run (Use the script)

```powershell
.\run.ps1
```

---

## 📂 Common File Locations

| What to Change          | File Location                                                |
| ----------------------- | ------------------------------------------------------------ |
| Drink types & prices    | `src/main/model/Product.java` or `Drink.java`                |
| GUI layout & buttons    | `src/main/ui/GUI.java`                                       |
| Order logic             | `src/main/model/Order.java`                                  |
| Save/Load functionality | `src/main/persistence/OrderWriter.java` & `OrderReader.java` |
| App entry point         | `src/main/ui/Main.java`                                      |

---

## 🔧 Common Modifications

### Change Button Text

**File:** `src/main/ui/GUI.java`

```java
addOrderButton = new JButton("Your New Text");
```

### Change Button Color

**File:** `src/main/ui/GUI.java` in `setButtons()` method

```java
button.setBackground(Color.BLUE);  // Change color
button.setForeground(Color.WHITE); // Text color
```

### Add New Drink Type

1. Update `model/Product.java` or `Drink.java` with new drink
2. Add radio button in `GUI.java`:
   - Declare: `private JRadioButton newDrinkRadioButton;`
   - Initialize in `setRadioButton()`
   - Set position in `setBoundOfRadioButton()`
   - Add action handler

### Change Window Size

**File:** `src/main/ui/GUI.java`

```java
this.setSize(width, height);  // Add this to constructor
```

---

## 🐛 Common Issues & Fixes

| Problem              | Solution                                                |
| -------------------- | ------------------------------------------------------- |
| Changes don't appear | Recompile before running                                |
| "Cannot find symbol" | Check imports and class names                           |
| Image not loading    | Ensure coffee.png is in src/main/ui/                    |
| App won't start      | Check classpath includes out/production/Project-Starter |
| NullPointerException | Check all fields are initialized                        |

---

## 📝 Development Checklist

When adding a feature:

- [ ] Update Model classes (add fields, getters, setters)
- [ ] Update GUI (add UI components)
- [ ] Update logic (handle user actions)
- [ ] Update persistence (save/load new data)
- [ ] Compile
- [ ] Test thoroughly
- [ ] Save your work!

---

## 💡 Pro Tips

1. **After every change:** Compile → Run → Test
2. **Use descriptive names:** `customerPhoneTextField` not `textField3`
3. **Test with data:** Try edge cases (empty fields, very long text, etc.)
4. **Save often:** Use Git or backup your files regularly
5. **Read error messages:** They usually tell you exactly what's wrong

---

## 🎯 Key Concepts

### MVC Pattern in this Project

- **Model**: `src/main/model/` - Data and business logic
- **View**: `src/main/ui/GUI.java` - What user sees
- **Controller**: `src/main/ui/TakeOrderGUI.java` - Handles user actions

### Persistence

- **Writer**: Saves data to JSON files
- **Reader**: Loads data from JSON files
- **Location**: `data/` folder (created at runtime)

---

## 📚 Learning Resources

- **Java Swing Tutorial**: https://docs.oracle.com/javase/tutorial/uiswing/
- **JSON in Java**: https://www.json.org/json-en.html
- **Java Collections**: https://docs.oracle.com/javase/8/docs/technotes/guides/collections/

---

## 🎨 GUI Component Positions

Components use `setBounds(x, y, width, height)`:

- **x**: Distance from left edge (pixels)
- **y**: Distance from top edge (pixels)
- **width**: Component width (pixels)
- **height**: Component height (pixels)

Example:

```java
button.setBounds(10, 20, 100, 30);  // Left=10px, Top=20px, Width=100px, Height=30px
```

---

## ⌨️ Application Usage

### Basic Workflow:

1. **Load** existing orders (date: yyyymmdd) → Click "Read"
2. **Create** new order:
   - Enter customer name
   - Select drinks + size
   - Click "Enter" for each drink
   - Click "Add Order"
3. **Modify** order:
   - Enter customer code
   - Click "Select"
   - Add/change drinks
   - Click "Modify Order"
4. **Complete** order: Enter code → "Done Order"
5. **Save** all orders: Enter date → "Save"

---

**Remember:** Always test your changes! Good luck! 🍀
