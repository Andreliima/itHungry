package ee.ut.math.tvt.salessystem.domain.data;


import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryItem implements Cloneable, DisplayableItem {

    private Long id;
    private PurchaseInfoTableModel table;
    
    private String date;
    private String time;
    private double totalCost;
    
    private String name;
    private Integer quantity;
    private double price;
    
    public HistoryItem(PurchaseInfoTableModel table) {
        this.table = table;
        this.date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        this.time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        this.totalCost = 0;
        
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

    
}
