/**
 * 
 */
package com.trans.domain;

/**
 * @author Lili
 *
 */
public class Driver {
	private int id;
	private String firstName;
	private String lastName;
	private long egn;
	private Integer vehicleNumber;
	private String town;
	private String address;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(Integer vehicleId) {
		this.vehicleNumber = vehicleId;
	}
	public long getEgn() {
		return egn;
	}
	public void setEgn(long egn) {
		this.egn = egn;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return  firstName + " " + lastName
				+ ", EGN: " + egn 
				+ ", Vehicle id: " + vehicleNumber 
				+ ", Address: " + address;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
}
