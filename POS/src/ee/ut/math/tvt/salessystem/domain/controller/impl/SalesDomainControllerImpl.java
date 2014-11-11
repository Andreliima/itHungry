package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

import org.hibernate.Session;

/**
 * Implementation of the sales domain controller.
 */
@SuppressWarnings("unchecked")
public class SalesDomainControllerImpl implements SalesDomainController {
	
	private Session session = HibernateUtil.currentSession();
	public void submitCurrentPurchase(List<SoldItem> goods) throws VerificationFailedException {
		// Let's assume we have checked and found out that the buyer is underaged and
		// cannot buy chupa-chups
		//throw new VerificationFailedException("Underaged!");
		// XXX - Save purchase
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {				
		// XXX - Cancel current purchase
	}
	

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {
		// XXX mock implementation
		List<StockItem> dataset = session.createQuery("from StockItem").list();
				
		return dataset;
	}

	public List<HistoryItem> loadHistoryState(){
		List<HistoryItem> dataset = session.createQuery("from HistoryItem").list();
				
		return dataset;
	}
	
	public List<SoldItem> loadSoldItems(){
		List<SoldItem> dataset = session.createQuery("from SoldItem").list();
		return dataset;
	}
	
	
	// End current database session.
	public void endSession() {
	    HibernateUtil.closeSession();
	}
	
}
