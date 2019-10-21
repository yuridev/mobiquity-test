package com.mobiquityinc.model;

import com.mobiquityinc.exception.APIException;

public class Item {

    private Integer index;
    private double weight;
    private double price;
	
    public Item(Integer index, double weight, double price) {
		super();
		this.index = index;
		this.weight = weight;
		this.price = price;
	}
    
    public boolean isValid() throws APIException {
    	if(weight > 100) {
			throw new APIException("The item weight souldn't be more than 100.");
		}
		
		if(price > 100) {
			throw new APIException("The item weight souldn't be more than 100.");
		}
    	return true;
    }

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
    
    

}
