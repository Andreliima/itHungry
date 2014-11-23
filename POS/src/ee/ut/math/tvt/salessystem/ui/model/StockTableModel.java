package ee.ut.math.tvt.salessystem.ui.model;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);

	public StockTableModel() {
		super(new String[] {"Id", "Name", "Price", "Quantity"});
	}

	@Override
	protected Object getColumnValue(StockItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	/**
	 * Add new stock item to table. If there already is a stock item with
	 * same id, then existing item's quantity will be increased.
	 * @param stockItem
	 * @throws VerificationFailedException 
	 */
	public void addItem(final StockItem stockItem)
			throws VerificationFailedException {
		try {
			StockItem item = getItemById(stockItem.getId());
			if (!stockItem.getName().equals(item.getName())) {
				throw new VerificationFailedException(
						"Id exists, different name");
			}
			item.setQuantity(item.getQuantity() + stockItem.getQuantity());
			log.debug("Found existing item " + stockItem.getName()
					+ " increased quantity by " + stockItem.getQuantity());
		} catch (NoSuchElementException e) {
			try {
				StockItem item = getItemByName(stockItem.getName());
				throw new VerificationFailedException("Name not unique!");
			} catch (NoSuchElementException e1) {
				rows.add(stockItem);

				log.debug("Added " + stockItem.getName() + " quantity of "
						+ stockItem.getQuantity());
			}
		}
		fireTableDataChanged();
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final StockItem stockItem : rows) {
			buffer.append(stockItem.getId() + "\t");
			buffer.append(stockItem.getName() + "\t");
			buffer.append(stockItem.getPrice() + "\t");
			buffer.append(stockItem.getQuantity() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
	
	

}
