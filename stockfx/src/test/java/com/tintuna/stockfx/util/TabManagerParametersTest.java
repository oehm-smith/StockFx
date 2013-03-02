package com.tintuna.stockfx.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TabManagerParametersTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test01() {
		TabManagerParameters tmp = TabManagerParameters.startParams().insertAtBeginning();
		assertEquals("", tmp.getInsertAfter());
		assertEquals("", tmp.getInsertBefore());
		assertEquals(new Integer(0), tmp.getInsertAt());
		assertEquals(false, tmp.isUseSuffix());
	}
	
	@Test
	public void test02() {
		TabManagerParameters tmp = TabManagerParameters.startParams().insertAtEnd();
		assertEquals("", tmp.getInsertAfter());
		assertEquals("", tmp.getInsertBefore());
		assertEquals(new Integer(-1), tmp.getInsertAt());
		assertEquals(false, tmp.isUseSuffix());
	}

	@Test
	public void test03() {
		TabManagerParameters tmp = TabManagerParameters.startParams().insertAt(1);
		assertEquals("", tmp.getInsertAfter());
		assertEquals("", tmp.getInsertBefore());
		assertEquals(new Integer(1), tmp.getInsertAt());
		assertEquals(false, tmp.isUseSuffix());
	}
	
	@Test
	public void test04() {
		TabManagerParameters tmp = TabManagerParameters.startParams().insertAfter("tab 1");
		assertEquals("tab 1", tmp.getInsertAfter());
		assertEquals("", tmp.getInsertBefore());
		assertEquals(null, tmp.getInsertAt());
		assertEquals(false, tmp.isUseSuffix());
	}
	
	@Test
	public void test05() {
		TabManagerParameters tmp = TabManagerParameters.startParams().insertBefore("tab 1");
		assertEquals("", tmp.getInsertAfter());
		assertEquals("tab 1", tmp.getInsertBefore());
		assertEquals(null, tmp.getInsertAt());
		assertEquals(false, tmp.isUseSuffix());
	}
	
	@Test
	public void test06() {
		TabManagerParameters tmp = TabManagerParameters.startParams().insertBefore("tab 1").insertAfter("tab 2");
		assertEquals("", tmp.getInsertAfter());
		assertEquals("tab 1", tmp.getInsertBefore());
		assertEquals(null, tmp.getInsertAt());
		assertEquals(false, tmp.isUseSuffix());
	}
	
	@Test
	public void test07() {
		TabManagerParameters tmp = TabManagerParameters.startParams().insertAfter("tab 2").insertBefore("tab 1");
		assertEquals("", tmp.getInsertAfter());
		assertEquals("tab 1", tmp.getInsertBefore());
		assertEquals(null, tmp.getInsertAt());
		assertEquals(false, tmp.isUseSuffix());
	}
}
