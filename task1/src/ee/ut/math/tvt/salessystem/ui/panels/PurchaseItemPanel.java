package ee.ut.math.tvt.salessystem.ui.panels;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.util.*;

/**
 * Purchase pane + shopping cart tabel UI.
 */
public class PurchaseItemPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    // Text field on the dialogPane
    private JComboBox<String> itemBox;
    private JTextField barCodeField;
//    private JTextField quantityField;
    private JTextField priceField;
    private JSpinner quantityField;
    
    
    SpinnerNumberModel numberModel = new SpinnerNumberModel(
    		new Integer(1), // value
            new Integer(1), // min
            new Integer(100), // max
            new Integer(1) // step
            );
    

    private JButton addItemButton;

    // Warehouse model
    private SalesSystemModel model;

    /**
     * Constructs new purchase item panel.
     * 
     * @param model
     *            composite model of the warehouse and the shopping cart.
     */
    public PurchaseItemPanel(SalesSystemModel model) {
        this.model = model;

        setLayout(new GridBagLayout());

        add(drawDialogPane(), getDialogPaneConstraints());
        add(drawBasketPane(), getBasketPaneConstraints());

        setEnabled(false);
    }

    // shopping cart pane
    private JComponent drawBasketPane() {

        // Create the basketPane
        JPanel basketPane = new JPanel();
        basketPane.setLayout(new GridBagLayout());
        basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));

        // Create the table, put it inside a scollPane,
        // and add the scrollPane to the basketPanel.
        JTable table = new JTable(model.getCurrentPurchaseTableModel());
        JScrollPane scrollPane = new JScrollPane(table);

        basketPane.add(scrollPane, getBacketScrollPaneConstraints());

        return basketPane;
    }

    // purchase dialog
    private JComponent drawDialogPane() {

        // Create the panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Product"));

        // Initialize the textfields
        itemBox = new JComboBox<String>();
        barCodeField = new JTextField();
        quantityField = new JSpinner(numberModel);
        priceField = new JTextField();
        

        // Fill the dialog fields if the bar code text field loses focus
        itemBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
               fillDialogFields();
            }
         }); 

        itemBox.setSelectedIndex(-1);
        priceField.setEditable(false);
        barCodeField.setEditable(false);

        // == Add components to the panel

        // - bar code
        panel.add(new JLabel("ID:"));
        panel.add(barCodeField);
        
        // Name
        panel.add(new JLabel("Name:"));
        panel.add(itemBox);

        // - amount
        panel.add(new JLabel("Amount:"));
        panel.add(quantityField);


        // - price
        panel.add(new JLabel("Price:"));
        panel.add(priceField);

        // Create and add the button
        addItemButton = new JButton("Add to cart");
        addItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItemEventHandler();
            }
        });

        panel.add(addItemButton);

        return panel;
    }
    
    public void updateData(){
    	if(this.isEnabled()){
    		StockItem stockItem = getStockItemByName();
    		addData();
    		itemBox.setSelectedIndex(model.getWarehouseTableModel().indexOf(stockItem));
    		
    	}
    }
    
    public void addData(){
    	itemBox.removeAllItems();
    	List<StockItem> list = model.getWarehouseTableModel().getTableRows();
        
        for(StockItem item : list){
        	itemBox.addItem(item.getName());
        }
    }

    // Fill dialog with data from the "database".
    public void fillDialogFields() {
        StockItem stockItem = getStockItemByName();
        
        if (stockItem != null) {
            String priceString = String.valueOf(stockItem.getPrice());
            priceField.setText(priceString);
            String idString = String.valueOf(stockItem.getId());
            barCodeField.setText(idString);
            numberModel.setMaximum(stockItem.getQuantity());
            quantityField.setValue(1);
        } else {
            reset();
        }
    }

    // Search the warehouse for a StockItem with the bar code entered
    // to the barCode textfield.
    private StockItem getStockItemByBarcode() {
        try {
            int code = itemBox.getSelectedIndex() + 1;
            return model.getWarehouseTableModel().getItemById(code);
        } catch (NumberFormatException ex) {
            return null;
        } catch (NoSuchElementException ex) {
            return null;
        }
    }
    
 // Search the warehouse for a StockItem with the name selected
    // on the combobox.
    private StockItem getStockItemByName() {
        try {
            String name = (String) itemBox.getSelectedItem();
            return model.getWarehouseTableModel().getItemByName(name);
        } catch (NumberFormatException ex) {
            return null;
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    /**
     * Add new item to the cart.
     */
    public void addItemEventHandler() {
        // add chosen item to the shopping cart.
        StockItem stockItem = getStockItemByName();
        if (stockItem != null) {
            int quantity;
            try {
                quantity = (int) quantityField.getValue();
            } catch (NumberFormatException ex) {
                quantity = 1;
            }
            try{
            	SoldItem item = model.getCurrentPurchaseTableModel().getItemByName(stockItem.getName());
            	if(item.getQuantity() + quantity > stockItem.getQuantity()){
            		JOptionPane.showMessageDialog(this, "Insufficient items in the warehouse!", "Warning", JOptionPane.WARNING_MESSAGE);
            	}else{
            		item.setQuantity(item.getQuantity() + quantity);
            	}
            	
            } catch (NoSuchElementException ex) {
            	if(quantity > stockItem.getQuantity()){
            		JOptionPane.showMessageDialog(this, "Insufficient items in the warehouse!", "Warning", JOptionPane.WARNING_MESSAGE);
            	}else{
            		model.getCurrentPurchaseTableModel().addItem(new SoldItem(stockItem, quantity));
            	}
            }
            
        }
        model.getCurrentPurchaseTableModel().fireTableDataChanged();
    }

    
    public float totalCost(){
  	  float cost = 0;
  	  List<SoldItem> list = model.getCurrentPurchaseTableModel().getTableRows();
  	  for(SoldItem item : list){
        	cost+= item.getPrice() * item.getQuantity();
        }
  	  
  	  return cost;
    }
    
    
    /**
     * Sets whether or not this component is enabled.
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.addItemButton.setEnabled(enabled);
        this.itemBox.setEnabled(enabled);
        this.quantityField.setEnabled(enabled);
    }

    /**
     * Reset dialog fields.
     */
    public void reset() {
        barCodeField.setText("");
        itemBox.setSelectedIndex(-1);
        quantityField.setValue(1);
        priceField.setText("");
    }

    /*
     * === Ideally, UI's layout and behavior should be kept as separated as
     * possible. If you work on the behavior of the application, you don't want
     * the layout details to get on your way all the time, and vice versa. This
     * separation leads to cleaner, more readable and better maintainable code.
     * 
     * In a Swing application, the layout is also defined as Java code and this
     * separation is more difficult to make. One thing that can still be done is
     * moving the layout-defining code out into separate methods, leaving the
     * more important methods unburdened of the messy layout code. This is done
     * in the following methods.
     */

    // Formatting constraints for the dialogPane
    private GridBagConstraints getDialogPaneConstraints() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.2;
        gc.weighty = 0d;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.fill = GridBagConstraints.NONE;

        return gc;
    }

    // Formatting constraints for the basketPane
    private GridBagConstraints getBasketPaneConstraints() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.2;
        gc.weighty = 1.0;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.fill = GridBagConstraints.BOTH;

        return gc;
    }

    private GridBagConstraints getBacketScrollPaneConstraints() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        return gc;
    }

}
