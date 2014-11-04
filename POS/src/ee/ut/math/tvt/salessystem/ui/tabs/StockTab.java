package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import org.apache.log4j.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.table.JTableHeader;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;
import javax.swing.JComponent;

import java.lang.Runnable;


public class StockTab {

  private JSpinner barCodeField;
  private JTextField nameField;
  private JTextField descriptionField;
  private JSpinner priceField;
  private JSpinner quantityField;
  private JButton addItem;
  
  private static final Logger log = Logger.getLogger(PurchaseTab.class);
  
  
  SpinnerNumberModel priceNumberModel = new SpinnerNumberModel(
  		new Double(0.0), // value
          new Double(0.0), // min
          new Double(1000000000.0), // max
          new Double(0.01) // step
          );

  SpinnerNumberModel quantityNumberModel = new SpinnerNumberModel(
	  		new Integer(1), // value
	          new Integer(1), // min
	          new Integer(10000000), // max
	          new Integer(1) // step
	          );
  
  SpinnerNumberModel idNumberModel = new SpinnerNumberModel(
	  		new Integer(0), // value
	          new Integer(0), // min
	          new Integer(10000000), // max
	          new Integer(1) // step
	          );

  private SalesSystemModel model;
  private PurchaseTab purchaseTab;

  public StockTab(SalesSystemModel model, PurchaseTab purchaseTab) {
    this.model = model;
    this.purchaseTab = purchaseTab;
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

    barCodeField = new JSpinner(idNumberModel);
    nameField = new JTextField();
    descriptionField = new JTextField();
    priceField = new JSpinner(priceNumberModel);
    quantityField = new JSpinner(quantityNumberModel);
    
    ((JSpinner.DefaultEditor) priceField.getEditor()).getTextField().addFocusListener(new FocusAdapter() {
        public void focusGained(FocusEvent evt) {
        	SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	try {
                	    Thread.sleep(30);
                	} catch (InterruptedException e) {}
//                	log.info("focus priceField");
                	((JSpinner.DefaultEditor) priceField.getEditor()).getTextField().selectAll();
                }
            });
        }
    });
    
    ((JSpinner.DefaultEditor) quantityField.getEditor()).getTextField().addFocusListener(new FocusAdapter() {
        public void focusGained(FocusEvent evt) {
        	SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	try {
                	    Thread.sleep(30);
                	} catch (InterruptedException e) {}
//                	log.info("focus quantityField");
                	((JSpinner.DefaultEditor) quantityField.getEditor()).getTextField().selectAll();
                }
            });
        }
    });
    
    ((JSpinner.DefaultEditor) barCodeField.getEditor()).getTextField().addFocusListener(new FocusAdapter() {
        public void focusGained(FocusEvent evt) {
        	SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	try {
                	    Thread.sleep(30);
                	} catch (InterruptedException e) {}
//                	log.info("focus barCodeField");
                	((JSpinner.DefaultEditor) barCodeField.getEditor()).getTextField().selectAll();
                }
            });
        }
    });
    
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
  
  private StockItem getStockItemByName(String name) {
      try {
//    	  log.info(model.getWarehouseTableModel());
          return model.getWarehouseTableModel().getItemByName(name);
      } catch (NumberFormatException ex) {
//    	  log.info(ex.toString());
          return null;
      } catch (NoSuchElementException ex) {
//    	  log.info(ex.toString());
          return null;
      }
  }
  
  
  private StockItem getStockItemById(Long id) {
      try {
//    	  log.info(model.getWarehouseTableModel());
          return model.getWarehouseTableModel().getItemById(id);
      } catch (NumberFormatException ex) {
//    	  log.info(ex.toString());
          return null;
      } catch (NoSuchElementException ex) {
//    	  log.info(ex.toString());
          return null;
      }
  }

  /**
   * Add new item to the warehouse.
   */
  public void addItemEventHandler() {
	  // Add chosen item to the warehouse.
	  if (!nameField.getText().isEmpty()) {
//		  log.info(nameField.getText());
		  StockItem stockItem = getStockItemByName((String)nameField.getText());
//		  log.info(stockItem);
		  if (stockItem != null) {
			  Long barCode;
			  try {
				  barCode = Long.valueOf((int)barCodeField.getValue());
			  } catch (NumberFormatException ex) {
				  barCode = -1L;
			  }
//			  log.info(barCode);
			  if((stockItem.getId().equals(barCode) && stockItem.getName().equals(nameField.getText()) && stockItem.getPrice() == (double)priceField.getValue()) || 
				(((int)barCodeField.getValue()) == 0 && stockItem.getName().equals(nameField.getText()) && stockItem.getPrice() == (double)priceField.getValue())){
				  stockItem.setQuantity(stockItem.getQuantity() + (int)quantityField.getValue());
			  }
		  }
		  else {
			  Long barCode;
			  Long defaultNewBarCode = 0L;
			  if(((int)barCodeField.getValue()) == 0)
				  barCode = model.getWarehouseTableModel().getTableRows().get(model.getWarehouseTableModel().getRowCount() - 1).getId() + 1;
			  else
			  try {
				  barCode = Long.valueOf((int)barCodeField.getValue());
			  } catch (NumberFormatException ex) {
				  barCode = defaultNewBarCode;
			  }
			  try {
				  StockItem item = model.getWarehouseTableModel().getItemById(barCode);
				  if (!item.getName().equals(nameField.getText())) {
					  barCode = defaultNewBarCode;
				  }
			  } catch (NoSuchElementException ex) {
			  }
			  Double price;
			  try {
				  price = (double)priceField.getValue();
			  } catch (NumberFormatException ex) {
				  price = 0.00;
			  }
			  int quantity;
			  try {
				  quantity = (int)quantityField.getValue();
			  } catch (NumberFormatException ex) {
				  quantity = 1;
			  }
			  model.getWarehouseTableModel().addItem(new StockItem(barCode, nameField.getText(), descriptionField.getText(), price, quantity));
		  }
	  }else if(((int)barCodeField.getValue()) != 0){
		  Long barCode;
		  try {
			  barCode = Long.valueOf((int)barCodeField.getValue());
		  } catch (NumberFormatException ex) {
			  barCode = -1L;
		  }		  
		  StockItem stockItem = getStockItemById(barCode);
//		  log.info(stockItem);
		  if (stockItem != null) {
			  if((stockItem.getId().equals(barCode) && stockItem.getPrice() == (double)priceField.getValue())) {
				  stockItem.setQuantity(stockItem.getQuantity() + (int)quantityField.getValue());
			  }
		  }
	  }
	  model.getWarehouseTableModel().fireTableDataChanged();
	  purchaseTab.getPurchasePane().updateData();
	  
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
