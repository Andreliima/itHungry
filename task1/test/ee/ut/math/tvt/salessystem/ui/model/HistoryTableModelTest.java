package ee.ut.math.tvt.salessystem.ui.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class HistoryTableModelTest {

	
	// Get stock item by name && getStockItemById
	
	private HistoryTableModel model;
	private HistoryItem historyItem1;
	private HistoryItem historyItem2;
	private SoldItem soldItem1;
	private SoldItem soldItem2;
	private StockItem stockItem1;
	private StockItem stockItem2;
	private String[] headers = new String[]{"Date", "Time", "Total cost"};
	
	@Before
	public void setUp() {
		model = new HistoryTableModel();
		stockItem1 = new StockItem(5L, "Pirukas", "", 10);
		stockItem2 = new StockItem(6L, "Janes", "", 5);
		soldItem1 = new SoldItem(stockItem1, 1);
		soldItem2 = new SoldItem(stockItem2, 1);
		historyItem1 = new HistoryItem(10L, "5-5-2014", "21:04:40");
		historyItem2 = new HistoryItem(11L, "6-5-2014", "12:04:03");
		historyItem1.addSoldItem(soldItem1);
		historyItem2.addSoldItem(soldItem2);
		List<HistoryItem> items = new ArrayList<HistoryItem>();
		items.add(historyItem1);
		items.add(historyItem2);
		model.setRows(items);
	}
	
	@Test
	public void testGetColumnName() {
		for (int i=0; i<3; i++) {
			assertEquals(headers[i],model.getColumnName(i));
		}
	}
	
	@Test
	public void testGetValueAt() {
		assertEquals("21:04:40", model.getValueAt(0, 1));
		assertEquals("6-5-2014", model.getValueAt(1, 0));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetValueAtOutOfArrayThrowException() {
		model.getValueAt(2, 1);
	}
	
	@Test
	public void testGetColumnValue() {
		assertEquals("5-5-2014", model.getColumnValue(historyItem1, 0));
		assertEquals("21:04:40", model.getColumnValue(historyItem1, 1));
		assertEquals(10.0, (double)model.getColumnValue(historyItem1, 2), 0.0001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetColumnValueThrowException() {
		model.getColumnValue(historyItem1, 3);
	}
}
