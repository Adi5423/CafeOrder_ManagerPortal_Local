package com.coffee;

import com.coffee.ui.CoffeeDashboard;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(() -> {
            CoffeeDashboard dashboard = new CoffeeDashboard();
            dashboard.setVisible(true);
        });
    }
}
