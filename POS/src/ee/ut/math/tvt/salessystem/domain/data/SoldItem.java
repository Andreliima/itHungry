package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;

import ee.ut.math.tvt.salessystem.util.HibernateUtil;

import org.hibernate.Session;

/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving history. 
 */
@Entity
@Table(name = "SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem, Serializable {
	
	private static final long serialVersionUID = 1420672609912364060L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne
    @JoinColumn(name="SALE_ID")
    private HistoryItem historyItem;
	
	@ManyToOne
    @JoinColumn(name = "STOCKITEM_ID", nullable = false)
    private StockItem stockItem;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "QUANTITY")
    private Integer quantity;
    
    @Column(name = "ITEMPRICE")
    private double price;
    
    public SoldItem(StockItem stockItem, int quantity) {
        this.stockItem = stockItem;
        this.name = stockItem.getName();
        this.price = stockItem.getPrice();
        this.quantity = quantity;
        
    }
    
    public SoldItem(StockItem stockItem, HistoryItem historyItem, int quantity) {
        this.stockItem = stockItem;
        this.name = stockItem.getName();
        this.price = stockItem.getPrice();
        this.quantity = quantity;
        this.historyItem = historyItem;
        
    }
    
    public SoldItem() {
    }
    
    public void setHistoryItem(HistoryItem historyItem) {
        this.historyItem = historyItem;
    }  
    
    public HistoryItem getHistoryItem() {
        return this.historyItem;
        
    }  
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return price * ((double) quantity);
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }
    
}
