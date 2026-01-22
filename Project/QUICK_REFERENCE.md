# CoffeeShop v2 Quick Ref ⚡

### 🎹 Shortcuts & Commands

- **Run App**: `.\run.ps1`
- **Package Release**: `.\package.ps1`
- **Source Root**: `src/com/coffee`
- **Data Storage**: `data/*.json`

### 🏗️ Architecture Modules

| Module          | Responsibility                                   |
| :-------------- | :----------------------------------------------- |
| **Model**       | `Order`, `Drink`, `DrinkType`, `Event`           |
| **Controller**  | `OrderService` (Business Logic)                  |
| **Persistence** | `JsonStorage` (Data Backup)                      |
| **UI**          | `CoffeeDashboard`, `ModernButton`, `ModernPanel` |

### 📊 Business Analytics KPIs

- **Revenue**: Total earnings from completed orders.
- **Top Drink**: The product with the highest quantity sold.
- **Top Size**: The most frequently ordered cup size.
- **Avg Value**: (Total Revenue + Pending) / Total Orders.

### 📜 System Event Types

- `Order Created`: Logged when a new ticket is submitted.
- `Order Edited`: Logged when items are added/removed.
- `Order Completed`: Logged when moved to history.
- `Data Saved/Loaded`: Persistence actions.

---

_Professional Coffee Management System v2.0_
