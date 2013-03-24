package com.tintuna.stockfx.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tintuna.stockfx.application.MainApplication;
import com.tintuna.stockfx.application.TabStandardNames;
import com.tintuna.stockfx.persistence.PCollection;
import com.tintuna.stockfx.persistence.Portfolio;
import com.tintuna.stockfx.persistence.PortfolioStock;
import com.tintuna.stockfx.ui.table.CollectionsTableConfiguration;
import com.tintuna.stockfx.ui.table.PortfolioTableConfiguration;
import com.tintuna.stockfx.ui.table.StockTableConfiguration;
import com.tintuna.stockfx.util.TabManagerParameters;

public class PortfoliosController extends StockFxBorderPaneController {
	private static final Logger log = LoggerFactory.getLogger(PortfoliosController.class);

	private PortfoliosController.CollectionsSubController collectionsSubController;
	private PortfoliosController.PortfoliosSubController portfoliosSubController;
	private PortfoliosController.StocksSubController stocksSubController;

	@FXML
	private TableView<PCollection> collectionTable;
	@FXML
	private TableView<Portfolio> portfolioTable;
	@FXML
	private VBox stocksVBox;
	@FXML
	private TableView<PortfolioStock> stockTable;
//	private TableView<PortfolioStock> newStockTable;

	// @FXML
	// private GridPane gridPane;
	// @FXML
	// private TabPane tabPane;
	@FXML
	private Button newCollectionButton;
	@FXML
	private Button openCollectionButton;
	@FXML
	private Button newPortfolioButton;
	@FXML
	private Button openPortfolioButton;
	@FXML
	private Button newStockButton;
	@FXML
	private Button openStockButton;
	@FXML
	private Label messageBox;

	// public PortfoliosController() {
	// super();
	// }

	protected void subClassPreConstructor() {
		collectionsSubController = new CollectionsSubController();
		portfoliosSubController = new PortfoliosSubController();
		stocksSubController = new StocksSubController();
	}

	protected void initializeController() {
		collectionsSubController.initialize();
		portfoliosSubController.initialize();
		stocksSubController.initialize();
	}

	@Override
	protected String getFXML() {
		return "/fxml/Portfolios.fxml";
	}

	@Override
	protected void initializeButtons() {
	}

	@Override
	protected void initializeFields() {
	}

	@Override
	public void loadSelectedEntity() {
	}

	@Override
	public void clearCurrentEntity() {
	}

	@Override
	public void controllerDocumentSelected() {
	}

	@Override
	public void controllerDocumentDeselected() {
	}

	// public VBox getPortfolioList() {
	// return portfolioList;
	// }
	//
	// public void setPortfolioList(VBox portfolioList) {
	// this.portfolioList = portfolioList;
	// }

	// public TableView<Portfolio> getPortfolioTable() {
	// return portfolioTable;
	// }
	//
	// public void setPortfolioTable(TableView<Portfolio> portfolioTable) {
	// this.portfolioTable = portfolioTable;
	// }

	// public GridPane getGridPane() {
	// return gridPane;
	// }
	//
	// public void setGridPane(GridPane gridPane) {
	// this.gridPane = gridPane;
	// }

	// public TabPane getTabPane() {
	// return tabPane;
	// }
	//
	// public void setTabPane(TabPane tabPane) {
	// this.tabPane = tabPane;
	// }

	private class CollectionsSubController extends StockFxSubController {
		@Override
		protected void initializeButtons() {
			initializeNewCollectionButton();
			initializeOpenCollectionButton();
		}

		@Override
		protected void initializeFields() {
			initializeCollectionTable();
		}

		private void initializeCollectionTable() {
			CollectionsTableConfiguration.setupTable(collectionTable);

//			TableColumn<PCollection, String> col = new TableColumn<>("Collection");
//			PortfoliosController.this.collectionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//			col.setCellValueFactory(new PropertyValueFactory<PCollection, String>("Name"));
//			col.setText("Name");
//			collectionTable.getColumns().clear();
//			collectionTable.getColumns().add(col);
			initializeCollectionTableData();
			// When a different collection is selected in the list, update the display of portfolios to those that
			// belong to this newly selected one
			collectionTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PCollection>() {
				@Override
				public void changed(ObservableValue<? extends PCollection> arg0, PCollection arg1, PCollection arg2) {
					if (arg2 != null && arg2 instanceof PCollection) {
						MainApplication.getModelFactory().getCollectionsModel().setSelected(arg2);
						MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().setSelected(null);
						newPortfolioButton.setDisable(false);
						portfoliosSubController.setPortfolioItemsFromSelectedCollection();
						openCollectionButton.setDisable(false);
					}
				}
			});

		}

		/**
		 * When the collections data changes, update the displayed collections so any new / renamed / deleted ones are
		 * shown/not shown
		 */
		// TODO - This should be able to be a Property so that this event handler can be removed. The table data and
		// the property are sync'd
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private void initializeCollectionTableData() {
			MainApplication.getModelFactory().getCollectionsModel().addCollectionsListener(new ListChangeListener() {

				@SuppressWarnings("rawtypes")
				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change c) {
					updateCollectionsTableDataItems();
				}

			});
			updateCollectionsTableDataItems();
		}

		protected void updateCollectionsTableDataItems() {
			collectionTable.setItems(MainApplication.getModelFactory().getCollectionsModel().getCollections());
		}

		private void initializeNewCollectionButton() {
			newCollectionButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					newCollectionButtonAction();
				}
			});
		}

		private void newCollectionButtonAction() {
			log.debug("-> newCollectionButtonAction");
			StockFxBorderPaneController controller = MainApplication.getAppFactory().getCollectionsController();
			controller.setControllerState(CONTROLLER_STATE.NEW_DOCUMENT);
			// Set the state and when the tab is activated, it will make a call back to this controller and the
			// fields will be cleared in order to allow a new entity to be created
			MainApplication.getAppFactory().getTabManager().addTabWithNode(TabStandardNames.Collection.name(), controller,
					TabManagerParameters.startParams().insertAfter(TabStandardNames.Portfolios.name()).openNotAdd(true));
		}

		private void initializeOpenCollectionButton() {
			openCollectionButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					openCollectionButtonAction();
				}
			});
			openCollectionButton.setDisable(true);
		}

		private void openCollectionButtonAction() {
			log.debug("-> openCollectionButtonAction");
			StockFxBorderPaneController controller = MainApplication.getAppFactory().getCollectionsController();
			// Set the state and when the tab is activated, it will make a call back to this controller and the
			// entity will be opened to allow it to be viewed/edited
			controller.setControllerState(CONTROLLER_STATE.OPENED_DOCUMENT);
			MainApplication.getAppFactory().getTabManager().addTabWithNode(TabStandardNames.Collection.name(), controller,
					TabManagerParameters.startParams().insertAfter(TabStandardNames.Portfolios.name()).openNotAdd(true));
		}
	}

	private class PortfoliosSubController extends StockFxSubController {
		@Override
		protected void initializeButtons() {
			initializeNewPortfolioButton();
			initializeOpenPortfolioButton();
		}

		@Override
		protected void initializeFields() {
			initializePortfolioTable();
		}

		private void initializePortfolioTable() {
			PortfolioTableConfiguration.setupTable(portfolioTable);

//			TableColumn<Portfolio, String> col = new TableColumn<>("Portfolio");
//			portfolioTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//			col.setCellValueFactory(new PropertyValueFactory<Portfolio, String>("Name"));
//			col.setText("Name");
//			initializePortfolioTableData();
//			portfolioTable.getColumns().clear();
//			portfolioTable.getColumns().add(col);
			// When a different portfolio is selected in the list, update the display of stocks to those that
			// belong to this newly selected one
			portfolioTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Portfolio>() {
				@Override
				public void changed(ObservableValue<? extends Portfolio> arg0, Portfolio arg1, Portfolio arg2) {
					if (arg2 != null && arg2 instanceof Portfolio) {
						MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().setSelected(arg2);
						newStockButton.setDisable(false);
						openPortfolioButton.setDisable(false);
						stocksSubController.setStockItemsFromSelectedPortfolio();
					}
				}
			});
		}

		/**
		 * When the portfolio data changes, update the displayed portfolios so any new / renamed / deleted ones are
		 * shown/not shown
		 */
		// TODO - This should be able to be a Property so that this event handler can be removed. The table data and
		// the property are sync'd
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private void initializePortfolioTableData() {
			// TODO - I don't like the 'if (MainApplication.getModelFactory().getCollectionsModel().getSelected() !=
			// null) ' guard here
			// as that should be encapsulated in the 'SelectedCollection' part of
			// getPortfoliosModelFromSelectedCollection(), but as
			// it stands there is nothing you can do. Twould be nice to rearchitect this to prevent such slopping coding
			if (MainApplication.getModelFactory().getCollectionsModel().getSelected() != null) {
				MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().addPortfoliosListener(new ListChangeListener() {

					@SuppressWarnings("rawtypes")
					@Override
					public void onChanged(javafx.collections.ListChangeListener.Change c) {
						setPortfolioItemsFromSelectedCollection();
					}

				});
			}
			setPortfolioItemsFromSelectedCollection();
		}

		private void initializeNewPortfolioButton() {
			newPortfolioButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					// MainApplication.getModelFactory().getPortfolios().newPortfolio();
					newPortfolio();
				}

				protected void newPortfolio() {
					boolean openSelected = false;
					StockFxBorderPaneController controller = MainApplication.getAppFactory().getPortfolioController();
					// Set the state and when the tab is activated, it will make a call back to this controller and the
					// fields will be cleared in order to allow a new entity to be created
					controller.setControllerState(CONTROLLER_STATE.NEW_DOCUMENT);
					MainApplication.getAppFactory().getTabManager().addTabWithNode(TabStandardNames.Portfolio.name(), controller,
							TabManagerParameters.startParams().insertAfter(TabStandardNames.Portfolios.name()).openNotAdd(true));
				}
			});
			newPortfolioButton.setDisable(true);
		}

		private void initializeOpenPortfolioButton() {
			openPortfolioButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					// MainApplication.getModelFactory().getPortfolios().newPortfolio();
					openPortfolio();
				}

				protected void openPortfolio() {
					boolean openSelected = true;
					StockFxBorderPaneController controller = MainApplication.getAppFactory().getPortfolioController();
					// Set the state and when the tab is activated, it will make a call back to this controller and the
					// entity will be opened to allow it to be viewed/edited
					controller.setControllerState(CONTROLLER_STATE.OPENED_DOCUMENT);

					MainApplication.getAppFactory().getTabManager().addTabWithNode(TabStandardNames.Portfolio.name(), controller,
							TabManagerParameters.startParams().insertAfter(TabStandardNames.Portfolios.name()).openNotAdd(true));
				}
			});
			newPortfolioButton.setDisable(true);
		}

		private void setPortfolioItemsFromSelectedCollection() {
			// TODO - I don't like the 'if (MainApplication.getModelFactory().getCollectionsModel().getSelected() !=
			// null) ' guard here
			// as that should be encapsulated in the 'SelectedCollection' part of
			// getPortfoliosModelFromSelectedCollection(), but as
			// it stands there is nothing you can do. Twould be nice to rearchitect this to prevent such slopping coding
			if (MainApplication.getModelFactory().getCollectionsModel().getSelected() != null) {
				portfolioTable.setItems(MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getPortfolios());
			}
		}
	}

	private class StocksSubController extends StockFxSubController {
		@Override
		protected void initializeButtons() {
			initializeNewStockButton();
			initializeOpenStockButton();
		}

		@Override
		protected void initializeFields() {
			initializeStockTable();
		}

		private void initializeNewStockButton() {
			newStockButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					newStockForPortfolio();
				}

				protected void newStockForPortfolio() {
					StockFxBorderPaneController controller = MainApplication.getAppFactory().getStockController();
					controller.setControllerState(CONTROLLER_STATE.NEW_DOCUMENT);
					// Set the state and when the tab is activated, it will make a call back to this controller and the
					// fields will be cleared in order to allow a new entity to be created
					MainApplication.getAppFactory().getTabManager().addTabWithNode(TabStandardNames.Stock.name(), controller,
							TabManagerParameters.startParams().insertAfter(TabStandardNames.Portfolios.name()).openNotAdd(true));
				}
			});
			newStockButton.setDisable(true);
		}

		private void initializeOpenStockButton() {
			openStockButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					// MainApplication.getModelFactory().getPortfolios().newPortfolio();
					openStock();
				}

				protected void openStock() {
					StockFxBorderPaneController controller = MainApplication.getAppFactory().getStockController();
					// Set the state and when the tab is activated, it will make a call back to this controller and the
					// entity will be opened to allow it to be viewed/edited
					controller.setControllerState(CONTROLLER_STATE.OPENED_DOCUMENT);

					MainApplication.getAppFactory().getTabManager().addTabWithNode(TabStandardNames.Stock.name(), controller,
							TabManagerParameters.startParams().insertAfter(TabStandardNames.Portfolios.name()).openNotAdd(true));
				}
			});
			openPortfolioButton.setDisable(true);
		}

		/**
		 * When the stock data changes, update the displayed collections so any new / renamed / deleted onesare
		 * shown/not shown
		 */
		// TODO - This should be able to be a Property so that this event handler can be removed. The table data and
		// the property are sync'd
		@SuppressWarnings("unchecked")
		private void initializeStockTable() {
			StockTableConfiguration.setupTable(stockTable);
			initializeStockTableData(stockTable);
			// When a different stock is selected, just make it the selected
			stockTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PortfolioStock>() {
				@Override
				public void changed(ObservableValue<? extends PortfolioStock> arg0, PortfolioStock arg1, PortfolioStock arg2) {
					log.debug("-> initializeStockTable - changed: "+arg2);
					if (arg2 != null && arg2 instanceof PortfolioStock) {
						MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio().setSelected(arg2);
						openStockButton.setDisable(false);
					}
				}
			});
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private void initializeStockTableData(final TableView<PortfolioStock> table) {
			// TODO - I don't like the 'if (MainApplication.getModelFactory().getCollectionsModel().getSelected() !=
			// null) ' guards here (YES, I HAVE TO HAVE TWO AT THIS LOWER LEVEL)
			// as that should be encapsulated in the 'SelectedCollection' part of
			// getPortfoliosModelFromSelectedCollection(), but as
			// it stands there is nothing you can do. Twould be nice to rearchitect this to prevent such slopping coding
			if (MainApplication.getModelFactory().getCollectionsModel().getSelected() != null) {
				if (MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelected() != null) {
					MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio().addStocksListener(new ListChangeListener() {

						@SuppressWarnings("rawtypes")
						@Override
						public void onChanged(javafx.collections.ListChangeListener.Change c) {
							setStockItemsFromSelectedPortfolio();
						}
					});
				}
			}
			setStockItemsFromSelectedPortfolio();
		}

		private void setStockItemsFromSelectedPortfolio() {
			// TODO - I don't like the 'if (MainApplication.getModelFactory().getCollectionsModel().getSelected() !=
			// null) ' guards here (YES, I HAVE TO HAVE TWO AT THIS LOWER LEVEL)
			// as that should be encapsulated in the 'SelectedCollection' part of
			// getPortfoliosModelFromSelectedCollection(), but as
			// it stands there is nothing you can do. Twould be nice to rearchitect this to prevent such slopping coding
			Portfolio selectedPortfolio = null;
			if (MainApplication.getModelFactory().getCollectionsModel().getSelected() != null) {
				selectedPortfolio = MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelected();
			}
			log.debug(String.format("setStockItemsFromSelectedPortfolio - selected collection: %s, selected portfolio: %s", MainApplication.getModelFactory().getCollectionsModel().getSelected(),
					selectedPortfolio));
			//
			if (MainApplication.getModelFactory().getCollectionsModel().getSelected() != null) {
				if (MainApplication.getModelFactory().getPortfoliosModelFromSelectedCollection().getSelected() != null) {
					log.debug(String.format("setStockItemsFromSelectedPortfolio - setItems(%s)", MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio().getStocks()));
					stockTable.setItems(MainApplication.getModelFactory().getStocksModelFromSelectedPortfolio().getStocks());
				}
			}
		}
	}

	@Override
	public void newEntity() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSelectedEntity() {
		// TODO Auto-generated method stub

	}
}
