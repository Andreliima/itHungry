package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.HistoryTableModel;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

import org.apache.log4j.Logger;

import java.awt.Component;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.Point;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;



/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {
    
    // TODO - implement!
	
	 private static final Logger log = Logger.getLogger(SalesSystemModel.class);
	
	private HistoryTableModel model;

    
    public HistoryTab() {
    	this.model = new HistoryTableModel(new String[]{"Date", "Time", "Total cost"});
    }
    
    public HistoryTableModel getHistoryModel(){
    	return model;
    }
    
    private JPanel historyWindow(HistoryItem historyTable){
    	JPanel paneel = new JPanel();
    	JTable table = new JTable(historyTable);

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);

        GridBagConstraints gc = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        paneel.setLayout(gb);
        paneel.add(scrollPane, gc);

        paneel.setBorder(BorderFactory.createTitledBorder("Order details"));
    	

    	return paneel;
    }
    
    public Component draw() {
        JPanel panel = new JPanel();

        JTable table = new JTable(model);

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

        panel.setBorder(BorderFactory.createTitledBorder("History"));
        
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table =(JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                	HistoryItem historyTable = model.getTableAt(row);
//                	log.info(row);
//                	log.info(historyTable.getTableRows());
                	JOptionPane.showConfirmDialog(null, historyWindow(historyTable), "Detailed order view", JOptionPane.PLAIN_MESSAGE,
              			  JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        
        return panel;
    }
}