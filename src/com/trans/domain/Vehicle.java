package com.trans.domain;

/**
 * @author Lili
 *
 */
public class Vehicle {
	private int id;
	private int vehicleNumber; // номер на кола във фирмата
	private String registrationNumber;
	private Model model;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(int vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	@Override
	public String toString() {
		return getVehicleNumber() + ", " 
				+ getRegistrationNumber() + ", "
				+ model.toString();
	}
}
