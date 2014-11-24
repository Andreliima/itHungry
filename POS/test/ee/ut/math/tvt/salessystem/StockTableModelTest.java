package ee.ut.math.tvt.salessystem;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {
	private StockItem item1;
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
		model.addItem(new StockItem(50L, "Porgand", "", 50, 1));
	}
	
	// hasEnoughInStock not testable with current code.
	/*
	@Test
	public void testHasEnoughInStock() throws VerificationFailedException {
		model = new StockTableModel();
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
