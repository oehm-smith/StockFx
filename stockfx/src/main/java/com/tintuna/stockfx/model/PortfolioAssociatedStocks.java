package com.tintuna.stockfx.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.persistence.Portfolio;

/**
 * @Entity Portfolios have @Entity Stocks. There is one model of @Entity Portfolios and its called Portfolios. Each @Entity
 *         Portfolio needs to be associated with a Model of its Stocks and that is the purpose of this.
 * 
 * @author bsmith
 * 
 */
public class PortfolioAssociatedStocks {
	private static final Logger log = LoggerFactory.getLogger(PortfolioAssociatedStocks.class);

	private static Map<String, Stocks> portfolioStocks = new HashMap<>();

	public static Map<String, Stocks> getPortfolioStocks() {
		return portfolioStocks;
	}

	public static void setPortfolioStocks(Map<String, Stocks> passedPortfolioStocks) {
		portfolioStocks = passedPortfolioStocks;
	}

	// TODO - this will probably fail when stock are added to portfolio in some other way
	public static Stocks getStockModelForAssociatedPortfolio(Portfolio portfolio) {
		log.debug("LOOKUP Stocks model for portfolio: "+portfolio.getName());
		if (portfolioStocks.containsKey(portfolio.getName().toLowerCase())) {
			log.debug("Return Stocks model for existing mapped portfolio: "+portfolio.getName());
			return portfolioStocks.get(portfolio.getName().toLowerCase());
		} else {
			log.debug("Return NEW Stocks model for new mapped portfolio: "+portfolio.getName());
			Stocks s = new Stocks(portfolio);
			portfolioStocks.put(portfolio.getName().toLowerCase(), s);
			return s;
		}
	}
}
