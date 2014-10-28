package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import java.text.NumberFormat;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "Point-of-sale" in the menu).
 */
public class PurchaseTab {

  private static final Logger log = Logger.getLogger(PurchaseTab.class);

  private final SalesDomainController domainController;

  private JButton newPurchase;

  private JButton submitPurchase;

  private JButton cancelPurchase;

  private PurchaseItemPanel purchasePane;

  private SalesSystemModel model;
  
  private HistoryTab historyTab;
  
  private JFormattedTextField sumField;
  
  private JFormattedTextField payField;
  
  private JFormattedTextField changeField;


  public PurchaseTab(SalesDomainController controller,
      SalesSystemModel model, HistoryTab historyTab)
  {
    this.domainController = controller;
    this.model = model;
    this.historyTab = historyTab;
  }


  /**
   * The purchase tab. Consists of the purchase menu, current purchase dialog and
   * shopping cart table.
   */
  public Component draw() {
    JPanel panel = new JPanel();

    // Layout
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    panel.setLayout(new GridBagLayout());

    // Add the purchase menu
    panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());

    // Add the main purchase-panel
    purchasePane = new PurchaseItemPanel(model);
    panel.add(purchasePane, getConstraintsForPurchasePanel());

    return panel;
  }




  // The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
  private Component getPurchaseMenuPane() {
    JPanel panel = new JPanel();

    // Initialize layout
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gc = getConstraintsForMenuButtons();

    // Initialize the buttons
    newPurchase = createNewPurchaseButton();
    submitPurchase = createConfirmButton();
    cancelPurchase = createCancelButton();

    // Add the buttons to the panel, using GridBagConstraints we defined above
    panel.add(newPurchase, gc);
    panel.add(submitPurchase, gc);
    panel.add(cancelPurchase, gc);

    return panel;
  }


  // Creates the button "New purchase"
  private JButton createNewPurchaseButton() {
    JButton b = new JButton("New purchase");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newPurchaseButtonClicked();
      }
    });

    return b;
  }

  // Creates the "Confirm" button
  private JButton createConfirmButton() {
    JButton b = new JButton("Confirm");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        submitPurchaseButtonClicked();
      }
    });
    b.setEnabled(false);

    return b;
  }


  // Creates the "Cancel" button
  private JButton createCancelButton() {
    JButton b = new JButton("Cancel");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelPurchaseButtonClicked();
      }
    });
    b.setEnabled(false);

    return b;
  }





  /* === Event handlers for the menu buttons
   *     (get executed when the buttons are clicked)
   */


  /** Event handler for the <code>new purchase</code> event. */
  protected void newPurchaseButtonClicked() {
    log.info("New sale process started");
    try {
      domainController.startNewPurchase();
      startNewSale();
    } catch (VerificationFailedException e1) {
      log.error(e1.getMessage());
    }
  }


  /**  Event handler for the <code>cancel purchase</code> event. */
  protected void cancelPurchaseButtonClicked() {
    log.info("Sale cancelled");
    try {
      domainController.cancelCurrentPurchase();
      endSale();
      model.getCurrentPurchaseTableModel().clear();
    } catch (VerificationFailedException e1) {
      log.error(e1.getMessage());
    }
  }


  /** Event handler for the <code>submit purchase</code> event. */
  protected void submitPurchaseButtonClicked() {
	  int response =  JOptionPane.showConfirmDialog(null, paymentWindow(), "Payment", JOptionPane.OK_CANCEL_OPTION,
			  JOptionPane.PLAIN_MESSAGE);  
	  if(response == JOptionPane.YES_OPTION){ 
		  log.info("Sale complete");
		  try {
			  log.debug("Contents of the current basket:\n" + model.getCurrentPurchaseTableModel());
			  domainController.submitCurrentPurchase(
					  model.getCurrentPurchaseTableModel().getTableRows()
					  );
			  
		  } catch (VerificationFailedException e1) {
			  log.error(e1.getMessage());
		  }
		  deductGoods();
		  endSale();
		  historyTab.getHistoryModel().addSale(model.getCurrentPurchaseTableModel());
		  model.getCurrentPurchaseTableModel().clear();
	  }
  }


  private void deductGoods(){
	  List<SoldItem> soldGoods = model.getCurrentPurchaseTableModel().getTableRows();
	  for(SoldItem soldItem : soldGoods){
		  StockItem stockItem = model.getWarehouseTableModel().getItemByName(soldItem.getName());
		  stockItem.setQuantity(stockItem.getQuantity() - soldItem.getQuantity());
	  }
	  
	  
  }
  
  private JPanel paymentWindow(){
	  JPanel paneel = new JPanel(new GridLayout(3,2));
	  
	  NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
	  
	  paneel.add(new JLabel("Sum: "));
	  
	  sumField = new JFormattedTextField(defaultFormat);
	  sumField.setEditable(false);
	  sumField.setValue(purchasePane.totalCost());
	  sumField.setHorizontalAlignment(JFormattedTextField.RIGHT);
	  paneel.add(sumField);
	  
	  paneel.add(new JLabel("Cash: "));
	  
	  payField = new JFormattedTextField(defaultFormat);
	  payField.setEditable(true);
	  payField.setHorizontalAlignment(JFormattedTextField.RIGHT);
	  paneel.add(payField);
	  
	  paneel.add(new JLabel("Change: "));
	  
	  changeField = new JFormattedTextField(defaultFormat);
	  changeField.setEditable(false);
	  changeField.setHorizontalAlignment(JFormattedTextField.RIGHT);
	  paneel.add(changeField);
	  
	  payField.getDocument().addDocumentListener(new DocumentListener() {
	      public void changedUpdate(DocumentEvent documentEvent) {
	          update();
	        }
	        public void insertUpdate(DocumentEvent documentEvent) {
	          update();
	        }
	        public void removeUpdate(DocumentEvent documentEvent) {
	          update();
	        }
	        
	        private void update() {
	        	 
	        	  double nr1 = 0.0;
	        	  double nr2 = 0.0;
	        	  
	        	  try{
	        		  nr1 = ((Number)sumField.getValue()).doubleValue();
	        		  nr2 = ((Number)payField.getValue()).doubleValue();
	        		  
	        	  }catch (NullPointerException ex){
	        		  nr2 = 0.0;
	        	  }finally{
	        		  changeField.setValue(nr2-nr1);
	        	  }
	        }
	      });
	  
	  return paneel;
  }

  
  /* === Helper methods that bring the whole purchase-tab to a certain state
   *     when called.
   */

  // switch UI to the state that allows to proceed with the purchase
  private void startNewSale() {
    purchasePane.reset();
    purchasePane.addData();

    purchasePane.setEnabled(true);
    submitPurchase.setEnabled(true);
    cancelPurchase.setEnabled(true);
    newPurchase.setEnabled(false);
  }

  // switch UI to the state that allows to initiate new purchase
  private void endSale() {
    purchasePane.reset();

    cancelPurchase.setEnabled(false);
    submitPurchase.setEnabled(false);
    newPurchase.setEnabled(true);
    purchasePane.setEnabled(false);
  }




  /* === Next methods just create the layout constraints objects that control the
   *     the layout of different elements in the purchase tab. These definitions are
   *     brought out here to separate contents from layout, and keep the methods
   *     that actually create the components shorter and cleaner.
   */

  private GridBagConstraints getConstraintsForPurchaseMenu() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 0d;

    return gc;
  }


  private GridBagConstraints getConstraintsForPurchasePanel() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.fill = GridBagConstraints.BOTH;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 1.0;

    return gc;
  }


  // The constraints that control the layout of the buttons in the purchase menu
  private GridBagConstraints getConstraintsForMenuButtons() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.weightx = 0;
    gc.anchor = GridBagConstraints.CENTER;
    gc.gridwidth = GridBagConstraints.RELATIVE;

    return gc;
  }

}
