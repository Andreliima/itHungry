package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;
import org.hibernate.Session;
import org.apache.log4j.Logger;
import java.io.Serializable;

/**
 * Stock item. Corresponds to the Data Transfer Object design pattern.
 */
@Entity
@Table(name = "STOCKITEM")
public class StockItem implements Cloneable, DisplayableItem, Serializable {
	
	private static final Logger log = Logger.getLogger(StockItem.class);
	private static final long serialVersionUID = 1420672609912364066L;
	
	@Id
    private Long id;
	
	@Column(name = "NAME")
    private String name;
	
	@Column(name = "PRICE")
    private double price;

	@Column(name = "DESCRIPTION")
    private String description;
    
	@Column(name = "QUANTITY")
    private int quantity;

    /**
     * Constucts new <code>StockItem</code> with the specified values.
     * @param id barcode id
     * @param name name of the product
     * @param desc description of the product
     * @param price price of the product
     */
    public StockItem(Long id, String name, String desc, double price) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.price = price;
    }
    
    public StockItem(Long id, String name, String desc, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Constructs new  <code>StockItem</code>.
     */
    public StockItem() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        Session session = HibernateUtil.currentSession();
        session.beginTransaction();
//        if(session.get(this.name, id) == null) log.info("Kirjet ei leitud");
//        else log.info("Kirjed on olemas");
        session.saveOrUpdate(this);
        session.getTransaction().commit();
        session.flush();
        HibernateUtil.closeSession();
    }

    public String toString() {
        return id + " " + name + " " + description + " " + price;
    }

    /**
     * Method for querying the value of a certain column when StockItems are shown
     * as table rows in the SalesSstemTableModel. The order of the columns is:
     * id, name, price, quantity.
     */
    public Object getColumn(int columnIndex) {
        switch(columnIndex) {
            case 0: return id;
            case 1: return name;
            case 2: return new Double(price);
            case 3: return new Integer(quantity);
            default: throw new RuntimeException("invalid column!");
        }
    }
    
    
    public Object clone() {
        StockItem item =
            new StockItem(getId(), getName(), getDescription(), getPrice(), getQuantity());
        return item;
    }
		
}
