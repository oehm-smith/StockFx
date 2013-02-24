package com.tintuna.stockfx.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.tintuna.stockfx.persistence.Portfolio;

public class PortfolioLoaderDB implements PortfolioLoader {
	private static final String PERSISTENCE_UNIT_NAME = "stockFxPU";

	public PortfolioLoaderDB() {
	}

	@Override
	public List<com.tintuna.stockfx.model.Portfolio> loadPortfoliosData() {
		// Read the existing entries and write to console
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("select t from Portfolio t");
		List<Portfolio> portfolioList = q.getResultList();
		em.close();
		List<com.tintuna.stockfx.model.Portfolio> portfolioModels = new ArrayList<>();
		for (Portfolio persistencePortfolio : portfolioList) {
			com.tintuna.stockfx.model.Portfolio portfolio =
					new com.tintuna.stockfx.model.Portfolio(persistencePortfolio.getName(),
							persistencePortfolio.getType());
			portfolioModels.add(portfolio);
		}
		
		return portfolioModels;
	}

	@Override
	public void savePortfolioData(List<com.tintuna.stockfx.model.Portfolio> portfolios) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		for (com.tintuna.stockfx.model.Portfolio p : portfolios) {
			Portfolio port = new Portfolio(p.getName(), p.getType());
			em.persist(port);
		}
		em.getTransaction().commit();
		em.close();
	}

}
