package ee.ut.math.tvt.salessystem.domain.data;

import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "HISTORYITEM")
public class HistoryItem implements Cloneable, DisplayableItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private PurchaseInfoTableModel table;
    // TODO: Connect through SALE_ID
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE", nullable = false, updatable = false)
    private Date date;

	@Column(name = "TOTALCOST")
    private double totalCost;
    
    public HistoryItem(PurchaseInfoTableModel table) {
        this.table = new PurchaseInfoTableModel();
        this.table.populateWithData(table.getTableRows());
        this.date = new Date();
        this.totalCost = totalCost();
        
    }
    
    public float totalCost(){
    	float cost = 0;
    	List<SoldItem> list = table.getTableRows();
    	for(SoldItem item : list){
    		cost+= item.getPrice() * item.getQuantity();
    	}
    	return cost;
    }
    
    public PurchaseInfoTableModel getTable(){
    	return table;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getDate() {
        return date;
    }
    
    public String getDateString() {
    	return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }
    
    public String getTimeString() {
    	return new SimpleDateFormat("HH:mm:ss").format(date);
    }
    
    public double getCost() {
        return totalCost;
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
