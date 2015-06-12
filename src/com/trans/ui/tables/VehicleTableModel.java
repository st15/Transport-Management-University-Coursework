package com.trans.ui.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.trans.domain.Vehicle;

/**
 * 
 * @author Lili
 */
public class VehicleTableModel extends AbstractTableModel implements ITransportTableModel<Vehicle> {

	private static final long serialVersionUID = 1L;

	private List<Vehicle> vehicles;
	
	private String[] colNames = { "Vehicle number", "Reg. number", "Model", "Capacity (kg)" };

	private Class<?>[] colTypes = { Integer.class, String.class, String.class, Integer.class };

	public VehicleTableModel() {
	}

	public void reload(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
		fireTableDataChanged();
	}

	@Override
	public Class<?> getColumnClass(int column) {
		return colTypes[column];
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}

	@Override
	public int getRowCount() {
		if (vehicles == null) {
			return 0;
		}
		return vehicles.size();
	}

	@Override
	public Object getValueAt(int line, int column) {
		Vehicle vehicle = vehicles.get(line);
		switch (column) {
		case 0:
			return vehicle.getVehicleNumber();
		case 1:
			return vehicle.getRegistrationNumber();
		case 2:
			return vehicle.getModel().getModel();
		case 3:
			return vehicle.getModel().getLoadCapacity();
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Vehicle getItemAt(int index) {
		return vehicles.get(index);
	}

}
