package com.trans.ui.forms;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trans.domain.Driver;

/**
 * 
 * @author Lili
 */
public class DriverForm extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static Logger LOG = LogManager.getLogger(DriverForm.class.getName());
	
	private Driver selectedDriver;

	private JTextField firstName;
	private JTextField lastName;
	private JTextField egn;
	private JComboBox<Integer> vehicle;
	private JTextField town;
	private JTextField address;

	public DriverForm() {
		super(new GridBagLayout());
				
		firstName = new JTextField();
		firstName.setColumns(20);
		JPanel firstNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		firstNamePanel.add(firstName);
		JLabel firstNameLabel = new JLabel("First name", JLabel.RIGHT);
		firstNameLabel.setLabelFor(firstName);
		
		lastName = new JTextField();
		lastName.setColumns(20);
		JPanel lastNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lastNamePanel.add(lastName);
		JLabel lastNameLabel = new JLabel("Last name", JLabel.RIGHT);
		lastNameLabel.setLabelFor(lastName);

		egn = new JTextField();
		egn.setColumns(10);
		JPanel egnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		egnPanel.add(egn);
		JLabel egnLabel = new JLabel("EGN", JLabel.RIGHT);
		egnLabel.setLabelFor(egn);
		
		vehicle = new JComboBox<Integer>();
//		vehicle.setPreferredSize(new Dimension(100, 30));
		
		JPanel vehiclePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		vehiclePanel.add(vehicle);
		JLabel vehicleLabel = new JLabel("Vehicle Number", JLabel.RIGHT);
		vehicleLabel.setLabelFor(vehicle);
		
		town = new JTextField();
		town.setColumns(25);
		JPanel townPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		townPanel.add(town);
		JLabel townLabel = new JLabel("Town", JLabel.RIGHT);
		townLabel.setLabelFor(town);
		
		address = new JTextField();
		address.setColumns(25);
		JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		addressPanel.add(address);
		JLabel addressLabel = new JLabel("Address", JLabel.RIGHT);
		addressLabel.setLabelFor(address);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(3,3,3,3);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		this.add(firstNameLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		this.add(firstNamePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		this.add(lastNameLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		this.add(lastNamePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LINE_END;
		this.add(egnLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LINE_START;
		this.add(egnPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LINE_END;
		this.add(vehicleLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LINE_START;
		this.add(vehiclePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.LINE_END;
		this.add(townLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.LINE_START;
		this.add(townPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.LINE_END;
		this.add(addressLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.LINE_START;
		this.add(addressPanel, gbc);
	}
	
	public Driver getDriver() {

		selectedDriver.setAddress(address.getText());
		selectedDriver.setEgn(Long.parseLong(egn.getText()));
		selectedDriver.setFirstName(firstName.getText());
		selectedDriver.setLastName(lastName.getText());
		selectedDriver.setTown(town.getText());
		
		selectedDriver.setVehicleNumber((Integer) vehicle.getSelectedItem());
		
		return selectedDriver;
	}
	
	public void setVehiclesList(List<Integer> vehicles) {
		vehicle.removeAllItems();
		for (Integer vehicleId : vehicles)
			vehicle.addItem(vehicleId);
		vehicle.setSelectedIndex(vehicle.getItemCount()-1);
	}
	
	public void setDriver(Driver driver) {
		
		this.selectedDriver = driver;
		LOG.info("driver.getVehicleId() : " + driver.getVehicleNumber());
		
		lastName.setText(driver.getLastName());
		firstName.setText(driver.getFirstName());
		egn.setText(Long.toString(driver.getEgn()));
		town.setText(driver.getTown());
		address.setText(driver.getAddress());
		
		for (int i = 0; i < vehicle.getItemCount(); i++)
		{
			Integer vehicleId = (Integer) vehicle.getItemAt(i);
			LOG.info("vehicle.getItemAt(i) : " + vehicleId);
			if (vehicleId.equals(driver.getVehicleNumber())) // == работи само за Integer от -128 до 127, заради кеширането
			{
				LOG.info("equal ");
				vehicle.setSelectedIndex(i);
				return;
			}
		}
		vehicle.setSelectedIndex(-1);
	}
}