package ee.ut.math.tvt.salessystem.ui.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/*
 * Moved this class to *.ui.model package to be able to access protected classes.
 */
public class PurchaseInfoTableModelTest {
	
	private PurchaseInfoTableModel model;
	private StockItem stItem1;
	private StockItem stItem2;
	private SoldItem soItem1;
	private SoldItem soItem2;
	
	
	@Before
	public void startUp() {
		stItem1 = new StockItem(50L,"Janes","Odav",5,1);
		stItem2 = new StockItem(40L, "Pann", "", 4, 1);
		soItem1 = new SoldItem(stItem1, 2);
		soItem2 = new SoldItem(stItem2, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidColumnValueThrowsException() {
		model = new PurchaseInfoTableModel();
		model.addItem(soItem1);
		model.getColumnValue(soItem1, 5);
	}
	
	@Test
	public void testGetColumnValues() {
		model = new PurchaseInfoTableModel();
		assertEquals("Janes", model.getColumnValue(soItem1, 1));
		assertEquals(5.0, (double)model.getColumnValue(soItem1, 2), 0.001);
		assertEquals(2, (int)model.getColumnValue(soItem1, 3));
		assertEquals(10.0, (double)model.getColumnValue(soItem1, 4), 0.001);
	}
	
	@Test
	public void testTotalCostZeroItems() {
		model = new PurchaseInfoTableModel();
		assertEquals(0.0, model.totalCost(), 0.0001);
	}

	@Test
	public void testTotalCostMultipleItems() {
		model = new PurchaseInfoTableModel();
		model.addItem(soItem1);
		model.addItem(soItem2);
		assertEquals(14.0, model.totalCost(), 0.0001);
	}
}
	