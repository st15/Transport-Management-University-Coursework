/**
 * 
 */
package com.trans.domain;

/**
 * @author Lili
 *
 */
public class Model {
	private int id;
	
	private String model;
	
	private int loadCapacity = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getLoadCapacity() {
		return loadCapacity;
	}
	public void setLoadCapacity(int loadCapacity) {
		this.loadCapacity = loadCapacity;
	}

	@Override
	public String toString() {
		return model + " (" + loadCapacity + "kg)";
	}
}
