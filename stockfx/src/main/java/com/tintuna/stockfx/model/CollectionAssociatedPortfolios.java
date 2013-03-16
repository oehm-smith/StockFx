package com.tintuna.stockfx.model;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.persistence.PCollection;

/**
 * @Entity Collections have @Entity Portfolios (1+). There is one model of @Entity Collections - CollectionsModel; and
 *         there is one PortfoliosModel for each Collection. This class maintains the map mapping each Collection to a
 *         PortfoliosModel
 * 
 * @author bsmith
 * 
 */
public class CollectionAssociatedPortfolios {
	private static final Logger log = LoggerFactory.getLogger(CollectionAssociatedPortfolios.class);

	private static Map<String, PortfoliosModel> collectionsPortfolios = new HashMap<>();

	public static Map<String, PortfoliosModel> getPortfolioStocks() {
		return collectionsPortfolios;
	}

	public static void setPortfolioStocks(Map<String, PortfoliosModel> passedPortfolioStocks) {
		collectionsPortfolios = passedPortfolioStocks;
	}

	// TODO - this will probably fail when stock are added to portfolio in some other way
	public static PortfoliosModel getStockModelForAssociatedPortfolio(PCollection collection) {
		if (collectionsPortfolios.containsKey(collection.getName().toLowerCase())) {
			log.debug("Return Portfolio model for existing mapped collection: " + collection.getName());
			return collectionsPortfolios.get(collection.getName().toLowerCase());
		} else {
			log.debug("Return NEW Portfolio model for new mapped collection: " + collection.getName());
			PortfoliosModel s = new PortfoliosModel(collection);
			collectionsPortfolios.put(collection.getName().toLowerCase(), s);
			return s;
		}
	}
}
