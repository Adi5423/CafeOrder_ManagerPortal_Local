# Developer Guide: CoffeeShop Professional v2 🛠️

This guide provides a technical deep dive into the architecture and extension patterns of the system.

## 🏗️ Architecture Overview

The application follows a strict **Controller-Service-Model** pattern to ensure separation of concerns and maintainability.

### 📦 Package Structure

- `com.coffee.model`: Pure data carriers and domain entities.
- `com.coffee.controller`: The engine of the app, containing business logic and service orchestration.
- `com.coffee.persistence`: Handles JSON serialization and file I/O.
- `com.coffee.ui`: Swing implementation, custom components, and view management.

## 🧱 Core Modules

### 1. Model Layer (`com.coffee.model`)

- **Order**: Manages a collection of Drinks and calculates totals. Serializes to JSON.
- **Drink**: Represents a beverage unit with a `DrinkType` and integer size.
- **DrinkType**: Enum defining base prices and display names.
- **EventLog**: Singleton that captures system-wide occurrences for auditing.

### 2. Service Layer (`com.coffee.controller`)

- **OrderService**: The central hub. It maintains the live state of `waitingOrders` and `completedOrders`. It provides the UI with filtered data and high-level analytics (KPIs).

### 3. UI System (`com.coffee.ui`)

- **CoffeeDashboard**: The main container. Uses a `CardLayout`-like manual switching for views.
- **Modern Components**: Reusable UI elements (`ModernButton`, `ModernPanel`) that override `paintComponent` to provide rounded corners and anti-aliasing.

## ➕ Adding a New Feature

To add a new drink type:

1. Update `DrinkType.java` with the new enum constant and price.
2. The UI will automatically populate the new type in the order form.

To add a new analytics card:

1. Add a calculation method in `OrderService.java`.
2. Update the `showOverview()` method in `CoffeeDashboard.java` to include a new `createStatCard` call.

## 🧪 Testing

The system is designed for modular testing. The `OrderService` can be instantiated without a GUI for headless logic verification.
