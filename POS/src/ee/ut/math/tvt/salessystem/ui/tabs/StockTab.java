package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;


public class StockTab {

  private JTextField barCodeField;
  private JTextField nameField;
  private JTextField descriptionField;
  private JTextField priceField;
  private JTextField quantityField;
  private JButton addItem;

  private SalesSystemModel model;

  public StockTab(SalesSystemModel model) {
    this.model = model;
  }

  // warehouse stock tab - consists of a menu and a table
  public Component draw() {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    GridBagLayout gb = new GridBagLayout();
    GridBagConstraints gc = new GridBagConstraints();
    panel.setLayout(gb);

    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 0d;

    panel.add(drawStockMenuPane(), gc);

    gc.weighty = 1.0;
    gc.fill = GridBagConstraints.BOTH;
    panel.add(drawStockMainPane(), gc);
    return panel;
  }

  // warehouse menu
  private Component drawStockMenuPane() {

	// Create the panel
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(6, 2));
    panel.setBorder(BorderFactory.createTitledBorder("New product"));

    // Initialize the textfields
    JTextField barCodeField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField descriptionField = new JTextField();
    JTextField priceField = new JTextField();
    JTextField quantityField = new JTextField();
    
    // Add components to the panel
    // Bar code
    panel.add(new JLabel("Bar code:"));
    panel.add(barCodeField);
    // Name
    panel.add(new JLabel("Name:"));
    panel.add(nameField);
    // Name
    panel.add(new JLabel("Description:"));
    panel.add(descriptionField);
    // Price
    panel.add(new JLabel("Price:"));
    panel.add(priceField);
    // Quantity
    panel.add(new JLabel("Quantity:"));
    panel.add(quantityField);
    
    // Create and add the button
    JButton addItemButton = new JButton("Add");
    addItemButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            addItemEventHandler();
        }
    });
    panel.add(new JLabel());
    panel.add(addItemButton);
    
    return panel;
  }

  /**
   * Add new item to the warehouse.
   */
  public void addItemEventHandler() {
      // Add chosen item to the warehouse.
      int quantity;
      try {
          quantity = Integer.parseInt(quantityField.getText());
      } catch (NumberFormatException ex) {
          quantity = 1;
      }
      model.getWarehouseTableModel().addItem(new StockItem(Long.parseLong(barCodeField.getText()), nameField.getText(), descriptionField.getText(), Double.parseDouble(priceField.getText()), quantity));
  }


  // table of the wareshouse stock
  private Component drawStockMainPane() {
    JPanel panel = new JPanel();

    JTable table = new JTable(model.getWarehouseTableModel());

    JTableHeader header = table.getTableHeader();
    header.setReorderingAllowed(false);

    JScrollPane scrollPane = new JScrollPane(table);

    GridBagConstraints gc = new GridBagConstraints();
    GridBagLayout gb = new GridBagLayout();
    gc.fill = GridBagConstraints.BOTH;
    gc.weightx = 1.0;
    gc.weighty = 1.0;

    panel.setLayout(gb);
    panel.add(scrollPane, gc);

    panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
    return panel;
  }

}
