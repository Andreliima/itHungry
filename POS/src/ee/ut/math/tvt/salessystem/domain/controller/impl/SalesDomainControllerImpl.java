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
import org.hibernate.dialect.FirebirdDialect;

import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
/**
 * Implementation of the sales domain controller.
 */
@SuppressWarnings("unchecked")
public class SalesDomainControllerImpl implements SalesDomainController {
	private SalesSystemModel model;
	private Session session = HibernateUtil.currentSession();
	public void submitCurrentPurchase(List<SoldItem> goods) throws VerificationFailedException {
		
		
		session.beginTransaction();
		try {
			

		double sum = 0;
		
		for (SoldItem soldItem : goods) {
			sum += soldItem.getSum();
		}
		
		HistoryItem historyItem = new HistoryItem(sum);
		
		session.saveOrUpdate(historyItem);
		
		Long saleId = historyItem.getId();
		
		for (SoldItem soldItem : goods) {
			soldItem.setSaleId(saleId);
			session.saveOrUpdate(soldItem);
		}

		session.getTransaction().commit();
		session.clear();
		} catch (Exception e){
			session.getTransaction().rollback();
		}
			
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
