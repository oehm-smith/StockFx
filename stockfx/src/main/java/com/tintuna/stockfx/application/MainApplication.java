package com.tintuna.stockfx.application;

import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.model.ModelFactory;
import com.tintuna.stockfx.persistence.Portfolio;
import com.tintuna.stockfx.persistence.Stock;

public class MainApplication extends Application {

	private static final Logger log = LoggerFactory.getLogger(MainApplication.class);
	private static final String PERSISTENCE_UNIT_NAME = "stockFxPU";
	private static EntityManagerFactory factory;
	
	
	private static AppFactory appFactory = new AppFactory();
	private static ModelFactory modelFactory = new ModelFactory();
	private static ServiceFactory serviceFactory = new ServiceFactory();
	
	public static AppFactory getAppFactory() {
		return appFactory;
	}

	public static ModelFactory getModelFactory() {
		return modelFactory;
	}
	
	public static ServiceFactory getServiceFactory() {
		return serviceFactory;
	}
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		databaseDebugPrintout();
		setDependencies();
		Scene scene = new Scene(appFactory.getMainController().getRoot(), 1000, 700);
		scene.getStylesheets().add("/styles/styles.css");
		stage.setTitle("StockFx");
		stage.setScene(scene);
		stage.show();
		
		appFactory.getTabManager().setTabPane(appFactory.getMainController().getTabPane());
		
		appFactory.getTabManager().addNewDocument("Portfolios", appFactory.getPortfolioController().getRoot());
		appFactory.getTabManager().addNewDocument("one", "");
		appFactory.getTabManager().addNewDocument("two", "");
		appFactory.getTabManager().addNewDocument("three", "");
	}


	private void setDependencies() {
	}

	public static void databaseDebugPrintout() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		// Read the existing entries and write to console
		Query q = em.createQuery("select t from Portfolio t");
		List<Portfolio> portfolioList = q.getResultList();
		for (Portfolio todo : portfolioList) {
			System.out.println(todo);
		}
		System.out.println("Size: " + portfolioList.size());

		Query q2 = em.createQuery("select s from Stock s");
		List<Stock> stockList = q2.getResultList();
		for (Stock stock : stockList) {
			System.out.println(stock);
		}
		System.out.println("Size: " + stockList.size());
		em.getTransaction().commit();
		em.close();
	}
}
