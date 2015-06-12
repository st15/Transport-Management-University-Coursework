package com.trans.ui.forms;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Lili
 */
public class ModelForm extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField modelName;
	private JTextField loadCapacity;
	
	public ModelForm() {
		super(new GridBagLayout());
				
		modelName = new JTextField();
		modelName.setColumns(20);
		JPanel modelNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		modelNamePanel.add(modelName);
		JLabel modelNameLabel = new JLabel("Model name", JLabel.RIGHT);
		modelNameLabel.setLabelFor(modelName);
		
		loadCapacity = new JTextField();
		loadCapacity.setColumns(20);
		JPanel capacityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		capacityPanel.add(loadCapacity);
		JLabel capacityLabel = new JLabel("Load capacity", JLabel.RIGHT);
		capacityLabel.setLabelFor(loadCapacity);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(3,3,3,3);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		this.add(modelNameLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		this.add(modelNamePanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		this.add(capacityLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		this.add(capacityPanel, gbc);
		
	}
	
	public String getModelName() {
		return modelName.getText();
	}

	public String getLoadCapacity() {
		return loadCapacity.getText();
	}
}
