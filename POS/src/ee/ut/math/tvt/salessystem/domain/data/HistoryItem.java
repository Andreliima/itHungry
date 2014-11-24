package ee.ut.math.tvt.salessystem.domain.data;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

@Entity
@Table(name = "HISTORYITEM")
public class HistoryItem  extends AbstractTableModel implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6794917670756529051L;

	private static final Logger log = Logger.getLogger(PurchaseInfoTableModel.class);
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy="historyItem")
    private List<SoldItem> rows;
    
    @Column(name = "SALE_DATE")
    private String date;
    
    @Column(name = "SALE_TIME")
    private String time;
    
    @Column(name = "TOTALCOST")
    private double totalCost;
    
    @Transient
    protected final String[] headers;
    
    public HistoryItem(Long id, String date, String time, List<SoldItem> rows) {
    	this.id = id;
    	this.date = date;
    	this.time = time;
    	this.rows = new ArrayList<SoldItem>(rows);
    	this.totalCost = totalCost();
    	this.headers = new String[] { "Id", "Name", "Price", "Quantity", "Sum"};
    }
 
    public HistoryItem(Long id, String date, String time) {
    	this.id = id;
    	this.date = date;
    	this.time = time;
    	this.rows = new ArrayList<SoldItem>();
    	this.totalCost = totalCost();
    	this.headers = new String[] { "Id", "Name", "Price", "Quantity", "Sum"};
    }    
    
    public HistoryItem(Long id, String date, String time, double totalCost) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.totalCost = totalCost;
        this.rows = new ArrayList<SoldItem>();
        this.headers = new String[] { "Id", "Name", "Price", "Quantity", "Sum"};
       
        log.info(id);
        log.info(date);
        log.info(totalCost);
    }
    
    public HistoryItem(Long id, String date, String time, double totalCost, List<SoldItem> rows) {
    	this.id = id;
        this.date = date;
        this.time = time;
        this.totalCost = totalCost;
        this.rows = rows;
        this.headers = new String[] { "Id", "Name", "Price", "Quantity", "Sum"};
        
    }
    
    
    public HistoryItem() {
    	this.headers = new String[] { "Id", "Name", "Price", "Quantity", "Sum"};
    }
    
    
    public float totalCost(){
    	  float cost = 0;
    	  for(SoldItem item : rows){
          	cost+= item.getPrice() * item.getQuantity();
          }
    	  
    	  return cost;
      }
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDate() {
        return date;
    }
    
    public String getTime() {
        return time;
    }
    
    public double getCost() {
        return totalCost;
    }
    
    public void addSoldItem(SoldItem item){
    	rows.add(item);
    	totalCost = totalCost();
    }
    
    public List<SoldItem> getTableRows(){
    	return this.rows;
    }
    public void setTableRows(List<SoldItem> rows){
    	this.rows = rows;
    }
    

    
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		case 4:
			return item.getQuantity() * item.getPrice();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final SoldItem item : rows) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getName() + "\t");
			buffer.append(item.getPrice() + "\t");
			buffer.append(item.getQuantity() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
	
	@Override
    public String getColumnName(final int columnIndex) {
        return headers[columnIndex];
    }
	
	public int getColumnCount() {
        return headers.length;
    }

    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(final int rowIndex, final int columnIndex) {
        return getColumnValue(rows.get(rowIndex), columnIndex);
    }
    
}
