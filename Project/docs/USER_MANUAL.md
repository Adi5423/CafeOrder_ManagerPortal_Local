# User Manual: Manager Portal ☕

Welcome to your professional CoffeeShop Management Portal. This guide explains how to effectively operate the terminal and dashboard.

## 🏠 Dashboard Overview

The **Overview** tab is your Command Center.

- **KPI Cards**: Monitor live revenue, average order value, and product popularity.
- **Live Clock**: Always keep track of time in the sidebar.
- **Dynamic Greetings**: The system adjusts to your working shift (Morning/Afternoon/Evening).

## 📝 Managing Orders

- **Creating Orders**: Click the **" + New Order"** button in the sidebar. Enter the customer name and select drinks from the dropdowns. Sizes are listed as **Small, Medium, and Large**.
- **Active Orders**: In this view, you can track pending tickets. Use the **"Edit"** button to adjust items or **"Mark Done"** to move a ticket to history.
- **History (Completed)**: Look back at past sales. Use **"Read Details"** to see an itemized breakdown of any historical order.

## 📜 Audit & Security

The **System Logs** section provides a real-time audit trail.

- Every order created, edited, or completed is logged with a timestamp.
- Data saves and loads are also captured here.
- Managers can clear the log periodically to refresh the trail.

## 🔄 Data Synchronization

- **Saving**: Enter a date (format: `YYYYMMDD`) and click **"Save Current"**. This creates a JSON backup in the `data/` folder.
- **Loading**: Enter the date of a previous backup and click **"Load Data"**. This will restore that day's order state.

## 🚀 Exporting for Windows

If you need to move the app to another machine:

1. Run `.\package.ps1`.
2. Find the generated `release/` folder.
3. Share the entire folder. The user only needs to run `Launch_CoffeeShop.bat`.
