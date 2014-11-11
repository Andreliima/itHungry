package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import javax.swing.table.AbstractTableModel;

import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class HistoryTableModel extends SalesSystemTableModel<HistoryItem> {

    private static final long serialVersionUID = 1L;

    protected List<HistoryItem> rows;
    
    private static final Logger log = Logger.getLogger(SalesSystemModel.class);

    public HistoryTableModel() {
    	super(new String[]{"Date", "Time", "Total cost"});
        rows = new ArrayList<HistoryItem>();
    }

    /**
     * @param item
     *            item describing selected row
     * @param columnIndex
     *            selected column index
     * @return value displayed in column with specified index
     */
    protected Object getColumnValue(HistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getDateString();
		case 1:
			return item.getTimeString();
		case 2:
			return item.getCost();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

    public void addSale(PurchaseInfoTableModel sale){
//    	log.info(sale);
    	rows.add(new HistoryItem(sale));
    	fireTableDataChanged();
    }
    
    public PurchaseInfoTableModel getTableAt(int pos){
    	return rows.get(pos).getTable();
    }
    
    public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final HistoryItem item : rows) {
			buffer.append(item.getDateString() + "\t");
			buffer.append(item.getTimeString() + "\t");
			buffer.append(item.getCost() + "\t");
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
