package com.tintuna.stockfx.model;

import java.util.ArrayList;
import java.util.List;

public class PortfolioLoaderTesting implements PortfolioLoader {

	@Override
	public List<Portfolio> loadPortfoliosData() {
		final List<Portfolio> data = new ArrayList<>();
		for (Integer i : new int[] { 1, 2, 3 }) {
			data.add(new Portfolio("Portfolio " + i.toString(), "Home"));
		}
		return data;
	}

	@Override
	public void savePortfolioData(List<Portfolio> portfolios) {
		// TODO Auto-generated method stub
		
	}

}
