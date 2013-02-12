package com.tintuna.stockfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        log.info("Starting Hello JavaFX and Maven demonstration application");

        String fxmlFile = "/fxml/Application.fxml";
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode, 400, 200);
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("Hello JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }
    
//    private static final Random random = new Random();
//
//    private static void ensureVisible(ScrollPane pane, Node node) {
//        double width = pane.getContent().getBoundsInLocal().getWidth();
//        double height = pane.getContent().getBoundsInLocal().getHeight();
//
//        double x = node.getBoundsInParent().getMaxX();
//        double y = node.getBoundsInParent().getMaxY();
//
//        // scrolling values range from 0 to 1
//        pane.setVvalue(y/height);
//        pane.setHvalue(x/width);
//
//        // just for usability
//        node.requestFocus();
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//
//        final ScrollPane root = new ScrollPane();
//        final Pane content = new Pane();
//        root.setContent(content);
//
//        // put 10 buttons at random places with same handler
//        final EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
//
//			public void handle(ActionEvent arg0) {
//				int index = random.nextInt(10);
//                System.out.println("Moving to button " + index);
//                ensureVisible(root, content.getChildren().get(index));				
//			}
//        };
//
//        for (int i = 0; i < 10; i++) {
//            Button btn = new Button("next " + i);
//            btn.setOnAction(handler);
//            content.getChildren().add(btn);
//            btn.relocate(0, 200*i);
//        }
//
//        Scene scene = new Scene(root, 300, 250);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//        // run once to don't search for a first button manually
//        handler.handle(null);
//    }
}
