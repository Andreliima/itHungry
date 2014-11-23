package ee.ut.math.tvt.salessystem;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;

public class StockTableModelTest {
	private static final Logger log = Logger.getLogger(StockTableModelTest.class);
	private StockItem item1;
	private StockItem item2;
	private StockTableModel model;
	
	@Before
	public void startUp() {
		item1 = new StockItem(50L, "Karu", "", 50, 2);
	}
	
	@Test(expected = VerificationFailedException.class)
	public void testValidateNameUniqueness() throws VerificationFailedException {
		model = new StockTableModel();
		model.addItem(item1);
		model.addItem(new StockItem(30L, "Karu", "Boo", 30, 4));
	}
	
	@Test(expected = VerificationFailedException.class)
	public void testValidateIdUniqueness() throws VerificationFailedException {
		model = new StockTableModel();
		model.addItem(item1);
		model.addItem(new StockItem(50L, "Porgrand", "", 50, 1));
	}
	
	/*// Pole kindel, mida siin tahetakse.
	@Test
	public void testHasEnoughInStock() {
		assertEquals(model.getRowCount(), 0);
		model.addItem(item1);
		assertEquals(model.getRowCount(), 1);
	}
	*/
	@Test
	public void testGetItemByIdWhenItemExists() throws VerificationFailedException {
		model = new StockTableModel();
		model.addItem(item1);
		assertEquals(model.getItemById(50), item1);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException() throws VerificationFailedException {
		model = new StockTableModel();
		model.addItem(item1);
		model.getItemById(30);
	}
	
}
