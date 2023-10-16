import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Objects;

public class PizzaGUIFrame extends JFrame {

    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private JComboBox<String> sizeComboBox;
    private JCheckBox[] toppings;
    private JTextArea orderSummaryTextArea;
    private static final double[] TOPPING_PRICES = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
    private static final double TAX_RATE = 0.07;


    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));

        JPanel sizePanel = createSizePanel();
        JPanel crustPanel = createCrustPanel();
        JPanel toppingsPanel = createToppingsPanel();

        selectionPanel.add(sizePanel);
        selectionPanel.add(crustPanel);
        selectionPanel.add(toppingsPanel);

        JPanel orderSummaryPanel = createOrderSummaryPanel();

        mainPanel.add(selectionPanel, BorderLayout.WEST);
        mainPanel.add(orderSummaryPanel, BorderLayout.CENTER);

        Container contentPane = getContentPane();
        contentPane.add(mainPanel, BorderLayout.CENTER);
    }



    private JPanel createSizePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Size"));

        String[] sizes = {"Please Select Size", "Small", "Medium", "Large", "Super"};
        sizeComboBox = new JComboBox<>(sizes);

        panel.add(sizeComboBox);

        sizeComboBox.addActionListener(e -> updateOrderSummary());

        panel.setPreferredSize(new Dimension(200, 20));

        return panel;
    }

    private JPanel createCrustPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Crust Type"));

        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-dish");

        ButtonGroup group = new ButtonGroup();
        group.add(thinCrust);
        group.add(regularCrust);
        group.add(deepDishCrust);

        panel.add(thinCrust);
        panel.add(regularCrust);
        panel.add(deepDishCrust);

        ActionListener crustListener = e -> updateOrderSummary();
        thinCrust.addActionListener(crustListener);
        regularCrust.addActionListener(crustListener);
        deepDishCrust.addActionListener(crustListener);

        panel.setPreferredSize(new Dimension(200, 100)); // Adjust the values as needed

        return panel;
    }

    private JPanel createToppingsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Toppings"));

        String[] toppingNames = {"Pepperoni", "Mushrooms", "Olives", "Pineapple", "Bacon", "Extra Cheese"};
        toppings = new JCheckBox[toppingNames.length];

        for (int i = 0; i < toppingNames.length; i++) {
            toppings[i] = new JCheckBox(toppingNames[i]);
            panel.add(toppings[i]);

            toppings[i].addActionListener(e -> updateOrderSummary());
        }

        panel.setPreferredSize(new Dimension(200, 100)); // Adjust the values as needed

        return panel;
    }



    private JPanel createOrderSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Order Summary"));

        // Remove the local declaration here
        orderSummaryTextArea = new JTextArea(24, 28);  // Adjust rows and columns as needed
        orderSummaryTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(orderSummaryTextArea);
        panel.add(scrollPane);

        JPanel buttonPanel = new JPanel();
        JButton placeOrderButton = new JButton("Place Order");
        JButton clearButton = new JButton("Clear");
        JButton quitButton = new JButton("Quit");

        placeOrderButton.addActionListener(e -> placeOrder());
        clearButton.addActionListener(e -> clearForm());
        quitButton.addActionListener(e -> quitApplication());

        buttonPanel.add(placeOrderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        panel.add(buttonPanel);

        return panel;
    }

    private void updateOrderSummary() {
        double baseCost = 0;
        if (Objects.equals(sizeComboBox.getSelectedItem(), "Please Select Size")) baseCost = 0.00;
        if (Objects.equals(sizeComboBox.getSelectedItem(), "Small")) baseCost = 8.00;
        if (Objects.equals(sizeComboBox.getSelectedItem(), "Medium")) baseCost = 12.00;
        if (Objects.equals(sizeComboBox.getSelectedItem(), "Large")) baseCost = 16.00;
        if (Objects.equals(sizeComboBox.getSelectedItem(), "Super")) baseCost = 20.00;

        DecimalFormat df = new DecimalFormat("#.##");

        StringBuilder summary = new StringBuilder("Item\t\tPrice\n");
        summary.append("===========================================\n");
        summary.append(sizeComboBox.getSelectedItem());
        if (!Objects.equals(sizeComboBox.getSelectedItem(),"Please Select Size"))
         {
            summary.append("\t\t").append("$").append(df.format(baseCost)).append(".00");
        }

        if (thinCrust.isSelected())
        {
            summary.append("\nThin Crust\t").append("\n");
        }

        if (regularCrust.isSelected())
        {
            summary.append("\nRegular Crust\t").append("\n");
        }

        if (deepDishCrust.isSelected())
        {
            summary.append("\nDeep-dish Crust\t").append("\n");
        }

        summary.append("\nToppings:\n");
        summary.append("--------------------------------------------------------------------------\n");

        for (JCheckBox topping : toppings) {
            if (topping.isSelected()) {
                summary.append(topping.getText()).append("\t\t$1.00\n");
            }
        }
        orderSummaryTextArea.setText(summary.toString());
    }

    private double calculateOrderTotal() {
        double baseCost = 0;
        if (Objects.equals(sizeComboBox.getSelectedItem(), "Small")) baseCost = 8.00;
        if (Objects.equals(sizeComboBox.getSelectedItem(), "Medium")) baseCost = 12.00;
        if (Objects.equals(sizeComboBox.getSelectedItem(), "Large")) baseCost = 16.00;
        if (Objects.equals(sizeComboBox.getSelectedItem(), "Super")) baseCost = 20.00;

        double toppingCost = 0;
        for (int i = 0; i < toppings.length; i++) {
            if (toppings[i].isSelected()) {
                toppingCost += TOPPING_PRICES[i];
            }
        }

        return baseCost + toppingCost;
    }

    private void placeOrder() {

        if (!thinCrust.isSelected() && !regularCrust.isSelected() && !deepDishCrust.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a type of crust before placing your order.", "Crust Selection Required", JOptionPane.ERROR_MESSAGE);
        } else {
            String crustChoice = "";
            if (thinCrust.isSelected()) crustChoice = "Thin Crust";
            if (regularCrust.isSelected()) crustChoice = "Regular Crust";
            if (deepDishCrust.isSelected()) crustChoice = "Deep-dish Crust";

            double baseCost = 0;
            if (Objects.equals(sizeComboBox.getSelectedItem(), "Small")) baseCost = 8.00;
            if (Objects.equals(sizeComboBox.getSelectedItem(), "Medium")) baseCost = 12.00;
            if (Objects.equals(sizeComboBox.getSelectedItem(), "Large")) baseCost = 16.00;
            if (Objects.equals(sizeComboBox.getSelectedItem(), "Super")) baseCost = 20.00;

            String sizeChoice = Objects.requireNonNull(sizeComboBox.getSelectedItem()).toString();

            double orderTotal = calculateOrderTotal();
            double tax = orderTotal * TAX_RATE;
            double totalWithTax = orderTotal + tax;

            DecimalFormat df = new DecimalFormat("#.##");
            StringBuilder receipt = new StringBuilder("Item\t\tPrice\n");
            receipt.append("===========================================\n");
            receipt.append(sizeChoice).append(" ").append("\t\t$").append(df.format(baseCost)).append(".00\n");
            receipt.append(crustChoice);
            receipt.append("\n\nToppings:\n");
            receipt.append("--------------------------------------------------------------------------\n");

            for (JCheckBox topping : toppings) {
                if (topping.isSelected()) {
                    receipt.append(topping.getText()).append("\t\t$1.00\n");
                }
            }

            receipt.append("\n\nSub-total:\t\t$").append(df.format(orderTotal)).append("\n");
            receipt.append("Tax:\t\t$").append(df.format(tax)).append("\n");
            receipt.append("--------------------------------------------------------------------------\n");
            receipt.append("Total:\t\t$").append(df.format(totalWithTax)).append("\n");
            receipt.append("===========================================");

            orderSummaryTextArea.setText(receipt.toString());
        }
    }

    private void clearForm() {
        thinCrust.setSelected(false);
        regularCrust.setSelected(false);
        deepDishCrust.setSelected(false);
        sizeComboBox.setSelectedIndex(0);

        for (JCheckBox topping : toppings) {
            topping.setSelected(false);
        }
        orderSummaryTextArea.setText("");
    }

    private void quitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
