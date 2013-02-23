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
import com.tintuna.stockfx.model.PortfolioLoaderTesting;
import com.tintuna.stockfx.persistence.Portfolio;

public class MainApplication extends Application {

	private static final Logger log = LoggerFactory.getLogger(MainApplication.class);
	private static final String PERSISTENCE_UNIT_NAME = "stockFxPU";
	private static EntityManagerFactory factory;
	
	
	private static AppFactory appFactory = new AppFactory();
	private static ModelFactory modelFactory = new ModelFactory();
	
	public static AppFactory getAppFactory() {
		return appFactory;
	}

	public static ModelFactory getModelFactory() {
		return modelFactory;
	}
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(Stage stage) throws Exception {

		startDatabase();
//		String fxmlFile = "/fxml/Main.fxml";
//		log.debug("Loading FXML for main view from: {}", fxmlFile);
//		FXMLLoader loader = new FXMLLoader();
//		Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
		setDependencies();
		Scene scene = new Scene(appFactory.getMainController().getRoot(), 1000, 700);
		scene.getStylesheets().add("/styles/styles.css");
		stage.setTitle("StockFx");
		stage.setScene(scene);
		stage.show();
		
		appFactory.getTabManager().setTabPane(appFactory.getMainController().getTabPane());
		
		appFactory.getTabManager().addNewDocument("Portfolios", appFactory.getPortfolioController().getRoot());
		appFactory.getTabManager().addNewDocument("one", null, null);
		appFactory.getTabManager().addNewDocument("two", null, null);
		appFactory.getTabManager().addNewDocument("three", null, null);
	}


	private void setDependencies() {
		appFactory.setPortfolioLoader(new PortfolioLoaderTesting());
	}

	private void startDatabase() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		Query q = em.createQuery("select t from Portfolio t");
		List<Portfolio> portfolioList = q.getResultList();
		for (Portfolio todo : portfolioList) {
			System.out.println(todo);
		}
		System.out.println("Size: " + portfolioList.size());

		// Create new todo
		em.getTransaction().begin();
		Portfolio todo = new Portfolio("Test persistence","play");
		em.persist(todo);
		em.getTransaction().commit();

		em.close();
	}

	// private static final Random random = new Random();
	//
	// private static void ensureVisible(ScrollPane pane, Node node) {
	// double width = pane.getContent().getBoundsInLocal().getWidth();
	// double height = pane.getContent().getBoundsInLocal().getHeight();
	//
	// double x = node.getBoundsInParent().getMaxX();
	// double y = node.getBoundsInParent().getMaxY();
	//
	// // scrolling values range from 0 to 1
	// pane.setVvalue(y/height);
	// pane.setHvalue(x/width);
	//
	// // just for usability
	// node.requestFocus();
	// }
	//
	// @Override
	// public void start(Stage primaryStage) {
	//
	// final ScrollPane root = new ScrollPane();
	// final Pane content = new Pane();
	// root.setContent(content);
	//
	// // put 10 buttons at random places with same handler
	// final EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
	//
	// public void handle(ActionEvent arg0) {
	// int index = random.nextInt(10);
	// System.out.println("Moving to button " + index);
	// ensureVisible(root, content.getChildren().get(index));
	// }
	// };
	//
	// for (int i = 0; i < 10; i++) {
	// Button btn = new Button("next " + i);
	// btn.setOnAction(handler);
	// content.getChildren().add(btn);
	// btn.relocate(0, 200*i);
	// }
	//
	// Scene scene = new Scene(root, 300, 250);
	// primaryStage.setScene(scene);
	// primaryStage.show();
	//
	// // run once to don't search for a first button manually
	// handler.handle(null);
	// }
}
