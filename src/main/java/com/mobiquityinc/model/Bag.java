package com.mobiquityinc.model;

import java.util.List;

public class Bag {
	
	private List<Item> items;
	private int limit;
	
	public Bag(List<Item> items, int limit) {
		super();
		this.items = items;
		this.limit = limit;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
