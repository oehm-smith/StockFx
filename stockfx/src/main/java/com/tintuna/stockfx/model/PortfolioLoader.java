package com.tintuna.stockfx.model;

import java.util.List;

public interface PortfolioLoader {
	public List<Portfolio> loadPortfoliosData();
	
	public void savePortfolioData(List<Portfolio> portfolios);
}
