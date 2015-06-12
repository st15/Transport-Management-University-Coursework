package com.trans.ui.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.trans.domain.Driver;

/**
 * 
 * @author Lili
 */
public class DriverTableModel extends AbstractTableModel implements ITransportTableModel<Driver> {

	private static final long serialVersionUID = 1L;

	private List<Driver> drivers;
	
	private String[] colNames = { "First Name", "Last Name", "EGN", "Vehicle", "Town", "Address" };

	private Class<?>[] colTypes = { String.class, String.class, Integer.class, Integer.class, String.class, String.class };

	public DriverTableModel() {
	}

	public void reload(List<Driver> drivers) {
		this.drivers = drivers;
		fireTableDataChanged();
	}

	@Override
	public Class<?> getColumnClass(int column) {
		return colTypes[column];
	}

	@Override
	public int getColumnCount() {
		return colNames.length; //4;
	}

	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}

	@Override
	public int getRowCount() {
		if (drivers == null) {
			return 0;
		}
		return drivers.size();
	}

	@Override
	public Object getValueAt(int line, int column) {
		Driver driver = drivers.get(line);
		switch (column) {
		case 0:
			return driver.getFirstName();
		case 1:
			return driver.getLastName();
		case 2:
			return driver.getEgn();
		case 3:
			return driver.getVehicleNumber();
		case 4:
			return driver.getTown();
		case 5:
			return driver.getAddress();
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Driver getItemAt(int index) {
		return drivers.get(index);
	}
}
