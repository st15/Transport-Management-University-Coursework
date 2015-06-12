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

import com.trans.domain.Model;
import com.trans.domain.Vehicle;

/**
 * 
 * @author Lili
 */
public class VehicleForm extends JPanel {
	private static final long serialVersionUID = 1L;

	private Vehicle selectedVehicle;
	
	private JTextField idNumber;
	private JTextField registrationNumber;
	private JComboBox<Model> modelsList;

	public VehicleForm() {
		super(new GridBagLayout());
		
		idNumber = new JTextField();
		idNumber.setColumns(10);
		JPanel idNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		idNumberPanel.add(idNumber);
		JLabel idNumberLabel = new JLabel("Vehicle number", JLabel.RIGHT);
		idNumberLabel.setLabelFor(idNumber);
		
		registrationNumber = new JTextField();
		registrationNumber.setColumns(20);
		JPanel regNumPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		regNumPanel.add(registrationNumber);
		JLabel regNumLabel = new JLabel("Registration number", JLabel.RIGHT);
		regNumLabel.setLabelFor(registrationNumber);
		
		
		modelsList = new JComboBox<Model>();
		
		JPanel modelsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		modelsPanel.add(modelsList);
		JLabel modelsLabel = new JLabel("Models", JLabel.RIGHT);
		modelsLabel.setLabelFor(modelsList);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(3,3,3,3);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		this.add(idNumberLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		this.add(idNumberPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		this.add(regNumLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		this.add(regNumPanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LINE_END;
		this.add(modelsLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LINE_START;
		this.add(modelsPanel, gbc);
	}
	
	public void setModelList(List<Model> models) {
		modelsList.removeAllItems();
		for(Model m : models)
			modelsList.addItem(m);
		modelsList.setSelectedIndex(modelsList.getItemCount()-1);
	}

	public Vehicle getVehicle() {
		
		selectedVehicle.setRegistrationNumber(registrationNumber.getText());
		selectedVehicle.setVehicleNumber(Integer.parseInt(idNumber.getText()));
		selectedVehicle.setModel((Model) modelsList.getSelectedItem());
		return selectedVehicle;
	}
	
	public void setVehicle(Vehicle vehicle) {
		
		this.selectedVehicle = vehicle;
		
		idNumber.setText(Integer.toString(vehicle.getVehicleNumber()));
		registrationNumber.setText(vehicle.getRegistrationNumber());
		
		for (int i = 0; i < modelsList.getItemCount(); i++)
		{
			Model listModel = (Model) modelsList.getItemAt(i);
			if (vehicle.getModel() != null && vehicle.getModel().getId() == listModel.getId())
			{
				modelsList.setSelectedIndex(i);
				return;
			}
		}
		modelsList.setSelectedIndex(-1);
	}
}