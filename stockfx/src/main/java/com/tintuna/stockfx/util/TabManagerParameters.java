package com.tintuna.stockfx.util;

import java.util.HashMap;
import java.util.Map;

import com.tintuna.stockfx.db.QueryParameter;

/**
 * Builder-like pattern to enable setting options to the TabManager intuitively.  You use it like this:
 * TabManager.startParams().insertBefore("some tab name").useSuffix(true);
 * @author bsmith
 *
 */
public class TabManagerParameters {
	/**
	 * tab name suffix to make unique - values t
	 */
	private boolean useSuffix; //
	/**
	 * index to insert tab at. If <=-1 or > size() then the last position, if 0 then the first position, else that index
	 * and push everthing to the right. If null then not set.
	 */
	private Integer insertAt; 
	/**
	 * tab to insert this before. Takes precedence over insertAfter
	 */
	private String insertBefore;
	/**
	 * tab to insert this after. insertBefore takes precedence over this
	 */
	private String insertAfter;
	/**
	 * If a tab with given name exists, open it instead of try to add new one.  Takes precedence over useSuffix. 
	 */
	private boolean openNotAdd;

	public enum Params {
		USE_SUFFIX,
	}

	private TabManagerParameters() {
		useSuffix = false;
		insertAt = null;
		insertBefore = "";
		insertAfter = "";
		openNotAdd = false;
	}

	public static TabManagerParameters startParams() {
		return new TabManagerParameters();
	}

	public TabManagerParameters useSuffix(boolean useSuffix) {
		this.useSuffix = useSuffix;
		return this;
	}

	public TabManagerParameters insertAt(int insertAt) {
		this.insertAt = insertAt;
		return this;
	}

	public TabManagerParameters insertAtBeginning() {
		this.insertAt = 0;
		return this;
	}

	public TabManagerParameters insertAtEnd() {
		this.insertAt = -1;
		return this;
	}

	public TabManagerParameters insertBefore(String insertBefore) {
		this.insertBefore = insertBefore;
		// insertBefore takes precedence so null any insertAfter
		return insertAfter("");
	}

	public TabManagerParameters insertAfter(String insertAfter) {
		if (insertBefore.length() == 0) {
			this.insertAfter = insertAfter;
		} else {
			// insertBefore takes precedence so kill any existing values
			this.insertAfter = "";
		}
		return this;
	}

	public TabManagerParameters openNotAdd(boolean openNotAdd) {
		this.openNotAdd = openNotAdd;
		return this;
	}

	// ---------

	public boolean isUseSuffix() {
		return useSuffix;
	}

	public Integer getInsertAt() {
		return insertAt;
	}

	public String getInsertBefore() {
		return insertBefore;
	}

	public String getInsertAfter() {
		return insertAfter;
	}

	public boolean isOpenNotAdd() {
		return openNotAdd;
	}
}
