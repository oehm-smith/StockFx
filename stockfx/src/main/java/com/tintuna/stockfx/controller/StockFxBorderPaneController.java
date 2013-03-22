package com.tintuna.stockfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

/**
 * Abstract class to use as the base of FXML controllers. In the subclasses of this define all the @FXML attributes
 * based on the fx:id attribute in the .fxml files.
 * 
 * The abstract method getFXML(); is used to return the path (String) to the fxml file.
 * 
 * The event handlers, setup binding and synchronization etc are defined in the abstract methods: initializeButtons();
 * initializeFields();
 * 
 * These controllers can be used to insert the contents into any FXML container using the getRoot() method:
 * 
 * Tab newTab = new Tab(tabName); newTab.setContent(controller.getRoot());
 * 
 * These controllers can be re-used - call clear() to clear any existing values. Or these controllers can be used to
 * load an existing item - call loadSelected(), where selected is defined elsewhere in the application.
 * 
 * @author bsmith
 * 
 */
public abstract class StockFxBorderPaneController extends BorderPane implements Initializable {
	protected static final long NOTHING_SELECTED_ID = -1;
	/**
	 * Assign the selectedId to the Id of any item that is opened in the page for viewing / editing. If the page is
	 * opened to create a new item then the selectedId should be set to NOTHING_SELECTED_ID
	 */
	protected long selectedId = NOTHING_SELECTED_ID;

	/**
	 * The .fxml file should have a fx:root BorderPane with an fx:id="root". Eg: <fx:root
	 * type="javafx.scene.layout.BorderPane" fx:id="root" prefHeight="..." prefWidth="..."
	 * xmlns:fx="http://javafx.com/fxml">
	 */
	@FXML
	protected Parent root;

	/**
	 * The .fxml file should have a fx:root BorderPane with an fx:id="root". Eg: <fx:root
	 * type="javafx.scene.layout.BorderPane" fx:id="root" prefHeight="..." prefWidth="..."
	 * xmlns:fx="http://javafx.com/fxml">
	 * 
	 * @return
	 */
	public Parent getRoot() {
		return root;
	}

	/**
	 * Maintain the state of the controller, whether it is currently showing content and how that content came to be
	 * (loaded existing entity/document or a new one coming into being).
	 * 
	 * STARTED - The controller has been created but it doesn't hold anything yet
	 * NEW_DOCUMENT - The controller is loaded with a new document / entity to be created
	 * OPEN_DOCUMENT -  The controller is loaded with an existing document / entity to be viewed or edited
	 * FINISHED	- The controller is finished with a document/entity and may load another.  This state is not required to open/new a different document/entity.
	 * 
	 * @author bsmith
	 * 
	 */
	enum CONTROLLER_STATE {
		STARTED,		// The controller has been created but it doesn't hold anything yet
		NEW_DOCUMENT, 	// The controller is loaded with a new document / entity to be created
		OPENED_DOCUMENT, 	// The controller is loaded with an existing document / entity to be viewed or edited
		FINISHED		// The controller is finished with a document/entity and may load another
	}

	private CONTROLLER_STATE controllerState = CONTROLLER_STATE.STARTED;
	
	protected void setControllerState(CONTROLLER_STATE controllerState) {
		this.controllerState = controllerState;
	}
	
	protected CONTROLLER_STATE getControllerState() {
		return controllerState;
	}
	
	/**
	 * Return location and name of the fxml file.
	 */
	protected abstract String getFXML();

	/**
	 * Constructor loads the fxml specified in getFXML() and sets this as the root and controller.
	 */
	public StockFxBorderPaneController() {
		subClassPreConstructor();
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(getClass().getResource(getFXML()));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(String.format("Unable to load fxml file '%s'", getFXML()));
		}
	}

	/**
	 * Subclasses can Override this to perform any initialization that needs to be done before the fxml is loaded in the
	 * constructor. Any Sub-Controllers should be created here.
	 */
	protected void subClassPreConstructor() {
		// override in client if required
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeController();
		initializeButtons();
		initializeFields();
	}

	/**
	 * Override this if any special initialization needs to happen in the controller. If the Controller has any
	 * Sub-Controller's (these will extend StockFxSubController), this is where their initialize() method is called.
	 */
	protected void initializeController() {
	};

	/**
	 * Define this to initialize any buttons in the fxml that are controlled by this sub-controller.
	 */
	protected abstract void initializeButtons();

	/**
	 * Define this to initialize any fields in the fxml that are controlled by this sub-controller, such as with events
	 * or to setup binding and synchronization.
	 */
	protected abstract void initializeFields();

	/**
	 * Define this to create a new entity that the Controller represents.
	 */
	public abstract void newEntity();

	/**
	 * Define this to load the selected entity (set up and handled elsewhere defined by the application) into this
	 * page's fxml.
	 */
	public abstract void loadSelectedEntity();

	/**
	 * For when the fxml is being used to create a new entity, any existing values need to be cleared.
	 */
	public abstract void clearCurrentEntity();

	/**
	 * Delete the entity that the controller is currently controlling and which is selected in the UI.
	 */
	public abstract void deleteSelectedEntity();

	/**
	 * For when the UI element that is represented by the controller (such as a tab pane, window, etc...) is selected.
	 * This gives the Controller a chance to load new or selected information, clear it etc..
	 */
	public abstract void controllerDocumentSelected();

	/**
	 * For when the UI element that is represented by the controller (such as a tab pane, window, etc...) is deselected.
	 * This gives the Controller a chance to unload the fxml, persist it etc..
	 */
	public abstract void controllerDocumentDeselected();

}
