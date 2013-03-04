package com.tintuna.stockfx.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import org.junit.Before;
import org.junit.Test;

import com.tintuna.stockfx.exception.StockFxException;
import com.tintuna.stockfx.util.TabManagerParameters;

public class TabManagerTest {
	TabManager tabManager = null;

	@Before
	public void setUp() throws Exception {
		tabManager = new TabManager(new TabPane());
	}

	// tabs have unique names - if already have one with given name tab not created unless 'suffix if exists' options
	// used
	@Test(expected = StockFxException.class)
	public void uniqueNameException() {
		String tabName = "same name";
		tabManager.addTabWithNode(tabName, new BorderPane());
		tabManager.addTabWithNode(tabName, new BorderPane());
		assertEquals(5, tabManager.getTabPane().getTabs().size());
	}

	@Test
	public void uniqueNameExceptionCatchException() {
		String tabName = "same name";
		tabManager.addTabWithNode(tabName, new BorderPane());
		try {
			tabManager.addTabWithNode(tabName, new BorderPane());
		} catch (StockFxException e) {
			// ignore
		}
		assertEquals(1, tabManager.getTabPane().getTabs().size());
	}

	@Test
	public void uniqueNameUseSuffix() {
		String tabName = "same name";
		tabManager.addTabWithNode(tabName, new BorderPane());
		tabManager.addTabWithNode(tabName, new BorderPane(), TabManagerParameters.startParams().useSuffix(true));
		assertEquals(2, tabManager.getTabPane().getTabs().size());
		assertNotSame("tab names shouldn't be same name.", tabManager.getTabPane().getTabs().get(0), tabManager
				.getTabPane().getTabs().get(1));
	}

	@Test
	public void uniqueNameDontUseSuffix() {
		String tabName = "one name";
		String tabName2 = "two name";
		tabManager.addTabWithNode(tabName, new BorderPane());
		tabManager.addTabWithNode(tabName2, new BorderPane(), TabManagerParameters.startParams().useSuffix(true));
		assertEquals(2, tabManager.getTabPane().getTabs().size());
		assertNotSame("tab names shouldn't be same name.", tabManager.getTabPane().getTabs().get(0), tabManager
				.getTabPane().getTabs().get(1));
	}

	// can insert tab before or after a given tab (name). Default is at the end.
	// @Test
	public void insertTabAtEndDefault() {
		// covered by previous tests
		assertTrue(true);
	}

	@Test
	public void insertTabAtEndExplicitly() {
		String tabName = "one name";
		String tabName2 = "two name";
		tabManager.addTabWithNode(tabName, new BorderPane());
		tabManager.addTabWithNode(tabName2, new BorderPane(), TabManagerParameters.startParams().insertAtEnd());
		assertEquals(2, tabManager.getTabPane().getTabs().size());
		assertNotSame("tab names shouldn't be same name.", tabManager.getTabPane().getTabs().get(0), tabManager
				.getTabPane().getTabs().get(1));
	}

	@Test
	public void insertTabBeforeFirst() {
		// using tab name
		String tabName1 = "tab 1";
		String tabName2 = "tab 2";
		String tabName3 = "tab 3";
		String tabName4 = "tab 4";
		tabManager.addTabWithNode(tabName1, new BorderPane());
		tabManager.addTabWithNode(tabName2, new BorderPane());
		tabManager.addTabWithNode(tabName3, new BorderPane());
		tabManager
				.addTabWithNode(tabName4, new BorderPane(), TabManagerParameters.startParams().insertBefore(tabName1));
		assertEquals(4, tabManager.getTabPane().getTabs().size());
	}

	@Test
	public void insertTabAfterFirst() {
		// using tab name
		String tabName1 = "tab 1";
		String tabName2 = "tab 2";
		String tabName3 = "tab 3";
		String tabName4 = "tab 4";
		tabManager.addTabWithNode(tabName1, new BorderPane());
		tabManager.addTabWithNode(tabName2, new BorderPane());
		tabManager.addTabWithNode(tabName3, new BorderPane());
		tabManager.addTabWithNode(tabName4, new BorderPane(), TabManagerParameters.startParams().insertAfter(tabName1));
		assertEquals(4, tabManager.getTabPane().getTabs().size());
	}

	@Test
	public void insertTabBeforeLast() {
		// using tab name
		String tabName1 = "tab 1";
		String tabName2 = "tab 2";
		String tabName3 = "tab 3";
		String tabName4 = "tab 4";
		tabManager.addTabWithNode(tabName1, new BorderPane());
		tabManager.addTabWithNode(tabName2, new BorderPane());
		tabManager.addTabWithNode(tabName3, new BorderPane());
		tabManager
				.addTabWithNode(tabName4, new BorderPane(), TabManagerParameters.startParams().insertBefore(tabName3));
		assertEquals(4, tabManager.getTabPane().getTabs().size());
	}

	@Test
	public void insertTabAfterLast() {
		// using tab name
		String tabName1 = "tab 1";
		String tabName2 = "tab 2";
		String tabName3 = "tab 3";
		String tabName4 = "tab 4";
		tabManager.addTabWithNode(tabName1, new BorderPane());
		tabManager.addTabWithNode(tabName2, new BorderPane());
		tabManager.addTabWithNode(tabName3, new BorderPane());
		tabManager.addTabWithNode(tabName4, new BorderPane(), TabManagerParameters.startParams().insertAfter(tabName3));
		assertEquals(4, tabManager.getTabPane().getTabs().size());
	}

	// using explicit index; and it will insert in the first spot (0)
	@Test
	public void insertTabBeforeFirstOne() {
		// using tab index
		String tabName1 = "tab 1";
		String tabName2 = "tab 2";
		String tabName3 = "tab 3";
		String tabName4 = "tab 4";
		tabManager.addTabWithNode(tabName1, new BorderPane());
		tabManager.addTabWithNode(tabName2, new BorderPane());
		tabManager.addTabWithNode(tabName3, new BorderPane());
		tabManager.addTabWithNode(tabName4, new BorderPane(), TabManagerParameters.startParams().insertAt(-2));
		assertEquals(4, tabManager.getTabPane().getTabs().size());
		// assert first tab is 4
//		assertEquals(new Integer(0), tabManager.getTabNames().get(tabName4));
		assertEquals(new Integer(0), new Integer(tabManager.getIndexOfTabWithName(tabName4)));
	}

	// using explicit index; and it will insert in the last spot (size()+1)
	@Test
	public void insertTabAfterLastOne() {
		// using tab index
		String tabName1 = "tab 1";
		String tabName2 = "tab 2";
		String tabName3 = "tab 3";
		String tabName4 = "tab 4";
		tabManager.addTabWithNode(tabName1, new BorderPane());
		tabManager.addTabWithNode(tabName2, new BorderPane());
		tabManager.addTabWithNode(tabName3, new BorderPane());
		tabManager.addTabWithNode(tabName4, new BorderPane(), TabManagerParameters.startParams().insertAt(91));
		assertEquals(4, tabManager.getTabPane().getTabs().size());
		// assert last tab is 4
//		assertEquals(new Integer(3), tabManager.getTabNames().get(tabName4));
		assertEquals(new Integer(3), new Integer(tabManager.getIndexOfTabWithName(tabName4)));
			}

	@Test
	public void testNameCaseInsensitivity() {
		String tabName1 = "tab one";
		String tabName1MC = "tAb oNe";
		boolean haveException = false;
		tabManager.addTabWithNode(tabName1, new BorderPane());
		try {
			tabManager.addTabWithNode(tabName1MC, new BorderPane());
		} catch (StockFxException e) {
			haveException = true;
			// ignore otherwise
		}
		assertTrue(haveException);
		assertEquals(1, tabManager.getTabPane().getTabs().size());
	}

	// When you have two tabs and insert 1 inbetween (ie. before the 2nd or after the 1st) then the reference by name to
	// the now 3rd is still correct and to the third and not the 2nd anymore.
	@Test
	public void testInsertBeforeExistingTab_ReferenceByNameStillCorrect() {
		String tabName1 = "tab one";
		String tabName2 = "tab two";
		String tabName3 = "tab three";
		BorderPane borderPane1 = new BorderPane();
		borderPane1.getChildren().add(new Button(tabName1));
		BorderPane borderPane2 = new BorderPane();
		borderPane1.getChildren().add(new Button(tabName2));
		BorderPane borderPane3 = new BorderPane();
		borderPane1.getChildren().add(new Button(tabName3));
		tabManager.addTabWithNode(tabName1, borderPane1);
		tabManager.addTabWithNode(tabName2, borderPane2);
		// preliminary confirmation
		assertEquals(new Integer(0), new Integer(tabManager.getIndexOfTabWithName(tabName1)));
		assertEquals(new Integer(1), new Integer(tabManager.getIndexOfTabWithName(tabName2)));
		tabManager.addTabWithNode(tabName3, borderPane3, TabManagerParameters.startParams().insertBefore(tabName2));
		// now test the tab that was in 2nd place is now in 3rd and the new one is in 2nd place
		assertEquals(new Integer(1), new Integer(tabManager.getIndexOfTabWithName(tabName3)));
		assertEquals(new Integer(2), new Integer(tabManager.getIndexOfTabWithName(tabName2)));

	}
}
