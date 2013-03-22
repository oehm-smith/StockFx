package com.tintuna.stockfx.controller;

public abstract class StockFxSubController {

	public void initialize() {
		initializeSubController();
		initializeButtons();
		initializeFields();
	}

	/**
	 * Override this if any special initialization needs to happen in the sub-controller.
	 */
	protected void initializeSubController() {
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
}
