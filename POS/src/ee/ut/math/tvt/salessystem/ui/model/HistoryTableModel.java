package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import javax.swing.table.AbstractTableModel;

import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;

@SuppressWarnings("unchecked")
public  class HistoryTableModel extends
        AbstractTableModel {

    private static final long serialVersionUID = 1L;
    
    private long sale_id = 0L;

    protected List<HistoryItem> rows;
    public List<HistoryItem> getRows() {
		return rows;
	}

	public void setRows(List<HistoryItem> rows) {
		this.rows = rows;
	}

	protected final String[] headers;
    
    private static final Logger log = Logger.getLogger(SalesSystemModel.class);

    public HistoryTableModel(final String[] headers) {
        this.headers = headers;
        Session session = HibernateUtil.currentSession();
        rows = session.createQuery("from HistoryItem").list();
    }
    
    public HistoryTableModel() {
    	this.headers = new String[]{"Date", "Time", "Total cost"};
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
			return item.getDate();
		case 1:
			return item.getTime();
		case 2:
			return item.getCost();
		}
		throw new IllegalArgumentException("Column index out of range");
	}
    
    public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final HistoryItem item : rows) {
			buffer.append(item.getDate() + "\t");
			buffer.append(item.getTime() + "\t");
			buffer.append(item.getCost() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(final int columnIndex) {
        return headers[columnIndex];
    }

    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return getColumnValue(rows.get(rowIndex), columnIndex);
    }

/*    // search for item with the specified id
    public HistoryItem getItemById(final long id) {
        for (final HistoryItem item : rows) {
            if (item.getId() == id)
                return item;
        }
        throw new NoSuchElementException();
    }
    
 // search for item with the specified name
    public HistoryItem getItemByName(final String name) {
        for (final HistoryItem item : rows) {
            if (item.getName() == name)
                return item;
        }
        throw new NoSuchElementException();
    }
*/    
    public HistoryItem getTableAt(int pos){
    	return rows.get(pos);
    }

    public List<HistoryItem> getTableRows() {
        return rows;
    }
    

    public void addSale(PurchaseInfoTableModel sale){
//    	log.info(sale);
    	String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    	String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
    	HistoryItem item = new HistoryItem(Long.valueOf(rows.size()), date, time, sale.totalCost(), sale.getTableRows());
    	Session session = HibernateUtil.currentSession();
        session.beginTransaction();
        session.saveOrUpdate(item);
        session.getTransaction().commit();
        HibernateUtil.closeSession();

        session = HibernateUtil.currentSession();
        session.beginTransaction();
 
    	for(SoldItem soldItem : item.getTableRows()){
        	soldItem.setHistoryItem(item);
//        	item.addSoldItem(soldItem);
        	session.saveOrUpdate(soldItem);
        }
    	
        session.getTransaction().commit();
        HibernateUtil.closeSession();
        
    	rows.add(item);
    	sale_id++;
    	this.fireTableDataChanged();
    }
    
    public void clear() {
        rows = new ArrayList<HistoryItem>();
        this.fireTableDataChanged();
    }

    public void populateWithData(final List<HistoryItem> data) {
        rows.clear();
        rows.addAll(data);
    }
    
    
}
