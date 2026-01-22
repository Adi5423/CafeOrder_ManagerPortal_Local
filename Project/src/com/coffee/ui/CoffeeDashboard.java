package com.coffee.ui;

import com.coffee.controller.OrderService;
import com.coffee.model.Drink;
import com.coffee.model.DrinkType;
import com.coffee.model.Order;
import com.coffee.ui.component.ModernButton;
import com.coffee.ui.component.ModernPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CoffeeDashboard extends JFrame {
    private static final Color BG_DARK = new Color(15, 23, 42);
    private static final Color BG_LIGHT = new Color(248, 250, 252);
    private static final Color ACCENT = new Color(99, 102, 241);
    private static final Color TEXT_MAIN = new Color(30, 41, 59);
    private static final Color TEXT_SUB = new Color(100, 116, 139);
    private static final Color CARD_BG = Color.WHITE;

    private final OrderService service;
    private JPanel mainContent;
    private JLabel statTotalOrders, statRevenue, statPopular;
    private String currentActiveTab = "Overview";
    private final java.util.List<JButton> navButtons = new ArrayList<>();
    private JLabel clockLabel;

    public CoffeeDashboard() {
        super("Coffee Professional");
        service = new OrderService();
        setupFrame();
        initLayout();
        showOverview();
    }

    private void setupFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_LIGHT);
    }

    private void initLayout() {
        setLayout(new BorderLayout());
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBackground(BG_DARK);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(40, 20, 40, 20));

        JLabel logo = new JLabel("Coffee v2");
        logo.setFont(new Font("Inter", Font.BOLD, 24));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(60));

        addNavButton(sidebar, "Overview", e -> {
            currentActiveTab = "Overview";
            updateNavSelection();
            showOverview();
        });
        addNavButton(sidebar, "Active Orders", e -> {
            currentActiveTab = "Active Orders";
            updateNavSelection();
            showActiveOrders();
        });
        addNavButton(sidebar, "Completed", e -> {
            currentActiveTab = "Completed";
            updateNavSelection();
            showCompleted();
        });
        addNavButton(sidebar, "System Logs", e -> {
            currentActiveTab = "System Logs";
            updateNavSelection();
            showLogs();
        });
        addNavButton(sidebar, "Data Sync", e -> {
            currentActiveTab = "Data Sync";
            updateNavSelection();
            showSync();
        });

        sidebar.add(Box.createVerticalStrut(30));
        ModernButton btnNew = new ModernButton(" + New Order", ACCENT);
        btnNew.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnNew.setMaximumSize(new Dimension(200, 45));
        btnNew.addActionListener(e -> showNewOrderForm());
        sidebar.add(btnNew);

        sidebar.add(Box.createVerticalGlue());

        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Inter", Font.PLAIN, 12));
        clockLabel.setForeground(TEXT_SUB);
        clockLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(clockLabel);
        startClock();

        add(sidebar, BorderLayout.WEST);
        mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(BG_LIGHT);
        updateNavSelection();
        add(mainContent, BorderLayout.CENTER);
    }

    private String getGreeting() {
        int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        if (hour < 12)
            return "Good Morning";
        if (hour < 17)
            return "Good Afternoon";
        return "Good Evening";
    }

    private void startClock() {
        Timer timer = new Timer(true);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> clockLabel.setText(sdf.format(new Date())));
            }
        }, 0, 1000);
    }

    private void updateNavSelection() {
        for (JButton btn : navButtons) {
            if (btn.getText().equals(currentActiveTab)) {
                btn.setForeground(Color.WHITE);
                btn.setFont(new Font("Inter", Font.BOLD, 15));
            } else {
                btn.setForeground(new Color(148, 163, 184));
                btn.setFont(new Font("Inter", Font.PLAIN, 15));
            }
        }
    }

    private void addNavButton(JPanel parent, String text, java.awt.event.ActionListener l) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Inter", Font.PLAIN, 15));
        btn.setForeground(new Color(148, 163, 184));
        btn.setBackground(BG_DARK);
        btn.setBorder(new EmptyBorder(12, 10, 12, 10));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(l);
        navButtons.add(btn);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!btn.getText().equals(currentActiveTab))
                    btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!btn.getText().equals(currentActiveTab))
                    btn.setForeground(new Color(148, 163, 184));
            }
        });
        parent.add(btn);
        parent.add(Box.createVerticalStrut(10));
    }

    private void showOverview() {
        clearContent("Dashboard Overview");

        JPanel grid = new JPanel(new GridLayout(2, 3, 25, 25));
        grid.setBackground(BG_LIGHT);
        grid.setBorder(new EmptyBorder(0, 0, 30, 0));

        statTotalOrders = new JLabel("0");
        statRevenue = new JLabel("$0.00");
        statPopular = new JLabel("N/A");
        JLabel statAvgValue = new JLabel("$0.00");
        JLabel statDrinksSold = new JLabel("0");
        JLabel statPopSize = new JLabel("N/A");

        grid.add(createStatCard("Total Sales", statTotalOrders));
        grid.add(createStatCard("Total Revenue", statRevenue));
        grid.add(createStatCard("Avg Order Value", statAvgValue));
        grid.add(createStatCard("Drinks Sold", statDrinksSold));
        grid.add(createStatCard("Top Drink", statPopular));
        grid.add(createStatCard("Top Size", statPopSize));

        mainContent.add(grid, BorderLayout.NORTH);

        updateStats();
        // Update the extra stats
        statAvgValue.setText("$" + String.format("%.2f", service.getAverageOrderValue()));
        statDrinksSold.setText(String.valueOf(service.getTotalDrinksSold()));
        statPopSize.setText(service.getMostPopularSize());
    }

    private void showActiveOrders() {
        clearContent("Active Orders");
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(BG_LIGHT);
        for (Order o : service.getWaitingOrders()) {
            listPanel.add(createOrderRow(o));
            listPanel.add(Box.createVerticalStrut(12));
        }
        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.setBackground(BG_LIGHT);
        scroll.getViewport().setBackground(BG_LIGHT);
        mainContent.add(scroll, BorderLayout.CENTER);
    }

    private void showCompleted() {
        clearContent("Completed Orders");
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(BG_LIGHT);
        for (Order o : service.getCompletedOrders()) {
            listPanel.add(createOrderRow(o));
            listPanel.add(Box.createVerticalStrut(12));
        }
        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        mainContent.add(scroll, BorderLayout.CENTER);
    }

    private void showLogs() {
        clearContent("System Audit Logs");

        JPanel logPanel = new JPanel();
        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));
        logPanel.setBackground(BG_LIGHT);

        com.coffee.model.EventLog.getInstance().forEach(event -> {
            ModernPanel p = new ModernPanel(new BorderLayout());
            p.setBackground(CARD_BG);
            p.setBorder(new EmptyBorder(10, 20, 10, 20));
            p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            JLabel lblEvent = new JLabel(event.toString());
            lblEvent.setFont(new Font("Inter", Font.PLAIN, 13));
            lblEvent.setForeground(TEXT_MAIN);
            p.add(lblEvent, BorderLayout.CENTER);

            logPanel.add(p);
            logPanel.add(Box.createVerticalStrut(8));
        });

        JScrollPane scroll = new JScrollPane(logPanel);
        scroll.setBorder(null);
        scroll.setBackground(BG_LIGHT);
        scroll.getViewport().setBackground(BG_LIGHT);
        mainContent.add(scroll, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(BG_LIGHT);
        footer.add(new ModernButton("Clear Logs", new Color(225, 29, 72)) {
            {
                addActionListener(e -> {
                    com.coffee.model.EventLog.getInstance().clear();
                    showLogs();
                });
            }
        });
        mainContent.add(footer, BorderLayout.SOUTH);
    }

    private void showSync() {
        clearContent("Data Synchronization");
        ModernPanel p = new ModernPanel(new GridBagLayout());
        p.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JTextField txtDate = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        p.add(new JLabel("Enter Date (yyyymmdd):"), gbc);
        gbc.gridx = 1;
        p.add(txtDate, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JPanel btnP = new JPanel(new FlowLayout());
        btnP.setBackground(CARD_BG);
        btnP.add(new ModernButton("Save Current", ACCENT) {
            {
                addActionListener(e -> {
                    try {
                        service.save(txtDate.getText());
                        JOptionPane.showMessageDialog(null, "Saved!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error");
                    }
                });
            }
        });
        btnP.add(new ModernButton("Load Data", new Color(51, 65, 85)) {
            {
                addActionListener(e -> {
                    try {
                        if (txtDate.getText().isEmpty())
                            throw new Exception("Please enter a date");
                        service.load(txtDate.getText());
                        updateStats();
                        JOptionPane.showMessageDialog(null, "Data loaded successfully for " + txtDate.getText());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Load failed: " + ex.getMessage(), "Persistence Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
        });
        p.add(btnP, gbc);
        mainContent.add(p, BorderLayout.NORTH);
    }

    private void showNewOrderForm() {
        JDialog d = new JDialog(this, "Place Order", true);
        d.setSize(550, 600);
        d.setLocationRelativeTo(this);
        d.setLayout(new BorderLayout());
        ((JPanel) d.getContentPane()).setBorder(new EmptyBorder(30, 30, 30, 30));
        d.getContentPane().setBackground(CARD_BG);

        // Header
        JLabel header = new JLabel("New Order Entry");
        header.setFont(new Font("Inter", Font.BOLD, 22));
        header.setForeground(TEXT_MAIN);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));
        d.add(header, BorderLayout.NORTH);

        // Center Form
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(CARD_BG);

        JLabel nameLabel = new JLabel("Customer Name");
        nameLabel.setFont(new Font("Inter", Font.BOLD, 13));
        nameLabel.setForeground(TEXT_SUB);
        center.add(nameLabel);
        center.add(Box.createVerticalStrut(8));

        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(0, 45));
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        nameField.setFont(new Font("Inter", Font.PLAIN, 15));
        center.add(nameField);
        center.add(Box.createVerticalStrut(25));

        JLabel drinkLabel = new JLabel("Add Items");
        drinkLabel.setFont(new Font("Inter", Font.BOLD, 13));
        drinkLabel.setForeground(TEXT_SUB);
        center.add(drinkLabel);
        center.add(Box.createVerticalStrut(8));

        JPanel addRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        addRow.setBackground(CARD_BG);
        JComboBox<DrinkType> drinksCombo = new JComboBox<>(DrinkType.values());
        JComboBox<String> sizeCombo = new JComboBox<>(new String[] { "Small", "Medium", "Large" });
        drinksCombo.setPreferredSize(new Dimension(180, 40));
        sizeCombo.setPreferredSize(new Dimension(100, 40));

        ArrayList<Drink> currentDrinks = new ArrayList<>();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);
        list.setFont(new Font("Inter", Font.PLAIN, 14));
        list.setFixedCellHeight(30);

        ModernButton btnAdd = new ModernButton("Add", ACCENT) {
            {
                setPreferredSize(new Dimension(80, 40));
                addActionListener(e -> {
                    Drink dr = new Drink((DrinkType) drinksCombo.getSelectedItem(), sizeCombo.getSelectedIndex() + 1);
                    currentDrinks.add(dr);
                    listModel.addElement(dr.toString());
                });
            }
        };

        addRow.add(drinksCombo);
        addRow.add(sizeCombo);
        addRow.add(btnAdd);
        center.add(addRow);
        center.add(Box.createVerticalStrut(15));

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        scroll.setPreferredSize(new Dimension(0, 150));
        center.add(scroll);

        d.add(center, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 20));
        footer.setBackground(CARD_BG);
        footer.add(new ModernButton("Submit Order", ACCENT) {
            {
                setPreferredSize(new Dimension(160, 45));
                addActionListener(e -> {
                    if (!nameField.getText().isEmpty() && !currentDrinks.isEmpty()) {
                        service.createOrder(nameField.getText(), currentDrinks);
                        updateStats();
                        d.dispose();
                        showActiveOrders();
                    }
                });
            }
        });
        d.add(footer, BorderLayout.SOUTH);

        d.setVisible(true);
    }

    private void showEditOrder(Order o) {
        JDialog d = new JDialog(this, "Editing Order " + o.getOrderID(), true);
        d.setSize(600, 600);
        d.setLocationRelativeTo(this);
        d.setLayout(new BorderLayout(0, 20));
        ((JPanel) d.getContentPane()).setBorder(new EmptyBorder(25, 25, 25, 25));
        DefaultListModel<String> listModel = new DefaultListModel<>();
        o.getDrinks().forEach(dr -> listModel.addElement(dr.toString()));
        JList<String> list = new JList<>(listModel);
        JButton btnRemove = new ModernButton("Delete Selection", new Color(225, 29, 72)) {
            {
                addActionListener(e -> {
                    int idx = list.getSelectedIndex();
                    if (idx != -1) {
                        o.removeDrink(idx);
                        listModel.remove(idx);
                    }
                });
            }
        };
        JButton btnDone = new ModernButton("Finish Editing", ACCENT) {
            {
                addActionListener(e -> {
                    updateStats();
                    d.dispose();
                    showActiveOrders();
                });
            }
        };
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bp.add(btnRemove);
        bp.add(btnDone);
        d.add(new JLabel("Manage Items for " + o.getCustomerName()), BorderLayout.NORTH);
        d.add(new JScrollPane(list), BorderLayout.CENTER);
        d.add(bp, BorderLayout.SOUTH);
        d.setVisible(true);
    }

    private void showOrderDetails(Order o) {
        JDialog d = new JDialog(this, "Order Details - #" + o.getOrderID(), true);
        d.setSize(500, 600);
        d.setLocationRelativeTo(this);
        d.setLayout(new BorderLayout());
        ((JPanel) d.getContentPane()).setBorder(new EmptyBorder(30, 30, 30, 30));
        d.getContentPane().setBackground(CARD_BG);

        JLabel h = new JLabel("Order Breakdown");
        h.setFont(new Font("Inter", Font.BOLD, 20));
        h.setForeground(TEXT_MAIN);
        h.setBorder(new EmptyBorder(0, 0, 20, 0));
        d.add(h, BorderLayout.NORTH);

        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setBackground(CARD_BG);

        addDetailLine(details, "Customer", o.getCustomerName());
        addDetailLine(details, "Order ID", o.getOrderID());
        addDetailLine(details, "Status", o.isDone() ? "Completed" : "Active");
        addDetailLine(details, "Total Price", "$" + String.format("%.2f", o.getTotalPrice()));

        details.add(Box.createVerticalStrut(20));
        JLabel itemsH = new JLabel("Items Ordered:");
        itemsH.setFont(new Font("Inter", Font.BOLD, 14));
        itemsH.setForeground(TEXT_SUB);
        details.add(itemsH);
        details.add(Box.createVerticalStrut(10));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        o.getDrinks().forEach(dr -> listModel.addElement(dr.toString()));
        JList<String> list = new JList<>(listModel);
        list.setFont(new Font("Inter", Font.PLAIN, 14));
        list.setBackground(new Color(248, 250, 252));

        d.add(details, BorderLayout.CENTER);
        d.add(new JScrollPane(list), BorderLayout.SOUTH);

        d.setVisible(true);
    }

    private void addDetailLine(JPanel p, String key, String val) {
        JPanel r = new JPanel(new BorderLayout());
        r.setBackground(CARD_BG);
        r.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel k = new JLabel(key + ":");
        k.setFont(new Font("Inter", Font.BOLD, 13));
        k.setForeground(TEXT_SUB);
        JLabel v = new JLabel(val);
        v.setFont(new Font("Inter", Font.PLAIN, 13));
        v.setForeground(TEXT_MAIN);
        r.add(k, BorderLayout.WEST);
        r.add(v, BorderLayout.EAST);
        p.add(r);
        p.add(Box.createVerticalStrut(5));
    }

    private void clearContent(String title) {
        mainContent.removeAll();
        mainContent.setBorder(new EmptyBorder(40, 40, 40, 40));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_LIGHT);
        header.setBorder(new EmptyBorder(0, 0, 40, 0));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Inter", Font.BOLD, 28));
        t.setForeground(TEXT_MAIN);
        header.add(t, BorderLayout.WEST);

        JLabel greet = new JLabel(getGreeting() + ", Manager");
        greet.setFont(new Font("Inter", Font.PLAIN, 14));
        greet.setForeground(TEXT_SUB);
        header.add(greet, BorderLayout.SOUTH);

        mainContent.add(header, BorderLayout.NORTH);
        mainContent.revalidate();
        mainContent.repaint();
    }

    private JPanel createStatCard(String title, JLabel val) {
        ModernPanel p = new ModernPanel();
        p.setBackground(CARD_BG);
        p.setBorder(new EmptyBorder(25, 25, 25, 25));
        JLabel t = new JLabel(title);
        t.setFont(new Font("Inter", Font.PLAIN, 14));
        t.setForeground(TEXT_SUB);
        val.setFont(new Font("Inter", Font.BOLD, 26));
        val.setForeground(TEXT_MAIN);
        p.add(t, BorderLayout.NORTH);
        p.add(val, BorderLayout.CENTER);
        return p;
    }

    private JPanel createOrderRow(Order o) {
        ModernPanel p = new ModernPanel(new BorderLayout());
        p.setBackground(CARD_BG);
        p.setBorder(new EmptyBorder(15, 25, 15, 25));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JLabel info = new JLabel(o.toString() + "  -  $" + String.format("%.2f", o.getTotalPrice()));
        info.setFont(new Font("Inter", Font.BOLD, 15));
        p.add(info, BorderLayout.WEST);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actions.setBackground(CARD_BG);
        if (!o.isDone()) {
            actions.add(new ModernButton("Edit", new Color(71, 85, 105)) {
                {
                    setPreferredSize(new Dimension(80, 35));
                    addActionListener(e -> showEditOrder(o));
                }
            });
            actions.add(new ModernButton("Mark Done", ACCENT) {
                {
                    setPreferredSize(new Dimension(100, 35));
                    addActionListener(e -> {
                        service.markAsDone(o);
                        updateStats();
                        showActiveOrders();
                    });
                }
            });
        } else {
            actions.add(new ModernButton("Read Details", new Color(51, 65, 85)) {
                {
                    setPreferredSize(new Dimension(120, 35));
                    addActionListener(e -> showOrderDetails(o));
                }
            });
            JLabel done = new JLabel("Completed");
            done.setForeground(new Color(22, 163, 74));
            actions.add(done);
        }
        p.add(actions, BorderLayout.EAST);
        return p;
    }

    private void updateStats() {
        if (statTotalOrders != null) {
            statTotalOrders
                    .setText(String.valueOf(service.getWaitingOrders().size() + service.getCompletedOrders().size()));
            statRevenue.setText("$" + String.format("%.2f", service.getTotalReceived()));
            statPopular.setText(service.getMostPopularDrink());
        }
    }
}
