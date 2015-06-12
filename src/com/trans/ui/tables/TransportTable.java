package com.trans.ui.tables;

import java.util.List;

import javax.swing.JTable;

/**
 * @author Lili
 *
 */
public class TransportTable<T> extends JTable {

	private static final long serialVersionUID = 1L;

	private ITransportTableModel<T> tableModel;

	public TransportTable(ITransportTableModel<T> tableModel) {
		this.tableModel = tableModel;
		setModel(tableModel);
	}

	public T getSelectedItem() {
		int i = getSelectedRow();
		if (i < 0) {
			return null;
		}
		return tableModel.getItemAt(i);
	}

	public void reload(List<T> items) {
		tableModel.reload(items);
	}
}
