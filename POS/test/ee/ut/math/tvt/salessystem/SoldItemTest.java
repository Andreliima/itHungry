package ee.ut.math.tvt.salessystem;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemTest {
	
	private StockItem stockItem;
	
	@Before
	public void startUp() {
		stockItem = new StockItem(30L, "Porgand", "Magus", 5.5, 2);
	}
	
	@Test
	public void testGetSum() {
		SoldItem soldItem = new SoldItem(stockItem, 2);
		assertEquals(soldItem.getSum(), 11.0, 0.001);
	}
	
	@Test
	public void testGetSumWithZeroQuantity() {
		SoldItem soldItem = new SoldItem(stockItem, 0);
		assertEquals(soldItem.getSum(), 0, 0.0001);
		
	}
}
