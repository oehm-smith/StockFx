package com.tintuna.stockfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.controller.AppFactory;
import com.tintuna.stockfx.controller.MainController;
import com.tintuna.stockfx.controller.TabManager;

public class MainApp extends Application {

	private static final Logger log = LoggerFactory.getLogger(MainApp.class);
	
	
	private AppFactory controllerFactory = new AppFactory();


	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(Stage stage) throws Exception {

		log.info("Starting Hello JavaFX and Maven demonstration application");

//		startDatabase();
//		String fxmlFile = "/fxml/Main.fxml";
//		log.debug("Loading FXML for main view from: {}", fxmlFile);
//		FXMLLoader loader = new FXMLLoader();
//		Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
		Scene scene = new Scene(controllerFactory.getMainController().getRoot(), 1000, 700);
		scene.getStylesheets().add("/styles/styles.css");
		stage.setTitle("StockFx");
		stage.setScene(scene);
		stage.show();
		
		controllerFactory.getTabManager().setTabPane(controllerFactory.getMainController().getTabPane());
		
		controllerFactory.getTabManager().addNewDocument("Portfolios", controllerFactory.getPortfolioController().getRoot());
		controllerFactory.getTabManager().addNewDocument("one", null, null);
		controllerFactory.getTabManager().addNewDocument("two", null, null);
		controllerFactory.getTabManager().addNewDocument("three", null, null);
	}

//	private void startDatabase() {
//	private static final String PERSISTENCE_UNIT_NAME = "stockFxPU";
//	private static EntityManagerFactory factory;
//		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//		EntityManager em = factory.createEntityManager();
//		// Read the existing entries and write to console
//		Query q = em.createQuery("select t from Portfolio t");
//		List<Portfolio> portfolioList = q.getResultList();
//		for (Portfolio todo : portfolioList) {
//			System.out.println(todo);
//		}
//		System.out.println("Size: " + portfolioList.size());
//
//		// Create new todo
//		em.getTransaction().begin();
//		Portfolio todo = new Portfolio();
//		todo.setName("Home Portfolio " + Math.random() * 20);
//		todo.setType("Personal");
//		em.persist(todo);
//		em.getTransaction().commit();
//
//		em.close();
//	}

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
