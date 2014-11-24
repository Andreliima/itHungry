package ee.ut.math.tvt.salessystem;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class HistoryItemTest {
	
	private StockItem stockItem1;
	private StockItem stockItem2; 
	private SoldItem soldItem1;
	private SoldItem soldItem2; 
	
	
	@Before
	public void startUp() {
		stockItem1 = new StockItem(30L, "Porgand", "Magus", 5.5);
		stockItem2 = new StockItem(40L, "Kapsas", "Hapu", 10);
		soldItem1 = new SoldItem(stockItem1, 1);
		soldItem2 = new SoldItem(stockItem2, 1);
	}
	
	@Test
	public void testAddSoldItem() {
		HistoryItem historyItem = new HistoryItem(50L, "15-09-2014", "23:40:12");
		assertEquals(0, historyItem.getRowCount());
		historyItem.addSoldItem(soldItem1);
		assertEquals(historyItem.totalCost(), soldItem1.getSum(), 0.0001);
		assertEquals(1, historyItem.getRowCount());
	}
	
	
	@Test
	public void testGetSumWithNoItems() {
		HistoryItem historyItem = new HistoryItem(50L, "15-09-2014", "23:40:12");
		assertEquals(0, historyItem.totalCost(), 0.0001);
	}
	
	
	@Test
	public void testGetSumWithOneItem() {
		List<SoldItem> items = new ArrayList<SoldItem>();
		items.add(soldItem1);
		HistoryItem historyItem = new HistoryItem(50L, "15-09-2014", "23:40:12", items);
		assertEquals(5.5, historyItem.totalCost(), 0.0001);
	}
	
	@Test
	public void testGetSumWithMultipleItems() {
		List<SoldItem> items = new ArrayList<SoldItem>();
		items.add(soldItem1);
		items.add(soldItem2);
		HistoryItem historyItem = new HistoryItem(50L, "15-09-2014", "23:40:12", items);
		assertEquals(15.5, historyItem.totalCost(), 0.0001);
		
	}
	
}
