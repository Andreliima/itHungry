package ee.ut.math.tvt.salessystem.domain.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

@Entity
@Table(name = "HISTORYITEM")
public class HistoryItem implements Cloneable, DisplayableItem {
	
	private static final Logger log = Logger.getLogger(HistoryItem.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
    private Long id;
    
    //@Temporal(TemporalType.TIMESTAMP)
    //@Column(name = "DATE", nullable = false, updatable = false)
    @Column(name = "DATE")
    private Date date;	
    
    /*
    @OneToMany(mappedBy = "historyItem")
    private Set<SoldItem> soldItems;
    */
	
	@Column(name = "TOTALCOST")
    private double totalCost;
	
	@Transient
	private PurchaseInfoTableModel table;
	
	
    public HistoryItem(Long id, Date date, double totalCost) {
    	this.id = id;
    	this.date = date;
    	this.totalCost = totalCost;
    }
    
    
    public HistoryItem(double totalCost) {
    	this.date = new Date();
    	this.totalCost = totalCost;
    }
    
    public HistoryItem()
    {
    	
    }
    
	
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
    
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setTable(PurchaseInfoTableModel table) {
		this.table = table;
	}

	@Override
	public String getName() {
		return getDateString() + " " + getTimeString() + ": " + getCost();
	}
}
